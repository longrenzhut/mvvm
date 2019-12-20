package com.zhongcai.common.widget.webview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zc3 on 2018/4/12.
 */

public class WebParam implements Parcelable {

    private String url;
    private String title = "";
    private int type;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.title);
        dest.writeInt(this.type);
    }

    public WebParam() {
    }

    protected WebParam(Parcel in) {
        this.url = in.readString();
        this.title = in.readString();
        this.type = in.readInt();
    }

    public static final Creator<WebParam> CREATOR = new Creator<WebParam>() {
        @Override
        public WebParam createFromParcel(Parcel source) {
            return new WebParam(source);
        }

        @Override
        public WebParam[] newArray(int size) {
            return new WebParam[size];
        }
    };
}
