package UI.panel.im;

import UI.AppMainWindow;
import UI.ConstantsUI;
import UI.button.NBSIconButton;
import UI.common.NBSAbstractPanel;
import UI.common.ToolbarStatsPanel;
import UI.templete.WihteBackJPanel;
import com.alibaba.fastjson.JSON;
import com.nbs.entity.ContactsItem;
import com.nbs.entity.PeerInfoBase;
import com.nbs.ipfs.IPFSHelper;
import com.nbs.ipfs.entity.IpfsMessage;
import com.nbs.tools.DateHelper;
import com.nbs.ui.components.ColorCnst;
import com.nbs.ui.panels.ChatPanel;
import com.nbs.utils.Base64CodecUtil;
import io.ipfs.api.IPFS;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @Package : UI.panel.im
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/15-13:10
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IMPanel extends NBSAbstractPanel {
    private static final long serialVersionUID = 1L;
    public static final String PKUI_PANEL_IM_LABEL = "nbs.ui.panel.im.label";
    private static IMPanel context;

    private boolean testRunning = false;
    public static AtomicInteger msgMax = new AtomicInteger(0);
    /**
     * 左侧IM peers
     */
    private ImLeftPanel leftPanel;
    /**
     * 右侧聊天面板
     */
    private  ChatPanel chatPanel;


    private static JTextArea imMSGShow = new JTextArea();
    /**
     * 顶部显示
     */
    private static ToolbarStatsPanel toolbarStatsPanel = new ToolbarStatsPanel(PKUI_PANEL_IM_LABEL);

    private static JTextArea inputArea = new JTextArea();

    /**
     *
     */
    private static ContactsItem CURRENT_TO_CONTACTS_ITEM = null;



    /**
     * 聊天主窗口，内部将包含：
     * 1.对话显示窗 center
     * 2.信息输入框 south
     *   inputArea  sendBtn
     */
    private static JPanel messPanel;

    protected static NBSIconButton sendButton = new NBSIconButton(ConstantsUI.ICON_SEND,ConstantsUI.ICON_SEND_ENABLED,ConstantsUI.ICON_SEND_DISABLED,"发送");
    protected static NBSIconButton testButton = new NBSIconButton(ConstantsUI.ICON_SEND,ConstantsUI.ICON_SEND_ENABLED,ConstantsUI.ICON_SEND_DISABLED,"发送TEST");


    public IMPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        context = this;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void addComponent() {
       // this.add(toolbarStatsPanel,BorderLayout.NORTH);
        /**
         *
         */
        this.add(getCenterPanel(),BorderLayout.CENTER);
    }



    /**
     * @Date    : 2018/6/22 9:59
     * @Author  : lanbery
     * @Decription :
     * <p></p>
     * @Param   :
     * @return 
     * @throws
     */
    private JPanel getCenterPanel(){
        WihteBackJPanel centerPanel = new WihteBackJPanel();
        centerPanel.setLayout(new BorderLayout());

        /**
         *
         */
        Dimension leftDimsnsion = new Dimension(260,ConstantsUI.MAIN_WINDOW_HEIGHT);
        leftPanel = new ImLeftPanel();
        //leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        leftPanel.setPreferredSize(leftDimsnsion);

        centerPanel.add(leftPanel,BorderLayout.WEST);

        /**
         * 聊天主窗口
         */
        //centerPanel.add(buildMessMainPanel(),BorderLayout.CENTER);
        chatPanel = new ChatPanel(this);
        centerPanel.add(chatPanel,BorderLayout.CENTER);
        return centerPanel;
    }

    @Override
    public void load() {

    }

    /**
     *
     */
    @Override
    protected void addListener() {
        sendButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });

        /**
         * 测试按钮TODO
         */
        testButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IPFS _ipfs = IPFSHelper.getInstance().getIpfs();
                Thread autoSendThread = new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            String text = "Welcome NBS World!";
                            while (testRunning){
                                try {
                                    TimeUnit.SECONDS.sleep(2);
                                    _ipfs.pubsub.pub(IPFSHelper.NBSWORLD_IMS_TOPIC,Base64CodecUtil.encode(text));
                                    logger.info(DateHelper.currentTime()+"SEND MSG : "+text);
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                );
                if(!testRunning){
                    testRunning = true;
                    testButton.setIcon(ConstantsUI.ICON_SEND_ENABLED);
                    autoSendThread.start();
                }else {
                    //testButton.setEnabled(false);
                    testButton.setIcon(ConstantsUI.ICON_SEND);
                    testRunning = false;
                }
            }
        });
    }



    /**
     * 上下结构
     * @return
     */
    private JPanel buildMessMainPanel(){
        messPanel = new WihteBackJPanel();
        messPanel.setLayout(new BorderLayout());
        int downHeight = 116;
        int upHeight = ConstantsUI.MAIN_WINDOW_HEIGHT -24;

        /**
         * 上部
         */
        WihteBackJPanel imupContainer = new WihteBackJPanel();
        imupContainer.setLayout(new BorderLayout());

        /**
         * JTextArea
         */
        imMSGShow.setBackground(ColorCnst.WINDOW_BACKGROUND_LIGHT);
        imMSGShow.setTabSize(4);
        imMSGShow.setFont(ConstantsUI.FONT_NORMAL);
        imMSGShow.setForeground(ColorCnst.DARK);
        imMSGShow.setLineWrap(true);
        imMSGShow.setWrapStyleWord(true);
        imMSGShow.setPreferredSize(new Dimension(ConstantsUI.MAIN_WINDOW_WIDTH-48-245,upHeight));
        imMSGShow.setEditable(false);

        //testText(300);
        JScrollPane imshowScroll = new JScrollPane(imMSGShow,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        imupContainer.add(imshowScroll,BorderLayout.CENTER);


        /**
         * 下部
         */
        WihteBackJPanel imDownContainer = new WihteBackJPanel();
        imDownContainer.setLayout(new BorderLayout());

        inputArea.setBounds(2,2,400,downHeight-4);
        inputArea.setBackground(ColorCnst.WINDOW_BACKGROUND_LIGHT);
        inputArea.setForeground(ColorCnst.DARKER);
        inputArea.setLineWrap(true);
        //inputArea.setText("Hello NBS.");
        imDownContainer.add(inputArea,BorderLayout.CENTER);

        /**
         *
         */
        WihteBackJPanel imOperPanel = new WihteBackJPanel();
        imOperPanel.setBackground(ColorCnst.WINDOW_BACKGROUND_LIGHT);
        Dimension bd = new Dimension(80,downHeight-4);
        imOperPanel.setPreferredSize(bd);
        imOperPanel.setLayout(new BorderLayout());
        imOperPanel.add(sendButton,BorderLayout.SOUTH);

        //
        //imOperPanel.add(testButton,BorderLayout.NORTH);
        imDownContainer.add(imOperPanel,BorderLayout.EAST);

        messPanel.add(imupContainer,BorderLayout.CENTER);
        messPanel.add(imDownContainer,BorderLayout.SOUTH);

        return messPanel;
    }

    private void testText(int rows){
        String s = "安徽的看法啥都好的萨拉客服哈打发到手机爱卡士大夫函数fd";
        if(rows<=0)rows=1;
        for(int i =0;i<rows;i++){
            if(i%2==0){
                imMSGShow.append(ConstantsUI.WSPACE_CHARACTER4);
            }
            imMSGShow.append(s);
            imMSGShow.append(ConstantsUI.ENTER_CHARACTER);
        }
        imMSGShow.revalidate();
        imMSGShow.updateUI();
    }

    /**
     *
     * 发送消息
     */
    private void sendMsg(){
        String sendContent = inputArea.getText();
        if(StringUtils.isBlank(sendContent))return;
        PeerInfoBase current = AppMainWindow.currentPeerInfo();
/*        if(CURRENT_TO_CONTACTS_ITEM == null){
            JOptionPane.showMessageDialog(AppMainWindow.frame,"请选中联系人");
            return;
        }*/
        String topic = IPFSHelper.NBSWORLD_IMS_TOPIC;

        try {
            StringBuffer sb = new StringBuffer();
            sb.append(current.getNick()).append("  ").append(DateHelper.currentTime());
            sb.append(ConstantsUI.ENTER_CHARACTER);
            sb.append(ConstantsUI.WSPACE_CHARACTER4).append(sendContent);

            //send pub
            AppMainWindow.ipfs.pubsub.pub(topic,Base64CodecUtil.encodeByCtrlType(sendContent,Base64CodecUtil.CtrlTypes.normal));
            inputArea.setText("");
            sb.append(ConstantsUI.ENTER_CHARACTER);
            if(msgMax.intValue()>18){
                msgMax.set(0);
                imMSGShow.setText("");
                //清空
            }
            int next = msgMax.intValue() +1;
            msgMax.set(next);
            imMSGShow.append(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            String error = ConstantsUI.WSPACE_CHARACTER4+"发送失败....."+ConstantsUI.ENTER_CHARACTER;
            imMSGShow.append(error);
        }
    }



    /**
     *
     * @param json
     */
    private void appenRevcMsg(String json){
        IpfsMessage message = JSON.parseObject(json,IpfsMessage.class);
        ContactsItem item = AppMainWindow.findContactsItemByFrom(message.getFrom());
        String nick = (item==null||item.getName()==null) ? message.getFrom() : item.getName();
        message = Base64CodecUtil.parseIpmsMessageCtrlType(message);
        if(message==null||message.getTypes()!=Base64CodecUtil.CtrlTypes.normal){
            logger.info("message is null");
            return;
        }
        String msg = message.getContents();
        StringBuffer sb = new StringBuffer();
        sb.append(nick).append("  ").append(DateHelper.currentTime()).append(ConstantsUI.ENTER_CHARACTER);
        sb.append(ConstantsUI.WSPACE_CHARACTER4).append(msg).append(ConstantsUI.ENTER_CHARACTER);
        imMSGShow.append(sb.toString());
        //TODO save SQLite
    }

    public static void setCurrentToPeer(ContactsItem item){
        CURRENT_TO_CONTACTS_ITEM = item;
    }

    /**
     * 切换聊天peer
     */
    public void contactsItemChanged(ContactsItem item){
        //TODO
        CURRENT_TO_CONTACTS_ITEM = item;
        if(item==null)return;
        toolbarStatsPanel.resetContacts(item.getName());
    }

    public static void appendMsgShow(List<ContactsItem> items,IpfsMessage ipfsMessage){
        if(ipfsMessage==null)return;
        if(msgMax.intValue()>18){
            msgMax.set(0);
            imMSGShow.setText("");
            //清空
        }
        int next = msgMax.intValue() +1;
        msgMax.set(next);
        String formid = ipfsMessage.getFrom();
        String nick = formid;
        //
        if(items!=null&&items.size()>0){
            for(ContactsItem item : items){
                if(item.getFormid().equals(formid)){
                    nick = item.getName();
                    break;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(nick).append(">>").append(ipfsMessage.getTime()).append(ConstantsUI.ENTER_CHARACTER);
        sb.append(ConstantsUI.WSPACE_CHARACTER4).append(ipfsMessage.getContents()).append(ConstantsUI.ENTER_CHARACTER);
        imMSGShow.append(sb.toString());
    }

    /**
     *
     * @return
     */
    public static IMPanel getContext() {
        return context;
    }

}
