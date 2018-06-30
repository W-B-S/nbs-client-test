package com.nbs.ui.panels;

import io.ipfs.nbs.ui.panels.ParentAvailablePanel;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : com.nbs.ui.panels
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/22-10:31
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ListPanel extends ParentAvailablePanel {

    private static ListPanel context;
    /**
     * 联系人
     */
    private ContactsPanel contactsPanel;
    public static final String CHAT = "CHAT";
    public static final String CONTACTS = "CONTACTS";
    public static final String COLLECTIONS = "COLLECTIONS";
    public static final String SEARCH = "SEARCH";

    private String previousTab = CHAT;
    private String currentTab = CHAT;

    private CardLayout cardLayout = new CardLayout();


    /**
     * @param parent
     */
    public ListPanel(JPanel parent) {
        super(parent);
        context = this;
        initComponents();
        initView();
    }

    /**
     * 初始化组件
     */
    private void initComponents(){
        contactsPanel = new ContactsPanel(this);
    }

    private void initView(){
        //this.setLayout(cardLayout);
        this.setLayout(new BorderLayout());
        add(contactsPanel);
    }

    public void showPanel(String who){
        cardLayout.show(this,CONTACTS);
    }

    /**
     *
     * @return
     */
    public static ListPanel getContext() {
        return context;
    }
}
