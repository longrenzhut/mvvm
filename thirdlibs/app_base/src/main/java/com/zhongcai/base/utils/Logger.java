package com.zhongcai.base.utils;


import android.util.Log;

import com.zhongcai.base.Config;

public class Logger {

	public static void debug(String message){
		if(Config.DEVELOP || Config.TEST || Config.PRE)
			Log.d("com.zhut.debug", "---------------: " + message );
	}

	public static void d(String message){
		if(Config.DEVELOP || Config.TEST || Config.PRE)
			Log.e("com.zhut.error", "---------------: " + message );
	}

	public static void info(String message){
		if(Config.DEVELOP || Config.TEST || Config.PRE)
			Log.i("com.zhut.info", "---------------: " + message );
	}


}
