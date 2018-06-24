package com.nbs.biz.model;

/**
 * @Package : com.nbs.biz.model
 * @Description :
 * <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/24-9:06
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ContactsModel extends BasicModel {

    private String id;
    private String nick;
    /**
     * hash58
     */
    private String avatar;
    /**
     * .png
     */
    private String avatarSuffix;

    private String remark;
    private String extJson;
    private String seckey;
    private Integer ctime;
    private Integer lmtime;

    public ContactsModel(String id, String nick, String avatar, String avatarSuffix) {
        this.id = id;
        this.nick = nick;
        this.avatar = avatar;
        this.avatarSuffix = avatarSuffix;
    }

    public ContactsModel(String id, String nick) {
        this.id = id;
        this.nick = nick;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
