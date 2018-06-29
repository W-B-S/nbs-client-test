package com.nbs.ui.components;

import io.ipfs.nbs.cnsts.ColorCnst;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * @Package : com.nbs.ui.components
 * @Description :
 * <p>
 *     自定义滚动条
 * </p>
 * @Author : lambor.c
 * @Date : 2018/6/22-10:47
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ScrollUI extends BasicScrollBarUI {

    private Color thumbColor;
    private Color trackColor;

    /**
     *
     * @param thumbColor
     * @param trackColor
     */
    public ScrollUI(Color thumbColor, Color trackColor) {
        this.thumbColor = thumbColor;
        this.trackColor = trackColor;
    }

    /**
     *
     */
    public ScrollUI() {
        this.thumbColor = ColorCnst.SCROLL_BAR_THUMB;
        this.trackColor = ColorCnst.DARK;
    }

    public void setThumbColor(Color thumbColor) {
        this.thumbColor = thumbColor;
    }

    public void setTrackColor(Color trackColor) {
        this.trackColor = trackColor;
    }

    @Override
    protected void configureScrollBarColors() {
        //滑道
        setThumbBounds(0,0,3,10);
    }

    /**
     * @Date    : 2018/6/22 10:55
     * @Author  : lanbery
     * @Decription :
     * <p>设置滚动条宽度</p>
     * @Param   :
     * @return
     * @throws
     */
    @Override
    public Dimension getPreferredSize(JComponent c) {
        c.setPreferredSize(new Dimension(7,0));
        return super.getPreferredSize(c);
    }

    /**
     * 重绘滑块的滑动区域背景
     * @param g
     * @param c
     * @param trackBounds
     */
    public void paintTrack(Graphics g,JComponent c,Rectangle trackBounds){
        Graphics2D gcs2 = (Graphics2D)g;
        GradientPaint gp = null;

        if(this.scrollbar.getOrientation() == JScrollBar.VERTICAL){
            //水平滚动
            gp = new GradientPaint(0,0,trackColor,trackBounds.width,0,trackColor);
        }
        if(this.scrollbar.getOrientation() == JScrollBar.HORIZONTAL){
            gp = new GradientPaint(0,0,trackColor,trackBounds.height,0,trackColor);
        }

        gcs2.setPaint(gp);
        gcs2.fillRect(trackBounds.x,trackBounds.y,trackBounds.width,trackBounds.height);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.translate(thumbBounds.x,thumbBounds.y);
        g.setColor(thumbColor);

        Graphics2D g2 = (Graphics2D)g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.addRenderingHints(rh);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f));
        //填充圆角矩形
        g2.fillRoundRect(0,0,7,thumbBounds.height-1,5,5);
       // super.paintThumb(g, c, thumbBounds);
    }


}
