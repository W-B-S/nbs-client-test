package com.nbs.ui.panels;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : com.nbs.ui.panels
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/26-23:18
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ExpressionItem extends JPanel {
    protected String code;
    protected ImageIcon icon;
    protected String displayName;
    protected Dimension size;

    private JLabel iconLabel;

    public ExpressionItem(String code, ImageIcon icon, String displayName) {
        this.code = code;
        this.icon = icon;
        this.displayName = displayName;
        setPreferredSize(new Dimension(30,30));

        iconLabel = new JLabel();
        setIconPreferredSize(new Dimension(20,20));
        iconLabel.setIcon(icon);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(iconLabel);
        this.setToolTipText(displayName);
    }

    public void setIconPreferredSize(Dimension size)
    {
        iconLabel.setPreferredSize(size);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public Dimension getSize() {
        return size;
    }

    @Override
    public void setSize(Dimension size) {
        this.size = size;
    }
}
