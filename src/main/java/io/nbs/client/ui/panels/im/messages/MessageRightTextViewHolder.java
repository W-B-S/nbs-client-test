package io.nbs.client.ui.panels.im.messages;

import io.nbs.client.Launcher;
import io.nbs.client.ui.components.NBSIconButton;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.ui.components.GBC;
import io.nbs.client.ui.components.SizeAutoAdjustTextArea;
import com.nbs.ui.components.messages.IMRightImageMessageBubble;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.holders.BaseMessageViewHolder;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : com.nbs.ui.holder
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/26-23:48
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MessageRightTextViewHolder extends BaseMessageViewHolder {


    public SizeAutoAdjustTextArea text;
    /**
     * TODO
     */
    public ImageIcon defaltIcon = new ImageIcon("/icons/nbs128.png");
    public NBSIconButton messageButton = new NBSIconButton(defaltIcon,defaltIcon,"");
    public JLabel resend = new JLabel(); // 重发按钮
    public JLabel sendingProgress = new JLabel(); // 正在发送

    private JPanel timePanel = new JPanel();
    private JPanel messageAvatarPanel = new JPanel();

    public IMRightImageMessageBubble messageBubble = new IMRightImageMessageBubble();

    public MessageRightTextViewHolder(){
       // defaltIcon.setImage(defaltIcon.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
        initComponents();
        initView();
    }

    private void initComponents(){
        timePanel.setBackground(ColorCnst.WINDOW_BACKGROUND);
        messageAvatarPanel.setBackground(ColorCnst.WINDOW_BACKGROUND);

        int maxWidth = (int)(MainFrame.getContext().currentWindowHeight*0.5);
        text = new SizeAutoAdjustTextArea(maxWidth);
        text.setParseUrl(true);

        time.setForeground(ColorCnst.FONT_GRAY);
        time.setFont(FontUtil.getDefaultFont(12));
       // resend.setIcon(resendIcon);
        resend.setVisible(false);
        resend.setToolTipText("消息发送失败.");
        resend.setCursor(new Cursor(Cursor.HAND_CURSOR));

        text.setCaretPosition(text.getDocument().getLength());
    }

    private void initView(){
        setLayout(new BorderLayout());

        timePanel.add(time);

        avatar.setIcon(defaltIcon);
        avatar.setVisible(true);

        messageBubble.add(text,BorderLayout.CENTER);

        //JPanel res
        JPanel resendTextPanel = new JPanel();
        resendTextPanel.setBackground(ColorCnst.WINDOW_BACKGROUND);
        resendTextPanel.add(resend, BorderLayout.WEST);
        resendTextPanel.add(sendingProgress, BorderLayout.WEST);
        resendTextPanel.add(messageBubble, BorderLayout.CENTER);

        messageAvatarPanel.setLayout(new GridBagLayout());

        //messageAvatarPanel.setBorder(MainFrame.redBorder);
        messageAvatarPanel.add(resendTextPanel,
                new GBC(1,0).setWeight(1000,1).setAnchor(GBC.EAST).setInsets(0,0,5,0));
        messageAvatarPanel.add(avatar,new GBC(2,0).setWeight(1,1).setAnchor(GBC.NORTH).setInsets(5,0,0,10));
        add(timePanel,BorderLayout.NORTH);//
        add(messageAvatarPanel,BorderLayout.CENTER);
    }

}
