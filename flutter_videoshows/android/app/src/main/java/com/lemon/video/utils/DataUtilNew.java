package com.lemon.video.utils;

import android.content.Context;

import com.lemon.video.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

public class DataUtilNew {

	/**
	 * 计算时间差
	 *
	 * @param starTime
	 *            开始时间
	 *
	 *       当前时间是     结束时间
	 *
	 *  返回类型 ==1----天，时，分。 ==2----时
	 * @return 返回时间差
	 */
//    public String getTimeDifference(String starTime, String endTime) {
	public static String getTimeDifference(String starTime) {
		String timeString = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			Date parse = dateFormat.parse(starTime);
			Date parse1 = new Date(System.currentTimeMillis());

			long diff = parse1.getTime() - parse.getTime();

			long day = diff / (24 * 60 * 60 * 1000);
			long hour = (diff / (60 * 60 * 1000) - day * 24);
			long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
					- min * 60 * 1000 - s * 1000);
			// System.out.println(day + "天" + hour + "小时" + min + "分" + s +
			// "秒");
			long hour1 = diff / (60 * 60 * 1000);
			String hourString = hour1 + "";
			long min1 = ((diff / (60 * 1000)) - hour1 * 60);
			timeString = hour1 + "小时" + min1 + "分";
			// System.out.println(day + "天" + hour + "小时" + min + "分" + s +
			// "秒");

			if (  360>day && day >= 330){
				timeString =  "11 Months ago";
			}else if (330>day && day >= 300){
				timeString =  "10 Months ago";
			}else if (300>day && day >= 270){
				timeString =  "9 Months ago";
			}else if (270>day && day >= 240){
				timeString =  "8 Months ago";
			}else if ( 240>day && day >= 210){
				timeString =  "7 Months ago";
			}else if ( 210>day && day >= 180){
				timeString =  "6 Months ago";
			}else if ( 180>day && day >= 150){
				timeString =  "5 Months ago";
			}else if ( 150>day && day >= 120){
				timeString =  "4 Months ago";
			}else if ( 120>day && day >= 90){
				timeString =  "3 Months ago";
			}else if ( 90>day && day >= 60){
				timeString =  "2 Months ago";
			}else if ( 60>day && day >= 30){
				timeString =  "1 Month ago";
			}else if (30>day && day >= 28){
				timeString =  "4 Weeks ago";
			}else if (28 >day && day >= 21){
				timeString =  "3 Weeks ago";
			}else if (21 >day && day >= 14){
				timeString =  "2 Weeks ago";
			}else if ( 14 > day && day >=7){
				timeString =  "1 Weeks ago";
			}else if(day>=1 && 7 > day){
				if (day == 1){
					timeString =  day +" day ago";
				}else{
					timeString =  day +" days ago";
				}
			}else if (hour >=1 && 23>= hour){
				if (hour == 1){
					timeString =  hour1+" Hour ago";
				}else{
					timeString =  hour1+" Hours ago";
				}
			}else if (  min >= 1  && 59>min){
				if (min >= 1 && 3 >= min){
					timeString =  "Just";
				}else{
					timeString =  min+" Mins ago";
				}

			}else if (5<s && s<=59){
//				timeString =  s+" seconds ago";
				timeString =  "Just";
			}else if (s <= 5){
//				timeString = "刚刚";
				timeString = "Just";
			}else{
				timeString = starTime;
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return timeString;

	}


