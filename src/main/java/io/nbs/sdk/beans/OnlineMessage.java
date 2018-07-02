package io.nbs.sdk.beans;

/**
 * @Package : io.nbs.sdk.beans
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/2-17:10
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class OnlineMessage {
    //peerId
    private String id;
    private String nick;
    private String from;
    private String avatar;
    private String avatarFile;
    private String avatarSuffix;
    private String ip;
    private String locations;
    private long ts = System.currentTimeMillis();

    public OnlineMessage(String id, String nick, String from) {
        this.id = id;
        this.nick = nick;
        this.from = from;
    }

    public OnlineMessage(String id, String nick, String from, String avatar, String avatarFile, String avatarSuffix) {
        this.id = id;
        this.nick = nick;
        this.from = from;
        this.avatar = avatar;
        this.avatarFile = avatarFile;
        this.avatarSuffix = avatarSuffix;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(String avatarFile) {
        this.avatarFile = avatarFile;
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

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}
