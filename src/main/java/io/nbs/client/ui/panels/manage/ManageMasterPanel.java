package io.nbs.client.ui.panels.manage;

import io.nbs.client.ui.components.GBC;
import io.nbs.client.ui.components.NBSIconButton;
import io.nbs.client.ui.components.SearchTextField;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.TitlePanel;
import io.nbs.commons.helper.ConfigurationHelper;
import io.nbs.commons.utils.ButtonIconUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.panels.manage
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/8-20:15
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ManageMasterPanel extends JPanel {

    private TitlePanel winTitlePanel;

    private JPanel centerPanel;
    private MMHeaderPanel headerPanel;
    private MMBodyPanel bodyPanel;


    private MMSearcherPanel searcherPanel;

    public ManageMasterPanel() {
        initComponents();
        initView();
        setListeners();
    }

    private void initComponents(){
        winTitlePanel = new TitlePanel(this);
        winTitlePanel.setTitle(ConfigurationHelper.getInstance().getI18nProperty("nbs.ui.panel.data.label","Data Manager"));
        centerPanel = new JPanel();
        headerPanel = new MMHeaderPanel(this);
        searcherPanel = new MMSearcherPanel(this);

        bodyPanel = new MMBodyPanel(this);

    }

    private void initView(){
        setLayout(new BorderLayout());
        centerPanel.setLayout(new BorderLayout());

        /* ======================= 构造内部Start =====================*/


        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(searcherPanel);
        //headerPanel.setBackground(null);

        centerPanel.add(headerPanel,BorderLayout.NORTH);
        centerPanel.add(bodyPanel,BorderLayout.CENTER);

        /* ======================= 构造内部End =====================*/
        add(winTitlePanel,BorderLayout.NORTH);
        add(centerPanel,BorderLayout.CENTER);
    }

    private void setListeners(){

    }
}
