package io.nbs.sdk.beans;

import io.nbs.sdk.prot.IPMTypes;

/**
 * @Package : io.nbs.sdk.beans
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/3-13:48
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MessageBaseBean<C> {

    /**
     *
     * @param seqno
     * @param from
     * @param mtype
     * @param content
     * @param topics
     */
    public MessageBaseBean(String seqno, String from,IPMTypes mtype, C content, String[] topics) {
        this.seqno = seqno;
        this.from = from;
        this.mtype = mtype.name();
        this.content = content;
        this.topics = topics;
    }

    public MessageBaseBean(String seqno, String from, IPMTypes mtype, C content) {
        this.seqno = seqno;
        this.from = from;
        this.mtype = mtype.name();
        this.content = content;
    }

    public MessageBaseBean(String from, C content) {
        this.from = from;
        this.content = content;
        this.mtype = IPMTypes.nomarl.name();
    }

    public MessageBaseBean() {
        this.mtype = IPMTypes.nomarl.name();
    }

    /**
     * key
     */
    private String seqno;
    /**
     * fromid
     */
    private String from;
    /**
     * text,online,ctrl
     */
    private String mtype;
    /**
     * content
     */
    private C content;

    private String[] topics;
    /**
     * peerid
     */
    private String pid;

    private Long ts;

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

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public C getContent() {
        return content;
    }

    public void setContent(C content) {
        this.content = content;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String[] getTopics() {
        return topics;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }
}
