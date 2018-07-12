package io.nbs.client.ui.panels.manage.body;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.ui.components.LCJlabel;
import io.nbs.client.ui.components.NBSButton;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * @Package : io.nbs.client.ui.panels.manage.body
 * @Description :
 * <p>
 *     不在本地的hash
 * </p>
 * @Author : lambor.c
 * @Date : 2018/7/13-2:26
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class OutResultHashPanel extends ParentAvailablePanel {
    private static OutResultHashPanel context;
    private static String PRE_NUMLINKS ="NumLinks";
    private static String PRE_BLKSIZE ="BlockSize";
    private static String PRE_LSIZE ="LinksSize";
    private static String PRE_DSIZE ="DataSize";
    private static String PRE_SUMSIZE ="CumulativeSize";

    private JPanel contentPanel = new JPanel();

    private LCJlabel hashLabel;
    private LCJlabel errorMsg;
    private JLabel loading;

    private LCJlabel numLinks;
    private LCJlabel blockSize;
    private LCJlabel linksSize;
    private LCJlabel dataSize;
    private LCJlabel cumulativeSize;

    private NBSButton addBtn;
    /**
     * construction
     */
    public OutResultHashPanel(JPanel parent) {
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
        hashLabel = new LCJlabel();
        hashLabel.setHorizontalAlignment(JLabel.CENTER);

        errorMsg = new LCJlabel(16);
        errorMsg.setHorizontalAlignment(JLabel.CENTER);
       // errorMsg.setFont(FontUtil.getDefaultFont(16));
        errorMsg.setForeground(ColorCnst.RED);
        errorMsg.setVisible(false);

        loading = new JLabel();
        loading.setHorizontalAlignment(JLabel.CENTER);
        loading.setVisible(false);

        numLinks = new LCJlabel(14);
        blockSize = new LCJlabel(14);
        linksSize = new LCJlabel(14);
        dataSize = new LCJlabel(14);
        cumulativeSize = new LCJlabel(14);
    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        contentPanel.setLayout(new MigLayout("","[left]"));
        contentPanel.add(hashLabel,"span,growx ,gaptop 10,wrap");
        contentPanel.add(errorMsg,"span,growx,gaptop 10,wrap");


    }

    private void setListeners() {

    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static OutResultHashPanel getContext() {
        return context;
    }


}