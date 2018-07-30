package io.nbs.client.ui.panels;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    protected static Logger logger = LoggerFactory.getLogger(ParentAvailablePanel.class);
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
