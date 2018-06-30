package io.ipfs.nbs.ui.panels;

import com.nbs.ui.components.VerticalFlowLayout;
import com.nbs.ui.listener.AbstractMouseListener;
import io.ipfs.nbs.cnsts.ColorCnst;
import io.ipfs.nbs.cnsts.FontUtil;
import io.ipfs.nbs.cnsts.OSUtil;
import io.ipfs.nbs.ui.components.GBC;
import io.ipfs.nbs.ui.components.LamBorder;
import io.ipfs.nbs.ui.frames.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @Package : io.ipfs.nbs.ui.panels
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-18:03
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved. ParentAvailablePanel
 */
public class TitlePanel extends JPanel {

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
    private boolean windowMax;

    private Rectangle desktopBounds; // 去除任务栏后窗口的大小
    private Rectangle normalBounds;
    private static Point origin = new Point();


    /**
     * JPanel parent
     */
    public TitlePanel() {
       // super(parent);
        context = this;
        initComponents();

        initView();

        setListeners();
        initBounds();
    }

    private void initComponents(){
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
        Dimension ctrlItemSize = new Dimension(30,30);

        maxIcon = new ImageIcon(getClass().getResource("/icons/window_max.png"));
        minIcon = new ImageIcon(getClass().getResource("/icons/window_min.png"));

        titlePanel = new JPanel();
        titlePanel.setLayout(new GridBagLayout());

        titleLabel = new JLabel();
        titleLabel.setFont(FontUtil.getDefaultFont(16));
        titleLabel.setText("NBS Chain 世界频道");


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

        maxLabel = new JLabel();
        maxLabel.setIcon(maxIcon);
        maxLabel.setHorizontalAlignment(JLabel.CENTER);
        maxLabel.setOpaque(true);
        maxLabel.setPreferredSize(ctrlItemSize);
        maxLabel.setCursor(handCursor);

        minLabel = new JLabel();
        minLabel.setIcon(minIcon);
        minLabel.setHorizontalAlignment(JLabel.CENTER);
        minLabel.setOpaque(true);
        minLabel.setPreferredSize(ctrlItemSize);
        minLabel.setCursor(handCursor);



    }

    private void initView(){
        setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,0,0,true,true));
        setBorder(null);
        this.setBorder(new LamBorder(LamBorder.BOTTOM,ColorCnst.LIGHT_GRAY));

        ctrlPanel.add(minLabel);
        ctrlPanel.add(maxLabel);
        ctrlPanel.add(closeLabel);

        int margin;
        if(OSUtil.getOsType() != OSUtil.Mac_OS){
            add(ctrlPanel);
            add(titlePanel);
            margin = 5;
        }else {
            add(titlePanel);
            margin = 15;
        }

        titlePanel.add(titleLabel,
                new GBC(0,0).setFill(GBC.BOTH).setWeight(300,1).setInsets(margin,margin,0,0));
        //titlePanel.add()

    }

    private void setListeners(){

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
                MainFrame.getContext().setVisible(false);
            }else if(e.getComponent() == maxLabel){

            }else if(e.getComponent() == minLabel){

            }
            super.mouseClicked(e);
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
