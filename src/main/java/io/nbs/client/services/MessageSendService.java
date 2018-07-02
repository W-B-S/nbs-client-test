package io.nbs.client.services;

import org.apache.ibatis.session.SqlSession;

/**
 * @Package : io.nbs.client.services
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/2-18:16
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class MessageSendService {
    private SqlSession sqlSession;

    public MessageSendService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
}
