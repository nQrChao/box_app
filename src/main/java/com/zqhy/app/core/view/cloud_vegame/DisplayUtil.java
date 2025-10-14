package com.zqhy.app.core.view.cloud_vegame;

import android.content.Context;

/**
 * dp銆乻p 杞崲涓?px 鐨勫伐鍏风被
 * 
 * @author jingle1267@163.com
 * 
 */
public class DisplayUtil {
	/**
	 * 灏唒x鍊艰浆鎹负dip鎴杁p鍊硷紝淇濊瘉灏哄澶у皬涓嶅彉
	 * 
	 * @param pxValue
	 * @param scale
	 *            锛圖isplayMetrics绫讳腑灞炴?density锛?
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 灏哾ip鎴杁p鍊艰浆鎹负px鍊硷紝淇濊瘉灏哄澶у皬涓嶅彉
	 * 
	 * @param dipValue
	 * @param scale
	 *            锛圖isplayMetrics绫讳腑灞炴?density锛?
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 灏唒x鍊艰浆鎹负sp鍊硷紝淇濊瘉鏂囧瓧澶у皬涓嶅彉
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            锛圖isplayMetrics绫讳腑灞炴?scaledDensity锛?
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 灏唖p鍊艰浆鎹负px鍊硷紝淇濊瘉鏂囧瓧澶у皬涓嶅彉
	 * 
	 * @param spValue
	 * @param fontScale
	 *            锛圖isplayMetrics绫讳腑灞炴?scaledDensity锛?
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 鑾峰彇鐘舵?鏍忛珮搴?
	 * 
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		java.lang.reflect.Field field = null;
		int x = 0;
		int statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
			return statusBarHeight;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusBarHeight;
	}

}
