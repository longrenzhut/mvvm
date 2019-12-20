package com.zhongcai.base.https.jsonfactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    JsonResponseBodyConverter() {

    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JSONObject jsonObj;
        try {
            String v = value.string();
            jsonObj = new JSONObject(v);
            value.close();
            return (T) jsonObj;
        } catch(JSONException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
