package com.zhut.wechat.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by zc3 on 2018/8/10.
 */

//    {
//        "access_token":"ACCESS_TOKEN",
//            "expires_in":7200,
//            "refresh_token":"REFRESH_TOKEN",
//            "openid":"OPENID",
//            "scope":"SCOPE",
//            "unionid":"o6_bmasdasdsad6_2sgVt7hMZOPfL"
//    }


//{
//    "openid": "o8pTy0-IumeBiTN3XaA3RhAJyYbg",
//    "nickname": "萧沁尘",
//    "sex": 1,
//    "language": "zh_CN",
//    "city": "Hangzhou",
//    "province": "Zhejiang",
//    "country": "CN",
//    "headimgurl": "http:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/eFJX4UJRbv1lfXIT6VibJx1mRY3KtEPJf3UfIt8eic8Eb6n6W8GUWnsGZgM3HRRZzxwxEbv0eNguR29IzMQ1wHZA\/132",
//    "privilege": [],
//    "unionid": "oc3tw1td0WJXMN--4RAJtu21uBWo"
//}

data class WxUserModel(
        //第一级别获取
        val access_token: String? = null,
        val expires_in: String? = null,
        val refresh_token: String? = null,
        val openid: String? = null,
        val scope: String? = null,
        val unionid: String? = null,

        //第二级获取
        var nickname: String? = null,
        var headimgurl: String? = null,
        var sex:Int? = null,
        var language:String? = null,
        var city:String? = null,
        var province:String? = null,
        var country:String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(access_token)
        parcel.writeString(expires_in)
        parcel.writeString(refresh_token)
        parcel.writeString(openid)
        parcel.writeString(scope)
        parcel.writeString(unionid)
        parcel.writeString(nickname)
        parcel.writeString(headimgurl)
        parcel.writeValue(sex)
        parcel.writeString(language)
        parcel.writeString(city)
        parcel.writeString(province)
        parcel.writeString(country)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WxUserModel> {
        override fun createFromParcel(parcel: Parcel): WxUserModel {
            return WxUserModel(parcel)
        }

        override fun newArray(size: Int): Array<WxUserModel?> {
            return arrayOfNulls(size)
        }
    }
}