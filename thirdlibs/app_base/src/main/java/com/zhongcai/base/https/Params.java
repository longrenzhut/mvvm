package com.zhongcai.base.https;


import com.zhongcai.base.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by Administrator on 2018/3/7.
 */

public class Params {

    private boolean mHasKey = true;//是否有key
    private String mEncrypt = "AES";//加密方式
    private JSONObject json;
    private JSONArray jsonArr;

    public Params(){
        json = new JSONObject();
    }

    public Params(JSONObject json){
        this.json = json;
    }

    public Params(JSONArray jsonArr){
        this.jsonArr = jsonArr;
    }




    public Params put(String key, String value){
        try {
            json.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Params put(String key, RequestBody value){
        try {
            json.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }


    public Params put(String key, double value){
        try {
            json.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Params put(String key, JSONObject value){
        try {
            json.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Params put(String key, Object value){
        try {
            json.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Params put(String key, JSONArray value){
        try {
            json.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }


    public Params put(String key, int value){
        try {
            json.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void setEncrypt(String encrypt){
        mEncrypt = encrypt;
    }

    public String getJson() {
        if(jsonArr != null)
            return jsonArr.toString();
        return json.toString();
    }



    public JSONObject getJSONObject() {
        return json;
    }



    public RequestBody getBody(){
        String content;
        if(mHasKey){
            content = getJson();
        }else {
            content = getJson();
        }

        MediaType mediaType;
        mediaType = MediaType.parse("application/json;charset=utf-8");
//        if (Config.DEVELOP || Config.TEST || Config.PRE){
//            mediaType = MediaType.parse("application/json;charset=utf-8");
//        } else {//加密
//            mediaType = MediaType.parse("text/plain;charset=utf-8");
//            try {
//                if(mEncrypt == "RSA"){
//                    content = RSAEncrypt.encrypt(content,RSAEncrypt.getPublicKey(RSAEncrypt.KEY_PUBLIC));
//                }else {
//                    content = AESHexEncrypt.encrypt(content,AESHexEncrypt.KEY);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        return RequestBody.create(mediaType, content);
    }

}
