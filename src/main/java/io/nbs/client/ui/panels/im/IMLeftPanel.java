package io.nbs.client.ui.panels.im;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.ui.panels.ParentAvailablePanel;

import javax.swing.*;
import java.awt.*;

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

    /**
     *
     */
    private SearchePanel searchePanel;
    /**
     * 列表展示区
     */
    private IMListPanel listPanel;

    public IMLeftPanel(JPanel parent) {
        super(parent);
        initComponents();
        initView();

        setListeners();
    }

    private void initComponents(){

        searchePanel = new SearchePanel(this);
        listPanel = new IMListPanel(this);
        //listPanel.setBackground(ColorCnst.CONTACTS_ITEM_GRAY_MAIN);
        //searchePanel.setBorder(MainFrame.buleBorder);

    }

    private void initView(){
        setBackground(ColorCnst.CONTACTS_ITEM_GRAY);
        setLayout(new BorderLayout());


        add(searchePanel,BorderLayout.NORTH);
               // new GBC(0,0).setFill(GBC.BOTH).setWeight(1,1).setAnchor(GBC.CENTER));
        add(listPanel,BorderLayout.CENTER);
               // new GBC(0,0).setFill(GBC.BOTH).setWeight(1,67).setAnchor(GBC.CENTER));
    }

    private void setListeners(){

    }
}
