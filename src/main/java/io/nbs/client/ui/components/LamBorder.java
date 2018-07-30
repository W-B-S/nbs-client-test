package io.nbs.client.ui.components;


import io.nbs.client.cnsts.ColorCnst;

import java.awt.*;

/**
 * @Package : io.ipfs.nbs.ui.components
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-18:38
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class LamBorder implements javax.swing.border.Border {
    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    private int orientation;
    private Color color;
    private float heightScale = 1.0F;

    /**
     *
     * @param orientation
     */
    public LamBorder(int orientation) {
        this(orientation,ColorCnst.DARKER);
    }

    public LamBorder(int orientation, Color color) {
        this.orientation = orientation;
        this.color = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(color);
        switch (this.orientation){
            case TOP:
                g.drawLine(x, 1, width, 1);
                break;
            case BOTTOM:
                g.drawLine(x, height - 1, width, height - 1);
                break;
            case RIGHT:
                int h = (int) (height * heightScale);
                g.drawLine(width - 1, y + h, width - 1, height - h);
                break;
            case LEFT:
                g.drawLine(x, y, x, height);
                break;
        }
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(1,5,5,1);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    public void setHeightScale(float heightScale) {
        this.heightScale = heightScale;
    }
}
