package io.ipfs.nbs.ui.panels;

import io.ipfs.nbs.ui.frames.MainFrame;
import io.ipfs.nbs.ui.panels.im.IMMasterPanel;
import io.ipfs.nbs.ui.panels.info.InfoMasterPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.ipfs.nbs.ui.panels
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-19:07
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MainContentPanel extends JPanel {
    private static MainContentPanel context;

    public MainContentPanel() {
        initComponents();
        initView();
    }

    private void initComponents(){

    }

    private void initView(){

    }

    /**
     *
     * @return
     */
    public static MainContentPanel getContext() {
        return context;
    }




}
