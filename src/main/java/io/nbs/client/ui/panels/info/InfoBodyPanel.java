package io.nbs.client.ui.panels.info;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.nbs.client.Launcher;
import io.nbs.client.cnsts.AppGlobalCnst;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.helper.AvatarImageHandler;
import io.nbs.client.listener.AbstractMouseListener;
import io.nbs.client.ui.components.GBC;
import io.nbs.client.ui.components.LCJlabel;
import io.nbs.client.ui.components.VerticalFlowLayout;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.commons.helper.ConfigurationHelper;
import io.nbs.commons.utils.IconUtil;
import io.nbs.sdk.beans.PeerInfo;
import io.nbs.sdk.prot.IPMParser;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Package : io.ipfs.nbs.ui.panels.info
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/1-11:00
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class InfoBodyPanel extends ParentAvailablePanel {

    private static InfoBodyPanel context;
    private JPanel avatarJPanel;
    private JLabel avatarLabel;
    private LCJlabel nickLabel;
    private LCJlabel locationsLabel;
    private JPanel peerPanel;
    private LCJlabel peerIDLabel;
    private JTextField peerIdField;
    private JFileChooser fileChooser;
    private IPFS ipfs;
    private AvatarImageHandler imageHandler ;

    public InfoBodyPanel(JPanel parent) {
        super(parent);
        context = this;
        ipfs = Launcher.getContext().getIpfs();
        imageHandler = AvatarImageHandler.getInstance();
        initComponents();
        initView();
        setListeners();
    }

    /**
     *
     */
    private void initComponents(){
        avatarJPanel = new JPanel();
        avatarLabel = new JLabel();
        PeerInfo current = getCurrent();
        String avatarName = current.getAvatarName();
        ImageIcon avatarIcon;
        if(StringUtils.isBlank(avatarName)){
            avatarIcon = IconUtil.getIcon(this,"/images/nbs750.jpg",128,128);
        }else {
            String avatarPath = AppGlobalCnst.consturactPath(AvatarImageHandler.getAvatarProfileHome(),avatarName);
            avatarIcon =  AvatarImageHandler.getInstance().getImageIconFromOrigin(new File(avatarPath),128);
        }
        avatarLabel.setIcon(avatarIcon);
        avatarLabel.setBackground(ColorCnst.WINDOW_BACKGROUND_LIGHT);

        //nick
        String nick = current.getNick()==null? current.getId() : current.getNick();
        nickLabel = new LCJlabel(nick);
        nickLabel.setFont(FontUtil.getDefaultFont(30));
        nickLabel.setHorizontalAlignment(JLabel.CENTER);
        //locations
        String locations = current.getLocations();
        if(StringUtils.isBlank(locations))locations = current.getIp()==null ? "" : current.getIp();
        locationsLabel = new LCJlabel(locations);
        locationsLabel.setFont(FontUtil.getDefaultFont(20));
        locationsLabel.setHorizontalAlignment(JLabel.CENTER);

        //peer
        peerPanel = new JPanel();
        peerIDLabel = new LCJlabel("Peer ID :");
        peerIDLabel.setHorizontalAlignment(JLabel.RIGHT);
        peerIDLabel.setFont(FontUtil.getDefaultFont(13));
        peerIdField = new JTextField(current.getId());
        peerIdField.setBorder(null);
        peerIdField.setHorizontalAlignment(JTextField.LEFT);
        peerIdField.setEditable(false);
        peerIdField.setBackground(ColorCnst.WINDOW_BACKGROUND);



    }

    private void initView(){
        setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,0,20,true,false));
        /*=====================================================*/
        avatarLabel.setHorizontalAlignment(JLabel.CENTER);

        peerPanel.setLayout(new GridBagLayout());
        JLabel placeHolder = new JLabel();
        placeHolder.setMinimumSize(new Dimension(100,50));
        add(placeHolder);
        add(avatarLabel);
        peerPanel.add(peerIDLabel
        ,new GBC(0,0).setWeight(1,1).setFill(GBC.BOTH).setInsets(0,0,0,0));
        peerPanel.add(peerIdField
                ,new GBC(1,0).setWeight(1,1).setFill(GBC.BOTH).setInsets(0,10,0,0));

        add(avatarLabel);
               // new GBC(0,0).setWeight(1,5).setFill(GBC.HORIZONTAL).setInsets(0,0,0,0));
        add(nickLabel);
              //  new GBC(0,1).setWeight(1,2).setFill(GBC.HORIZONTAL).setInsets(0,0,0,0));

        add(locationsLabel);
        add(peerPanel);
    }
    private void setListeners(){
        //头像事件
        avatarLabel.addMouseListener(new AbstractMouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                uploadAvatar();

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                avatarLabel.setCursor(MainFrame.handCursor);
                avatarLabel.setToolTipText("点击修改头像");
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        });

        //修改昵称
        nickLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String oriNick = nickLabel.getText();
                String upText = JOptionPane.showInputDialog(context,"请输入新的昵称","修改昵称",JOptionPane.INFORMATION_MESSAGE);
                if(StringUtils.isBlank(upText)||upText.trim().equals(oriNick))return;

                upText = upText.trim();
                IPFS ipfs = Launcher.getContext().getIpfs();
                if(ipfs==null)return;
                try {
                    String enUpText = IPMParser.urlEncode(upText);
                    ipfs.config.set(ConfigurationHelper.JSON_NICKNAME_KEY,enUpText);
                    MainFrame.getContext().getCurrentPeer().setNick(upText);
                    nickLabel.setText(upText);
                } catch (IOException ioe) {
                    logger.error("更新 IPFS config error :{}",ioe.getMessage());
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                nickLabel.setToolTipText("点击修改昵称");
                nickLabel.setCursor(MainFrame.handCursor);
                super.mouseEntered(e);
            }
        });
    }

    private PeerInfo getCurrent(){
        return MainFrame.getContext().getCurrentPeer();
    }

    /**
     *
     */
    private void uploadAvatar(){
        PeerInfo self = getCurrent();
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.showDialog(this,"选择图片");
        File file = fileChooser.getSelectedFile();
        if(file!=null) {
            String name = file.getName();//源文件名

            List<MerkleNode> nodes;
            try {
                //上传前先压缩
                String originAvatarName = imageHandler.createdAvatar4Profile(file,name);
                File file128 = new File(AppGlobalCnst.consturactPath(AvatarImageHandler.getAvatarProfileHome(),originAvatarName));
                NamedStreamable.FileWrapper fileWrapper = new NamedStreamable.FileWrapper(file128);
                //上传ipfs
                nodes = ipfs.add(fileWrapper);
                String fileHash = nodes.get(0).hash.toBase58();

                self.setAvatar(fileHash);
                self.setAvatarName(originAvatarName);
                self.setAvatarSuffix(name.substring(name.lastIndexOf(".")));

                ipfs.config.set(ConfigurationHelper.JSON_AVATAR_KEY,fileHash);
                ipfs.config.set(ConfigurationHelper.JSON_AVATAR_SUFFIX_KEY,self.getAvatarSuffix());
                ipfs.config.set(ConfigurationHelper.JSON_AVATAR_NAME_KEY,originAvatarName);

                //TODO 存数据库upload
                /**
                 * 创建Hash 头像 :cache/avatar/custom
                 */
                String hashFileName = fileHash + AvatarImageHandler.AVATAR_SUFFIX;
                try {
                    imageHandler.createContactsAvatar(file,hashFileName);
                    //BufferedImage image = ImageIO.read(file128);
                    ImageIcon avatarIcon = AvatarImageHandler.getInstance().getAvatarScaleIcon(file128,128);

                    logger.info( file128.getAbsolutePath());
                    if(avatarIcon!=null){
                        logger.info(fileHash);
                        avatarLabel.setIcon(avatarIcon);
                        avatarLabel.validate();
                        avatarLabel.updateUI();
                        MainFrame.getContext().refreshAvatar();
                    }
                } catch (Exception e) {
                    logger.info(e.getMessage());
                    return;
                }
            } catch (Exception e) {
                logger.error("上传失败：{}",e.getMessage());
                JOptionPane.showMessageDialog(context,"上传失败");
                return;
            }
            // new Thread(()->{ }).start();
        }
    }
}
