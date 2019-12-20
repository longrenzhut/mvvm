package com.zhongcai.base.utils;

import android.widget.TextView;


public class StringUtils {

    public static boolean isEmpty(String content){
        if(content == null || content.equals("") || content.equals("null")){
            return true;
        }
        return false;
    }

    public static boolean isEmpty(TextView text){
        if(null == text)
            return true;
        String content = text.getText().toString();
        if(content == null || content.equals("") || content.equals("null")){
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Object content){
        if(content == null){
            return true;
        }
        return false;
    }

    public static String getValue(String content){
        if(content == null || content.equals("") || content.equals("null")){
            return "";
        }
        return content;
    }

    /**
     * 是否有值
     * @param content
     * @return
     */
    public static boolean isValue(String content) {
        if (content == null || content.equals("") || content.equals("null")) {
            return false;
        }
        return true;
    }
    /**
     * 是否有值
     * @param content
     * @return
     */
    public static boolean isValue(Object content){
        if(content == null){
            return false;
        }
        return true;
    }



}
