package com.nbs.entity;

/**
 * @Package : com.nbs.entity
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/24-15:30
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class PeerBoradcastInfo {

    private String id;
    private String nick;
    private String avatarHash;
    private String avatarSuffix;
    private String from;

    /**
     *
     * @param id
     * @param nick
     * @param avatarHash
     * @param avatarSuffix
     */
    public PeerBoradcastInfo(String id, String nick, String avatarHash, String avatarSuffix) {
        this.id = id;
        this.nick = nick;
        this.avatarHash = avatarHash;
        this.avatarSuffix = avatarSuffix;
    }

    /**
     *
     * @param id
     * @param nick
     */
    public PeerBoradcastInfo(String id, String nick) {
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

    public String getAvatarHash() {
        return avatarHash;
    }

    public void setAvatarHash(String avatarHash) {
        this.avatarHash = avatarHash;
    }

    public String getAvatarSuffix() {
        return avatarSuffix;
    }

    public void setAvatarSuffix(String avatarSuffix) {
        this.avatarSuffix = avatarSuffix;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
