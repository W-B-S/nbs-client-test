package com.nbs.biz.dao;

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

    public void createNbsContacts(){
        session.update("createNbsContacts");
    }

    public void createCurrentPeers(){
        session.update("createCurrentPeers");
    }

    public void createNbsMessage(){
        session.update("createNbsMessage");
    }


    public List<NBSTest> findAll(){
        return session.selectList("findAll");
    }
}
