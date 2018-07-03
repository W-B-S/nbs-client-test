package io.nbs.client.adapter;

import io.nbs.client.listener.OnlineNotifier;
import io.nbs.client.ui.components.NbsListView;
import io.nbs.client.ui.panels.im.IMPeersPanel;
import io.nbs.client.vo.ContactsItem;
import io.nbs.sdk.beans.OnlineMessage;
import io.nbs.sdk.beans.PeerInfo;
import io.nbs.sdk.beans.SystemCtrlMessageBean;
import io.nbs.sdk.prot.IPMTypes;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

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
    private SqlSession sqlSession;

    private static Map<String,ContactsItem> contactsCacheMap ;

    public ContactOnlineAdapter(SqlSession sqlSession, List<ContactsItem> contactItems) {
        this.sqlSession = sqlSession;
        contactsCacheMap = new HashMap<>();


    }

    @Override
    public void notifyRecvSystemMessage(SystemCtrlMessageBean messageBean) {
        if(messageBean!=null&&messageBean.getContent()!=null&&messageBean.getMtype().equals(IPMTypes.online.name())){
           if(messageBean.getContent() instanceof OnlineMessage){
               IMPeersPanel peersPanel = IMPeersPanel.getContext();
               OnlineMessage onlineMessage = (OnlineMessage)messageBean.getContent();

               ContactsItem item  = new ContactsItem();
               item.setFormid(messageBean.getFrom());
               item.setName(onlineMessage.getNick());
               item.setAvatar(onlineMessage.getAvatar());
               item.setAvatarSuffix(onlineMessage.getAvatarSuffix());
               item.setId(onlineMessage.getId());
               item.setIp(onlineMessage.getIp());
               item.setLocations(onlineMessage.getLocations());
               item.setOnline(1);
               peersPanel.getContactItems().add(item);
               int size = peersPanel.getContactItems().size();
               peersPanel.getPeerlistView().notifyItemInserted(size-1,true);
               peersPanel.getPeerlistView().setAutoScrollToBottom();
               //存库TODO

           }
        }
    }

    /**
     * TODO 数据库中查找并缓存到MAP 供消息用
     * @param contactItems
     */
    private void loadContactPeersMap(List<ContactsItem> contactItems){

    }

    private void updataCacheMap(ContactsItem item){
        if(item!=null&&StringUtils.isNotBlank(item.getFormid())){
            contactsCacheMap.put(item.getFormid(),item);
        }
    }
}
