package com.zhongcai.tencent

/**
 * Created by zc3 on 2018/9/27.
 */
data class SinaModel(
        val content: String,//微博内容

//        用户ip。
//        必须正确填写用户侧真实ip，不能为内网ip及以127或255开头的ip，以分析用户所在地。
        val clientip: String,
        //	用户所在地理位置的经度。
//        为实数，最多支持10位有效数字。有效范围：-180.0到+180.0，+表示东经，默认为0.0。
        val longitude: String = "0.0",
        //用户所在地理位置的纬度。
//        为实数，最多支持10位有效数字。有效范围：-90.0到+90.0，+表示北纬，默认为0.0。
        val latitude: String = "0.0",
        //	标识是否将发布的微博同步到QQ空间（0：同步； 1：不同步；），默认为0。
        val syncflag: Int = 0,
        val drawable: Int  = 0,
        val url: String  = ""//网络图片

)