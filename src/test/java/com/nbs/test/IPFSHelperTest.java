package com.nbs.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.nbs.ipfs.IPFSHelper;
import com.nbs.ipfs.entity.IpfsMessage;
import io.ipfs.api.IPFS;
import io.ipfs.api.JSONParser;
import io.ipfs.nbs.helper.ConfigurationHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Package : com.nbs.test
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/23-0:50
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IPFSHelperTest {

    private static boolean r = true;
    public static final IPFSHelper helper = IPFSHelper.getInstance();
    public static final String TOPIC = "NBS";

    public static List<Map<String,Object>> messages = new ArrayList<>();

    public static void main(String[] args){
        IPFSHelperTest t = new IPFSHelperTest();

       /* new Thread(() -> {
            while (true){
                try {
                    System.out.println("--------------begin-------------");
                    TimeUnit.MILLISECONDS.sleep(100);
                    Stream<Map<String, Object>> maps = helper.getIpfs().pubsub.sub(TOPIC);
                    List<Map<String, Object>> res = maps.limit(1).collect(Collectors.toList());
                    String json = JSONParser.toString(res.get(0));
                    System.out.println(json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
    }



    public void subThread(int sleepTimes){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //
                    // 先他妈休息一会儿
                    //TimeUnit.SECONDS.sleep(sleepTimes);
                    Stream<Map<String,Object>> sub = helper.getIpfs().pubsub.sub(TOPIC);
                    List<Map> lst = sub.limit(1).collect(Collectors.toList());
                    String json = JSONParser.toString(lst.get(0));
                    System.out.println(json);
                    IpfsMessage im = JSON.parseObject(json,IpfsMessage.class);
                    System.out.println(">>>>>>>>>>.."+ JSON.toJSONString(lst));
                    subThread(sleepTimes);
                } catch (IOException e) {
                    e.printStackTrace();
                    subThread(sleepTimes);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    subThread(sleepTimes);
                } catch (Exception e) {
                    e.printStackTrace();
                    subThread(sleepTimes);
                }
            }
        }).start();
    }



}
