package io.ipfs.nbs.ui.frames;

import com.nbs.biz.PeersOperatorService;
import com.nbs.biz.service.TableService;
import com.nbs.ui.components.VerticalFlowLayout;
import com.nbs.ui.listener.AbstractMouseListener;
import io.ipfs.api.IPFS;
import io.ipfs.api.JSONParser;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.nbs.Launcher;
import io.ipfs.nbs.cnsts.AppGlobalCnst;
import io.ipfs.nbs.cnsts.ColorCnst;
import io.ipfs.nbs.cnsts.FontUtil;
import io.ipfs.nbs.cnsts.OSUtil;
import io.ipfs.nbs.helper.ConfigurationHelper;
import io.ipfs.nbs.helper.HttpURLImageHelper;
import io.ipfs.nbs.peers.PeerInfo;
import io.ipfs.nbs.ui.components.GBC;
import io.ipfs.nbs.ui.components.NBSButton;
import io.ipfs.nbs.utils.DataBaseUtil;
import io.ipfs.nbs.utils.IconUtil;
import io.ipfs.nbs.utils.RadomCharactersHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @Package : io.ipfs.nbs.ui.frames
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/29-18:34
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class InitialFrame extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(InitialFrame.class);

    private static final int W = 550;
    private static final int H = 350;


    private IPFS ipfs;
    private static Point origin = new Point();

    private SqlSession sqlSession;
    private TableService tableService;
    private PeersOperatorService operatorService;
    private final static String DEFAULT_NICK_PREFFIX = "NBSChain_";

    private final static String TIP = "点击头像图标或下方按钮可上传头像.";

    /**
     *
     */
    private JPanel ctrlPanel;
    private JPanel editPanel;
    private JLabel closeLabel;
    private JTextArea peerIdText;
    private JTextField nickField;
    private JPanel buttonPanel;

    private NBSButton initButton;
    private NBSButton cancleButton;
    private JLabel statusLabel;
    private JPanel statusPanel;
    private JLabel avatarLabel;


    private NBSButton avatarButton;

    private PeerInfo tempInfo;

    private JFileChooser fileChooser;

    private String upFileName;
    public InitialFrame(IPFS ipfs){
        this.ipfs = ipfs;
        /**
         * first
         */
        initWorkDist();
        /**
         * initService 保持最先加载 second
         */
        initService();

        initComponents();
        initView();
        setListeners();
        centerScreen();
    }

    /**
     *
     */
    private void initComponents(){
        Dimension windowSize = new Dimension(W, H);
        setMinimumSize(windowSize);
        setMaximumSize(windowSize);


        ctrlPanel = new JPanel();
        ctrlPanel.setLayout(new GridBagLayout());

        JPanel closePanel = new JPanel();
        closePanel.setLayout(new FlowLayout(FlowLayout.RIGHT,10,5));
        closeLabel = new JLabel();
        closeLabel.setIcon(IconUtil.getIcon(this,"/icons/close.png"));
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closePanel.add(closeLabel);

        /**
         * 标题
         */
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,2));
        //titlePanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("欢迎加入NBS Chain，请设置信息.");
        titleLabel.setHorizontalAlignment(JLabel.LEFT);
        titleLabel.setFont(FontUtil.getDefaultFont(18));
        titlePanel.add(titleLabel);

        ctrlPanel.add(titlePanel,
                new GBC(0,0).setWeight(6,1).setFill(GBC.HORIZONTAL).setInsets(0,0,0,0));
        ctrlPanel.add(closePanel,
                new GBC(1,0).setWeight(1,1).setFill(GBC.HORIZONTAL).setInsets(0,40,30,0));


        /**
         * 内容编辑区
         */
        editPanel = new JPanel();
        editPanel.setLayout(new GridBagLayout());


        JPanel editLeft = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                Image icon = getIconImage();
                ImageIcon imageIcon = new ImageIcon(icon);
                if(icon != null){
                   g.drawImage(icon,0,0,getWidth(),getHeight(),imageIcon.getImageObserver());
                }
                super.paintComponent(g);

            }
        };
        editLeft.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,15,20,false,false));
        avatarLabel = new JLabel();
        avatarLabel.setIcon(IconUtil.getIcon(this,"/icons/nbs128.png"));
        avatarLabel.setPreferredSize(new Dimension(128,128));
        avatarButton = new NBSButton("上传头像",ColorCnst.MAIN_COLOR,ColorCnst.MAIN_COLOR_DARKER);
        avatarButton.setPreferredSize(new Dimension(100,25));

        editLeft.add(avatarLabel);
        editLeft.add(avatarButton);

        JPanel editRight = new JPanel();
        editRight.setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEADING,10,25,false,false));



        JPanel peerLabelPanel = new JPanel();
        peerLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,2));
        JLabel peerLabel = new JLabel("Peer ID:");
        peerLabel.setFont(FontUtil.getDefaultFont(15));
        peerLabel.setHorizontalAlignment(JLabel.RIGHT);
        peerLabel.setPreferredSize(new Dimension(60,35));
        peerLabelPanel.add(peerLabel);

        peerIdText = new JTextArea();
        peerIdText.setPreferredSize(new Dimension(245,35));
        peerIdText.setFont(FontUtil.getDefaultFont(13));
        peerIdText.setForeground(ColorCnst.FONT_GRAY);
        peerIdText.setLineWrap(true);
        peerIdText.setEditable(false);

        JPanel nickPanel = new JPanel();
        nickPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,2));
        JLabel nickLabelTitle = new JLabel("昵称:");
        nickLabelTitle.setHorizontalAlignment(JLabel.RIGHT);
        nickLabelTitle.setFont(FontUtil.getDefaultFont(15));
        nickLabelTitle.setPreferredSize(new Dimension(60,30));

        nickField = new JTextField();
        nickField.setForeground(ColorCnst.FONT_GRAY_DARKER);
        nickField.setHorizontalAlignment(JLabel.LEFT);
        nickField.setFont(FontUtil.getDefaultFont(15));
        nickField.setPreferredSize(new Dimension(245,30));

        nickPanel.add(nickLabelTitle);
        nickPanel.add(nickField);

        peerLabelPanel.add(peerIdText);
        editRight.add(peerLabelPanel);

        editRight.add(nickPanel);

        /**
         * 放置编辑
         */
        editPanel.add(editLeft,
                new GBC(0,0).setWeight(1,1).setFill(GBC.BOTH).setInsets(0,10,0,0));

        editPanel.add(editRight,
                new GBC(1,0).setWeight(7,1).setFill(GBC.BOTH).setInsets(0,0,0,10));

        /**
         * 按钮区
         * 2行
         */
        statusPanel = new JPanel();
        statusLabel = new JLabel();
        statusLabel.setFont(FontUtil.getDefaultFont(14));
        statusLabel.setForeground(ColorCnst.RED);
        statusLabel.setVisible(true);
        statusPanel.add(statusLabel);

        buttonPanel = new JPanel();
        initButton = new NBSButton("保存",ColorCnst.MAIN_COLOR,ColorCnst.MAIN_COLOR_DARKER);
        initButton.setFont(FontUtil.getDefaultFont(14));
        initButton.setPreferredSize(new Dimension(115,35));

        cancleButton = new NBSButton("关闭",ColorCnst.FONT_GRAY_DARKER,ColorCnst.DARK);
        cancleButton.setFont(FontUtil.getDefaultFont(14));
        cancleButton.setPreferredSize(new Dimension(115,35));


    }


    private void initView(){
        //frame
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new LineBorder(ColorCnst.LIGHT_GRAY));
        contentPanel.setLayout(new GridBagLayout());

        //添加顶部操作
        if(OSUtil.getOsType() != OSUtil.Mac_OS){
            setUndecorated(true);
            contentPanel.add(ctrlPanel,
                    new GBC(0,0).setFill(GBC.HORIZONTAL).setWeight(1,1).setInsets(0,0,10,0));
        }


        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
        buttonPanel.add(initButton);
        buttonPanel.add(cancleButton);

        //
        if(tempInfo!=null){
            String pid = tempInfo.getId()==null ? "" :tempInfo.getId();
            peerIdText.setText(pid);
            if( tempInfo.getNick()!=null)nickField.setText(tempInfo.getNick());
        }


        //statusLabel.setText("IPFS 初始化错误.");

        add(contentPanel);
       // setTitle("欢迎加入NBS Chain，请设置信息.");

        /**
         * fill
         */
        contentPanel.add(editPanel,
                new GBC(0,1).setWeight(1,6).setFill(GBC.BOTH).setInsets(0,0,0,0));
        contentPanel.add(statusPanel,
                new GBC(0,2).setWeight(1,1).setFill(GBC.BOTH).setInsets(5,0,0,0));
        contentPanel.add(buttonPanel,
                new GBC(0,3).setWeight(1,1).setFill(GBC.BOTH).setInsets(5,0,10,0));

    }

    private void setListeners(){
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(1);
                super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                closeLabel.setBackground(ColorCnst.LIGHT_GRAY);
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeLabel.setBackground(ColorCnst.WINDOW_BACKGROUND);
                super.mouseExited(e);
            }
        });

        if (OSUtil.getOsType() != OSUtil.Mac_OS)
        {
            addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent e)
                {
                    // 当鼠标按下的时候获得窗口当前的位置
                    origin.x = e.getX();
                    origin.y = e.getY();
                }
            });

            addMouseMotionListener(new MouseMotionAdapter()
            {
                public void mouseDragged(MouseEvent e)
                {
                    // 当鼠标拖动时获取窗口当前位置
                    Point p = InitialFrame.this.getLocation();
                    // 设置窗口的位置
                    InitialFrame.this.setLocation(p.x + e.getX() - origin.x, p.y + e.getY()
                            - origin.y);
                }
            });
        }

        cancleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });

        /**
         * 保存按钮
         */
        initButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean complete = false;
                /**
                 * 1.ipfs config
                 */
                boolean b = saveIpfsConfig(upFileName);
                /**
                 * 保存数据库
                 */
                if(b){
                    complete = operatorService.initSaveSelf(tempInfo,"首次登陆初始化");
                }
                if(complete){
                    openMainFrame();
                }else {
                    statusLabel.setText("保存失败，请重试.");
                    statusLabel.setVisible(true);
                    statusLabel.updateUI();
                }
            }
        });


        avatarLabel.addMouseListener(new AbstractMouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                uploadAvatar();
                super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                statusLabel.setText(TIP);
                statusLabel.setVisible(true);
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                statusLabel.setVisible(false);
                super.mouseExited(e);
            }
        });

        avatarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadAvatar();
            }
        });
    }
    private void openMainFrame(){
        //Launcher.setCurrentPeer(tempInfo);
        this.dispose();
        MainFrame frame = new MainFrame(tempInfo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }


    /**
     * 更新到IPFS Config
     * @param originAvatarName
     * @return
     */
    private boolean saveIpfsConfig(String originAvatarName){
        if(tempInfo==null||tempInfo.getId()==null||StringUtils.isBlank(tempInfo.getFrom()))return false;
        try {
            ipfs.config.set(ConfigurationHelper.JSON_NICKNAME_KEY,tempInfo.getNick());
            ipfs.config.set(ConfigurationHelper.JSON_CFG_FROMID_KEY,tempInfo.getFrom());
            ipfs.config.set(ConfigurationHelper.JSON_AVATAR_KEY,tempInfo.getAvatar());
            ipfs.config.set(ConfigurationHelper.JSON_AVATAR_SUFFIX_KEY,tempInfo.getAvatarSuffix());
            if(StringUtils.isNotBlank(originAvatarName))ipfs.config.set(ConfigurationHelper.JSON_AVATAR_NAME_KEY,originAvatarName);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     *
     */
    private void initService(){
        sqlSession = DataBaseUtil.getSqlSession();
        tableService = new TableService(sqlSession);
        tableService.initClientDB();

        operatorService = new PeersOperatorService(sqlSession);

        tempInfo = new PeerInfo();
        //获取fromid
        try {
            Map peerMap = ipfs.id();
            String randomNick = RadomCharactersHelper.getInstance().generated(DEFAULT_NICK_PREFFIX,6);
            tempInfo.setNick(randomNick);
            if(peerMap.containsKey("ID")){
                String peerId = peerMap.get("ID").toString();
                tempInfo.setId(peerId);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        getFromid(tempInfo);
    }

    private void saveDBPContacts(PeerInfo peerInfo){
        if(peerInfo==null)return;


    }

    private void getFromid(PeerInfo info){
        String tmpTopic = RadomCharactersHelper.getInstance().generated(8);

        try {
            Stream<Map<String,Object>> subs = ipfs.pubsub.sub(tmpTopic);
            logger.info("get from id topic {}",tmpTopic);
            ipfs.pubsub.pub(tmpTopic,info.getId());
            ipfs.pubsub.pub(tmpTopic,info.getId());

            List<Map<String, Object>> lst = subs.limit(1).collect(Collectors.toList());
            Object fromidObj = JSONParser.getValue(lst.get(0),"from");
            if(fromidObj!=null){
                info.setFrom((String)fromidObj);
            }
        } catch (Exception e) {
            logger.error("获取消息失败,{}",e.getMessage());
        }
    }

    private void centerScreen()
    {
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocation((tk.getScreenSize().width - W) / 2,
                (tk.getScreenSize().height - H) / 2);
    }


    private void initWorkDist(){
        /**
         *
         */
         String tempBase = Launcher.CURRENT_DIR+Launcher.FILE_SEPARATOR + AppGlobalCnst.TEMP_FILE;
         String avatarCache = AppGlobalCnst.consturactPath(Launcher.appBasePath,"cache","avatars");
         File tmpFile = new File(tempBase);
         if(!tmpFile.exists())tmpFile.mkdirs();
         File avatarFile = new File(avatarCache);
         if(!avatarFile.exists())avatarFile.mkdirs();
    }
    /**
     * 上传头像
     */
    private void uploadAvatar(){
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.showDialog(this,"选择图片");
       // fileChooser.setFileFilter(new ImageFileFilter());
        File file = fileChooser.getSelectedFile();
        if(file!=null){
            //上传前先压缩
            new Thread(()->{
                List<MerkleNode> nodes;
                NamedStreamable.FileWrapper fileWrapper = new NamedStreamable.FileWrapper(file);
                FileOutputStream fos = null;
                try {
                    nodes = ipfs.add(fileWrapper);
                    String fileHash = nodes.get(0).hash.toBase58();
                    String name = file.getName();
                    tempInfo.setAvatar(fileHash);
                    tempInfo.setAvatarSuffix(name.substring(name.lastIndexOf(".")));
                    //TODO 存数据库upload
                    String avatarFileName = fileHash+ name.substring(name.lastIndexOf("."));

                    URL url = new URL("http://127.0.0.1:8080/ipfs/"+fileHash);
                    String avatarCachePath = AppGlobalCnst.consturactPath(Launcher.appBasePath,"cache","avatars");
                    File avatarFile = new File(avatarCachePath,avatarFileName);
                    HttpURLImageHelper httpURLImageHelper = HttpURLImageHelper.getInstance();
                    try {
                        File localFile = httpURLImageHelper.saveFileFromUrl(url,avatarFileName);
                        //图片压缩TODO
                        Image image = ImageIO.read(localFile);
                        ImageIcon icon = httpURLImageHelper.generateThumbScale(image,fileHash+tempInfo.getAvatarSuffix(),128);
                        if(icon!=null){
                            logger.info(fileHash);
                            avatarLabel.setIcon(icon);
                            avatarLabel.updateUI();
                            upFileName = file.getName();
                        }
                    } catch (Exception e) {
                        logger.info(e.getMessage());
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                   logger.error("上传失败：{}",e.getMessage());
                   statusLabel.setText(e.getMessage());
                   statusPanel.setVisible(true);
                }
            }).start();
        }
    }
}
