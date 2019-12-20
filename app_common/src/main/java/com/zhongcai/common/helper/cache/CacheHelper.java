package com.zhongcai.common.helper.cache;


import com.zhongcai.base.cache.Cache;
import com.zhongcai.base.utils.StringUtils;


/**
 * 缓存帮助类
 */

public class CacheHelper {


    //当退出登录 可以清除的缓存的对象
    public static Cache getVar(){
        return Cache.getVar();
    }


    // 不清除的缓存
    public static Cache getVal(){
        return Cache.getVal();
    }





    public static void change(String key){
        getVal().put(key,"");
    }

    public static boolean isChanged(String key ) {
        boolean v = StringUtils.isEmpty(getVal().getString(key));
        if(!v)
            getVal().remove(key);
        return !v;
    }

    public static boolean isEmpty(String key) {
        return StringUtils.isEmpty(getVal().getString(key));
    }
}
