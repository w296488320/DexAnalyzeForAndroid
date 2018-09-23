package com.makelove.dex.utils;

import android.util.Log;

public class LogUtils {

	private static  boolean  enableLog = true;

	public static void e(String tag , String msg){

		if(enableLog){
			Log.e(tag, msg);
		}
	}

	public static void e(String msg){

		if(enableLog){
			Log.e("Q296488320", msg);
		}
	}



}
