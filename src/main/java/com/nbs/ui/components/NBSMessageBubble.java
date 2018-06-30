package com.nbs.ui.components;

import javax.swing.*;
import java.awt.event.MouseListener;

/**
 * @Package : com.nbs.ui.components
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/27-0:35
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public interface NBSMessageBubble {
    void addMouseListener(MouseListener l);

    void setBackgroundIcon(Icon icon);

   NBS9PathImageIcon getNBS9PathImageIcon();
}
