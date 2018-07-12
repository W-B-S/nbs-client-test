package io.nbs.client.ui.panels.manage;

import com.alibaba.fastjson.JSON;
import com.nbs.biz.service.AttachmentInfoService;
import io.nbs.client.Launcher;
import io.nbs.client.cache.AttachmentsViewHolderCacheHelper;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.ParentAvailablePanel;
import io.nbs.client.ui.panels.manage.adapter.AttachmentDataAdapter;
import io.nbs.client.ui.panels.manage.body.*;
import io.nbs.client.ui.panels.manage.listener.FillDetailInfoListener;
import io.nbs.client.vo.AttachmentDataDTO;
import io.nbs.sdk.beans.PeerInfo;
import io.nbs.sdk.page.PageCondition;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @Package : io.nbs.client.ui.panels.manage
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/8-20:52
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MMBodyPanel extends ParentAvailablePanel implements FillDetailInfoListener {
    private static MMBodyPanel context;
    private MMRightPanel rightPanel;
    private MMBodyLeftCardPanel cardPanel;

    private CardLayout cardLayout;
    private MMDataListPanel dataListPanel;
    private MMSearchResultPanel searchResultPanel;
    private AttachmentsViewHolderCacheHelper viewHolderCacheHelper;
    private AttachmentDataAdapter adapter;

    private java.util.List<AttachmentDataDTO> attachDatas = new ArrayList<>();
    private PeerInfo current;

    private AttachmentInfoService attachmentInfoService;
    private PageCondition pageCondition;

    private OutResultHashPanel outResultHashPanel;

    /**
     * construction
     */
    public MMBodyPanel(JPanel parent) {
        super(parent);
        context = this;
        pageCondition = new PageCondition("ctime","DESC");
        current = MainFrame.getContext().getCurrentPeer();
        viewHolderCacheHelper = new AttachmentsViewHolderCacheHelper(context);
        attachmentInfoService = new AttachmentInfoService(Launcher.getSqlSession());
        initComponents();
        initView();
        loadInit();
        setListeners();
    }

    /**
     * [initComponents description]
     *
     * @return {[type]} [description]
     */
    private void initComponents() {
        cardPanel = new MMBodyLeftCardPanel(this);
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        //文件列表
        dataListPanel = new MMDataListPanel();
        searchResultPanel = new MMSearchResultPanel(pageCondition);
        adapter = new AttachmentDataAdapter(attachDatas,dataListPanel.getListView(),viewHolderCacheHelper,context);
        dataListPanel.getListView().setAdapter(adapter);
        rightPanel = new MMRightPanel(this);
    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {
        setLayout(new BorderLayout());
        /* ===================================================================== */

        cardPanel.add(dataListPanel,MMNames.LISTF.name());
        cardPanel.add(searchResultPanel,MMNames.SEARCHE.name());

        rightPanel.setPreferredSize(new Dimension(310,MainFrame.getContext().currentWindowHeight));
        /* ===================================================================== */
        add(cardPanel,BorderLayout.CENTER);
        add(rightPanel,BorderLayout.EAST);
    }

    private void setListeners() {

    }

    /**
     *
     * @param who
     */
    public void showPanel(MMNames who){
        cardLayout.show(context,who.name());
    }

    private void loadInit(){
        java.util.List<AttachmentDataDTO> attachmentDataDTOList = attachmentInfoService.getLearstCount(1,null);
        logger.info("加载历史文件>>>>{}条",attachmentDataDTOList.size());
        if(attachmentDataDTOList.size()>0){
            for(AttachmentDataDTO dto : attachmentDataDTOList){
                attachDatas.add(dto);
            }
            dataListPanel.getListView().notifyDataSetChanged(false);
            dataListPanel.getListView().setAutoScrollToBottom();
        }
    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static MMBodyPanel getContext() {
        return context;
    }


    public static enum MMNames{
        LISTF,SEARCHE;
    }

    @Override
    public void fillAttachmentDetailInfo(AttachmentDataDTO dataDTO) {
       //logger.info("CLICK:{}", JSON.toJSONString(dataDTO));
        rightPanel.setAttachmentDetailInfo(dataDTO);
    }

    public void setSearchCondition(String text){
        pageCondition.setSearchStr(text);
    }

    public PageCondition getPageCondition() {
        return pageCondition;
    }

    public void setPageCondition(PageCondition pageCondition) {
        this.pageCondition = pageCondition;
    }
}