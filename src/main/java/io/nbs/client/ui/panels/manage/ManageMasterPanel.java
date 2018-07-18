package io.nbs.client.ui.panels.manage;
import io.nbs.client.ui.panels.TitlePanel;
import io.nbs.commons.helper.ConfigurationHelper;

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
    private static ManageMasterPanel context;

    private TitlePanel winTitlePanel;

    private JPanel centerPanel;
    private MMHeaderPanel headerPanel;
    private MMBodyPanel bodyPanel;


    private MMSearcherPanel searcherPanel;

    public ManageMasterPanel() {
        context = this;
        initComponents();
        initView();
        setListeners();
    }

    private void initComponents(){
        winTitlePanel = new TitlePanel(this);
        winTitlePanel.setTitle(ConfigurationHelper.getInstance().getI18nProperty("nbs.ui.panel.data.label","Data Manager"));
        centerPanel = new JPanel();
        headerPanel = new MMHeaderPanel(this);
        bodyPanel = new MMBodyPanel(this);
        searcherPanel = new MMSearcherPanel(this);



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

    public static ManageMasterPanel getContext() {
        return context;
    }

    public MMBodyPanel getBodyPanel() {
        return bodyPanel;
    }

    public MMSearcherPanel getSearcherPanel() {
        return searcherPanel;
    }
}
