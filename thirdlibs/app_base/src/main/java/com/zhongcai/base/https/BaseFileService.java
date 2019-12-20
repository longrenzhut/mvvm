package com.zhongcai.base.https;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by zc3 on 2018/8/16.
 */

public interface BaseFileService {

/*    val params = UpFileParam()
        params.putParam("version",3)
                params.putParam("uid", BaseApp.getUserModel().uid)
            params.putParam("module","forumupload")

            params.putFile("Filedata",path)
    UpImg(params,object : ReqCallBack<ImgModel>(){
    */
    /**
     * @param path 路径 如 index.php
     * @param map 带参数 requestbody 可以 参数 也可以是文件流
     * @return
     * 上传文件 如mp3 图片 文件等
     * 没测试过批量上传
     */
    @Multipart
    @POST("{url}")
    Observable<ResponseBody> upFile(@Path(value = "url", encoded = true) String path,
                                    @PartMap Map<String, RequestBody> map);


    /**
     * @param map
     * @param part
     * @return
     * 单个上传
     * 前面参数  后面文件
     */
    @Multipart
    @POST("{url}")
    Observable<ResponseBody> upFile(@Path(value = "url", encoded = true) String path,
                                    @PartMap Map<String, RequestBody> map,
                                    @Part MultipartBody.Part part);

    /**
     * 不带参数 单个上传 文件的 转换成 MultipartBody
     * @param path
     * @param multipartBody
     * @return
     */
    @POST("{url}")
    Observable<ResponseBody> upFile(@Path(value = "url", encoded = true) String path,
                                    @Body MultipartBody multipartBody);


    //批量上传文件
    @Multipart
    @POST("{url}")
    Observable<ResponseBody> upFile(@Path(value = "url", encoded = true) String path,
                                    @PartMap Map<String, RequestBody> map,
                                    @Part List<MultipartBody.Part> parts);


    //------ 现在文件

    @GET("{fileName}")
    Observable<ResponseBody> loadFile(@Path(value = "fileName", encoded = true) String fileName,
                                      @QueryMap Map<String, Object> param);

    @GET()
    Observable<ResponseBody> loadFile();
}
