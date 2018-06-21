package UI.templete;

import UI.ConstantsUI;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : UI.templete
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/19-14:56
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class WihteBackJPanel extends JPanel {
    public WihteBackJPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        initSet();
    }

    public WihteBackJPanel(LayoutManager layout) {
        super(layout);
        initSet();
    }

    public WihteBackJPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        initSet();
    }

    public WihteBackJPanel() {
        super();
        initSet();
    }

    private void initSet(){
        this.setFont(ConstantsUI.FONT_NORMAL);
        this.setBackground(ConstantsUI.MAIN_BACK_COLOR);
    }
}
