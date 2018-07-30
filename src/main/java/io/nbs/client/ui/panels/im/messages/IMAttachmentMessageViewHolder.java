package io.nbs.client.ui.panels.im.messages;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.ui.components.AttachmentPanel;
import io.nbs.client.ui.components.NBSButton;
import io.nbs.client.ui.components.SizeAutoAdjustTextArea;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.holders.BaseMessageViewHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @Package : io.nbs.client.ui.panels.im.messages
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/13-16:13
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IMAttachmentMessageViewHolder extends BaseMessageViewHolder {
    public SizeAutoAdjustTextArea attachTitle;//文件名
    public JPanel timePanel = new JPanel();
    public AttachmentPanel attachmentPanel = new AttachmentPanel();//附件面板
    public JPanel messageAvatarPanel = new JPanel();//头像和消息组合
    public JPanel toolsPanel = new JPanel();//按钮区
    public JLabel attachmentIcon = new JLabel(); // 附件类型icon

    public JLabel sizeLabel = new JLabel();
    public NBSButton openBrowserButton;
    public NBSButton pinedIPFSButton;

    public JPanel messageBubble;


    public IMAttachmentMessageViewHolder() {
        initComponents();
        setListeners();
    }

    /**
     * 初始化
     */
    private void initComponents(){
        /*------------------------------------------------------------------------- */
        /* timePanel                                                                */
        /* titleContent                                                     avatars */
        /*                                                                  avatars */
        /*                                                                  avatars */
        /* sizeLabel                            openBrowser addIPFS         avatars */
        /*------------------------------------------------------------------------- */
        int maxWidth = (int)(MainFrame.getContext().currentWindowWidth*0.415);
        attachTitle = new SizeAutoAdjustTextArea(maxWidth);
        timePanel.setBackground(ColorCnst.WINDOW_BACKGROUND);
        messageAvatarPanel.setBackground(ColorCnst.WINDOW_BACKGROUND);

        time.setForeground(ColorCnst.FONT_GRAY_DARKER);
        time.setFont(FontUtil.getDefaultFont(12));


        attachmentPanel.setOpaque(false);

        sizeLabel.setForeground(ColorCnst.FONT_GRAY);
        sizeLabel.setFont(FontUtil.getDefaultFont(12));

        toolsPanel.setBackground(ColorCnst.WINDOW_BACKGROUND);
        toolsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,4,2));

        openBrowserButton = new NBSButton("浏览器中查看");
        Dimension btnSize = new Dimension(45,26);
        openBrowserButton.setMaximumSize(btnSize);
        pinedIPFSButton = new NBSButton("添加缓存任务");
        pinedIPFSButton.setMaximumSize(btnSize);

        toolsPanel.add(openBrowserButton);
        toolsPanel.add(pinedIPFSButton);

    }

    private void setListeners(){
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        };


    }
}
