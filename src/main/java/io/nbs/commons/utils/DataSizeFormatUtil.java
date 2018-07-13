package io.nbs.commons.utils;

import java.text.DecimalFormat;

/**
 * @Package : io.nbs.commons.utils
 * @Description : <p></p>
 * @Author : lambor.c
 * @Date : 2018/7/10-22:24
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class DataSizeFormatUtil {

    public static DecimalFormat formater = new DecimalFormat("#,###.00");

    /**
     *
     * @param size
     * @return
     */
    public static String formatDataSize(Long size){
        if(size==null)size=0l;
        if(size<1024){
            return size+"B";
        }else if(size<1024*1024){
            float kbSize = size/1024.00F;
            return formater.format(kbSize)+"KB";
        }else if(size<1024*1024*1024){
            float mbSize = size/(1024*1024.00F);
            return formater.format(mbSize)+"MB";
        }else if(size<1024*1024*1024*1024){
            float gbSize = size/(1024*1024*1024.00F);
            return formater.format(gbSize)+"GB";
        }else {
            float tbSize = size/(1024*1024*1024*1024.00F);
            return formater.format(tbSize)+"GB";
        }
    }

    /**
     *
     * @param volume
     * @return
     */
    public static String formatIntelnetRate(Long volume){
        if(volume==null)return "0B/s";
        String sf = formatDataSize(volume);
        return sf+"/s";
    }
}
