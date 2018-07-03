package UI.panel.about;

import UI.ConstantsUI;
import io.nbs.client.ui.components.NBSIconButton;
import com.nbs.tools.PropertyUtil;
import io.nbs.commons.helper.ConfigurationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * @Package : UI.panel
 * @Description : <p>
 *     NBS 介绍
 * </p>
 * @Author : lambor.c
 * @Date : 2018/6/15-15:16
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AboutPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(AboutPanel.class);
    protected static final String NBS_DOWNLOAD_SITE="http://nbsio.net/#prototype";
    private static final String UPDATE_LOG_NAME = "update.log.html";

    private static NBSIconButton checkUpdateBtn;

    private static NBSIconButton updateClientRootBtn;

    /**
     *
     */
    public AboutPanel(){
        initialize();
        addComponent();
        addListener();
    }

    /**
     * 初始化
     */
    private void initialize(){
        this.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
    }

    public void addComponent(){
        this.add(buildPanelTop(),BorderLayout.NORTH);
        this.add(buildPanelCenter(),BorderLayout.CENTER);
        this.add(biuldPanelBottom(),BorderLayout.SOUTH);
    }

    /**
     * @Date    : 2018/6/15 15:30
     * @Author  : lanbery
     * @Decription :
     * <p> 头部</p>
     * @Param   :
     * @return
     * @throws
     */
    public JPanel buildPanelTop(){
        JPanel panelTop = new JPanel();
        panelTop.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelTop.setLayout(new FlowLayout(FlowLayout.LEFT,ConstantsUI.MAIN_H_GAP,5));

        JLabel labelTitle = new JLabel(PropertyUtil.getProperty(PropertyUtil.PKUI_PANEL_ABOUT_LABEL));
        labelTitle.setFont(ConstantsUI.FONT_TITLE);
        labelTitle.setForeground(ConstantsUI.PANEL_TITILE_COLOR);

        panelTop.add(labelTitle);
        return panelTop;
    }

    /**
     *
     * @return
     */
    private JPanel buildPanelCenter(){
        JPanel panelCenter = new JPanel(true);
        panelCenter.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelCenter.setLayout(new BorderLayout());

        /**
         * About Short Title and logo Begin
         */
        // title and logo
        JPanel shortPanel = new JPanel(true);
        shortPanel.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        shortPanel.setLayout(new BorderLayout());

        JLabel shortTitle = new JLabel(" NBS IPFS the p2p data share!");
        shortTitle.setFont(ConstantsUI.FONT_SUB_TITLE);
        shortTitle.setForeground(ConstantsUI.NORMAL_FONT_COLOR);
        shortPanel.add(shortTitle,BorderLayout.CENTER);

        //logo
        JLabel logoLabel = new JLabel(ConstantsUI.NBS_ICON);
        shortPanel.add(logoLabel,BorderLayout.WEST);

        //current version
        JPanel verPanel = new JPanel(false);
        verPanel.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        verPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,ConstantsUI.MAIN_H_GAP,5));

        JLabel verLabel = new JLabel(PropertyUtil.getProperty("nbs.ui.panel.about.label.ver-name","Current Version :"));
        verLabel.setFont(ConstantsUI.FONT_LABEL);
        verLabel.setForeground(ConstantsUI.PANEL_TITILE_COLOR);
        verLabel.setLayout(new BorderLayout());

        JLabel verValueLabel = new JLabel(PropertyUtil.getProperty("nbs.ui.panel.about.label.ver-value"));
        verValueLabel.setFont(ConstantsUI.FONT_NORMAL);
        verValueLabel.setForeground(ConstantsUI.NORMAL_FONT_COLOR);

        verPanel.add(verLabel);
        verPanel.add(verValueLabel);
        shortPanel.add(verPanel,BorderLayout.NORTH);
        //shortTitle.add(verPanel);



        panelCenter.add(shortPanel,BorderLayout.NORTH);

        /**
         * contentPanel
         */
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        contentPanel.setLayout(new BorderLayout());

        //TODO update history html
        JEditorPane updateLog = new JEditorPane();
        updateLog.setEditable(false);


        try {
            updateLog.setPage(PropertyUtil.getProperty("nbs.ui.panel.about.html.ver-log"));
        } catch (IOException e) {
            e.printStackTrace();
            updateLog.setText(e.getMessage());
        }
        contentPanel.add(updateLog);
        panelCenter.add(contentPanel,BorderLayout.CENTER);
        return panelCenter;
    }

    /**
     * 加载信息
     */
    public void loadPanelInfo(){

    }

    /**
     * 底部
     * @return
     */
    private JPanel biuldPanelBottom(){
        JPanel panelBottom = new JPanel();
        panelBottom.setBackground(ConstantsUI.MAIN_BACK_COLOR);

        JLabel cyrt = new JLabel(PropertyUtil.getProperty("nbs.soft.copyright","Copyright by NBS 2018"));
        cyrt.setFont(ConstantsUI.FONT_CPR);
        cyrt.setForeground(ConstantsUI.NORMAL_FONT_COLOR);
        panelBottom.add(cyrt,BorderLayout.WEST);

        panelBottom.setLayout(new FlowLayout(FlowLayout.RIGHT,ConstantsUI.PANEL_H_GAP10,15));
        checkUpdateBtn = new NBSIconButton(
                ConstantsUI.ICON_CHECK,ConstantsUI.ICON_CHECK_ENABLED,ConstantsUI.ICON_CHECK_DISABLED,"");
        panelBottom.add(checkUpdateBtn);

        //
        updateClientRootBtn = new NBSIconButton(ConstantsUI.ICON_TEST,ConstantsUI.ICON_TEST,ConstantsUI.ICON_TEST,"TEST");
        //panelBottom.add(updateClientRootBtn);



        return panelBottom;
    }

    /**
     * @Date    : 2018/6/15 15:55
     * @Author  : lanbery
     * @Decription :
     * <p>为控件添加事件</p>
     * @Param   :
     * @return
     * @throws
     */
    public void addListener(){
        /**
         *
         */
        checkUpdateBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String site = PropertyUtil.getProperty("nbs.soft.download.url",NBS_DOWNLOAD_SITE);
                try {
                    java.net.URI uri = java.net.URI.create(site);
                    java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                    String v =ConfigurationHelper.getInstance().getIPFSAddress();
                    logger.info(v);
                    if(desktop.isSupported(Desktop.Action.BROWSE)){
                            desktop.browse(uri);
                    }else{
                        JOptionPane.showMessageDialog(null,PropertyUtil.getProperty("nbs.btn.checked.version.nosetBorowser",""));
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,PropertyUtil.getProperty("nbs.global.error",""));
                }
            }
        });

        /**
         *
         */
        updateClientRootBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info(">>>>NBS TEST BUTTON.");
  /*              try {
                    ConfigHelper.getInstance().storeClientAddFileRootHash("skjfhsafkdasfd","TEST");
                    logger.info(ConfigHelper.getInstance().getClientAddFileRootHash());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }*/
            }
        });
    }

}
