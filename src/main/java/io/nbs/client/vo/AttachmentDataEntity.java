package io.nbs.client.vo;

import com.nbs.biz.data.entity.AttachmentInfoEntity;

/**
 * @Package : io.nbs.client.vo
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-17:37
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AttachmentDataEntity extends AttachmentInfoEntity {
    /**
     * 0  不存在
     * 1 : blk
     * 2 : local file
     */
    private int dataType = 0;
    private Long ts;


    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        if(ts==null)ts = System.currentTimeMillis();
        this.ts = ts;
    }
}
