package com.zhongcai.base.https

import com.zhongcai.base.Config
import com.zhongcai.base.rxbus.RxBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Created by zhutao on 2017/9/28.
 * 文件下载的回调
 */
abstract class DownFileSubscriber(val fileName: String?,val path: String = Config.path): DisposableObserver<ResponseBody>() {


    var disporsable: Disposable? = null

    init {
        disporsable = RxBus.instance().toFlowable(FileLoadModel::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                progress(it.progress, it.total)
            }

    }

    override fun onComplete() {
        disporsable?.dispose()
    }

    override fun onNext(t: ResponseBody) {
        saveFile(t)
    }

    override fun onError(e: Throwable) {
        OnFailed()
    }

    open fun onSuccess(file: File) {
        disporsable?.dispose()

    }

    open fun OnFailed() {
        disporsable?.dispose()
    }

    abstract fun progress(progress: Long, total: Long)

    @Throws(IOException::class)
    private fun saveFile(response: ResponseBody?): File? {
        val tempFile = File(path + "${fileName}.temp")
        if(tempFile.exists())
            tempFile.delete()

        var inputStream: InputStream? = null
        val buf = ByteArray(2048)
        var len: Int = 0
        var fos: FileOutputStream? = null
        try {
            inputStream = response?.byteStream()
            val dir = File(path)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val file = File(dir, "${fileName}.temp")
            fos = FileOutputStream(file)
            inputStream?.let {
                len = it.read(buf)
                while (len != -1) {
                    fos?.write(buf, 0, len)
                    len = it.read(buf)
                }
            }
            fos?.flush()
            val newFile = File(dir, fileName)
            file.renameTo(newFile)
            onSuccess(newFile)
            return newFile
        } catch (e: IOException) {
            OnFailed()
        } finally {
            inputStream?.close()
            fos?.close()

        }

        return null
    }
}
