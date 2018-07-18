package io.nbs.client;

import io.ipfs.api.IPFS;
import io.ipfs.api.exceptions.IllegalIPFSMessageException;
import io.ipfs.nbs.helper.IPAddressHelper;
import io.nbs.client.cnsts.AppGlobalCnst;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.OSUtil;
import io.nbs.commons.helper.ConfigurationHelper;
import io.nbs.commons.utils.Base64CodecUtil;
import io.nbs.commons.utils.TimeUtil;
import io.nbs.sdk.beans.PeerInfo;
import io.nbs.client.ui.frames.FailFrame;
import io.nbs.client.ui.frames.InitialFrame;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.commons.utils.DataBaseUtil;
import io.nbs.commons.utils.IconUtil;
import io.nbs.sdk.prot.IPMParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private static ProcessBuilder ipfsBuilder;
    private static Process ipfsProcess;
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
    public static String DOWNLOAD_FILE_PATH;
    private static ConfigurationHelper cfgHelper;
    private static boolean ipfsRuning = false;
    private static boolean cliStartFirst = true;

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
            checkedIPFSRunning();
            if(ipfsRuning){
                boolean first = needInitConfig(ipfs);
                //first = true;
                if(first){
                    currentFrame = new InitialFrame(ipfs);
                }else {
                    currentFrame = new MainFrame(currentPeer);
                    currentFrame.setVisible(true);
                }
            }else {
                goFailFrame("您的 NBS 服务未启动,请检查.");
            }
        }catch (RuntimeException re){
            cliStartFirst =false;
            re.printStackTrace();
            System.out.println(re.getMessage());
            goFailFrame("您的 NBS 服务未启动,请检查.");
        } catch (IOException e) {
            cliStartFirst =false;
            logger.error(e.getMessage());
            goFailFrame("未能获取服务配置信息,请检查IPFS 服务是否已启动.");
        }
        currentFrame.setBackground(ColorCnst.WINDOW_BACKGROUND);
        currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if(OSUtil.getOsType()!=OSUtil.Mac_OS){
            currentFrame.setIconImage(logo.getImage());
        }
        currentFrame.setVisible(true);
    }

    private void goFailFrame(String msg){
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append(msg);
        sb.append("</html");
        currentFrame = new FailFrame(sb.toString());
    }

    /**
     *
     */
    private void checkedIPFSRunning(){
        int checkTimes = 0;
        while (!ipfsRuning&& checkTimes<20){
            if(ipfs==null){
                String apiURL = cfgHelper.getIPFSAddress();
                try {
                    ipfs =  new IPFS(apiURL);
                }catch (RuntimeException e){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    checkTimes++;
                    continue;
                }
            }
            try {
                ipfs.id();
                ipfsRuning = true;
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            System.out.println("NBS 服务启动检查...{"+checkTimes+"}.");
            checkTimes++;
        }
    }

    public void reStartMain(){
        //init IPFS and check
        checkedIPFSRunning();
        boolean first = false;
        try {
            first = needInitConfig(ipfs);
            //first = true;
            if(first){
                currentFrame = new InitialFrame(ipfs);
            }else {
                currentFrame = new MainFrame(currentPeer);
                currentFrame.setVisible(true);
            }
            currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            currentFrame.setBackground(ColorCnst.WINDOW_BACKGROUND);
            if(OSUtil.getOsType()!=OSUtil.Mac_OS){
                currentFrame.setIconImage(logo.getImage());
            }
            currentFrame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            destoryIPFS();
            System.exit(1);
        }
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
            Object nickObj = cfg.get(ConfigurationHelper.JSON_NICKNAME_KEY);
            String nick = IPMParser.urlDecode(nickObj.toString());
            String fromid =  (String)cfg.get(ConfigurationHelper.JSON_CFG_FROMID_KEY);
            if(StringUtils.isBlank(fromid)||StringUtils.isBlank(nick))return true;
            currentPeer = new PeerInfo();
            currentPeer.setId(peerid);
            currentPeer.setNick(nick);
            //
            currentPeer.setFrom(fromid);

            Object avatar = cfg.get(ConfigurationHelper.JSON_AVATAR_KEY);
            Object avatarSuffix = cfg.get(ConfigurationHelper.JSON_AVATAR_SUFFIX_KEY);
            if(avatar!=null&&!avatar.toString().equals("")
                    &&avatarSuffix!=null&& !"".equals(avatarSuffix.toString())){
                currentPeer.setAvatar(avatar.toString());
                currentPeer.setAvatarSuffix(avatarSuffix.toString());
            }
            Object avatarName = cfg.get(ConfigurationHelper.JSON_AVATAR_NAME_KEY);
            if(avatarName!=null){
                String avatarFileName = IPMParser.urlDecode(avatarName.toString());
                currentPeer.setAvatarName(avatarFileName);
            }
            //setIP
            new Thread(()->{
                String ip = IPAddressHelper.getInstance().getRealIP();
                if(ip!=null&&!"".equals(ip)){
                    currentPeer.setIp(ip);
                    String locations = IPAddressHelper.getInstance().getLocations(ip);
                    if(StringUtils.isNotBlank(locations))currentPeer.setLocations(locations);
                }
            }).start();
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

        DOWNLOAD_FILE_PATH = AppGlobalCnst.consturactPath(userHome,AppGlobalCnst.NBS_ROOT);
        File userFile = new File(DOWNLOAD_FILE_PATH);
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
        if(ipfs ==null){
            String apiURL = ConfigurationHelper.getInstance().getIPFSAddress();
            try{
                ipfs =  new IPFS(apiURL);
            }catch (RuntimeException e){
                logger.error("未能链接上IPFS服务，请检查服务是否已停止.");
            }
        }
        return ipfs;
    }

    public static SqlSession getSqlSession() {
        return sqlSession;
    }

    /**
     *
     * @return
     */
    public JFrame getCurrentFrame() {
        return currentFrame;
    }

    /**
     *
     * @return
     */
    public boolean startIPFS() throws IllegalIPFSMessageException {
        if(ipfsBuilder==null){
            //ipfs daemon --routing=dhtclient --enable-pubsub-experiment

            String ipfsExe = AppGlobalCnst.consturactPath(CURRENT_DIR,AppGlobalCnst.IPFS_BASE);
            File ipfsPath = new File(ipfsExe);
            File exeFile = new File(ipfsPath,"ipfs.exe");
            if(!exeFile.exists()){
                logger.error("IPFS SEVER NOT FOUND IN PATH :{}",ipfsExe);
                throw new IllegalIPFSMessageException("没有在["+ipfsExe+"]下找到服务文件.");
            }
            ipfsBuilder = new ProcessBuilder("ipfs.exe","daemon" ,"--routing=dhtclient","--enable-pubsub-experiment");
            ipfsBuilder.directory(ipfsPath);
            try {
                ipfsProcess = ipfsBuilder.start();
                return ipfsProcess.waitFor(2,TimeUnit.SECONDS);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public boolean initNBSSvr() throws IllegalIPFSMessageException {
        String ipfsExe = AppGlobalCnst.consturactPath(CURRENT_DIR,AppGlobalCnst.IPFS_BASE);
        File ipfsPath = new File(ipfsExe);
        File exeFile = new File(ipfsPath,"ipfs.exe");
        if(!exeFile.exists()){
            logger.error("IPFS SEVER NOT FOUND IN PATH :{}",ipfsExe);
            throw new IllegalIPFSMessageException("没有在["+ipfsExe+"]下找到服务文件.");
        }
        ProcessBuilder initNBSBuilder =  new ProcessBuilder("ipfs.exe","init" ,"-p local-discovery");
        initNBSBuilder.directory(ipfsPath);
        try {
            Process initNBSProcess = initNBSBuilder.start();
            initNBSProcess.waitFor(2,TimeUnit.SECONDS);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalIPFSMessageException("初始化失败.");
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new IllegalIPFSMessageException("初始化失败.");
        }
        return true;
    }

    /**
     * 退出时同时结束IPFS服务
     */
    public static void destoryIPFS(){
        if(ipfsProcess!=null && cfgHelper.exitStopIPFS()){
            ipfsProcess.destroy();
        }
    }

    public static boolean isIpfsRuning() {
        return ipfsRuning;
    }
}
