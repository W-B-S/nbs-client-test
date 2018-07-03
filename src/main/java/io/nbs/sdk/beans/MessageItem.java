package io.nbs.sdk.beans;

import com.nbs.ipfs.entity.IpfsMessage;
import io.nbs.client.vo.ContactsItem;

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
     * uuid
     */
    private String id;
    /**
     *
     */
    private String seqno;
    /**
     * 群聊 TOPIC
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
     * 发送者peerID
     */
    private String senderId;
    /**
     * 发送者from
     */
    private String from;
    private boolean needToResend;
    /**
     * 0系统
     * 1,2,3 左边
     * -1,-2,-3右边
     */
    private int messageType;

    private long updatedAt;

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
    public MessageItem(ContactsItem item, IpfsMessage message) {
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

    public static int getSystemMessage() {
        return SYSTEM_MESSAGE;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
