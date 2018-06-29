package UI.panel.fm;

import UI.ConstantsUI;
import com.nbs.tools.DateHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @Package : UI.panel.fm
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/20-16:15
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class FileHisPanel extends JPanel {
    public FileHisPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        initialize();
    }

    public FileHisPanel(LayoutManager layout) {
        super(layout);
        initialize();
    }

    public FileHisPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        initialize();
    }

    public FileHisPanel() {
        initialize();
    }


    public void initialize(){
        this.setLayout(new FlowLayout(FlowLayout.LEFT,2,0));
        this.setPreferredSize(new Dimension(ConstantsUI.MAIN_WINDOW_WIDTH*2/5,580));
        this.setBackground(ConstantsUI.COMMAND_BACK_COLOR);
        this.setFont(ConstantsUI.FONT_NORMAL);
        this.setForeground(Color.WHITE);


        //timeLabel.widthWarp((ConstantsUI.MAIN_WINDOW_WIDTH*2/5-6),);

    }

    /**
     *
     * @param name
     * @param b
     * @param hash58
     */
    public void appendLog(String name,boolean b,String hash58){
        LogFlowJText timeLabel ,hashLabel;
        StringBuffer sb = new StringBuffer();
        sb.append(" > ").append(DateHelper.currentTime());
        if(StringUtils.isNotBlank(name))sb.append(",上传").append(name);

        if(b){
            sb.append("成功.");
            hash58 = "Hash : "+hash58;
        }else {
            sb.append("失败.");
        }

        timeLabel =  new LogFlowJText(sb.toString(),18);
        hashLabel =  new LogFlowJText(hash58,32);
        this.add(timeLabel);
        this.add(hashLabel);
        this.updateUI();
    }
}
