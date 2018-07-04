package io.ipfs.nbs.helper;

import io.nbs.client.ui.components.adapters.MessageMouseListener;
import io.nbs.client.ui.panels.im.messages.MessageLeftTextViewHolder;
import io.nbs.client.ui.panels.im.messages.MessageRightTextViewHolder;
import io.nbs.client.ui.panels.im.messages.MessageSystemMessageViewHolder;

import javax.swing.*;
import java.awt.event.MouseListener;
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

    /**
     *
     * @return
     */
    public synchronized MessageRightTextViewHolder tryGetRightTextViewHolder(){
        MessageRightTextViewHolder holder =null;
        if(rightTextPosition <CACHE_CAPACITY && rightTextViewHolders.size()>0){
            holder = rightTextViewHolders.get(rightTextPosition);
            rightTextPosition++;
        }
        return holder;
    }

    /**
     *
     * @return
     */
    public synchronized MessageLeftTextViewHolder tryGetLeftTextViewHolder()
    {
        MessageLeftTextViewHolder holder = null;
        if (leftTextPosition < CACHE_CAPACITY && leftTextViewHolders.size() > 0)
        {
            holder = leftTextViewHolders.get(leftTextPosition);
            leftTextPosition++;
        }

        return holder;
    }

    /**
     * @Date    : 2018/7/3 0:04
     * @Author  : lanbery
     * @Decription :
     * <p></p>
     * @Param   :
     * @return
     * @throws
     */
    public synchronized MessageSystemMessageViewHolder tryGetSystemMessageViewHolder()
    {
        MessageSystemMessageViewHolder holder = null;
        if (systemMessagePosition < CACHE_CAPACITY && systemMessageViewHolders.size() > 0)
        {
            holder = systemMessageViewHolders.get(systemMessagePosition);
            systemMessagePosition++;
        }

        return holder;
    }

    public synchronized void reset(){
        for (int i = 0; i < rightTextPosition; i++)
        {
            MessageRightTextViewHolder viewHolder = rightTextViewHolders.get(i);
            clearMouseListener(viewHolder.messageBubble);
            clearMouseListener(viewHolder.resend);
            clearMouseListener(viewHolder.text);
        }

        for (int i = 0; i < leftTextPosition; i++)
        {
            MessageLeftTextViewHolder viewHolder = leftTextViewHolders.get(i);

            clearMouseListener(viewHolder.text);
            clearMouseListener(viewHolder.messageBubble);
            clearMouseListener(viewHolder.avatar);
        }

        rightTextPosition = 0;
        rightImagePosition = 0;
        rightAttachmentPosition = 0;

        leftTextPosition = 0;
        leftImagePosition = 0;
        leftAttachmentPosition = 0;

        systemMessagePosition = 0;
    }

    private void clearMouseListener(JComponent component)
    {
        for (MouseListener l : component.getMouseListeners())
        {
            if (l instanceof MessageMouseListener)
            {
                component.removeMouseListener(l);
            }
        }
    }
}
