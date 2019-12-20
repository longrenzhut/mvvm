package com.zhut.wechat

import android.app.Activity
import com.google.gson.Gson
import com.zhut.wechat.model.WxUserModel
import com.zhut.wechat.service.WxService
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zhongcai.base.https.BaseSubscriber
import com.zhongcai.base.https.HttpProvider
import com.zhongcai.base.utils.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONObject

/**
 * Created by zhutao on 2017/8/31.
 * 微信登陆
 */
class WxLoginHelper{

    companion object {
        private val instance by lazy{
            WxLoginHelper()
        }

        fun get(): WxLoginHelper = instance
    }

    private var api: IWXAPI? = null


    private fun isWXAppInstalled(act: Activity?): Boolean{
        if(null == api)
            api = WXAPIFactory.createWXAPI(act, Consts.APP_ID, true)
        api?.let{
            if(!it.isWXAppInstalled)
                ToastUtils.showToast("您还未安装微信客户端")
            return it.isWXAppInstalled
        }
        return true
    }


    /**
     * 调起微信支付 WXEntryActivity 回调
     * 获取code
     */
    fun login(act:Activity) {
        if(!isWXAppInstalled(act)) return
        api?.let {
            it.registerApp(Consts.APP_ID)

            val req = SendAuth.Req()
            req.scope = "snsapi_userinfo"
            req.state = "wechat_sdk_demo_test"
            it.sendReq(req)
        }
    }


    /**
     * 根据code 获取token 等数据
     * isGetAll 是否获取所有数据
     */
    fun requestToken(code: String,listener: OnWxUserListener,isGetAll: Boolean = false){
        val url = "https://api.weixin.qq.com/sns/oauth2/"

        HttpProvider.getHttp().buildRetrofit(url)
                .create(WxService::class.java)
                .requestToken(Consts.APP_ID,Consts.APP_SECRET,code,"authorization_code")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object: BaseSubscriber<ResponseBody>(){
                    override fun onNext(responseBody: ResponseBody) {
                        val value = responseBody.string()
                        responseBody.close()

                        val model = Gson().fromJson<WxUserModel>(value,WxUserModel::class.java)
                        if(!isGetAll)
                            listener.OnWxUser(model)
                        else{
                            requestUser(model,listener)
                        }
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        listener.onError()
                    }

                })
    }



    /**
     * 请求用户信息
     */
    private fun requestUser(wxUserModel: WxUserModel,listener: OnWxUserListener? = null){
        val url = "https://api.weixin.qq.com/sns/"


        HttpProvider.getHttp().buildRetrofit(url)
                .create(WxService::class.java)
                .requestUser(wxUserModel.access_token?:"",wxUserModel.openid?:"")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object: BaseSubscriber<ResponseBody>(){
                    override fun onNext(responseBody: ResponseBody) {
                        val value = responseBody.string()
                        responseBody.close()

                        val model = Gson().fromJson<WxUserModel>(value,WxUserModel::class.java)
                        wxUserModel.nickname = model.nickname
                        wxUserModel.headimgurl = model.headimgurl
                        wxUserModel.sex = model.sex
                        wxUserModel.language = model.language
                        wxUserModel.city = model.city
                        wxUserModel.province = model.province
                        wxUserModel.country = model.country
                        listener?.OnWxUser(wxUserModel)
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        listener?.onError()
                    }
                }
                )

    }

}