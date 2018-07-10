package io.nbs.client.listener;

import io.nbs.sdk.beans.OnlineMessage;
import io.nbs.sdk.beans.SystemCtrlMessageBean;

/**
 * @Package : io.nbs.client.listener
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/4-2:22
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public interface OnlineNotifier {
    /**
     *
     * @param messageBean
     */
    void notifyRecvSystemMessage(SystemCtrlMessageBean messageBean);

    void notifyRecvystemMessage(SystemCtrlMessageBean<OnlineMessage> ctrlMessageBean);
}
