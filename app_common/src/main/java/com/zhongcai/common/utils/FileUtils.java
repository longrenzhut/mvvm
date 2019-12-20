package com.zhongcai.common.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.zhongcai.base.Config;
import com.zhongcai.base.utils.StringUtils;
import com.zhongcai.common.helper.cache.CacheHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;



/**
 * Created by Jwen on 2018/4/16.
 */

public class FileUtils {

    public static final long MIN_STORAGE=52428800;//50*1024*1024最低50m



    /**
     * 获取保存文件路径
     * @param saveSize  预留空间
     * @return 文件路径
     */
    public static String getSavePath(long saveSize) {
        String savePath = null;
        if (StorageUtil.getExternaltStorageAvailableSpace() > saveSize) {//扩展存储设备>预留空间
            savePath = StorageUtil.getExternalStorageDirectory();
            File saveFile = new File(savePath);
            if (!saveFile.exists()) {
                saveFile.mkdirs();
            } else if (!saveFile.isDirectory()) {
                saveFile.delete();
                saveFile.mkdirs();
            }
        } else if (StorageUtil.getSdcard2StorageAvailableSpace() > saveSize) {//sdcard2外部存储空间>预留空间
            savePath = StorageUtil.getSdcard2StorageDirectory();
            File saveFile = new File(savePath);
            if (!saveFile.exists()) {
                saveFile.mkdirs();
            } else if (!saveFile.isDirectory()) {
                saveFile.delete();
                saveFile.mkdirs();
            }
        } else if (StorageUtil.getEmmcStorageAvailableSpace() > saveSize) {//可用的 EMMC 内部存储空间>预留空间
            savePath = StorageUtil.getEmmcStorageDirectory();
            File saveFile = new File(savePath);
            if (!saveFile.exists()) {
                saveFile.mkdirs();
            } else if (!saveFile.isDirectory()) {
                saveFile.delete();
                saveFile.mkdirs();
            }
        } else if (StorageUtil.getOtherExternaltStorageAvailableSpace()>saveSize) {//其他外部存储可用空间>预留空间
            savePath = StorageUtil.getOtherExternalStorageDirectory();
            File saveFile = new File(savePath);
            if (!saveFile.exists()) {
                saveFile.mkdirs();
            } else if (!saveFile.isDirectory()) {
                saveFile.delete();
                saveFile.mkdirs();
            }
        }else if (StorageUtil.getInternalStorageAvailableSpace() > saveSize) {//内部存储目录>预留空间
            savePath = StorageUtil.getInternalStorageDirectory() + File.separator;
        }
        return savePath;
    }


    /**
     * 创建文件夹
     * @param path
     */
    public static void makeDir(String path){
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        file = null;
    }

    /**
     * 创建readme
     * @param uri
     */
    public static void createReadme(String uri){
        File textFile = new File(uri + "/readme.txt");
        if(!textFile.exists()){
            try {
                textFile.createNewFile();
                print(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 打印说明信息
     * @param path
     */
    private static void print(String path) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(path + "/readme.txt", true);
            bw = new BufferedWriter(fw); // 将缓冲对文件的输出
            bw.write("this is zcfinance file" + "\n"); // 写入文件
            bw.write("welcome read and not delete" + "\n"); // 写入文件
            bw.newLine();
            bw.flush(); // 刷新该流的缓冲
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                bw.close();
                fw.close();
            } catch (IOException e1) {
            }
        }
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }



    /**
     * 将字节转换为数字
     * @param sizeStr
     * @return
     */
    public static String getPrintSize(String sizeStr) {
        long size = Long.valueOf(sizeStr);

        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }


    public static void createFileUri(Context context){
        String path =  CacheHelper.getVal().getString(Config.PATH_BASE,"");
        if(StringUtils.isEmpty(path) || !path.contains(Config.FOLDER_NAME)){
            String baseUri= FileUtils.getCachePath();
            CacheHelper.getVal().put(Config.PATH_BASE,baseUri);
        }
    }

    //缓存路径
    public static String getCachePath(){
        String baseUri = getSavePath(MIN_STORAGE);
        if(StringUtils.isEmpty(baseUri)){
            return null;
        }
        baseUri= baseUri + Config.FOLDER_NAME;

        makeDir(baseUri);
        makeDir(baseUri + Config.PATH_VIDEO);
        makeDir(baseUri + Config.PATH_PIC);
        makeDir(baseUri + Config.PATH_DOWNLOAD);
        createReadme(baseUri);
        return baseUri;
    }

}
