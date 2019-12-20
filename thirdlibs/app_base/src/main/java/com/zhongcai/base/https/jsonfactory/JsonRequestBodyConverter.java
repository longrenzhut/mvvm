package com.zhongcai.base.https.jsonfactory;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

final class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    JsonRequestBodyConverter() {

    }

    public RequestBody convert(T value) throws IOException {
//        String body =  Encryption.encrypt(
//                value.toString());
        return RequestBody.create(MEDIA_TYPE,  value.toString());
    }
}
