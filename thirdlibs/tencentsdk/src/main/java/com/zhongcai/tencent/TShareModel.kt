package com.zhongcai.tencent

/**
 * Created by zc3 on 2018/6/29.
 */
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by zhutao on 2017/8/31.
 */
data class TShareModel(val title: String? = null,
                      val content: String? = null,
                      val linkUrl: String? = null,
                      val imgurl: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
        writeString(content)
        writeString(linkUrl)
        writeString(imgurl)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TShareModel> = object : Parcelable.Creator<TShareModel> {
            override fun createFromParcel(source: Parcel): TShareModel = TShareModel(source)
            override fun newArray(size: Int): Array<TShareModel?> = arrayOfNulls(size)
        }
    }
}