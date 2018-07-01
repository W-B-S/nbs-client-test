package com.nbs.im.service;

import com.nbs.entity.ContactsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package : com.nbs.im.service
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/1-23:25
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class PeerServiceImpl {

    /**
     * 开启客户端加载peers LIST
     * @param filterNoFrom 是否过滤信息不全的
     * @return
     */
    public List<ContactsItem> loadPeers(Boolean filterNoFrom){
        if(filterNoFrom==null)filterNoFrom = false;
        List<ContactsItem>  contactsItems = new ArrayList<>();
        /**
         * ipfs 获取
         */
        //TODO

        //return contactsItems;
        return init4Test(25);
    }


    private List<ContactsItem> init4Test(int size){
        String nick = "";
        String hash = "QmaF4D3YrRiAhvjsN1hMWBTxSJ7e4aA3d1rs6XwXi94udN";

        List<ContactsItem>  contactsItems = new ArrayList<>();
        for(int i = 0 ;i<size;i++){
            ContactsItem item = new ContactsItem();
            item.setId(hash);
            item.setFormid("QmaF4D3YrRiAhvjsN1hMWBTxSJ7e4aA3d1rs6XwXi94udN");
            item.setName("Hlsa"+i);
            item.setAvatar("QmetTG1vuiEmuLdczNK7dgZAqvVgr4icZFJxSc62SBvJtn");
            item.setAvatarSuffix(".png");
            item.setOnline(1);
            contactsItems.add(item);
        }
        return contactsItems;
    }
}
