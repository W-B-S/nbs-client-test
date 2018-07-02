package io.nbs.client.ui.frames;

import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.cnsts.OSUtil;
import io.nbs.sdk.beans.PeerInfo;
import io.nbs.client.ui.panels.MainContentPanel;
import io.nbs.client.ui.panels.ToolbarPanel;
import io.nbs.client.ui.panels.about.AboutMasterPanel;
import io.nbs.client.ui.panels.im.IMMasterPanel;
import io.nbs.client.ui.panels.info.InfoMasterPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * @Package : io.ipfs.nbs.ui.frames
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-14:46
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MainFrame extends JFrame {

    public  static  Border redBorder = BorderFactory.createLineBorder(Color.RED);

    public  static  Border buleBorder = BorderFactory.createLineBorder(Color.blue,1);

    private static MainFrame context;
    private PeerInfo currentPeer;
    public static final int W_SIZE = 900;
    public static final int H_SIZE = 650;
    public int currentWindowWidth = W_SIZE;
    public int currentWindowHeight = H_SIZE;

    public static final  int TOOLBAR_WIDTH = 52;
    public static final  int LEFT_WIDTH = 260;



    private ToolbarPanel toolbarPanel;
    private JPanel leftMenuPanle;

    private MainContentPanel mainCentetPanel;
    private CardLayout cardLayout;

    private static JPanel mainJPanel;


    /**
     * PEER INFO
     */
    private InfoMasterPanel infoMasterPanel;
    /**
     * 聊天
     */
    private IMMasterPanel imMasterPanel;


    private AboutMasterPanel aboutMasterPanel;

    public static Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

    public MainFrame(PeerInfo peerInfo){
        context = this;
        currentPeer = peerInfo;
        mainJPanel = new JPanel(true);
       // mainJPanel.setBackground(ColorCnst.WINDOW_BACKGROUND_LIGHT);

        initComponents();

        initView();
        setListeners();


    }

    private void initComponents(){
        //设置全局字体

        UIManager.put("Label.font",FontUtil.getDefaultFont());
        UIManager.put("Panel.font", FontUtil.getDefaultFont());
        UIManager.put("TextArea.font", FontUtil.getDefaultFont());

        UIManager.put("Panel.background", ColorCnst.WINDOW_BACKGROUND);
        UIManager.put("CheckBox.background", ColorCnst.WINDOW_BACKGROUND);

        if(OSUtil.getOsType() != OSUtil.Mac_OS){
            setUndecorated(true);//隐藏标题栏
            String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
            try {
                UIManager.setLookAndFeel(windows);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        cardLayout = new CardLayout();
        /**
         * 功能按钮
         */
        leftMenuPanle = new JPanel();
        leftMenuPanle.setBackground(ColorCnst.DARKER);
        toolbarPanel = new ToolbarPanel(context);
        toolbarPanel.setBackground(ColorCnst.DARKER);
        toolbarPanel.setPreferredSize(new Dimension(MainFrame.TOOLBAR_WIDTH,MainFrame.H_SIZE));
        leftMenuPanle.add(toolbarPanel);


        /**
         * 主窗口
         */
        mainCentetPanel = new MainContentPanel();
        mainCentetPanel.setBackground(ColorCnst.WINDOW_BACKGROUND);
        mainCentetPanel.setLayout(cardLayout);

        infoMasterPanel = new InfoMasterPanel();
        imMasterPanel = new IMMasterPanel();
        aboutMasterPanel = new AboutMasterPanel();

        mainCentetPanel.add(infoMasterPanel,MainFrame.MainCardLayoutTypes.INFO.name());
        mainCentetPanel.add(imMasterPanel,MainCardLayoutTypes.IM.name());
        mainCentetPanel.add(aboutMasterPanel,MainCardLayoutTypes.ABOUT.name());
    }

    private void initView(){
        Dimension winDimension = new Dimension(W_SIZE,H_SIZE);
        setSize(winDimension);
        setMinimumSize(winDimension);
        //mainJPanel.setLayout(new BorderLayout());

        //mainJPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        mainJPanel.setLayout(new BorderLayout());
        mainJPanel.add(leftMenuPanle,BorderLayout.WEST);
        mainJPanel.add(mainCentetPanel,BorderLayout.CENTER);

        /**
         * 设置默认显示
         */
        toolbarPanel.setDefaultSelected();
        add(mainJPanel);

        centerScreen();
    }

    /**
     * 设置监听
     */
    private void  setListeners(){

    }

    /**
     *
     * @param clType
     */
    public void mainWinShow(MainCardLayoutTypes clType){
        cardLayout.show(mainCentetPanel,clType.name());
    }
    /**
     * 居中设置
     */
    private void centerScreen(){
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocation((tk.getScreenSize().width - W_SIZE) / 2,
                (tk.getScreenSize().height - H_SIZE) / 2);
    }

    public static MainFrame getContext() {
        return context;
    }

    public static enum MainCardLayoutTypes{
        INFO,IM,DATDA,MUSIC,ABOUT;
    }

    public PeerInfo getCurrentPeer() {
        return currentPeer;
    }

    public void setCurrentPeer(PeerInfo currentPeer) {
        this.currentPeer = currentPeer;
    }

    public void refreshAvatar(){
        toolbarPanel.refreshToolbarAvatar();
    }
}
