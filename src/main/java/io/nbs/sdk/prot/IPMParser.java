package io.nbs.sdk.prot;

import com.alibaba.fastjson.JSON;
import io.ipfs.api.exceptions.IllegalIPFSMessageException;
import io.nbs.sdk.beans.OnlineMessage;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Package : io.nbs.sdk.prot
 * @Description :
 * <p>
 *     the ipfs message protocol parse util
 * </p>
 * @Author : lambor.c
 * @Date : 2018/7/2-16:44
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class IPMParser {
    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     *
     * @param data
     * @return
     * @throws IllegalIPFSMessageException
     * @throws UnsupportedEncodingException
     */
    public static String encode(String data) throws IllegalIPFSMessageException, UnsupportedEncodingException {
        if(StringUtils.isBlank(data))throw new IllegalIPFSMessageException("数据类型不符合要求{}."+data);
        String sendData = IPMTypes.nomarl.protocolContact(data);
        return URLEncoder.encode(data,DEFAULT_ENCODING);
    }

    /**
     *
     * @param data
     * @return
     * @throws IllegalIPFSMessageException
     * @throws UnsupportedEncodingException
     */
    public static String encode(Object data) throws IllegalIPFSMessageException, UnsupportedEncodingException {
        if(data==null) throw new IllegalIPFSMessageException("数据为空.");
        IPMTypes types = IPMTypes.nomarl;
        if(data instanceof OnlineMessage){
            types = IPMTypes.online;
        }
        return encode(data,types);
    }

    /**
     *
     * @param data
     * @param types
     * @return
     * @throws IllegalIPFSMessageException
     * @throws UnsupportedEncodingException
     */
    public static String encode(Object data,IPMTypes types) throws IllegalIPFSMessageException, UnsupportedEncodingException {
        if(types == null) types = IPMTypes.nomarl;
        if(data==null) throw new IllegalIPFSMessageException("数据类型不符合要求{}."+data.toString());
        String sendData;
        switch (types){
            case pctrl:
                if( !(data instanceof OnlineMessage))
                    throw new IllegalIPFSMessageException("数据类型不符合要求{}."+data.getClass().getName());
                sendData = types.protocolContact(JSON.toJSONString(data));
                break;
            case online:
                if( !(data instanceof OnlineMessage))
                    throw new IllegalIPFSMessageException("数据类型不符合要求{}."+data.getClass().getName());
                sendData = types.protocolContact(JSON.toJSONString(data));
                break;
            case nomarl:
                if((data instanceof String)){
                    sendData = types.protocolContact((String)data);
                }else if(data instanceof Integer || data instanceof Long || data instanceof Double || data instanceof Float){
                    sendData = String.valueOf(data);
                }else {
                    sendData = JSON.toJSONString(data);
                }
                break;
            case unkonw:
            case image:
            default:
                throw new IllegalIPFSMessageException("暂不支持的数据格式{}."+types.name());
        }

        return URLEncoder.encode(sendData,DEFAULT_ENCODING);
    }

    public static enum  IPMTypes{
        /**
         * $ON.B64.J$xxxxxsssds$
         */
        online("ON.B64.J","$"),
        /**
         * $PC.B64.J$xxxxxxxx$
         */
        pctrl("PC.B64.J","$"),
        /**
         * $PC.B64.J$xxxxxxxx$
         */
        nomarl("IMN.URLEN.S","$"),
        /**
         *
         */
        image("FIM.N.S","$"),
        /**
         *
         */
        unkonw("","");
        private String separator;
        private String tag;

        IPMTypes(String tag,String separator) {
            this.separator = separator;
            this.tag = tag;
        }

        public String getPrefix(){
            return this.separator+this.tag+this.separator;
        }

        public int getPrefixLength(){
            return getPrefix().length();
        }

        /**
         *
         * @param indata
         * @return
         */
        public String protocolContact(String indata){
            if(this!= IPMTypes.unkonw){
                StringBuilder sb = new StringBuilder();
                sb.append(separator).append(tag).append(separator).append(indata).append(separator);
                return sb.toString();
            }else {
                return indata;
            }
        }
    }
}
