package UI;

import UI.panel.*;
import UI.panel.about.AboutPanel;
import UI.panel.fm.FilePanel;
import UI.panel.im.IMPanel;
import UI.panel.monitor.ConsolePanel;
import UI.panel.setting.SettingPanel;
import com.alibaba.fastjson.JSON;
import com.nbs.ipfs.IPFSHelper;
import com.nbs.tools.ConfigHelper;
import io.ipfs.api.IPFS;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @Package : UI
 * @Description :
 * <p>
 *     NBS ipfs Client for Java.
 *     <h1>程序主入口</h1>
 * </p>
 * @Author : lambor.c
 * @Date : 2018/6/13-14:55
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AppMainWindow {

    /**
     *
     */
    private static final Logger logger = LoggerFactory.getLogger(AppMainWindow.class);

    public static JFrame frame;
    /**
     * 主窗口
     */
    private static JPanel mainPanel;
    /**
     * 内容窗口
     */
    public static  JPanel mainPanelCenter;
    /**
     * 状态窗口
     */
    public static StatusPanel statusPanel;

    public static IMPanel imPanel;

    public static FilePanel filePanel;

    public static String PEER_ID = "";

    public static boolean SERVER_STAT = false;

    protected static  IPFSHelper ipfsHelper = IPFSHelper.getInstance();
    /**
     *
     */
    public static IPFS ipfs = IPFSHelper.getInstance().getIpfs();


    public static String PROFILE_NICKNAME = "";

    /**
     *
     */
    public static SettingPanel settingPanel;
    /**
     * 关于面板
     */
    public static AboutPanel aboutPanel;

    public static void main(String[] args){

        EventQueue.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        try{
                            AppMainWindow window = new AppMainWindow();
                            window.frame.setVisible(true);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }
        );
    }


    /**
     * @Date    : 2018/6/13 16:49
     * @Author  : lanbery
     * @Decription :
     * <p>构造NBS App</p>
     * @Param   :
     * @return
     * @throws
     */
    public AppMainWindow(){
        initialize();

    }
    /**
     * 初始化frame内容
     */
    private void initialize(){
        logger.info("NBS initializing start ...");
        loadEnv();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException
                | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame = new JFrame();
        //set window Size
        frame.setBounds(ConstantsUI.MAIN_WINDOW_X,ConstantsUI.MAIN_WINDOW_Y
                ,ConstantsUI.MAIN_WINDOW_WIDTH,ConstantsUI.MAIN_WINDOW_HEIGHT);
        frame.setTitle(ConstantsUI.APP_NAME);
        frame.setIconImage(ConstantsUI.IMAGE_ICON);
        frame.setBackground(ConstantsUI.MAIN_BACK_COLOR);

        //主要窗口
        mainPanel = new JPanel(true);
        mainPanel.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        mainPanel.setLayout(new BorderLayout());

        //工具栏
        ToolBarPanel toolbar = new ToolBarPanel();
        statusPanel = new StatusPanel();

        //初始化 面板
        aboutPanel = new AboutPanel();
        settingPanel = new SettingPanel(true);
        imPanel = new IMPanel(true);
        filePanel = new FilePanel(true);

        //TODO other panel init


        //设置左侧菜单栏位
        mainPanel.add(toolbar,BorderLayout.WEST);

        /**
         * 设置中部内容显示面板
         * 默认 status
         */
        mainPanelCenter = new JPanel(true);
        mainPanelCenter.setLayout(new BorderLayout());
        mainPanelCenter.add(statusPanel,BorderLayout.CENTER);

        mainPanel.add(mainPanelCenter,BorderLayout.CENTER);
        //
        frame.add(mainPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        logger.info("NBS initialized ...");
    }

    /**
     * 加载环境配置
     */
    private void loadEnv(){
        Properties props = ConfigHelper.getEnv();
        logger.info("ENV ============================>>");
        for(String k : props.stringPropertyNames()){
            String v = props.getProperty(k);
            logger.info(k+"="+v);
        }
        logger.info("ENV ============================<<");

        File f = new File(ConstantsUI.PROFILE_ROOT);
        if(!f.exists()){
            f.mkdirs();
        }

        try {
            if(ipfs==null)ipfs = new IPFS(ConfigHelper.getIpfsAddress());
            SERVER_STAT = true;
            Map map = ipfs.id();

            if(map.get("ID")!=null)PEER_ID = (String)map.get("ID");
            logger.info(">>>>>>>>>>>>>."+map.get("ID"));
            initNick();
        }catch (Exception e){
            logger.error("ipfs Server is dead.",e.getCause());
            ipfs = new IPFS(ConfigHelper.getIpfsAddress());
        }

    }


    public void initNick(){
        if(StringUtils.isNotBlank(PROFILE_NICKNAME))return;
        String nick = "";
        try {
            Map cfgMap = ipfs.config.show();
            if(cfgMap.containsKey(IPFSHelper.JSON_NICKNAME_KEY)){
                nick = (String)cfgMap.get(IPFSHelper.JSON_NICKNAME_KEY);
            }
        } catch (IOException e) {
            e.printStackTrace();
            nick = ipfsHelper.generateNickName();
            try {
                ipfs.config.set(IPFSHelper.JSON_NICKNAME_KEY,nick);
                String s=JSON.toJSONString(ipfs.config.show(),true);
                logger.info(s);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        PROFILE_NICKNAME = nick;
    }

}
