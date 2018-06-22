package com.nbs.ui.panels;

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
    /**
     *
     */
    private static ContactsPanel context;
    /**
     * 在线peer
     */
    List<String> peerItems = new ArrayList<>();
    /**
     *
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

    private void initComponent(){

    }
}
