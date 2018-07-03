package io.nbs.commons.utils;

import com.alibaba.fastjson.JSON;
import com.nbs.entity.PeerBoradcastInfo;
import io.ipfs.api.exceptions.IllegalIPFSMessageException;
import com.nbs.ipfs.entity.IpfsMessage;
import io.ipfs.multihash.Multihash;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * @Package : com.nbs.utils
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/24-14:21
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class Base64CodecUtil {
    private static final Base64 base64 = new Base64();

    /**
     *
     * @param origin
     * @return
     */
    public static String encode(String origin){
        if(origin==null||origin.length()==0)return origin;
        try {
            byte[] encodeByte = origin.getBytes(CharEncoding.UTF_8);
            return base64.encodeToString(encodeByte);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param baseStr
     * @return
     */
    public static String decode(String baseStr){
        if(baseStr==null||baseStr.length()==0)return baseStr;
        try {
            String result = new String(base64.decode(baseStr),CharEncoding.UTF_8);
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return baseStr;
        }
    }









}
