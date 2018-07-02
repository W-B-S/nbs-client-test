package io.nbs.commons.helper;

/**
 * @Package : com.nbs.utils
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/6/24-9:27
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class RadomCharactersHelper {
    public static final String[] sources = new String[]{
            "8","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
            "a","b","c","d","e","f","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "0","1","2","3","4","5","6","7","9","8"
    };

    public static final String[] onlyzimu = new String[]{
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
            "a","b","c","d","e","f","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
    };

    private final int DEFAULT_LENGTH = 6;

    private RadomCharactersHelper(){

    }

    private static class Holder{
        static final RadomCharactersHelper instance = new RadomCharactersHelper();
    }

    public static RadomCharactersHelper getInstance(){
        return Holder.instance;
    }

    /**
     *
     * @param prefix
     * @return
     */
    public String generated(String prefix){
        return generated(prefix,DEFAULT_LENGTH);
    }

    /**
     *
     * @param len
     * @return
     */
    public String generated(int len){
        return generated(null,len);
    }

    /**
     *
     * @param prefix
     * @param len
     * @return
     */
    public String generated(String prefix,int len){
        if(len<1)return "";
        StringBuffer result = new StringBuffer();
        if(prefix==null)prefix="";
        result.append(prefix);
        for(int i =0 ;i <len;i++){
            int c = getRadom(sources.length);
            if(c<1||c>=sources.length)c = 1;
            result.append(sources[c-1]);
        }
        return result.toString();
    }

    /**
     * 只含字母
     * @param suffix
     * @param len
     * @return
     */
    public String generatedOnlyCharater(String suffix,int len){
        if(len<1)return "";
        StringBuffer result = new StringBuffer();
        if(suffix==null)suffix="";

        for(int i =0 ;i <len;i++){
            int c = getRadom(onlyzimu.length);
            if(c<1||c>=onlyzimu.length)c = 1;
            result.append(onlyzimu[c-1]);
        }
        result.append(suffix);
        return result.toString();
    }
    private int getRadom(int len){
        return (int)Math.round(Math.random()*len);
    }

}
