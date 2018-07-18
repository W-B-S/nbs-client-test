package io.nbs.client.ui.panels.about;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.ui.components.SizeAutoAdjustTextArea;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.TitlePanel;

import io.nbs.commons.helper.ConfigurationHelper;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

/**
 * @Package : io.ipfs.nbs.ui.panels.about
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/1-8:49
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AboutMasterPanel extends JPanel {
    private TitlePanel winTitlePanel;

    private JPanel centerPanel;
    private AboutHeaderPanel headerPanel;
    private AboutBodyPanel bodyPanel;
    private AboutFooterPanel footerPanel;
    protected static Properties cfgProps;


    public AboutMasterPanel() {
        this.cfgProps = ConfigurationHelper.getInstance().getCfgProps();
        initComponents();
        initView();
        setListeners();
    }

    private void initComponents(){
        this.winTitlePanel = new TitlePanel(this);
        winTitlePanel.setTitle(ConfigurationHelper.getInstance().getI18nProperty("nbs.ui.panel.about.label","About NBS Chain"));

        centerPanel = new JPanel();
        headerPanel = new AboutHeaderPanel(this);
        bodyPanel = new AboutBodyPanel(this);
        footerPanel = new AboutFooterPanel(this);

       // headerPanel.setOpaque(false);
        headerPanel.setBackground(ColorCnst.WINDOW_BACKGROUND_LIGHT);
        bodyPanel.setBackground(ColorCnst.WINDOW_BACKGROUND_LIGHT);
        footerPanel.setBackground(ColorCnst.WINDOW_BACKGROUND_LIGHT);

       // headerPanel.setBorder(MainFrame.redBorder);

    }

    private void initView(){
        setLayout(new BorderLayout());
        /* ======================= 构造内部Start =====================*/
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(headerPanel,BorderLayout.NORTH);


        centerPanel.add(bodyPanel,BorderLayout.CENTER);
        centerPanel.add(footerPanel,BorderLayout.SOUTH);

        /* ======================= 构造内部End =====================*/
        add(winTitlePanel,BorderLayout.NORTH);
        add(centerPanel,BorderLayout.CENTER);
    }

    private void setListeners(){

    }
}
