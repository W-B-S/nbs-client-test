package io.nbs.sdk.beans;

/**
 * @Package : io.nbs.sdk.beans
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/2-14:54
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ControlMessage<D> {
    private String id;
    private D data;

    public ControlMessage(String id, D data) {
        this.id = id;
        this.data = data;
        this.ts = System.currentTimeMillis();
    }

    public ControlMessage(String id) {
        this.id = id;
    }

    private Long ts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }
}
