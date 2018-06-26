package com.nbs.ui.components.messages;

import com.nbs.ui.components.NBS9PathImageIcon;
import com.nbs.ui.components.NBSAttachmentMessageBubble;

import java.awt.*;

/**
 * @Package : com.nbs.ui.components.messages
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/27-3:34
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class NBSLeftImageMessageBubble extends NBSAttachmentMessageBubble {

    public NBSLeftImageMessageBubble() {
        NBS9PathImageIcon normal = new NBS9PathImageIcon(this.getClass().getResource("/icon/nbs64.png"));
        NBS9PathImageIcon active = new NBS9PathImageIcon(this.getClass().getResource("/icon/nbs64.png"));
        setBackgroundIcon(normal);
        setNormal(normal);
        setActive(active);
    }

    @Override
    public Insets getInsets() {
        return new Insets(2,9,3,2);
    }
}
