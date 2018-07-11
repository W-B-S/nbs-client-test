package io.nbs.client.ui.components.forms;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;

import javax.swing.*;

/**
 * @Package : io.nbs.client.ui.components.forms
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-15:21
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class LCJTextArea extends JTextArea {

    public LCJTextArea(String content) {
        setText(content);
        initComponents();
    }

    public LCJTextArea(String ...agrs) {
        if(agrs.length>0){
            StringBuffer sb = new StringBuffer();
            for(String s : agrs){
                sb.append(s);
            }
            setText(sb.toString());
        }
        initComponents();
    }

    private void initComponents(){
        setLineWrap(true);
        setEditable(false);
        setFont(FontUtil.getDefaultFont(12));
        setForeground(ColorCnst.ITEM_SELECTED);
    }

    /**
     *
     * @param agrs
     */
    public LCJTextArea appendText(String ...agrs){
        if(agrs.length>0){
            StringBuffer sb = new StringBuffer();
            sb.append(getText());
            for(String s: agrs){
                sb.append(s);
            }
            setText(sb.toString());
        }
        return this;
    }
}
