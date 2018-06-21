package UI.panel.setting;

import UI.ConstantsUI;
import UI.common.NBSAbstractPanel;
import UI.common.ToolbarStatsPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

/**
 * @Package : UI.panel
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/13-16:27
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class SettingPanel extends NBSAbstractPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(SettingPanel.class);
    public static final String PKUI_PANEL_STATUS_LABEL = "nbs.ui.panel.setting.label";

    public SettingPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    @Override
    protected void addComponent() {
        //TOP title
        ToolbarStatsPanel toolbarStatsPanel = new ToolbarStatsPanel(PKUI_PANEL_STATUS_LABEL);
        this.add(toolbarStatsPanel,BorderLayout.NORTH);
        this.add(buildMain(),BorderLayout.CENTER);

    }

    @Override
    protected void addListener() {

    }

    @Override
    public void load() {

    }

    private JPanel buildMain(){
        JPanel center = new JPanel();
        center.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        center.setLayout(new BorderLayout());

        JLabel label = new JLabel("ID");
        JLabel peerID = new JLabel("123");

        return center;
    }
}
