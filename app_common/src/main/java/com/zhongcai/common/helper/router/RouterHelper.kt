package zhongcai.common.helper.router

import com.zhongcai.base.router.Router
import com.zhongcai.common.helper.router.Paths

/**
 * 路由跳转
 */

object RouterHelper {

    /**
     * 个人信息
     * @return
     */
    fun buildUserInfo(act: AbsActivity,id: String){
        val router = Router.instance().build(Paths.PATH_MAIN)
                .withString("id",id)
        router.navigation(act)
    }



}
