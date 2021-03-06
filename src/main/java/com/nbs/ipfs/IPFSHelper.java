package com.nbs.ipfs;

import io.nbs.commons.utils.Base64CodecUtil;
import io.ipfs.api.IPFS;
import io.nbs.commons.helper.ConfigurationHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Package : com.nbs.ipfs
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/19-11:47
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IPFSHelper {

    private static Logger logger = LoggerFactory.getLogger(IPFSHolder.class);
    /**
     *
     */
    private ConcurrentHashMap secMap = new ConcurrentHashMap();
    public static String CLIENT_PEERID = null;

    private IPFS ipfs;

    /**
     *
     */
    public static final String NBSWORLD_CTRL_TOPIC = Base64CodecUtil.encode("$NBS.CTRL.J$");
    public static final String NBSWORLD_IMS_TOPIC = Base64CodecUtil.encode("nbsio.net");

    public IPFSHelper() {
        ipfs = new IPFS(ConfigurationHelper.getInstance().getIPFSAddress());
        try {
            Map m = ipfs.id();
            secMap.putAll(m);
            CLIENT_PEERID = m.get("ID") == null ? "" : m.get("ID").toString();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

    private static class IPFSHolder{
        static final IPFSHelper instance = new IPFSHelper();
    }


    public static IPFSHelper getInstance(){
        return IPFSHolder.instance;
    }

    /**
     *
     * @return
     */
    public IPFS rebuild(){
        ipfs = new IPFS(ConfigurationHelper.getInstance().getIPFSAddress());
        return ipfs;
    }

    public IPFS getIpfs() {
        return ipfs;
    }

    public String generateNickName(){
        String[] sources = new String[]{
                "8","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
                "a","b","c","d","e","f","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
                "0","1","2","3","4","5","6","7","9","8"
        };
        StringBuffer nick = new StringBuffer();
        nick.append("NBSChain_");
        for(int i=0;i<6;i++){
            int c = getRadom(sources.length);
            if(c<0||c>sources.length)c = 1;
            nick.append(sources[c-1]);
        }
        return nick.toString();
    }


    private int getRadom(int len){
        return (int)Math.round(Math.random()*len);
    }

    /**
     *
     * @param nick
     * @return
     * @throws IOException
     */
    public String updateNick(String nick) throws IOException {
        if(ipfs==null||StringUtils.isBlank(nick))return null;
        nick = nick.trim();
        ipfs.config.set(ConfigurationHelper.JSON_NICKNAME_KEY,nick);
        return nick;
    }



}
