package com.nbs.biz.service;

import com.nbs.biz.data.dao.AttachmentInfoDao;
import com.nbs.biz.data.entity.AttachmentInfoEntity;
import org.apache.ibatis.session.SqlSession;

/**
 * @Package : com.nbs.biz.service
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/5-17:36
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AttachmentInfoService extends BasicService<AttachmentInfoDao,AttachmentInfoEntity> {

    public AttachmentInfoService(SqlSession sqlSession) {
        dao = new AttachmentInfoDao(sqlSession);
        setDao(dao);
    }
}
