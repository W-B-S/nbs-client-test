package com.nbs.entity;

/**
 * @Package : com.nbs.entity
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/23-22:44
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ContactsItem  implements Comparable<ContactsItem>{
    private String hash;
    private String name;
    private String type;

    private ContactsItem(String hash) {
        this.hash = hash;
        this.type = TYPE.p.toString();
    }

    public ContactsItem(String hash, String name) {
        this.hash = hash;
        this.name = name;
        this.type = TYPE.p.toString();
    }

    public ContactsItem(String hash, String name, String type) {
        this.hash = hash;
        this.name = name;
        this.type = type;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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
    public int compareTo(ContactsItem o) {
        return 0;
    }

    /**
     * 群、点
     */
    public static enum TYPE{
        p,q;
    }
}
