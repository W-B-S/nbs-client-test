package io.nbs.client;

import io.ipfs.api.IPFS;
import io.nbs.client.cnsts.AppGlobalCnst;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.OSUtil;
import io.nbs.commons.helper.ConfigurationHelper;
import io.nbs.sdk.beans.PeerInfo;
import io.nbs.client.ui.frames.FailFrame;
import io.nbs.client.ui.frames.InitialFrame;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.commons.utils.DataBaseUtil;
import io.nbs.commons.utils.IconUtil;
import org.apache.commons.lang3.StringUtils;
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

    private Logger logger = LoggerFactory.getLogger(Launcher.class);
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

    private IPFS ipfs;
    /**
     * 当前Frame
     */
    private JFrame currentFrame;

    public static PeerInfo currentPeer;

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

        /*if(args.length>0){

        }*/
    }


    public void launch(){
        /**
         * 1.初始化目录
         */
        initialStartup();
        /**
         * 2.构建IPFS
         */
        ipfs = null;

        try{
            String apiURL = ConfigurationHelper.getInstance().getIPFSAddress();
            ipfs =  new IPFS(apiURL);
            boolean first = needInitConfig(ipfs);
            //first = true;
            if(first){
                currentFrame = new InitialFrame(ipfs);
            }else {
                currentFrame = new MainFrame(currentPeer);
                currentFrame.setVisible(true);
            }
            currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }catch (RuntimeException re){
            re.printStackTrace();
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
        if(OSUtil.getOsType()!=OSUtil.Mac_OS){
            currentFrame.setIconImage(logo.getImage());
        }
        currentFrame.setVisible(true);
    }

    /**
     *
     * @param ipfs
     * @return
     * @throws IOException
     */
    private boolean needInitConfig(IPFS ipfs) throws IOException {
        Map cfg = ipfs.config.show();
        String peerid = (String)ipfs.id().get("ID");
        if(cfg.containsKey(ConfigurationHelper.JSON_NICKNAME_KEY)
                && cfg.containsKey(ConfigurationHelper.JSON_CFG_FROMID_KEY)){
            String nick = (String)cfg.get(ConfigurationHelper.JSON_NICKNAME_KEY);
            String fromid =  (String)cfg.get(ConfigurationHelper.JSON_CFG_FROMID_KEY);
            if(StringUtils.isBlank(fromid)||StringUtils.isBlank(nick))return true;

            currentPeer = new PeerInfo();
            currentPeer.setId(peerid);
            currentPeer.setNick(nick);
            currentPeer.setFrom(fromid);

            Object avatar = cfg.get(ConfigurationHelper.JSON_AVATAR_KEY);
            Object avatarSuffix = cfg.get(ConfigurationHelper.JSON_AVATAR_SUFFIX_KEY);
            if(avatar!=null&&!avatar.toString().equals("")
                    &&avatarSuffix!=null&& !"".equals(avatarSuffix.toString())){
                currentPeer.setAvatar(avatar.toString());
                currentPeer.setAvatarSuffix(avatarSuffix.toString());
            }
            Object avatarName = cfg.get(ConfigurationHelper.JSON_AVATAR_NAME_KEY);
            if(avatarName!=null)currentPeer.setAvatarName((String)avatarName);
            return false;
        }else {
            return true;
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

    public IPFS getIpfs() {
        return ipfs;
    }

    public static SqlSession getSqlSession() {
        return sqlSession;
    }
}
