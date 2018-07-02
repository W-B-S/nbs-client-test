package com.nbs.biz.service;

import com.nbs.biz.data.dao.PeerMessageDao;
import com.nbs.biz.data.entity.BasicEntity;
import com.nbs.biz.data.entity.PeerMessageEntity;
import io.nbs.commons.helper.DateHelper;
import org.apache.ibatis.session.SqlSession;

/**
 * @Package : com.nbs.biz.service
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/2-16:03
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class PeerMessageService extends BasicService<PeerMessageDao,PeerMessageEntity> {

    public PeerMessageService(SqlSession sqlSession) {
        dao = new PeerMessageDao(sqlSession);
    }

    /**
     *
     * @param entity
     * @return
     */
    public int insert(PeerMessageEntity entity) {
        if(entity.getCtime()==null)entity.setCtime(DateHelper.currentSecond());
        if(entity.getRecvtime()==null)entity.setRecvtime(DateHelper.currentSecond());
        return super.insert(entity);
    }
}
