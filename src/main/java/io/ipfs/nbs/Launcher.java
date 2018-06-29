package io.ipfs.nbs;

import io.ipfs.api.IPFS;
import io.ipfs.nbs.cnsts.AppGlobalCnst;
import io.ipfs.nbs.cnsts.ColorCnst;
import io.ipfs.nbs.helper.ConfigurationHelper;
import io.ipfs.nbs.peers.PeerInfo;
import io.ipfs.nbs.ui.frames.FailFrame;
import io.ipfs.nbs.ui.frames.InitialFrame;
import io.ipfs.nbs.utils.DataBaseUtil;
import io.ipfs.nbs.utils.IconUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @Package : io.ipfs.app
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/29-14:01
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class Launcher {

    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);
    private static Launcher context;
    private static SqlSession sqlSession;
    private static final String APP_VERSION = "2.0";

    public static ImageIcon logo ;
    /**
     * 文件基础路径
     * ${basedir}/.nbs/
     * .nbs/download/cache
     * files
     * music
     * videos
     * profiles
     */
    public static String appBasePath;
    /**
     *
     */
    public static String userHome;
    public static String CURRENT_DIR;
    public static final String FILE_SEPARATOR;
    private ConfigurationHelper cfgHelper;
    /**
     * 当前Frame
     */
    private JFrame currentFrame;

    private static PeerInfo currentPeer;

    static {
        sqlSession = DataBaseUtil.getSqlSession();
        CURRENT_DIR = System.getProperty("user.dir");
        FILE_SEPARATOR = System.getProperty("file.separator");
    }

    public Launcher(){
        context = this;
        logo = IconUtil.getIcon(this,"/icons/nbs.png");
        cfgHelper = ConfigurationHelper.getInstance();
    }
    public Launcher(String[] args){
        context = this;
        cfgHelper = ConfigurationHelper.getInstance();
        if(args.length>0){

        }
    }


    public void launch(){
        /**
         * 1.初始化目录
         */
        initialStartup();
        /**
         * 2.构建IPFS
         */
        IPFS ipfs = null;

        try{
            ipfs =  new IPFS(cfgHelper.getIPFSAddress());
            boolean first = checkedFirst(ipfs);
            if(first){

            }
            currentFrame = new InitialFrame(ipfs);

            currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }catch (RuntimeException re){
            System.out.println(re.getMessage());
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            sb.append("您的 NBS 服务未启动,请检查.");
            sb.append("</html");
            currentFrame = new FailFrame(sb.toString());
            currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (IOException e) {
            logger.error(e.getMessage());
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            sb.append("未能获取服务配置信息,请检查IPFS 服务是否已启动.");
            sb.append("</html");
            currentFrame = new FailFrame(sb.toString());
            currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        currentFrame.setBackground(ColorCnst.WINDOW_BACKGROUND);
        currentFrame.setIconImage(logo.getImage());
        currentFrame.setVisible(true);
    }

    /**
     *
     * @param ipfs
     * @return
     * @throws IOException
     */
    private boolean checkedFirst(IPFS ipfs) throws IOException {
        Map cfg = ipfs.config.show();
        if(cfg.containsKey(ConfigurationHelper.JSON_NICKNAME_KEY)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 启动初始化
     */
    private void initialStartup(){
        userHome = System.getProperty("user.home");
        appBasePath = CURRENT_DIR+FILE_SEPARATOR+AppGlobalCnst.NBS_ROOT;
        /**
         * 初始化目录
         */
        File userFile = new File(userHome+FILE_SEPARATOR+AppGlobalCnst.NBS_ROOT);
        if(!userFile.exists()){
            userFile.mkdirs();
        }
        File appBaseFile = new File(appBasePath);
        if(!appBaseFile.exists()){
            appBaseFile.mkdirs();
        }

        File appTempFile = new File(CURRENT_DIR+FILE_SEPARATOR+AppGlobalCnst.TEMP_FILE);
        if(!appBaseFile.exists()){
            appBaseFile.mkdirs();
        }
        //数据库建表初始化

    }

    /**
     *
     * @return
     */
    public static Launcher getContext() {
        return context;
    }

    public static PeerInfo getCurrentPeer() {
        return currentPeer;
    }

    public static void setCurrentPeer(PeerInfo currentPeer) {
        Launcher.currentPeer = currentPeer;
    }
}
