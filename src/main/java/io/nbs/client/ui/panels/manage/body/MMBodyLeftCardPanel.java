package io.nbs.client.ui.panels.manage.body;

import io.nbs.client.cache.AttachmentsViewHolderCacheHelper;
import io.nbs.client.ui.panels.ParentAvailablePanel;

import javax.swing.*;

/**
 * @Package : io.nbs.client.ui.panels.manage.body
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-16:31
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MMBodyLeftCardPanel extends ParentAvailablePanel {
    private static MMBodyLeftCardPanel context;

    private AttachmentsViewHolderCacheHelper viewHolderCacheHelper;



    /**
     * construction
     */
    public MMBodyLeftCardPanel(JPanel parent) {
        super(parent);
        viewHolderCacheHelper = new AttachmentsViewHolderCacheHelper();
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

    }

    /**
     * [initView description]
     *
     * @return {[type]} [description]
     */
    private void initView() {

    }

    private void setListeners() {

    }

    /**
     * [getContext description]
     *
     * @return {[type]} [description]
     */
    public static MMBodyLeftCardPanel getContext() {
        return context;
    }

    /**
     *
     * @return
     */
    public AttachmentsViewHolderCacheHelper getViewHolderCacheHelper() {
        return viewHolderCacheHelper;
    }
}