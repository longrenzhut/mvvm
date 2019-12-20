package com.zhut.wechat

import com.zhut.wechat.model.WxUserModel

/**
 * Created by zc3 on 2018/8/10.
 */
interface OnWxUserListener {
    fun OnWxUser(model: WxUserModel)
    fun onError()
}