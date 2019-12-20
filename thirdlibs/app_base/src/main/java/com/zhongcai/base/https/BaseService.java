package com.zhongcai.base.https;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2018/3/7.
 */

public interface BaseService {

    @POST("{url}")
    Observable<ResponseBody> post(@Path(value = "url", encoded = true) String path,
                                  @Body RequestBody json);



}