	//比较时间差  天，小时，分钟
	public static String getDateDifferentValue(Context context, String date){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String notice = null ;
		try{
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());
			String currentDate = sDateFormat.format(curDate);
			Date d1 = df.parse(date.substring(0,19));
			Date d2 = df.parse(currentDate);//当前的时间
			long diff = d2.getTime() - d1.getTime();//这样得到的差值是微秒级别
			long days = diff / (1000 * 60 * 60 * 24);
			long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
			long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
			//用户没有修改时间，那就是正数，如果修改了有可能是负数
			if (diff > 0){
				//1.天数大于1
				if (days >= 1){
					String ymd = date.substring(0,10);  //2016-08-22
					String y = ymd.substring(0,4);
					String m = ymd.substring(5,7);
					String d = ymd.substring(8,10);
					notice = ymd;
				}else {
					//天数小于等于1,也就是24小时内
					if (hours >= 1 && hours < 2){
						notice = "1"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 2 && hours < 3){
						notice = "2"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 3 && hours < 4){
						notice = "3"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 4 && hours < 5){
						notice = "4"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 5 && hours < 6){
						notice = "5"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 6 && hours < 7){
						notice = "6"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 7 && hours < 8){
						notice = "7"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 8 && hours < 9){
						notice = "8"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 9 && hours < 10){
						notice = "9"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 10 && hours < 11){
						notice = "10"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 11 && hours < 12){
						notice = "11"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 12 && hours < 13){
						notice = "12"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 13 && hours < 14){
						notice = "13"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 14 && hours < 15){
						notice = "14"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 15 && hours < 16){
						notice = "15"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 16 && hours < 17){
						notice = "16"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 17 && hours < 18){
						notice = "17"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 18 && hours < 19){
						notice = "18"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 19 && hours < 20){
						notice = "19"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 20 && hours < 21){
						notice = "20"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 21 && hours < 22){
						notice = "21"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 22 && hours < 23){
						notice = "22"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours >= 23 && hours < 24){
						notice = "23"+ context.getResources().getString(R.string.time_notice1);
					}else if (hours < 1){
						//小于= 1个小时
						if (minutes <= 1){
							notice = context.getResources().getString(R.string.time_notice2);
						}else{
							notice = minutes +context.getResources().getString(R.string.time_notice3);
						}

					}
				}
			}else{
				//修改了时间，就是负数了，那就是显示日期了
				String ymd = date.substring(0,10);  //2016-08-22
				String y = ymd.substring(0,4);
				String m = ymd.substring(5,7);
				String d = ymd.substring(8,10);
				notice = ymd;
			}


		}catch (Exception e){

		}

