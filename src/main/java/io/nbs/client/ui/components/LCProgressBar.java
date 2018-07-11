package io.nbs.client.ui.components;

import io.nbs.client.cnsts.ColorCnst;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.components
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-17:14
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class LCProgressBar extends JProgressBar {

    public LCProgressBar() {
        setForeground(ColorCnst.PROGRESS_BAR_START);
        setBorder(new LineBorder(ColorCnst.PROGRESS_BAR_END));
    }

    @Override
    protected void paintBorder(Graphics g) {
        super.paintBorder(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(),6);
    }
}
