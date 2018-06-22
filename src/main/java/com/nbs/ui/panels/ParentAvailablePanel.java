package com.nbs.ui.panels;

import javax.swing.*;

/**
 * @Package : com.nbs.ui.panels
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/22-10:29
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ParentAvailablePanel extends JPanel {

    /**
     *
     */
    private JPanel parent;

    /**
     *
     * @param parent
     */
    public ParentAvailablePanel(JPanel parent) {
        this.parent = parent;
    }

    /**
     *
     * @return
     */
    public JPanel getParentPanel() {
        return parent;
    }
}
