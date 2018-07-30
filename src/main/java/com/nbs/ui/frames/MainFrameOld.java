package com.nbs.ui.frames;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * @Package : com.nbs.ui.frames
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/27-0:15
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
@Deprecated
public class MainFrameOld extends JFrame {
    public static int DEFAULT_WIDTH = 885;
    public static int DEFAULT_HEIGHT = 636;
    public int currentWindowWidth = DEFAULT_WIDTH;
    public int currentWindowHeight = DEFAULT_HEIGHT;

    private static MainFrameOld context;

    public static MainFrameOld getContext() {
        return context;
    }

    public MainFrameOld(){
        context = this;
        setListeners();
    }

    private void setListeners(){
        addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e) {
                currentWindowWidth = (int)e.getComponent().getBounds().getWidth();
                currentWindowHeight = (int)e.getComponent().getBounds().getHeight();
            }
        });
    }
}
