package io.nbs.client.ui.panels.manage.body;

import io.nbs.client.ui.components.LCFromLabel;
import io.nbs.client.ui.components.NBSIconButton;
import io.nbs.client.ui.components.forms.LCJTextArea;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.client.ui.panels.manage.MMCubePanel;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.panels.manage
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-15:04
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MMRightPanel extends ParentAvailablePanel {
    private static MMRightPanel context;
    private static final int minWidth = 260;

    private LCFromLabel hashLabel;
    private LCJTextArea hashVolume;
    private LCFromLabel sizeLabel;
    private LCFromLabel sizeVolume;
    private LCFromLabel fileNameLabel;
    private LCJTextArea fileNameVol;
    private LCFromLabel blkSizeLabel;
    private LCFromLabel blkSizeVol;
    private LCFromLabel percentLabel;
    private LCFromLabel getPercentVol;
    private NBSIconButton pinBtn;
    private JPanel topPanel;
    private JPanel middlePanel;

    /**
     * construction
     */
    public MMRightPanel(JPanel parent) {
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
        topPanel = new JPanel();
        middlePanel = new JPanel();

    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        setLayout(new BorderLayout());



        middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        for(int i = 0;i<30;i++){
            boolean pined = i%2==1 ? true : false;
            String hash="Hash_"+i;
            long ds = i*1000l;
            MMCubePanel cubePanel = new MMCubePanel(hash,ds,pined);
            middlePanel.add(cubePanel);
        }

        add(topPanel,BorderLayout.NORTH);
        add(middlePanel,BorderLayout.CENTER);
    }

    private void setListeners() {

    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static MMRightPanel getContext() {
        return context;
    }
}