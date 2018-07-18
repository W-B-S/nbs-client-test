package com.nbs.biz.data.dao;

import com.nbs.biz.data.entity.BasicEntity;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package : com.nbs.biz.dao
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/23-21:08
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class BasicDao<E> {
    private Logger logger = LoggerFactory.getLogger(BasicDao.class);
    protected SqlSession session;
    private String className;


    public BasicDao(SqlSession session, Class clazz)
    {
        this.session = session;
        this.className = clazz.getName();
    }

    public int insert(BasicEntity model)
    {
        return session.insert(className + ".insert", model);
    }

    public List<E> findAll()
    {
        //return session.selectList(className + ".findAll");
        return _findAll(0);
    }

    private List<E> _findAll(int time)
    {
        if (time > 10)
        {
            System.out.println("查询到 BasicModelList 对象失败次数>10，放弃查询");
            logger.warn("{}->findAll(),查询到 BasicModelList 对象失败次数>10，放弃查询",className);
            return null;
        }

        try
        {
            return session.selectList(className + ".findAll");
        } catch (PersistenceException exception)
        {
            System.out.println("没有查询到 BasicModelList 对象，继续查询");
            return _findAll(++time);
        }
    }

    public BasicEntity findById(String id)
    {
        return _findById(id, 0);
    }

    private BasicEntity _findById(String id, int time)
    {
        if (time > 10)
        {
            System.out.println("查询到 BasicModel 对象失败次数>10，放弃查询 id："+id);
            return null;
        }

        try
        {
            return (BasicEntity) session.selectOne(className + ".findById", id);
        } catch (PersistenceException exception)
        {
            System.out.println("没有查询到 BasicModel 对象，继续查询:"+className+".findById");
            return _findById(id, ++time);
        }
    }

    public List<E> find(String field, Object val)
    {
        Map map = new HashMap();
        map.put("field", field);

        if (val instanceof String)
        {
            map.put("val", "'" + val + "'");
        }
        else
        {
            map.put("val", val);
        }

        return session.selectList(className + ".find", map);
    }

    public int delete(String id)
    {
        return session.delete(className + ".delete", id);
    }

    public int deleteAll()
    {
        return session.delete(className + ".deleteAll");
    }

    public int update(BasicEntity model)
    {
        return session.update(className + ".update", model);
    }

    public int updateIgnoreNull(BasicEntity model)
    {
        return session.update(className + ".updateIgnoreNull", model);
    }

    public List updateField(String field, Object val)
    {
        Map map = new HashMap();
        map.put("field", field);

        if (val instanceof String || val instanceof Boolean)
        {
            map.put("val", "'" + val + "'");
        }
        else
        {
            map.put("val", val);
        }

        return session.selectList(className + ".updateField", map);
    }

    public int count()
    {
        return (int) session.selectOne(className + ".count");
    }

    public boolean exist(String id)
    {
        return ((int) (session.selectOne(className + ".exist", id))) > 0;
    }

}
