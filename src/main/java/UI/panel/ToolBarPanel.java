package UI.panel;

import UI.AppMainWindow;
import UI.ConstantsUI;
import UI.button.NBSIconButton;
import com.nbs.tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @Package : UI.panel
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/13-16:25
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ToolBarPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static NBSIconButton buttonStatus;
    private static NBSIconButton buttonFile;
    private static NBSIconButton buttonIM;
    private static NBSIconButton buttonAbout;

    private static NBSIconButton buttonSetting;

    public ToolBarPanel(){
        initialize();
        addButton();
        addListener();
    }

    /**
     * 初始化
     */
    private void initialize(){
        Dimension preferredSize = new Dimension(48,ConstantsUI.MAIN_WINDOW_HEIGHT);
        this.setPreferredSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);
        this.setLayout(new GridLayout(2,1));
    }

    /**
     * 添加按钮
     */
    private void addButton(){
        JPanel panelUp = new JPanel();
        panelUp.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(-2,-2,-4));
        //
        JPanel panelDown = new JPanel();
        //panelDown.setLayout(new FlowLayout(-2,-2,-4));
        panelDown.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);
        panelDown.setLayout(new BorderLayout(0,0));

        buttonStatus = new NBSIconButton(ConstantsUI.ICON_STATUS,ConstantsUI.ICON_STATUS_ENABLED,PropertyUtil.getProperty("nbs.ui.btn.status.title"));//
        buttonFile = new NBSIconButton(ConstantsUI.ICON_FILE,ConstantsUI.ICON_FILE_ENABLED,PropertyUtil.getProperty("nbs.ui.btn.file.title"));//
        buttonIM = new NBSIconButton(ConstantsUI.ICON_IM,ConstantsUI.ICON_IM_ENABLED,PropertyUtil.getProperty("nbs.ui.btn.im.title"));//
        buttonSetting = new NBSIconButton(ConstantsUI.ICON_SETTING,ConstantsUI.ICON_SETTING_ENABLED,ConstantsUI.ICON_SETTING_DISABLED,PropertyUtil.getProperty("nbs.ui.btn.setting.title"));//
        buttonAbout = new NBSIconButton(ConstantsUI.ICON_ABOUT,ConstantsUI.ICON_ABOUT_ENABLED,PropertyUtil.getProperty("nbs.ui.btn.about.title"));

        panelUp.add(buttonStatus);
        panelUp.add(buttonFile);
        panelUp.add(buttonIM);

        panelUp.add(buttonSetting);
        panelUp.add(buttonAbout);

        panelDown.add(buttonAbout,BorderLayout.SOUTH);
        this.add(panelUp);
        this.add(panelDown);
    }

    /**
     * 为按钮添加事件监听
     */
    private void addListener(){
        buttonStatus.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonStatus.setIcon(ConstantsUI.ICON_STATUS_ENABLED);
                buttonFile.setIcon(ConstantsUI.ICON_FILE);
                buttonIM.setIcon(ConstantsUI.ICON_IM);
                buttonSetting.setIcon(ConstantsUI.ICON_SETTING);
                buttonAbout.setIcon(ConstantsUI.ICON_ABOUT);
                //TODO mianCenter
                AppMainWindow.mainPanelCenter.removeAll();
                AppMainWindow.statusPanel.load();
                AppMainWindow.mainPanelCenter.add(AppMainWindow.statusPanel);
                AppMainWindow.mainPanelCenter.updateUI();

            }
        });

        buttonFile.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonStatus.setIcon(ConstantsUI.ICON_STATUS);
                buttonFile.setIcon(ConstantsUI.ICON_FILE_ENABLED);
                buttonIM.setIcon(ConstantsUI.ICON_IM);
                buttonSetting.setIcon(ConstantsUI.ICON_SETTING);
                buttonAbout.setIcon(ConstantsUI.ICON_ABOUT);
                //
                AppMainWindow.mainPanelCenter.removeAll();
                AppMainWindow.filePanel.load();
                AppMainWindow.mainPanelCenter.add(AppMainWindow.filePanel);
                AppMainWindow.mainPanelCenter.updateUI();

            }
        });

        buttonIM.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonStatus.setIcon(ConstantsUI.ICON_STATUS);
                buttonFile.setIcon(ConstantsUI.ICON_FILE);
                buttonIM.setIcon(ConstantsUI.ICON_IM_ENABLED);
                buttonSetting.setIcon(ConstantsUI.ICON_SETTING);
                buttonAbout.setIcon(ConstantsUI.ICON_ABOUT);
                //
                AppMainWindow.mainPanelCenter.removeAll();
                AppMainWindow.imPanel.load();
                AppMainWindow.mainPanelCenter.add(AppMainWindow.imPanel);
                AppMainWindow.mainPanelCenter.updateUI();
            }
        });


        buttonSetting.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonStatus.setIcon(ConstantsUI.ICON_STATUS);
                buttonFile.setIcon(ConstantsUI.ICON_FILE);
                buttonIM.setIcon(ConstantsUI.ICON_IM);
                buttonSetting.setIcon(ConstantsUI.ICON_SETTING_ENABLED);
                buttonAbout.setIcon(ConstantsUI.ICON_ABOUT);
                //TODO mianCenter
                AppMainWindow.mainPanelCenter.removeAll();
                AppMainWindow.settingPanel.load();
                AppMainWindow.mainPanelCenter.add(AppMainWindow.settingPanel);
                AppMainWindow.mainPanelCenter.updateUI();
            }
        });

        buttonAbout.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonStatus.setIcon(ConstantsUI.ICON_STATUS);
                buttonFile.setIcon(ConstantsUI.ICON_FILE);
                buttonIM.setIcon(ConstantsUI.ICON_IM);
                buttonSetting.setIcon(ConstantsUI.ICON_SETTING);
                buttonAbout.setIcon(ConstantsUI.ICON_ABOUT_ENABLED);

                AppMainWindow.mainPanelCenter.removeAll();
                AppMainWindow.aboutPanel.loadPanelInfo();
                AppMainWindow.mainPanelCenter.add(AppMainWindow.aboutPanel);

                AppMainWindow.mainPanelCenter.updateUI();

            }
        });
    }


}
