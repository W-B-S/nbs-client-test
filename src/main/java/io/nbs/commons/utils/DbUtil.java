package io.nbs.commons.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @Package : com.nbs.utils
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/23-21:53
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class DbUtil {
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

    private DbUtil(){

    }

    /**
     *
     * @return
     */
    public static SqlSession getSqlSession() {
        return sqlSession;
    }
}
