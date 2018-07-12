package io.nbs.client.ui.panels.manage;

import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.commons.utils.DataSizeFormatUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @Package : io.nbs.client.ui.panels.manage
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-15:32
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MMCubePanel extends JPanel {
    private MMCubePanel context;
    private static int whSize = 15;
    private String hash;
    private long dataSize = 0;
    private boolean pined = false;

    /**
     * construction
     */
    public MMCubePanel(String hash,Long size) {
        context =this;
        init(hash,size,true);
    }

    public MMCubePanel(String hash,Long size,boolean pined) {
        init(hash,size,pined);
    }

    private void init(String hash,Long size,boolean pined){
        this.hash = hash;
        this.pined = pined;
        this.dataSize = size;
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
        Dimension cubeSize = new Dimension(whSize,whSize);
        setPreferredSize(cubeSize);
        setMaximumSize(cubeSize);
        setMinimumSize(cubeSize);
        setBorder(BorderFactory.createLineBorder(new Color(240,255,255),1));
    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        setToolTipText(this.hash+"["+this.dataSize+"]");
    }

    private void setListeners() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        });
    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public MMCubePanel getContext() {
        return context;
    }

    @Override
    protected void paintComponent(Graphics g) {

        if(!isOpaque())return;

        int w = getWidth();
        int h = getHeight();
        Graphics2D g2 = (Graphics2D)g;
        GradientPaint gp ;
        if(pined){
            gp = new GradientPaint(0,0,new Color(151,251,152),w,h,new Color(144,238,144));
        }else {
            gp = new GradientPaint(0,0,new Color(230,230,250),w,h,new Color(248,248,255));
        }
        g2.setPaint(gp);
        g2.fillRect(0,0,w,h);
    }

    public String getHash() {
        return hash;
    }

    public long getDataSize() {
        return dataSize;
    }

    public boolean isPined() {
        return pined;
    }

    public void setPined(boolean pined) {
        this.pined = pined;
    }
}