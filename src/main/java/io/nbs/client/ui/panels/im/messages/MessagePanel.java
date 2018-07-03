package io.nbs.client.ui.panels.im.messages;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.ui.components.NbsListView;
import io.nbs.client.ui.panels.ParentAvailablePanel;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : com.nbs.ui.panels
 * @Description :
 * <p>右侧消息展示内容面板</p>
 * @Author : lambor.c
 * @Date : 2018/6/26-21:15
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MessagePanel extends ParentAvailablePanel {

    protected NbsListView listView;

    public MessagePanel(JPanel parent) {
        super(parent);

        initComponents();
        setListeners();
        initView();
    }

    private void initComponents(){
        listView = new NbsListView(0,15);
        listView.setScrollBarColor(ColorCnst.SCROLL_BAR_THUMB,ColorCnst.SCROLL_BAR_TRACK_LIGHT);
        listView.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listView.setScrollHiddenOnMouseLeave(listView);
    }

    private void setListeners(){

    }

    private void initView(){
        this.setLayout(new BorderLayout());
        add(listView,BorderLayout.CENTER);
    }

    public NbsListView getListView() {
        return listView;
    }

    public void setListView(NbsListView listView) {
        this.listView = listView;
    }
}
