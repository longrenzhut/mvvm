package com.zhut.mvvm



import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import androidx.annotation.Nullable
import com.zhongcai.base.base.activity.AbsActivity
import com.zhongcai.common.helper.cache.CacheHelper
import com.zhongcai.common.helper.cache.Caches
import com.zhongcai.common.helper.permission.OnPermission
import com.zhongcai.common.helper.permission.Permission
import com.zhongcai.common.helper.permission.PermissionHelper
import com.zhongcai.common.utils.FileUtils
import com.zhongcai.common.widget.dialog.PromptDialog


/**
 * Created by Jwen on 2018/2/24.
 */

class SplashAct : AbsActivity() {
    override fun getLayoutId(): Int {
        return  0
    }

    private var isOpenSeting = false // 是否启动过应用的设置


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //        changeApplicationState();

        if (!this.isTaskRoot) {
            val intent = intent
            if (intent != null) {
                val action = intent.action
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == action) {
                    finish()
                    return
                }
            }
        } else {
            setContentView(R.layout.act_splash)
            initView(savedInstanceState)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {

        reqPermission()
    }


    fun initAnimat() {
//        FileUtils.createFileUri(this)
        Handler().postDelayed({
            if (CacheHelper.isEmpty(Caches.JOINED_LEADER)) {
                go()
            } else {
                go()
            }
            finish()
        }, 1500)
    }


    private fun go() {
        val intent = Intent(this, MainAct::class.java)
        startActivity(intent)
    }


    private fun reqPermission() {

        PermissionHelper.instance().reqPermission(this,
            Permission.STORAGE,object : OnPermission{

                override fun onFailed() {
                    showMissingPermissionDialog()
                }
                override fun onSuccess() {
                    initAnimat()
                }

            })
    }

    public override fun onResume() {
        super.onResume()
        if (isOpenSeting) {
            reqPermission()

            isOpenSeting = false
        }
    }

    // 显示缺失权限提示
    private fun showMissingPermissionDialog() {

        PromptDialog().setContent(
            getString(R.string.string_help_text)
        )
            .setRight(getString(R.string.settings))
            .setLeft(getString(R.string.quit))
            .setPCancelable(false)
            .setRightListener{
                startAppSettings()
            }
            .setLeftListener{
                finish()
            }.show(supportFragmentManager,javaClass.name)
    }


    // 启动应用的设置
    private fun startAppSettings() {
        isOpenSeting = true
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + this.packageName)
        startActivity(intent)
    }




    override fun onBackPressed() {

    }
}
