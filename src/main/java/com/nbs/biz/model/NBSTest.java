package com.nbs.biz.model;

import java.util.Date;

/**
 * @Package : com.nbs.biz.model
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/23-22:04
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class NBSTest {
    private Integer id;
    private String name;
    private String ctime;
    private Date lmtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public Date getLmtime() {
        return lmtime;
    }

    public void setLmtime(Date lmtime) {
        this.lmtime = lmtime;
    }
}