		return  notice;
	}
	 /**
	  * 获取现在时间
	  * 
	  * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	  */
	 public static Date getNowDate() {
	  Date currentTime = new Date();
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String dateString = formatter.format(currentTime);
	  ParsePosition pos = new ParsePosition(8);
	  Date currentTime_2 = formatter.parse(dateString, pos);
	  return currentTime_2;
	 }

	 /**
	  * 获取现在时间
	  * 
	  * @return返回短时间格式 yyyy-MM-dd
	  */
	 public static Date getNowDateShort() {
	  Date currentTime = new Date();
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	  String dateString = formatter.format(currentTime);
	  ParsePosition pos = new ParsePosition(8);
	  Date currentTime_2 = formatter.parse(dateString, pos);
	  return currentTime_2;
	 }

	 /**
	  * 获取现在时间
	  * 
	  * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	  */
	 public static String getStringDate() {
	  Date currentTime = new Date();
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String dateString = formatter.format(currentTime);
	  return dateString;
	 }

	 /**
	  * 获取现在时间
	  * 
	  * @return 返回短时间字符串格式yyyy-MM-dd
	  */
	 public static String getStringDateShort() {
	  Date currentTime = new Date();
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	  String dateString = formatter.format(currentTime);
	  return dateString;
	 }

	 /**
	  * 获取时间 小时:分;秒 HH:mm:ss
	  * 
	  * @return
	  */
	 public static String getTimeShort() {
	  SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
	  Date currentTime = new Date();
	  String dateString = formatter.format(currentTime);
	  return dateString;
	 }

	 /**
	  * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	  * 
	  * @param strDate
	  * @return
	  */
	 public static Date strToDateLong(String strDate) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  ParsePosition pos = new ParsePosition(0);
	  Date strtodate = formatter.parse(strDate, pos);
	  return strtodate;
	 }

	 /**
	  * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	  * 
	  * @param dateDate
	  * @return
	  */
	 public static String dateToStrLong(Date dateDate) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String dateString = formatter.format(dateDate);
	  return dateString;
	 }

	 /**
	  * 将短时间格式时间转换为字符串 yyyy-MM-dd
	  * 
	  * @param dateDate
	  * @return
	  */
	 public static String dateToStr(Date dateDate) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	  String dateString = formatter.format(dateDate);
	  return dateString;
	 }
	 /**
	  * 将短时间格式时间转换为字符串 MM/dd  带有截取字符操作
	  * 
	  * @param dateDate
	  * @return
	  */
	 public static String dateToStr2(Date dateDate) {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		 String dateString = formatter.format(dateDate);
		 String rightTime =dateString.subSequence(5, dateString.length()).toString();
		 return rightTime;
	 }
	 
	 /**
	  * 将短时间格式时间转换为字符串 yyyy/MM/dd  
	  * @param dateDate
	  * @return
	  */
	 public static String dateToStr3(Date dateDate) {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		 String dateString = formatter.format(dateDate);
		 return dateString;
	 }
	 
	 /**
	  * 将短时间格式时间转换为字符串 yyyy-MM-dd  
	  * @param dateDate
	  * @return
	  */
	 public static String dateToStr4(Date dateDate) {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		 String dateString = formatter.format(dateDate);
		 return dateString;
	 }

	 /**
	  * 将短时间格式字符串转换为时间 yyyy-MM-dd 
	  * 
	  * @param strDate
	  * @return
	  */
	 public static Date strToDate(String strDate) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	  ParsePosition pos = new ParsePosition(0);
	  Date strtodate = formatter.parse(strDate, pos);
	  return strtodate;
	 }

	 /**
	  * 得到现在时间
	  * 
	  * @return
	  */
	 public static Date getNow() {
	  Date currentTime = new Date();
	  return currentTime;
	 }

	 /**
	  * 提取一个月中的最后一天
	  * 
	  * @param day
	  * @return
	  */
	 public static Date getLastDate(long day) {
	  Date date = new Date();
	  long date_3_hm = date.getTime() - 3600000 * 34 * day;
	  Date date_3_hm_date = new Date(date_3_hm);
	  return date_3_hm_date;
	 }

	 /**
	  * 得到现在时间
	  * 
	  * @return 字符串 yyyyMMdd HHmmss
	  */
	 public static String getStringToday() {
	  Date currentTime = new Date();
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
	  String dateString = formatter.format(currentTime);
	  return dateString;
	 }

	 /**
	  * 得到现在小时
	  */
	 public static String getHour() {
	  Date currentTime = new Date();
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String dateString = formatter.format(currentTime);
	  String hour;
	  hour = dateString.substring(11, 13);
	  return hour;
	 }

	 /**
	  * 得到现在分钟
	  * 
	  * @return
	  */
	 public static String getTime() {
	  Date currentTime = new Date();
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String dateString = formatter.format(currentTime);
	  String min;
	  min = dateString.substring(14, 16);
	  return min;
	 }

	 /**
	  * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	  * 
	  * @param sformat
	  *            yyyyMMddhhmmss
	  * @return
	  */
	 public static String getUserDate(String sformat) {
	  Date currentTime = new Date();
	  SimpleDateFormat formatter = new SimpleDateFormat(sformat);
	  String dateString = formatter.format(currentTime);
	  return dateString;
	 }

	 /**
	  * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
	  */
	 public static String getTwoHour(String st1, String st2) {
	  String[] kk = null;
	  String[] jj = null;
	  kk = st1.split(":");
	  jj = st2.split(":");
	  if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
	   return "0";
	  else {
	   double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
	   double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
	   if ((y - u) > 0)
	    return y - u + "";
	   else
	    return "0";
	  }
	 }

	 /**
	  * 得到二个日期间的间隔天数
	  */
	 public static String getTwoDay(String sj1, String sj2) {
	  SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
	  long day = 0;
	  try {
	   Date date = myFormatter.parse(sj1);
	   Date mydate = myFormatter.parse(sj2);
	   day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
	  } catch (Exception e) {
	   return "";
	  }
	  return day + "";
	 }

	 /**
	  * 时间前推或后推分钟,其中JJ表示分钟.
	  */
	 public static String getPreTime(String sj1, String jj) {
	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String mydate1 = "";
	  try {
	   Date date1 = format.parse(sj1);
	   long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
	   date1.setTime(Time * 1000);
	   mydate1 = format.format(date1);
	  } catch (Exception e) {
	  }
	  return mydate1;
	 }

	 /**
	  * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	  */
	 public static String getNextDay(String nowdate, String delay) {
	  try{
	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	  String mdate = "";
	  Date d = strToDate(nowdate);
	  long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
	  d.setTime(myTime * 1000);
	  mdate = format.format(d);
	  return mdate;
	  }catch(Exception e){
	   return "";
	  }
	 }

	 /**
	  * 判断是否润年
	  * 
	  * @param ddate
	  * @return
	  */
	 public static boolean isLeapYear(String ddate) {

	  /**
	   * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
	   * 3.能被4整除同时能被100整除则不是闰年
	   */
	  Date d = strToDate(ddate);
	  GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
	  gc.setTime(d);
	  int year = gc.get(Calendar.YEAR);
	  if ((year % 400) == 0)
	   return true;
	  else if ((year % 4) == 0) {
		  return (year % 100) != 0;
	  } else
	   return false;
	 }

	 /**
	  * 返回美国时间格式 26 Apr 2006
	  * 
	  * @param str
	  * @return
	  */
	 public static String getEDate(String str) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	  ParsePosition pos = new ParsePosition(0);
	  Date strtodate = formatter.parse(str, pos);
	  String j = strtodate.toString();
	  String[] k = j.split(" ");
	  return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
	 }

	 /**
	  * 获取一个月的最后一天
	  * 
	  * @param dat
	  * @return
	  */
	 public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
	  String str = dat.substring(0, 8);
	  String month = dat.substring(5, 7);
	  int mon = Integer.parseInt(month);
	  if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
	   str += "31";
	  } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
	   str += "30";
	  } else {
	   if (isLeapYear(dat)) {
	    str += "29";
	   } else {
	    str += "28";
	   }
	  }
	  return str;
	 }

	 /**
	  * 判断二个时间是否在同一个周
	  * 
	  * @param date1
	  * @param date2
	  * @return
	  */
