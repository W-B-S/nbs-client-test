package io.nbs.client.ui.frames;

import com.nbs.biz.service.PeerLoginService;
import io.ipfs.api.IPFS;
import io.nbs.client.Launcher;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.cnsts.OSUtil;
import io.nbs.client.services.IpfsMessageSender;
import io.nbs.client.services.MessageSendService;
import io.nbs.commons.helper.ConfigurationHelper;
import io.nbs.sdk.beans.OnlineMessage;
import io.nbs.sdk.beans.PeerInfo;
import io.nbs.client.ui.panels.MainContentPanel;
import io.nbs.client.ui.panels.ToolbarPanel;
import io.nbs.client.ui.panels.about.AboutMasterPanel;
import io.nbs.client.ui.panels.im.IMMasterPanel;
import io.nbs.client.ui.panels.info.InfoMasterPanel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * @Package : io.ipfs.nbs.ui.frames
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-14:46
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MainFrame extends JFrame {
    public static Logger logger = LoggerFactory.getLogger(MainFrame.class);

    /**
     * 测试用
     */
    public  static  Border redBorder = BorderFactory.createLineBorder(Color.RED);
    public  static  Border buleBorder = BorderFactory.createLineBorder(Color.blue,1);

    private static MainFrame context;
    private PeerInfo currentPeer;
    public static final int W_SIZE = 920;
    public static final int H_SIZE = 650;
    public int currentWindowWidth = W_SIZE;
    public int currentWindowHeight = H_SIZE;
    public static  int RIGHT_EIDTH = 540;
    private static boolean heartMonitor = true;

    public static final  int TOOLBAR_WIDTH = 80;


    private ToolbarPanel toolbarPanel;
    private JPanel leftMenuPanle;

    private MainContentPanel mainCentetPanel;
    private CardLayout cardLayout;

    private static JPanel mainJPanel;

    /**
     *
     */
    private IpfsMessageSender messageSender;
    private MessageSendService messageSendService;

    private PeerLoginService peerLoginService;
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
        initServices();
        initComponents();

        initView();
        setListeners();

        //
        notifyWorldOnline();
    }

    private void initServices(){
       IPFS ipfs = Launcher.getContext().getIpfs();
       messageSender = new IpfsMessageSender(ipfs);
       messageSendService = new MessageSendService(Launcher.getSqlSession());
       peerLoginService = new PeerLoginService(Launcher.getSqlSession());
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

        leftMenuPanle.setOpaque(false);
       // leftMenuPanle.setBackground(ColorCnst.DARKER);
        toolbarPanel = new ToolbarPanel(context);
        //toolbarPanel.setBackground(ColorCnst.DARKER);
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
        mainJPanel.add(toolbarPanel,BorderLayout.WEST);
       // mainJPanel.add(leftMenuPanle,BorderLayout.WEST);
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
        INFO,IM,DATD,MUSIC,ABOUT;
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

    /**
     *
     */
    private void notifyWorldOnline(){
        PeerInfo info = Launcher.currentPeer;
        if(info==null)return;
        OnlineMessage message = convertByPeerInfo(info);
        //IP 解析
        //nbs.client.heart.monitor.seconds
        final int seconds = ConfigurationHelper.getInstance().getHeartMonitorSleep();
        new Thread(()->{
            while (heartMonitor){
                try {
                    if(message!=null)messageSender.sendOnline(message);
                    logger.info("heart monitor {}",System.currentTimeMillis());
                } catch (Exception e) {
                    logger.warn(e.getMessage(),e.getCause());
                }
                try {
                    TimeUnit.SECONDS.sleep(seconds);
                } catch (InterruptedException e) {
                }
            }
        }).start();

        //刷新数据库登录
        peerLoginService.refreshLoginInfo(info);
    }

    /**
     *
     * @return
     */
    private OnlineMessage convertByPeerInfo(PeerInfo info){
        OnlineMessage message = new OnlineMessage(info.getId(),info.getNick(),info.getFrom());
        if(StringUtils.isNotBlank(info.getAvatar())){
            message.setAvatar(info.getAvatar());
            message.setAvatarFile(info.getAvatarName());
            message.setAvatarSuffix(info.getAvatarSuffix());
            message.setLocations("中国*北京");
        }
        return message;
    }


    public IpfsMessageSender getMessageSender() {
        return messageSender;
    }

    public MessageSendService getMessageSendService() {
        return messageSendService;
    }
}
