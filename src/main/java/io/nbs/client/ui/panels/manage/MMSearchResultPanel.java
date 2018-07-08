package io.nbs.client.ui.panels.manage;

import io.nbs.client.ui.panels.ParentAvailablePanel;

import javax.swing.*;

/**
 * @Package : io.nbs.client.ui.panels.manage
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/8-20:52
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MMSearchResultPanel extends ParentAvailablePanel {
    private static MMSearchResultPanel context;

    /**
     * construction
     */
    public MMSearchResultPanel(JPanel parent) {
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

    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {

    }

    private void setListeners() {

    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static MMSearchResultPanel getContext() {
        return context;
    }
}