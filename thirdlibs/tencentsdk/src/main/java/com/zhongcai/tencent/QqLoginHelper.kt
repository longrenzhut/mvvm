package com.zhongcai.tencent

import android.app.Activity
import android.content.Intent
import com.google.gson.Gson
import com.tencent.connect.UserInfo
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError

/**
 * Created by zhutao on 2017/8/31.
 * qq 登陆 获取信息
 */

class QqLoginHelper{

    companion object {
        private val instance by lazy{
            QqLoginHelper()
        }

        fun get(): QqLoginHelper = instance
    }

    private var mTencent: Tencent? = null
    private var mCallback: ShareCallback? = null

    private fun with(act: Activity) {
        if(null == mTencent)
            mTencent = Tencent.createInstance(Consts.APP_ID, act)
    }

    /**
     *  必须activity或者fragment里面的
     *  onActivityResult回调方面里面调用
     *  不然没有回调值
     */
    fun onActivityResultData(requestCode: Int, resultCode: Int
                             , data: Intent?){
        if (requestCode == 11101) {
            mCallback?.let{
                Tencent.onActivityResultData(requestCode, resultCode,
                        data, it)
                Tencent.handleResultData(data, it)
            }
        }

    }

    /**
     * 拉起qq 获取openid,token
     */
    fun login(act: Activity,listener: OnQqUserListener,isGetAll: Boolean = false){
        with(act)
        mCallback = ShareCallback({
            it?.let{
                val model = Gson().fromJson<QqUserModel>(it.toString(),QqUserModel::class.java)
                if(isGetAll)
                    requestUserInfo(act,model,listener)
                else
                    listener.OnQqUser(model)
            }
        },{
            listener.onError()
        },1)
        mTencent?.login(act, "all", mCallback)
    }


    /**
     * 请求qq用户信息 昵称 以及 头像
     */
    private fun requestUserInfo(act: Activity,qqUserModel: QqUserModel,listener: OnQqUserListener){
        mTencent?.let{
            val user = UserInfo(act, it.qqToken)
            user.getUserInfo(object : IUiListener{
                override fun onComplete(result: Any?) {
                    result?.let{
                        val model = Gson().fromJson<QqUserModel>(it.toString(),QqUserModel::class.java)
                        qqUserModel.nickname = model.nickname
                        qqUserModel.figureurl_qq_2 = model.figureurl_qq_2
                        listener.OnQqUser(qqUserModel)
                    }
                }

                override fun onCancel() {
                    listener.onError()
                }

                override fun onError(p0: UiError?) {
                    listener.onError()
                }
            })
        }
    }

    /**
     * 退出qq登陆
     */
    fun logout(act: Activity) {
        mTencent?.let {
            it.logout(act)
        }
    }

}
