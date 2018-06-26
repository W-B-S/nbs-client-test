package UI.panel;

import UI.AppMainWindow;
import UI.ConstantsUI;
import UI.button.AvatarIconButton;

import UI.templete.WihteBackJPanel;
import com.alibaba.fastjson.JSON;
import com.nbs.ipfs.IPFSHelper;
import com.nbs.tools.PropertyUtil;
import com.nbs.tools.StringHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @Package : UI.panel
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/13-16:43
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class StatusPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(StatusPanel.class);

    public static final String AVATAR_IMAGE_PATH = ConstantsUI.PROFILE_ROOT+"avatars"+File.separator;


    //Button Defined
    //public static NBSIconButton

    //Label
    public static JLabel labelStatus;

    public StatusPanel(){
        super(false);
        initialize();
        addComponent();

    }

    /**
     *
     */
    private void initialize(){
        this.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
    }

    /**
     *
     */
    private void addComponent(){
        this.add(getUpPanel(),BorderLayout.NORTH);
        this.add(getCenterPanel(),BorderLayout.CENTER);
        this.add(getDownPanel(),BorderLayout.SOUTH);
    }

    public void load(){

    }

    /**
     * 构造上部面板
     * @return
     */
    private JPanel getUpPanel(){
        JPanel panelUp = new JPanel();
        panelUp.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(FlowLayout.LEFT,ConstantsUI.MAIN_H_GAP,5));

        JLabel labelTitle = new JLabel(PropertyUtil.getProperty("nbs.ui.panel.status.label"));
        labelTitle.setFont(ConstantsUI.FONT_TITLE);
        labelTitle.setForeground(ConstantsUI.PANEL_TITILE_COLOR);
        panelUp.add(labelTitle);
        return panelUp;
    }

    /**
     * 中间面板
     * @return
     */
    private JPanel getCenterPanel(){
        JPanel center = new JPanel();
        center.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        center.setLayout(new BorderLayout());

        //first ROW
        JPanel firstRow = new WihteBackJPanel();
        firstRow.setLayout(new BorderLayout());
        JPanel cell11 = new WihteBackJPanel();
        cell11.setLayout(new FlowLayout(FlowLayout.LEFT,ConstantsUI.MAIN_H_GAP,5));

        //头像
        String nickVal = StringUtils.isBlank(AppMainWindow.selfNick()) ?
                PropertyUtil.getProperty("nbs.ui.panel.status.default-nickname") : AppMainWindow.selfNick();
        ImageIcon icon = new ImageIcon(AVATAR_IMAGE_PATH + PropertyUtil.getProperty("nbs.ui.panel.status.default-avatar"));
        AvatarIconButton avatar = new AvatarIconButton(icon,nickVal);
        //
        avatar.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(AppMainWindow.frame,"暂时不能设置头像");
            }
        });
        cell11.add(avatar);

        firstRow.add(cell11);
        center.add(firstRow,BorderLayout.NORTH);

        /**
         * Peer INFO
         */
        WihteBackJPanel peerInfo = new WihteBackJPanel();
        peerInfo.setLayout(new GridLayout(2,1));
        //

        JLabel nickLabel = new ContentJLabel(AppMainWindow.selfNick());
        nickLabel.setFont(ConstantsUI.FONT_LABEL);


        nickLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String newNick = JOptionPane.showInputDialog(AppMainWindow.frame,"昵称",nickLabel.getText());
                if(StringUtils.isNotBlank(newNick)&&newNick.trim().length()<=40){
                    try {
                        String res = IPFSHelper.getInstance().updateNick(newNick);
                        if(res!=null)AppMainWindow.currentPeerInfo().setNick(res);
                        nickLabel.setText(res);
                        nickLabel.updateUI();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(AppMainWindow.frame,"修改失败.");
                    }
                }
                super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                nickLabel.setToolTipText("点击修改昵称");
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
                super.mouseExited(e);
            }
        });
        peerInfo.add(nickLabel);

        JLabel peerIDLabel = new ContentJLabel(PropertyUtil.getProperty("nbs.ui.panel.status.label.peer-id","PeerID:")+AppMainWindow.PEER_ID);
        peerIDLabel.setFont(ConstantsUI.FONT_NORMAL);
        peerInfo.add(peerIDLabel);
        cell11.add(peerInfo,BorderLayout.CENTER);

        /**
         * Peer 详细信息
         */
        JScrollPane scrollPane = new JScrollPane(getPeerIdInfo(AppMainWindow.PEER_ID),JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //scrollPane.setLayout(new BorderLayout());
        scrollPane.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        scrollPane.setSize(ConstantsUI.MAIN_WINDOW_WIDTH-85,ConstantsUI.MAIN_WINDOW_HEIGHT-136);

        center.add(scrollPane,BorderLayout.CENTER);
        return center;
    }

    /**
     * 加载peer ID info
     * @param idhash
     * @return
     */
    private ContentJLabel getPeerIdInfo(String idhash){

        Map map = null;
        ContentJLabel peerLabel = new ContentJLabel();
        try {
            if(StringUtils.isNotBlank(idhash)){
                map = AppMainWindow.ipfs.id(idhash);
            }else {
                map = AppMainWindow.ipfs.id();
            }
            if(map!=null){
                StringBuffer sb = new StringBuffer();
                sb.append("<html><body><div style=\"margin : 10px;\" >");
                String peerJson = JSON.toJSONString(map,true);
                String sss = StringHelper.getInstance().replaceAllCRLF2Br(peerJson);
                logger.info(sss);
                sb.append(sss);
                //sb.append("skdfsfdlksfdsldfkskfd");
                sb.append("</div></body></html>");

                peerLabel.setText(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return peerLabel;
    }

    private String getPeerDetail(String idhash){
        Map map = null;
        try {
            if(StringUtils.isNotBlank(idhash)){
                map = AppMainWindow.ipfs.id(idhash);
            }else {
                map = AppMainWindow.ipfs.id();
            }
            if(map!=null){
                StringBuffer sb = new StringBuffer();
                sb.append("<html><body><div style=\"margin : 10px;\" >");
                String peerJson = JSON.toJSONString(map,true);
                String sss = StringHelper.getInstance().replaceAllCRLF2Br(peerJson);
                logger.info(sss);
                sb.append(sss);
                //sb.append("skdfsfdlksfdsldfkskfd");
                sb.append("</div></body></html>");
                return sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private JPanel getDownPanel(){
        JPanel panelDown = new JPanel();


        return panelDown;
    }
}
