package io.nbs.client.ui.panels.media;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.ui.components.LCJlabel;
import io.nbs.client.ui.panels.TitlePanel;
import io.nbs.commons.helper.ConfigurationHelper;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.panels.media
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/13-14:53
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MediaMasterPanel extends JPanel {
    private static MediaMasterPanel context;
    private TitlePanel winTitlePanel;

    private JPanel centerPanel;

    /**
     * construction
     */
    public MediaMasterPanel() {

        context = this;
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
        this.winTitlePanel = new TitlePanel(this);
        winTitlePanel.setTitle(ConfigurationHelper.getInstance().getI18nProperty("nbs.ui.panel.media.label","MultiMedia"));
        winTitlePanel.setBackground(ColorCnst.LIGHT_GRAY);
        centerPanel = new JPanel();
    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        setLayout(new BorderLayout());
        /* ======================= 构造内部Start =====================*/
        centerPanel.setLayout(new BorderLayout());
        LCJlabel msgLabel = new LCJlabel("该功能尚未开放，尽请期待....",16);
        msgLabel.setForeground(ColorCnst.FONT_ABOUT_TITLE_BLUE);
        msgLabel.setHorizontalAlignment(JLabel.CENTER);

        centerPanel.add(msgLabel,BorderLayout.CENTER);

        /* ======================= 构造内部End =====================*/
        add(winTitlePanel,BorderLayout.NORTH);
        add(centerPanel,BorderLayout.CENTER);
    }

    private void setListeners() {

    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static MediaMasterPanel getContext() {
        return context;
    }
}