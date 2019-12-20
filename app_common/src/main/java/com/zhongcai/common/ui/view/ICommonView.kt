package com.zhongcai.common.ui.view

import com.zhongcai.base.base.view.IBaseView

/**
 * Created by zc3 on 2019/2/15.
 */
interface ICommonView: IBaseView{
    fun onCompleted(){

    }
    fun onError(){

    }

    fun OnFailed(code: Int){

    }
}