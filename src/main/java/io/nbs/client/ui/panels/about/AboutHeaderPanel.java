package io.nbs.client.ui.panels.about;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.ui.components.GBC;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.commons.utils.IconUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.panels.about
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/9-11:47
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AboutHeaderPanel extends ParentAvailablePanel {
    private static AboutHeaderPanel context;
    private JLabel titleLabel;
    private JLabel versionLabel;
    private JLabel logoLabel;
    private JLabel prevousLabel;

    /**
     * construction
     */
    public AboutHeaderPanel(JPanel parent) {
        super(parent);
        initComponents();
        initView();
        setListeners();
    }

    /**
     * [initComponents description]
     *
     * @return {[type]} [description]
     */
    private void initComponents() {
        prevousLabel = new JLabel();

        logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(JLabel.RIGHT);

        titleLabel = new JLabel();
        titleLabel.setHorizontalAlignment(JLabel.LEFT);

        versionLabel = new JLabel();
        versionLabel.setHorizontalAlignment(JLabel.RIGHT);

        setBackground(ColorCnst.WINDOW_BACKGROUND_LIGHT);
        prevousLabel.setBackground(ColorCnst.WINDOW_BACKGROUND_LIGHT);
        logoLabel.setBackground(ColorCnst.WINDOW_BACKGROUND_LIGHT);
        titleLabel.setBackground(ColorCnst.WINDOW_BACKGROUND_LIGHT);
        versionLabel.setBackground(ColorCnst.WINDOW_BACKGROUND_LIGHT);
    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        setLayout(new GridBagLayout());

        logoLabel.setIcon(IconUtil.getIcon(this,"/icons/nbs128.png",60,60));
        String title = AboutMasterPanel.cfgProps.getProperty("nbs.ui.panel.about.label.version-title","The Next Block chain System");
        titleLabel.setText(title);
        titleLabel.setFont(FontUtil.getDefaultFont(32));
        titleLabel.setForeground(ColorCnst.FONT_ABOUT_TITLE_BLUE);
        String verText =  AboutMasterPanel.cfgProps.getProperty("nbs.ui.panel.about.label.ver-value","v0.2.2");
        versionLabel.setText(verText);
        versionLabel.setFont(FontUtil.getDefaultFont(12));

        add(prevousLabel,
                new GBC(0,0).setWeight(1,3).setFill(GBC.BOTH).setInsets(5,0,5,0));
        add(logoLabel,
                new GBC(1,0).setWeight(2,3).setFill(GBC.BOTH).setInsets(5,0,5,0));
        add(titleLabel,
                new GBC(2,0).setWeight(4,3).setFill(GBC.BOTH).setInsets(5,10,5,0));
        add(versionLabel,
                new GBC(3,0).setWeight(2,1).setFill(GBC.HORIZONTAL).setInsets(0,0,20,20));

    }

    private void setListeners() {

    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static AboutHeaderPanel getContext() {
        return context;
    }
}