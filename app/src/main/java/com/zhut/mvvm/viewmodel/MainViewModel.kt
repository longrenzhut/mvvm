package com.zhut.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zhongcai.base.base.viewmodel.BaseViewModel
import com.zhongcai.base.https.Params
import com.zhongcai.base.https.ReqCallBack
import org.json.JSONObject

class MainViewModel : BaseViewModel() {

    val mConfig by lazy {
        MutableLiveData<Int>()
    }


    fun getConfig(){

        postP("app/access/getConfig", Params(),object : ReqCallBack<JSONObject>(){
            override fun OnSuccess(json: JSONObject?) {
                val config = json?.optInt("switch")?:0
                mConfig.value = config
            }
        })

    }
}