package com.nbs.biz.data.dao;

import com.nbs.biz.data.entity.AttachmentInfoEntity;
import io.nbs.sdk.page.PageCondition;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<AttachmentInfoEntity> pageList(PageCondition condition){
        List list = session.selectList("pageList",condition);
        return list;
    }

    public int pageTotal(PageCondition condition){
        Integer total = session.selectOne("pageTotal",condition);
        return total;
    }
}
