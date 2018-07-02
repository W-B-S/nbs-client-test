package io.nbs.client.ui.panels.im;

import io.nbs.client.services.IpfsMessageSender;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.sdk.beans.MessageItem;
import io.ipfs.nbs.helper.MessageViewHolderCacheHelper;
import io.nbs.client.ui.panels.im.messages.MessageEditorPanel;
import io.nbs.client.ui.panels.im.messages.MessagePanel;
import io.nbs.client.adapter.MessageAdapter;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.ui.components.GBC;
import com.nbs.ui.components.NbsBorder;
import io.nbs.client.ui.components.NbsListView;
import io.nbs.client.ui.panels.ParentAvailablePanel;
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

    private java.util.List<MessageItem> messageItems = new ArrayList<>();

    private MessageViewHolderCacheHelper messageViewHolderCacheHelper;
    private MessageAdapter adapter;



    /**
     *
     * @param parent
     */
    public ChatPanel(JPanel parent) {
        super(parent);
        context = this;
        messageViewHolderCacheHelper = new MessageViewHolderCacheHelper();
        initComponents();
        initView();
        setListeners();
        initData();
    }

    private void initComponents(){
        messagePanel = new MessagePanel(this);
        messagePanel.setBorder(new NbsBorder(NbsBorder.BOTTOM,ColorCnst.LIGHT_GRAY));

        adapter = new MessageAdapter(messageItems,messagePanel.getListView(),messageViewHolderCacheHelper);
        messagePanel.getListView().setAdapter(adapter);

        messageEditorPanel = new MessageEditorPanel(this);
       // messageEditorPanel.setPreferredSize(new Dimension(MainFrame.DEFAULT_WIDTH*2/5,200));

    }
    private void initView(){
        this.setLayout(new GridBagLayout());
        add(messagePanel,new GBC(0,0).setFill(GBC.BOTH).setWeight(1,7));
        add(messageEditorPanel,new GBC(0,1).setFill(GBC.BOTH).setWeight(1,2));
    }

    /**
     *
     */
    private void setListeners(){
        messagePanel.getListView().setScrollToTopListener(new NbsListView.ScrollToTopListener() {
            @Override
            public void onScrollToTop() {
                //区数据库加载
            }
        });

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
    private void initData(){
        autoLoadMessage();

    }


    private void autoLoadMessage(){
        for(int i = 0;i<30;i++){
            MessageItem item = new MessageItem();
            item.setFrom("sfsdfsdf");
            item.setMessageContent("NBS");
            item.setRoomId("111");
            int j = i/10;
            item.setSenderId("iD"+j);
            messageItems.add(item);
        }
        messagePanel.getListView().notifyDataSetChanged(true);
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
        IpfsMessageSender sender = MainFrame.getContext().getMessageSender();


        try {
            sender.ipfsSendMessage(data.toString());
            messageEditorPanel.getTextEditor().setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
     /*   String text = (String)inTextList.get(0);
        IPFS ipfs = IPFSHelper.getInstance().getIpfs();
        String encodeMsg = Base64CodecUtil.encode(text);
        boolean success = false;
        int i = 0;
        while (!success && i<5 ){
            i++;
            try {
                ipfs.pubsub.pub(IPFSHelper.NBSWORLD_IMS_TOPIC,encodeMsg);
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/



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
