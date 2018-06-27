package com.nbs.ui.components;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * @Package : com.nbs.ui.components
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/27-0:38
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class NBS9PathImageIcon extends ImageIcon {

    public NBS9PathImageIcon(String filename) {
        super(filename);
    }

    public NBS9PathImageIcon(URL location) {
        super(location);
    }

    public synchronized void paintIcon(Component c, Graphics g, int x, int y){
        int iCompWidth = c.getWidth();
        int iCompHeigth = c.getHeight();
    }
}
