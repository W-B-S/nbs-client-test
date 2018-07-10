package io.nbs.client.adapter;

import com.alibaba.fastjson.JSON;
import com.nbs.biz.data.entity.PeerContactsEntity;
import com.nbs.biz.service.PeerContactsService;
import io.nbs.client.listener.OnlineNotifier;
import io.nbs.client.ui.components.NbsListView;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.im.IMPeersPanel;
import io.nbs.client.vo.ContactsItem;
import io.nbs.sdk.beans.OnlineMessage;
import io.nbs.sdk.beans.PeerInfo;
import io.nbs.sdk.beans.SystemCtrlMessageBean;
import io.nbs.sdk.prot.IPMTypes;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package : io.nbs.client.adapter
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/4-2:45
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ContactOnlineAdapter implements OnlineNotifier {
    private static Logger logger = LoggerFactory.getLogger(ContactOnlineAdapter.class);
    private SqlSession sqlSession;

    private static Map<String,ContactsItem> contactsCacheMap ;
    private PeerInfo info;

    private PeerContactsService peerContactsService;

    public ContactOnlineAdapter(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
        info = MainFrame.getContext().getCurrentPeer();
        contactsCacheMap = new HashMap<>();
        peerContactsService = new PeerContactsService(sqlSession);
    }

    @Override
    public void notifyRecvSystemMessage(SystemCtrlMessageBean messageBean) {
        if(messageBean!=null&&messageBean.getContent()!=null&&messageBean.getMtype().equals(IPMTypes.online.name())){
           if(messageBean.getContent() instanceof OnlineMessage){
                IMPeersPanel peersPanel = IMPeersPanel.getContext();
                OnlineMessage onlineMessage = (OnlineMessage)messageBean.getContent();
                if(info.getId().equals(onlineMessage.getId())){
                    logger.info("收到自己的上线消息:{}",JSON.toJSONString(messageBean));
                    return;
                }
               /**
                *  存库TODO
                *  刷新contacts
                *  异步刷新数据库
                */
               new Thread(()->{
                   inssetOrUpdateOnlineDB(messageBean);
               }).start();

                ContactsItem item  = new ContactsItem();
                item.setFormid(messageBean.getFrom());
                item.setName(onlineMessage.getNick());
                item.setAvatar(onlineMessage.getAvatar());
                item.setAvatarSuffix(onlineMessage.getAvatarSuffix());
                item.setId(onlineMessage.getId());
                item.setIp(onlineMessage.getIp());
                item.setLocations(onlineMessage.getLocations());
                item.setOnline(1);
                boolean exists = false;
                int pos = findOrAddContacts(item,peersPanel.getContactItems(),exists);
                if(exists){
                    peersPanel.getPeerlistView().notifyItemRangeInserted(pos,1);
                }else {
                    peersPanel.getContactItems().sort(ContactsItem::compareTo);
                    peersPanel.getPeerlistView().notifyDataSetChanged(false);
                    peersPanel.getPeerlistView().setAutoScroll2Top();
                }
           }
        }
    }

    /**
     * -1 没变化
     * @param item
     * @param peerList
     * @return
     */
    private int findOrAddContacts(ContactsItem item,List<ContactsItem> peerList,boolean exists){

        if(peerList==null){
            peerList = new ArrayList<>();
            peerList.add(item);
            return 0;
        }
        int i=0;
        for(ContactsItem peer : peerList){
            if(peer.getId()!=null&&peer.getId().equals(item.getId())){
                exists = true;
                peer = item;
                return i;
            }
            i++;
        }
        peerList.add(item);
        return peerList.size()-1;
    }

    /**
     * 独立线程处理
     * 上线信息
     * @param bean
     */
    private void inssetOrUpdateOnlineDB(SystemCtrlMessageBean bean){
        if(bean==null||bean.getContent()==null)return;
        if(bean.getContent() instanceof OnlineMessage){
            OnlineMessage onlineMessage = (OnlineMessage)bean.getContent();
            String id = onlineMessage.getId();

            PeerContactsEntity entity = peerContactsService.findById(id);
            if(entity==null){
                entity = new PeerContactsEntity();
                entity.setId(onlineMessage.getId());
                entity.setFromid(onlineMessage.getFrom());
                entity.setNick(onlineMessage.getNick());
                entity.setAvatar(onlineMessage.getAvatar());
                entity.setAvatarSuffix(onlineMessage.getAvatarSuffix());
                entity.setIp(onlineMessage.getIp());
                entity.setLocations(onlineMessage.getLocations());
                entity.setLmtime(System.currentTimeMillis());

                peerContactsService.insert(entity);
            }else {
                if(StringUtils.isNotBlank(onlineMessage.getAvatar()))
                    entity.setAvatar(onlineMessage.getAvatar());
                if(StringUtils.isNotBlank(onlineMessage.getAvatarSuffix()))
                    entity.setAvatarSuffix(onlineMessage.getAvatarSuffix());
                if(StringUtils.isNotBlank(onlineMessage.getNick()))
                    entity.setNick(onlineMessage.getNick());
                entity.setIp(onlineMessage.getIp());
                entity.setLocations(onlineMessage.getLocations());
                entity.setLmtime(System.currentTimeMillis());
                peerContactsService.update(entity);
            }
        }else {

        }
    }

    /**
     * TODO 数据库中查找并缓存到MAP 供消息用
     * @param item
     */
    private void loadContactPeersMap(ContactsItem item,List<ContactsItem> peerList){

    }

    private void updataCacheMap(ContactsItem item){
        if(item!=null&&StringUtils.isNotBlank(item.getFormid())){
            contactsCacheMap.put(item.getFormid(),item);
        }
    }

    @Override
    public void notifyRecvystemMessage(SystemCtrlMessageBean<OnlineMessage> ctrlMessageBean) {
        notifyRecvSystemMessage(ctrlMessageBean);
    }
}
