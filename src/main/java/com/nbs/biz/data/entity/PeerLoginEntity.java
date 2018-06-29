package com.nbs.biz.data.entity;

/**
 * @Package : com.nbs.biz.data.entity
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/29-20:24
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class PeerLoginEntity extends BasicModel {

    private String id;
    private String fromid;
    private String nick;
    private String avatar;
    private String avatarSuffix;
    private String ip;
    private String locations;
    private String remark;
    private String extJson;
    private String seckey;
    private String sysuser;
    private Integer ctime;
    private Integer lmtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarSuffix() {
        return avatarSuffix;
    }

    public void setAvatarSuffix(String avatarSuffix) {
        this.avatarSuffix = avatarSuffix;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtJson() {
        return extJson;
    }

    public void setExtJson(String extJson) {
        this.extJson = extJson;
    }

    public String getSeckey() {
        return seckey;
    }

    public void setSeckey(String seckey) {
        this.seckey = seckey;
    }

    public String getSysuser() {
        return sysuser;
    }

    public void setSysuser(String sysuser) {
        this.sysuser = sysuser;
    }

    public Integer getCtime() {
        return ctime;
    }

    public void setCtime(Integer ctime) {
        this.ctime = ctime;
    }

    public Integer getLmtime() {
        return lmtime;
    }

    public void setLmtime(Integer lmtime) {
        this.lmtime = lmtime;
    }
}
