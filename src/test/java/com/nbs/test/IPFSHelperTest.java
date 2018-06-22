package com.nbs.test;

import com.nbs.ipfs.IPFSHelper;

/**
 * @Package : com.nbs.test
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/23-0:50
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IPFSHelperTest {
    public static void main(String[] args){
        IPFSHelper helper = IPFSHelper.getInstance();
        System.out.println(IPFSHelper.CLIENT_PEERID);
        //String nickname = helper.generateNickName();
        System.out.println("");
    }
}
