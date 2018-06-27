package com.nbs.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @Package : com.nbs.ui.components
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/27-0:41
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class NBSAttachmentMessageBubble extends JPanel implements NBSMessageBubble {

    private NBS9PathImageIcon normal;
    private NBS9PathImageIcon active;

    public NBSAttachmentMessageBubble() {
        setOpaque(false);
        setListener();
    }

    /**
     * 当前背景
     */
    private Icon currentBGIcon;

    @Override
    public void setBackgroundIcon(Icon icon) {
        currentBGIcon = icon;
    }

    @Override
    public NBS9PathImageIcon getNBS9PathImageIcon() {
        return null;
    }

    public void setCurrentBGIcon(Icon background) {
        this.currentBGIcon = background;
    }

    private void setListener(){
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                setActiveStatus(true);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                setActiveStatus(false);
            }
        });
    }

    public void setActiveStatus(boolean status)
    {
        if (status)
        {
            setBackgroundIcon(active);
        }
        else
        {
            setBackgroundIcon(normal);
        }

        NBSAttachmentMessageBubble.this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(currentBGIcon != null){
            currentBGIcon.paintIcon(this,g,0,0);
        }
        super.paintComponent(g);
    }

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        for (MouseListener listener : getMouseListeners())
        {
            if (listener == l)
            {
                return;
            }
        }
        super.addMouseListener(l);
    }

    public NBS9PathImageIcon getNormal() {
        return normal;
    }

    public void setNormal(NBS9PathImageIcon normal) {
        this.normal = normal;
    }

    public NBS9PathImageIcon getActive() {
        return active;
    }

    public void setActive(NBS9PathImageIcon active) {
        this.active = active;
    }
}
