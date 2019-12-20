package com.zhongcai.common.utils;

import com.zhongcai.base.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jwen on 2017/8/7.
 */

public class TimeUtils {

    public static final long TIME_MINUTE = 60*1000;
    public static final long TIME_HOUR = 60*60*1000;
    public static final long TIME_DAY = 24*60*60*1000;

    public static final String DEFAULT_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE = "yyyy-MM-dd";
    public static final String DEFAULT_CHINA = "yyyy年MM月dd日";

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param millis 毫秒时间戳
     * @return 时间字符串
     */
    public static String millis2String(final long millis) {
        return millis2String(millis, DEFAULT_TIME);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为format</p>
     *
     * @param millis 毫秒时间戳
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String millis2String(final long millis, final String pattern) {
        Date date = new Date(millis);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }


    /**
     * 获取当前时间
     * @return
     */
    public static String getCurrentTime(){
        long timestamp = System.currentTimeMillis();
        String time = new SimpleDateFormat("HH:mm").
                format(new Date(timestamp));
        return time;
    }

    /**
     * 将字符串转为时间戳
     * @param dateString
     * @param pattern
     * @return
     */
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     * @param timeStr   时间戳
     * @return
     */
    public static String getStandardDate(String timeStr) {
        StringBuffer sb = new StringBuffer();

        long t = Long.parseLong(timeStr);
        long time = System.currentTimeMillis() - t;
        long mill = (long) Math.ceil(time /1000);//秒前

        long minute = (long) Math.ceil(time/60/1000.0f);// 分钟前

        long hour = (long) Math.ceil(time/60/60/1000.0f);// 小时

        long day = (long) Math.ceil(time/24/60/60/1000.0f);// 天前

        if (day - 1 > 0) {
            sb.append( millis2String(t,DEFAULT_DATE));
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("昨天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!(sb.toString().equals("刚刚") || hour >= 24 || day - 1 > 0)) {
            sb.append("前");
        }
        return sb.toString();
    }

    /**
     * 将字符串转换为代表"距现在多久之前"的字符串
     * @param date
     * @return
     */
    public static String getStandardDateByString(String date){
        String resultDate = "";
        if(!StringUtils.isEmpty(date)){
            long standardDate = TimeUtils.getStringToDate(date,"yyyy-MM-dd HH:mm:ss");
            resultDate = TimeUtils.getStandardDate(String.valueOf(standardDate));
        }
        return resultDate;
    }


}
