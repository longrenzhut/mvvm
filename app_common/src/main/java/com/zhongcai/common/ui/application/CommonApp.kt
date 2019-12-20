package  com.zhongcai.common.ui.application

import android.content.Context
import com.tencent.smtt.sdk.QbSdk
import com.zhongcai.base.base.application.BaseApplication
import org.jetbrains.anko.doAsync

/**
 * Created by zc3 on 2019/2/15.
 */
open class CommonApp: BaseApplication() {

    override fun init() {
        super.init()

    }


    /**
     * 初始化TBS浏览服务X5内核
     */
    fun initTBS(ctx: Context) {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.setDownloadWithoutWifi(true)//非wifi条件下允许下载X5内核
        doAsync{

            QbSdk.initX5Environment(ctx, object : QbSdk.PreInitCallback {

                override fun onCoreInitFinished() {

                }

                override fun onViewInitFinished(b: Boolean) {
                    //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                }
            })
        }

    }
}