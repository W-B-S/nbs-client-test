package io.nbs.client.listener;

import com.alibaba.fastjson.JSON;
import com.nbs.biz.service.AttachmentInfoService;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.nbs.client.Launcher;
import io.nbs.client.cnsts.AppGlobalCnst;
import io.nbs.client.exceptions.FileTooLargeException;
import io.nbs.client.services.IpfsMessageSender;
import io.nbs.client.ui.frames.MainFrame;
import io.nbs.client.ui.panels.im.messages.MessagePanel;
import io.nbs.commons.utils.DataSizeFormatUtil;
import io.nbs.commons.utils.UUIDGenerator;
import io.nbs.sdk.beans.MessageItem;
import io.nbs.sdk.beans.PeerInfo;
import io.nbs.sdk.prot.IPMParser;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

/**
 * @Package : io.nbs.client.listener
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/5-17:18
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IPFSFileUploader {
    private static Logger logger = LoggerFactory.getLogger(IPFSFileUploader.class);
    private static IPFS ipfs;
    private AttachmentInfoService service;
    public static long MAX_SIZE = 2000*1024*1024;
    private PeerInfo cureent;
    private MessagePanel messagePanel;
    private List<MessageItem> messageItems;

    /**
     *
     * @param ipfs
     * @param sqlSession
     */
    public IPFSFileUploader(IPFS ipfs, SqlSession sqlSession,MessagePanel panel,List<MessageItem> messageItems) {
        this.ipfs = ipfs;
        this.service = new AttachmentInfoService(sqlSession);
        cureent = MainFrame.getContext().getCurrentPeer();
        this.messagePanel = panel;
        this.messageItems = messageItems;
    }

    /**
     *
     * @param file
     */
    public MerkleNode addFileToIPFS(File file) throws FileTooLargeException {
        if(!file.exists()||file.isDirectory())return null;
        String name = file.getName();
        long size = file.length();
        if(size>MAX_SIZE){
            throw  new FileTooLargeException("上传文件【"+name+"】超过"+DataSizeFormatUtil.formatDataSize(MAX_SIZE)+"限制.");
        }
        try {
            NamedStreamable.FileWrapper fileWrapper = new NamedStreamable.FileWrapper(file);
            List<MerkleNode> list = ipfs.add(fileWrapper,false,false);
            logger.info(JSON.toJSONString(list.get(0)));
            uploadSuccessNotify(list.get(0));
            return list.get(0);
        } catch (IOException e) {
            //e.printStackTrace();
            logger.error("文件{}上传失败,{}",name,e.getMessage());
            return null;
        }
    }

    public MerkleNode addByteFileToIPFS(File file) throws FileTooLargeException {
        if(!file.exists()||file.isDirectory())return null;
        String fname = file.getName();
        long size = file.length();
        if(size>MAX_SIZE){
            throw  new FileTooLargeException("上传文件【"+fname+"】超过"+DataSizeFormatUtil.formatDataSize(MAX_SIZE)+"限制.");
        }
        int len = (int)size;
        byte[] lagerBytes = new byte[len];
        try {
            FileInputStream inputStream = new FileInputStream(file);
           // NamedStreamable.ByteArrayWrapper byteArrayWrapper = new NamedStreamable.ByteArrayWrapper(inputStream.);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return null;
    }



    /**
     *
     * 上传成功系统展示
     */
    private void uploadSuccessNotify(MerkleNode node){
        MessageItem item = new MessageItem();
        item.setId(UUIDGenerator.getUUID());
        item.setTimestamp(System.currentTimeMillis());
        item.setSenderId(cureent.getId());
        item.setSenderUsername(cureent.getNick());
        item.setMessageType(MessageItem.RIGHT_TEXT);
        item.setAvatar(cureent.getAvatar());
        item.setFrom(cureent.getFrom());
        StringBuilder contentSb = new StringBuilder();
        String fileName = IPMParser.urlDecode(node.name.get());
        logger.info(fileName);
        contentSb.append("分享文件【").append(fileName).append("】成功.\n");
        contentSb.append("可通下面串码查询:\n");
        contentSb.append(node.hash.toBase58()).append("\n");
        //contentSb.append("或在浏览器输入链接：\n");
        //contentSb.append("http://127.0.0.1:8080/ipfs/").append(node.hash.toBase58());
        item.setMessageContent(contentSb.toString());
        boolean need = false;
        try {
            MainFrame.getContext().getMessageSender().ipfsSendMessage(contentSb.toString());
        } catch (Exception e) {
            need = true;
            e.printStackTrace();
        }
        if(messagePanel!=null&&messageItems!=null){
            item.setNeedToResend(need);
            messageItems.add(item);
            int pos = messageItems.size();
            messagePanel.getListView().notifyItemInserted(pos-1,true);
        }
    }

    /**
     * 存库
     */
    private void uploadSuccessSaveDB( List<MerkleNode> list){

    }


}
