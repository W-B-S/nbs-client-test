package io.ipfs.nbs.ui.panels.im;

import io.ipfs.nbs.cnsts.ColorCnst;
import io.ipfs.nbs.ui.panels.ParentAvailablePanel;

import javax.swing.*;

/**
 * @Package : io.ipfs.nbs.ui.panels.im
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-18:58
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IMLeftPanel extends ParentAvailablePanel {

    /**
     * 联系人列表上部信息
     */
    private IMInfoPanel infoPanel;

    public IMLeftPanel(JPanel parent) {
        super(parent);

        initComponents();
        initView();
    }

    private void initComponents(){
        //setBorder(MainFrame.buleBorder);
    }

    private void initView(){
        setBackground(ColorCnst.CONTACTS_ITEM_GRAY);
    }
}
