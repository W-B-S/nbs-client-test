package io.nbs.client.ui.components.message;

import com.nbs.ui.components.NBS9PathImageIcon;

import javax.swing.*;
import java.awt.event.MouseListener;

/**
 * @Package : com.nbs.ui.components
 * @Description :
 * <p>
 *     气泡效果事件
 * </p>
 * @Author : lambor.c
 * @Date : 2018/6/27-0:35
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public interface IMMessageBubble {
    void addMouseListener(MouseListener l);

    void setBackgroundIcon(Icon icon);

   NBS9PathImageIcon getNBS9PathImageIcon();
}
