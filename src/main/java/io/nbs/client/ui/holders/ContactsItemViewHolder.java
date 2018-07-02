package io.nbs.client.ui.holders;

import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.ui.components.GBC;
import com.nbs.ui.components.NbsBorder;
import io.nbs.client.ui.holders.ViewHolder;

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

    /**
     * 列表项内容设置
     */
    private void initComponents(){
        setPreferredSize(new Dimension(100,50));
        setBackground(ColorCnst.CONTACTS_ITEM_GRAY_MAIN);
        setBorder(new NbsBorder(NbsBorder.BOTTOM,ColorCnst.CONTACTS_ITEM_LINE_GRAY));
        setOpaque(true);
        setForeground(ColorCnst.FONT_WHITE);

        roomName.setFont(FontUtil.getDefaultFont(13));
        roomName.setForeground(ColorCnst.ITEM_SELECTED);
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
