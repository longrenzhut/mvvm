package com.zhongcai.base;

import android.os.Build;
import android.os.Environment;

import com.zhongcai.base.base.application.BaseApplication;

import java.io.File;

/**
 * Created by zc3 on 2018/6/26.
 */

public class Config {


    //是否是开发
    public static boolean DEVELOP = false;
    //是否是测试
    public static boolean TEST = true;
    //是否是预发布环境
    public static boolean PRE = false;

    /**
     * 开发环境
     */
    public final static String JAVA_URL_DEV = "http://dev.oa.zcabc.com/api/";//java
//            "http://172.16.10.135:8099/";
    public final static String PHP_URL_DEV = "http://dev.oa.zcabc.com/api/";//php
    public final static String C_URL_DEV = "http://172.16.10.107:8410/";
//    "http://172.16.10.160:8410/";//c++
//    public final static String C_URL_DEV = "http://dev.oa.zcabc.com/api/v1/plus/request/";//"http://172.16.10.160:8410/";//c++
    public final static String DEV_UPLOAD = //"http://dev.oa.zcabc.com/api/";//文件
    "http://172.16.10.136:8099/";
//    public final static String DEV_UPLOAD = "http://172.16.10.135:8099/";//文件
//    public final static String IM_DEV = "http://172.16.10.160:8080/msg_server";
        public final static String IM_DEV = "http://172.16.10.107:8080/msg_server";
//

    /**
    * 测试环境
    **/
    public final static String JAVA_URL_TEST = "http://test.oa.zcabc.com/api/";//java
    public final static String PHP_URL_TEST = "http://test.oa.zcabc.com/api/";//php
    public final static String C_URL_TEST = "http://test.oa.zcabc.com/api/v1/plus/request/";//c
    public final static String TEST_UPLOAD = "http://test.oa.zcabc.com/api/";//文件
    public final static String IM_TEST = "http://172.16.10.172:8080/msg_server";//IM

    /**
    * 预发布环境
    **/
    public final static String JAVA_URL_PRE = "https://oapre.zhongcaicloud.com/api/";//java
    public final static String PHP_URL_PRE  = "https://oapre.zhongcaicloud.com/api/";//php
    public final static String C_URL_PRE  = "https://oapre.zhongcaicloud.com/api/v1/plus/request/";//c
    public final static String UPLOAD_PRE  = "https://oapre.zhongcaicloud.com/api/";//文件
//    public final static String IM_PRE  = "http://192.168.50.202:8080/msg_server";//IM
    public final static String IM_PRE  = "https://oapre.zhongcaicloud.com/msg_server";//IM

    /**
    * 正式环境
    **/
    public final static String JAVA_URL = "https://oa.zhongcaicloud.com/api/";//java
    public final static String PHP_URL  = "https://oa.zhongcaicloud.com/api/";//php
    public final static String C_URL  = "https://oa.zhongcaicloud.com/api/v1/plus/request/";//c
    public final static String UPLOAD  = "https://oa.zhongcaicloud.com/api/";//文件
    //    public final static String IM_PRE  = "http://192.168.50.202:8080/msg_server";//IM
    public final static String IM  = "https://oa.zhongcaicloud.com/msg_server";//IM

    /**
     * 开发环境
     */
    public final static String BUY_JAVA_URL_DEV = "http://dev.buy.zcabc.com/api/";//java

    /**
    * 测试环境
    **/
    public final static String BUY_JAVA_URL_TEST = "http://test.buy.zcabc.com/api/";//java

    /**
    * 预发布环境
    **/
    public final static String BUY_JAVA_URL_PRE = "https://buypre.zhongcaicloud.com/api/";//java

    /**
    * 正式环境
    **/
    public final static String BUY_JAVA_URL = "https://buy.zhongcaicloud.com/api/";//java



    public  static boolean isLogin = false;
    public  static boolean isPause = true;
    public  static String PUSH_TOKEN = "";



    //页码
    public final static int PAGE_SIZE = 10,NO_SIZE = 1000;
    public final static int BLOCK_SIZE = 5242880;//32768 * 16; //0.5MB


    public static final String FOLDER_NAME = "zcoa";//文件名
    public static final String PATH_VIDEO = "/cache/video";//语音
    public static final String PATH_PIC= "/cache/pic";//图片
    public static final String PATH_CACHE= "/cache";//图片
    public static final String PATH_DOWNLOAD = "/download";//下载文件

    public static final String DOWN_PATH = "";
    public static final String BASE_PATH = getBasePath() + File.separator + FOLDER_NAME;

    public static final String path = getBasePath() + File.separator + FOLDER_NAME + PATH_DOWNLOAD + "/";
    public static final String pathNoSep = getBasePath() + File.separator + FOLDER_NAME + PATH_DOWNLOAD ;
    public static final String path_cache = getBasePath() + File.separator + FOLDER_NAME + PATH_CACHE + "/";
    public static final String path_video = getBasePath() + File.separator + FOLDER_NAME + PATH_VIDEO + "/";
    public static final String path_pic = getBasePath() + File.separator + FOLDER_NAME + PATH_PIC ;



    public static String getBasePath(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            return BaseApplication.app.getExternalFilesDir(null).getAbsolutePath();
        }
        return Environment.getExternalStorageDirectory().getPath();
    }
}
