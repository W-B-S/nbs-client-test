package io.nbs.service.ipfs;

import com.alibaba.fastjson.JSON;
import com.nbs.biz.model.ContactsEntity;
import io.ipfs.api.IPFS;
import io.ipfs.api.JSONParser;

import java.net.URLEncoder;

/**
 * @Package : io.nbs.service.ipfs
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/2-15:26
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IPFSSenderTest {

    private static IPFS ipfs;
    public static String topic = "NBS";

    public IPFSSenderTest() {
        ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
    }

    public static void main(String[] agrs){
        IPFSSenderTest it = new IPFSSenderTest();
        it.send();
    }

    public void send(){
        try {
            ContactsEntity entity = new ContactsEntity("isidfisdf ","kadj安静看书发呆；阿里是否▲▲♠▲;lafafasfd");
            entity.setAvatar("kadj安静看书发呆；阿里是否▲▲♠▲;lafafasfd");
            String jsonData = JSON.toJSONString(entity);
            log(jsonData);
            jsonData = URLEncoder.encode(jsonData,"UTF-8");
            Object obj= ipfs.pubsub.pub(topic,jsonData);

            String json = JSON.toJSONString(obj);
            log(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void log(String s){
        System.out.println(s);
    }
}
