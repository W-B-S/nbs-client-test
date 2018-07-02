package io.nbs.client.ui.components;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * @Package : io.ipfs.nbs.ui.components
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/1-21:44
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class SearchTextField extends JTextField {
    private RoundRectangle2D shape;

    public SearchTextField() {
        setBorder(null);
        setBackground(ColorCnst.SEEARCHTEXT_ITEM_GRAYBG);
        setForeground(ColorCnst.FONT_WHITE);
       // setCaretColor(ColorCnst.CONTACTS_ITEM_GRAY);
        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    @Override
    public boolean contains(int x, int y) {
        shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight() , 15 , 15) ;
        return shape.contains(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(30, 30, 30, 100));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);

        if (getText().isEmpty()/* && !(FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)*/)
        {
            g2.setBackground(Color.gray);
            g2.setFont(FontUtil.getDefaultFont());
            g2.setColor(Color.GRAY);
            g2.drawString("搜索 ", 10, 20);
            g2.dispose();
        }

    }
}
