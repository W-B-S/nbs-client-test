package io.nbs.client.ui.components.message;

import com.nbs.ui.components.NBS9PathImageIcon;

import javax.swing.*;

/**
 * @Package : io.nbs.client.ui.components.message
 * @Description :
 * <p>
 *     消息气泡效果
 * </p>
 * @Author : lambor.c
 * @Date : 2018/7/2-21:18
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IMTextMessageBubble extends JTextArea implements IMMessageBubble {



    @Override
    public void setBackgroundIcon(Icon icon) {

    }

    @Override
    public NBS9PathImageIcon getNBS9PathImageIcon() {
        return null;
    }
}
