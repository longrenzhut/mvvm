package com.zhongcai.base.https.zip;

import io.reactivex.functions.Function4;
import okhttp3.ResponseBody;

/**
 * Created by zc3 on 2018/5/15.
 */

public class BaseFunction4 implements Function4<ResponseBody, ResponseBody,ResponseBody,ResponseBody,String[]> {
    @Override
    public String[] apply(ResponseBody responseBody, ResponseBody responseBody2, ResponseBody responseBody3
    ,ResponseBody responseBody4) throws Exception {

        String data1 = ValueUtil.ResponseBody2Str(responseBody);
        String data2 = ValueUtil.ResponseBody2Str(responseBody2);
        String data3 = ValueUtil.ResponseBody2Str(responseBody3);
        String data4 = ValueUtil.ResponseBody2Str(responseBody4);

        responseBody.close();
        responseBody2.close();
        responseBody3.close();
        responseBody4.close();

        return new String[]{data1,data2,data3,data4};
    }

}
