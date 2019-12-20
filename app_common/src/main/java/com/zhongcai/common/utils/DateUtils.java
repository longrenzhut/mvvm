package com.zhongcai.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author airsaid
 *
 * 日期工具类.
 */
public class DateUtils {

    private DateUtils(){
        // 工具类, 禁止实例化
        throw new AssertionError();
    }

    /**
     * 通过指定的年份和月份获取当月有多少天.
     *
     * @param year  年.
     * @param month 月.
     * @return 天数.
     */
    public static int getMonthDays(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)){
                    return 29;
                }else{
                    return 28;
                }
            default:
                return -1;
        }
    }

    /**
     * 获取指定年月的 1 号位于周几.
     * @param year  年.
     * @param month 月.
     * @return      周.
     */
    public static int getFirstDayWeek(int year, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 0);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getDayWeek(int year, int month,int date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date -1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static String getDayWeekDay(int year, int month, int date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date -1);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String str = "";
        switch (week){
            case 1:
                str = "一";
                break;
            case 2:
                str = "二";
                break;
            case 3:
                str = "三";
                break;
            case 4:
                str = "四";
                break;
            case 5:
                str = "五";
                break;
            case 6:
                str = "六";
                break;
            case 7:
                str = "日";
                break;
        }
        return str;
    }

    public static String getCurrentDate(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getCurYear());
        stringBuffer.append(getCurMonth());
        stringBuffer.append(getCurDay());
        return stringBuffer.toString();
    }

    public static int getCurDay(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    public static int getCurYear(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int getCurMonth(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getCurHour(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getCurMinute(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    public static String getCurDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        return year + "-" + toChange(month) + "-" + toChange(day);
    }

    public static String getPreSevenDate(){
        int preYear = 0;
        int preMonth = 0;
        int preDay = 0;

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        if(month == 1){
            if(day < 7) {
                 preYear = year - 1;
                 preMonth = 12;
                 int preMonthDays = getMonthDays(preYear,preMonth);
                 preDay = preMonthDays - (7 - day - 1);

            }
            else{
                 preYear = year;
                 preMonth = 1;
                 preDay = day + 1 - 7;
            }

        }
        else{
            if(day < 7) {
                 preYear = year;
                 preMonth = month - 1;
                int preMonthDays = getMonthDays(preYear,preMonth);
                 preDay = preMonthDays - (7 - day - 1);

            }
            else{
                 preYear = year;
                 preMonth = month;
                 preDay = day + 1 - 7;
            }
        }



        return  preYear + "-" + toChange(preMonth) + "-" + toChange(preDay);
    }


    static String toChange(int month){
        if(month >= 10)
        return month + "";
        return "0" + month;
    }


    public static String formatDateToMD(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy/MM/dd");
        String formatStr = "";
        try {
            formatStr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr;
    }

    public static String formatDateToYMD(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        String formatStr = "";
        try {
            formatStr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr;
    }

}
