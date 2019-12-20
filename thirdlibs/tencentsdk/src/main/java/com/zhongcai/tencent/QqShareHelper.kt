package com.zhongcai.tencent

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import com.tencent.connect.common.Constants
import com.tencent.connect.share.QQShare
import com.tencent.connect.share.QzoneShare
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import com.zhongcai.base.utils.BaseUtils
import com.zhongcai.base.utils.GlideHelper
import java.io.ByteArrayOutputStream

/**
 * Created by zhutao on 2017/8/31.
 * qq 分享帮助类
 */
class QqShareHelper {

    companion object {
        private val instance by lazy{
            QqShareHelper()
        }

        fun get(): QqShareHelper = instance
    }


    private var mTencent: Tencent? = null
    private var mCallback: ShareCallback? = null

    /**
     *  必须activity或者fragment里面的
     *  onActivityResult回调方面里面调用
     *  不然分享后没有回调
     */
    fun onActivityResultData(requestCode: Int, resultCode: Int
                             , data: Intent){
        mCallback?.let {
            Tencent.onActivityResultData(requestCode, resultCode,
                    data,it)
        }
    }


    private fun with(act: Activity): Bundle{
        mTencent?.let{
            mTencent = Tencent.createInstance(Consts.APP_ID, act)
        }
        return Bundle()
    }

    /**
     * 分享到qq
     */
    fun shareToQQ(act: Activity,model: TShareModel,
                onComplete: ((Any?)-> Unit)? = null,
                onError: ((UiError?)-> Unit)? = null){
        val params =  with(act)
        with(model) {
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT)
            params.putString(QQShare.SHARE_TO_QQ_TITLE, title)
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content)
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, linkUrl)
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgurl)
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "中财论坛")
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,System.currentTimeMillis().toInt() /*111*/)
        }
        mTencent?.shareToQQ(act, params,ShareCallback(onComplete,onError))
    }

    /**
     * 分享到qq空间
     */
    fun shareToQzone(act: Activity,model: TShareModel,
                   onComplete: ((Any?)-> Unit)? = null,
                   onError: ((UiError?)-> Unit)? = null){
        val params = with(act)
        with(model){
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title)//必填
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content)
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, linkUrl)//必填
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL,
                    arrayListOf<String>(imgurl?:""))
        }
        mCallback =  ShareCallback(onComplete,onError)
        mTencent?.shareToQzone(act, params, mCallback)

    }


    /**
     * 单独分享图片到qq
     */
    fun sharePicToqq(act: Activity,imgurl: String,
                        onComplete: ((Any?)-> Unit)? = null,
                        onError: ((UiError?)-> Unit)? = null){

        val params = with(act)

        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE)
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imgurl)
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "LPS htt")
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,
                QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE)
        mCallback =  ShareCallback(onComplete,onError)
        mTencent?.shareToQQ(act, params, mCallback)
    }

    /**
     * 单独分享图片到qq 空间
     */
    fun sharePicToQzone(act: Activity,imgurl: String,
                     onComplete: ((Any?)-> Unit)? = null,
                     onError: ((UiError?)-> Unit)? = null){
        val params = with(act)
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imgurl)
        //params.putString(QQShare.SHARE_TO_QQ_APP_NAME,name);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE)
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN)
        mCallback =  ShareCallback(onComplete,onError)
        mTencent?.shareToQQ(act, params, mCallback)
    }


    /**
     * 发表一条微博信息（纯文本）到腾讯微博平台上。
     */
    fun shareTestToSina(act: Activity,model: SinaModel,listener: BaseApiListener){

        with(act)

        val bundle = Bundle()
        //定义API返回的数据格式。
        bundle.putString("format", "json")
        //微博的内容
        bundle.putString("content", model.content)
        bundle.putString("clientip", model.clientip)
        bundle.putString("longitude", model.longitude)
        bundle.putString("latitude", model.latitude)
        bundle.putInt("syncflag", model.syncflag)
        //容错标志，支持按位操作，默认为0。
//        0x20：微博内容长度超过140字则报错；
//        0：以上错误均做容错处理，即发表普通微博。
        bundle.putInt("compatibleflag",0)

        mTencent?.requestAsync(Consts.ADD_T, bundle, Constants.HTTP_POST
                , listener,null)

    }


    /**
     * 上传一张图片，并发布一条消息到腾讯微博平台上。
     */
    fun shareTestPicToSina(act: Activity,model: SinaModel,listener: BaseApiListener){

        with(act)

        val bundle = Bundle()
        //定义API返回的数据格式。
        bundle.putString("format", "json")
        bundle.putString("content", model.content)

        bundle.putString("clientip", model.clientip)
        bundle.putString("longitude", model.longitude)
        bundle.putString("latitude", model.latitude)
        bundle.putInt("syncflag", model.syncflag)
        //容错标志，支持按位操作，默认为0。
//        0x20：微博内容长度超过140字则报错；
//        0：以上错误均做容错处理，即发表普通微博。
        bundle.putInt("compatibleflag",0)

        // 把 bitmap 转换为 byteArray , 用于发送请求
        if(model.url.isNotEmpty()){

            GlideHelper.instance().loadasBitmap(act,model.url){

                val baos = ByteArrayOutputStream()
                it.compress(Bitmap.CompressFormat.JPEG, 40, baos)
                val buff = baos.toByteArray()
                bundle.putByteArray("pic", buff)

                mTencent?.requestAsync(Consts.ADD_PIC_T, bundle, Constants.HTTP_POST
                        , listener,null)

            }
            return
        }
        if(model.drawable != 0) {
            val bitmap = BitmapFactory.decodeResource(BaseUtils.getResource(), model.drawable)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos)
            val buff = baos.toByteArray()
            bundle.putByteArray("pic", buff)
        }


        mTencent?.requestAsync(Consts.ADD_PIC_T, bundle, Constants.HTTP_POST
                , listener,null)

    }

}