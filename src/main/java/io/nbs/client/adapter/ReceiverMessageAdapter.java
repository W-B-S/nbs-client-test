package io.nbs.client.adapter;


import com.nbs.biz.data.entity.PeerMessageEntity;
import com.nbs.biz.service.PeerMessageService;

import io.nbs.client.listener.IPFSSubscribeListener;
import io.nbs.client.services.IpfsMessageSender;
import io.nbs.client.ui.components.NbsListView;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.im.IMPeersPanel;
import io.nbs.client.vo.ContactsItem;

import io.nbs.commons.helper.ConfigurationHelper;
import io.nbs.commons.utils.DataBaseUtil;
import io.nbs.commons.utils.UUIDGenerator;
import io.nbs.sdk.beans.MessageItem;
import io.nbs.sdk.beans.OnlineMessage;
import io.nbs.sdk.beans.PeerInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Package : io.nbs.client.adapter
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/4-2:51
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ReceiverMessageAdapter implements IPFSSubscribeListener {

    private List<MessageItem> items;
    private NbsListView listView;

    private PeerMessageService peerMessageService;

    public ReceiverMessageAdapter(List<MessageItem> items, NbsListView listView) {
        this.items = items;
        this.listView = listView;
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        peerMessageService = new PeerMessageService(sqlSession);
    }

    @Override
    public void notifyRecvMessage(MessageItem item) {
        PeerInfo peerInfo = MainFrame.getContext().getCurrentPeer();

        if(item.getFrom().equals(peerInfo.getFrom())){
            // 未来忽略自己发的
        }else {
            item.setMessageType(MessageItem.LEFT_TEXT);
            item = findInPeerList(item);
            items.add(item);
            int postion = items.size()<=0 ? 0:items.size()-1;
            listView.notifyItemInserted(postion,true);
            listView.setAutoScrollToBottom();
            //
           saveWorldMessage(item);
            /**
             * AutoReplay enabled
             */
           if(ConfigurationHelper.getInstance().getCfgProps().getProperty("nbs.client.im.replay-open","false").equals("true")){
               autoRepaly(item.getSenderUsername());
           }
           //
        }
    }

    /**
     *
     * @param message
     */
    @Override
    public void notifyOnlineMessage(OnlineMessage message) {
        MessageItem item = new MessageItem();
        item.setId(UUIDGenerator.getUUID());
        item.setMessageType(MessageItem.SYSTEM_MESSAGE);
        item.setSenderUsername(message.getNick());
        item.setSenderId(message.getId());
        String locations = StringUtils.isNotBlank(message.getLocations()) ? message.getLocations() : "";
        String content = StringUtils.join("用户",message.getNick(),locations);
        item.setMessageContent(content);
        item.setTimestamp(message.getTs());
        items.add(item);
        int postion = items.size()>0 ? items.size()-1 : 0;
        listView.notifyItemInserted(postion,true);
        listView.setAutoScrollToBottom();
    }

    private void saveWorldMessage(MessageItem item){
        if(item==null)return;
        PeerMessageEntity entity = new PeerMessageEntity();
        entity.setId(item.getId());
        entity.setRecvtime(item.getTimestamp());
        entity.setSeqno(item.getSeqno());
        entity.setFromid(item.getFrom());
        entity.setFromhash(item.getSenderId());
        entity.setRoomid(item.getRoomId());
        entity.setMsg(item.getMessageContent());
        entity.setMtype(item.getMessageType());
        if(item.getMessageType()<0){
            entity.setSelf(1);
        }else {
            entity.setSelf(0);
        }
        peerMessageService.insert(entity);
    }

    /**
     *
     * @param name
     */
    private void autoRepaly(final String name){
        IpfsMessageSender sender = MainFrame.getContext().getMessageSender();
        String text = "你好"+name+"收到！";
        try {
            sender.ipfsSendMessage(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param item
     * @return
     */
    private MessageItem findInPeerList(MessageItem item){
        List<ContactsItem> contactsItems = IMPeersPanel.getContext().getContactItems();

        if(contactsItems==null||contactsItems.size()==0)return item;
        for(ContactsItem contactsItem : contactsItems ){
            if(contactsItem.getFormid()!=null&&contactsItem.getFormid().equals(item.getFrom())){
                item.setSenderId(contactsItem.getId());
                item.setSenderUsername(contactsItem.getName());
                item.setAvatar(contactsItem.getAvatar());
                return item;
            }
        }
        item.setSenderUsername("unkonw_peerid");
        return item;
    }
}
