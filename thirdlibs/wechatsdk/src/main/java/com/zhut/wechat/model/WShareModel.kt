package com.zhut.wechat.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by zc3 on 2018/6/29.
 */
class WShareModel (val title: String? = null,
                   val content: String? = null,
                   val linkUrl: String? = null,
                   val imgurl: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(linkUrl)
        parcel.writeString(imgurl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WShareModel> {
        override fun createFromParcel(parcel: Parcel): WShareModel {
            return WShareModel(parcel)
        }

        override fun newArray(size: Int): Array<WShareModel?> {
            return arrayOfNulls(size)
        }
    }

}