package io.nbs.client.ui.panels;

import io.nbs.client.listener.AbstractMouseListener;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.cnsts.OSUtil;
import io.nbs.client.ui.components.GBC;
import io.nbs.client.ui.frames.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @Package : io.ipfs.nbs.ui.panels
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-18:03
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved. ParentAvailablePanel
 */
public class TitlePanel extends ParentAvailablePanel {

    private static TitlePanel context;

    private JPanel titlePanel;
    private JLabel titleLabel;
    private JLabel statusLabel;

    private JPanel ctrlPanel;
    private JLabel closeLabel;
    private JLabel maxLabel;
    private JLabel minLabel;

    private ImageIcon maxIcon;
    private ImageIcon minIcon;
    private ImageIcon restoreIcon;

    private boolean windowMax ;
    private Rectangle desktopBounds; // 去除任务栏后窗口的大小
    private Rectangle normalBounds;
    private long lastClickTime;
    private static Point origin = new Point();


    /**
     * JPanel parent
     */
    public TitlePanel(JPanel parent) {
        super(parent);
        context = this;
        initComponents();
        initView();
        setListeners();
        initBounds();
    }

    private void initComponents(){
        setBackground(ColorCnst.LIGHT_GRAY);
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
        Dimension ctrlItemSize = new Dimension(30,30);

        maxIcon = new ImageIcon(getClass().getResource("/icons/window_max.png"));
        minIcon = new ImageIcon(getClass().getResource("/icons/window_min.png"));
        restoreIcon =  new ImageIcon(getClass().getResource("/icons/window_restore.png"));
        titlePanel = new JPanel();
        titlePanel.setLayout(new GridBagLayout());

        titleLabel = new JLabel();
        titleLabel.setFont(FontUtil.getDefaultFont(15));


        CtrlLabelMouseListener listener = new CtrlLabelMouseListener();
        /**
         * 窗口控制
         */
        ctrlPanel = new JPanel();
        ctrlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,0,0));

        closeLabel = new JLabel();
        closeLabel.setIcon(new ImageIcon(getClass().getResource("/icons/close.png")));

        closeLabel.setHorizontalAlignment(JLabel.CENTER);
        closeLabel.setOpaque(true);
        closeLabel.setPreferredSize(ctrlItemSize);
        closeLabel.setCursor(handCursor);
        closeLabel.addMouseListener(listener);

        maxLabel = new JLabel();
        maxLabel.setIcon(maxIcon);
        maxLabel.setHorizontalAlignment(JLabel.CENTER);
        maxLabel.setOpaque(true);
        maxLabel.setPreferredSize(ctrlItemSize);
        maxLabel.setCursor(handCursor);
        maxLabel.addMouseListener(listener);

        minLabel = new JLabel();
        minLabel.setIcon(minIcon);
        minLabel.setHorizontalAlignment(JLabel.CENTER);
        minLabel.setOpaque(true);
        minLabel.setPreferredSize(ctrlItemSize);
        minLabel.setCursor(handCursor);
        minLabel.addMouseListener(listener);

    }

    /**
     *
     */
    private void initView(){
        setLayout(new GridBagLayout());
        setLayout(new GridBagLayout());
        JPanel left = new JPanel();
        left.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        JPanel right = new JPanel();
        right.setLayout(new FlowLayout(FlowLayout.RIGHT,0,0));

        setBorder(null);

        ctrlPanel.add(minLabel);
        ctrlPanel.add(maxLabel);
        ctrlPanel.add(closeLabel);

        int margin;
        if(OSUtil.getOsType() != OSUtil.Mac_OS){
            right.add(ctrlPanel);
            left.add(titlePanel);
            add(left,
                    new GBC(0,0).setFill(GBC.BOTH).setWeight(85,5).setInsets(0,0,0,0));
            add(right,
                    new GBC(1,0).setFill(GBC.HORIZONTAL).setWeight(10,2).setInsets(0,0,0,0) );
            margin = 5;
        }else {
            left.add(titlePanel);
            add(left,
                    new GBC(0,0).setFill(GBC.BOTH).setWeight(85,5).setInsets(0,0,0,0));
            margin = 15;
        }

        titlePanel.add(titleLabel,
                new GBC(0,0).setFill(GBC.BOTH).setWeight(300,1).setInsets(margin,margin,0,0));
        //titlePanel.add()

    }

    /**
     *
     */
    private void setListeners(){

        if(OSUtil.getOsType() != OSUtil.Mac_OS){
            MouseAdapter mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(System.currentTimeMillis()-lastClickTime < 700){
                        maxOrRestoreWindow();
                    }
                    lastClickTime =System.currentTimeMillis();
                    super.mouseClicked(e);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                   origin.x = e.getX();
                   origin.y =e.getY();
                }
            };

            /**
             * 拖动
             */
            MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    Point oldP = MainFrame.getContext().getLocation();
                    MainFrame.getContext().setLocation(oldP.x +e.getX()-origin.x,oldP.y+e.getY()-origin.y);
                    //super.mouseDragged(e);
                }
            };

            ctrlPanel.addMouseListener(mouseAdapter);
            ctrlPanel.addMouseMotionListener(mouseMotionListener);

            this.addMouseListener(mouseAdapter);
            this.addMouseMotionListener(mouseMotionListener);
        }

        /**
         * TODO room member control
         */
        MouseListener mouseListener = new AbstractMouseListener(){

        };

    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title){
        if(title==null)title="";
        titleLabel.setText(title);
    }

    private void maxOrRestoreWindow(){
        if(windowMax){
            MainFrame.getContext().setBounds(normalBounds);
            maxLabel.setIcon(maxIcon);
            windowMax = false;
        }else {
            MainFrame.getContext().setBounds(desktopBounds);
            maxLabel.setIcon(restoreIcon);
            windowMax = true;
        }
    }
    /**
     *
     */
    private void initBounds(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(MainFrame.getContext().getGraphicsConfiguration());

        desktopBounds = new Rectangle(insets.left,insets.top,
            screenSize.width - insets.left - insets.right,
                screenSize.height-insets.top-insets.bottom
        );

        normalBounds = new Rectangle(
                (screenSize.width -MainFrame.W_SIZE)/2,
                (screenSize.height -MainFrame.H_SIZE) /2,
                MainFrame.W_SIZE,MainFrame.H_SIZE
        );
    }

    private class CtrlLabelMouseListener extends AbstractMouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getComponent()==closeLabel){
                //MainFrame.getContext().setVisible(false);
                System.exit(1);
            }else if(e.getComponent() == maxLabel){
                maxOrRestoreWindow();
            }else if(e.getComponent() == minLabel){
                MainFrame.getContext().setExtendedState(JFrame.ICONIFIED);
            }
            //super.mouseClicked(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            ((JLabel)e.getSource()).setBackground(ColorCnst.LIGHT_GRAY);
            super.mouseEntered(e);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ((JLabel)e.getSource()).setBackground(ColorCnst.WINDOW_BACKGROUND);
            super.mouseExited(e);
        }
    }

    public static TitlePanel getContext() {
        return context;
    }
}
