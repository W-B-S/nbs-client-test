package io.nbs.client.listener;

import io.nbs.sdk.beans.MessageItem;
import io.nbs.sdk.beans.OnlineMessage;
import io.nbs.sdk.beans.SystemCtrlMessageBean;

/**
 * @Package : io.nbs.client.listener
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/3-22:24
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public interface IPFSSubscribeListener {

    /**
     *
     * @param item
     */
    void notifyRecvMessage(MessageItem item);

    /**
     *
     * @param message
     */
    void notifyOnlineMessage(OnlineMessage message);
}
