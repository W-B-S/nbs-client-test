package com.nbs.biz.data.dao;

import org.apache.ibatis.session.SqlSession;

/**
 * @Package : com.nbs.biz.data.dao
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-13:58
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class PeerContactsDao extends BasicDao {
    public PeerContactsDao(SqlSession session) {
        super(session, PeerContactsDao.class);
    }

    /**
     *
     * @param id
     * @return
     */
    public int peerOffLine(String id){
        return session.update("peerOffLine",id);
    }
}
