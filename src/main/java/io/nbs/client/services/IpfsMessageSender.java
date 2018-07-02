package io.nbs.client.services;

import UI.common.Base64CodecUtil;
import io.ipfs.api.IPFS;
import io.nbs.sdk.beans.OnlineMessage;
import io.nbs.sdk.prot.IPMParser;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Package : io.nbs.client.services
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/2-15:22
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IpfsMessageSender {
    private static final Logger logger = LoggerFactory.getLogger(IpfsMessageSender.class);
    /**
     *
     */
    private static final Base64 base64 = new Base64();
    /**
     * 暂时不用
     */
    @Deprecated
    public static final String NBSWORLD_CTRL_TOPIC = Base64CodecUtil.encode("$NBS.CTRL.J$");

    public static final String NBSWORLD_IMS_TOPIC = Base64CodecUtil.encode("nbsio.net");

    private IPFS ipfs;

    public IpfsMessageSender(IPFS ipfs) {
        this.ipfs = ipfs;
    }

    /**
     *
     * @param data
     * @param types
     * @return
     * @throws Exception
     */
    public String sendControlMessage(Object data, IPMParser.IPMTypes types) throws Exception {
        String encodeData = IPMParser.encode(data,types);
        logger.info(NBSWORLD_IMS_TOPIC);
        ipfs.pubsub.pub(NBSWORLD_IMS_TOPIC,encodeData);
        return encodeData;
    }

    /**
     * 发送上线通知
     * @param
     * @return
     * @throws Exception
     */
    public String sendOnline(OnlineMessage message) throws Exception {
        String encodeData = IPMParser.encode(message,IPMParser.IPMTypes.online);
        logger.info(NBSWORLD_IMS_TOPIC);
        ipfs.pubsub.pub(NBSWORLD_IMS_TOPIC,encodeData);
        return encodeData;
    }

    /**
     *
     * @param message
     * @return
     * @throws Exception
     */
    public Object ipfsSendMessage(String message) throws Exception {
        String sendData = IPMParser.encode(message);
        return ipfs.pubsub.pub(NBSWORLD_IMS_TOPIC,sendData);
    }


    public IPFS getIpfs() {
        return ipfs;
    }

    public void setIpfs(IPFS ipfs) {
        this.ipfs = ipfs;
    }
}
