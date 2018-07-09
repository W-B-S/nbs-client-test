package io.nbs.service.ipfs;

import io.ipfs.api.exceptions.IllegalIPFSMessageException;
import io.nbs.sdk.beans.OnlineMessage;
import io.nbs.sdk.prot.IPMParser;
import io.nbs.sdk.prot.IPMTypes;

import java.io.UnsupportedEncodingException;

/**
 * @Package : io.nbs.service.ipfs
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/9-14:36
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IMParseTest {

    public static void main(String[] args){
        IMParseTest it = new IMParseTest();
        it.generateOnmessage();
    }

    private void generateOnmessage(){
        OnlineMessage message = get68Message();

        try {
            String enText = IPMParser.encode(message, IPMTypes.online);
            enText = IPMParser.urlDecode(enText);
            System.out.println(enText);
        } catch (IllegalIPFSMessageException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private OnlineMessage get68Message(){
        OnlineMessage message = new OnlineMessage();
        message.setId("QmVZq3rbxZHWyCf81gYtB2xYfCh7woP9u9etRpZ1v9ZZUH");
        message.setNick("NBSChain_SVR68");
        message.setFrom("EiBrYg0RqeWuZIP7mvxiIuX4trIsm2ti/6chFFwkLMs1Tg==");
        message.setIp("101.200.196.68");
        message.setLocations("中国·北京·大兴区");
        message.setAvatarSuffix(".png");
        message.setAvatar("QmeBizBEiABSSWDqo755svoCeN9KjLEdEg4vY5tddkKGQZ");
        message.setAvatarFile("svr68.png");
        message.setTs(System.currentTimeMillis());
        return message;
    }
}
