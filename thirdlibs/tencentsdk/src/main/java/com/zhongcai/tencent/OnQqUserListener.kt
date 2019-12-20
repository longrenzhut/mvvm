package com.zhongcai.tencent

/**
 * Created by zc3 on 2018/8/10.
 */
interface OnQqUserListener {
    fun OnQqUser(model: QqUserModel)

    fun onError()
}