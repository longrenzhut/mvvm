package com.zhongcai.base.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.zhongcai.base.R;
import com.zhongcai.base.base.application.BaseApplication;

import java.lang.reflect.Field;

/**
 * Toast统一管理类
 * 
 */
public class ToastUtils {
	public static void showToast(String message) {
		if(isShowToast())
			return;
		if(null == message || "".equals(message) || "null".equals(message) || "非法操作".equals(message))
			return;
		View view = null;
			view = LayoutInflater.from(BaseApplication.app).inflate(R.layout.toast_text, null);
//        if(type == 1){
//            view.setBackgroundResource(R.drawable.shape_toast_gray_bg);
//        }
		TextView title = view.findViewById(R.id.m_tv_text);
		title.setText(message);
		Toast toast = new Toast(BaseApplication.app);
//        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, DensityUtils.dp2px(120));
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		try {
			Object mTN;
			mTN = getField(toast, "mTN");
			if (mTN != null) {
				Object mParams = getField(mTN, "mParams");
				if (mParams != null && mParams instanceof WindowManager.LayoutParams) {
					WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
					params.windowAnimations = R.style.AnimationToast;
				}
			}
		} catch (Exception e) {
//			Log.d("UIToast", "Toast windowAnimations setting failed");
		}

		toast.setView(view);
		toast.show();
	}

	private static Object getField(Object object, String fieldName)
			throws NoSuchFieldException, IllegalAccessException {
		Field field = object.getClass().getDeclaredField(fieldName);
		if (field != null) {
			field.setAccessible(true);
			return field.get(object);
		}
		return null;
	}


	private static long lastToastTime;
	private final static long TIME = 1500;

	public static boolean isShowToast() {
		long time = System.currentTimeMillis();
		if (time - lastToastTime < TIME) {
			return true;
		}
		lastToastTime = time;
		return false;
	}

}