package io.nbs.client.services;

import com.alibaba.fastjson.JSON;
import io.ipfs.api.IPFS;
import io.ipfs.api.exceptions.IllegalIPFSMessageException;
import io.nbs.client.Launcher;
import io.nbs.client.listener.IPFSSubscribeListener;
import io.nbs.client.listener.OnlineNotifier;
import io.nbs.client.ui.panels.im.ChatPanel;
import io.nbs.commons.helper.ConfigurationHelper;

import io.nbs.sdk.beans.*;
import io.nbs.sdk.prot.IPMParser;
import io.nbs.sdk.prot.IPMTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Package : io.nbs.client.services
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/3-17:55
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IpfsMessageReceiver{

    private static final Logger logger = LoggerFactory.getLogger(IpfsMessageReceiver.class);
    private IPFSSubscribeListener subscribeListener;
    private OnlineNotifier onlineNotifier;
    List<Map<String, Object>> resList = Collections.synchronizedList(new ArrayList<>());
    private IPFS ipfs;

    private static long sleepTimes = 1*600;
    private static long sleepGetTimes = 2*1000;
    private String worldTopic;
    boolean ctrlSign = true;
    private static boolean runing = false;

    public IpfsMessageReceiver() {
        worldTopic = IpfsMessageSender.NBSWORLD_IMS_TOPIC;
        ipfs = new IPFS(ConfigurationHelper.getInstance().getIPFSAddress());
    }

    public void startReceiver(){
        ctrlSign =true;
        new Thread(()->{
            logger.info("启动消息订阅{}......",worldTopic);
            while (ctrlSign){
                try {
                    TimeUnit.MILLISECONDS.sleep(sleepTimes);
                } catch (InterruptedException e) {
                }
                try {
                    ipfs.pubsub.sub(worldTopic,resList::add,t->t.printStackTrace());
                } catch (IOException e) {
                    logger.error("订阅器异常停止，{}",e.getMessage());
                    runing = false;
                    ctrlSign =false;
                }
            }

        }).start();

        /**
         * 取消息
         *
         */
        new Thread(()->{
            while (ctrlSign){
                try {
                    TimeUnit.MILLISECONDS.sleep(sleepGetTimes);
                } catch (InterruptedException e) { }
                if(resList.size()>0){
                    logger.info("收到{}条消息.",resList.size());
                    List<String> jsonList = new ArrayList<>();
                    for(Map<String,Object> msg : resList){
                        Object from = msg.get("from");
                        logger.info(from.toString());
                        String json = JSON.toJSONString(msg);
                        logger.info(json);
                        jsonList.add(json);
                    }
                    resList.clear();
                    proccessMessage(jsonList);
                }
            }
        }).start();
        runing = true;
    }

    /**
     * 处理消息
     * @param jsonMessages
     */
    private void proccessMessage(List<String> jsonMessages){
        new Thread(()->{
            for(String json: jsonMessages){
                logger.info("处理消息....");
                StandardIPFSMessage standardIPFSMessage = null;
                try {
                    standardIPFSMessage = IPMParser.decodeStandardIPFSMessage(json);
                } catch (IllegalIPFSMessageException e) {
                    logger.warn("消息JSON ：{}解析失败，忽略.可能原因:{}",json,e.getMessage());
                    continue;
                } catch (UnsupportedEncodingException e) {

                    logger.warn("消息JSON ：{}解析失败，忽略.可能原因:{}",json,e.getMessage());
                    continue;
                }
                if(standardIPFSMessage==null){
                    logger.warn("消息JSON ：{}解析失败，忽略.",json);
                    continue;
                }
                if(standardIPFSMessage.getMtype().equals(IPMTypes.nomarl.name())){
                    if(subscribeListener==null)continue;
                    MessageItem item = IPMParser.convertMessageItem(standardIPFSMessage);
                    item.setMessageType(1);
                    PeerInfo info =Launcher.currentPeer;
                    logger.info("{}<=====>{}",info.getId(),item.getFrom());

                    subscribeListener.notifyRecvMessage(item);
                }
                if(standardIPFSMessage.getMtype().equals(IPMTypes.online.name())){
                    try {
                        SystemCtrlMessageBean<OnlineMessage> ctrlMessageBean = IPMParser.convertOnlineMessage(standardIPFSMessage);
                        if(ctrlMessageBean==null||onlineNotifier==null){
                            continue;
                        }
                        onlineNotifier.notifyRecvystemMessage(ctrlMessageBean);
                    } catch (IllegalIPFSMessageException e) {
                        logger.warn("消息JSON ：{}解析失败，忽略.",json);
                        continue;
                    }
                }
            }
        }).start();
    }

    public void stopRecived(){
        this.ctrlSign =false;
    }

    /**
     *
     * @return
     */
    public static boolean isRuning() {
        return runing;
    }

    public void setSubscribeListener(IPFSSubscribeListener subscribeListener) {
        this.subscribeListener = subscribeListener;
    }

    public void setOnlineNotifier(OnlineNotifier onlineNotifier) {
        this.onlineNotifier = onlineNotifier;
    }
}
