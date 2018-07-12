package io.nbs.client.ui.panels.manage.body;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.ui.components.NbsListView;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.sdk.page.PageCondition;

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
public class MMSearchResultPanel extends JPanel {
    private static MMSearchResultPanel context;
    private PageCondition condition;

    NbsListView listView;

    /**
     * construction
     */
    public MMSearchResultPanel(PageCondition condition) {
        context =this;
        this.condition = condition;

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
    public static MMSearchResultPanel getContext() {
        return context;
    }

    public NbsListView getListView() {
        return listView;
    }
}