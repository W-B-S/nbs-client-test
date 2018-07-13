package io.nbs.client.ui.panels.manage;

import com.alibaba.fastjson.JSON;
import com.nbs.biz.data.entity.AttachmentInfoEntity;
import com.nbs.biz.service.AttachmentInfoService;
import com.nbs.ui.panels.ListPanel;
import io.ipfs.api.IPFS;
import io.ipfs.api.JSONParser;
import io.ipfs.api.beans.blk.BlockStat;
import io.ipfs.multihash.Multihash;
import io.nbs.client.Launcher;
import io.nbs.client.cnsts.ColorCnst;
import io.nbs.client.cnsts.FontUtil;
import io.nbs.client.listener.AbstractMouseListener;
import io.nbs.client.ui.components.GBC;
import io.nbs.client.ui.components.NBSIconButton;
import io.nbs.client.ui.components.SearchButtonPanel;
import io.nbs.client.ui.components.SearchTextField;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.client.ui.panels.manage.body.TipResultHashPanel;
import io.nbs.client.vo.AttachmentDataDTO;
import io.nbs.commons.utils.ButtonIconUtil;
import io.nbs.commons.utils.IconUtil;
import io.nbs.sdk.page.PageModel;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * @Package : io.nbs.client.ui.panels.manage
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/8-20:23
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MMSearcherPanel extends ParentAvailablePanel {
    private static MMSearcherPanel context;

    private SearchTextField searchTextField;
    private NBSIconButton searchBtn;

    private JPanel prevous;
    private JPanel tailer;

    private SearchButtonPanel buttonPanel;

    public MMSearcherPanel(JPanel parent ) {
        super(parent);
        context = this;
        initComponents();
        initView();
        setListeners();
    }

    /**
     * [initComponents description]
     *
     * @return {[type]} [description]
     */
    private void initComponents() {
        searchTextField = new SearchTextField();
        searchBtn = ButtonIconUtil.searchBtn;
        prevous = new JPanel();
        tailer = new JPanel();

        buttonPanel = new SearchButtonPanel("Search");


        searchTextField.setFont(FontUtil.getDefaultFont(14));
        //searchTextField.setBorder(MainFrame.redBorder);
        searchTextField.setForeground(ColorCnst.DARK);
    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        setBackground(ColorCnst.SEEARCH_ITEM_GRAY_LIGHT);
        prevous.setBackground(ColorCnst.SEEARCH_ITEM_GRAY_LIGHT);
        tailer.setBackground(ColorCnst.SEEARCH_ITEM_GRAY_LIGHT);
        setLayout(new GridBagLayout());
        //setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
        //add(this.searchTextField);
        //add(this.searchBtn);
        this.add(prevous,
                new GBC(0,0).setFill(GBC.BOTH).setWeight(1,1).setInsets(15));
        this.add(searchTextField,
                new GBC(1,0).setFill(GBC.BOTH).setWeight(18,1).setInsets(15,0,15,0));
        this.add(buttonPanel,
                new GBC(2,0).setFill(GBC.BOTH).setWeight(3,1).setInsets(15,0,15,0));
        this.add(tailer,
                new GBC(3,0).setFill(GBC.BOTH).setWeight(1,1).setInsets(15));
    }

    private void setListeners() {
        buttonPanel.addMouseListener(new AbstractMouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                searchHash(searchTextField.getText());
                //super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                buttonPanel.setCursor(MainFrame.handCursor);
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        });

        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    searchHash(searchTextField.getText());
                }
            }
        });
    }

    /**
     *
     * @param text
     */
    private void searchHash(final String text){
        if(StringUtils.isBlank(text));
        String searchStr = text.trim();
        MMBodyPanel bodyPanel = ManageMasterPanel.getContext().getBodyPanel();
        AttachmentInfoService infoService = bodyPanel.getAttachmentInfoService();

        PageModel<AttachmentInfoEntity> pageModel = infoService.queryPage(1,searchStr);

        java.util.List<AttachmentDataDTO> dataDTOS = infoService.copyFromAttachmentInfoEntity(pageModel.getList());

        Multihash multihash = null;
        try{
            multihash= Multihash.fromBase58(text);
        }catch (RuntimeException re){

        }

        if(multihash!=null&&(dataDTOS==null||dataDTOS.size()==0)){
            //没有查到，填充OUT
            AttachmentDataDTO dto = null;
            bodyPanel.showPanel(MMBodyPanel.MMNames.TIP);
            IPFS ipfs = Launcher.getContext().getIpfs();
            TipResultHashPanel tipResultHashPanel= MMBodyPanel.getContext().getTipResultHashPanel();
            tipResultHashPanel.clearVol();
            if(tipResultHashPanel.prevousHash().equals(text))return;
            tipResultHashPanel.setHash(multihash.toBase58());
            new Thread(()->{
               Multihash multihash1= Multihash.fromBase58(text);
               try {
                   Object o =ipfs.object.stat(multihash1);
                   String json = JSONParser.toString(o);
                   BlockStat stat = JSON.parseObject(json,BlockStat.class);
                   tipResultHashPanel.setBlkStat(stat,null);
               } catch (IOException e) {
                   e.printStackTrace();
                   logger.error(e.getMessage());
                   String error = "没有在NBS网络世界查到你要的数据["+multihash1.toBase58()+"]";
                   tipResultHashPanel.setBlkStat(null,error);
               }
            }).start();
        }else {
            bodyPanel.showPanel(MMBodyPanel.MMNames.LISTF);
            bodyPanel.researchLoadView(dataDTOS);
        }

    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static MMSearcherPanel getContext() {
        return context;
    }
}