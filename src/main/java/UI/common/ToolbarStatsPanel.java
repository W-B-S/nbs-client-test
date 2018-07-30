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
    private final JTextPane contactsToLabel = new JTextPane();
    public ToolbarStatsPanel(String key){
        titleLabel.setFont(ConstantsUI.FONT_TITLE);
        titleLabel.setForeground(ConstantsUI.PANEL_TITILE_COLOR);
        contactsToLabel.setFont(ConstantsUI.FONT_LABEL);
        contactsToLabel.setForeground(ConstantsUI.PANEL_TITILE_COLOR);
       // contactsToLabel.set
        if(StringUtils.isNoneBlank(key)&& null != PropertyUtil.getProperty(key)){
            titleLabel.setText(PropertyUtil.getProperty(key));
        }
        this.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        this.setLayout(new FlowLayout(FlowLayout.LEFT,4,5));
        this.add(titleLabel);
        this.add(contactsToLabel);
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

    /**
     *
     * @param contents
     */
    public void resetContacts(String contents){
        if(StringUtils.isBlank(contents))contents = "";
        contactsToLabel.setText(contents);
        contactsToLabel.updateUI();
        contactsToLabel.validate();
    }
}
