package io.nbs.client.ui.panels.manage;

import io.nbs.client.ui.components.GBC;
import io.nbs.client.ui.components.NBSIconButton;
import io.nbs.client.ui.components.SearchTextField;
import io.nbs.client.ui.panels.ParentAvailablePanel;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.panels.manage
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/8-20:18
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MMHeaderPanel extends ParentAvailablePanel {
    private static MMHeaderPanel context;

    private MMSearcherPanel searcherPanel;
    private JPanel prevousPanel;
    private JPanel suffixPanel;

    /**
     * construction
     */
    public MMHeaderPanel(JPanel parent) {
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
        prevousPanel = new JPanel();
        suffixPanel = new JPanel();
        searcherPanel = new MMSearcherPanel(this);
    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        add(searcherPanel);
    }

    private void setListeners() {

    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static MMHeaderPanel getContext() {
        return context;
    }
}