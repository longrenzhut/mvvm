package com.zhongcai.tencent

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by zc3 on 2018/8/10.
 */
data class QqUserModel(
        val access_token:String? = null,
        val expires_in:String? = null,
        val openid:String? = null,

        //第二级获取
        var nickname: String? = null,
        var figureurl_qq_2: String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(access_token)
        parcel.writeString(expires_in)
        parcel.writeString(openid)
        parcel.writeString(nickname)
        parcel.writeString(figureurl_qq_2)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QqUserModel> {
        override fun createFromParcel(parcel: Parcel): QqUserModel {
            return QqUserModel(parcel)
        }

        override fun newArray(size: Int): Array<QqUserModel?> {
            return arrayOfNulls(size)
        }
    }
}