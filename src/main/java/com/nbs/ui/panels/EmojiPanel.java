package com.nbs.ui.panels;

import io.ipfs.nbs.cnsts.ColorCnst;
import com.nbs.ui.listener.ExpressionListener;
import io.ipfs.nbs.utils.IconUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @Package : com.nbs.ui.panels
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/26-23:09
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class EmojiPanel extends JPanel {
    private ExpressionListener listener;
    private JPopupMenu parentPopup;

    public EmojiPanel() {
        initComponents();
        initView();
        initData();
    }

    private void initComponents(){
        this.setLayout(new GridLayout(8,10,3,0));
    }

    private void initView(){

    }

    private void initData(){

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ExpressionItem panel = (ExpressionItem)e.getSource();
                if(listener != null){
                    listener.onSelected(panel.getCode());
                    if(parentPopup != null){
                        parentPopup.setVisible(false);
                    }
                }
                super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JPanel panel = (JPanel)e.getSource();
                panel.setBackground(ColorCnst.SCROLL_BAR_TRACK_LIGHT);
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JPanel panel = (JPanel)e.getSource();
                panel.setBackground(ColorCnst.WINDOW_BACKGROUND);
                super.mouseExited(e);
            }
        };

        String[] codeList = new String[]{
                ":smile:",":blush:",":confused:",":anguished:",":cold_sweat:",":astonished:",":cry:",":joy:",
                ":disappointed_relieved:",":disappointed:",":anguished:",":confounded:",":angry:",":dizzy_face:",":expressionless:",":fearful:",
                ":flushed:",":frowning:",":grin:",":heart_eyes:",":heart_eyes_cat:",":hushed:",":imp:",":innocent:",
                ":kissing_closed_eyes:",":kissing_heart:",":laughing:",":neutral_face:",":no_mouth:",":open_mouth:",":pensive:",":persevere:",
                ":rage:",":relaxed:",":relieved:",":scream:",":sleeping:",":broken_heart:",":smirk:",":sob:",
                ":stuck_out_tongue_closed_eyes:",":sunglasses:",":sweat_smile:",":sweat:",":triumph:",":unamused:",":wink:",":yum:",
                ":cat:",":dog:",":bear:",":chicken:",":cow:",":ghost:",":hear_no_evil:",":koala:",
                ":mouse:",":airplane:",":ambulance:",":bike:",":bullettrain_side:",":bus:",":metro:",":oncoming_taxi:",
                ":walking:",":apple:",":banana:",":beer:",":birthday:",":cake:",":cherries:",":tada:",
                ":clap:",":fist:",":ok_hand:",":pray:",":thumbsup:",":thumbsdown:",":muscle:",":v:"
        };

        String iconPath = "/emoji/";
        for(int i = 0;i<codeList.length;i++){
            String name = codeList[i].substring(1,codeList[i].length()-1);
            JPanel panel = new ExpressionItem(codeList[i],IconUtil.getIcon(this,iconPath + name+".png"),name);
            add(panel);
        }

    }

    /**
     *
     * @param listener
     * @param popupMenu
     */
    public void setListener(ExpressionListener listener,JPopupMenu popupMenu) {
        this.listener = listener;
        this.parentPopup = popupMenu;
    }
}
