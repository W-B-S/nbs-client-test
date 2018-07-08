package io.nbs.client.ui.panels;

import io.nbs.client.ui.components.VerticalFlowLayout;
import io.nbs.client.cnsts.AppGlobalCnst;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.helper.AvatarImageHandler;
import io.nbs.sdk.beans.PeerInfo;
import io.nbs.client.ui.components.GBC;
import io.nbs.client.ui.components.NBSIconButton;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.commons.utils.ButtonIconUtil;
import io.nbs.commons.utils.IconUtil;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @Package : io.ipfs.nbs.ui.panels
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-16:41
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ToolbarPanel extends JPanel {
    private static ToolbarPanel context;
    private JPanel upButtonPanel;
    private JPanel bottomPanel;
    /**
     * 头像
     */
    private JLabel avatarLabel;
    /**
     *
     */
    private static NBSIconButton infoBTN;
    /**
     *
     */
    private static NBSIconButton imBTN;
    /**
     *
     */
    private static NBSIconButton dataBTN;
    /**
     *
     */
    private static NBSIconButton musicBTN;
    private static NBSIconButton setBTN;

    private static NBSIconButton aboutBTN;
    private MainFrame mainCtx;

    public ToolbarPanel(MainFrame mainFrameCtx) {
        mainCtx = mainFrameCtx;
        initComponents();
        initView();
        setListeners();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!isOpaque())return;
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2 = (Graphics2D)g;
        GradientPaint gradientPaint = new GradientPaint(0,0,new Color(37,32,72),width,height,new Color(59,54,98));
        g2.setPaint(gradientPaint);
        g2.fillRect(0,0,width,height);
    }

    private void initComponents(){

        upButtonPanel = new JPanel();
        upButtonPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,0,15,false,false));

        avatarLabel = new JLabel();
        PeerInfo peer = MainFrame.getContext().getCurrentPeer();
        ImageIcon icon = getAvatarIcon(peer);
        avatarLabel.setIcon(icon);
        avatarLabel.setHorizontalAlignment(JLabel.CENTER);
        initialButton();

        /**
         *
         */
        bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        //bottomPanel.setBackground(ColorCnst.DARKER);
        bottomPanel.setLayout(new BorderLayout());

        //new VerticalFlowLayout(VerticalFlowLayout.BOTTOM,0,10,false,false)

    }

    /**
     *
     * @param peer
     * @return
     */
    private ImageIcon getAvatarIcon(PeerInfo peer){
        ImageIcon icon;
        if(peer!=null&&StringUtils.isNotBlank(peer.getId())
                &&StringUtils.isNotBlank(peer.getAvatarSuffix())){
            String a48Path = AppGlobalCnst.consturactPath(AvatarImageHandler.getAvatarProfileHome(),peer.getAvatarName());
            //System.out.println(a48Path);
            if((new File(a48Path)).exists()){
                icon = new ImageIcon(a48Path);
                Image image = icon.getImage().getScaledInstance(42,48,Image.SCALE_SMOOTH);
                icon.setImage(image);
            }else {
                icon = IconUtil.getIcon(this,"/icons/lambor48.png");
            }
        }else {
            icon = IconUtil.getIcon(this,"/icons/lambor48.png");
        }
        return icon;
    }

    private void initView(){
        //setPreferredSize(new Dimension(MainFrame.TOOLBAR_WIDTH,MainFrame.HEIGHT));
        setLayout(new GridBagLayout());
        upButtonPanel.setOpaque(false);
        //upButtonPanel.setBackground(ColorCnst.DARKER);
        upButtonPanel.add(avatarLabel);
        //avatarLabel.setBackground(ColorCnst.DARK);

        upButtonPanel.add(infoBTN);
        upButtonPanel.add(imBTN);
        //imBTN.actived();
        upButtonPanel.add(dataBTN);
        upButtonPanel.add(musicBTN);


        bottomPanel.add(aboutBTN);

        add(upButtonPanel,
                new GBC(0,0).setWeight(1,7).setFill(GBC.VERTICAL).setInsets(2,0,0,0));

        add(bottomPanel,
                new GBC(0,1).setWeight(1,1).setFill(GBC.VERTICAL).setInsets(0,0,2,0));


    }

    private void setListeners(){
        infoBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBTN(infoBTN);
                mainCtx.mainWinShow(MainFrame.MainCardLayoutTypes.INFO);
            }
        });

        imBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBTN(imBTN);
                mainCtx.mainWinShow(MainFrame.MainCardLayoutTypes.IM);
            }
        });

        dataBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBTN(dataBTN);
                mainCtx.mainWinShow(MainFrame.MainCardLayoutTypes.DATD);
            }
        });

        aboutBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBTN(aboutBTN);
                mainCtx.mainWinShow(MainFrame.MainCardLayoutTypes.ABOUT);
            }
        });



        musicBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBTN(musicBTN);
                mainCtx.mainWinShow(MainFrame.MainCardLayoutTypes.ABOUT);
            }
        });
    }

    public void refreshToolbarAvatar(){
        ImageIcon icon = getAvatarIcon(MainFrame.getContext().getCurrentPeer());
        avatarLabel.setIcon(icon);
        avatarLabel.updateUI();
    }
    /**
     * Selection NBSButton
     * @param btn
     */
    private void selectedBTN(NBSIconButton btn){
        NBSIconButton[] btns = {infoBTN,imBTN,dataBTN,musicBTN,aboutBTN};
        for(NBSIconButton button : btns){
            if(btn==button){
                button.actived();
            }else {
                button.normal();
            }
        }
    }

    public void setDefaultSelected(){
        selectedBTN(imBTN);
        mainCtx.mainWinShow(MainFrame.MainCardLayoutTypes.IM);
    }
    private void initialButton(){
        infoBTN = ButtonIconUtil.infoBTN;
        imBTN = ButtonIconUtil.imBTN;
        dataBTN =ButtonIconUtil.dataBTN;
        musicBTN = ButtonIconUtil.musicBTN;
        aboutBTN = ButtonIconUtil.aboutBTN;
    }

    public static ToolbarPanel getContext() {
        return context;
    }
}
