package UI.panel.im;

import UI.AppMainWindow;
import UI.ConstantsUI;
import UI.button.NBSIconButton;
import UI.common.NBSAbstractPanel;
import UI.common.ToolbarStatsPanel;
import UI.templete.WihteBackJPanel;
import com.nbs.entity.PeerInfoBase;
import com.nbs.tools.DateHelper;
import com.nbs.ui.components.ColorCnst;
import io.ipfs.api.IPFS;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

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

    /**
     * 在线peers
     */
    private static JPanel peersJPanel;

    private static JPanel peerList;

    private static JScrollPane scrollPane;

    private static JTextArea imMSGShow = new JTextArea();
    /**
     *
     */
    private static JTextPane msgTextPane = new JTextPane();

    private static JTextArea inputArea = new JTextArea();

    private PeerInfoBase currentContactPeer = null;



    /**
     * 聊天主窗口，内部将包含：
     * 1.对话显示窗 center
     * 2.信息输入框 south
     *   inputArea  sendBtn
     */
    private static JPanel messPanel;

    protected static NBSIconButton sendButton = new NBSIconButton(ConstantsUI.ICON_SEND,ConstantsUI.ICON_SEND_ENABLED,ConstantsUI.ICON_SEND_DISABLED,"发送");
    protected static NBSIconButton sendTest = new NBSIconButton(ConstantsUI.ICON_SEND,ConstantsUI.ICON_SEND_ENABLED,ConstantsUI.ICON_SEND_DISABLED,"发送TEST");


    public IMPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void addComponent() {
        ToolbarStatsPanel toolbarStatsPanel = new ToolbarStatsPanel(PKUI_PANEL_IM_LABEL);
        this.add(toolbarStatsPanel,BorderLayout.NORTH);
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

        peerList = new JPanel();
        peerList.setBackground(new Color(221,221,221));
        Dimension leftDimsnsion = new Dimension(245,ConstantsUI.MAIN_WINDOW_HEIGHT);
        peerList.setPreferredSize(leftDimsnsion);
        peerList.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));

        centerPanel.add(peerList,BorderLayout.WEST);

        /**
         * 聊天主窗口
         */
        centerPanel.add(buildMessMainPanel(),BorderLayout.CENTER);
        return centerPanel;
    }

    @Override
    public void load() {

    }

    private void refreshPeers(){

    }

    /**
     * 加载在线节点
     * @return
     */
    public boolean loadOnlinePeers(){
        if(!AppMainWindow.SERVER_STAT)return false;
        IPFS ipfs = AppMainWindow.ipfs;

        return true;
    }

    @Override
    protected void addListener() {
        sendButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });
    }

    private void buildLeftPeerContainer(){

    }

    /**
     * 上下结构
     * @return
     */
    private JPanel buildMessMainPanel(){
        messPanel = new WihteBackJPanel();
        messPanel.setLayout(new BorderLayout());
        int downHeight = 116;
        int upHeight = ConstantsUI.MAIN_WINDOW_HEIGHT -downHeight-4;

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
        //imOperPanel.add(sendTest,BorderLayout.NORTH);
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
     * 发送消息
     */
    private void sendMsg(){
        String sendContent = inputArea.getText();
        if(StringUtils.isBlank(sendContent))return;

        try {
            StringBuffer sb = new StringBuffer();
            sb.append(AppMainWindow.PROFILE_NICKNAME).append("  ").append(DateHelper.currentTime());
            sb.append(ConstantsUI.ENTER_CHARACTER);
            sb.append(ConstantsUI.WSPACE_CHARACTER4).append(sendContent);
            //send pub
            AppMainWindow.ipfs.pubsub.pub("nbs",sendContent);
            inputArea.setText("");
            sb.append(ConstantsUI.ENTER_CHARACTER);
            imMSGShow.append(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }





    }
}
