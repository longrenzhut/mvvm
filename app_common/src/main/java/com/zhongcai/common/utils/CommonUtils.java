package com.zhongcai.common.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;

import com.google.gson.Gson;
import com.zhongcai.base.base.application.BaseApplication;
import com.zhongcai.base.theme.statusbar.StatusBarKitkatImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 通用方法
 */

public class CommonUtils {


    public static <T> T getListLast(List<T> list){
        if(null != list && list.size() > 0)
        return list.get(list.size() - 1);
        return null;
    }

    public static String[] getArrayByString(String content){
        return content.split(",");
    }

    public static List<String> getListByString(String content){
        List<String> result = new ArrayList<>();
        String[] data = content.split(",");
        for (String str : data) {
            result.add(str);
        }
        return result;
    }

    public static String getIdsString(StringBuilder stringBuilder){
        String ids = stringBuilder.toString().trim();
        if(ids.length()>0){
            return ids.substring(0,ids.length()-1);
        }
        return ids;
    }

    public static String getIdsString(String content){
        if(content.length()>0){
            return content.substring(0,content.length()-1);
        }
        return content;
    }

    public static String getIdsString(List<String> contents){
        StringBuilder stringBuilder = new StringBuilder();
        if(!isEmptyList(contents)){
            for (String content: contents) {
                stringBuilder.append(content).append(",");
            }
            return getIdsString(stringBuilder);
        }
        return "";
    }

    public static String getIdsString(List<String> contents, String remark){
        StringBuilder stringBuilder = new StringBuilder();
        if(!isEmptyList(contents)){
            for (String content: contents) {
                stringBuilder.append(content).append(remark);
            }
            return getIdsString(stringBuilder);
        }
        return "";
    }

    public static String getContentByNull(String content){
        if(content == null || "".equals(content)){
            return "";
        }
        return content;
    }


    public static JSONObject getJSONObject(Object obj){
        String value = new Gson().toJson(obj);
        try {
            return new JSONObject(value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray getJSONArray(List<Object> array){
        String value = new Gson().toJson(array);
        try {
            return new JSONArray(value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String substringBeforeLast(String content, String index){
        return content.substring(0,content.lastIndexOf(index));
    }

    public static String substringAfterLast(String content, String index){
        String[] resultData = content.split(index);
        return resultData[resultData.length -1];
    }

    public static boolean isEmptyList(List list) {
        return list == null || list.size() == 0;
    }


    //
    public static String stampToDateStr(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static Date stampToDate(String s){
        long lt = new Long(s);
        Date date = new Date(lt);
        return date;
    }


    public static Date stampToDate(long s){
        Date date = new Date(s);
        return date;
    }

    /*
   * 将时间转换为时间戳
   */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
    public static long dateToStampl(String s, String format) throws ParseException {
        if(format.isEmpty())
            format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        return ts;
    }



    public static boolean isNumber(String str) {
        //采用正则表达式的方式来判断一个字符串是否为数字，这种方式判断面比较全
        //可以判断正负、整数小数

        boolean isInt = Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
        boolean isDouble = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$").matcher(str).find();

        return isInt || isDouble;

    }


    /* 验证邮箱*/
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /*验证电话号码
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        if(null != mobileNumber) {
            try {
                Pattern regex = Pattern.compile("^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$");
                Matcher matcher = regex.matcher(mobileNumber);
                flag = matcher.matches();
            } catch (Exception e) {
                flag = false;
            }
        }
        return flag;
    }

    public static String listToString(List<String> list) {
        if(null == list || list.size() == 0)
            return "";
        return list.toString().replace("[","")
                .replace("]","")
                .replace(" ","");
    }


    /**
     * 生成随机数
     * @param length
     * @return
     */
    public static String randomNumber(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
