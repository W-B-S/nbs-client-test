package io.nbs.client.ui.panels.manage;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.listener.AbstractMouseListener;
import io.nbs.client.ui.components.GBC;
import io.nbs.client.ui.components.NBSIconButton;
import io.nbs.client.ui.components.SearchButtonPanel;
import io.nbs.client.ui.components.SearchTextField;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.commons.utils.ButtonIconUtil;
import io.nbs.commons.utils.IconUtil;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @Package : io.nbs.client.ui.panels.manage
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/8-20:23
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MMSearcherPanel extends ParentAvailablePanel {
    private static MMSearcherPanel context;

    private SearchTextField searchTextField;
    private NBSIconButton searchBtn;

    private JPanel prevous;
    private JPanel tailer;

    private SearchButtonPanel buttonPanel;

    public MMSearcherPanel(JPanel parent ) {
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
        searchTextField = new SearchTextField();
        searchBtn = ButtonIconUtil.searchBtn;
        prevous = new JPanel();
        tailer = new JPanel();

        buttonPanel = new SearchButtonPanel("Search");


        searchTextField.setFont(FontUtil.getDefaultFont(14));
        //searchTextField.setBorder(MainFrame.redBorder);
        searchTextField.setForeground(ColorCnst.DARK);
    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        setBackground(ColorCnst.SEEARCH_ITEM_GRAY_LIGHT);
        prevous.setBackground(ColorCnst.SEEARCH_ITEM_GRAY_LIGHT);
        tailer.setBackground(ColorCnst.SEEARCH_ITEM_GRAY_LIGHT);
        setLayout(new GridBagLayout());
        //setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
        //add(this.searchTextField);
        //add(this.searchBtn);
        this.add(prevous,
                new GBC(0,0).setFill(GBC.BOTH).setWeight(1,1).setInsets(15));
        this.add(searchTextField,
                new GBC(1,0).setFill(GBC.BOTH).setWeight(18,1).setInsets(15,0,15,0));
        this.add(buttonPanel,
                new GBC(2,0).setFill(GBC.BOTH).setWeight(3,1).setInsets(15,0,15,0));
        this.add(tailer,
                new GBC(3,0).setFill(GBC.BOTH).setWeight(1,1).setInsets(15));
    }

    private void setListeners() {
        buttonPanel.addMouseListener(new AbstractMouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                searchHash(searchTextField.getText());
                //super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                buttonPanel.setCursor(MainFrame.handCursor);
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        });

        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    searchHash(searchTextField.getText());
                }
            }
        });
    }

    /**
     *
     * @param text
     */
    private void searchHash(String text){
        if(StringUtils.isBlank(text))return;
        String searchStr = text.trim();

    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static MMSearcherPanel getContext() {
        return context;
    }
}