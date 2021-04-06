package com.zhongcai.base.https;

import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.zhongcai.base.rxbus.RxBus;
import com.zhongcai.base.utils.BaseUtils;
import com.zhongcai.base.utils.ToastUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by zc3 on 2018/8/16.
 */

public class UploadFileRequestBody extends RequestBody {

    private RequestBody mRequestBody;
    private BufferedSink bufferedSink;

    private String getMimeType(String filePath) {
        String ext = MimeTypeMap.getFileExtensionFromUrl(filePath);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);

    }

    public UploadFileRequestBody(File file) {
        String ext = getMimeType(file.getPath());
        if(!TextUtils.isEmpty(ext))
            this.mRequestBody = RequestBody.create(MediaType.parse(ext), file);
        else{
            try {
                byte[] bytes = BaseUtils.readStream(file.getPath());
                this.mRequestBody = RequestBody.create(bytes);
            } catch (Exception e) {
                e.printStackTrace();
                this.mRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            }
        }
    }


    public UploadFileRequestBody(Uri fileUri) {
        byte[]  bytes = BaseUtils.readStream(fileUri);
        if(null == bytes){
            ToastUtils.showToast("文件已损坏");
            return;
        }
        this.mRequestBody = RequestBody.create(bytes);
    }

    public UploadFileRequestBody(byte[] bytes) {
        if(null == bytes){
            ToastUtils.showToast("文件已损坏");
            return;
        }
        this.mRequestBody = RequestBody.create(bytes);
    }


    public UploadFileRequestBody(RequestBody requestBody) {
        this.mRequestBody = requestBody;
    }

    //返回了requestBody的类型，想什么form-data/MP3/MP4/png等等等格式
    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    //返回了本RequestBody的长度，也就是上传的totalLength
    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        mRequestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                //回调上传接口
                boolean isDone = false;
                if(bytesWritten != 0)
                    isDone = bytesWritten == contentLength;
                RxBus.instance().post(new FileLoadModel(bytesWritten,contentLength, isDone));
            }
        };
    }
}