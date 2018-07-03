package io.nbs.client.vo;

import io.nbs.commons.helper.CharacterParser;

/**
 * @Package : com.nbs.entity
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/24-9:13
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ContactsItem implements Comparable<ContactsItem> {
    /**
     * hash58
     */
    private String id;

    private String name;

    private String type;

    private String formid;
    /**
     *
     */
    private String avatar;

    private String avatarSuffix;

    private String ip;
    private String locations;
    /**
     * 是否在线
     */
    private Integer online;


    public ContactsItem() { }

    /**
     *
     * @param id
     * @param name
     */
    public ContactsItem(String id, String name) {
        this.id = id;
        this.name = name;
        this.type = TYPE.P.toString();
    }

    /**
     *
     * @param id
     * @param name
     * @param formid
     */
    public ContactsItem(String id, String name, String formid) {
        this.id = id;
        this.name = name;
        this.formid = formid;
        this.type = TYPE.P.toString();
    }

    /**
     *
     * @param id
     * @param name
     * @param formid
     * @param type
     */
    public ContactsItem(String id, String name,String formid, String type ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.formid = formid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("ContactsItem : {");
        sb.append("id : ").append(this.id==null ? "" : this.id).append(",");
        sb.append("name : ").append(this.name==null ? "" : this.name).append(",");
        sb.append("type : ").append(this.type==null ? "" : this.type);
        sb.append("}");
        return sb.toString();
    }

    @Override
    public int compareTo(ContactsItem o) {
        if(o==null)return -1;
        String tc = CharacterParser.getSelling(this.name).toUpperCase();
        String oc = CharacterParser.getSelling(o.name).toUpperCase();
        int r = tc.compareTo(oc);
        return 0==r ? this.id.compareTo(o.getId()) : r;
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
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

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    /**
     *
     */
    public static enum TYPE{
        Q,P;
    }
}
