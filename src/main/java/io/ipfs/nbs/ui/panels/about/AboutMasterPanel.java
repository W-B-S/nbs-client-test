package io.ipfs.nbs.ui.panels.about;

import io.ipfs.nbs.ui.panels.TitlePanel;

import javax.swing.*;

/**
 * @Package : io.ipfs.nbs.ui.panels.about
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/1-8:49
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AboutMasterPanel extends JPanel {
    private TitlePanel winTitlePanel;
    public AboutMasterPanel() {
        this.winTitlePanel = new TitlePanel(this);
        initComponents();
        initView();
        setListeners();
    }

    private void initComponents(){
        JLabel testShowLabel = new JLabel("NBS LINFO");

        add(winTitlePanel);
        add(testShowLabel);
    }

    private void initView(){

    }

    private void setListeners(){

    }
}
