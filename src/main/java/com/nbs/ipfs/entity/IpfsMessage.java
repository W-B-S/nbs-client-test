package com.nbs.ipfs.entity;

import io.nbs.commons.utils.Base64CodecUtil;

/**
 * @Package : com.nbs.ipfs.entity
 * @Description :
 * <p>
 *
 * </p>
 * @Author : lambor.c
 * @Date : 2018/6/24-20:45
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IpfsMessage {
    private String from;
    private String seqno;
    private String data;
    private String[] topicIDs;
    private String contents;
    private String peerId;

    private String time;

    private Base64CodecUtil.CtrlTypes types;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSeqno() {
        return seqno;
    }

    public void setSeqno(String seqno) {
        this.seqno = seqno;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String[] getTopicIDs() {
        return topicIDs;
    }

    public void setTopicIDs(String[] topicIDs) {
        this.topicIDs = topicIDs;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getPeerId() {
        return peerId;
    }

    public void setPeerId(String peerId) {
        this.peerId = peerId;
    }

    public Base64CodecUtil.CtrlTypes getTypes() {
        return types;
    }

    public void setTypes(Base64CodecUtil.CtrlTypes types) {
        this.types = types;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
