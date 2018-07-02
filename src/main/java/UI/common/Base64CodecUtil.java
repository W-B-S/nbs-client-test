package UI.common;

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



    /**
     *
     * @param message
     * @param types
     * @return
     */
    @Deprecated
    public static String encodeByCtrlType(Object message,CtrlTypes types){
        if(message==null)return null;
        StringBuffer sb = new StringBuffer();
        if(types==null)types = CtrlTypes.normal;
        switch (types){
            case online:
            case pctrl:
                sb.append(types.sperator).append(types.starter).append(types.sperator);
                sb.append(encode(JSON.toJSONString(message)));
                sb.append(types.sperator);
                return sb.toString();
            case normal:
                sb.append(types.sperator).append(types.starter).append(types.sperator);
                sb.append(encode(message.toString()));
                sb.append(types.sperator);
                return sb.toString();
            default:
                sb.append(encode(message.toString()));
                return sb.toString();
        }
    }

    /**
     * 只解析出协议类型并做Base64 基础解码放入content，
     * content内容解析具体有CtrlType 解析
     * @param m
     * @return
     */
    @Deprecated
    public static IpfsMessage parseIpmsMessageCtrlType(IpfsMessage m){
        if(m==null||m.getData()==null||m.getData().length()<1)return null;
        String decode64 = decode(m.getData());
        CtrlTypes t = null;
        int len = decode64.length();
        for(CtrlTypes types : CtrlTypes.values()){
            if(types != CtrlTypes.unkonw &&
                    decode64
                    .startsWith(types.sperator + types.starter + types.sperator)
                    && decode64.endsWith(types.sperator)){
                t = types;
                m.setContents(decode64.substring(types.starter.length()+2,len-1));
                m.setTypes(t);
                break;
            }
        }
        if(m.getTypes()==null){
            //无效消息
            m.setTypes(CtrlTypes.unkonw);
            m.setContents(decode64);
        }
        return m;
    }




    @Deprecated
    public static enum CtrlTypes{
        /**
         * $ON.B64.J$xxxxxsssds$
         * 在线通知
         */
        online("ON.B64.J","$"),
        /**
         * $PC.B64.J$xxxxxxxx$
         * 控制消息
         */
        pctrl("PC.B64.J","$"),
        /**
         * 正常消息
         * $IM.B64.S$xxx$
         */
        normal("IM.B64.S","$"),
        /**
         * 未知
         */
        unkonw("","");

        private String starter;
        private String sperator;

        CtrlTypes(String starter, String sperator) {
            this.starter = starter;
            this.sperator = sperator;
        }

        /**
         *
         * @return
         */
        public int getPreffixLength(){
            return this.getPreffix().length();
        }

        public String getStarter() {
            return starter;
        }

        public String getSperator() {
            return sperator;
        }

        public String toJsonString() {
            return "CtrlTypes{" +
                    "starter='" + starter + '\'' +
                    ", sperator=" + sperator +
                    '}';
        }

        /**
         *
         * @return
         */
        public String getPreffix(){
            return this.sperator+this.starter+this.sperator;
        }
        @Override
        public String toString() {
            return this.name().toString();
        }
    }

}
