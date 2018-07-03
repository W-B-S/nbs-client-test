package io.nbs.client.adapter;

import io.nbs.client.listener.IPFSSubscribeListener;
import io.nbs.client.ui.components.NbsListView;
import io.nbs.client.ui.frames.MainFrame;
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
            item.setMessageType(MessageItem.RIGHT_TEXT);
            item.setId(peerInfo.getId());
            item.setSenderUsername(peerInfo.getNick());
            //TODO 未来忽略自己发的
            items.add(item);
            listView.notifyItemInserted(items.size()-1,true);
            listView.setAutoScrollToBottom();
        }else {
            item.setMessageType(MessageItem.LEFT_TEXT);
            //TODO
           // item.setId(peerInfo.getId());
            item.setSenderUsername(peerInfo.getNick());
            listView.notifyItemInserted(items.size()-1,true);
            listView.setAutoScrollToBottom();
        }
    }
}
