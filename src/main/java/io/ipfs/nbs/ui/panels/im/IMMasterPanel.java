package io.ipfs.nbs.ui.panels.im;

import io.ipfs.nbs.cnsts.ColorCnst;
import io.ipfs.nbs.ui.frames.MainFrame;
import io.ipfs.nbs.ui.panels.TitlePanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

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
    JLabel testShowLanbel = new JLabel("聊天IMMasterPanel");

    private static IMMasterPanel context;


    private JPanel centerPanel;
    private IMLeftPanel leftPanel;
    private IMRightPanel rightPanel;

    public IMMasterPanel() {
        context = this;
        initComponents();
        initView();

    }

    private void initComponents(){
        leftPanel = new IMLeftPanel(this);
        leftPanel.setPreferredSize(new Dimension(IM_LEFT_WIDTH,MainFrame.H_SIZE));
        //leftPanel.setBackground(ColorCnst.FONT_GRAY_DARKER);
        rightPanel = new IMRightPanel(this);

        //centerPanel.add(winTitlePanel);
        //centerPanel.add(testShowLanbel);


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
}
