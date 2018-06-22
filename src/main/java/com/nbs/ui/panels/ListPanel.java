package com.nbs.ui.panels;

import javax.swing.*;

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

   // private


    /**
     * @param parent
     */
    public ListPanel(JPanel parent) {
        super(parent);
        context = this;

    }
}
