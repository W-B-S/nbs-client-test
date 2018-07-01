package io.nbs.commons.adapter;

import com.nbs.entity.MessageItem;
import com.nbs.tools.MessageViewHolderCacheHelper;
import io.ipfs.nbs.ui.components.NbsListView;
import com.nbs.ui.holder.BaseMessageViewHolder;
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
