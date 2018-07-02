package io.nbs.client.adapter;

import io.nbs.sdk.beans.MessageItem;
import io.ipfs.nbs.helper.MessageViewHolderCacheHelper;
import io.nbs.client.ui.components.NbsListView;
import io.nbs.client.ui.holders.BaseMessageViewHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Package : com.nbs.ui.adapter
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/27-1:15
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MessageAdapter extends BaseAdapter<BaseMessageViewHolder> {
    private static final Logger logger = LoggerFactory.getLogger(MessageAdapter.class);

    private List<MessageItem> messageItems;

    private NbsListView listView;
    MessageViewHolderCacheHelper messageViewHolderCacheHelper;

    /**
     *
     * @param messageItems 消息数组
     * @param listView 消息展示表容器
     * @param messageViewHolderCacheHelper 消息缓存处理类
     */
    public MessageAdapter(List<MessageItem> messageItems, NbsListView listView,MessageViewHolderCacheHelper messageViewHolderCacheHelper) {
        this.messageItems = messageItems;
        this.listView = listView;
        this.messageViewHolderCacheHelper = messageViewHolderCacheHelper;
    }

    @Override
    public BaseMessageViewHolder onCreateViewHolder(int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BaseMessageViewHolder viewHolder, int position) {

    }
}
