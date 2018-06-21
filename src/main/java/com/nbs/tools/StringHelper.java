package com.nbs.tools;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Package : com.nbs.tools
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/19-18:17
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class StringHelper {
    public static final Pattern CRLF = Pattern.compile("(\n\t|\n|\t)");
    public static final String[] units = new String[]{"","KB","GB","TB"};
    private StringHelper(){}
    private static class StringHolder{
         static final StringHelper instance = new StringHelper();
    }

    public static StringHelper getInstance(){
        return StringHolder.instance;
    }

    /**
     *
     * @param in
     * @return
     */
    public String replaceAllCRLF2Br(String in){
        if(in==null)return null;
        Matcher matcher = CRLF.matcher(in);
        if(matcher.find()){
            return matcher.replaceAll("<br>");
        }
        return in;
    }

    /**
     *
     * @param origin
     * @return
     */
    public String fileSizeConvert(String origin){
        if(origin==null)return "0";
        long size = 0;
        if(origin.toUpperCase().endsWith("KB")){
            size = Long.parseLong(origin.substring(0,origin.toUpperCase().lastIndexOf("KB")));
        }else if(origin.toUpperCase().endsWith("K")){
            size = Long.parseLong(origin.substring(0,origin.toUpperCase().lastIndexOf("K")));
        }
        else if(origin.toUpperCase().endsWith("MB")){
            size = Long.parseLong(origin.substring(0,origin.toUpperCase().lastIndexOf("MB")));
        }else if(origin.toUpperCase().endsWith("M")){
            size = Long.parseLong(origin.substring(0,origin.toUpperCase().lastIndexOf("M")));
        }
        else if(origin.toUpperCase().endsWith("GB")){
            size = Long.parseLong(origin.substring(0,origin.toUpperCase().lastIndexOf("GB")));
        }else if(origin.toUpperCase().endsWith("G")){
            size = Long.parseLong(origin.substring(0,origin.toUpperCase().lastIndexOf("G")));
        }else {
            size = Long.parseLong(origin);
        }
        return fileSizeConvert(size);
    }

    /**
     *
     * @param size
     * @return
     */
    public String fileSizeConvert(long size){
        if(size<=0)return "0";
        int digitGroup = (int)(Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024,digitGroup))+""+units[digitGroup];
    }
}
