package io.nbs.client.ui.panels.im;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.commons.helper.ConfigurationHelper;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.client.ui.panels.TitlePanel;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.ipfs.nbs.ui.panels.im
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-18:58
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IMRightPanel extends ParentAvailablePanel {

    private static IMRightPanel context;
    /**
     * win 窗口控制
     */
    public static TitlePanel winTitlePanel;
    public IMRightPanel(JPanel parent) {
        super(parent);
        context = this;
        initComponents();
        initView();
    }

    private void initComponents(){
        winTitlePanel = new TitlePanel(this);
        winTitlePanel.setTitle(ConfigurationHelper.getInstance().getI18nProperty("nbs.ui.panel.im.label","NBS World IM"));


    }

    private void initView(){
        setLayout(new BorderLayout());
        setBackground(ColorCnst.WINDOW_BACKGROUND);
        //setBorder(MainFrame.redBorder);

        add(winTitlePanel,BorderLayout.NORTH);
    }

    public static IMRightPanel getContext() {
        return context;
    }
}
