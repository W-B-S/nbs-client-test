package io.nbs.client.ui.components;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.panels.info.bitswap
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/10-21:55
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class LCFromLabel extends JLabel {
    private  Font DEF_LABEL_FONT = FontUtil.getDefaultFont(15,Font.PLAIN);
    private  Font DEF_VOL_FONT = FontUtil.getDefaultFont(13);
    private boolean isLabel = true;

    public LCFromLabel() {
        setStyle();
    }

    public LCFromLabel(String label) {
        setStyle();
        setText(label);
    }

    private void setStyle(){
        setOpaque(false);

        if(isLabel){
            setHorizontalAlignment(JLabel.RIGHT);
            setFont(DEF_LABEL_FONT);
            setForeground(ColorCnst.FONT_GRAY);
        }else {
            setHorizontalAlignment(JLabel.LEFT);
            setFont(DEF_VOL_FONT);
            setForeground(ColorCnst.FONT_ABOUT_TITLE_BLUE);
        }
    }

    public LCFromLabel(Font DEF_LABEL_FONT, Font DEF_VOL_FONT, boolean isLabel) {
        this.DEF_LABEL_FONT = DEF_LABEL_FONT;
        this.DEF_VOL_FONT = DEF_VOL_FONT;
        this.isLabel = isLabel;
        setStyle();
    }

    public LCFromLabel(boolean isLabel) {
        this.isLabel = isLabel;
        setStyle();
    }

    public void setLabel(boolean label) {
        isLabel = label;
    }
}
