package io.nbs.client.ui.panels.im;

import com.nbs.biz.data.entity.AttachmentInfoEntity;
import com.nbs.biz.service.AttachmentInfoService;
import io.ipfs.api.MerkleNode;
import io.nbs.client.Launcher;
import io.nbs.client.exceptions.FileTooLargeException;
import io.nbs.client.listener.IPFSFileUploader;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.im.messages.MessageEditorPanel;
import io.nbs.commons.helper.DateHelper;
import io.nbs.commons.utils.DataSizeFormatUtil;
import io.nbs.sdk.beans.PeerInfo;
import io.nbs.sdk.prot.IPMParser;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

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
    private MessageEditorPanel editorPanel;
    private AtomicInteger uploading = new AtomicInteger(0);
    private String upfileName = "";

    private JFileChooser jFileChooser;
    public IMFileActionListener(IPFSFileUploader fileUploader, JFileChooser fileChooser, SqlSession sqlSession) {
        this.fileUploader = fileUploader;
        this.jFileChooser = fileChooser;
        attachmentInfoService = new AttachmentInfoService(sqlSession);
    }

    public IMFileActionListener(IPFSFileUploader fileUploader, JFileChooser fileChooser, SqlSession sqlSession, MessageEditorPanel editorPanel) {
        this.editorPanel = editorPanel;
        this.fileUploader = fileUploader;
        this.jFileChooser = fileChooser;
        attachmentInfoService = new AttachmentInfoService(sqlSession);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(uploading.intValue()==1){
            JOptionPane.showMessageDialog(MainFrame.getContext(),"正在上传文件["+upfileName+"]，请稍后再传...");
            return;
        }
        this.jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        this.jFileChooser.showDialog(Launcher.getContext().getCurrentFrame(),"选择");
        File selection = jFileChooser.getSelectedFile();
        if(selection==null)return;
        this.upfileName = selection.getName();
        logger.info("{}在{}分享了{}",MainFrame.getContext().getCurrentPeer().getNick(),DateHelper.currentTime(),selection.getAbsolutePath());
        if(selection.length()>200*1024*1024){
            //JOptionPane.showMessageDialog(MainFrame.getContext(),"成功加入分享任务，由于文件较大需要稍等一会儿返回唯一串码.");
            editorPanel.setTipLabel("正在上传["+selection.getName()+"]请稍后...",true);
        }
        uploading.set(1);
        new Thread(()->{
            try {
                MerkleNode node = fileUploader.addFileToIPFS(selection);
                logger.info("添加文件成功.{}",selection.getName());
                editorPanel.setTipLabel(null,false);
                uploading.set(0);
                new Thread(()->{
                    saveUploadFileInfo2DB(node);
                }).start();
            } catch (FileTooLargeException e1) {
                logger.error("删除文件失败，{}-{}",e1.getMessage(),e1.getCause());
                JOptionPane.showMessageDialog(MainFrame.getContext(),"文件太大,最大只能上传["+DataSizeFormatUtil.formatDataSize(IPFSFileUploader.MAX_SIZE)+"]文件。");

            }
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
            entity.setFname(IPMParser.urlDecode(fname));
            entity.setFsize(Long.parseLong(node.largeSize.get()));
            String suffix = fname.lastIndexOf(".") > 0 ?"":fname.substring(fname.lastIndexOf("."));
            entity.setFsuffix(suffix);
            entity.setInlocal(1);
            attachmentInfoService.insert(entity);
        }

    }
}
