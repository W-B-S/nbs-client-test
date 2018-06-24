package UI;

import UI.panel.*;
import UI.panel.about.AboutPanel;
import UI.panel.fm.FilePanel;
import UI.panel.im.IMPanel;
import UI.panel.setting.SettingPanel;
import com.alibaba.fastjson.JSON;
import com.nbs.entity.ContactsItem;
import com.nbs.entity.PeerBoradcastInfo;
import com.nbs.ipfs.IPFSHelper;
import com.nbs.ipfs.entity.IpfsMessage;
import com.nbs.tools.ConfigHelper;
import com.nbs.utils.Base64CodecUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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

    public static PeerBoradcastInfo self = null;

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
        broadcastOnline();
        subSelf(400);
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
         *
         */

        File ipfsDir = new File(ConfigHelper.NBS_FILES_IPFS_ROOT);
        if(ipfsDir.isDirectory()&& !ipfsDir.exists()){
            ipfsDir.mkdirs();
        }

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
            String n = initNick();
            self = new PeerBoradcastInfo((String)map.get("ID"),n);
            //avatar must below init nick
            initAvatar();
        }catch (Exception e){
            logger.error("ipfs Server is dead.",e.getCause());
            ipfs = new IPFS(ConfigHelper.getIpfsAddress());
        }

    }


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
                self.setAvatarHash(avatarHash);
                self.setAvatarSuffix(suffix);
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
                    if(self==null||self.getNick()==null){
                        TimeUnit.SECONDS.sleep(30);
                        broadcastOnline();
                    }else {
                        String ctrlMsg = Base64CodecUtil.encodeCtrlMsg(self,Base64CodecUtil.CtrlTypes.online);
                        logger.info("Send CTRL MSG : "+ctrlMsg);
                        ipfs.pubsub.pub(IPFSHelper.NBSWORLD_CTRL_TOPIC,ctrlMsg);
                        logger.info(IPFSHelper.NBSWORLD_CTRL_TOPIC+"Send CTRL MSG : "+ctrlMsg);
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
                   logger.info("SUB SEFL :"+self.getId());
                   Stream<Map<String,Object>> sub = ipfs.pubsub.sub(self.getId());
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

    public void updatePeerItem (IpfsMessage m){
        logger.info(m.getContents());
        PeerBoradcastInfo info = JSON.parseObject(m.getContents(),PeerBoradcastInfo.class);
        ContactsItem item = null;
        if(info.getId().equals(self.getId())){
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
}
