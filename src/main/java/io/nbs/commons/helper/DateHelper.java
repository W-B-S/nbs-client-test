/*
 *   Copyright © 2018, lanbery,Lambor Co,. Ltd. All Rights Reserved.
 *
 *   Copyright Notice
 *   lanbery copyrights this specification. No part of this specification may be reproduced in any form or means, without the prior written consent of lanbery.
 *
 *   Disclaimer
 *   This specification is preliminary and is subject to change at any time without notice. Lambor assumes no responsibility for any errors contained herein.
 *
 */

package io.nbs.commons.helper;

import org.apache.commons.lang3.time.DateFormatUtils;

public class DateHelper {
    public static final String FORMAT_STANDARD_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_STANDARD_DATE = "yyyy-MM-dd";

    public static final long YEAR_MILLIONS = 365*24*60*60*1000;
    public static final long DAY_MILLIONS = 24*60*60*1000;
    public static final long HOUR_MILLIONS = 60*60*1000;
    public static final long MIN_MILLIONS = 60*60*1000;

    /**
     * @Date    : 2018/6/21 16:29
     * @Author  : lanbery
     * @Decription :
     * <p>获取当前时间</p>
     * @Param   :
     * @return
     * @throws
     */
    public static String currentTime(){
        return DateFormatUtils.format(System.currentTimeMillis(),FORMAT_STANDARD_TIME);
    }
    public static String currentDate(){
        return DateFormatUtils.format(System.currentTimeMillis(),FORMAT_STANDARD_DATE);
    }


    /**
     * int
     * @return
     */
    public static int currentSecond(){
        return (int)(System.currentTimeMillis()/1000);
    }

    /**
     *
     * @return
     */
    public static long timestamp(){return System.currentTimeMillis();}

    /**
     *
     * @param time
     * @return
     */
    public static String calcUsedTime(long time){
        if(time<=999)return time+"毫秒";
        StringBuffer sb = new StringBuffer();
        calc(sb,time);
        return sb.toString();
    }

    /**
     *
     * @param sb
     * @param time
     */
    private static void calc(StringBuffer sb,long time){
        long less = 0;
        if(time>=YEAR_MILLIONS){
            int y =(int) (time/YEAR_MILLIONS);
            sb.append(y).append("年");
            less = time%YEAR_MILLIONS;
            if(less!=0l)calc(sb,less);
        }else if(time>=DAY_MILLIONS){
            int d = (int)(time/DAY_MILLIONS);
            sb.append(d).append("天");
            less = time%DAY_MILLIONS;
            if(less!=0l)calc(sb,less);
        }else if(time>=HOUR_MILLIONS){
            int h = (int)(time/HOUR_MILLIONS);
            sb.append(h).append("小时");
            less = time%HOUR_MILLIONS;
            if(less!=0l)calc(sb,less);
        }else if(time>=MIN_MILLIONS){
            int m = (int)(time/MIN_MILLIONS);
            sb.append(m).append("分");
            less = time%MIN_MILLIONS;
            if(less!=0l)calc(sb,less);
        }else {
            int s = (int)(time/1000);
            if(s>0){
                sb.append(s).append("秒");
            }
        }
    }
}
