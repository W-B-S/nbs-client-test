package io.nbs.client.ui.panels.im;

import io.nbs.client.ui.panels.ParentAvailablePanel;

import javax.swing.*;

/**
 * @Package : io.ipfs.nbs.ui.panels.im
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/1-22:07
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IMSearchResultPanel extends ParentAvailablePanel {
    private static IMSearchResultPanel context;

    /**
     * construction
     */
    public IMSearchResultPanel(JPanel parent) {
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
    public static IMSearchResultPanel getContext() {
        return context;
    }
}