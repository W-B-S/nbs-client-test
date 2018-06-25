package UI;

import UI.panel.*;
import UI.panel.about.AboutPanel;
import UI.panel.fm.FilePanel;
import UI.panel.im.IMPanel;
import UI.panel.setting.SettingPanel;
import com.alibaba.fastjson.JSON;
import com.nbs.entity.ContactsItem;
import com.nbs.entity.PeerBoradcastInfo;
import com.nbs.entity.PeerInfoBase;
import com.nbs.ipfs.IPFSHelper;
import com.nbs.ipfs.entity.IpfsMessage;
import com.nbs.tools.ConfigHelper;
import com.nbs.tools.DateHelper;
import com.nbs.utils.Base64CodecUtil;
import com.nbs.utils.RadomCharactersHelper;
import io.ipfs.api.IPFS;
import io.ipfs.api.JSONParser;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.TIMEOUT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private static boolean boradcastSuccess = false;

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
    public static String IPFS_MSG_FROM = null;

    public static boolean SERVER_STAT = false;

    /**
     * 当前信息
     *
     */
    public static PeerInfoBase SEFL_BASE = null;

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
    /**
     * 联系人缓存
     */
    public static Map<String,ContactsItem> peerItems = new HashMap<>();

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
        //订阅世界消息
        subWorld();
        //广播自己上线
        broadcastOnline();
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
        /**
         * 初始化文件目录
         */
        File ipfsDir = new File(ConfigHelper.NBS_FILES_IPFS_ROOT);
        if(ipfsDir.isDirectory()&& !ipfsDir.exists()){
            ipfsDir.mkdirs();
        }

        File f = new File(ConstantsUI.PROFILE_ROOT);
        if(!f.exists()){
            f.mkdirs();
        }

        //
        try {
            if(ipfs==null)ipfs = new IPFS(ConfigHelper.getIpfsAddress());
            SERVER_STAT = true;
            Map map = ipfs.id();
            logger.info(JSON.toJSONString(map));
            if(map.get("ID")!=null)PEER_ID = (String)map.get("ID");
            SEFL_BASE = new PeerInfoBase(PEER_ID);
            String n = initNick();
            SEFL_BASE.setNick(n);
            //avatar must below init nick
            initAvatar();
        }catch (Exception e){
            logger.error("ipfs Server is dead.",e.getCause());
            ipfs = new IPFS(ConfigHelper.getIpfsAddress());
        }
        String topic = RadomCharactersHelper.getInstance().generated("$SELF.N.S",8);
        subFillForm(topic);
        pubFillFrom(topic,SEFL_BASE.getPeerID());

    }

    /**
     * 线程设置 from
     * @param topic
     */
    private void subFillForm(String topic){
        if(StringUtils.isNotBlank(SEFL_BASE.getFrom()))return;
        new Thread(()->{
            try {
                logger.info(topic);
                Stream<Map<String,Object>> subs = ipfs.pubsub.sub(topic);
                List<Map<String,Object>> m = subs.limit(1).collect(Collectors.toList());
                String json = JSONParser.toString(m.get(0));
                IpfsMessage ipfsMessage = JSON.parseObject(json,IpfsMessage.class);
                if(SEFL_BASE != null ){
                    SEFL_BASE.setFrom(ipfsMessage.getFrom());
                }
            } catch (Exception e) {

            }
            if(StringUtils.isBlank(SEFL_BASE.getFrom())){
                subFillForm(topic);
            }
        }).start();

    }

    /**
     * 发送设置消息
     * @param topic
     * @param id
     */
    private void pubFillFrom(String topic,String id){
        new Thread(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                logger.info(DateHelper.currentTime()+"SEND ...");
                for(int i =0;i<3;i++){
                    ipfs.pubsub.pub(topic,id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 构造Nickname
     * @return
     */
    public String initNick(){
        if(StringUtils.isNotBlank(PROFILE_NICKNAME))return PROFILE_NICKNAME;
        String nick = "";
        try {
            Map cfgMap = ipfs.config.show();
            if(cfgMap.containsKey(ConfigHelper.JSON_NICKNAME_KEY)){
                nick = (String)cfgMap.get(ConfigHelper.JSON_NICKNAME_KEY);
            }
        } catch (IOException e) {
            e.printStackTrace();
            nick = ipfsHelper.generateNickName();
            try {
                ipfs.config.set(ConfigHelper.JSON_NICKNAME_KEY,nick);
                String s=JSON.toJSONString(ipfs.config.show(),true);
                logger.info(s);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        PROFILE_NICKNAME = nick;
        return PROFILE_NICKNAME;
    }

    /**
     * 初始化头像
     */
    public void initAvatar(){
        try {
            String avatarHash,suffix,fileName;
            Map cfgMap = ipfs.config.show();
            if(cfgMap.containsKey(ConfigHelper.JSON_AVATAR_KEY)&&cfgMap.containsKey(ConfigHelper.JSON_AVATAR_SUFFIX_KEY)){
                //
            }else {
                File defaultAvatarImage = new File(ConfigHelper.PROFILE_ROOT+"nbs.png");
                if(!defaultAvatarImage.exists()||defaultAvatarImage.isDirectory())return;
                fileName = defaultAvatarImage.getName();
                suffix = fileName.substring(fileName.lastIndexOf("."));
                logger.info(suffix);
                NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(defaultAvatarImage);
                java.util.List<MerkleNode> merkleNodes= ipfs.add(file);
                avatarHash = merkleNodes.get(0).hash.toBase58();

                //存入config
                try{
                    ipfs.config.set(ConfigHelper.JSON_AVATAR_KEY,avatarHash);
                    ipfs.config.set(ConfigHelper.JSON_AVATAR_SUFFIX_KEY,suffix);
                    ipfs.config.set(ConfigHelper.JSON_AVATAR_NAME_KEY,fileName);
                }catch (Exception ioe){
                    logger.error(ioe.getMessage());
                }
                SEFL_BASE.setAvatarHash(avatarHash);
                SEFL_BASE.setAvatarSuffix(suffix);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上线广播
     */
    private void broadcastOnline(){
        if(boradcastSuccess)return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(SEFL_BASE==null||SEFL_BASE.getNick()==null){
                        TimeUnit.SECONDS.sleep(30);
                        broadcastOnline();
                    }else {
                        String ctrlMsg = Base64CodecUtil.encodeCtrlMsg(SEFL_BASE,Base64CodecUtil.CtrlTypes.online);
                        //logger.info("Send CTRL MSG : "+ctrlMsg);
                        Object o = ipfs.pubsub.pub(IPFSHelper.NBSWORLD_CTRL_TOPIC,ctrlMsg);
                        logger.info("TOPIC["+IPFSHelper.NBSWORLD_CTRL_TOPIC+" ]Send CTRL MSG : "+ctrlMsg);
                        boradcastSuccess = true;
                    }
                } catch (InterruptedException e) {
                    broadcastOnline();
                } catch (Exception e) {
                    logger.info(e.getMessage());
                    broadcastOnline();
                }
            }
        }).start();
    }

    /**
     * See yourself
     */
    private void subSelf(long sleepTimes){
       new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   if(StringUtils.isBlank(PEER_ID)){
                       TimeUnit.SECONDS.sleep(30);
                   }
                   if(sleepTimes>0l){ TimeUnit.MILLISECONDS.sleep(sleepTimes); }
                   logger.info("SUB SEFL :"+SEFL_BASE.getPeerID());
                   Stream<Map<String,Object>> sub = ipfs.pubsub.sub(SEFL_BASE.getPeerID());
                   List<Map> lst = sub.limit(1).collect(Collectors.toList());
                   String json = JSONParser.toString(lst.get(0));
                   logger.info(System.currentTimeMillis()+"-revc : "+json);
                   IpfsMessage message = JSON.parseObject(json,IpfsMessage.class);
                   //TODO 处理
                   updateContactsCache(message);
                   subSelf(sleepTimes);
               } catch (Exception e) {
                   e.printStackTrace();
                   logger.info(e.getMessage());
                   subSelf(sleepTimes);
               }
           }
       }).start();
    }

    public void updateContactsCache(IpfsMessage m){
        if(m==null)return;
        m = Base64CodecUtil.parseIpmsMessageCtrlType(m);
        switch (m.getTypes()){
            case online:

                //更新缓存
                updatePeerItem(m);
                break;
             default:
                break;
        }
    }

    /**
     *
     * @param m
     */
    public void updatePeerItem (IpfsMessage m){
        logger.info(m.getContents());
        PeerBoradcastInfo info = JSON.parseObject(m.getContents(),PeerBoradcastInfo.class);
        ContactsItem item = null;
        if(info.getId().equals(SEFL_BASE.getPeerID())){
            //自己不处理
            return;
        }
        if(peerItems.containsKey(info.getId())){
            item = peerItems.get(info.getId());
            item.setFormid(m.getFrom());
            if(info.getAvatarHash()!=null)item.setAvatar(info.getAvatarHash());
            if(info.getAvatarSuffix()!=null)item.setAvatarSuffix(info.getAvatarSuffix());
            item.setName(info.getNick());
        }else {
            item = new ContactsItem(info.getId(),info.getNick(),m.getFrom());
            item.setAvatar(info.getAvatarHash());
            item.setAvatarSuffix(info.getAvatarSuffix());
            peerItems.put(info.getId(),item);
        }
        safeAndFreshIM(item);
    }

    /**
     * 启用新线程下载头像，并存储
     * @param item
     */
    private void safeAndFreshIM(ContactsItem item){
        //TODO 通知
        new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO save sqlite
                String headRootPath = ConfigHelper.NBS_CACHE_AVATAR_ROOT_PATH;
                try {
                    if(StringUtils.isNotBlank(item.getAvatar())){
                        FileOutputStream fos ;
                        File headImage = new File(headRootPath,item.getAvatar()+item.getAvatarSuffix());
                        if(headImage.exists()){
                            headImage.delete();
                        }
                        headImage.createNewFile();
                        Multihash multihash = Multihash.fromBase58(item.getAvatar());
                        byte[] bytes = ipfs.get(multihash);
                        fos = new FileOutputStream(headImage);
                        fos.write(bytes);
                        fos.flush();
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 在缓存中查找
     * @param from
     * @return
     */
    public static ContactsItem findContactsItemByFrom(String from){
        ContactsItem item = null;
        for(Map.Entry<String,ContactsItem> entry : peerItems.entrySet()){
            item = entry.getValue();
            if(item.getFormid().equals(from)){
                return item;
            }
        }
        return null;
    }

    /**
     * 订阅世界主题
     */
    private void subWorld(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Stream<Map<String,Object>> subs = ipfs.pubsub.sub(IPFSHelper.NBSWORLD_CTRL_TOPIC);
                    List<Map<String,Object>> messages = subs.limit(1).collect(Collectors.toList());
                    logger.info(JSONParser.toString(messages));
                    //TODO
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
