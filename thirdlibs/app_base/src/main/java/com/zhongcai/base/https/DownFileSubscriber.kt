package com.zhongcai.base.https

import com.zhongcai.base.Config
import com.zhongcai.base.rxbus.RxBus
import com.zhongcai.base.theme.layout.LoadingDialog
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference

/**
 * Created by zc3 on 2018/5/7.
 */

abstract class DownFileSubscriber(val destFileDir: String,
                                  val destFileName: String) : Observer<ResponseBody> {


    constructor(destFileName: String):this(Config.DOWN_PATH,destFileName)

    override fun onComplete() {
        mLoading?.let{
            it.dismiss()
        }
    }

    private var mLoading: LoadingDialog? = null

    fun setLoading(mLoading: LoadingDialog?): DownFileSubscriber{
        if (null != mLoading) {
            val weak = WeakReference(mLoading)
            this.mLoading = weak.get()
        }
        return this
    }

    override fun onError(e: Throwable) {
        disposable?.dispose()
        OnFailed()
    }

    override fun onNext(response: ResponseBody) {
        saveFile(response)
    }


    override fun onSubscribe(d: Disposable) {
    }


    private var disposable: Disposable? = null

    init {
        disposable = RxBus.instance().toFlowable(FileLoadModel::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    progress(it.progress,it.total)

                })
    }


    @Throws(IOException::class)
    private fun saveFile(response: ResponseBody): File {
        var inputStream: InputStream? = null
        val buf = ByteArray(2048)
        var len: Int = 0
        var fos: FileOutputStream? = null
        try {
            inputStream = response.byteStream()
            val dir = File(destFileDir)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val file = File(dir, destFileName)
            fos = FileOutputStream(file)
            inputStream?.let{
                len = it.read(buf)
                while (len != -1) {
                    fos?.write(buf, 0, len)
                    len = it.read(buf)
                }
            }
            fos?.flush()
            disposable?.dispose()
            onSuccess(file)
            return file
        } finally {
            try {
                if (inputStream != null) inputStream.close()
            } catch (e: IOException) {
            }

            try {
                if (fos != null) fos.close()
            } catch (e: IOException) {
            }

        }
    }

    abstract fun onSuccess(file: File)
    open fun OnFailed(){

    }

    open fun progress(progress: Long, total: Long){

    }
}
