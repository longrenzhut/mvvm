package com.zhongcai.base.https.zip;

import io.reactivex.functions.BiFunction;
import okhttp3.ResponseBody;

/**
 * Created by zc3 on 2018/4/3.
 */

public class BaseBiFunction implements BiFunction<ResponseBody, ResponseBody,String[]> {



    @Override
    public String[] apply(ResponseBody responseBody, ResponseBody responseBody2) throws Exception {

        String data1 = ValueUtil.ResponseBody2Str(responseBody);
        String data2 = ValueUtil.ResponseBody2Str(responseBody2);

        responseBody.close();
        responseBody2.close();

        return new String[]{data1,data2};
    }
}
