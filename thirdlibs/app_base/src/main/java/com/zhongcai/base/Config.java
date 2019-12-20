package com.zhongcai.base;

import android.os.Environment;

import java.io.File;

/**
 * Created by zc3 on 2018/6/26.
 */

public class Config {


    //是否是开发
    public static boolean DEVELOP = true;
    //是否是测试
    public static boolean TEST = false;
    //是否是预发布环境
    public static boolean PRE = false;

    /**
     * 开发环境
     */
    public final static String JAVA_URL_DEV = "";//java
    public final static String PHP_URL_DEV = "http://dev.waterhome.zcabc.com/api/";//php
    public final static String DEV_UPLOAD = "http://dev.waterhome.zcabc.com/api/";//文件

    /**
    * 测试环境
    **/
    public final static String JAVA_URL_TEST = "";//java
    public final static String PHP_URL_TEST = "http://test.waterhome.zcabc.com/api/";//php
    public final static String TEST_UPLOAD = "http://test.waterhome.zcabc.com/api/";//文件

    /**
    * 预发布环境
    **/
    public final static String JAVA_URL_PRE = "";//java
    public final static String PHP_URL_PRE  = "https://water-pre.zhongcaicloud.com/api/";//php
    public final static String UPLOAD_PRE  = "https://water-pre.zhongcaicloud.com/api/";//文件

    /**
    * 正式环境
    **/
    public final static String JAVA_URL = "";//java
    public final static String PHP_URL = "https://water.zhongcaicloud.com/api/";//php
    public final static String UPLOAD = "https://water.zhongcaicloud.com/api/";//文件



    public  static boolean isLogin = false;



    //页码
    public final static int PAGE_SIZE = 20,NO_SIZE = 1000;


    public static final String PATH_BASE = "baseUrl";
    public static final String FOLDER_NAME = "hydraulics";//文件名
    public static final String PATH_VIDEO = "/cache/video";//语音
    public static final String PATH_PIC= "/cache/pic";//图片
    public static final String PATH_DOWNLOAD = "/download";//下载文件

    public static final String DOWN_PATH = "";

    public static final String path = Environment.getExternalStorageDirectory() + File.separator + FOLDER_NAME + PATH_DOWNLOAD + "/";

}
