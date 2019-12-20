package com.zhut.wechat

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import com.haiqi.wechat.R
import com.zhut.wechat.model.WShareModel
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zhongcai.base.utils.BaseUtils
import com.zhongcai.base.utils.GlideHelper
import com.zhongcai.base.utils.ToastUtils
import org.jetbrains.anko.doAsync

/**
 * Created by zhutao on 2017/8/31.
 * 微信分享帮组类
 */
class WxShareHelper {

    companion object {
        private val instance by lazy{
            WxShareHelper()
        }

        fun get(): WxShareHelper = instance
    }

    /**
     * 获取图片的大小
     */
    fun getBitmapsize(bitmap: Bitmap): Long {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            bitmap.byteCount.toLong()
        }
        else
            (bitmap.rowBytes * bitmap.height).toLong()

    }

    private fun obtainBitmap(act: Activity,url: String?,w: Int = 45, h: Int = w,
                             getBitmap: (Bitmap?)-> Unit){
      if(url.isNullOrEmpty()){
            val bitmap = BitmapFactory.decodeResource(BaseUtils.getResource(), R.drawable.wx_icon)
          getBitmap(bitmap)
          return
        }

        GlideHelper.instance().loadasBitmap(act,url,object : GlideHelper.onResourceReadyListener{
            override fun onResourceReady(bitmap: Bitmap?) {
                bitmap?.let {
                    getBitmap(it)
                    if (getBitmapsize(it) / 1024 <= 25) {
                        getBitmap(it)
                    } else {
                        doAsync {
                            val result = CompUtils.comp(it, w, h)
                            getBitmap(result)
                        }
                    }
                }
            }

        })

    }


    private var api: IWXAPI? = null


    private fun isWXAppInstalled(act: Activity): Boolean{
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
     *  scene 0是好友，1是朋友圈
     */
    fun shareWx(act: Activity, model: WShareModel, scene: Int = 0){
        if(!isWXAppInstalled(act)) return
        obtainBitmap(act,model.imgurl,45,45){

            val webpage = WXWebpageObject()
            val msg = WXMediaMessage(webpage)
            with(model){
                webpage.webpageUrl = linkUrl
                msg.title = title
                msg.description = content
            }
            if(null != it)
            msg.setThumbImage(it)

            val req = SendMessageToWX.Req()
            req.transaction = System.currentTimeMillis().toString()
            req.message = msg
            req.scene = scene
            api?.sendReq(req)

        }
    }


}