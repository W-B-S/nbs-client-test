package io.nbs.client.ui.panels.im;

import io.nbs.client.Launcher;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.listener.IPFSSubscribeListener;
import io.nbs.client.listener.OnlineNotifier;
import io.nbs.client.services.IpfsMessageReceiver;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.vo.ContactsItem;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package : io.ipfs.nbs.ui.panels
 * @Description :
 * <p>
 *     聊天窗口
 * </p>
 * @Author : lambor.c
 * @Date : 2018/6/30-18:54
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IMMasterPanel extends JPanel {

    public static final int IM_LEFT_WIDTH = 280;

    private static IMMasterPanel context;

    private JPanel centerPanel;
    private IMLeftPanel leftPanel;
    private IMRightPanel rightPanel;

    private static OnlineNotifier onlineNotifier;
    private static IPFSSubscribeListener subscribeListener;
    /**
     * 消息接收器
     */
    private IpfsMessageReceiver messageReceiver;

    public IMMasterPanel() {
        context = this;
        initComponents();
        initView();

        //保持在最后
        messageReceiver = new IpfsMessageReceiver(subscribeListener,onlineNotifier);
        messageReceiver.startReceiver();
    }

    private void initComponents(){
        leftPanel = new IMLeftPanel(this);
        leftPanel.setPreferredSize(new Dimension(IM_LEFT_WIDTH,MainFrame.H_SIZE));
        rightPanel = new IMRightPanel(this);

        setBorder(new LineBorder(ColorCnst.SCROLL_BAR_TRACK_LIGHT));


    }

    private void initView(){
        setLayout(new BorderLayout());
        add(leftPanel,BorderLayout.WEST);
        add(rightPanel,BorderLayout.CENTER);
    }

    public static IMMasterPanel getContext() {
        return context;
    }

    public static void setOnlineNotifier(OnlineNotifier onlineNotifier) {
        IMMasterPanel.onlineNotifier = onlineNotifier;
    }

    public static void setSubscribeListener(IPFSSubscribeListener subscribeListener) {
        IMMasterPanel.subscribeListener = subscribeListener;
    }
}
