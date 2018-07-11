package com.nbs.biz.data.entity;

import java.util.Date;

/**
 * @Package : com.nbs.biz.data.entity
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/5-17:06
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AttachmentInfoEntity extends BasicEntity {

    private String id;
    private String peername;
    private String peerhash;
    private String prehash;
    private String fname;
    private Long fsize;
    private String fsuffix;
    private String fromid;
    private String seqno;
    private Integer cached;
    private String cachedfile;
    private Integer inlocal;
    private String ftype;
    private Integer sortno;
    private Date ctime;
    private Long lmtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPeername() {
        return peername;
    }

    public void setPeername(String peername) {
        this.peername = peername;
    }

    public String getPeerhash() {
        return peerhash;
    }

    public void setPeerhash(String peerhash) {
        this.peerhash = peerhash;
    }

    public String getPrehash() {
        return prehash;
    }

    public void setPrehash(String prehash) {
        this.prehash = prehash;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Long getFsize() {
        return fsize;
    }

    public void setFsize(Long fsize) {
        this.fsize = fsize;
    }

    public String getFsuffix() {
        return fsuffix;
    }

    public void setFsuffix(String fsuffix) {
        this.fsuffix = fsuffix;
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    public String getSeqno() {
        return seqno;
    }

    public void setSeqno(String seqno) {
        this.seqno = seqno;
    }

    public Integer getCached() {
        return cached;
    }

    public void setCached(Integer cached) {
        this.cached = cached;
    }

    public String getCachedfile() {
        return cachedfile;
    }

    public void setCachedfile(String cachedfile) {
        this.cachedfile = cachedfile;
    }

    public Integer getInlocal() {
        return inlocal;
    }

    public void setInlocal(Integer inlocal) {
        this.inlocal = inlocal;
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Long getLmtime() {
        return lmtime;
    }

    public void setLmtime(Long lmtime) {
        this.lmtime = lmtime;
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }
}
