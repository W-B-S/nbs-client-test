package io.nbs.sdk.beans;

/**
 * @Package : io.nbs.sdk.beans
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/3-13:55
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IMMessageBean extends MessageBaseBean<String> {
    /**
     * 加密后的
     */
    private String encodeText;

    public IMMessageBean(String from, String content, String encodeText) {
        super(from, content);
        this.encodeText = encodeText;
    }
    public IMMessageBean(String from, String content) {
        super(from,content);
    }

    public IMMessageBean(String encodeText) {
        super();
        this.encodeText = encodeText;
    }
    public IMMessageBean() {
        super();
    }

    public String getEncodeText() {
        return encodeText;
    }

    public void setEncodeText(String encodeText) {
        this.encodeText = encodeText;
    }
}