//	 public static boolean isSameWeekDates(Date date1, Date date2) {
//	  Calendar cal1 = Calendar.getInstance();
//	  Calendar cal2 = Calendar.getInstance();
//	  cal1.setTime(date1);
//	  cal2.setTime(date2);
//	  int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
//	  if (0 == subYear) {
//	   if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
//	    return true;
//	  } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
//	   // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
//	   if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
//	    return true;
//	  } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
//	   if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
//	    return true;
//	  }
//	  return false;
//	 }

	 /**
	  * 产生周序列,即得到当前时间所在的年度是第几周
	  * 
	  * @return
	  */
	 public static String getSeqWeek() {
	  Calendar c = Calendar.getInstance(Locale.CHINA);
	  String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
	  if (week.length() == 1)
	   week = "0" + week;
	  String year = Integer.toString(c.get(Calendar.YEAR));
	  return year + week;
	 }

	 /**
	  * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
	  * 
	  * @param sdate
	  * @param num
	  * @return
	  */
	 public static String getWeek(String sdate, String num) {
	  // 再转换为时间
	  Date dd = strToDate(sdate);
	  Calendar c = Calendar.getInstance();
	  c.setTime(dd);
	  if (num.equals("1")) // 返回星期一所在的日期
	   c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	  else if (num.equals("2")) // 返回星期二所在的日期
	   c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
	  else if (num.equals("3")) // 返回星期三所在的日期
	   c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
	  else if (num.equals("4")) // 返回星期四所在的日期
	   c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
	  else if (num.equals("5")) // 返回星期五所在的日期
	   c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
	  else if (num.equals("6")) // 返回星期六所在的日期
	   c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
	  else if (num.equals("0")) // 返回星期日所在的日期
	   c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	  return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	 }

	 /**
	  * 根据一个日期，返回是星期几的字符串
	  * 
	  * @param sdate
	  * @return
	  */
	 public static String getWeek(String sdate) {
	  // 再转换为时间
	  Date date = strToDate(sdate);
	  Calendar c = Calendar.getInstance();
	  c.setTime(date);
	  // int hour=c.get(Calendar.DAY_OF_WEEK);
	  // hour中存的就是星期几了，其范围 1~7
	  // 1=星期日 7=星期六，其他类推
	  return new SimpleDateFormat("EEEE").format(c.getTime());
	 }
	 public static String getWeekStr(String sdate){
	  String str = "";
	  str = getWeek(sdate);
	  if("1".equals(str)){
//	   str = "星期日";
		  str = "Sun";
	  }else if("2".equals(str)){
//	   str = "星期一";
		  str = "Mon";
	  }else if("3".equals(str)){
//	   str = "星期二";
		  str = "Tue";
	  }else if("4".equals(str)){
//	   str = "星期三";
		  str = "Wed";
	  }else if("5".equals(str)){
//	   str = "星期四";
		  str = "Thu";
	  }else if("6".equals(str)){
//	   str = "星期五";
		  str = "Fri";
	  }else if("7".equals(str)){
//	   str = "星期六";
		  str = "Sat";
	  }
	  return str;
	 }
	 public static String getWeekStr2(String sdate){
		  String str = "";
		  str = getWeek(sdate);
		  if("星期日".equals(str)){
		   str = "Sun";
		  }else if("星期一".equals(str)){
		   str = "Mon";
		  }else if("星期二".equals(str)){
		   str = "Tue";
		  }else if("星期三".equals(str)){
		   str = "Wed";
		  }else if("星期四".equals(str)){
		   str = "Thu";
		  }else if("星期五".equals(str)){
		   str = "Fri";
		  }else if("星期六".equals(str)){
		   str = "Sat";
		  }
		  return str;
		 }

	 /**
	  * 两个时间之间的天数
	  * 
	  * @param date1
	  * @param date2
	  * @return
	  */
	 public static long getDays(String date1, String date2) {
	  if (date1 == null || date1.equals(""))
	   return 0;
	  if (date2 == null || date2.equals(""))
	   return 0;
	  // 转换为标准时间
	  SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
	  Date date = null;
	  Date mydate = null;
	  try {
	   date = myFormatter.parse(date1);
	   mydate = myFormatter.parse(date2);
	  } catch (Exception e) {
	  }
	  long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
	  return day;
	 }

	 /**
	  * 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间
	  * 此函数返回该日历第一行星期日所在的日期
	  * 
	  * @param sdate
	  * @return
	  */
	 public static String getNowMonth(String sdate) {
	  // 取该时间所在月的一号
	  sdate = sdate.substring(0, 8) + "01";

	  // 得到这个月的1号是星期几
	  Date date = strToDate(sdate);
	  Calendar c = Calendar.getInstance();
	  c.setTime(date);
	  int u = c.get(Calendar.DAY_OF_WEEK);
	  String newday = getNextDay(sdate, (1 - u) + "");
	  return newday;
	 }

	 /**
	  * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
	  * 
	  * @param k
	  *            表示是取几位随机数，可以自己定
	  */

	 public static String getNo(int k) {

	  return getUserDate("yyyyMMddhhmmss") + getRandom(k);
	 }

	 /**
	  * 返回一个随机数
	  * 
	  * @param i
	  * @return
	  */
	 public static String getRandom(int i) {
	  Random jjj = new Random();
	  // int suiJiShu = jjj.nextInt(9);
	  if (i == 0)
	   return "";
	  String jj = "";
	  for (int k = 0; k < i; k++) {
	   jj = jj + jjj.nextInt(9);
	  }
	  return jj;
	 }

	 public static boolean RightDate(String date) {

	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	 if (date == null){
	   return false;
	 }
	  if (date.length() > 10) {
	   sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	  } else {
	   sdf = new SimpleDateFormat("yyyy-MM-dd");
	  }
	  try {
	   sdf.parse(date);
	  } catch (ParseException pe) {
	   return false;
	  }
	  return true;
	 }
	 
	 /**
	  * 比较两个时间的先后
	  * @param DATE1
	  * @param DATE2
	  * @return
	  */

	 public static boolean compare_date(String DATE1, String DATE2) {
         
         
         DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         try {
                 Date dt1 = df.parse(DATE1);
                 Date dt2 = df.parse(DATE2);
                  if (dt1.getTime() <= dt2.getTime()) {
                     System.out.println("dt1<=dt2后");
                     return true;
                 } else {
                	 System.out.println("dt1比dt2大");
                	 return false;
                 }
         } catch (Exception exception) {
                 exception.printStackTrace();
         }
         return false;
 }

}
