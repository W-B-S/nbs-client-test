package io.nbs.client.ui.components.message;

import com.android.ninepatch.NinePatch;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * @Package : io.nbs.client.ui.components.message
 * @Description :
 * <p>
 * 9
 * </p>
 * @Author : lambor.c
 * @Date : 2018/7/2-21:21
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IM9PatchImageIcon extends ImageIcon {
    private static final long serialVersionUID = 1L;
    private NinePatch mNinePatch;

    public IM9PatchImageIcon(URL location) {
        try{
            this.mNinePatch = NinePatch.load(location,true);
        }catch (IOException e){
            e.printStackTrace();
            return;
        }
    }

    /**
     *
     * @param c
     * @param g
     * @param x
     * @param y
     */
    @Override
    public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
        int iWidth = c.getWidth();
        int iHeight = c.getHeight();
        mNinePatch.draw((Graphics2D) g,0,0,iWidth,iHeight);
    }
}
