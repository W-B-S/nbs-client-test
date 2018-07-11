package io.nbs.client.ui.components.forms;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;

import javax.swing.*;

/**
 * @Package : io.nbs.client.ui.components.forms
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-15:21
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class LCJTextArea extends JTextArea {

    public LCJTextArea(String content) {
        setText(content);
        initComponents();
    }

    private void initComponents(){
        setLineWrap(true);
        setFont(FontUtil.getDefaultFont(13));
        setForeground(ColorCnst.ITEM_SELECTED);
    }
}
