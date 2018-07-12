package io.nbs.client.ui.components;

import com.nbs.ui.components.NBS9PathImageIcon;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.ui.components.message.IMMessageBubble;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.commons.utils.IconUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @Package : io.nbs.client.ui.components
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-19:02
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class LCAttachMessageBubble extends JPanel implements IMMessageBubble {
    /**
     * 背景
     */
    private Icon currentBGIcon;
    private NinePatchImageIcon bgNormalIcon;
    private NinePatchImageIcon bgActiveIcon;

    public LCAttachMessageBubble() {
        setOpaque(false);
       // init();
        setListener();
    }

    private void init(){
        bgNormalIcon = new NinePatchImageIcon(this.getClass().getResource("/icons/left.9.png"));
        bgActiveIcon = new NinePatchImageIcon(this.getClass().getResource("/icons/left_active.9.png"));
        this.setCurrentBGIcon(bgNormalIcon);
    }

    @Override
    public Insets getInsets() {
        return new Insets(2,9,3,2);
    }

    private void setListener(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(currentBGIcon!=null){
            currentBGIcon.paintIcon(this,g,0,0);
        }
        super.paintComponent(g);
    }

    @Override
    public void setBackgroundIcon(Icon icon) {
        currentBGIcon = icon;
    }

    /**
     *
     * @param l
     */
    @Override
    public synchronized void addMouseListener(MouseListener l) {
        for(MouseListener listener : getMouseListeners()){
            if(listener == l){
                return;
            }
        }
        super.addMouseListener(l);
    }

    @Override
    public NBS9PathImageIcon getNBS9PathImageIcon() {
        return null;
    }

    public Icon getCurrentBGIcon() {
        return currentBGIcon;
    }

    public void setCurrentBGIcon(Icon currentBGIcon) {
        this.currentBGIcon = currentBGIcon;
    }

    public NinePatchImageIcon getBgNormalIcon() {
        return bgNormalIcon;
    }

    public void setBgNormalIcon(NinePatchImageIcon bgNormalIcon) {
        this.bgNormalIcon = bgNormalIcon;
    }

    public NinePatchImageIcon getBgActiveIcon() {
        return bgActiveIcon;
    }

    public void setBgActiveIcon(NinePatchImageIcon bgActiveIcon) {
        this.bgActiveIcon = bgActiveIcon;
    }
}
