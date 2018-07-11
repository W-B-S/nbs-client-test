package io.nbs.client.ui.panels.im;

import UI.AppMainWindow;
import com.nbs.biz.data.entity.AttachmentInfoEntity;
import com.nbs.biz.service.AttachmentInfoService;
import io.ipfs.api.MerkleNode;
import io.nbs.client.Launcher;
import io.nbs.client.listener.IPFSFileUploader;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.sdk.beans.PeerInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

/**
 * @Package : io.nbs.client.ui.panels.im
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/5-17:57
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IMFileActionListener implements ActionListener {
    private static Logger logger = LoggerFactory.getLogger(IMFileActionListener.class);

    private IPFSFileUploader fileUploader;
    private AttachmentInfoService attachmentInfoService;

    private JFileChooser jFileChooser;
    public IMFileActionListener(IPFSFileUploader fileUploader, JFileChooser fileChooser, SqlSession sqlSession) {
        this.fileUploader = fileUploader;
        this.jFileChooser = fileChooser;
        attachmentInfoService = new AttachmentInfoService(sqlSession);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        this.jFileChooser.showDialog(Launcher.getContext().getCurrentFrame(),"选择");
        File selection = jFileChooser.getSelectedFile();
        if(selection==null)return;
        logger.info(selection.getAbsolutePath());
        MerkleNode node = fileUploader.addFileToIPFS(selection);
        new Thread(()->{
            saveUploadFileInfo2DB(node);
        }).start();
    }

    private void saveUploadFileInfo2DB(MerkleNode node){
        if(node==null)return;
        PeerInfo info = MainFrame.getContext().getCurrentPeer();
        String fhash = node.hash.toBase58();
        AttachmentInfoEntity entity = attachmentInfoService.findById(fhash);
        if(entity!=null){
            entity.setCached(1);
            entity.setPeername(info.getNick());
            entity.setPeerhash(info.getId());
            entity.setLmtime(System.currentTimeMillis());

            String fname = node.name.get();
            entity.setFname(fname);
            entity.setFsize(Long.parseLong(node.largeSize.get()));
            String suffix = fname.lastIndexOf(".") > 0 ?"":fname.substring(fname.lastIndexOf("."));
            entity.setFsuffix(suffix);
            entity.setInlocal(1);
            attachmentInfoService.updateIgnoreNull(entity);
        }else {
            entity = new AttachmentInfoEntity();
            entity.setId(fhash);
            entity.setCached(1);
            entity.setPeername(info.getNick());
            entity.setPeerhash(info.getId());
            String fname = node.name.get();
            entity.setFname(fname);
            entity.setFsize(Long.parseLong(node.largeSize.get()));
            String suffix = fname.lastIndexOf(".") > 0 ?"":fname.substring(fname.lastIndexOf("."));
            entity.setFsuffix(suffix);
            entity.setInlocal(1);
            attachmentInfoService.insert(entity);
        }

    }
}
