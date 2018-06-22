package UI.panel.im;

import UI.ConstantsUI;
import UI.common.NBSAbstractPanel;
import UI.common.ToolbarStatsPanel;
import UI.templete.WihteBackJPanel;

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

    /**
     * 在线peers
     */
    private static JPanel peersJPanel;

    private static JPanel peerList;

    /**
     * 聊天主窗口，内部将包含：
     * 1.对话显示窗 center
     * 2.信息输入框 south
     *   inputArea  sendBtn
     */
    private static JPanel messPanel;


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
        this.add(getCenterPanel(),BorderLayout.CENTER);

    }

    /**
     * @Date    : 2018/6/22 9:59
     * @Author  : lanbery
     * @Decription :
     * <p></p>
     * @Param   :
     * @return 
     * @throws
     */
    private JPanel getCenterPanel(){
        WihteBackJPanel centerPanel = new WihteBackJPanel();
        centerPanel.setLayout(new BorderLayout());

        peerList = new JPanel();
        peerList.setBackground(new Color(221,221,221));
        Dimension leftDimsnsion = new Dimension(245,ConstantsUI.MAIN_WINDOW_HEIGHT);
        peerList.setPreferredSize(leftDimsnsion);
        peerList.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));



        centerPanel.add(peerList,BorderLayout.WEST);

        /**
         * 聊天主窗口
         */
        messPanel = new WihteBackJPanel();
        messPanel.setLayout(new BorderLayout());



        centerPanel.add(messPanel,BorderLayout.CENTER);

        return centerPanel;
    }

    @Override
    public void load() {

    }

    @Override
    protected void addListener() {

    }

    private void buildLeftPeerContainer(){

    }
}
