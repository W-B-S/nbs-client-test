package com.nbs.ui.panels;

import com.nbs.entity.ContactsItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package : com.nbs.ui.panels
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/22-10:35
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ContactsPanel extends ParentAvailablePanel {
    private Logger logger = LoggerFactory.getLogger(ContactsPanel.class);

    /**
     *
     */
    private static ContactsPanel context;
    /**
     * 在线peer缓存
     */
    List<ContactsItem> peerItems = new ArrayList<>();
    /**
     * 当前聊天用户
     */
    private String currentName;

    /**
     *
     * @param parent
     */
    public ContactsPanel(JPanel parent) {
        super(parent);
        context = this;

    }

    /**
     * 初始化组件
     */
    private void initComponent(){

    }


}
