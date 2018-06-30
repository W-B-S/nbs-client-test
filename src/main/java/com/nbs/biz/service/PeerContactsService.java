package com.nbs.biz.service;

import com.nbs.biz.data.dao.PeerContactsDao;
import com.nbs.biz.data.entity.PeerContactsEntity;
import io.ipfs.nbs.peers.PeerInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * @Package : com.nbs.biz.service
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-14:12
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class PeerContactsService extends BasicService<PeerContactsDao,PeerContactsEntity> {
    public PeerContactsService(SqlSession sqlSession) {
        dao = new PeerContactsDao(sqlSession);
    }
}
