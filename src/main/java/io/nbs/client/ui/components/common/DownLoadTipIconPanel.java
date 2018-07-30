package io.nbs.client.ui.components.common;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.ui.components.GBC;
import io.nbs.commons.utils.IconUtil;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.components.common
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/17-10:23
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class DownLoadTipIconPanel extends JPanel {
    private JCirclePanel circlePanel;
    private JLabel textLabel;
    public DownLoadTipIconPanel(JCirclePanel circlePanel) {
        initComponents(circlePanel);
        initView();
    }

    private void initComponents(JCirclePanel circlePanel){
        textLabel = new JLabel("Download Task:");
        textLabel.setHorizontalAlignment(JLabel.RIGHT);
        textLabel.setFont(FontUtil.getDefaultFont(16));
        textLabel.setForeground(ColorCnst.FONT_ABOUT_TITLE_BLUE);
        textLabel.setOpaque(false);
        this.circlePanel = circlePanel;

    }

    private void initView(){
        setLayout(new FlowLayout(FlowLayout.RIGHT,0,2));
        this.add(textLabel);
        this.add(circlePanel);
    }

}
