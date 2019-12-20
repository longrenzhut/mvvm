package com.zhongcai.base.router;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;


/**
 * 路由跳转
 */

public class Router {
    private static Router instance;

    public static Router instance() {
        if(null == instance)
            instance = new Router();
        return instance;
    }


    private ARouter aRouter;

    private Router(){
        aRouter = ARouter.getInstance();
    }


    /**
     *
     * @param path
     * @return
     */
    public Postcard build(String path){
        return aRouter.build(path).greenChannel();
    }

    /**
     *
     * @param path
     * @param enterAnim
     * @param exitAnim
     * @return
     */
    public Postcard build(String path, int enterAnim, int exitAnim){
        return  aRouter.build(path)
                .withTransition(enterAnim, exitAnim);
    }

}
