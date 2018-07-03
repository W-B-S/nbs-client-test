package io.nbs.client.ui.panels.im.chatstmp;

import io.nbs.client.ui.panels.ParentAvailablePanel;

import javax.swing.*;

/**
 * @Package : io.nbs.client.ui.panels.im
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/3-15:11
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class TMChatPanel extends ParentAvailablePanel {

    private static TMChatPanel context;

    public TMChatPanel(JPanel parent) {
        super(parent);
        context = this;
    }


}
