package UI;

import UI.panel.*;
import UI.panel.about.AboutPanel;
import UI.panel.fm.FilePanel;
import UI.panel.im.IMPanel;
import UI.panel.setting.SettingPanel;
import com.alibaba.fastjson.JSON;
import com.nbs.biz.ImPeersService;
import io.nbs.client.vo.ContactsItem;
import com.nbs.entity.PeerBoradcastInfo;
import com.nbs.entity.PeerInfoBase;
import com.nbs.ipfs.IPFSHelper;
import com.nbs.ipfs.entity.IpfsMessage;
import com.nbs.tools.ConfigHelper;
import com.nbs.tools.PropertyUtil;
import com.nbs.ui.frames.MainFrameOld;
import UI.common.Base64CodecUtil;
import io.nbs.commons.utils.BaseURLUtil;
import io.nbs.commons.helper.RadomCharactersHelper;
import io.ipfs.api.IPFS;
import io.ipfs.api.JSONParser;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import org.apache.commons.lang3.StringUtils;
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
import java.util.concurrent.atomic.AtomicInteger;
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

    public static MainFrameOld frame;
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
     * 在线peer缓存
     */
    List<ContactsItem> contactsItems = new ArrayList<>();
    /**
     * 当前信息
     *
     */
    private static PeerInfoBase SEFL_BASE = null;
   // public static String CURRENT_NICK = "";

    protected static  IPFSHelper ipfsHelper = IPFSHelper.getInstance();
    /**
     *
     */
    public static IPFS ipfs = IPFSHelper.getInstance().getIpfs();

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
        String basedir = System.getProperty("app.home");
        System.out.println("nbs-client-dir>>>>>>>>>>"+basedir);
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
       // subCtrlWorld();
        //广播自己上线
        broadcastOnline();
        ImPeersService imPeersService = new ImPeersService();
        /**
         * 加载在线缓存
         */
        loadWorldControllerListener(imPeersService);

    }

    /**
     *
     */
    private void loadWorldControllerListener(ImPeersService service){

        List<Map<String,Object>> ctrlMSGSub = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger size = new AtomicInteger(0);
        new Thread(()->{
            logger.info("Start up the World Controller Message........");
            IPFS worldIpfs = new IPFS(ConfigHelper.getInstance().getIpfsAddress());
            while (true){
                try{
                    TimeUnit.MILLISECONDS.sleep(100);
                    ipfs.pubsub.sub(IPFSHelper.NBSWORLD_CTRL_TOPIC,ctrlMSGSub::add,t->t.printStackTrace());
                }catch (Exception e){
                    logger.error(e.getMessage());
                }
            }
        }).start();
        //处理消息
        new Thread(()->{
            int i = 0;
            while (true){
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                    int currentSize = ctrlMSGSub.size();
                    int diff = currentSize-size.intValue();
                    if(diff>1){
                        for(int j = size.intValue();j<currentSize;j++){
                            //处理用户列表
                        }
                        size.set(currentSize);
                    }else if(diff==1){

                        size.set(currentSize);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 初始化frame内容
     */
    private void initialize(){
        logger.info("NBS Client start ...");
        loadEnv();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException
                | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame = new MainFrameOld();
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
        logger.info("NBS start complete ...");

    }

    /**
     * 加载环境配置
     */
    private void loadEnv(){
        logger.info(BaseURLUtil.getAppBaseDir());
        logger.info(BaseURLUtil.getAppJarPath());
        logger.info("=====>>>>>>> NBS Chain Client4J ENV initializing......");
        Properties props = ConfigHelper.getInstance().getEnv();
        for(String k : props.stringPropertyNames()){
            String v = props.getProperty(k);
            logger.info(k+"="+v);
        }

        /**
         * 初始化文件目录
         */
        File ipfsDir = new File(ConfigHelper.NBS_FILES_IPFS_ROOT);
        if(ipfsDir.isDirectory()&& !ipfsDir.exists()){
            ipfsDir.mkdirs();
        }

        /**
         * 初始化
         */
        File f = new File(ConfigHelper.NBS_CACHE_AVATAR_ROOT_PATH);
        if(!f.exists()){
            f.mkdirs();
        }

        /**
         * 构造id SELF
         */
        while (SEFL_BASE==null){
            SEFL_BASE = initCurrentPeerInfo();
        }
        SERVER_STAT = true;
        String s = PropertyUtil.getProperty("PKUI_PANEL_ABOUT_LABEL");
        logger.info(ConstantsUI.BUTTON_ICON_ROOT);
        logger.info("=====>>>>>>> NBS Chain Client4J ENV initialized Success......");
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
                File defaultAvatarImage = new File(ConfigHelper.PROFILE_ROOT+ "defaults/nbs.png");
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
                        String ctrlMsg = Base64CodecUtil.encode(JSON.toJSONString(SEFL_BASE));
                        Object o = ipfs.pubsub.pub(IPFSHelper.NBSWORLD_IMS_TOPIC,ctrlMsg);
                        logger.info("TOPIC["+IPFSHelper.NBSWORLD_IMS_TOPIC+" ]Send CTRL MSG : "+ctrlMsg);
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
     *
     * @return
     */
    public static PeerInfoBase currentPeerInfo(){
        if(SEFL_BASE!=null)return SEFL_BASE;
        SEFL_BASE = initCurrentPeerInfo();
        return SEFL_BASE;
    }

    /**
     * 获取当前的nick
     * @return
     */
    public static String selfNick(){
        if(SEFL_BASE==null){
            SEFL_BASE = initCurrentPeerInfo();
        }
        return SEFL_BASE.getNick();
    }

    /**
     * 获取当前用户信息
     * @return
     */
    private static PeerInfoBase initCurrentPeerInfo(){
        if(SEFL_BASE!=null &&
                StringUtils.isNotBlank(SEFL_BASE.getNick()) &&
                StringUtils.isNotBlank(SEFL_BASE.getFrom()))return SEFL_BASE;
        String peerid = getPeerId();
        if(peerid==null)return null;

        String _nick = _getNickName();
        if(_nick==null)return null;

        SEFL_BASE  = new PeerInfoBase(peerid,_nick);
        //未来增加本地SQLite读取
        while (StringUtils.isBlank(SEFL_BASE.getFrom())){
            _buildFrom(SEFL_BASE);
        }
        return SEFL_BASE;
    }

    /**
     *
     * @param base
     */
    private static void _buildFrom(PeerInfoBase base){
        /**
         *
         */
        String topic = Base64CodecUtil.encode("$SELF.FROM.ID$GET");
                //RadomCharactersHelper.getInstance().generated("$SELF.N.S",8);
        new Thread(()->{
            try {
                Stream<Map<String, Object>> checkSelf = ipfs.pubsub.sub(topic);
                List<Map<String, Object>> res = checkSelf.limit(1).collect(Collectors.toList());
                IpfsMessage ipfsMessage = JSON.parseObject(JSONParser.toString(res.get(0)),IpfsMessage.class);
                SEFL_BASE.setFrom(ipfsMessage.getFrom());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        try {
            ipfs.pubsub.pub(topic,SEFL_BASE.getPeerID());
        } catch (Exception e) {
        }
    }

    private static String _getNickName(){
        String tempNick = RadomCharactersHelper.getInstance().generated("NBSChain_",6);
        try {
            Map cfgMap = ipfs.config.show();
            if(cfgMap.containsKey(ConfigHelper.JSON_NICKNAME_KEY)){
                Object no = cfgMap.get(ConfigHelper.JSON_NICKNAME_KEY);
                if(no!=null&&no.toString().trim().length()<=0){
                    Map res = ipfs.config.set(ConfigHelper.JSON_NICKNAME_KEY,tempNick);
                    return tempNick;
                }else {
                    return no.toString();
                }
            }else {
                Map res = ipfs.config.set(ConfigHelper.JSON_NICKNAME_KEY,tempNick);
                return tempNick;
            }
        } catch (IOException e) {
            logger.error("GET NICKNAME ERROR :",e.getMessage());
            return null;
        }
    }

    /**
     * 获取PEER ID
     * @return
     */
    private static String getPeerId(){
        try {
            Map map = ipfs.id();
            if(map.containsKey("ID")){
                return (String)map.get("ID");
            }
        } catch (IOException e) {
            logger.error("获取 PEER ID ：",e.getMessage());
        }
        return null;
    }


    /**-------------------------------------------------*/
    /**
     * 订阅世界主题
     * 控制
     */
    private void subCtrlWorld(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Stream<Map<String,Object>> subs = ipfs.pubsub.sub(IPFSHelper.NBSWORLD_CTRL_TOPIC);
                    List<Map<String,Object>> messages = subs.limit(1).collect(Collectors.toList());
                    logger.info(JSONParser.toString(messages));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
