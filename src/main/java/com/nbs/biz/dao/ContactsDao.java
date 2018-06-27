package com.nbs.biz.dao;

import org.apache.ibatis.session.SqlSession;



/**
 * @Package : com.nbs.biz.dao
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/27-16:36
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ContactsDao extends BasicDao {
    public ContactsDao(SqlSession session) {
        super(session,ContactsDao.class);
    }

    public int existCountByPeerId(String peerId){
       return 0;
    }
}
