package io.nbs.sdk.beans;

/**
 * @Package : io.nbs.sdk.beans
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/3-22:50
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class SystemCtrlMessageBean<D> extends MessageBaseBean<D> {
    public SystemCtrlMessageBean(D d) {
        super();
        super.setContent(d);
    }
}
