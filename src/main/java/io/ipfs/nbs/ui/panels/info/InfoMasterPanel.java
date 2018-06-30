package io.ipfs.nbs.ui.panels.info;

import io.ipfs.nbs.ui.panels.TitlePanel;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.ipfs.nbs.ui.panels.info
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/1-0:54
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class InfoMasterPanel extends JPanel {
    private TitlePanel winTitlePanel;
    public InfoMasterPanel(TitlePanel winTitlePanel) {
       this.winTitlePanel = winTitlePanel;

       initComponents();
    }

    private void initComponents(){

        add(winTitlePanel);
    }
}
