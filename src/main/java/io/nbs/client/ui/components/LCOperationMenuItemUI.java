package io.nbs.client.ui.components;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;

import javax.swing.*;
import javax.swing.plaf.basic.BasicMenuItemUI;
import java.awt.*;

/**
 * @Package : io.ipfs.nbs.ui.components
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/1-19:39
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class LCOperationMenuItemUI extends BasicMenuItemUI {
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        c.setBackground(ColorCnst.FONT_WHITE);
        c.setPreferredSize(new Dimension(150,40));
        c.setFont(FontUtil.getDefaultFont(13));
        c.setBorder(new LamBorder(LamBorder.BOTTOM,ColorCnst.LIGHT_GRAY));
        selectionForeground = ColorCnst.FONT_BLACK;
        selectionBackground = ColorCnst.SCROLL_BAR_TRACK_LIGHT;
    }

    @Override
    protected void paintText(Graphics g, JMenuItem menuItem, Rectangle textRect, String text) {
        super.paintText(g, menuItem, textRect, text);
        int x = (int) ((menuItem.getSize().getWidth() - textRect.width) / 2);

        g.setColor(ColorCnst.FONT_BLACK);
        Rectangle newRect =  new Rectangle(x, textRect.y - 1 , textRect.width, textRect.height);
        super.paintText(g, menuItem, newRect, text);
    }
}
