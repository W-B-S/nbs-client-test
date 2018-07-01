package io.ipfs.nbs.ui.panels;

import UI.ConstantsUI;
import com.nbs.ui.components.VerticalFlowLayout;
import com.nbs.ui.listener.AbstractMouseListener;
import io.ipfs.nbs.cnsts.AppGlobalCnst;
import io.ipfs.nbs.cnsts.ColorCnst;
import io.ipfs.nbs.helper.AvatarImageHandler;
import io.ipfs.nbs.peers.PeerInfo;
import io.ipfs.nbs.ui.components.GBC;
import io.ipfs.nbs.ui.components.LamButtonIcon;
import io.ipfs.nbs.ui.components.NBSIconButton;
import io.ipfs.nbs.ui.frames.MainFrame;
import io.ipfs.nbs.utils.ButtonIconUtil;
import io.ipfs.nbs.utils.IconUtil;
import org.apache.commons.lang3.StringUtils;

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


    private void initComponents(){

        upButtonPanel = new JPanel();
        upButtonPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,0,15,false,false));

        avatarLabel = new JLabel();
        PeerInfo peer = MainFrame.getContext().getCurrentPeer();
        ImageIcon icon = null;
        if(peer!=null&&StringUtils.isNotBlank(peer.getId())
                &&StringUtils.isNotBlank(peer.getAvatarSuffix())){
            String a48Path = AppGlobalCnst.consturactPath(AvatarImageHandler.getAvatarProfileHome(),"thumbs",peer.getId()+peer.getAvatarSuffix());
            System.out.println(a48Path);
            if((new File(a48Path)).exists()){
                icon = new ImageIcon(a48Path);
            }else {
                icon = IconUtil.getIcon(this,"/icons/lambor48.png");
            }
        }

        avatarLabel.setIcon(icon);
        avatarLabel.setHorizontalAlignment(JLabel.CENTER);
        initialButton();

        /**
         *
         */
        bottomPanel = new JPanel();
        bottomPanel.setBackground(ColorCnst.DARKER);
        bottomPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.BOTTOM,0,10,false,false));


    }

    private void initView(){
        //setPreferredSize(new Dimension(MainFrame.TOOLBAR_WIDTH,MainFrame.HEIGHT));
        setLayout(new GridBagLayout());
        upButtonPanel.setBackground(ColorCnst.DARKER);
        upButtonPanel.add(avatarLabel);
        avatarLabel.setBackground(ColorCnst.DARK);

        upButtonPanel.add(infoBTN);
        upButtonPanel.add(imBTN);
        imBTN.actived();
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

        aboutBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBTN(aboutBTN);
                mainCtx.mainWinShow(MainFrame.MainCardLayoutTypes.ABOUT);
            }
        });
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
        selectedBTN(infoBTN);
        mainCtx.mainWinShow(MainFrame.MainCardLayoutTypes.INFO);
    }
    private void initialButton(){
        infoBTN = ButtonIconUtil.infoBTN;
        imBTN = ButtonIconUtil.imBTN;
        dataBTN =ButtonIconUtil.dataBTN;
        musicBTN = ButtonIconUtil.musicBTN;
        aboutBTN = ButtonIconUtil.aboutBTN;
    }
}
