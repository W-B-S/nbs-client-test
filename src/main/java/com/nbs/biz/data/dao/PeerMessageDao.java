package com.nbs.biz.data.dao;

import org.apache.ibatis.session.SqlSession;

/**
 * @Package : com.nbs.biz.data.dao
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/2-15:49
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class PeerMessageDao extends BasicDao {
    public PeerMessageDao(SqlSession session) {
        super(session, PeerMessageDao.class);
    }


}
