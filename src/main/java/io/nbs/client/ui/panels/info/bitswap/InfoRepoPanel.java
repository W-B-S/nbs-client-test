package io.nbs.client.ui.panels.info.bitswap;

import io.ipfs.api.repo.RepoStat;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.ui.components.GBC;
import io.nbs.client.ui.components.LCFromLabel;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.commons.utils.DataSizeFormatUtil;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.panels.info.bitswap
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-1:09
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class InfoRepoPanel extends ParentAvailablePanel {
    private static InfoRepoPanel context;
    private JLabel titleLabel;
    private LCFromLabel objectsLabel;
    private LCFromLabel objectsVol;
    private LCFromLabel objectDataLabel;
    private LCFromLabel objectDataVol;
    private LCFromLabel repoPathLabel;
    private LCFromLabel repoPathVol;
    private LCFromLabel tempPathLabel;
    private LCFromLabel tempPathVol;
    private JPanel centPanel;
    /**
     * construction
     */
    public InfoRepoPanel(JPanel parent) {
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
        titleLabel = new JLabel("NBS 资源库");
        titleLabel.setOpaque(false);
        titleLabel.setFont(FontUtil.getDefaultFont(15));
        titleLabel.setForeground(ColorCnst.FONT_ABOUT_TITLE_BLUE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        objectsLabel = new LCFromLabel("资源库文件数量:");
        objectsVol = new LCFromLabel(false);

        objectDataLabel = new LCFromLabel("资源库文件总量:");
        objectDataVol = new LCFromLabel(false);

        repoPathLabel = new LCFromLabel("NBS资源库位置:");
        repoPathVol = new LCFromLabel(false);
        tempPathLabel = new LCFromLabel("下载文件位置:");
        tempPathVol = new LCFromLabel(false);
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
        centPanel.add(objectsLabel
                ,new GBC(0,0).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(objectsVol
                ,new GBC(1,0).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(objectDataLabel
                ,new GBC(0,1).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(objectDataVol
                ,new GBC(1,1).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(repoPathLabel
                ,new GBC(0,2).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(repoPathVol
                ,new GBC(1,2).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(tempPathLabel
                ,new GBC(0,3).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));
        centPanel.add(tempPathVol
                ,new GBC(1,3).setWeight(5,1).setFill(GBC.BOTH).setInsets(5,0,0,10));

        add(centPanel,BorderLayout.CENTER);
    }

    private void setListeners() {

    }


    public void setVolume(RepoStat stat,String downloadPath){
        if(StringUtils.isNotBlank(downloadPath)) tempPathVol.setText(downloadPath);
        if(stat==null)return;
        objectsVol.setText(stat.getNumObjects()+"");
        objectDataVol.setText(DataSizeFormatUtil.formatDataSize(stat.getRepoSize()));

        repoPathVol.setText(stat.getRepoPath());

    }

    public void refreshVolume(RepoStat stat,String downloadPath){
        setVolume(stat,downloadPath);
        updateUI();
    }
    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static InfoRepoPanel getContext() {
        return context;
    }

    /**
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        //super.printComponent(g);
        if(!isOpaque())return;

        int w = getWidth();
        int h = getHeight();
        Graphics2D g2 = (Graphics2D)g;
        GradientPaint gp = new GradientPaint(0,0,new Color(253,204,153),w,h,new Color(204,204,153));
        g2.setPaint(gp);
        g2.fillRect(0,0,w,h);
    }
}