package com.zhut.mvvm

import android.os.Bundle
import com.zhongcai.base.base.activity.AbsActivity
import com.zhongcai.base.rxbinding.RxClick
import com.zhongcai.common.helper.db.helper.DbHelper
import com.zhongcai.common.widget.optaddress.OptAddressDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainAct : AbsActivity() {
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        DbHelper.instance().initLocal("water_address.db")
        RxClick.click(this,mTvTest){
            OptAddressDialog()
                .show(supportFragmentManager,javaClass.name)
        }

    }

}
