/*
 *   Copyright © 2018, lanbery,Lambor Co,. Ltd. All Rights Reserved.
 *
 *   Copyright Notice
 *   lanbery copyrights this specification. No part of this specification may be reproduced in any form or means, without the prior written consent of lanbery.
 *
 *   Disclaimer
 *   This specification is preliminary and is subject to change at any time without notice. Lambor assumes no responsibility for any errors contained herein.
 *
 */

package UI.panel.fm;

import UI.ConstantsUI;
import com.nbs.tools.PropertyUtil;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;

public class LogFlowJText extends JTextArea {

    public LogFlowJText() {

        initSet();
    }

    public LogFlowJText(String text) {
        super(text);
        initSet();
    }
    public LogFlowJText(String text,int height) {
        super(text);
        initSet(height);
    }

    public LogFlowJText(int rows, int columns) {
        super(rows, columns);
        initSet();
    }

    public LogFlowJText(String text, int rows, int columns) {
        super(text, rows, columns);
        initSet();
    }

    public LogFlowJText(Document doc) {
        super(doc);
        initSet();
    }

    public LogFlowJText(Document doc, String text, int rows, int columns) {
        super(doc, text, rows, columns);
        initSet();
    }

    private void initSet(){
        this.setPreferredSize(new Dimension(ConstantsUI.MAIN_WINDOW_WIDTH*3/10-6,36));
        this.setEditable(false);
        this.setForeground(ConstantsUI.MAIN_BACK_COLOR);
        this.setBackground(ConstantsUI.COMMAND_BACK_COLOR);
        this.setFont(new Font(PropertyUtil.getProperty("nbs.ui.font.family"),0,11));
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
    }

    private void initSet(int height){
        if(height<=10)height=20;
        this.setPreferredSize(new Dimension(ConstantsUI.MAIN_WINDOW_WIDTH*3/10-6,height));
        this.setEditable(false);
        this.setForeground(ConstantsUI.MAIN_BACK_COLOR);
        this.setBackground(ConstantsUI.COMMAND_BACK_COLOR);
        this.setFont(new Font(PropertyUtil.getProperty("nbs.ui.font.family"),0,11));
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
    }
}
