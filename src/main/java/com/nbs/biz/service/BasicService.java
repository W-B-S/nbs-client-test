package com.nbs.biz.service;

import com.nbs.biz.dao.BasicDao;
import com.nbs.biz.model.BasicModel;

import java.util.List;

/**
 * @Package : com.nbs.biz.service
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/27-17:52
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class BasicService<T extends BasicDao, E extends BasicModel> {
    T dao;

    public void setDao(T dao)
    {
        this.dao = dao;
    }

    public int insert(BasicModel model)
    {
        return dao.insert(model);
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

    public int update(BasicModel model)
    {
        return dao.update(model);
    }

    public int updateIgnoreNull(BasicModel model)
    {
        return dao.updateIgnoreNull(model);
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
