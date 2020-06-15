package com.gamecat.baselib.core.util;

import android.os.Build;

public class SystemVersionUtil {

	/**
	 * has API 8
	 * 
	 * @return
	 */
	public static boolean hasFroyo() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	/**
	 * has API 9
	 * 
	 * @return
	 */
	public static boolean hasGingerbread() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}

	/**
	 * has API 11
	 * 
	 * @return
	 */
	public static boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	/**
	 * has API 12
	 * 
	 * @return
	 */
	public static boolean hasHoneycombMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
	}
	
	/**
	 * has API 15
	 * 
	 * @return
	 */
	public static boolean has15() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
	}
	
	/**
	 * has API 19
	 * 
	 * @return
	 */
	public static boolean has19() {
		return Build.VERSION.SDK_INT >= 19;
	}
	

	/**
	 * has API 16
	 * 
	 * @return
	 */
	public static boolean hasJellyBean() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}

	/**
	 * has API 17
	 * 
	 * @return
	 */
	public static boolean hasJellyBeanMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
	}

	public static int getSDKVersion() {
		return Build.VERSION.SDK_INT;
	}
}
