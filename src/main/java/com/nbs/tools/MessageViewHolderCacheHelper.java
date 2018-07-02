package com.nbs.tools;

import io.nbs.client.ui.panels.im.messages.MessageLeftTextViewHolder;
import io.nbs.client.ui.panels.im.messages.MessageRightTextViewHolder;
import io.nbs.client.ui.panels.im.messages.MessageSystemMessageViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package : com.nbs.tools
 * @Description :
 * <p>
 *     提供消息ViewHolder缓存,
 *     对消息的ViewHolder进入缓存能大大加速消息列表的加载速度，在刚进入房间时，默认先加载10条消息，
 * </p>
 * @Author : lambor.c
 * @Date : 2018/6/26-23:46
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MessageViewHolderCacheHelper {
    private final int CACHE_CAPACITY = 20;
    private List<MessageRightTextViewHolder> rightTextViewHolders = new ArrayList<>();

    private List<MessageLeftTextViewHolder> leftTextViewHolders = new ArrayList<>();

    private List<MessageSystemMessageViewHolder> systemMessageViewHolders = new ArrayList<>();

    private int rightTextPosition = 0;
    private int rightImagePosition = 0;
    private int rightAttachmentPosition = 0;
    private int leftTextPosition = 0;
    private int leftImagePosition = 0;
    private int leftAttachmentPosition = 0;
    private int systemMessagePosition = 0;

    public MessageViewHolderCacheHelper() {
        initHolders();
    }

    private void initHolders(){
        new Thread(()->{
            long startTime = System.currentTimeMillis();
            initLeftTextViewHolder();
            initRightTextViewHolder();
        }).start();
    }

    private void initRightTextViewHolder(){
        for(int i=0;i<CACHE_CAPACITY;i++){
            rightTextViewHolders.add(new MessageRightTextViewHolder());
        }
    }

    private void initLeftTextViewHolder(){
        for(int i=0;i<CACHE_CAPACITY;i++){
            leftTextViewHolders.add(new MessageLeftTextViewHolder());
        }
    }
}
