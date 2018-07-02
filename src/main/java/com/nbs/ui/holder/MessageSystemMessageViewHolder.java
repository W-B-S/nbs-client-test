package com.nbs.ui.holder;

import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.ui.components.VerticalFlowLayout;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : com.nbs.ui.holder
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/27-3:42
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MessageSystemMessageViewHolder extends  BaseMessageViewHolder{
    public JLabel text = new JLabel();
    private JPanel timePanel = new JPanel();
    private JPanel textPanel;

    public MessageSystemMessageViewHolder() {
        avatar =null;
        initComponents();
        initView();
    }

    private void initComponents()
    {
        setBackground(ColorCnst.WINDOW_BACKGROUND);
        timePanel.setBackground(ColorCnst.WINDOW_BACKGROUND);

        time.setForeground(ColorCnst.FONT_GRAY);
        time.setFont(FontUtil.getDefaultFont(12));
        text.setHorizontalTextPosition(SwingConstants.CENTER);
        text.setFont(FontUtil.getDefaultFont(12));
        textPanel = new JPanel()
        {
            @Override
            public Insets getInsets()
            {
                return new Insets(-3, 0, -3, 0);
            }

            public void paint(Graphics g)
            {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(195, 195, 195));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                g2d.setColor(new Color(248, 248, 248));
                FontMetrics fm = getFontMetrics(getFont());
                int x = (getWidth() - fm.stringWidth(text.getText())) / 2;
                g2d.drawString(text.getText(), x, fm.getHeight() - 1);
                g2d.dispose();
            }
        };
        textPanel.setFont(FontUtil.getDefaultFont(12));
    }

    private void initView()
    {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.CENTER, 0, 0, true, false));
        timePanel.add(time);
        textPanel.add(text);
        contentPanel.add(timePanel);
        contentPanel.add(textPanel);

        add(contentPanel);
    }
}
