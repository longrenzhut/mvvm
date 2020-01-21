package zhongcai.common.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.zhongcai.base.utils.BaseUtils
import com.zhongcai.common.utils.AppUtils
import java.io.File


/**
 * Created by zhutao on 2017/9/28.
 * 通知栏帮助类
 */

class NotifyHelper(private val ctx: Context){



    private val builder by lazy(LazyThreadSafetyMode.NONE){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(AppUtils.getPackageName(ctx), AppUtils.getAppName(ctx), NotificationManager.IMPORTANCE_LOW)

            manager.createNotificationChannel(channel)
        }

       NotificationCompat.Builder(ctx,AppUtils.getPackageName(ctx))
    }

    private val manager by lazy(LazyThreadSafetyMode.NONE){
        ctx.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager
    }

    /**
     * 设置图标
     */
    fun setIcon(icon: Int,isbig: Boolean = false): NotifyHelper
            = this.apply {
        if(isbig)
            builder.setLargeIcon(BitmapFactory.decodeResource
            (BaseUtils.getResource(), icon))
        else
            builder.setSmallIcon(icon)
    }

//    notification.flags = Notification.FLAG_AUTO_CANCEL;

    /**
     * 设置标题
     */
    fun setTitle(title: String): NotifyHelper
            = this.apply {

        builder.setContentTitle(title)
    }

    /**
     * 设置内容
     */
    fun setContent(content: String): NotifyHelper
            = this.apply {
        builder.setContentText(content)
    }

    /**
     * 系统收到通知时，通知栏上面显示的文字
     * 开启了浮窗的通知 和 setContent 展示的内容一样
     */
    fun setTicker(content: String): NotifyHelper
            = this.apply {
        builder.setTicker(content)
    }

    /**
     * 通知产生的时间，会在通知信息里显示
     */
    fun setWhen(whens: Long): NotifyHelper
            = this.apply {
        builder.setWhen(whens)
    }

    /**
     * 设置该通知优先级
     */
    fun setPriority(priority: Int = Notification.VISIBILITY_PUBLIC): NotifyHelper
            = this.apply {
        builder.priority = priority
    }

    /**
     * 设置为不可清除模式
     */
    fun setOngoing(ongoing:Boolean = true): NotifyHelper
            = this.apply {
        builder.setOngoing(ongoing)
    }



    /**
     * 显示通知栏的进度条的进度
     */
    fun setProgress(progress: Int,max: Int): NotifyHelper
            = this.apply {
        builder.setProgress(max, progress, true)
    }

    /**
     *  通知栏的进度条分成100份
     */
    fun setProgress(progress: Int): NotifyHelper
            = this.apply {
        builder.setProgress(100,progress,false)
    }

    /**
     * 隐藏通知栏进度条
     */
    fun hideProgress(): NotifyHelper
            = this.apply {
        builder.setProgress(0, 0, false)
    }

    /**
     * 使用默认提示音，可以动态设置
     */
    fun setDefaults(flag: Int = Notification.DEFAULT_ALL): NotifyHelper
            = this.apply {
        builder.setDefaults(flag)
    }

    /**
     * 设置当点击通知栏 要跳转的界面
     */
    fun setIntent(intent: Intent): NotifyHelper{
        val pandIntent = PendingIntent.getActivity(ctx, System.currentTimeMillis().toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pandIntent)

        return this
    }

    /**
     * 用户点击就自动消失 true
     */
    fun setAutoCancel(autoCancel: Boolean): NotifyHelper{
        builder.setAutoCancel(autoCancel)

        return this
    }

    /**
     * 自定义通知栏
     */
    fun setContentView(layoutId: Int): NotifyHelper{
        builder.setCustomContentView(RemoteViews(ctx.packageName, layoutId))
        return this
    }

////自定义布局的通知
//    public void customLayoutNotice(View view) {
//        Builder mBuilder = new Builder(this);
//        mBuilder.setTicker(通知标题6);
//        mBuilder.setTicker(通知标题6);
//        mBuilder.setSmallIcon(R.drawable.ic_launcher);
//
//        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_layout_notice);
//        mBuilder.setContent(remoteViews);
//        //为RemoteViews上的按钮设置文字
//        remoteViews.setCharSequence(R.id.custom_layout_button1, setText, Button1);
//        remoteViews.setCharSequence(R.id.custom_layout_button2, setText, Button2);
//
//        //为RemoteViews上的按钮设置点击事件
//        Intent intent1 = new Intent(this, CustomLayoutResultActivity.class);
//        intent1.putExtra(content, From button1 click!);
//        PendingIntent pIntentButton1 = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickPendingIntent(R.id.custom_layout_button1, pIntentButton1);
//
//        Intent intent2 = new Intent(this, CustomLayoutResultActivity.class);
//        intent2.putExtra(content, From button2 click!);
//        PendingIntent pIntentButton2 = PendingIntent.getActivity(this, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickPendingIntent(R.id.custom_layout_button2, pIntentButton2);
//
//        nm.notify(6, mBuilder.build());
//    }


    /**
     * 点击通知栏跳转到A界面 ，返回跳到 T界面
     */
     fun <T> createPandingIntent(clazz: Class<T>,intent: Intent){
        //使用TaskStackBuilder为“通知页面”设置返回关系
        val stackBuilder = TaskStackBuilder.create(ctx)
        //为点击通知后打开的页面设定 返回 页面。（在manifest中指定）
        stackBuilder.addParentStack(clazz)
        stackBuilder.addNextIntent(intent)
        val pandIntent = stackBuilder.getPendingIntent(
                200,//回调
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(pandIntent)
    }

    /**
     * 安装apk
     */
    fun goInstallApk(file: File){
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        ctx.startActivity(intent)
    }

    /**
     * 点击通知栏安装apk
     */
    fun clickgoInstallApk(file: File): NotifyHelper{
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        return setIntent(intent)

    }


    /**
     * 显示以及刷新通知
     */
    fun showNotify(notifyId: Int){
        manager.notify(notifyId,builder.build())
    }

    /**
     * 删除一个通知
     */
    fun cancal(notifyId: Int){
        manager.cancel(notifyId)
    }
}