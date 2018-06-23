package com.nbs.biz.service;

import com.nbs.biz.dao.NBSTableDao;
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

    public void initNbsContacts(){
        String tbNbsContacts = "nbs_contacts";
        boolean b = dao.tableExist(tbNbsContacts);
        if(!b){
            dao.createNbsContacts();
        }
    }

    public List<NBSTest> getAll(){
        return dao.findAll();
    }
}
