package io.nbs.sdk.prot;

/**
 * @Package : io.nbs.sdk.prot
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/4-0:29
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public enum IPMTypes {
    /**
     * $ON.B64.J$xxxxxsssds$
     */
    online("ON.B64.J","$"),
    /**
     * $PC.B64.J$xxxxxxxx$
     */
    pctrl("PC.B64.J","$"),
    /**
     * URLEncode
     * $T.UE.S$xxxxxxxx$
     */
    nomarl("IMN.UE.S","$"),
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

    /**
     *
     * @param text
     * @return
     */
    public static IPMTypes parserProtocol(String text){
        if(text==null||text.length()==0)return IPMTypes.unkonw;
        for(IPMTypes types : IPMTypes.values()){
            if(types==IPMTypes.unkonw)continue;
            if(text.startsWith(types.getPrefix())&&text.endsWith(types.separator)){
                return types;
            }
        }
        return IPMTypes.unkonw;
    }

    public String protocolSplit(String str){
        if(this==IPMTypes.unkonw)return str;
        str = str.substring(this.getPrefixLength(),str.length()-this.separator.length());
        return str;
    }
}
