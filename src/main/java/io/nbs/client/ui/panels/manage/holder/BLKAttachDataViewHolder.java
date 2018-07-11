package io.nbs.client.ui.panels.manage.holder;

import io.nbs.client.ui.components.AttachmentPanel;
import io.nbs.client.ui.components.LCFromLabel;
import io.nbs.client.ui.components.LCProgressBar;
import io.nbs.client.ui.components.SizeAutoAdjustTextArea;
import io.nbs.client.ui.frames.MainFrame;

/**
 * @Package : io.nbs.client.ui.panels.manage.holder
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/11-17:07
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class BLKAttachDataViewHolder extends AttachDataViewHolder {
    public SizeAutoAdjustTextArea attaHashTitle;
    //进度条
    public LCProgressBar progressBar = new LCProgressBar();

    public LCFromLabel sizeLabel = new LCFromLabel();
    private String cacheLocalFile;
    private AttachmentPanel attaPanel = new AttachmentPanel();//附件信息

    public BLKAttachDataViewHolder() {
        initComponents();
        initView();
        setListeners();
    }

    private void initComponents(){
        int maxWidth = (int)(MainFrame.getContext().currentWindowWidth * 0.427);
        attaHashTitle = new SizeAutoAdjustTextArea(maxWidth);

    }

    private void initView(){

    }

    private void setListeners(){

    }

    public String getCacheLocalFile() {
        return cacheLocalFile;
    }

    public void setCacheLocalFile(String cacheLocalFile) {
        this.cacheLocalFile = cacheLocalFile;
    }
}
