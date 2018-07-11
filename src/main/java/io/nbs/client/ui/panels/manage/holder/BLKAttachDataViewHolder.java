package io.nbs.client.ui.panels.manage.holder;


import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.ui.components.GBC;
import io.nbs.client.ui.components.LCAttachMessageBubble;
import io.nbs.client.ui.components.VerticalFlowLayout;
import io.nbs.client.ui.frames.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.panels.manage.holder
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-17:07
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class BLKAttachDataViewHolder extends AttachDataViewHolder {

    public JLabel srcPanel = new JLabel();


    public BLKAttachDataViewHolder() {
        initComponents();
        initView();
        setListeners();
    }

    private void initComponents(){
        messageBubble = new LCAttachMessageBubble();
        srcPanel.setFont(FontUtil.getDefaultFont(12));
        srcPanel.setForeground(ColorCnst.FONT_GRAY_DARKER);

        messageBubble.setCursor(MainFrame.handCursor);
    }

    /**
     *
     */
    private void initView(){
        this.setLayout(new BorderLayout());
        timePanel.add(time);

        attachmentPanel.setLayout(new GridBagLayout());
        attachmentPanel.add(attachIcon
                ,new GBC(0,0).setWeight(1,1).setInsets(5,5,5,0));

        attachmentPanel.add(attachmentTitle
        ,new GBC(1,0).setWeight(100,1).setAnchor(GBC.NORTH).setInsets(5,8,5,5));
        attachmentPanel.add(progressBar
                ,new GBC(1,1).setWeight(1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.SOUTH).setInsets(0,8,5,5));
        attachmentPanel.add(sizeLabel
        ,new GBC(1,1).setWeight(1,1).setFill(GBC.HORIZONTAL).setAnchor(GBC.SOUTH).setInsets(-20,8,3,0));

        messageBubble.add(attachmentPanel);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(ColorCnst.WINDOW_BACKGROUND_LIGHT);
        contentPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,0,0,true,false));
        contentPanel.add(srcPanel);

        contentPanel.add(messageBubble);

        this.add(timePanel,BorderLayout.NORTH);
        this.add(contentPanel,BorderLayout.CENTER);
    }

    private void setListeners(){

    }

}
