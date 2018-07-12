package io.nbs.client.ui.components;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;

/**
 * @Package : io.nbs.client.ui.components
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/12-20:13
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class LCAutoJTextPanel extends JIMSendTextPane{
    public LCAutoJTextPanel() {
        initView();
    }
    public LCAutoJTextPanel(String text) {
        setText(text);
        initView();
    }


    private void initView(){
        this.setBackground(ColorCnst.WINDOW_BACKGROUND);
        this.setFont(FontUtil.getDefaultFont(14));
        this.setForeground(ColorCnst.DARKER);
        this.setEditable(false);

    }
}
