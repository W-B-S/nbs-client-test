package UI.common;

import UI.ConstantsUI;
import com.nbs.tools.PropertyUtil;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : UI.common
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/16-23:29
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ToolbarStatsPanel extends JPanel {

    private final JLabel titleLabel = new JLabel();
    public ToolbarStatsPanel(String key){
        titleLabel.setFont(ConstantsUI.FONT_TITLE);
        titleLabel.setForeground(ConstantsUI.PANEL_TITILE_COLOR);
        if(StringUtils.isNoneBlank(key)&& null != PropertyUtil.getProperty(key)){
            titleLabel.setText(PropertyUtil.getProperty(key));
        }
        this.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        this.setLayout(new FlowLayout(FlowLayout.LEFT,ConstantsUI.MAIN_H_GAP,5));
        this.add(titleLabel);
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title){
        if(title==null)title="";
        this.titleLabel.setText(title);
        this.updateUI();
    }

}
