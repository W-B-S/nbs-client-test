package io.nbs.client.ui.panels.manage.holder;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.ui.components.*;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.holders.ViewHolder;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @Package : io.nbs.client.ui.panels.manage.holder
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-17:00
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AttachDataViewHolder extends ViewHolder {
    public SizeAutoAdjustTextArea attachmentTitle;
    public LCProgressBar progressBar = new LCProgressBar();//进度条
    public JPanel timePanel = new JPanel();//时间面板
    public JPanel iconInfoPanel = new JPanel();//图标 组合面板
    public AttachmentPanel attachmentPanel = new AttachmentPanel();//附件
    public JLabel attachIcon = new JLabel();//附件类型
    public JLabel sizeLabel = new JLabel();
    public JLabel blkNumLabel = new JLabel();
    public JLabel time = new JLabel();

    public LCAttachMessageBubble messageBubble;

    public AttachDataViewHolder() {
        initComponents();
        setListeners();
    }

    /**
     *
     */
    private void initComponents(){
        int maxWidth = (int)(MainFrame.getContext().currentWindowWidth*0.427);
        attachmentTitle = new SizeAutoAdjustTextArea(maxWidth);

        iconInfoPanel.setBackground(ColorCnst.WINDOW_BACKGROUND);

        timePanel.setBackground(ColorCnst.WINDOW_BACKGROUND);
        time.setForeground(ColorCnst.FONT_GRAY_DARKER);
        time.setFont(FontUtil.getDefaultFont(12));



        attachmentPanel.setOpaque(false);

        progressBar.setMaximum(100);
        progressBar.setMinimum(0);
        progressBar.setValue(100);
        progressBar.setUI(new GradientProgressBarUI());
        progressBar.setVisible(false);

        sizeLabel.setFont(FontUtil.getDefaultFont(12));
        sizeLabel.setForeground(ColorCnst.FONT_ABOUT_TITLE_BLUE);
    }

    private void setListeners(){
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        };
        //
        attachmentPanel.addMouseListener(adapter);
        attachmentTitle.addMouseListener(adapter);
    }
}
