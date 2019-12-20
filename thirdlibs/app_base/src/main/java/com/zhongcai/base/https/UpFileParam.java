package com.zhongcai.base.https;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by zhutao on 2018/8/16.
 * 上传文件的参数
 */

public class UpFileParam {

    private Map<String, RequestBody> map;
    int len = 0;

    public UpFileParam(){
        map = new HashMap<>();

    }

    /**
     * 将参数转换成流
     * @param value
     * @return
     */
    private RequestBody paramtoRequestBody(Object value){
        RequestBody body = RequestBody.create(
                MediaType.parse("multipart/form-data"), value.toString());
        return body;
    }

    /**
     * 设置上传要带的参数
     * @param key
     * @param value
     * @return
     */
    public UpFileParam putParam(String key, Object value){

        map.put(key,paramtoRequestBody(value));
        return this;
    }


    /**
     * 上传文件有几种情况
     * @param key
     *  1. name 就是 后台接受的name定死了
     * 2， 一种name是随机的
     * @param file
     *
     * UploadFileRequestBody 监听上传的进度
     */
    private void fileToRequestBody(String key,File file){
        if(!file.exists())
            return;
        RequestBody requestBody =  new UploadFileRequestBody(file);
        map.put(key + "\"; filename=\"" + file.getName(), requestBody);
    }

    //name是随机的
    private void fileToRequestBody(File file){
        if(!file.exists())
            return;
        len ++;
        RequestBody requestBody =  new UploadFileRequestBody(file);
        map.put("file" + len  + "\"; filename=\"" + file.getName(), requestBody);
//        map.put(System.currentTimeMillis() + Math.random()*10 + "\"; filename=\"" + file.getName(), requestBody);
    }


//    /**
//     * @param path 路径 具体路径 如 bbs
//     * @param name 温江
//     */
//    public void put(String path,String name){
//        String abPath = Environment.getExternalStorageDirectory() +  File.separator + path + File.separator + name;
//        File file = new File(abPath);
//    }

    /**
     *  在根目录下
     *  这是定死的 根据接口传对应的key
     * @param key
     * @param path 路径
     */
    public UpFileParam putFile(String key, String path){
        File file = new File(path);
        fileToRequestBody(key,file);
        return this;
    }

    /**
     *  在根目录下
     *  随机的
     * @param path
     */
    public UpFileParam putFile(String path){
        File file = new File(path);
        fileToRequestBody(file);
        return this;
    }

    /**
     * 返回的file对象
     * @param file
     * @return
     */
    public UpFileParam putFile(File file){
        fileToRequestBody(file);
        return this;
    }

    public UpFileParam putFile(String key, File file){
        fileToRequestBody(key,file);
        return this;
    }

    public Map<String, RequestBody> getMap(){
        return map;
    }


    /**
     * 单个上传 转换成 MultipartBody.Part
     * @param key
     * @param file
     * @return
     */
    MultipartBody.Part part;

    public void putFileToPart(String key,File file) {
        RequestBody requestBody = new UploadFileRequestBody(file);
        part = MultipartBody.Part.createFormData(key, file.getName(), requestBody);
    }

    public MultipartBody.Part getPartBody(){
        return part;
    }


    //------------------- 将参数
    MultipartBody.Builder builder;
    public void putFileToMultipartBody(File file) {
        builder = new MultipartBody.Builder();
        String name = String.valueOf(System.currentTimeMillis());
        RequestBody requestBody = new UploadFileRequestBody(file);
        builder.addFormDataPart(name, file.getName(), requestBody);
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
    }

    public MultipartBody.Builder getMultipartBody(){
        return builder;
    }
}
