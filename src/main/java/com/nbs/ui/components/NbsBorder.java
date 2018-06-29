package com.nbs.ui.components;

import javax.swing.border.Border;
import java.awt.*;

/**
 * @Package : com.nbs.ui.components
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/24-7:51
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class NbsBorder implements Border {

    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    private int orientation;
    private Color color;
    private float heightScale = 1.0F;

    public NbsBorder(int orientation) {
        this(orientation,ColorCnst.DARK);
    }

    public NbsBorder(int orientation, Color color) {
        this.orientation = orientation;
        this.color = color;
    }

    /**
     *
     * @param c
     * @param g
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(color);

        switch (this.orientation)
        {
            case TOP:
            {
                g.drawLine(x, 1, width, 1);
                break;
            }
            case BOTTOM:
            {
                g.drawLine(x, height - 1, width, height - 1);
                break;
            }
            case RIGHT:
            {
                int h = (int) (height * heightScale);
                g.drawLine(width - 1, y + h, width - 1, height - h);
                break;
            }

            case LEFT:
            {
                g.drawLine(x, y, x, height);
                break;
            }
        }
    }


    public void setHeightScale(float scale)
    {
        this.heightScale = scale;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(1,5,5,1);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
