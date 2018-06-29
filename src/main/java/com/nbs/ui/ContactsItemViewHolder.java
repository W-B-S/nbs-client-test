package com.nbs.ui;

import com.nbs.ui.cnst.FontUtil;
import com.nbs.ui.components.ColorCnst;
import com.nbs.ui.components.GBC;
import com.nbs.ui.components.NbsBorder;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : com.nbs.ui
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/24-7:47
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ContactsItemViewHolder extends ViewHolder {
    public JLabel avatar = new JLabel();
    public JLabel roomName = new JLabel();

    public ContactsItemViewHolder() {
        initComponents();
        initView();
    }

    private void initComponents(){
        setPreferredSize(new Dimension(100,50));
        setBackground(ColorCnst.DARK);
        setBorder(new NbsBorder(NbsBorder.BOTTOM));
        setOpaque(true);
        setForeground(ColorCnst.FONT_WHITE);

        roomName.setFont(FontUtil.getDefaultFont(13));
        roomName.setForeground(ColorCnst.FONT_WHITE);
    }

    /**
     *
     */
    private void initView()
    {
        setLayout(new GridBagLayout());
        add(avatar, new GBC(0, 0).setWeight(1, 1).setFill(GBC.BOTH).setInsets(0,5,0,0));
        add(roomName, new GBC(1, 0).setWeight(10, 1).setFill(GBC.BOTH));
    }
}
