package io.nbs.client.ui.panels.manage.holder;

import com.alibaba.fastjson.JSON;
import io.nbs.client.Launcher;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.ui.components.*;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.holders.ViewHolder;
import io.nbs.client.ui.panels.manage.listener.FillDetailInfoListener;
import io.nbs.client.vo.AttachmentDataDTO;
import io.nbs.commons.helper.ConfigurationHelper;
import io.nbs.commons.utils.IconUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * @Package : io.nbs.client.ui.panels.manage.holder
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-17:00
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public abstract class AttachDataViewHolder extends ViewHolder {
    private static final Logger logger = LoggerFactory.getLogger(AttachDataViewHolder.class);
    private int iconSize = 28;
    public SizeAutoAdjustTextArea attachmentTitle;
    public SizeAutoAdjustTextArea hashTitle;
    public LCProgressBar progressBar = new LCProgressBar();//进度条
    public JPanel timePanel = new JPanel();//时间面板
    public JPanel iconInfoPanel = new JPanel();//图标 组合面板
    public AttachmentPanel attachmentPanel = new AttachmentPanel();//附件
    public JLabel attachIcon = new JLabel();//附件类型
    public JLabel sizeLabel = new JLabel();
    public JLabel blkNumLabel = new JLabel();
    public JLabel time = new JLabel();
    protected FillDetailInfoListener listener;
    protected NBSIconButton openBtn;
    public NBSIconButton downloadBtn;

    public LCAttachMessageBubble messageBubble;

    public AttachDataViewHolder() {
        initComponents();
        setListeners();
    }

    public abstract void setFillDetailListener(FillDetailInfoListener listener);

    /**
     *
     */
    private void initComponents(){
        int maxWidth = (int)(MainFrame.getContext().currentWindowWidth*0.35);
        int pmaxWidth = (int)(getWidth()*0.65);
        attachmentTitle = new SizeAutoAdjustTextArea(pmaxWidth);
        hashTitle = new SizeAutoAdjustTextArea(pmaxWidth);
        attachmentTitle.setEditable(false);
        hashTitle.setEditable(false);

        //iconInfoPanel.setBackground(ColorCnst.MAIN_COLOR);
        iconInfoPanel.setBackground(ColorCnst.WINDOW_BACKGROUND_LIGHT);
       // iconInfoPanel.setMinimumSize(new Dimension(45,90));

        ImageIcon lnormal =  IconUtil.getIcon(Launcher.getContext(),"/icons/link.png",iconSize,iconSize);
        ImageIcon lactived =  IconUtil.getIcon(Launcher.getContext(),"/icons/link_actived.png",iconSize,iconSize);
        openBtn = new NBSIconButton(lnormal,lactived, "查看");
        downloadBtn = new NBSIconButton(
                IconUtil.getIcon(Launcher.getContext(),"/icons/download.png",iconSize,iconSize),
                IconUtil.getIcon(Launcher.getContext(),"/icons/download_actived.png",iconSize,iconSize),
                "下载到本地");

        //timePanel.setBackground(ColorCnst.WINDOW_BACKGROUND);
        time.setForeground(ColorCnst.FONT_GRAY_DARKER);
        time.setFont(FontUtil.getDefaultFont(12));


        attachmentPanel.setOpaque(false);

        progressBar.setMaximum(100);
        progressBar.setMinimum(0);
        progressBar.setValue(100);
        progressBar.setUI(new GradientProgressBarUI());
        progressBar.setVisible(false);

        sizeLabel.setFont(FontUtil.getDefaultFont(12));
        sizeLabel.setHorizontalAlignment(JLabel.LEFT);
        sizeLabel.setForeground(ColorCnst.FONT_ABOUT_TITLE_BLUE);


    }

    private void setListeners(){

        //remove listener
        MouseListener[] mouseAdapters = attachmentTitle.getMouseListeners();
        for(MouseListener adapter: mouseAdapters){
            attachmentTitle.removeMouseListener(adapter);
        }
        MouseListener[] htListeners = hashTitle.getMouseListeners();
        for(MouseListener adapter: htListeners){
            hashTitle.removeMouseListener(adapter);
        }

        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setCursor(MainFrame.handCursor);
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }

            /**
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                Object tag = attachmentPanel.getTag();
                if(tag!=null&& tag instanceof AttachmentDataDTO){
                    logger.info(JSON.toJSONString(tag));
                    AttachmentDataDTO dto = (AttachmentDataDTO)tag;
                    listener.fillAttachmentDetailInfo(dto);
                }
            }
        };
        //
        attachmentPanel.addMouseListener(adapter);
        attachmentTitle.addMouseListener(adapter);



        /**
         *
         */
        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String urlBase = ConfigurationHelper.getInstance().getGateWayURL();
                Object o = attachmentPanel.getTag();
                if(o!=null&& o instanceof AttachmentDataDTO){
                    AttachmentDataDTO m = (AttachmentDataDTO)o;
                    if(StringUtils.isNotBlank(m.getId())){
                        String hash = m.getId();
                        java.net.URI uri = java.net.URI.create(urlBase+hash);
                        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                        if(desktop.isSupported(Desktop.Action.BROWSE)){
                            try {
                                desktop.browse(uri);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                                logger.error(e1.getMessage());
                            }
                        }

                    }
                }
            }
        });

        downloadBtn.addMouseListener(new BaseBtnMouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                logger.info(">>>>>>>>>>>>>>>>>>>>>>>>");
            }
        });

    }



    private class BaseBtnMouseAdapter extends MouseAdapter{
        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            e.getComponent().setCursor(MainFrame.handCursor);
        }
    }
}
