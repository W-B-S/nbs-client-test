package com.nbs.biz.data.dao;

import org.apache.ibatis.session.SqlSession;

/**
 * @Package : com.nbs.biz.data.dao
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/5-17:15
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AttachmentInfoDao extends BasicDao {

    public AttachmentInfoDao(SqlSession session) {
        super(session, AttachmentInfoDao.class);
    }
}
