package io.nbs.client.adapter;

import io.nbs.client.Launcher;
import io.nbs.client.cnsts.AppGlobalCnst;
import io.nbs.client.helper.AvatarImageHandler;
import io.nbs.client.ui.components.adapters.MessageMouseListener;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.holders.ViewHolder;
import io.nbs.client.ui.panels.im.messages.MessageLeftTextViewHolder;
import io.nbs.client.ui.panels.im.messages.MessageRightTextViewHolder;
import io.nbs.client.ui.panels.im.messages.MessageSystemMessageViewHolder;
import io.nbs.commons.utils.AvatarUtil;
import io.nbs.commons.utils.TimeUtil;
import io.nbs.sdk.beans.MessageItem;
import io.ipfs.nbs.helper.MessageViewHolderCacheHelper;
import io.nbs.client.ui.components.NbsListView;
import io.nbs.client.ui.holders.BaseMessageViewHolder;
import io.nbs.sdk.beans.PeerInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.text.ViewFactory;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
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

    /**
     * 消息数组
     */
    private List<MessageItem> messageItems;

    private NbsListView listView;
    MessageViewHolderCacheHelper messageViewHolderCacheHelper;

    private PeerInfo currentPeer;
    /**
     *
     * @param messageItems 消息数组
     * @param listView 消息展示表容器
     * @param messageViewHolderCacheHelper 消息缓存处理类
     */
    public MessageAdapter(List<MessageItem> messageItems, NbsListView listView,MessageViewHolderCacheHelper messageViewHolderCacheHelper) {
        this.currentPeer = MainFrame.getContext().getCurrentPeer();
        this.messageItems = messageItems;
        this.listView = listView;
        this.messageViewHolderCacheHelper = messageViewHolderCacheHelper;
    }

    /**
     * 获取消息类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return messageItems.get(position).getMessageType();
    }

    @Override
    public void onBindViewHolder(BaseMessageViewHolder viewHolder, int position) {
        if(viewHolder==null)return;
        final MessageItem item = messageItems.get(position);
        MessageItem prevItem = 0 == position ? null : messageItems.get(position-1);
        processTimeAndAvatar(item, prevItem, viewHolder);
        if(viewHolder instanceof MessageSystemMessageViewHolder){
            processSystemMessage(viewHolder,item);
        }
        else if(viewHolder instanceof MessageRightTextViewHolder){
            processRightTextMessage(viewHolder, item);
        }
        else if(viewHolder instanceof MessageLeftTextViewHolder){
            processLeftTextMessage(viewHolder, item);
        }
    }

    /**
     * @Date    : 2018/7/3 20:41
     * @Author  : lanbery
     * @Decription :
     * <p></p>
     * @Param   :
     * @return
     * @throws
     */
    @Override
    public BaseMessageViewHolder onCreateViewHolder(int viewType) {
        switch (viewType){
            case MessageItem.SYSTEM_MESSAGE :
                MessageSystemMessageViewHolder holder = messageViewHolderCacheHelper.tryGetSystemMessageViewHolder();
                if(holder==null){
                    holder = new MessageSystemMessageViewHolder();
                }
                return holder;
            case MessageItem.RIGHT_TEXT:
            case MessageItem.RIGHT_ATTACHMENT:
            case MessageItem.RIGHT_IMAGE:
                MessageRightTextViewHolder rHolder = messageViewHolderCacheHelper.tryGetRightTextViewHolder();
                if(rHolder==null){
                    rHolder = new MessageRightTextViewHolder();
                }
                return rHolder;
            case MessageItem.LEFT_TEXT:
            case MessageItem.LEFT_ATTACHMENT:
            case MessageItem.LEFT_IMAGE:
                MessageLeftTextViewHolder lHolder = messageViewHolderCacheHelper.tryGetLeftTextViewHolder();
                if(lHolder==null){
                    lHolder = new MessageLeftTextViewHolder();
                }
                return lHolder;
        }

        return null;
    }

    /*===================================== 消息处理 =======================================*/

    private void processLeftTextMessage(ViewHolder viewHolder, final MessageItem item){
        MessageLeftTextViewHolder holder = (MessageLeftTextViewHolder) viewHolder;

        holder.text.setText(item.getMessageContent());
        holder.text.setTag(item.getId());

        holder.sender.setText(item.getSenderUsername());
        ImageIcon selfIcon = new ImageIcon(AppGlobalCnst.consturactPath(AvatarImageHandler.getAvatarProfileHome(),currentPeer.getAvatarName()));
        selfIcon.setImage(selfIcon.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH));
        holder.avatar.setIcon(selfIcon);

        listView.setScrollHiddenOnMouseLeave(holder.messageBubble);
        listView.setScrollHiddenOnMouseLeave(holder.text);
        //attachPopupMenu(viewHolder, MessageItem.LEFT_TEXT);
    }

    private void processRightTextMessage(ViewHolder viewHolder,final MessageItem item){
        MessageRightTextViewHolder holder = (MessageRightTextViewHolder)viewHolder;
        holder.text.setText(item.getMessageContent());
        holder.text.setTag(item.getId());

        boolean needToUpdateResendStatus = !item.isNeedToResend() && item.getUpdatedAt() < 1 && System.currentTimeMillis() - item.getTimestamp() >100*1000;

        if(item.isNeedToResend()||needToUpdateResendStatus){
            if(needToUpdateResendStatus){
                //TODO 数据库
            }

            holder.sendingProgress.setVisible(false);
            holder.resend.setVisible(false);

        }
        else
        {
            holder.resend.setVisible(false);
            //TODO 发送进度条处理
        }

        holder.resend.addMouseListener(new MessageMouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                //
            }
        });

        listView.setScrollHiddenOnMouseLeave(holder.messageBubble);
        listView.setScrollHiddenOnMouseLeave(holder.text);
    }

    /**
     *
     * @param viewHolder
     * @param item
     */
    private void processSystemMessage(ViewHolder viewHolder, MessageItem item)
    {
        MessageSystemMessageViewHolder holder = (MessageSystemMessageViewHolder) viewHolder;
        holder.text.setText(item.getMessageContent());
    }

    /**
     *
     * @param item
     * @param preItem
     * @param holder
     */
    private void processTimeAndAvatar(MessageItem item, MessageItem preItem, BaseMessageViewHolder holder) {
        // 如果当前消息的时间与上条消息时间相差大于1分钟，则显示当前消息的时间
        if(preItem!=null){
            if (TimeUtil.inTheSameMinute(item.getTimestamp(), preItem.getTimestamp()))
            {
                holder.time.setVisible(false);
            }
            else
            {
                holder.time.setVisible(true);
                holder.time.setText(TimeUtil.diff(item.getTimestamp(), true));
            }
        }
        else
        {
            holder.time.setVisible(true);
            holder.time.setText(TimeUtil.diff(item.getTimestamp(), true));
        }

        if (holder.avatar != null) {
            ImageIcon icon = new ImageIcon();
            Image image = null;
            if(item.getMessageType()<0){
                PeerInfo currentPeer = MainFrame.getContext().getCurrentPeer();
                try {
                    image =  AvatarUtil.getPeerAvatar(currentPeer);
                    AvatarUtil.updateCacheIamge(currentPeer.getAvatarName(),image);
                } catch (IOException e) {

                }
            }
            if(image == null){
                boolean bflag = false;
                String identify = item.getSenderUsername();
                if(StringUtils.isNotBlank(item.getAvatar())){
                    identify = item.getAvatar();
                    bflag = true;
                }
                image = AvatarUtil.createOrLoadUserAvatar(identify,bflag,item.getSuffix());
            }
            icon.setImage( image.getScaledInstance(36,36,Image.SCALE_SMOOTH));
            holder.avatar.setIcon(icon);

            //如果不是自己
            if (item.getMessageType() == MessageItem.LEFT_TEXT
                    || item.getMessageType() == MessageItem.LEFT_IMAGE
                    || item.getMessageType() == MessageItem.LEFT_TEXT) {
               // bindAvatarAction(holder.avatar, item.getSenderUsername());
            }

        }
    }

    /**
     * 绑定头像事件
     * @param avatarLabel
     * @param username
     */
    private void bindAvatarAction(JLabel avatarLabel, String username){

    }
}
