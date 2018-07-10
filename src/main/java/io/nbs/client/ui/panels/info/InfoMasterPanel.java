package io.nbs.client.ui.panels.info;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.commons.helper.ConfigurationHelper;
import io.nbs.client.ui.panels.TitlePanel;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.ipfs.nbs.ui.panels.info
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/1-0:54
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class InfoMasterPanel extends JPanel {
    private TitlePanel winTitlePanel;

    private JPanel centerPanel;
    private InfoHeaderPanel headerPanel;
    private InfoBodyPanel bodyPanel;
    private InfoFooterPanel footerPanel;

    public InfoMasterPanel() {
        initComponents();
        initView();
        setListeners();
        initData();

    }

    /**
     * 初始化控件
     */
    private void initComponents(){
        this.winTitlePanel = new TitlePanel(this);
        winTitlePanel.setTitle(ConfigurationHelper.getInstance().getI18nProperty("nbs.ui.panel.info.label","PEER INFO"));
        winTitlePanel.setBackground(ColorCnst.LIGHT_GRAY);
        /**
         *
         */
        centerPanel = new JPanel();
        //headerPanel = new InfoHeaderPanel(this);
        bodyPanel = new InfoBodyPanel(this);

        footerPanel = new InfoFooterPanel(this);

    }

    /**
     * 初始化布局和位置
     */
    private void initView(){
        setLayout(new BorderLayout());
        /* ======================= 构造内部Start =====================*/
        centerPanel.setLayout(new BorderLayout());
       // headerPanel.setBorder(MainFrame.redBorder);
        //bodyPanel.setBorder(MainFrame.buleBorder);

        //centerPanel.add(headerPanel,BorderLayout.NORTH);
        centerPanel.add(bodyPanel,BorderLayout.CENTER);
        centerPanel.add(footerPanel,BorderLayout.SOUTH);

        /* ======================= 构造内部End =====================*/
        add(winTitlePanel,BorderLayout.NORTH);
        add(centerPanel,BorderLayout.CENTER);

    }

    private void setListeners(){

    }

    private void initData(){

    }
}
