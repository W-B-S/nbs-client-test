package com.nbs.biz.service;

import com.nbs.biz.data.dao.PeerLoginDao;
import com.nbs.biz.data.entity.PeerLoginEntity;
import io.nbs.commons.helper.DateHelper;
import io.nbs.sdk.beans.PeerInfo;
import org.apache.ibatis.session.SqlSession;

/**
 * @Package : com.nbs.biz.service
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/29-20:59
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class PeerLoginService extends BasicService<PeerLoginDao,PeerLoginEntity> {

    public PeerLoginService(SqlSession sqlSession) {
        dao = new PeerLoginDao(sqlSession);
    }

    /**
     *
     * @param entity
     * @return
     */
    public int updateOrInsertPeer(PeerLoginEntity entity){
       if(exist(entity.getId())){
          return dao.updateIgnoreNull(entity);
       }else {
           return dao.insert(entity);
       }
    }

    /**
     *
     * @param info
     * @return
     */
    public int refreshLoginInfo(PeerInfo info){
        if(info==null)return 0;
        PeerLoginEntity entity = convertFromPeerInfo(info);
        return updateOrInsertPeer(entity);
    }

    public static PeerLoginEntity convertFromPeerInfo(PeerInfo info){
        PeerLoginEntity entity = new PeerLoginEntity();
        entity.setId(info.getId());
        entity.setNick(info.getNick());
        entity.setAvatar(info.getAvatar());
        entity.setAvatarSuffix(info.getAvatarSuffix());
        entity.setIp(info.getIp());
        entity.setFromid(info.getFrom());
        entity.setSysuser(System.getProperty("user.name",""));
        entity.setLmtime(DateHelper.currentSecond());
        return entity;
    }
}
