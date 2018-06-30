package io.ipfs.nbs.ui.frames;

import io.ipfs.nbs.peers.PeerInfo;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.ipfs.nbs.ui.frames
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-14:46
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MainFrame extends JFrame {

    private static MainFrame context;
    private static PeerInfo currentPeer;
    private static final int W_SIZE = 900;
    private static final int H_SIZE = 650;
    private JPanel test;

    public MainFrame(PeerInfo peerInfo){
        context = this;
        currentPeer = peerInfo;
        initComponents();
    }

    private void initComponents(){
        Dimension winDimension = new Dimension(W_SIZE,H_SIZE);
        setSize(winDimension);
        setMinimumSize(winDimension);

        test = new JPanel();
        JLabel jLabel = new JLabel(currentPeer.getNick());
        add(jLabel);
    }
}
