package io.nbs.client.ui.panels.manage;

import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.client.ui.panels.manage.body.MMBodyLeftCardPanel;
import io.nbs.client.ui.panels.manage.body.MMDataListPanel;
import io.nbs.client.ui.panels.manage.body.MMRightPanel;
import io.nbs.client.ui.panels.manage.body.MMSearchResultPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.panels.manage
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/8-20:52
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MMBodyPanel extends ParentAvailablePanel {
    private static MMBodyPanel context;
    private MMRightPanel rightPanel;
    private MMBodyLeftCardPanel cardPanel;

    private CardLayout cardLayout;
    private MMDataListPanel dataListPanel;
    private MMSearchResultPanel searchResultPanel;


    /**
     * construction
     */
    public MMBodyPanel(JPanel parent) {
        super(parent);
        initComponents();
        initView();
        setListeners();
    }

    /**
     * [initComponents description]
     *
     * @return {[type]} [description]
     */
    private void initComponents() {
        cardPanel = new MMBodyLeftCardPanel(this);
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        //文件列表
        dataListPanel = new MMDataListPanel();
        searchResultPanel = new MMSearchResultPanel(this);


        rightPanel = new MMRightPanel(this);
    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        setLayout(new BorderLayout());
        /* ===================================================================== */

        cardPanel.add(dataListPanel,MMNames.LISTF.name());
        cardPanel.add(searchResultPanel,MMNames.SEARCHE.name());

        rightPanel.setPreferredSize(new Dimension(310,MainFrame.getContext().currentWindowHeight));

        /* ===================================================================== */
        add(cardPanel,BorderLayout.CENTER);
        add(rightPanel,BorderLayout.EAST);
    }

    private void setListeners() {

    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static MMBodyPanel getContext() {
        return context;
    }

    public static enum MMNames{
        LISTF,SEARCHE;
    }
}