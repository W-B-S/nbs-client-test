package io.nbs.client.ui.components.common;

import io.nbs.client.cnsts.FontUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.components.common
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/17-13:12
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class JCirclePanel extends JPanel {
    private int r = 10;
    public final static Color DEF_COLOR =  new Color(255,102,99);
    private Color color;
    private JLabel tip;

    private int num = 0;

    public JCirclePanel(int r, Color color) {
        this.r = r;
        this.color = color;
        initComponents();
    }

    public JCirclePanel(int r) {
        this(r,DEF_COLOR);
    }

    private void initComponents(){
        this.setLayout(new BorderLayout());

        tip = new JLabel("3");
        tip.setFont(FontUtil.getDefaultFont(10,Font.BOLD));
        tip.setForeground(Color.WHITE);
        tip.setOpaque(false);
        tip.setHorizontalAlignment(JLabel.CENTER);
        this.add(tip,BorderLayout.CENTER);
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(this.color);
        int w = this.getWidth();
        int h = this.getHeight();
        g.fillOval(w/2-r,h/2-r,2*r,2*r);
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
        this.repaint();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        this.repaint();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        this.setVisible(true);
        this.setToolTipText("有"+num+"个文件正在下载.");
        this.repaint();
    }


}
