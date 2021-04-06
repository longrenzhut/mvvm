package com.zhongcai.base.https.zip;

import io.reactivex.functions.Function4;
import io.reactivex.functions.Function5;
import okhttp3.ResponseBody;

/**
 * Created by zc3 on 2018/5/15.
 */

public class BaseFunction5 implements Function5<ResponseBody, ResponseBody,ResponseBody,ResponseBody,ResponseBody,String[]> {
    @Override
    public String[] apply(ResponseBody responseBody, ResponseBody responseBody2, ResponseBody responseBody3
            ,ResponseBody responseBody4,ResponseBody responseBody5) throws Exception {

        String data1 = ValueUtil.ResponseBody2Str(responseBody);
        String data2 = ValueUtil.ResponseBody2Str(responseBody2);
        String data3 = ValueUtil.ResponseBody2Str(responseBody3);
        String data4 = ValueUtil.ResponseBody2Str(responseBody4);
        String data5 = ValueUtil.ResponseBody2Str(responseBody5);

        responseBody.close();
        responseBody2.close();
        responseBody3.close();
        responseBody4.close();
        responseBody5.close();

        return new String[]{data1,data2,data3,data4,data5};
    }

}
