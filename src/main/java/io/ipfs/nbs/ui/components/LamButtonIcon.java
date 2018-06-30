package io.ipfs.nbs.ui.components;

import io.ipfs.nbs.cnsts.AppGlobalCnst;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.ipfs.nbs.ui.components
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-20:24
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class LamButtonIcon extends JLabel {
    private ImageIcon normal;
    private ImageIcon active;
    private String tip;


    /**
     *
     * @param normal
     * @param active
     * @param tip
     */
    public LamButtonIcon(ImageIcon normal, ImageIcon active, String tip) {
       this.initLamButtonIcon(normal,active,tip);
    }

    /**
     *
     * @param normal
     * @param tip
     */
    public LamButtonIcon(ImageIcon normal,String tip) {
        this.initLamButtonIcon(normal,normal,tip);
    }

    /**
     *
     * @param normal
     * @param active
     */
    public LamButtonIcon(ImageIcon normal, ImageIcon active) {
        this.initLamButtonIcon(normal,normal,"");
    }

    /**
     *
     */
    private void initComponents(){
        setToolTipText(this.tip);
        setPreferredSize(new Dimension(48,48));
        setIcon(active);
        setNormal(active);
        setHorizontalAlignment(JLabel.CENTER);
        setVisible(true);
    }

    /**
     *
     * @param normal
     * @param active
     * @param tip
     */
    public LamButtonIcon(String normal, String active,String tip) {
        ImageIcon norIcon = new ImageIcon(AppGlobalCnst.TOOL_ICON_PATH+normal);
        ImageIcon axtIcon =  new ImageIcon(AppGlobalCnst.TOOL_ICON_PATH+active);
        this.initLamButtonIcon(norIcon,axtIcon,tip);
    }

    /**
     *
     * @param normal
     * @param active
     */
    public LamButtonIcon(String normal, String active) {
        ImageIcon norIcon = new ImageIcon(AppGlobalCnst.TOOL_ICON_PATH+normal);
        ImageIcon axtIcon =  new ImageIcon(AppGlobalCnst.TOOL_ICON_PATH+active);
        this.initLamButtonIcon(norIcon,axtIcon,"");
    }

    private void initLamButtonIcon(ImageIcon normal, ImageIcon active, String tip){
        this.normal = normal;
        this.active = active;
        this.tip = tip;
        initComponents();
    }

    public ImageIcon getNormal() {
        return normal;
    }

    public void setNormal(ImageIcon normal) {
        this.normal = normal;
    }

    public ImageIcon getActive() {
        return active;
    }

    public void setActive(ImageIcon active) {
        this.active = active;
    }


}
