package io.nbs.client.ui.components;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.commons.utils.IconUtil;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : io.nbs.client.ui.components
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/8-22:34
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class SearchButtonPanel extends JPanel {

    private JLabel iconLabel;
    private JLabel titleLabel;

    public SearchButtonPanel(String title) {
        if(StringUtils.isBlank(title))title = "SEARCH";
        iconLabel = new JLabel();
        titleLabel = new JLabel(title);
        titleLabel.setMinimumSize(new Dimension(80,26));
        iconLabel.setIcon(IconUtil.getIcon(this,"/icons/tools/search_ic.png"));

        initVeiw();
    }

    private void initVeiw(){
        setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
        this.setBackground(ColorCnst.SEEARCH_ITEM_GRAY_LIGHT);
        add(iconLabel);
        add(titleLabel);
    }

    public void setIcon(ImageIcon icon){
        this.iconLabel.setIcon(icon);
    }
}
