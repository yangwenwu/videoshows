package com.lemon.video.utils;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

/**
 * 获取手机SIM卡信息的工具类 需要权限：android.permission.READ_PHONE_STATE
 * 
 * @author mingsong.zhang
 * @date 20120627
 */
public class TelephonyManagerUtils {

	/**
	 * 唯一的设备ID：<br/>
	 * 如果是GSM网络，返回IMEI；如果是CDMA网络，返回MEID<br/>
	 * 需要权限：android.permission.READ_PHONE_STATE
	 * 
	 * @return null if device ID is not available.
	 */
	public static String getImei(Context context) {

		String imei = (String) SPUtils.get(context, "imei", "");
		if (StringUtils.isEmpty(imei)) {
			TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

			imei = telMgr.getDeviceId();
			SPUtils.put(context, "imei", imei);

		}
		return imei;
	}

	/**
	 * 获取mac地址 需要权限：android.permission.READ_PHONE_STATE
	 *
	 * @return null if device ID is not available.
	 */
	public static String getMacAddress(Context context) {

		String mac = (String) SPUtils.get(context, "mac", "");
		if (StringUtils.isEmpty(mac)) {
			// tencent过滤了获取服务WIFI_SERVICE，必须获取app的context来获取
			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

			mac = wifiManager.getConnectionInfo().getMacAddress();
			SPUtils.put(context, "mac", mac);

		}
		return mac;
	}

	/**
	 * 获取运营商信息
	 *
	 * @return null if device ID is not available.
	 */
	public static String getProvidersName(Context context) {

		String providersName = (String) SPUtils.get(context, "providersName", "");
		if (StringUtils.isEmpty(providersName)) {
			TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			// 返回唯一的用户ID;就是这张卡的编号神马的
			String IMSI = telMgr.getSubscriberId();
			// 可能为null
			if (StringUtils.isNotEmpty(IMSI)) {
				// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
				if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
					providersName = "cmcc";
				} else if (IMSI.startsWith("46001")) {
					// 联通
					providersName = "cucc";
				} else if (IMSI.startsWith("46003")) {
					// 电信
					providersName = "ctcc";
				}
			}

			return providersName;

		}
		return providersName;
	}

	/**
	 * 获取手机号码
	 *
	 * @param context
	 * @return
	 */
	public static String getMobileNumber(Context context) {
		TelephonyManager tmManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String telString = tmManager.getLine1Number();
		if (StringUtils.isEmpty(telString))
			telString = "";
		return telString;
	}

	/**
	 * 获取ram总内存大小
	 */
	public static float getTotalMemory() {
		String str1 = "/proc/meminfo";// 系统内存信息文件
		String str2;
		String[] arrayOfString;
		float initial_memory = 0;
		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8);
			str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
			arrayOfString = str2.split("\\s+");
			initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
			localBufferedReader.close();
		} catch (IOException e) {
		}
		// Byte转换为GB，内存大小规格化
		return initial_memory / (1024 * 1024 * 1024);
	}

	/**
	 * 获取当前手机系统语言。
	 *
	 * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
	 */
	public static String getSystemLanguage() {
		return Locale.getDefault().getLanguage();
	}

	/**
	 * 获取当前系统上的语言列表(Locale列表)
	 *
	 * @return  语言列表
	 */
	public static Locale[] getSystemLanguageList() {
		return Locale.getAvailableLocales();
	}

	/**
	 * 获取当前手机系统版本号
	 *
	 * @return  系统版本号
	 */
	public static String getSystemVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获取手机型号
	 *
	 * @return  手机型号
	 */
	public static String getSystemModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获取手机厂商
	 *
	 * @return  手机厂商
	 */
	public static String getDeviceBrand() {
		return android.os.Build.BRAND;
	}

	/**
	 * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
	 *
	 * @return  手机IMEI
	 */
	public static String getIMEI(Context ctx) {
		TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
		if (tm != null) {
			return tm.getDeviceId();
		}
		return null;
	}
}
