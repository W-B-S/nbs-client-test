package com.nbs.entity;

import com.nbs.ipfs.entity.IpfsMessage;

/**
 * @Package : com.nbs.entity
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/26-23:34
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MessageItem implements Comparable<MessageItem> {
    public static final int SYSTEM_MESSAGE = 0;
    public static final int LEFT_TEXT = 1;
    public static final int LEFT_IMAGE = 2;
    public static final int LEFT_ATTACHMENT = 3;

    public static final int RIGHT_TEXT = -1;
    public static final int RIGHT_IMAGE = -2;
    public static final int RIGHT_ATTACHMENT = -3;

    /**
     *
     */
    private String seqno;
    /**
     * 群聊
     */
    private String roomId;
    /**
     * 消息内容
     */
    private String messageContent;
    private long timestamp;
    /**
     * nickname
     */
    private String senderUsername;
    /**
     * peerID
     */
    private String senderId;
    /**
     *
     */
    private String from;
    private boolean needToResend;

    public MessageItem() {
    }

    public MessageItem(long timestamp) {
        this.timestamp = timestamp;
    }

    public MessageItem(String seqno, String messageContent, String senderId) {
        this.seqno = seqno;
        this.messageContent = messageContent;
        this.senderId = senderId;
    }

    /**
     * TODO
     * @param item
     * @param message
     */
    public MessageItem(ContactsItem item,IpfsMessage message) {
        this.senderUsername = senderUsername;
    }

    @Override
    public int compareTo(MessageItem o) {
        return (int) (this.getTimestamp() - o.getTimestamp());
    }

    public String getSeqno() {
        return seqno;
    }

    public void setSeqno(String seqno) {
        this.seqno = seqno;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public boolean isNeedToResend() {
        return needToResend;
    }

    public void setNeedToResend(boolean needToResend) {
        this.needToResend = needToResend;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
