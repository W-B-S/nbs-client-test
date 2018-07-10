package io.nbs.client.ui.panels.info;

import com.nbs.ipfs.IPFSHelper;
import io.nbs.client.listener.AbstractMouseListener;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.nbs.client.Launcher;
import io.nbs.client.cnsts.AppGlobalCnst;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.helper.AvatarImageHandler;
import io.nbs.commons.helper.ConfigurationHelper;
import io.nbs.commons.utils.IconUtil;
import io.nbs.sdk.beans.PeerInfo;
import io.nbs.client.ui.components.GBC;
import io.nbs.client.ui.components.LCJlabel;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.sdk.prot.IPMParser;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Package : io.ipfs.nbs.ui.panels.info
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/1-10:58
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class InfoHeaderPanel extends ParentAvailablePanel {

    private JLabel avatarLabel;

    private LCJlabel nickLabel;
    private LCJlabel locationLabel;
    private LCJlabel peerIDPanel;
    private LCJlabel peerIDLabel;
    private JTextField peerIdField;

    private PeerInfo self;
    private static InfoHeaderPanel context;
    private JFileChooser fileChooser;
    private AvatarImageHandler imageHandler ;
    private IPFS ipfs;

    public InfoHeaderPanel(JPanel parent) {
        super(parent);
        context =this;
        ipfs = IPFSHelper.getInstance().getIpfs();
        self = MainFrame.getContext().getCurrentPeer();
        imageHandler = AvatarImageHandler.getInstance();
        initComponents();
        initView();

        setListeners();

    }

    /**
     *
     */
    private void initComponents(){
        avatarLabel = new JLabel();
        nickLabel = new LCJlabel(ColorCnst.FONT_GRAY_DARKER);
        nickLabel.setFont(FontUtil.getDefaultFont(24));

        locationLabel = new LCJlabel();
        locationLabel.setFont(FontUtil.getDefaultFont(14));
        peerIDPanel = new LCJlabel();
        peerIDLabel = new LCJlabel();
        peerIDLabel.setFont(FontUtil.getDefaultFont(12));
        peerIdField = new JTextField();
        peerIdField.setHorizontalAlignment(JTextField.LEFT);
        peerIdField.setFont(FontUtil.getDefaultFont(12));
        peerIdField.setEditable(false);
        peerIdField.setBorder(null);
    }

    /**
     *
     */
    private void initView(){
        setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();




        /**
         * avatar
         */
        String avatar128Path = AppGlobalCnst.consturactPath(AvatarImageHandler.getAvatarProfileHome(),self.getAvatarName());
        ImageIcon avatar = AvatarImageHandler.getInstance().getImageIconFromOrigin(avatar128Path,200);
        avatarLabel.setIcon(avatar);

        //leftPanel.setPreferredSize(new Dimension(150,150));
        avatarLabel.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(avatarLabel);

        //right begin
        rightPanel.setLayout(new GridBagLayout());

        if(self!=null&& StringUtils.isNotBlank(self.getNick()))nickLabel.setText(self.getNick());

        nickLabel.setHorizontalAlignment(JLabel.LEFT);

        locationLabel.setText(self.getLocations()!=null?self.getLocations():"");
        locationLabel.setHorizontalAlignment(JLabel.LEFT);

        JLabel peerIDTtile = new LCJlabel("Peer ID :");
        peerIDTtile.setHorizontalAlignment(JLabel.LEFT);

        peerIDTtile.setFont(FontUtil.getDefaultFont(12));

        //QmVJECTorWRbZAVnHeB2jpNnyUhfNAFJtWg8NSVRiAnrr5  测试用
        if(self!=null){
            peerIDLabel.setText(self.getId());
            peerIdField.setText(self.getId());
        }

        peerIDPanel.setLayout(new FlowLayout(FlowLayout.LEFT,2,2));
        peerIDPanel.add(peerIDTtile);
        peerIDPanel.add(peerIdField);

        rightPanel.add(nickLabel,
                new GBC(0,0).setFill(GBC.HORIZONTAL).setWeight(1,10).setInsets(0,15,0,0)
        );
        rightPanel.add(locationLabel,
                new GBC(0,1).setFill(GBC.HORIZONTAL).setWeight(1,9).setInsets(0,15,0,0)
        );
        rightPanel.add(peerIDPanel,
                new GBC(0,2).setFill(GBC.BOTH).setWeight(1,8).setInsets(0,15,5,0)
        );

        add(leftPanel,BorderLayout.WEST);
        add(rightPanel,BorderLayout.CENTER);

    }

    private void setListeners(){



    }



    /**
     * 更新数据库
     */
    private void updatePeers(){

    }
}
