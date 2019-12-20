package com.zhongcai.base.https.zip;

import io.reactivex.functions.Function3;
import okhttp3.ResponseBody;

/**
 * Created by zc3 on 2018/4/3.
 */

public class BaseFunction3 implements Function3<ResponseBody, ResponseBody,ResponseBody,String[]> {
    @Override
    public String[] apply(ResponseBody responseBody, ResponseBody responseBody2, ResponseBody responseBody3) throws Exception {

        String data1 = ValueUtil.ResponseBody2Str(responseBody);
        String data2 = ValueUtil.ResponseBody2Str(responseBody2);
        String data3 = ValueUtil.ResponseBody2Str(responseBody3);

        responseBody.close();
        responseBody2.close();
        responseBody3.close();

        return new String[]{data1,data2,data3};
    }

}
