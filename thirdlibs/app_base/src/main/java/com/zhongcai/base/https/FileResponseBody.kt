package com.zhongcai.base.https

import com.zhongcai.base.rxbus.RxBus
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.Okio
import java.io.IOException

/**
 * Created by zhutao on 2017/9/28.
 */
class FileResponseBody(internal var originalResponse: Response) : ResponseBody() {

    override fun contentType(): MediaType? {
        return originalResponse.body()?.contentType()
    }

    override fun contentLength(): Long {
        return originalResponse.body()?.contentLength() ?: -1L
    }

    override fun source(): BufferedSource {
        return Okio.buffer(object : ForwardingSource(originalResponse.body()?.source()) {
            internal var bytesReaded: Long = 0

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                bytesReaded += if (bytesRead == -1L) 0 else bytesRead
                RxBus.instance().post(FileLoadModel(bytesReaded,contentLength(),bytesReaded == contentLength()))

                return bytesRead
            }
        })
    }

}