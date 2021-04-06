package com.zhongcai.common.utils

import com.zhongcai.base.base.activity.AbsActivity
import com.zhongcai.base.https.HttpProvider
import com.zhongcai.base.https.ReqCallBack
import com.zhongcai.base.https.UpFileParam
import com.zhongcai.base.rxbus.RxBus
import com.zhongcai.base.utils.CompUtils
import com.zhongcai.common.ui.model.FileModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable


class UploadHelper{

    companion object {


        private val insta  by lazy {
            UploadHelper()
        }

        fun instance(): UploadHelper = insta
    }



    private var mCompositeDisposable: CompositeDisposable? = null

    private fun addDisposable(disposable: Disposable) {
        if (null == mCompositeDisposable)
            mCompositeDisposable = CompositeDisposable()
        mCompositeDisposable?.add(disposable)
    }


    fun dispose() {
        mCompositeDisposable?.dispose()
    }


    fun post(absActivity: AbsActivity, model: FileModel,
                   onSuccess: ((model: FileModel?,isSuc: Boolean)->Unit)? = null){
        CompUtils.compress(absActivity, model.loacalurl?:"") {
            val params = UpFileParam()
            params.putFile(it)

           val disposable = HttpProvider.getHttp().upFile(absActivity, "app/attach/upload", params,
                object : ReqCallBack<MutableList<FileModel>>() {
                    override fun OnSuccess(list: MutableList<FileModel>) {

                        list[0]?.let {
                            it.loacalurl = model.loacalurl
                            onSuccess?.invoke(it, true)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        super.onError(e)

                        onSuccess?.invoke(null, false)
                    }


                }.setIspaging().setKey("infos"))

            addDisposable(disposable)
        }
    }


    private var lenX = 0

    private val datasX by lazy {
        mutableListOf<FileModel>()
    }


    fun post(absActivity: AbsActivity, urls: List<String>,
             onSuccess: ((list: MutableList<FileModel>?,isSuc: Boolean)->Unit)? = null,
             value: ((list: MutableList<FileModel>?,isSuc: Boolean)->Unit)? = null){

        if(lenX == 0)
            datasX.clear()
        val url = urls[lenX]
        CompUtils.compress(absActivity, url) {
            val params = UpFileParam()
            params.putFile(it)

           val disposable = HttpProvider.getHttp().upFile(absActivity,"app/attach/upload", params,
                object : ReqCallBack<MutableList<FileModel>>() {
                    override fun OnSuccess(list: MutableList<FileModel>) {
                        lenX++
                        list[0]?.let{
                            datasX.add(it)
                        }
                        //上传成功
                        if(lenX == urls.size){
                            onSuccess?.invoke(datasX,true)
                            value?.invoke(datasX,true)
                            RxBus.instance().post(5000,0)
                            lenX = 0
                        }
                        else{
                            post(absActivity,urls,onSuccess,value)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        super.onError(e)
                        RxBus.instance().post(5000,1)
                        onSuccess?.invoke(datasX,false)
                        if(lenX < urls.size && value != null) {
                            for (index in lenX until urls.size){
                                datasX.add(changeModel(null,urls[index]))
                            }
                            value.invoke(datasX,false)
                        }

                        lenX = 0

                    }

                    override fun OnFailed(code: Int) {
                        super.OnFailed(code)
                        RxBus.instance().post(5000,1)
                        onSuccess?.invoke(datasX,false)
                        if(lenX < urls.size && value != null) {
                            for (index in lenX until urls.size){
                                datasX.add(changeModel(null,urls[index]))
                            }
                            value.invoke(datasX,false)
                        }
                        lenX = 0

                    }


                    override fun onCompleted() {
                        absActivity.dismiss()
                    }
                }.setIspaging().setKey("infos"))

            addDisposable(disposable)
        }

    }


    private fun changeModel(mFileModel: FileModel?,localUrl: String,model: FileModel? = null): FileModel{
        val model = FileModel()
        mFileModel?.let {
            model.id = it.id
            model.url = it.url
            model.ext = it.ext
            model.name = it.name
        }
        model.loacalurl = localUrl

        return model

    }

}