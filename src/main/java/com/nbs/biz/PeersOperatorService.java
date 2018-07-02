package com.nbs.biz;

import com.nbs.biz.data.entity.PeerContactsEntity;
import com.nbs.biz.service.PeerContactsService;
import com.nbs.tools.DateHelper;
import io.nbs.sdk.beans.PeerInfo;
import org.apache.ibatis.session.SqlSession;

/**
 * @Package : com.nbs.biz
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/30-14:18
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class PeersOperatorService {
    private PeerContactsService contactsService;
    private SqlSession sqlSession;

    public PeersOperatorService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
        contactsService = new PeerContactsService(sqlSession);
    }

    public PeerContactsService getContactsService() {
        return contactsService;
    }

    public boolean initSaveSelf(PeerInfo info,String remark){
        PeerContactsEntity entity = contactsService.findById(info.getId());
        int u = 0;
        if(entity==null){
            entity = convertContacts(null,info,1);
            entity.setRemark(remark);
            u = contactsService.insert(entity);
        }else {
            entity = convertContacts(entity,info,1);
            u = contactsService.update(entity);
        }
        return u>0;
    }

    /**
     *
     * @param entity
     * @param info
     * @param online
     * @return
     */
    public PeerContactsEntity convertContacts(PeerContactsEntity entity,PeerInfo info,int online){
        if(entity==null)entity = new PeerContactsEntity();
        entity.setId(info.getId());
        entity.setNick(info.getNick());
        entity.setAvatar(info.getAvatar());
        entity.setFromid(info.getFrom());
        entity.setAvatarSuffix(info.getAvatarSuffix());
        entity.setIp(info.getIp());
        entity.setLocations(info.getLocations());
        entity.setOnline(online==1 ? 1 : 0);
        entity.setLmtime(DateHelper.currentSecond());
        return entity;
    }
}
