package io.nbs.client.ui.panels.im;

import com.nbs.ui.components.NbsBorder;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.services.IpfsMessageSender;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.im.chatstmp.TMChatShowPanel;
import io.nbs.commons.helper.RadomCharactersHelper;
import io.nbs.sdk.beans.IMMessageBean;
import io.nbs.sdk.beans.MessageItem;
import io.ipfs.nbs.helper.MessageViewHolderCacheHelper;
import io.nbs.client.ui.panels.im.messages.MessageEditorPanel;
import io.nbs.client.ui.panels.im.messages.MessagePanel;
import io.nbs.client.adapter.MessageAdapter;
import io.nbs.client.ui.components.GBC;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.sdk.beans.PeerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package : com.nbs.ui.panels
 * @Description :
 * <p>右侧聊天面板</p>
 * @Author : lambor.c
 * @Date : 2018/6/26-21:13
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ChatPanel extends ParentAvailablePanel {

    private static Logger logger = LoggerFactory.getLogger(ChatPanel.class);

    private static ChatPanel context;

    private MessagePanel messagePanel;

    private MessageEditorPanel messageEditorPanel;

    // 每次加载的消息条数
    private static final int PAGE_LENGTH = 20;

    /**
     * TOPIC ID
     */
    public static String CHAT_ROOM_OPEN_ID = "";

    private static List<String> remoteHistoryLoadedRooms = new ArrayList<>();

    private java.util.List<MessageItem> messages = new ArrayList<>();

    List<IMMessageBean> messageBeanList = new ArrayList<>();

    private MessageViewHolderCacheHelper messageViewHolderCacheHelper;
    private MessageAdapter adapter;

    private TMChatShowPanel chatShowPanel;
    private PeerInfo current;

    /**
     *
     * @param parent
     */
    public ChatPanel(JPanel parent) {
        super(parent);
        context = this;
        current = MainFrame.getContext().getCurrentPeer();
        messageViewHolderCacheHelper = new MessageViewHolderCacheHelper();
        initComponents();
        initView();
        setListeners();
        initData();
    }

    private void initComponents(){
        messagePanel = new MessagePanel(this);
        messagePanel.setBorder(new NbsBorder(NbsBorder.BOTTOM,ColorCnst.LIGHT_GRAY));

        adapter = new MessageAdapter(messages,messagePanel.getListView(),messageViewHolderCacheHelper);
        messagePanel.getListView().setAdapter(adapter);

//        chatShowPanel = new TMChatShowPanel(this);
//        chatShowPanel.setBorder(null);

        messageEditorPanel = new MessageEditorPanel(this);
        //messageEditorPanel.setPreferredSize(new Dimension(MainFrame.DEFAULT_WIDTH*2/5,200));

    }
    private void initView(){

        this.setLayout(new GridBagLayout());
        add(messagePanel,new GBC(0,0).setFill(GBC.BOTH).setWeight(1,7));
        //add(chatShowPanel,new GBC(0,0).setFill(GBC.BOTH).setWeight(1,6).setInsets(0,0,0,0));
        add(messageEditorPanel,new GBC(0,1).setFill(GBC.BOTH).setWeight(1,2).setInsets(0,0,0,0));

        //this.setLayout(new BorderLayout());
//        add(chatShowPanel,BorderLayout.CENTER);
//        add(messageEditorPanel,BorderLayout.SOUTH);

    }

    /**
     *
     */
    private void setListeners(){
/*        messagePanel.getListView().setScrollToTopListener(new NbsListView.ScrollToTopListener() {
            @Override
            public void onScrollToTop() {
                //区数据库加载
            }
        });*/

        JTextPane editor = messageEditorPanel.getTextEditor();
        Document document = editor.getDocument();
        editor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER){
                    try
                    {
                        document.insertString(editor.getCaretPosition(), "\n", null);
                    }
                    catch (BadLocationException e1)
                    {
                        e1.printStackTrace();
                    }
                }else if(!e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER){
                    sendMessage();
                    e.consume();
                }
            }
        });

        //发送按钮
        messageEditorPanel.getSendButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }

    /**
     *
     */
    private void initData(){
        //autoLoadMessage();
        autoLoadMessage();
    }


    private void autoLoadMessage(){
        String congtent = "";
        long ts = System.currentTimeMillis();
        RadomCharactersHelper randonHelper = RadomCharactersHelper.getInstance();
        int mtype = 1;
        for(int i = 0;i<30;i++){
            if(i%3==0&&i%5!=0){
                mtype = -1;
            }else if(i%7==0&&i%3!=0&&i%5!=0){
                mtype =0;
            }else {
                mtype = 1;
            }
            MessageItem item = new MessageItem();
            item.setFrom("sfsdfsdf"+i);
            item.setTimestamp(ts+30*1000);
            switch (mtype){
                case 1:
                    item.setSenderUsername("NBSChain_9527");
                    congtent = randonHelper.generated("NBS ",15);
                    break;
                case -1:
                    item.setSenderUsername(current.getNick());
                    congtent = randonHelper.generated("欢迎 ",5);
                    break;
                case 0:
                    congtent = "系统提示你傻逼.";
                    break;
            }
            item.setMessageContent(congtent);
            item.setRoomId("111");
            item.setMessageType(mtype);
            int j = i/10;
            item.setSenderId("ID"+j);

            messages.add(item);
        }
        messagePanel.getListView().notifyItemRangeInserted(0,messages.size());
        messagePanel.getListView().setAutoScrollToBottom();
    }

    /**
     * 发送消息
     */
    private void sendMessage(){

        List<Object> inTextList = getEditorText();
        if(inTextList==null||inTextList.size()<=0){
            return;
        }
        Object data = inTextList.get(0);
        if(data==null||"".equals((String)data))return;
        IpfsMessageSender sender = MainFrame.getContext().getMessageSender();
        try {
            sender.ipfsSendMessage(data.toString());
            messageEditorPanel.getTextEditor().setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //添加到消息面板
        MessageItem item = new MessageItem();
        item.setMessageContent((String)data);
        item.setMessageType(MessageItem.RIGHT_TEXT);
        item.setSenderUsername(current.getNick());
        item.setNeedToResend(false);
        addMessageItemToEnd(item);
    }

    /**
     * 发送消息添加到最后
     * @param item
     */
    private void addMessageItemToEnd(MessageItem item){
        this.messages.add(item);
        messagePanel.getListView().notifyItemInserted(messages.size()-1,true);
        messagePanel.getListView().setAutoScrollToBottom();
    }


    /**
     *
     * @return
     */
    private List<Object> getEditorText(){
        List<Object> inputData = new ArrayList<>();
        Document doc = messageEditorPanel.getTextEditor().getDocument();
        int count = doc.getRootElements()[0].getElementCount();

        // 是否是纯文本，如果发现有图片或附件，则不是纯文本
        boolean pureText = true;

        for (int i = 0; i < count; i++)
        {
            Element root = doc.getRootElements()[0].getElement(i);

            int elemCount = root.getElementCount();

            for (int j = 0; j < elemCount; j++)
            {
                try
                {
                    Element elem = root.getElement(j);
                    String elemName = elem.getName();
                    switch (elemName)
                    {
                        case "content":
                        {
                            int start = elem.getStartOffset();
                            int end = elem.getEndOffset();
                            String text = doc.getText(elem.getStartOffset(), end - start);
                            inputData.add(text);
                            break;
                        }
                        case "component":
                        {
                            pureText = false;
                            Component component = StyleConstants.getComponent(elem.getAttributes());
                            //inputData.add(component);
                            break;
                        }
                        case "icon":
                        {
                            pureText = false;
                            ImageIcon icon = (ImageIcon) StyleConstants.getIcon(elem.getAttributes());
                            //inputData.add(icon);
                            break;
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }

        // 如果是纯文本，直接返回整个文本，否则如果出消息中有换行符\n出现，那么每一行都会被解析成一句话，会造成一条消息被分散成多个消息发送
        if (pureText)
        {
            inputData.clear();
            inputData.add(messageEditorPanel.getTextEditor().getText());
        }
        return inputData;
    }

    public void paste(){

    }
    /**
     *
     * @return
     */
    public static ChatPanel getContext() {
        return context;
    }


}
