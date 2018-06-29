package io.ipfs.nbs.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @Package : io.ipfs.nbs.utils
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/29-14:21
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class DataBaseUtil {
    private static SqlSession sqlSession = null;
    static {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-conf.xml");
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
            sqlSession = factory.openSession(true);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DataBaseUtil(){
    }

    /**
     *
     * @return
     */
    public static SqlSession getSqlSession() {
        return sqlSession;
    }
}
