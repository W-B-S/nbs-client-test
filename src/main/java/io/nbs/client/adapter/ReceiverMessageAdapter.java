package io.nbs.client.adapter;

import io.ipfs.api.IPFS;
import io.nbs.client.listener.IPFSSubscribeListener;
import io.nbs.client.services.IpfsMessageSender;
import io.nbs.client.ui.components.NbsListView;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.im.IMPeersPanel;
import io.nbs.client.vo.ContactsItem;
import io.nbs.commons.helper.ConfigurationHelper;
import io.nbs.sdk.beans.MessageItem;
import io.nbs.sdk.beans.PeerInfo;

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


    public ReceiverMessageAdapter(List<MessageItem> items, NbsListView listView) {
        this.items = items;
        this.listView = listView;
    }

    @Override
    public void notifyRecvMessage(MessageItem item) {
        PeerInfo peerInfo = MainFrame.getContext().getCurrentPeer();


        if(item.getFrom().equals(peerInfo.getFrom())){
            //TODO 未来忽略自己发的
/*            item.setMessageType(MessageItem.RIGHT_TEXT);
            item.setId(peerInfo.getId());
            item.setSenderUsername(peerInfo.getNick());

            items.add(item);
            listView.notifyItemInserted(items.size()-1,true);
            listView.setAutoScrollToBottom();*/
        }else {
            item.setMessageType(MessageItem.LEFT_TEXT);
            item = findInPeerList(item);
            items.add(item);
            int postion = items.size()<=0 ? 0:items.size()-1;
            listView.notifyItemInserted(postion,true);
            listView.setAutoScrollToBottom();
            //TODO 存库

            //autoRepaly(item.getSenderUsername());
        }
    }

    private void autoRepaly(final String name){
        new Thread(()->{
            IpfsMessageSender sender = MainFrame.getContext().getMessageSender();
            String text = "你好"+name+"收到！";
            try {
                sender.ipfsSendMessage(text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
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
