package io.nbs.commons.utils;

import io.nbs.commons.helper.DateHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Package : io.nbs.commons.utils
 * @Description :
 * <p>
 *     时间处理类
 * </p>
 * @Author : lambor.c
 * @Date : 2018/7/3-20:58
 * Copyright (c) 2018, NBS , lambor.c<lanbery@gmail.com>.
 * All rights reserved.
 */
public class TimeUtil
{
    private static final SimpleDateFormat daySimpleDateFormat = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat monthSimpleDateFormat = new SimpleDateFormat("MM/dd");
    private static final SimpleDateFormat monthToDateFormat = new SimpleDateFormat("MM-dd");
    private static final SimpleDateFormat yearSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    private static final Calendar calendar = Calendar.getInstance();

    public static boolean inTheSameMinute(long ts1, long ts2)
    {
        calendar.setTimeInMillis(ts1);
        int min1 = calendar.get(Calendar.MINUTE);

        calendar.setTimeInMillis(ts2);
        int min2 = calendar.get(Calendar.MINUTE);

        return min1 == min2;
    }

    /**
     * 同一天
     * @param d1
     * @param d2
     * @return
     */
    public static boolean inTheSameDate(Date d1,Date d2){
        if(d1==null)d1 = new Date(System.currentTimeMillis());
        if(d2==null)d2 = new Date(System.currentTimeMillis());
        return monthSimpleDateFormat.format(d1).equals(monthSimpleDateFormat.format(d2));
    }

    public static boolean inTheSameDate(String ds1,String ds2){
        if(ds1==null)ds1 = DateHelper.currentDate();
        if(ds2==null)ds2 = DateHelper.currentDate();
        return ds1.equalsIgnoreCase(ds2);
    }

    /**
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean inTheSameDate(Long d1,Long d2){
        if(d1==null)d1 = System.currentTimeMillis();
        if(d2==null)d2 =System.currentTimeMillis();
        return monthSimpleDateFormat.format(new Date(d1)).equals(monthSimpleDateFormat.format(new Date(d2)));
    }

    public static String formatMonthDay(Date date){
        if(date==null)date = new Date(System.currentTimeMillis());
        return monthToDateFormat.format(date);
    }

    public static String formatMonthDay(Long date){
        if(date==null)return "";
        return monthToDateFormat.format(date);
    }

    public static String diff(long timestamp)
    {
        return diff(timestamp, false);
    }

    public static String diff(long timestamp, boolean detail)
    {
        long current = System.currentTimeMillis();
        calendar.setTimeInMillis(current);
        int today = calendar.get(Calendar.DAY_OF_YEAR);
        int thisYear = calendar.get(Calendar.YEAR);

        calendar.setTimeInMillis(timestamp);
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);

        int diff = today - day;
        boolean sameYear = thisYear == year;

        String ret;
        if (sameYear && diff < 1)
        {
            ret = daySimpleDateFormat.format(new Date(timestamp));
        }
        else if (sameYear && diff < 2)
        {
            if (detail)
            {
                ret = "昨天 " + daySimpleDateFormat.format(new Date(timestamp));
            }
            else
            {
                ret = "昨天"/* + daySimpleDateFormat.format(new Date(timestamp))*/;
            }
        }
        else if (sameYear && diff < 8)
        {
            if (detail)
            {
                ret = "星期" + getWeekDay(calendar.get(Calendar.DAY_OF_WEEK)) + " " + daySimpleDateFormat.format(new Date(timestamp));
            }
            else
            {
                ret = "星期" + getWeekDay(calendar.get(Calendar.DAY_OF_WEEK))/* + " " + daySimpleDateFormat.format(new Date(timestamp))*/;
            }
        }
        else if (sameYear && diff < 366)
        {
            if (detail)
            {
                ret = monthSimpleDateFormat.format(new Date(timestamp)) + " " + daySimpleDateFormat.format(new Date(timestamp));
            }
            else
            {
                ret = monthSimpleDateFormat.format(new Date(timestamp));
            }
        }
        else
        {
            ret = yearSimpleDateFormat.format(new Date(timestamp));
        }
        return ret;
    }



    private static String getWeekDay(int val)
    {
        switch (val)
        {
            case 1:
                return "日";
            case 2:
                return "一";
            case 3:
                return "二";
            case 4:
                return "三";
            case 5:
                return "四";
            case 6:
                return "五";
            case 7:
                return "六";
            default:
                return "";
        }
    }
}
