package java_servlet_service.util;

import java.util.Calendar;
import java.util.TimeZone;

public class DateUtil {

	public static String dateFormart4ymdHMS(long time) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		cal.setTimeInMillis(time);
		return String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", cal.getTime());
	}
	
	public static String dateFormart4ymd(long time) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		cal.setTimeInMillis(time);
		return String.format("%1$tY%1$tm%1$td", cal.getTime());
	}
	
	/**
	 * 获取当天的日期 格式：2017-02-17
	 * @return
	 * 2017年2月17日 下午2:32:29 by lichenyi
	 */
	public static String dateFormart4yyyy_mm_dd(){
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.add(Calendar.DAY_OF_MONTH, 0);
		return String.format("%1$tY-%1$tm-%1$td", cal.getTime());
	}

	/**
	 * 获取当天的日期 格式：20170217
	 * @return
	 * 2017年2月17日 下午2:32:29 by lichenyi
	 */
	public static String dateFormart4yyyymmdd(){
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.add(Calendar.DAY_OF_MONTH, 0);
		return String.format("%1$tY%1$tm%1$td", cal.getTime());
	}

	/**
	 * 获取当天的日期 格式：20170217
	 * @return
	 * 2017年2月17日 下午2:32:29 by lichenyi
	 */
	public static String dateFormart4yyyymmdd(int delayDay){
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.add(Calendar.DAY_OF_MONTH, delayDay);
		return String.format("%1$tY%1$tm%1$td", cal.getTime());
	}

}
