package UI.panel.im;

import UI.common.NBSAbstractPanel;
import UI.common.ToolbarStatsPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : UI.panel.im
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/15-13:10
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IMPanel extends NBSAbstractPanel {
    private static final long serialVersionUID = 1L;
    public static final String PKUI_PANEL_IM_LABEL = "nbs.ui.panel.im.label";


    public IMPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    @Override
    protected void init() {
        super.init();

    }

    @Override
    protected void addComponent() {
        ToolbarStatsPanel toolbarStatsPanel = new ToolbarStatsPanel(PKUI_PANEL_IM_LABEL);
        this.add(toolbarStatsPanel,BorderLayout.NORTH);
    }

    @Override
    public void load() {

    }

    @Override
    protected void addListener() {

    }
}
