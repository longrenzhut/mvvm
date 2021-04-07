package com.aiwo.service

import android.content.Context
import androidx.multidex.MultiDex
import com.zhongcai.common.ui.application.CommonApp

class App :CommonApp(){

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)

    }
}