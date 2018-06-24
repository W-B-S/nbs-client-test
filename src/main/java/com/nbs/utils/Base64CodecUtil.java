package com.nbs.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static final String REGX_BASE64_MSG = "^\\$BASE64\\|\\w+\\|$";

    public static final String BASE64_START = "$BASE64|";
    public static final String BASE64_END = "|";
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


    public static boolean isBase64(String string){
        Pattern pattern = Pattern.compile(REGX_BASE64_MSG);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    public static boolean isBase64encode(String base64Str){
        return base64Str.startsWith(BASE64_START)&&base64Str.endsWith(BASE64_END);
    }

    /**
     *
     * @param origin
     * @return
     */
    public static String encodeIPFSMsg(String origin){
        String rs = encode(origin);
        return BASE64_START+rs+BASE64_END;
    }

    /**
     * IPFS 消息解码
     * @param encodeStr
     * @return
     */
    public static String decodeIPFSMsg(String encodeStr){
        if(isBase64encode(encodeStr)){
            int len = encodeStr.length();
            encodeStr = encodeStr.substring(BASE64_START.length(),len-2);
            return decode(encodeStr);
        }else {
            return encodeStr;
        }
    }

    /**
     * 编码控制消息
     * @param o
     * @param type
     * @return
     */
    public static String encodeCtrlMsg(Object o,CtrlTypes type){
        if(o==null)return null;
        if(type==null)type=CtrlTypes.online;
        StringBuffer mSb = new StringBuffer();
        mSb.append(type.sperator).append(type.starter).append(type.sperator);
        String json = JSON.toJSONString(o);
        mSb.append(encode(json));
        mSb.append(type.sperator);
        return mSb.toString();
    }

    /**
     *
     * @param baseStr
     * @param type
     * @return
     */
    public static String decodeCtrlMsg(String baseStr,CtrlTypes type){
        if(baseStr==null)return null;
        if(type==null)type=CtrlTypes.online;
        if(baseStr.startsWith(type.sperator+type.starter+type.sperator)&&baseStr.endsWith(type.sperator+"")){
            int len = baseStr.length();
            baseStr = baseStr.substring(type.starter.length()+2,len-2);
            return decode(baseStr);
        }else {
            return decode(baseStr);
        }
    }


    public static enum CtrlTypes{
        /**
         *
         */
        online("ONLINE",'$');

        private String starter;
        private char sperator;

        CtrlTypes(String starter, char sperator) {
            this.starter = starter;
            this.sperator = sperator;
        }

        public String getStarter() {
            return starter;
        }

        public char getSperator() {
            return sperator;
        }

        public String toJsonString() {
            return "CtrlTypes{" +
                    "starter='" + starter + '\'' +
                    ", sperator=" + sperator +
                    '}';
        }

        @Override
        public String toString() {
            return this.name().toString();
        }
    }

    public static void main(String[] args) {
        String in = "你好NBS 的圣诞  xccxv 节收到 \\n slfsak阿卡恢复大师的/n";
        System.out.println(in);
        String encodeIn = encode(in);
        System.out.println(encodeIn);
        String enMsg = "$BASE64|"+encodeIn+"|";
        System.out.println(enMsg);
        String dec = decodeIPFSMsg(enMsg);
        System.out.println(dec);
    }
}
