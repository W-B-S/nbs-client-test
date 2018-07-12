package io.nbs.client.ui.panels.manage.holder;


import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.ui.components.LCAttachMessageBubble;
import io.nbs.client.ui.components.VerticalFlowLayout;
import io.nbs.client.ui.panels.manage.listener.FillDetailInfoListener;
import net.miginfocom.swing.MigLayout;

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

    public BLKAttachDataViewHolder(FillDetailInfoListener listener) {
        super.listener = listener;
        initComponents();
        initView();
        setListeners();
    }

    @Override
    public void setFillDetailListener( FillDetailInfoListener listener) {
        super.listener = listener;
    }

    private void initComponents(){
        messageBubble = new LCAttachMessageBubble();
        messageBubble.setLayout(new BorderLayout());
        srcPanel.setFont(FontUtil.getDefaultFont(14));
        srcPanel.setForeground(ColorCnst.FONT_GRAY_DARKER);
        sizeLabel.setHorizontalAlignment(JLabel.LEFT);
       // messageBubble.setCursor(MainFrame.handCursor);
    }

    /**
     *
     */
    private void initView(){
        this.setLayout(new BorderLayout());
        timePanel.add(time);
        /**
         * 消息布局
         */
        attachmentPanel.setLayout(new MigLayout("","[left]"));
        attachmentPanel.add(hashTitle,"growx ,gap 4,wrap");
        attachmentPanel.add(attachmentTitle,"growx,gap 4,wrap");
        attachmentPanel.add(progressBar,"growx,gaptop 0,wrap");
        attachmentPanel.add(sizeLabel,"split,span,growx,gaptop 0");
        attachmentPanel.add(blkNumLabel,"span,growx,gaptop 4,wrap");

        /**
         * 图标布局
         */
        iconInfoPanel.setLayout(new MigLayout("","[right]"));
        iconInfoPanel.add(attachIcon,"growx,span ,gaptop 10,wrap");
        //iconInfoPanel.add(new JLabel(),"split,span 2,growx,gaptop 20");
        iconInfoPanel.add(openBtn,"split,span,growx,gaptop 20");
        iconInfoPanel.add(downloadBtn,"span,growx,gapright 20,wrap");


        messageBubble.add(attachmentPanel,BorderLayout.CENTER);
        messageBubble.add(iconInfoPanel,BorderLayout.EAST);


        /**
         * 整体垂直布局
         */
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
