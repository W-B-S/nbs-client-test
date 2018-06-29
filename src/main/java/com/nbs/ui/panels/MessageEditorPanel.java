package com.nbs.ui.panels;

import UI.AppMainWindow;
import UI.ConstantsUI;
import UI.button.NBSIconButton;
import io.ipfs.nbs.ui.components.NBSButton;
import io.ipfs.nbs.cnsts.FontUtil;
import io.ipfs.nbs.cnsts.OSUtil;
import io.ipfs.nbs.cnsts.ColorCnst;
import io.ipfs.nbs.ui.components.GBC;
import com.nbs.ui.components.NBSTextEditor;
import com.nbs.ui.components.ScrollUI;
import com.nbs.ui.frames.ScreenShot;
import com.nbs.ui.icons.NBSIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @Package : com.nbs.ui.panels
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/26-21:23
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MessageEditorPanel extends ParentAvailablePanel {

    /**
     * 控制区，放置操作图标
     */
    private JPanel controlLabel;

    private JLabel fileLabel;
    private JLabel expressionLabel;
    private JLabel cutLabel;
    private JScrollPane textScrollPane;
    private NBSTextEditor textEditor;

    private JPanel sendPanel;
    /**
     * 发送按钮
     */
    private NBSButton sendButton;

    private NBSIcon emojiIcon;

    private NBSIconButton cutIcon;
    private NBSIconButton fileIcon;



    /**
     *
     * @param parent
     */
    public MessageEditorPanel(JPanel parent) {
        super(parent);
        initComponents();
        initView();
        setListeners();
        if(OSUtil.getOsType() == OSUtil.Windows){
            registerHotkey();
        }
    }

    private void registerHotkey(){
        int SEREEN_SHOT_CODE =10001;
/*        JIntellitype.getInstance().registerHotKey(SEREEN_SHOT_CODE,JIntellitype.MOD_ALT,'$');
        JIntellitype.getInstance().addHotKeyListener(new HotkeyListener(){
            @Override
            public void onHotKey(int markCode){
                if(markCode == SEREEN_SHOT_CODE){
                    screenShot();
                }
            }
        });*/
    }

    /**
     * 初始化组件
     */
    private void initComponents(){
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

        controlLabel = new JPanel();
        controlLabel.setLayout(new FlowLayout(FlowLayout.LEFT,20,7));

        expressionLabel = new JLabel();
        /**
         * 图标
         */
        String buttonIconRoot = ConstantsUI.BUTTON_ICON_ROOT;


        emojiIcon = new NBSIcon(
                AppMainWindow.class.getResource(buttonIconRoot+"emotion.png"),
                AppMainWindow.class.getResource(buttonIconRoot+"emotion_active.png"),
                "表情");
        expressionLabel.setIcon(emojiIcon);
        //expressionLabel.add(emojiIcon);

        fileIcon = new NBSIconButton(
                new ImageIcon(AppMainWindow.class.getResource(buttonIconRoot+"file.png")),
                new ImageIcon(AppMainWindow.class.getResource(buttonIconRoot+"file_active.png")),
                "发送文件/图片"
        );
        String cutTip = "截图(Alt + A)";
        if(OSUtil.getOsType() == OSUtil.Windows){
            cutTip = "截图(Alt + A)";
        }else {
            cutTip = "截图(当前系统下不支持全局热键)";
        }
        cutIcon = new NBSIconButton(
                new ImageIcon(AppMainWindow.class.getResource(buttonIconRoot+"cut.png")),
                new ImageIcon(AppMainWindow.class.getResource(buttonIconRoot+"cut_active.png")),
                cutTip
        );

        textEditor = new NBSTextEditor();
        textEditor.setBackground(ColorCnst.WINDOW_BACKGROUND);
        textEditor.setFont(FontUtil.getDefaultFont(14));
        textEditor.setMargin(new Insets(0,15,0,0));
        textScrollPane = new JScrollPane(textEditor);

        textScrollPane.getVerticalScrollBar().setUI(new ScrollUI(ColorCnst.SCROLL_BAR_THUMB,ColorCnst.WINDOW_BACKGROUND));
        textScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textScrollPane.setBorder(null);

        /**
         *
         */
        sendPanel = new JPanel();
        sendPanel.setLayout(new BorderLayout());
        sendButton = new NBSButton("发送");
        sendPanel.add(sendButton,BorderLayout.EAST);
        sendButton.setForeground(ColorCnst.DARKER);
        sendButton.setFont(FontUtil.getDefaultFont(13));
        sendButton.setPreferredSize(new Dimension(75,23));
        sendButton.setToolTipText("Enter 发送消息,Ctrl + Enter换行");

    }

    private void initView(){
        this.setLayout(new GridBagLayout());
        controlLabel.add(expressionLabel);
        controlLabel.add(fileIcon);
        controlLabel.add(cutIcon);

        add(controlLabel,new GBC(0,0).setFill(GBC.HORIZONTAL).setWeight(1,1));
        add(textScrollPane,new GBC(0,1).setFill(GBC.BOTH).setWeight(1,15));
        add(sendPanel,new GBC(0,2)
                .setFill(GBC.BOTH).setWeight(1,1)
                .setInsets(0,0,10,10));
    }

    /**
     *
     */
    private void setListeners(){
        fileIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        });


        expressionLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               // expressionLabel.setIcon();
                super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                emojiIcon.hover();
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                emojiIcon.normal();
                super.mouseExited(e);
            }
        });

        textEditor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }
        });

    }

    private void screenShot()
    {
        try
        {
            ScreenShot ssw = new ScreenShot();
            ssw.setVisible(true);
        }
        catch (AWTException e1)
        {
            e1.printStackTrace();
        }
    }

    public JLabel getFileLabel() {
        return fileLabel;
    }

    public NBSTextEditor getTextEditor() {
        return textEditor;
    }

    public JPanel getSendPanel() {
        return sendPanel;
    }

    public NBSButton getSendButton() {
        return sendButton;
    }
}
