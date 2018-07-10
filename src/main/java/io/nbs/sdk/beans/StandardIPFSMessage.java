package io.nbs.sdk.beans;

/**
 * @Package : io.nbs.sdk.beans
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/4-0:18
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class StandardIPFSMessage {
    private String seqno;
    private String from;
    private String data;
    private String[] topicIDs;
    private String content;
    /**
     * 对应IPMTypes
     */
    private String mtype;

    public String getSeqno() {
        return seqno;
    }

    public void setSeqno(String seqno) {
        this.seqno = seqno;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
