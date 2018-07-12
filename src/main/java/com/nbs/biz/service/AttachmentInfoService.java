package com.nbs.biz.service;

import com.nbs.biz.data.dao.AttachmentInfoDao;
import com.nbs.biz.data.entity.AttachmentInfoEntity;
import io.nbs.client.vo.AttachmentDataDTO;
import io.nbs.sdk.page.PageCondition;
import io.nbs.sdk.page.PageModel;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package : com.nbs.biz.service
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/5-17:36
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class AttachmentInfoService extends BasicService<AttachmentInfoDao,AttachmentInfoEntity> {
    private static int PAGE_SIZE = 10;

    public AttachmentInfoService(SqlSession sqlSession) {
        dao = new AttachmentInfoDao(sqlSession);
        setDao(dao);
    }

    /**
     *
     * @param pageNo
     * @param searchHash
     * @return
     */
    public PageModel<AttachmentInfoEntity> queryPage(int pageNo,String searchHash){
        PageCondition condition = new PageCondition("ctime","DESC");
        condition.setPageConition(pageNo);
        condition.setSearchStr(searchHash);
        int total = dao.pageTotal(condition);
        List<AttachmentInfoEntity> list = dao.pageList(condition);
        return new PageModel<>(list,total);
    }

    public List<AttachmentDataDTO> getLearstCount(int pageNo, String searchHash){
        PageModel<AttachmentInfoEntity> pageModel = queryPage(pageNo,searchHash);
        return copyFromAttachmentInfoEntity(pageModel.getList());
    }

    /**
     *
     * @param infoEntities
     * @return
     */
    public List<AttachmentDataDTO> copyFromAttachmentInfoEntity(List<AttachmentInfoEntity> infoEntities){
        List<AttachmentDataDTO> resEntities = new ArrayList<>();
        if(infoEntities==null||infoEntities.size()==0)return resEntities;
        for(AttachmentInfoEntity entity : infoEntities){
            AttachmentDataDTO entity1 = new AttachmentDataDTO();
            BeanUtils.copyProperties(entity,entity1);
            resEntities.add(entity1);
        }
        return resEntities;
    }
}
