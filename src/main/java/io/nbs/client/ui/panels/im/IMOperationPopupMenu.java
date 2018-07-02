package io.nbs.client.ui.panels.im;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.ui.components.LCOperationMenuItemUI;
import io.nbs.commons.utils.IconUtil;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Package : io.ipfs.nbs.ui.panels.im
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/1-19:34
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IMOperationPopupMenu extends JPopupMenu {

    public IMOperationPopupMenu() {
        initMenuItems();
    }

    private void initMenuItems(){
        JMenuItem itemOnline = new JMenuItem("在线Peers");
        JMenuItem itemAll = new JMenuItem("All");

        itemOnline.setUI(new LCOperationMenuItemUI());

        itemOnline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });
        ImageIcon iconOnline = IconUtil.getIcon(this,"/icons/peers_active.png");
        iconOnline.setImage(iconOnline.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        itemOnline.setIcon(iconOnline);
        itemOnline.setIconTextGap(5);

        this.add(itemOnline);
        setBorder(new LineBorder(ColorCnst.SCROLL_BAR_TRACK_LIGHT));
        setBackground(ColorCnst.FONT_WHITE);
    }
}
