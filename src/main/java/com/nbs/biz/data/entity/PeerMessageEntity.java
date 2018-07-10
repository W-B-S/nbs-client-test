package com.nbs.biz.data.entity;

/**
 * @Package : com.nbs.biz.data.entity
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/2-15:43
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class PeerMessageEntity extends BasicEntity {
    /**
     * uuid
     */
    private String id;
    private String seqno;
    private String fromid;
    private String fromhash;
    private Integer self;
    private String topic;
    private String roomid;
    private String msg;
    private Integer mtype;
    private String ip;
    private Long recvtime;
    private Integer ctime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeqno() {
        return seqno;
    }

    public void setSeqno(String seqno) {
        this.seqno = seqno;
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    public String getFromhash() {
        return fromhash;
    }

    public void setFromhash(String fromhash) {
        this.fromhash = fromhash;
    }

    public Integer getSelf() {
        return self;
    }

    public void setSelf(Integer self) {
        this.self = self;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getMtype() {
        return mtype;
    }

    public void setMtype(Integer mtype) {
        this.mtype = mtype;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getRecvtime() {
        return recvtime;
    }

    public void setRecvtime(Long recvtime) {
        this.recvtime = recvtime;
    }

    public Integer getCtime() {
        return ctime;
    }

    public void setCtime(Integer ctime) {
        this.ctime = ctime;
    }
}
