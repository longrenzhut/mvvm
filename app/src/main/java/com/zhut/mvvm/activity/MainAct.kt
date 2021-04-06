package com.zhut.mvvm.activity

import android.os.Bundle
import com.zhongcai.base.base.activity.BaseActivity
import com.zhongcai.base.rxbinding.RxClick
import com.zhongcai.base.utils.ToastUtils
import com.zhongcai.common.helper.db.helper.DbHelper
import com.zhongcai.common.widget.optaddress.OptAddressDialog
import com.zhut.mvvm.R
import com.zhut.mvvm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainAct : BaseActivity<MainViewModel>() {

    override fun getViewModel(): MainViewModel = LViewModelProviders(MainViewModel::class.java)

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        DbHelper.instance().initLocal("water_address.db")
        RxClick.click(this,mTvTest){
            OptAddressDialog()
                .show(supportFragmentManager,javaClass.name)
        }


        mViewModel.getConfig()
    }

    override fun setObserve() {
        observe<Int>(mViewModel.mConfig){
            ToastUtils.showToast( "werer$it")
        }
    }

}
