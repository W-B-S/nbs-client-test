package io.ipfs.api;

/**
 * @Package : io.ipfs.api
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/10-22:50
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ResData<D> {
    private int code;
    private String message;
    private D data;

    public ResData(D data) {
        code = 0;
        message = "success";
        this.data = data;
    }

    public ResData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResData(String message) {
        code =1;
        this.message = message;
    }

    /**
     * 0 success
     * @return
     */
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }
}
