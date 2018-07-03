package io.nbs.sdk.prot;

import com.alibaba.fastjson.JSON;
import io.ipfs.api.exceptions.IllegalIPFSMessageException;
import io.nbs.commons.utils.Base64CodecUtil;
import io.nbs.sdk.beans.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

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
     * 默认NORMAL
     * 纯文本采用URL
     * @param data
     * @return String
     * @throws IllegalIPFSMessageException
     * @throws UnsupportedEncodingException
     */
    public static String encode(String data) throws IllegalIPFSMessageException, UnsupportedEncodingException {
        if(StringUtils.isBlank(data))throw new IllegalIPFSMessageException("数据类型不符合要求{}."+data);
        String sendData = IPMTypes.nomarl.protocolContact(data);
        return URLEncoder.encode(data,DEFAULT_ENCODING);
    }

    /**
     * 直接URL
     * @param content
     * @return
     */
    public static String urlEncode(String content){
        if(StringUtils.isBlank(content))return null;
        try {
            return URLEncoder.encode(content,DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return content;
        }
    }
    /**
     *
     * @param data
     * @return
     * @throws IllegalIPFSMessageException
     * @throws UnsupportedEncodingException
     */
    public static String encodeOnline(OnlineMessage data) throws IllegalIPFSMessageException, UnsupportedEncodingException {
        if(data==null) throw new IllegalIPFSMessageException("数据为空.");
        return encode(data,IPMTypes.online);
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
                sendData = data.toString();
                break;
            case image:
            default:
                throw new IllegalIPFSMessageException("暂不支持的数据格式{}."+types.name());
        }

        return URLEncoder.encode(sendData,DEFAULT_ENCODING);
    }


    /**
     * 将data 内容解析到Content
     * type 和content优质
     * @param json
     * @return
     * @throws IllegalIPFSMessageException
     */
    public static StandardIPFSMessage decodeStandardIPFSMessage(String json) throws IllegalIPFSMessageException {
        if(json==null||json.length()==0)throw new IllegalIPFSMessageException("json 数据为null或空串.");
        StandardIPFSMessage simsg = JSON.parseObject(json,StandardIPFSMessage.class);
        String endata = simsg.getData();
        String deData = Base64CodecUtil.decode(endata);
        IPMTypes types = IPMTypes.parserProtocol(deData);
        if(types==IPMTypes.unkonw){
            simsg.setContent(deData);
        }else {
            simsg.setContent(types.protocolSplit(deData));
        }
        return simsg;
    }

    /**
     *
     * @param message
     * @return
     */
    public static IMMessageBean convertIMMessageBean(StandardIPFSMessage message){

        IMMessageBean bean = new IMMessageBean();
        bean.setFrom(message.getFrom());
        bean.setSeqno(message.getSeqno());
        bean.setMtype(message.getMtype());
        bean.setTopics(message.getTopicIDs());
        bean.setContent(message.getContent());
        bean.setTs(System.currentTimeMillis());
        return bean;
    }

    /**
     *
     * @param message
     * @return
     */
    public static MessageItem convertMessageItem(StandardIPFSMessage message){
        MessageItem bean = new MessageItem();
        bean.setNeedToResend(false);
        bean.setTimestamp(System.currentTimeMillis());
        bean.setFrom(message.getFrom());
        bean.setSeqno(message.getSeqno());
        bean.setMessageContent(message.getContent());
        bean.setUpdatedAt(System.currentTimeMillis());
        return bean;
    }

    /**
     *
     * @param message
     * @return
     * @throws IllegalIPFSMessageException
     */
    public static SystemCtrlMessageBean convertOnlineMessage(StandardIPFSMessage message) throws IllegalIPFSMessageException {
        if(!message.getMtype().equals(IPMTypes.online.name()))throw new IllegalIPFSMessageException("数据类型不对，无法转换成系统消息.");
        if(StringUtils.isBlank(message.getContent()))throw new IllegalIPFSMessageException("数据内容不存在，无法转换成系统消息.");
        OnlineMessage onlineMessage = JSON.parseObject(message.getContent(),OnlineMessage.class);
        if(onlineMessage==null||onlineMessage.getId()==null)throw new IllegalIPFSMessageException("数据内容无效，无法转换成系统消息.");
        SystemCtrlMessageBean ctrlMessageBean = new SystemCtrlMessageBean(onlineMessage);
        ctrlMessageBean.setFrom(message.getFrom());
        ctrlMessageBean.setMtype(message.getMtype());
        ctrlMessageBean.setSeqno(message.getSeqno());
        ctrlMessageBean.setTopics(message.getTopicIDs());
        ctrlMessageBean.setTs(System.currentTimeMillis());
        ctrlMessageBean.setPid(onlineMessage.getId());
        return ctrlMessageBean;
    }
}
