package io.nbs.sdk.beans;

/**
 * @Package : io.nbs.sdk.beans
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/9-18:33
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class TaobaoResult {
    private String code;
    private TaoBaoAddressData data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TaoBaoAddressData getData() {
        return data;
    }

    public void setData(TaoBaoAddressData data) {
        this.data = data;
    }
}
