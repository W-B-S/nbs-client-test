package com.nbs.biz.data.dao;

import com.nbs.biz.model.NBSTest;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Package : com.nbs.biz.dao
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/23-21:12
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class NBSTableDao {
    private SqlSession session;

    public NBSTableDao(SqlSession session) {
        this.session = session;
    }

    public boolean tableExist(String name)
    {
        return ((int) session.selectOne("tableExist", name)) > 0;
    }

    /**
     *
     */
    public void createPeerContacts(){
        session.update("createPeerContacts");
    }

    /**
     * 世界消息
     */
    public void createWorldMessages(){
        session.update("createWorldMessages");
    }

    /**
     * 点对点消息
     */
    public void createPeerMessages(){
        session.update("createPeerMessages");
    }

    /**
     * 创建
     */
    public void createPeerdLogin(){
        session.update("createPeerdLogin");
    }

    public void createUploadInfo(){
        session.update("createUploadInfo");
    }


    /**
     *
     */
    public void createAttachmentInfo(){
        session.update("createAttachmentInfo");
    }

    public void createHashLinks(){
        session.update("createHashLinks");
    }
    public List<NBSTest> findAll(){
        return session.selectList("findAll");
    }
}
