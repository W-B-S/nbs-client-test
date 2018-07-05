package io.nbs.client.listener;

import com.nbs.biz.service.AttachmentInfoService;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.nbs.sdk.beans.MessageItem;
import org.apache.ibatis.session.SqlSession;

import java.io.File;
import java.io.IOException;
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
    private static IPFS ipfs;
    private AttachmentInfoService service;

    /**
     *
     * @param ipfs
     * @param sqlSession
     */
    public IPFSFileUploader(IPFS ipfs, SqlSession sqlSession) {
        this.ipfs = ipfs;
        this.service = new AttachmentInfoService(sqlSession);
    }

    /**
     *
     * @param file
     */
    public void addFileToIPFS(File file){

        if(!file.exists()||file.isDirectory())return;

        try {
            NamedStreamable.FileWrapper fileWrapper = new NamedStreamable.FileWrapper(file);
            List<MerkleNode> list = ipfs.add(fileWrapper);
            uploadSuccessNotify();
            uploadSuccessSaveDB(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 上传成功系统展示
     */
    private void uploadSuccessNotify(){

    }

    /**
     * 存库
     */
    private void uploadSuccessSaveDB( List<MerkleNode> list){

    }
}
