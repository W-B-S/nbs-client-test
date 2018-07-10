package com.nbs.biz.service;


import com.nbs.biz.data.dao.BasicDao;
import com.nbs.biz.data.entity.BasicEntity;

import java.util.List;

/**
 * @Package : com.nbs.biz.service
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/27-17:52
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class BasicService<T extends BasicDao, E extends BasicEntity> {
    T dao;

    public void setDao(T dao)
    {
        this.dao = dao;
    }

    public int insert(BasicEntity entity)
    {
        return dao.insert(entity);
    }

    public List<E> findAll()
    {
        return dao.findAll();
    }

    public E findById(String id)
    {
        return (E) dao.findById(id);
    }

    public List<E> find(String field, Object val)
    {
        return dao.find(field, val);
    }

    public int delete(String id)
    {
        return dao.delete(id);
    }

    public int deleteAll()
    {
        return dao.deleteAll();
    }

    public int update(BasicEntity entity)
    {
        return dao.update(entity);
    }

    public int updateIgnoreNull(BasicEntity entity)
    {
        return dao.updateIgnoreNull(entity);
    }

    public int count()
    {
        return dao.count();
    }

    public boolean exist(String id)
    {
        return dao.exist(id);
    }
}
