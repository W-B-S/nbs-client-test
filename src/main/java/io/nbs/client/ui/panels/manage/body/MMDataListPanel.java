package io.nbs.client.ui.panels.manage.body;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.ui.components.NbsListView;
import io.nbs.client.ui.panels.ParentAvailablePanel;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.panels.manage.body
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-16:38
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MMDataListPanel extends JPanel {
    private static MMDataListPanel context;
    /**
     *
     */
    NbsListView listView;

    /**
     * construction
     */
    public MMDataListPanel() {

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
        listView = new NbsListView(0,15);
        listView.setScrollBarColor(ColorCnst.SCROLL_BAR_TRACK_LIGHT,ColorCnst.SCROLL_BAR_TTRACK);
        listView.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listView.setScrollHiddenOnMouseLeave(listView);
    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        this.setLayout(new BorderLayout());
        this.add(listView,BorderLayout.CENTER);
    }

    private void setListeners() {

    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static MMDataListPanel getContext() {
        return context;
    }

    public NbsListView getListView() {
        return listView;
    }
}