package io.nbs.client.ui.panels.manage.listener;

import io.nbs.client.vo.AttachmentDataDTO;

/**
 * @Package : io.nbs.client.ui.panels.manage.listener
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/13-1:20
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public interface FillDetailInfoListener {
    /**
     * 填充
     * @param dataDTO
     */
    void fillAttachmentDetailInfo(AttachmentDataDTO dataDTO);
}
