package UI.panel;

import UI.ConstantsUI;

import javax.swing.*;

/**
 * @Package : UI.panel
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/15-17:11
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ContentJLabel extends JLabel {

    /**
     *
     * @param text
     */
    public ContentJLabel(String text) {
        super(text);
        this.setFont(ConstantsUI.FONT_NORMAL);
        this.setForeground(ConstantsUI.NORMAL_FONT_COLOR);
    }

    /**
     *
     */
    public ContentJLabel() {
        this.setFont(ConstantsUI.FONT_NORMAL);
        this.setForeground(ConstantsUI.NORMAL_FONT_COLOR);
    }
}
