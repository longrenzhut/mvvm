package com.zhongcai.base.utils;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.zhongcai.base.base.application.BaseApplication;

/**
 * 获得屏幕相关的辅助类
 *
 * @author  Jwen
 *
 */
public class ScreenUtils
{
	private ScreenUtils()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}



	private static int screenH = 0;
	private static int screenW = 0;

	public static void init(Context context){
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		screenW = outMetrics.widthPixels;
		screenH = outMetrics.heightPixels;
	}

	public static int getScreenWidth() {
		return screenW;
	}

	public static int getScreenHeight() {
		return screenH;
	}

	/**
	 * 获得状态栏的高度
	 *
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context)
	{

		int statusHeight = -1;
		try
		{
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 *
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth();
		int height = getScreenHeight();
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 *
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth();
		int height = getScreenHeight();
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;

	}


	/**
	 * 判断手机是否含有虚拟按键  99%
	 */
	public static boolean hasVirtualNavigationBar() {
		boolean hasSoftwareKeys = true;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			Display d = ((WindowManager) BaseApplication.app.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

			DisplayMetrics realDisplayMetrics = new DisplayMetrics();
			d.getRealMetrics(realDisplayMetrics);

			int realHeight = realDisplayMetrics.heightPixels;
			int realWidth = realDisplayMetrics.widthPixels;

			DisplayMetrics displayMetrics = new DisplayMetrics();
			d.getMetrics(displayMetrics);

			int displayHeight = displayMetrics.heightPixels;
			int displayWidth = displayMetrics.widthPixels;

			hasSoftwareKeys = (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			boolean hasMenuKey = ViewConfiguration.get(BaseApplication.app).hasPermanentMenuKey();
			boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
			hasSoftwareKeys = !hasMenuKey && !hasBackKey;
		}

		return hasSoftwareKeys;
	}

	/**
	 * 获取导航栏高度，有些没有虚拟导航栏的手机也能获取到，建议先判断是否有虚拟按键
	 */
	public static int getNavigationBarHeight() {
		int resourceId = BaseUtils.getResource().getIdentifier("navigation_bar_height", "dimen", "android");
		return resourceId > 0 ? BaseUtils.getResource().getDimensionPixelSize(resourceId) : 0;
	}

	  /*      1. 全面屏下
     *          1.1 开启全面屏开关-返回0
     *          1.2 关闭全面屏开关-执行非全面屏下处理方式
     *      2. 非全面屏下
     *          2.1 没有虚拟键-返回0
     *          2.1 虚拟键隐藏-返回0
     *          2.2 虚拟键存在且未隐藏-返回虚拟键实际高度
     */
	public static int getNavigationBarHeightIfRoom(Context context) {
		if(navigationGestureEnabled(context)){
			return 0;
		}
		return getCurrentNavigationBarHeight(((Activity) context));
	}

	/**
	 * 全面屏（是否开启全面屏开关 0 关闭  1 开启）
	 *
	 * @param context
	 * @return
	 */
	public static boolean navigationGestureEnabled(Context context) {
		int val = Settings.Global.getInt(context.getContentResolver(), getDeviceInfo(), 0);
		return val != 0;
	}

	/**
	 * 获取设备信息（目前支持几大主流的全面屏手机，亲测华为、小米、oppo、魅族、vivo都可以）
	 *
	 * @return
	 */
	public static String getDeviceInfo() {
		String brand = Build.BRAND;
		if(TextUtils.isEmpty(brand)) return "navigationbar_is_min";

		if (brand.equalsIgnoreCase("HUAWEI")) {
			return "navigationbar_is_min";
		} else if (brand.equalsIgnoreCase("XIAOMI")) {
			return "force_fsg_nav_bar";
		} else if (brand.equalsIgnoreCase("VIVO")) {
			return "navigation_gesture_on";
		} else if (brand.equalsIgnoreCase("OPPO")) {
			return "navigation_gesture_on";
		} else {
			return "navigationbar_is_min";
		}
	}

	/**
	 * 非全面屏下 虚拟键实际高度(隐藏后高度为0)
	 * @param activity
	 * @return
	 */
	public static int getCurrentNavigationBarHeight(Activity activity){
		if(isNavigationBarShown(activity)){
			return getNavigationBarHeight(activity);
		} else{
			return 0;
		}
	}

	/**
	 * 非全面屏下 虚拟按键是否打开
	 * @param activity
	 * @return
	 */
	public static boolean isNavigationBarShown(Activity activity){
		//虚拟键的view,为空或者不可见时是隐藏状态
		View view  = activity.findViewById(android.R.id.navigationBarBackground);
		if(view == null){
			return false;
		}
		int visible = view.getVisibility();
		if(visible == View.GONE || visible == View.INVISIBLE){
			return false ;
		}else{
			return true;
		}
	}

	/**
	 * 非全面屏下 虚拟键高度(无论是否隐藏)
	 * @param context
	 * @return
	 */
	public static int getNavigationBarHeight(Context context){
		int result = 0;
		int resourceId = context.getResources().getIdentifier("navigation_bar_height","dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

}
