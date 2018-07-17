package io.nbs.service.ipfs;

import com.alibaba.fastjson.JSON;
import com.nbs.biz.model.ContactsEntity;
import io.ipfs.api.IPFS;
import io.ipfs.api.exceptions.IllegalIPFSMessageException;
import io.nbs.sdk.beans.OnlineMessage;
import io.nbs.sdk.prot.IPMParser;
import io.nbs.sdk.prot.IPMTypes;

import java.io.UnsupportedEncodingException;
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
       // it.send();
        it.generateOnlineMessage234();
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

    public void generateOnlineMessage234(){
        OnlineMessage message = new OnlineMessage();
        message.setIp("172.0.0.0");
        message.setTs(System.currentTimeMillis());
        message.setId("QmbykDm5MJ8Z3wKe2aEMWzCs5cwGNswa9WsBVTSsyiMeNp");//QmbykDm5MJ8Z3wKe2aEMWzCs5cwGNswa9WsBVTSsyiMeNp
        message.setAvatarFile("lambor234.png");
        message.setNick("lamsvr");
        message.setAvatar("QmT7CXKZ8iy8nYEsUhteA1d9UKWT5CY796qqjvt5ExFFrF");
        message.setAvatarSuffix(".png");
        message.setLocations("西班牙·巴塞罗那");
        message.setFrom("EiDKqKbPOx9T1DWfwylESVSD59WhKb5m7UHwW/uy8xQB1Q==");

        try {
            String svText = IPMParser.encode(message,IPMTypes.online);
            svText = IPMParser.urlDecode(svText);
            System.out.println(svText);
        } catch (IllegalIPFSMessageException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
