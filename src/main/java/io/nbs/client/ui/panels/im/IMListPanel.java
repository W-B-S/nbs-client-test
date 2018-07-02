package io.nbs.client.ui.panels.im;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.ui.panels.ParentAvailablePanel;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.ipfs.nbs.ui.panels.im
 * @Description :
 * <p>
 *     列表Card
 * </p>
 * @Author : lambor.c
 * @Date : 2018/7/1-22:04
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IMListPanel extends ParentAvailablePanel {
    private static IMListPanel context;

    /**
     * 在线节点列表
     */
    private IMPeersPanel peersPanel;
    /**
     * 搜索结果列表
     */
    private IMSearchResultPanel resultPanel;
    /**
     * 提示
     */
    private JLabel tipLabel;

    public static final String PEERS_ONLINE = "CONTACTS";

    public static final String SEARCH = "SEARCH";

    private String previousTab = PEERS_ONLINE;
    private String currentTab = PEERS_ONLINE;

    private CardLayout cardLayout = new CardLayout();

    /**
     * construction
     */
    public IMListPanel(JPanel parent) {
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
        peersPanel = new IMPeersPanel(this);
        peersPanel.setBackground(ColorCnst.CONTACTS_ITEM_GRAY_MAIN);
        resultPanel = new IMSearchResultPanel(this);
        resultPanel.setBackground(ColorCnst.CONTACTS_ITEM_GRAY_MAIN);
    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        this.setLayout(cardLayout);
        //peersPanel.setBorder(MainFrame.buleBorder);
        add(peersPanel,PEERS_ONLINE);
        add(resultPanel,SEARCH);
    }

    private void setListeners() {

    }

    /**
     * 设置显示
     * @param showKey
     */
    public void showPanel(String showKey){
        previousTab = currentTab;
        if(!showKey.equals(SEARCH)){
            currentTab = showKey;
        }
        cardLayout.show(this,showKey);
    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static IMListPanel getContext() {
        return context;
    }
}