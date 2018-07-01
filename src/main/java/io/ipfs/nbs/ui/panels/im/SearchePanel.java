package io.ipfs.nbs.ui.panels.im;

import io.ipfs.nbs.cnsts.ColorCnst;
import io.ipfs.nbs.cnsts.FontUtil;
import io.ipfs.nbs.ui.components.GBC;
import io.ipfs.nbs.ui.components.SearchTextField;
import io.ipfs.nbs.ui.frames.MainFrame;
import io.ipfs.nbs.ui.panels.ParentAvailablePanel;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.ipfs.nbs.ui.panels.im
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/1-19:20
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class SearchePanel extends ParentAvailablePanel {
    private static SearchePanel context;

    private IMOperationPopupMenu operationPopupMenu;

    private SearchTextField searchTextField;
    /**
     * 操作图标
     */
    private JLabel menuIcon;

    /**
     * construction
     */
    public SearchePanel(JPanel parent) {
        super(parent);
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
        searchTextField = new SearchTextField();
        searchTextField.setFont(FontUtil.getDefaultFont(14));
        searchTextField.setForeground(ColorCnst.FONT_WHITE);

        menuIcon = new JLabel();
        menuIcon.setIcon(new ImageIcon(getClass().getResource("/icons/options.png")));
        menuIcon.setForeground(ColorCnst.FONT_WHITE);
        menuIcon.setCursor(MainFrame.handCursor);
        operationPopupMenu = new IMOperationPopupMenu();
    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        setBackground(ColorCnst.SEEARCH_ITEM_GRAY_LIGHT);

        this.setLayout(new GridBagLayout());
        add(searchTextField,
                new GBC(0,0).setFill(GBC.BOTH).setWeight(20,1).setInsets(10,2,10,5));
        add(menuIcon,
                new GBC(1,0).setFill(GBC.BOTH).setWeight(1,1).setInsets(3,5,10,0));
    }

    private void setListeners() {

    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static SearchePanel getContext() {
        return context;
    }
}