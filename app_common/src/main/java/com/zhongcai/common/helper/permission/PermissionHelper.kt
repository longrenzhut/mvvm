package com.zhongcai.common.helper.permission


import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission

class PermissionHelper {

    companion object {
      private val mInstance by lazy {
          PermissionHelper()
      }
        fun instance(): PermissionHelper {
            return mInstance
        }
    }


    /**
     * 打电话
     * @param context
     * @param phone
     */
    fun call(context: FragmentActivity, phone: String) {
        if (phone.contains("*"))
            return
        reqPermissionEach(context, Permission.CALL_PHONE, object : OnPermission {

            override fun onSuccess() {
                    val intent = Intent(Intent.ACTION_DIAL)
                    val data = Uri.parse("tel:$phone")
                    intent.data = data
                    context.startActivity(intent)
                }
            })

    }

    fun reqPermissionEach(activity: FragmentActivity, permission: String, listener: OnPermission) {
        reqPermission(activity, arrayOf(permission), listener)
    }

    fun reqPermission(activity: FragmentActivity,
        permissions: Array<String>,
        listener: OnPermission
    ) {
        if (permissions.isEmpty()) {
            listener.onSuccess()
            return
        }

        AndPermission.with(activity)
            .runtime()
            .permission(permissions)
            .onGranted {
                listener.onSuccess()
            }
            .onDenied {
                listener.onFailed()
            }
            .start()
    }

    fun reqPermission(activity: Fragment,
        permissions: Array<String>,
        listener: OnPermission
    ) {
        if (permissions.isEmpty()) {
            listener.onSuccess()
            return
        }

        AndPermission.with(activity)
            .runtime()
            .permission(permissions)
            .onGranted {
                listener.onSuccess()
            }
            .onDenied {
                listener.onFailed()
            }
            .start()
    }



    //
    //    public void showMissingPerDialog(final Context ctx){
    //        new PromptDialog(ctx).setContent(
    //                ctx.getString(R.string.string_help_text))
    //                .setRight(ctx.getString(R.string.settings))
    //                .setLeft(ctx.getString(R.string.quit))
    //                .setPCancelable(false)
    //                .setRightListener(new PromptDialog.OnRightClickListener() {
    //                    @Override
    //                    public void OnClick() {
    //                        startAppSettings(ctx);
    //                    }
    //                }).show();
    //    }


    //    // 启动应用的设置
    //    public void startAppSettings(Context ctx) {
    //        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    //        intent.setData(Uri.parse("package:" + ctx.getPackageName()));
    //        ctx.startActivity(intent);
    //    }


}
