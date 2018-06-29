package io.ipfs.nbs.ui.frames;

import com.nbs.ui.listener.AbstractMouseListener;
import io.ipfs.nbs.cnsts.ColorCnst;
import io.ipfs.nbs.cnsts.FontUtil;
import io.ipfs.nbs.cnsts.OSUtil;
import io.ipfs.nbs.ui.components.GBC;
import io.ipfs.nbs.ui.components.NBSButton;
import io.ipfs.nbs.utils.IconUtil;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * @Package : io.ipfs.nbs.ui.frames
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/29-15:23
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class FailFrame extends JFrame {
    private static final int WIDTH = 300;
    private static final int HEIGHT = 200;

    private JPanel controlPanel ;
    private JLabel closeLabel;
    private JLabel messageLabel;
    private JLabel titleLabel;
    private NBSButton exitButton;
    private static Point origin = new Point();

    public FailFrame(String errorMsg){
        initComponents();
        centerScreen();
        initView(errorMsg);
        addListeners();
    }

    private void initComponents(){
        Dimension winSize = new Dimension(WIDTH,HEIGHT);
        setMinimumSize(winSize);
        setMaximumSize(winSize);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,10,5));

        closeLabel = new JLabel();
        closeLabel.setIcon(IconUtil.getIcon(this,"/icons/close.png"));
        closeLabel.setHorizontalAlignment(JLabel.CENTER);
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        titleLabel = new JLabel("客户端启动失败");
        titleLabel.setFont(FontUtil.getDefaultFont(18));

        exitButton = new NBSButton("退出",ColorCnst.MAIN_COLOR,ColorCnst.MAIN_COLOR_DARKER);
        exitButton.setPreferredSize(new Dimension(200,40));

        messageLabel = new JLabel();
        messageLabel.setFont(FontUtil.getDefaultFont(16));
    }

    private void initView(String message){
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new LineBorder(ColorCnst.LIGHT_GRAY));
        contentPanel.setLayout(new GridBagLayout());
       // contentPanel.setBackground(ColorCnst.WINDOW_BACKGROUND);

        controlPanel.add(closeLabel);

        if(OSUtil.getOsType() != OSUtil.Mac_OS){
            setUndecorated(true);
            contentPanel.add(controlPanel
                    ,new GBC(0,0)
                            .setFill(GBC.BOTH)
                            .setWeight(1,1)
                            .setInsets(10,0,0,0));
        }
        JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.add(exitButton,new GBC(0,0)
        .setFill(GBC.BOTH).setWeight(1,1).setInsets(10,0,0,0));

        JPanel messagePanel = new JPanel();
        messageLabel.setText(message);
        messagePanel.add(messageLabel);


        //contentPanel.add(messageLabel);
        add(contentPanel);

        contentPanel.add(titlePanel,
                new GBC(0,1).setWeight(1,1).setFill(GBC.BOTH).setInsets(10,10,0,10));
        contentPanel.add(messagePanel,
                new GBC(0,2).setWeight(1,10).setFill(GBC.BOTH).setInsets(10,10,0,10));
        contentPanel.add(buttonPanel,
                new GBC(0,3).setWeight(1,1).setFill(GBC.HORIZONTAL).setInsets(10,10,10,10));
    }

    private void centerScreen(){
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocation(
                (tk.getScreenSize().width-WIDTH)/2,
                (tk.getScreenSize().height-HEIGHT)/2
        );
    }

    private void addListeners(){
        closeLabel.addMouseListener(new AbstractMouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(1);
                super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                closeLabel.setBackground(ColorCnst.LIGHT_GRAY);
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeLabel.setBackground(ColorCnst.WINDOW_BACKGROUND);
                super.mouseExited(e);
            }
        });
        if(OSUtil.getOsType() != OSUtil.Mac_OS){
            addMouseListener(new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent e) {
                    origin.x = e.getX();
                    origin.y = e.getY();
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    Point p = FailFrame.this.getLocation();
                    FailFrame.this.setLocation(p.x+e.getX()-origin.x,p.y+e.getY()-origin.y);
                }
            });

        }
    }
}
