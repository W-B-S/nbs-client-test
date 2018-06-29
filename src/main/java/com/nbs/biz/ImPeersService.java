package com.nbs.biz;

import com.alibaba.fastjson.JSON;
import com.nbs.entity.ContactsItem;
import com.nbs.ipfs.IPFSHelper;
import io.ipfs.api.IPFS;
import io.ipfs.api.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @Package : com.nbs.biz
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/26-15:03
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class ImPeersService {

    private static Logger logger = LoggerFactory.getLogger(ImPeersService.class);

    /**
     *
     * @param ipfs
     * @return
     */
    public List<String> getOnlinePeers(IPFS ipfs){
        List<String> result = null;
        try {
            Object o = ipfs.pubsub.peers(IPFSHelper.NBSWORLD_CTRL_TOPIC);
            result = (List<String>)JSONParser.getValue(o,"Strings");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
