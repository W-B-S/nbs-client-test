package io.nbs.client.ui.components.forms;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.components.forms
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-23:37
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class LCFormLabel extends JPanel {
    private JLabel label;
    private LCJTextArea textArea;

    public LCFormLabel(String labelText) {
        initComponents();
        this.label.setText(labelText);
    }

    public LCFormLabel(String labelText,String text) {
        initComponents();
        this.label.setText(labelText);
        this.textArea.setText(text);
    }

    private void initComponents(){

        label = new JLabel();
        textArea = new LCJTextArea();
        label.setHorizontalAlignment(JLabel.RIGHT);
        label.setFont(FontUtil.getDefaultFont(12));
        label.setForeground(ColorCnst.ITEM_SELECTED);
        this.setLayout(new BorderLayout());
        this.add(label,BorderLayout.WEST);
        this.add(textArea,BorderLayout.CENTER);
        this.setOpaque(false);
    }

    public void setVolumeText(String text){
        this.textArea.setText(text);
    }

    public void setHorizontalAlignment(int horizontalAlignment){
        this.label.setHorizontalAlignment(horizontalAlignment);
    }
}
