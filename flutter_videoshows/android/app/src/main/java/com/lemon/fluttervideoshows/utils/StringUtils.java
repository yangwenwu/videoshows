package com.lemon.fluttervideoshows.utils;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串工具类
 * 
 * @author mingsong.zhang
 * @date 2012-08-07
 */
@SuppressLint("SimpleDateFormat")
public class StringUtils {

	/**
	 * 将字符串数组拼装成用","隔开的字符串,
	 * 
	 * @return 需要返回的字符串
	 * @paramstr 需要拼装的数组
	 */
	public static String getLinkString(String[] array) {
		String str = "";
		StringBuilder sb = new StringBuilder();
		if (null != array && array.length > 0) {
			for (String s : array) {
				sb.append(s).append(",");
			}
			str = sb.toString();
		}
		if (str.indexOf(",") != -1) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	/**
	 * 将字符串数组拼装成用","隔开的字符串,
	 * 
	 * @return 需要返回的字符串
	 * @paramstr 需要拼装的数组
	 */
	public static String getLinkString(Object[] array) {
		String str = "";
		StringBuilder sb = new StringBuilder();
		if (null != array && array.length > 0) {
			for (Object s : array) {
				sb.append(s.toString()).append(",");
			}
			str = sb.toString();
		}
		if (str.indexOf(",") != -1) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	/**
	 * 将字符串List拼装成用"'1','2','3'"隔开的字符串,
	 * 
	 * @return 需要返回的字符串
	 * @paramstr 需要拼装的List
	 * @author yaoyuan
	 */
	public static String getSqlInClauseByList(String[] strArray) {
		String str = "";
		StringBuffer sb = new StringBuffer();
		if (null != strArray && strArray.length > 0) {
			for (String s : strArray) {
				sb.append("'");
				sb.append(s);
				sb.append("'");
				sb.append(",");
			}
			str = sb.toString();
		}
		if (str.indexOf(",") != -1) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	/**
	 * 判断字符是否有内容 为空则返回true
	 **/
	public static boolean isEmpty(String src) {
		if (src == null || src.trim().length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * 判断字符是否有内容 不为空返回true
	 **/
	public static boolean isNotEmpty(String src) {
		return !isEmpty(src);
	}

	/**
	 * 将数字格式化为指定的格式的字符 例如pattern为“00000000” ，number为1 则返回00000001
	 * 
	 * @param pattern
	 *            格式模板
	 * @param number
	 *            需要格式的数字
	 * @return 格式化后的字符
	 */
	public static String formatNumber(String pattern, long number) {
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(number);
	}

	public static String decodingFromISO8858_1(String str) throws UnsupportedEncodingException {
		if (StringUtils.isEmpty(str))
			return "";
		return new String(str.getBytes("ISO8859-1"));
	}

	/**
	 * 去掉字符串中html代码 2009-09-01
	 * 
	 * @param htmlstr
	 * @return
	 * @author
	 */

	public static String removeHtmlTag(String htmlstr) {

		if (!"".equals(htmlstr) && htmlstr != null) {
			Pattern pat = Pattern.compile("\\s*<.*?>\\s*", Pattern.DOTALL | Pattern.MULTILINE
					| Pattern.CASE_INSENSITIVE);
			Matcher m = pat.matcher(htmlstr);
			// 去掉所有html标记
			String rs = m.replaceAll("");
			rs = rs.replaceAll("&nbsp;", " ");
			rs = rs.replaceAll("&lt;", "<");
			rs = rs.replaceAll("&gt;", ">");
			return rs;
		} else {
			return "";
		}

	}

	/**
	 * 瀑布流内容去html标签
	 * 
	 * @param htmlstr
	 * @return
	 */
	public static String removeHtmlFlow(String htmlstr) {

		if (!"".equals(htmlstr) && htmlstr != null) {
			// 去头尾的\n
			Pattern pat = Pattern.compile("\\s*<.*?>\\s*", Pattern.DOTALL | Pattern.MULTILINE
					| Pattern.CASE_INSENSITIVE);
			Matcher m = pat.matcher(htmlstr);
			// 去掉所有html标记“”
			String rs = m.replaceAll("");
			rs = rs.replaceAll("\n", "<br>     ");
			return rs;
		} else {
			return "";
		}

	}

	/**
	 * 瀑布流内容去html标签
	 * 
	 * @param htmlstr
	 * @return
	 */
	public static String removeNFlow(String htmlstr) {
		if (StringUtils.isNotEmpty(htmlstr)) {
			if (htmlstr.startsWith("\n")) {
				htmlstr = htmlstr.replaceFirst("\n", "");
			}
			htmlstr = htmlstr.replaceAll("\n", "<br>     ");
			return htmlstr;
		} else {
			return "";
		}

	}

	/**
	 * 把s中所有为oldS替换掉 2008-10-25
	 * 
	 * @param s
	 *            需要替换的字符串
	 * @param oldS
	 *            s中旧的字符串
	 * @param newS
	 *            代替oldS的字符串
	 * @return 返回替换掉后的新字符串
	 * @yaoyuan
	 */
	public static String replace(String s, String oldS, String newS) {
		StringBuffer buf = new StringBuffer();
		int i = 0;

		while (true) {
			int pos = s.indexOf(oldS, i);

			if (pos == -1) {
				break;
			}

			buf.append(s.substring(i, pos));
			buf.append(newS);
			i = pos + oldS.length();

			if (i >= s.length()) {
				break;
			}
		}

		buf.append(s.substring(i));

		return buf.toString();
	}

	/**
	 * 把数字转换成多少位的字段串
	 * <p/>
	 * e.g. add0(36, 6) will return 000036 NOTES: result is undetermine if
	 * number of decimal of given integer is large then desired length of
	 * string.
	 * 
	 * @param v
	 *            需要转换的数字
	 * @param l
	 *            转换的长度
	 * @return the result.
	 */
	public static String add0(int v, int l) {
		long lv = (long) Math.pow(10, l);
		return String.valueOf(lv + v).substring(1);
	}

	/**
	 * 字符解码（UTF-8） Dec 1, 2008
	 * 
	 * @param src
	 * @return
	 * @author
	 */
	public static String urlDecode(String src) {
		try {
			return URLDecoder.decode(src, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * 直接出现在URL中要进行编码 2008-12-23
	 * 
	 * @param src
	 * @return
	 * @author
	 */
	public static String urlEncode(String src) {
		try {
			return URLEncoder.encode(src, "UTF-8");
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 把null转为""
	 * <p/>
	 * 2009-3-21
	 * 
	 * @return
	 * @yaoyuan
	 * @paramvalue
	 */
	public static String convertNullValue(String val) {
		if (null == val)
			return "";
		return val;
	}

	/**
	 * 取得当前操作系统的行分割符 2009-5-13
	 * 
	 * @return
	 * @author yaoyuan
	 */
	public static String getLineSeparator() {
		return System.getProperty("line.separator");
	}

	/**
	 * 把String 是否为数字
	 * <p/>
	 * 2009-8-20
	 * 
	 * @param val
	 * @return
	 * @yaoyuan
	 */
	public static boolean isNumber(String val) {
		try {
			Double.parseDouble(val);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 将iso-88591编码转换成utf8格式
	 * 
	 * @author LQY 2011-5-6
	 */
	public static String convert88591Toutf8(String strSrc) {
		try {
			return new String(strSrc.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * 获取接口url的字符串
	 * 
	 * @param strs
	 * @return
	 */
	public static String getUrlString(String[] strs) {
		StringBuffer strBuffer = new StringBuffer();
		if (strs != null) {
			int len = strs.length;
			for (int i = 0; i < len; i++) {
				strBuffer.append(strs[i]);
			}
			return strBuffer.toString();
		}

		return null;
	}

	/**
	 * 根据字符串分钟时间转化成毫秒数
	 * 
	 * @param minTime
	 * @return
	 */
	public static long getMillisFromMin(String minTime) {
		if (StringUtils.isEmpty(minTime))
			return 0;
		else
			return Integer.parseInt(minTime) * 60000;
	}

	/**
	 * 获取url资源名字 包括后缀 eg:http://www.baidu.com/abc/d.jpg 取d.jpg
	 * 
	 * @param url
	 * @return 抛异常则返回""
	 * @author mingsong.zhang
	 */
	public static String getNameFromUrl(String url) {
		String name = "";
		try {
			if (isNotEmpty(url)) {
				name = url.substring(url.lastIndexOf("/") + 1);
			}

		} catch (Exception e) {
			return "";
		}
		return name;
	}

	/**
	 * 获取url资源名字 不包括后缀 eg:http://www.baidu.com/abc/d.jpg 取d
	 * 
	 * @param url
	 * @return 抛异常则返回""
	 * @author mingsong.zhang
	 */
	public static String getNameFromUrlWithoutPostfix(String url) {
		String name = "";
		try {
			if (isNotEmpty(url)) {

				name = url.substring((url.lastIndexOf("/") + 1), url.lastIndexOf("."));
			}
		} catch (Exception e) {
			return "";
		}
		return name;
	}

	/**
	 * 计算资讯发布时间与当前时间相差多少分钟或小时或天数,并返回
	 * 
	 * @param time
	 * @return 失败则返回""
	 */
	public static String differTime(String time) {

		if (isNotEmpty(time)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = (Date) sdf.parse(time);
				long theTime = date.getTime();
				long currentTime = System.currentTimeMillis();
				long differTime = currentTime - theTime;
				long differ = 0;
				if ((differ = differTime / 60000) < 60) {
					return differ + "分钟前";
					// return "";
				} else if ((differ = differTime / 3600000) < 24) {
					return differ + "小时前";
					// return "";
				} else if ((differ = differTime / 86400000) >= 1 && (differ = differTime / 86400000) <= 3) {
					return differ + "天前";
				} else if ((differ = differTime / 86400000) > 3) {
					return time.substring(5, time.lastIndexOf("-") + 3);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return "";
	}

	/**
	 * 将字符串按","分开成字符串数组
	 * 
	 * @param str
	 * @return 字符串为空或者报异常则返回null
	 */
	public static String[] splitStringComma(String str) {
		String[] strs = null;
		try {
			if (isNotEmpty(str)) {
				strs = str.split(",");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strs;
	}

	/**
	 * 得到当前系统时间的字符串表示 格式为“XX小时前“
	 * 
	 * @param m
	 * @return
	 */
	public static String getSysTime(long m) {
		long theTime = m;
		long currentTime = System.currentTimeMillis();
		long differTime = currentTime - theTime;
		long differ = 0;
		if ((differ = differTime / 60000) < 60) {
			return differ + "分钟前";
		} else if ((differ = differTime / 3600000) < 24) {
			return differ + "小时前";
		} else if ((differ = differTime / 86400000) >= 1) {
			return differ + "天前";
		}
		return "";
	}

	/**
	 * 获取最高温度
	 * 
	 * @param temp
	 * @return
	 */
	public static String getWeatherHighTemp(String temp) {
		String str = "";
		if (isNotEmpty(temp)) {
			try {
				str = temp.substring(temp.indexOf("~") + 1, temp.length() - 1) + "°";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	/**
	 * 将字符串中的中文字符转换成utf-8编码
	 * 
	 * @param imgUrl
	 * @return
	 * @author mingsong.zhang
	 */
	public static String encodeUrl(String imgUrl) {
		if (isNotEmpty(imgUrl)) {
			String encodeUrl = imgUrl;
			ArrayList<Character> chs = new ArrayList<Character>(5);
			for (int i = 0; i < imgUrl.length(); i++) {
				char ch = imgUrl.charAt(i);
				int v = (int) ch;
				if (v >= 19968 && v <= 171941) {
					chs.add(ch);
				}
			}

			for (Character character : chs) {
				try {
					encodeUrl = encodeUrl.replace(character.toString(),
							URLEncoder.encode(character.toString(), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			return encodeUrl;
		}

		return "";
	}

	/**
	 * 正则表达式检查字符串是否为email格式
	 * 
	 * @param email
	 * @return
	 * @author blueming.wu
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 正则表达式验证字符串是否为电话号码
	 * 
	 * @param mobiles
	 * @return
	 * @author blueming.wu
	 */
	public static boolean checkMobile(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证密码是否符合规则，规则为6-13为数字字母组合
	 * 
	 * @param pwd
	 * @return
	 * @author blueming.wu
	 */
	public static boolean checkPwd(String pwd) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^[a-z0-9A-Z]{6,13}$");
			Matcher m = p.matcher(pwd);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证密码是否包含非法字符
	 * 
	 * @param pwd
	 * @return
	 * @author blueming.wu
	 */
	public static boolean checkPwdCharacter(String pwd) {
		boolean flag = false;
		try {

			Pattern p = Pattern.compile("^[a-z0-9A-Z]+$");
			Matcher m = p.matcher(pwd);

			flag = m.matches();

		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 将url转换成md5码加密保存
	 * 
	 * @param key
	 * @return
	 */
	public static String generator(String key) {
		String cacheKey;
		try {
			// 创建指定算法
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}

	/**
	 * 字符数组转换为16进制字符串
	 * 
	 * @param bytes
	 * @return
	 */
	private static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	/**
	 * 设置数值保留两位小数显示,四舍五入
	 * 
	 * @param num
	 * @return
	 */
	public static String setSizetoDoble(double num) {

		BigDecimal mData = new BigDecimal(num).setScale(2, BigDecimal.ROUND_HALF_UP);
		return mData.toString();

	}

	/**
	 * 验证是否只包含数字，字母，下划线，汉字
	 * 
	 * @param content
	 * @return
	 */
	public static boolean checkInput(String content) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^[a-zA-Z0-9_\u4e00-\u9fa5]+$");
			Matcher m = p.matcher(content);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 将字符串集合拼成以","隔开的字符串
	 * 
	 * @param list
	 * @return
	 */
	public static String getNewString(ArrayList<String> list) {
		String str = "";
		StringBuilder sb = new StringBuilder();
		if (null != list && list.size() > 0) {
			for (String s : list) {
				sb.append(s).append(",");
			}
			str = sb.toString();
		}
		if (str.indexOf(",") != -1) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	/**
	 * 根据Url获取保存apk的名字
	 * 
	 * @param url
	 * @return
	 */
	public static String getFileNameFromUrl(String url) {

		// 名字不能只用这个
		// 通过 ‘？’ 和 ‘/’ 判断文件名
		String extName = ".apk";
		String filename;

		filename = hashKeyForDisk(url) + "." + extName;
		return filename;
	}

	public static String getApkNameFromUrl(String url) {
		if (TextUtils.isEmpty(url) || url.length() < 4) {
			return "update" + new Random().nextInt(10000) + ".apk";
		}

		if (url.contains("/") && url.contains(".apk")) {
			int fileNamePosition = url.lastIndexOf('/');
			return url.substring(fileNamePosition + 1);
		}

		return hashKeyForDisk(url) + ".apk";
	}

	/**
	 * 一个散列方法,改变一个字符串(如URL)到一个散列适合使用作为一个磁盘文件名。
	 */
	public static String hashKeyForDisk(String key) {
		String cacheKey;
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}

	/**
	 * 去掉字符串中html代码 给tts语音朗读
	 * 
	 * @param htmlstr
	 * @return
	 * @author
	 */

	public static String removeHtmlCssForTts(String htmlstr) {

		if (!"".equals(htmlstr) && htmlstr != null) {
			Pattern pat = Pattern.compile("\\s*<.*?>\\s*", Pattern.DOTALL | Pattern.MULTILINE
					| Pattern.CASE_INSENSITIVE);
			Matcher m = pat.matcher(htmlstr);
			// 去掉所有html标记
			String rs = m.replaceAll("");
			rs = rs.replaceAll("&nbsp", "");
			rs = rs.replaceAll("&lt;", "<");
			rs = rs.replaceAll("&gt;", ">");
			rs = rs.substring(rs.lastIndexOf("false") + 7);
			rs = rs.trim();
			return rs;
		} else {
			return "";
		}

	}

	/**
	 * 判断是否为http连接地址
	 * 
	 * @param http
	 */
	public static boolean httpJudge(String http) {

		Pattern pattern = Pattern.compile("http://(([a-zA-z0-9]|-){1,}\\.){1,}[a-zA-z0-9]{1,}-*");
		Matcher matcher = pattern.matcher(http);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 年月日时间格式
	 * 
	 * @param time
	 * @return
	 */
	public static String formatTimeAd(long time) {
		if (time == 0)
			return "";
		time = time * 1000; // 转换为毫秒
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(time);
		String altertime = sdf.format(time) + "       " + getWeek(date);
		return altertime;
	}

	// 根据日期取得星期几
	public static String getWeek(Date date) {
		String[] weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week_index < 0) {
			week_index = 0;
		}
		return weeks[week_index];
	}

	/**
	 * 瀑布流用
	 * 
	 * @param time
	 * @return
	 */
	public static String waterwallTime(long time) {
		if (time == 0)
			return "";
		long currentTime = System.currentTimeMillis();
		long differTime = currentTime - time * 1000;
		long differ = 0;
		StringBuilder sb = new StringBuilder();

		if (differTime > 0) {

			if ((differ = differTime / 60000) < 60) {
				differ = differ == 0 ? 1 : differ;
				return sb.append(differ).append("分钟前").toString();

			} else if ((differ = differTime / 3600000) < 24) {
				return sb.append(differ).append("小时前").toString();
			}
		} else {
			return "";
		}

		return "";
	}

	/**
	 * 评论页面
	 * 
	 * @param time
	 * @return
	 */
	public static String commentTime(long time) {
		if (time == 0)
			return "";
		try {
			time = time * 1000; // 转换为毫秒
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			long currentTime = System.currentTimeMillis();
			long differTime = currentTime - time;
			int differ = 0;
			StringBuilder sb = new StringBuilder();
			if (differTime > 0 || differTime < 0 || differTime == 0) {

				if ((differ = (int) (differTime / 60000)) <= 1) {
					return "刚刚";
				} else if ((differ = (int) (differTime / 60000)) < 60) {
					return sb.append(differ).append("分钟前").toString();
				} else if ((differ = (int) (differTime / 3600000)) < 24) {
					return sb.append(differ).append("小时前").toString();
				} else if ((int) (differTime / (24 * 60 * 60 * 1000)) < 365) {
					return sdf1.format(time);
				} else {
					return sdf2.format(time);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}

	/**
	 * 瀑布流用
	 * 
	 * @param time
	 * @return
	 */
	public static String waterwallRssTime(long time) {
		if (time == 0)
			return "";
		long currentTime = System.currentTimeMillis();
		long differTime = currentTime - time * 1000;
		long differ = 0;
		if (differTime > 0) {
			if ((differ = differTime / 60000) < 60) {
				differ = differ == 0 ? 1 : differ;
				return differ + "分钟前";

			} else if ((differ = differTime / 3600000) < 24) {
				return differ + "小时前";

			} else if ((differ = differTime / 86400000) >= 1) {
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
				return sdf.format(new Date(time * 1000));
			}
		} else {
			return "";
		}

		return "";
	}

	/**
	 * 瀑布流用
	 * 
	 * @param time
	 * @return
	 */
	public static String waterwallTime(String time) {

		if (isNotEmpty(time)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date date = (Date) sdf.parse(time);
				long theTime = date.getTime();
				return waterwallTime(theTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return "";
	}

	/**
	 * 瀑布流微博用
	 * 
	 * @param time
	 * @return
	 */
	public static String waterwallTimeForWeibo(String time) {

		if (isNotEmpty(time)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
				Date date = (Date) sdf.parse(time);
				long theTime = date.getTime();
				long currentTime = System.currentTimeMillis();
				long differTime = currentTime - theTime;
				long differ = 0;
				if ((differ = differTime / 60000) < 60) {
					differ = differ == 0 ? 1 : differ;
					return differ + "分钟前";

				} else if ((differ = differTime / 3600000) < 24) {
					return differ + "小时前";

				} else if ((differ = differTime / 86400000) >= 1 && (differ = differTime / 86400000) <= 3) {
					return differ + "天前";
				} else if ((differ = differTime / 86400000) > 3) {
					// return time.substring(5, time.lastIndexOf("-") + 3);
					return "";
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return "";
	}

	/**
	 * 瀑布流微博用
	 * 
	 * @param time
	 * @return
	 */
	public static String waterwallTimeForWeibo(long time) {

		long currentTime = System.currentTimeMillis();
		long differTime = currentTime - time;

		long differ = 0;
		if ((differ = differTime / 60000) < 60) {
			differ = differ == 0 ? 1 : differ;
			return differ + "分钟前";

		} else if ((differ = differTime / 3600000) < 24) {
			return differ + "小时前";

		} else if ((differ = differTime / 86400000) >= 1 && (differ = differTime / 86400000) <= 3) {
			return differ + "天前";
		} else if ((differ = differTime / 86400000) > 3) {
			// return time.substring(5, time.lastIndexOf("-") + 3);
			return "";
		}

		return "";
	}

	/**
	 * 将字符串写到文件中
	 * 
	 * @param jsonStr
	 *            需要写在文件中的字符串
	 * @param path
	 *            文件路径
	 * @throws IOException
	 */
	public static synchronized void saveJsonStrToFile(String jsonStr, String path) throws IOException {
		if (sdCardExist() && StringUtils.isNotEmpty(path)) {

			// sd卡满
			if (BitmapUtils.getUsableSpace(Environment.getExternalStorageDirectory()) == 0) {
				return;
			}
			File file = new File(path);

			File fileTemp = new File(file.getParent());
			if (!fileTemp.exists()) {
				fileTemp.mkdirs();
			}
			file.createNewFile();

			if (file.exists()) {
				BufferedWriter bw = null;
				try {
					bw = new BufferedWriter(new FileWriter(path));
					bw.write(jsonStr);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (null != bw)
						bw.close();
				}
			}

		}

	}

	/**
	 * 判断sd卡是否存在
	 *
	 * @return true:存在；false:不存在
	 */
	public static boolean sdCardExist() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取json格式文件中的字符串
	 *
	 * @param path
	 *            该文件的路径
	 * @return
	 * @throws IOException
	 */

	public static String getJsonString(String path) {
		String jsonStr = "";
		BufferedReader br = null;
		try {
			File file = new File(path);

			if (!file.exists()) {
				return jsonStr;
			}

			br = new BufferedReader(new FileReader(file));
			jsonStr = br.readLine();
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			try {
				if (null != br)
					br.close();
			} catch (IOException e) {
				return null;
			}
		}
		return jsonStr;
	}

	/**
	 * 根据key获取value
	 * 该方法适用于简单的格式:{"smsnumber":"13128896155","clientId":"100000","city ":"深圳"}
	 *
	 * @param key
	 *            要查询的key
	 * @param path
	 *            eg:该的路径
	 * @return 返回查询到的值，没有该值则返回""
	 * @throws IOException
	 * @date 2014-1-4
	 */
	public static String getValue(String key, String path) {
		String value = ""; // 需要返回的值
		if (sdCardExist() && StringUtils.isNotEmpty(path)) {
			try {
				String jsonStr = getJsonString(path); // 获取json格式文件中的内容
				if (StringUtils.isNotEmpty(jsonStr)) {
					JSONObject dataJson = new JSONObject(jsonStr);
					if (dataJson.has(key)) {
						value = dataJson.getString(key);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return "";
			}
		}
		return value;
	}

	/**
	 * 保存value
	 * 该方法适用于简单的格式:{"smsnumber":"13128896155","clientId":"100000","city ":"深圳"}
	 *
	 * @param key
	 *            key名
	 * @param value
	 *            key对应的值
	 * @param path
	 *            eg:该文件的路径:sdcard/FlyShare/datatxt/camn.txt
	 * @throws IOException
	 */
	public static synchronized void putValue(String key, String value, String path) throws IOException {
		if (sdCardExist() && StringUtils.isNotEmpty(path)) {
			// sd卡满
			if (BitmapUtils.getUsableSpace(Environment.getExternalStorageDirectory()) == 0) {
				return;
			}

			File file = new File(path);

			File fileTemp = new File(file.getParent());
			if (!fileTemp.exists()) {
				fileTemp.mkdirs();
			}
			file.createNewFile();

			if (file.exists()) {

				BufferedWriter bw = null;
				JSONObject dataJson = new JSONObject();
				try {
					String jsonStr = getJsonString(path);

					/** 假如该json格式文件中已有内容则重新实例化JSONObject **/
					if (StringUtils.isNotEmpty(jsonStr)) {
						dataJson = new JSONObject(jsonStr);
					}
					/** 将新的键值对加进来 **/
					dataJson.put(key, value);

					bw = new BufferedWriter(new FileWriter(path));
					String result = dataJson.toString();
					bw.write(result);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (null != bw) {
						bw.flush();
						bw.close();
					}
				}

			}

		}

	}

	/**
	 * 同时保存多个value
	 * 该方法适用于简单的格式:{"smsnumber":"13128896155","clientId":"100000","city ":"深圳"}
	 *
	 * @param path
	 *            eg:该文件的路径:sdcard/FlyShare/datatxt/camn.txt
	 * @throws IOException
	 * @paramkey key名
	 * @paramvalue key对应的值
	 */
	public static synchronized void putValueS(HashMap<String, String> hashMap, String path) throws IOException {

		if (sdCardExist() && StringUtils.isNotEmpty(path)) {

			// sd卡满
			if (BitmapUtils.getUsableSpace(Environment.getExternalStorageDirectory()) == 0) {
				return;
			}
			File file = new File(path);

			File fileTemp = new File(file.getParent());
			if (!fileTemp.exists()) {
				fileTemp.mkdirs();
			}
			file.createNewFile();

			if (file.exists()) {

				BufferedWriter bw = null;
				JSONObject dataJson = new JSONObject();
				try {
					String jsonStr = getJsonString(path);

					/** 假如该json格式文件中已有内容则重新实例化JSONObject **/
					if (StringUtils.isNotEmpty(jsonStr)) {
						dataJson = new JSONObject(jsonStr);
					}
					/** 将新的键值对加进来 **/
					Set<Map.Entry<String, String>> set = hashMap.entrySet();
					for (Map.Entry<String, String> entry : set) {
						dataJson.put(entry.getKey(), entry.getValue());
					}

					bw = new BufferedWriter(new FileWriter(path));
					String result = dataJson.toString();
					bw.write(result);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (null != bw) {
						bw.flush();
						bw.close();
					}
				}

			}
		}

	}

	public static synchronized void saveJsonTocache(String json, String path) throws IOException {

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(path));
			bw.write(json);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != bw)
				bw.close();
		}

	}

	/**
	 * 将秒格式化成yyyy-MM-dd
	 * 
	 * @param time
	 * @return
	 */
	public static String formatTime(long time) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(time * 1000);
		return sdf.format(date);

	}

	/**
	 * 比较字符串
	 */
	private static int compare(String str, String target) {

		int d[][]; // 矩阵

		int n = str.length();

		int m = target.length();

		int i; // 遍历str的

		int j; // 遍历target的

		char ch1; // str的

		char ch2; // target的

		int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1

		if (n == 0) {

			return m;

		}

		if (m == 0) {

			return n;

		}

		d = new int[n + 1][m + 1];

		for (i = 0; i <= n; i++) { // 初始化第一列

			d[i][0] = i;

		}

		for (j = 0; j <= m; j++) { // 初始化第一行

			d[0][j] = j;

		}

		for (i = 1; i <= n; i++) { // 遍历str

			ch1 = str.charAt(i - 1);

			// 去匹配target

			for (j = 1; j <= m; j++) {

				ch2 = target.charAt(j - 1);

				if (ch1 == ch2) {

					temp = 0;

				} else {

					temp = 1;

				}

				// 左边+1,上边+1, 左上角+temp取最小

				d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1]

				+ temp);

			}

		}

		return d[n][m];

	}

	private static int min(int one, int two, int three) {

		return (one = one < two ? one : two) < three ? one : three;

	}

	/**
	 * 获取两字符串的相似度
	 * 
	 * @param str
	 * @param target
	 * @return
	 */

	public static float getSimilarityRatio(String str, String target) {

		return 1 - (float) compare(str, target)

		/ Math.max(str.length(), target.length());

	}

	/**
	 * 传入评论数目 返回如下设置格式 超过一千显示X.X千、 超过一万显示X.X万、 超过十万显示XX万 超过百万显示XXX万
	 * 
	 * @param num
	 * @return
	 */
	public static String getNum(int num) {
		StringBuilder sb = new StringBuilder();
		if (num < 0) {
			num = 0;
		}
		if (num >= 1000 && num < 1000 * 10) {
			sb.append(num / 1000).append(".").append(num % 1000 / 100).append("k");
			return sb.toString();
		} else if (num >= 1000 * 10 && num < 1000 * 100) {
			sb.append(num / 10000).append(".").append(num % 10000 / 1000).append("w");
			return sb.toString();
		} else if (num >= 1000 * 100) {
			sb.append(num / 10000).append(".").append(num % 10000 / 1000).append("w");
			return sb.toString();
		} else {
			return String.valueOf(num);
		}
	}

	/**
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 * @Title: getUpDataRules
	 * @Description: 上传数据时，字符串不能包涵“&”，全部转换成“*”
	 */
	public static String getUpDataRules(String str) {
		if (isEmpty(str)) {
			return "";
		}
		if (str.contains("&")) {
			return str.replaceAll("&", "*");
		} else {
			return str;
		}
	}

	/**
	 * 得到imageview控件内容长度
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private static int getImageViewFieldValue(Object object, String fieldName) {
		int value = 0;
		if (object instanceof ImageView) {
			try {
				Field field = ImageView.class.getDeclaredField(fieldName);
				field.setAccessible(true);
				int fieldValue = (Integer) field.get(object);
				if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
					value = fieldValue;
				}
			} catch (Throwable e) {
			}
		}
		return value;
	}

	/**
	 * 检测非法字符
	 * 
	 * @param para
	 * @return
	 */
	public static boolean checkParameter(String para) {
		int flag = 0;
		flag += para.indexOf("'") + 1;
		flag += para.indexOf(";") + 1;
		flag += para.indexOf("1=1") + 1;
		flag += para.indexOf("|") + 1;
		flag += para.indexOf("<") + 1;
		flag += para.indexOf(">") + 1;
		if (flag != 0) {
			return false;
		}
		return true;
	}
}
