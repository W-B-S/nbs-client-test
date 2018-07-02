package io.nbs.client.services;

import org.apache.ibatis.session.SqlSession;

/**
 * @Package : io.nbs.client.services
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/2-15:22
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IpfsMessageSender {

    private SqlSession sqlSession;
    public IpfsMessageSender(SqlSession sqlSession) {
        sqlSession = sqlSession;
    }

    public void ipfsSend(){
        String message = "s";
    }
}
