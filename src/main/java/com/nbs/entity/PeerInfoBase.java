package com.nbs.entity;

/**
 * @Package : com.nbs.entity
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/22-18:40
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class PeerInfoBase {
    private String peerID;
    private String nick;
    private String avatarHash;
    private String from;
    private String avatarSuffix;

    public PeerInfoBase(String peerID) {
        this.peerID = peerID;
    }

    public PeerInfoBase(String peerID, String nick, String avatarHash) {
        this.peerID = peerID;
        this.nick = nick;
        this.avatarHash = avatarHash;
    }

    public String getPeerID() {
        return peerID;
    }

    public void setPeerID(String peerID) {
        this.peerID = peerID;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getAvatarSuffix() {
        return avatarSuffix;
    }

    public void setAvatarSuffix(String avatarSuffix) {
        this.avatarSuffix = avatarSuffix;
    }
}
