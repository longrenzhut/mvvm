package com.zhongcai.base.https;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zc3 on 2018/5/7.
 */

public class DownParam {

    private Map<String,Object> map;

    public DownParam(){
        map = new HashMap<String,Object>();
    }

    public DownParam put(String key, Object value){
        map.put(key,value);
        return this;

    }

    public Map<String,Object> getMap(){
        return map;
    }
}
