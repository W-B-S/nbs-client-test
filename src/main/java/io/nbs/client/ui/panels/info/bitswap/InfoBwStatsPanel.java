package io.nbs.client.ui.panels.info.bitswap;

import io.ipfs.api.beans.bw.BwStats;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.ui.components.GBC;
import io.nbs.client.ui.components.LCFromLabel;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.commons.utils.DataSizeFormatUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.panels.info.bitswap
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-0:33
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class InfoBwStatsPanel extends ParentAvailablePanel {
    private static InfoBwStatsPanel context;
    private JLabel titleLabel;
    private LCFromLabel shareTotalLabel;
    private LCFromLabel shareTotalVol;
    private LCFromLabel getTotalLabel;
    private LCFromLabel getTotalVol;
    private LCFromLabel rateInLabel;
    private LCFromLabel rateInVol;
    private LCFromLabel rateOutLabel;
    private LCFromLabel rateOutVol;
    private JPanel centPanel;

    /**
     * construction
     */
    public InfoBwStatsPanel(JPanel parent) {
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
        centPanel = new JPanel();
        titleLabel = new JLabel("Bitswap 交换数据量");
        titleLabel.setOpaque(false);
        titleLabel.setFont(FontUtil.getDefaultFont(15));
        titleLabel.setForeground(ColorCnst.FONT_ABOUT_TITLE_BLUE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        shareTotalLabel = new LCFromLabel("分享数据量:");
        shareTotalVol = new LCFromLabel(false);

        getTotalLabel = new LCFromLabel("获取数据量:");
        getTotalVol = new LCFromLabel(false);

        rateInLabel = new LCFromLabel("接收速率:");
        rateInVol = new LCFromLabel(false);
        rateOutLabel = new LCFromLabel("发送速率:");
        rateOutVol = new LCFromLabel(false);
    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        setLayout(new BorderLayout());
        centPanel.setLayout(new GridBagLayout());
        centPanel.setOpaque(false);

        add(titleLabel,BorderLayout.NORTH);
        centPanel.add(shareTotalLabel
                ,new GBC(0,0).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(shareTotalVol
                ,new GBC(1,0).setWeight(4,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(getTotalLabel
                ,new GBC(0,1).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(getTotalVol
                ,new GBC(1,1).setWeight(4,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(rateInLabel
                ,new GBC(0,2).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(rateInVol
                ,new GBC(1,2).setWeight(4,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(rateOutLabel
                ,new GBC(0,3).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(rateOutVol
                ,new GBC(1,3).setWeight(4,1).setFill(GBC.BOTH).setInsets(5,0,0,10));

        add(centPanel,BorderLayout.CENTER);
    }

    private void setListeners() {

    }

    public void setVolume(BwStats bwStats){
        if(bwStats==null)return;
        shareTotalVol.setText(DataSizeFormatUtil.formatDataSize(bwStats.getTotalOut()));
        getTotalVol.setText(DataSizeFormatUtil.formatDataSize(bwStats.getTotalIn()));

        rateInVol.setText(DataSizeFormatUtil.formatIntelnetRate(bwStats.getRateIn()));
        rateOutVol.setText(DataSizeFormatUtil.formatIntelnetRate(bwStats.getRateOut()));
    }

    public void refreshVolume(BwStats bwStats){
        setVolume(bwStats);
        updateUI();
    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static InfoBwStatsPanel getContext() {
        return context;
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.printComponent(g);
        if(!isOpaque())return;

        int w = getWidth();
        int h = getHeight();
        Graphics2D g2 = (Graphics2D)g;
        GradientPaint gp = new GradientPaint(0,0,new Color(129,194,214),w,h,new Color(131,252,216));
        g2.setPaint(gp);
        g2.fillRect(0,0,w,h);
    }
}