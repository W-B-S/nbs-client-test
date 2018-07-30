package com.nbs.biz.data.dao;

import org.apache.ibatis.session.SqlSession;

/**
 * @Package : com.nbs.biz.data.dao
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/29-20:32
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class PeerLoginDao extends BasicDao {

    public PeerLoginDao(SqlSession session) {
        super(session, PeerLoginDao.class);
    }



}
