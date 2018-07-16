package io.nbs.client.ui.panels.about;

import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.commons.utils.IconUtil;

import javax.swing.*;


/**
 * @Package : io.nbs.client.ui.panels.about
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/9-11:38
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AboutBodyPanel extends ParentAvailablePanel {
    private static AboutBodyPanel context;

    private JLabel backgroundLabel;

    /**
     * construction
     */
    public AboutBodyPanel(JPanel parent) {
        super(parent);
        context =this;

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

        ImageIcon icon = IconUtil.getIcon(context,"/icons/about_bg.png");
        backgroundLabel = new JLabel();
        backgroundLabel.setIcon(icon);


    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        this.add(backgroundLabel);
    }

    private void setListeners() {

    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static AboutBodyPanel getContext() {
        return context;
    }
/*
    @Override
    protected void paintComponent(Graphics g) {

        g.drawImage(bgImage,this.getWidth(),this.getHeight(),null);
        Graphics2D g2 = (Graphics2D)g;
*//*        int width = getWidth();
        int height = getHeight();
        GradientPaint gradientPaint = new GradientPaint(0,0,new Color(37,32,72),width,height,new Color(59,54,98));
        g2.setPaint(gradientPaint);
        g2.fillRect(0,0,width,height);*//*
    }*/

}