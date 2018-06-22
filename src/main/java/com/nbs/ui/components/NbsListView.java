package com.nbs.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 * @Package : com.nbs.ui.components
 * @Description :
 * <p>RCListView</p>
 * @Author : lambor.c
 * @Date : 2018/6/22-10:41
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class NbsListView extends JScrollPane {

    private JPanel contactsPanel;

    private int vGap;
    private int hGap;

    boolean scrollToBottom = false;
    //自适应
    private AdjustmentListener adjustmentListener;
    /**
     * 滚动条
     */
    private ScrollUI scrolUI;
    /**
     * 顶部监听事件
     */
    private Scroll2TopListener scroll2TopListener;
    private boolean scrollBarPressed = false;
    private int lastScrollValue = -1;

    public NbsListView() {
        this(0,0);
    }

    public NbsListView(int vGap, int hGap) {
        this.vGap = vGap;
        this.hGap = hGap;
    }

    /**
     * 滚动到top
     *
     */
    public interface Scroll2TopListener{
        void onScroll2Top();
    }

    /**
     * 鼠标离开
     * @param jComponent
     */
    public void setScrollHiddenOnMouseLeave(JComponent jComponent){
        //TODO
    }

    /**
     * 设置滚动条的颜色 TODO
     * @param thumbColor
     * @param trackColor
     */
    public void setScrollBarColor(Color thumbColor,Color trackColor){
        if(scrolUI == null){
            scrolUI = new ScrollUI(thumbColor,trackColor);
            this.getVerticalScrollBar().setUI(scrolUI);
        }else {
            scrolUI.setThumbColor(thumbColor);
            scrolUI.setTrackColor(trackColor);
        }
    }

    private void initComponent(){
        contactsPanel = new JPanel();
        contactsPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,hGap,vGap,true,true));
        contactsPanel.setBackground(ColorCnst.WINDOW_BACKGROUND);

        this.setViewportView(contactsPanel);
        this.setBorder(null);
        this.getVerticalScrollBar().setUnitIncrement(25);
        this.getVerticalScrollBar().setUI(new ScrollUI());
    }

    /**
     * 设置监听
     */
    private void setListeners(){
        adjustmentListener = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                //TODO
            }
        };
    }

    public void notifyDataSetChanged(boolean needRedraw){
        if(needRedraw){
            //TODO
        }
    }
}
