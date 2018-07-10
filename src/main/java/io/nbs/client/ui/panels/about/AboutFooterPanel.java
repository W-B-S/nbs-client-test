package io.nbs.client.ui.panels.about;

import io.nbs.client.ui.panels.ParentAvailablePanel;

import javax.swing.*;

/**
 * @Package : io.nbs.client.ui.panels.about
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/9-11:39
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AboutFooterPanel extends ParentAvailablePanel {
    private static AboutFooterPanel context;

    /**
     * construction
     */
    public AboutFooterPanel(JPanel parent) {
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
    public static AboutFooterPanel getContext() {
        return context;
    }
}