package com.zhongcai.base.utils;

import android.util.TypedValue;

import com.zhongcai.base.base.application.BaseApplication;


/**
 * 常用单位转换的辅助类
 *
 */
public class DensityUtils {
	private DensityUtils() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * dp转px
	 *
	 * @param dpVal
	 * @return
	 */
	public static int dp2px(float dpVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, BaseApplication.app.getResources().getDisplayMetrics());
	}

	/**
	 * sp转px
	 *
	 * @param spVal
	 * @return
	 */
	public static int sp2px(float spVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				spVal, BaseApplication.app.getResources().getDisplayMetrics());
	}

	/**
	 * px转dp
	 *
	 * @param pxVal
	 * @return
	 */
	public static float px2dp(float pxVal) {
		final float scale = BaseApplication.app.getResources().getDisplayMetrics().density;
		return (pxVal / scale);
	}

	/**
	 * px转sp
	 *
	 * @param pxVal
	 * @return
	 */
	public static float px2sp(float pxVal) {
		return (pxVal / BaseApplication.app.getResources().getDisplayMetrics().scaledDensity);
	}
}
