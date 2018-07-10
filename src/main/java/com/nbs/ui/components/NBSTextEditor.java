package com.nbs.ui.components;

import io.nbs.commons.utils.ClipboardUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @Package : com.nbs.ui.components
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/26-21:40
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class NBSTextEditor extends JTextPane {

    /**
     *
     */
    @Override
    public void paste() {
        Object data = ClipboardUtil.paste();
        if(data instanceof String ){
            this.replaceSelection((String)data);
        }else if(data instanceof ImageIcon){
            ImageIcon icon = (ImageIcon)data;
            adjustAndInsertImage(icon);
        }

        super.paste();
    }

    private void adjustAndInsertImage(ImageIcon img){
        String path = img.getDescription();
        int iconW = img.getIconHeight();
        int iconH = img.getIconHeight();
        float scale = iconW*1.0F/iconH;
        boolean needToScale = false;
        int max =100;

        if(iconW>=iconH && iconW >max){
            iconW = max;
            iconH = (int)(iconW/scale);
            needToScale = true;
        }else if(iconH >= iconW && iconH > max){
            iconH = max;
            iconW = (int)(iconH*scale);
            needToScale = true;
        }
        JLabel label = new JLabel();
        if(needToScale){
            ImageIcon scaleIcon = new ImageIcon(img.getImage().getScaledInstance(iconW,iconH,Image.SCALE_SMOOTH));
            label.setIcon(scaleIcon);
        }else {
            label.setIcon(img);
        }

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //双击
               // super.mouseClicked(e);
            }
        });

        insertComponent(label);
    }
}
