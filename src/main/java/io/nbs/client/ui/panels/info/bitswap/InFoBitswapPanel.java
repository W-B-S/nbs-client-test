package io.nbs.client.ui.panels.info.bitswap;

import io.ipfs.api.bitswap.BitSwap;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.ui.components.GBC;
import io.nbs.client.ui.components.LCFromLabel;
import io.nbs.client.ui.components.LCJlabel;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.commons.utils.DataSizeFormatUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.panels.info.bitswap
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/10-21:39
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class InFoBitswapPanel extends ParentAvailablePanel {
    private static InFoBitswapPanel context;

    private JLabel titleLabel;
    private LCFromLabel recBlockLabel;
    private LCFromLabel recBlockVol;
    private LCFromLabel recDataLabel;
    private LCFromLabel recDataVol;
    private LCFromLabel sendBlockLabel;
    private LCFromLabel sendBlockVol;
    private LCFromLabel sendDataLabel;
    private LCFromLabel sendDataVol;
    private LCFromLabel dupBlockLabel;
    private LCFromLabel dupBlockVol;
    private LCFromLabel dupDataLabel;
    private LCFromLabel dupDataVol;
    private LCFromLabel wantSizeLabel;
    private LCFromLabel wantSizeVolume;
    private LCFromLabel partnersLabel;
    private LCFromLabel partnersVolume;
    private JPanel centPanel;


    /**
     * construction
     */
    public InFoBitswapPanel(JPanel parent) {
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
        titleLabel = new JLabel("bitswap 统计");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(FontUtil.getDefaultFont(15));
        titleLabel.setForeground(ColorCnst.FONT_ABOUT_TITLE_BLUE);
        titleLabel.setOpaque(false);

        centPanel = new JPanel();

        recBlockLabel = new LCFromLabel("接收NBS数据块:");
        recDataLabel = new LCFromLabel("接收NBS数据量:");
        sendBlockLabel = new LCFromLabel("贡献NBS数据块:");
        sendDataLabel = new LCFromLabel("贡献NBS数据量:");

        dupBlockLabel = new LCFromLabel("重复数据块:");
        dupDataLabel = new LCFromLabel("重复数据量:");

        wantSizeLabel = new LCFromLabel("待接收NBS数据块:");
        partnersLabel = new LCFromLabel("已连接NBS节点数:");

        recBlockVol = new LCFromLabel(false);
        recDataVol = new LCFromLabel(false);
        sendBlockVol = new LCFromLabel(false);
        sendDataVol = new LCFromLabel(false);

        wantSizeVolume = new LCFromLabel(false);
        partnersVolume = new LCFromLabel(false);
    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        setLayout(new BorderLayout());
        centPanel.setOpaque(false);
        centPanel.setLayout(new GridBagLayout());
        add(titleLabel,BorderLayout.NORTH);
               // ,new GBC(0,0).setWeight(7,1).setFill(GBC.BOTH).setInsets(5,0,0,10));


        centPanel.add(recBlockLabel
             ,new GBC(0,0).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(recBlockVol
                ,new GBC(1,0).setWeight(2,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(recDataLabel
                ,new GBC(0,1).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(recDataVol
                ,new GBC(1,1).setWeight(2,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(sendBlockLabel
                ,new GBC(0,2).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(sendBlockVol
                ,new GBC(1,2).setWeight(2,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(sendDataLabel
                ,new GBC(0,3).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(sendDataVol
                ,new GBC(1,3).setWeight(2,1).setFill(GBC.BOTH).setInsets(5,0,0,10));

        centPanel.add(wantSizeLabel
                ,new GBC(0,4).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(wantSizeVolume
                ,new GBC(1,4).setWeight(2,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(partnersLabel
                ,new GBC(0,5).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(partnersVolume
                ,new GBC(1,5).setWeight(2,1).setFill(GBC.BOTH).setInsets(5,0,0,10));

        /*        add(dupBlockLabel
                ,new GBC(0,4).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        add(dupBlockVol
                ,new GBC(1,4).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));*/

        add(centPanel,BorderLayout.CENTER);

    }

    private void setListeners() {

    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static InFoBitswapPanel getContext() {
        return context;
    }

    public void setVolume(BitSwap bitSwap){
        if(bitSwap==null)return;
        recBlockVol.setText(bitSwap.getBlocksReceived()+"");
        long recDataSize = bitSwap.getDataReceived()==null?0:bitSwap.getDataReceived();
        recDataVol.setText(DataSizeFormatUtil.formatDataSize(recDataSize));

        sendBlockVol.setText(bitSwap.getBlocksSent()+"");
        long sendData = bitSwap.getDataSent()==null? 0 :bitSwap.getDataSent();
        sendDataVol.setText(DataSizeFormatUtil.formatDataSize(sendData));

        wantSizeVolume.setText(bitSwap.getWantSize()+"");
        partnersVolume.setText(bitSwap.getPeersSize()+"个");

    }

    public void refreshData(BitSwap bitSwap){
        setVolume(bitSwap);
        updateUI();
    }



    @Override
    protected void paintComponent(Graphics g) {
        //super.printComponent(g);
        if(!isOpaque())return;

        int w = getWidth();
        int h = getHeight();
        Graphics2D g2 = (Graphics2D)g;
        GradientPaint gp = new GradientPaint(0,0,new Color(255,255,153),w,h,new Color(255,255,204));
        g2.setPaint(gp);
        g2.fillRect(0,0,w,h);
    }
}