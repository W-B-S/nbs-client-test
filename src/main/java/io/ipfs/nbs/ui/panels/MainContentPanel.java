package io.ipfs.nbs.ui.panels;

import io.ipfs.nbs.ui.panels.im.IMMasterPanel;
import io.ipfs.nbs.ui.panels.info.InfoMasterPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.ipfs.nbs.ui.panels
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-19:07
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MainContentPanel extends JPanel {
    private static MainContentPanel context;

    /**
     * win 窗口控制
     */
    public static TitlePanel winTitlePanel;
    /**
     * PEER INFO
     */
    private InfoMasterPanel infoMasterPanel;
    /**
     * 聊天
     */
    private IMMasterPanel imMasterPanel;



    public MainContentPanel() {
        /**
         * win ctrl
         */
        winTitlePanel = new TitlePanel();
        initComponents();

        initView();
    }

    private void initComponents(){

        infoMasterPanel = new InfoMasterPanel(winTitlePanel);

        imMasterPanel = new IMMasterPanel(winTitlePanel);


    }

    private void initView(){
        add(imMasterPanel);
    }

    /**
     *
     * @return
     */
    public static MainContentPanel getContext() {
        return context;
    }

    /**
     *
     * @param types
     */
    public void setPanelShow(MasterTypes types){

    }

    public static enum MasterTypes{
        INFO,IM,DATDA,MUSIC,ABOUT;
    }
}
