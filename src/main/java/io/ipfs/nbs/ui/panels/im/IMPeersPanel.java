package io.ipfs.nbs.ui.panels.im;

import com.nbs.entity.ContactsItem;
import com.nbs.im.service.PeerServiceImpl;
import io.nbs.commons.adapter.ContactsItemAdapter;
import io.ipfs.nbs.cnsts.ColorCnst;
import io.ipfs.nbs.ui.components.GBC;
import io.ipfs.nbs.ui.components.NbsListView;
import io.ipfs.nbs.ui.panels.ParentAvailablePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package : io.ipfs.nbs.ui.panels.im
 * @Description :
 * <p>
 *     左侧节点
 * </p>
 * @Author : lambor.c
 * @Date : 2018/7/1-22:06
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IMPeersPanel extends ParentAvailablePanel {
    private Logger logger = LoggerFactory.getLogger(IMPeersPanel.class);

    private static IMPeersPanel context;

    /**
     * 缓存
     */
    private List<ContactsItem> contactItems = new ArrayList<>();

    private NbsListView peerlistView;

    private PeerServiceImpl peerService;

    /**
     * construction
     */
    public IMPeersPanel(JPanel parent) {
        super(parent);
        initComponents();
        initView();

        initData();
        peerlistView.setAdapter(new ContactsItemAdapter(contactItems));


    }

    /**
     * [initComponents description]
     *
     * @return {[type]} [description]
     */
    private void initComponents() {
        peerlistView = new NbsListView();

        peerService = new PeerServiceImpl();

    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        setLayout(new GridBagLayout());

        peerlistView.setContentPanelBackground(ColorCnst.CONTACTS_ITEM_GRAY_MAIN);
        add(peerlistView,
                new GBC(0,0).setFill(GBC.BOTH).setWeight(1,1));
    }

    private void initData(){
        contactItems.clear();
        contactItems = peerService.loadPeers(false);
    }

    private void setListeners() {

    }

    private void initTestPeers(){
        String avatarHash = ".nbs/cache/avatars/thumbs/QmcGgwkoZpVZa8nytvPCCLuMQUMFF2QrzXDG77D9Qz1N5T.JPG";
    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static IMPeersPanel getContext() {
        return context;
    }
}