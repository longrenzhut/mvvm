package com.zhongcai.base.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.zhongcai.base.base.application.BaseApplication;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * 缓存
 */

public class Cache {


    private static Cache instance;

    public static Cache getVal() {
        if (instance == null) {
            synchronized (Cache.class) {
                if (instance == null) {
                    instance = new Cache("unclear");
                }
            }
        }
        return instance;
    }

    private static Cache caninstance;

    public static Cache getVar() {
        if (caninstance == null) {
            synchronized (Cache.class) {
                if (caninstance == null) {
                    caninstance = new Cache("canclear");
                }
            }
        }
        return caninstance;
    }

    public static final String COMMON = "common";

    private SharedPreferences cache;

    private Map<String,String> cacheMap;

    public Cache(String name){
        cache = BaseApplication.app.getSharedPreferences(name, Context.MODE_PRIVATE);
        initMap();
    }


    private void initMap(){
        if(null == cacheMap)
            cacheMap = new HashMap<>();
    }

    /**
     * 设置值
     */
    public void put(String key, String value){
        String newValue = cacheMap.get(key);
        if(null != newValue && newValue.equals(value)){
            return;
        }
        cacheMap.put(key,value);
        cache.edit().putString(key,value).commit();
    }

    public String getString(String key){
        return  getString(key,"");
    }

    public String getString(String key, String defalut){
        String newValue = cacheMap.get(key);
        if(null != newValue)
            return  newValue;
        if(!cache.contains(key)){
            return defalut;
        }

        String value = cache.getString(key,"");
        cacheMap.put(key,value);
        return value;
    }


    public void putInt(String key, int values){
        put(key, String.valueOf(values));
    }

    public int getInt(String key, int defalut){
        return Integer.parseInt(getString(key, String.valueOf(defalut)));
    }

    public void clear(){
        cacheMap.clear();
        cache.edit().clear().commit();
    }


    public void remove(String key){
        cacheMap.remove(key);
        cache.edit().remove(key).apply();
    }

    public  <T> void putEntity(String key, T entity) {
        put(key, new Gson().toJson(entity));
    }

    public  <T> T getEntity(String key, Class<T> tClass){
        String value = getString(key,"");
        if(value == "")
            return null;
        return new Gson().fromJson(value, tClass);
    }


}
