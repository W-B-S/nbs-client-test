package com.nbs.biz.service;

import com.nbs.biz.data.dao.NBSTableDao;
import com.nbs.biz.model.NBSTest;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Package : com.nbs.biz.service
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/23-21:19
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class TableService {
    private NBSTableDao dao;

    public TableService(SqlSession session) {
        this.dao = new NBSTableDao(session);
    }

    /**
     * 初始化所有表
     */
    public void initClientDB(){
        initAttachmentInfo();
        initPeerContacts();
        initPeerLogin();
        initPeerMessages();
        initUploadInfo();
        initWorldMessages();
    }

    /**
     *
     */
    public void initPeerLogin(){
        boolean b = dao.tableExist(TableTypes.PeerLogin.getTname());
        if(!b){
            dao.createPeerdLogin();
        }
    }

    /**
     *
     */
    public void initPeerContacts(){
        boolean b = dao.tableExist(TableTypes.PeerContacts.getTname());
        if(!b){
            dao.createPeerContacts();
        }
    }

    /**
     *
     */
    public void initAttachmentInfo(){
        boolean b = dao.tableExist(TableTypes.AttachmentInfo.getTname());
        if(!b){
            dao.createAttachmentInfo();
        }
    }

    /**
     *
     */
    public void initPeerMessages(){
        boolean b = dao.tableExist(TableTypes.PeerMessages.getTname());
        if(!b){
            dao.createPeerMessages();
        }
    }

    /**
     *
     */
    public void initWorldMessages(){
        boolean b = dao.tableExist(TableTypes.WorldMessages.getTname());
        if(!b){
            dao.createWorldMessages();
        }
    }

    /**
     *
     */
    public void initUploadInfo(){
        boolean b = dao.tableExist(TableTypes.UploadInfo.getTname());
        if(!b){
            dao.createUploadInfo();
        }
    }

    public List<NBSTest> getAll(){
        return dao.findAll();
    }

    private static enum TableTypes{
        PeerLogin("p_login","登录记录"),
        PeerContacts("p_contacts","联系人"),
        PeerMessages("p_messages","消息"),
        WorldMessages("w_messages","世界消息"),
        UploadInfo("upload_info","下载"),
        AttachmentInfo("attac_info","附件")
        ;

        private String tname;
        private String remark;

        TableTypes(String tname, String remark) {
            this.tname = tname;
            this.remark = remark;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
