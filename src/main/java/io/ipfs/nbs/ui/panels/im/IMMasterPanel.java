package io.ipfs.nbs.ui.panels.im;

import io.ipfs.nbs.ui.panels.TitlePanel;

import javax.swing.*;

/**
 * @Package : io.ipfs.nbs.ui.panels
 * @Description :
 * <p>
 *     聊天窗口
 * </p>
 * @Author : lambor.c
 * @Date : 2018/6/30-18:54
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IMMasterPanel extends JPanel {

    public static final int IM_LEFT_WIDTH = 280;
    JLabel testShowLanbel = new JLabel("聊天IMMasterPanel");

    private TitlePanel winTitlePanel;

    public IMMasterPanel(TitlePanel winTitlePanel) {
        this.winTitlePanel = winTitlePanel;

        initComponents();
    }

    private void initComponents(){
        add(testShowLanbel);
    }
}
