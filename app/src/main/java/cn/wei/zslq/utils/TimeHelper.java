package cn.wei.zslq.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/** 操作时间的工具类. */
public class TimeHelper {
	/** 返回自1970年至现在的毫秒数为特定的字符串. */
	public static String updateMilliSecToFormatDateStr(long mSeconds) {
		SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd H:mm");
		return mDateFormat.format(new Date(mSeconds));
	}

	public static String updateMilliSecToFormatDateStr(long mSeconds, String mFormat) {
		SimpleDateFormat mDateFormat = new SimpleDateFormat(mFormat);
		return mDateFormat.format(new Date(mSeconds));
	}

	/** 返回当前发送音频的时间点. */
	public static String getChatSendTime() {
		try {
			Date now = new Date();
			DateFormat format = DateFormat.getDateInstance();
			DateFormat format2 = DateFormat.getTimeInstance();
			return format.format(now) + format2.format(now);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getHeadTime() {
		Date date = new Date();
		SimpleDateFormat myFormatter = new SimpleDateFormat("MM-dd HH:mm");
		return myFormatter.format(date);
	}

	/**
	 * Get the format time.
	 */
	public static String getTime() {
		Date date = new Date();
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return myFormatter.format(date);
	}

	public static String getChatTime(long time) {
		Date date = new Date(time);
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return myFormatter.format(date);
	}

	public static long getCurrTime() {
		return System.currentTimeMillis();
	}

	/**
	 * @param time
	 *            milliseconds
	 * @return
	 */
	public static String getTimeRule1(long time) {
		Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		c1.setTimeInMillis(time);
		return compare1(c1);
	}

	/**
	 * @param time
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getTimeRule1(String time) {
		if (time == null || "".equals(time.trim())) {
			return "刚刚";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(time);
			Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
			c1.setTime(date);
			return compare1(c1);
		} catch (ParseException e) {
			return "刚刚";
		}
	}

	/**
	 * @param time
	 *            milliseconds
	 * @return
	 */
	public static String getTimeRule2(long time) {
		Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		c1.setTimeInMillis(time);
		return compare2(c1);
	}

	/**
	 * @param time
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getTimeRule2(String time) {
		if (time == null || "".equals(time.trim())) {
			return "刚刚";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			return "刚刚";
		}
		Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		c1.setTime(date);
		return compare2(c1);
	}

	public static String getTimeRule3(long time) {
		Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		c1.setTimeInMillis(time);
		return compare3(c1);
	}
	
	public static String getTimeRule4(long time) {
		Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		c1.setTimeInMillis(time);
		return compare4(c1);
	}

	private static String compare4(Calendar c1) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");
		Calendar c2 = Calendar.getInstance(TimeZone.getDefault());
		if (c1.get(Calendar.YEAR) < c2.get(Calendar.YEAR)) {
			return sdf1.format(c1.getTime());
		}
		if (c1.get(Calendar.MONTH) < c2.get(Calendar.MONTH)
				|| (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DAY_OF_MONTH) < c2.get(Calendar.DAY_OF_MONTH) - 1)) {
			return sdf2.format(c1.getTime());
		}
		if (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH) - 1) {
			return "昨天 " + c1.get(Calendar.HOUR_OF_DAY) + ":"
					+ (c1.get(Calendar.MINUTE) < 10 ? "0" + c1.get(Calendar.MINUTE) : c1.get(Calendar.MINUTE));
		}
		if (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)) {
			return c1.get(Calendar.HOUR_OF_DAY) + ":" + (c1.get(Calendar.MINUTE) < 10 ? "0" + c1.get(Calendar.MINUTE) : c1.get(Calendar.MINUTE));
		}
		return sdf2.format(c1.getTime());
	}

	private static String compare3(Calendar c1) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yy-MM-dd HH:mm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd HH:mm");
		Calendar c2 = Calendar.getInstance(TimeZone.getDefault());
		if (c1.get(Calendar.YEAR) < c2.get(Calendar.YEAR)) {
			return sdf1.format(c1.getTime());
		}
		if (c1.get(Calendar.MONTH) < c2.get(Calendar.MONTH)
				|| (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DAY_OF_MONTH) < c2.get(Calendar.DAY_OF_MONTH) - 1)) {
			return sdf2.format(c1.getTime());
		}
		if (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH) - 1) {
			return "昨天 " + c1.get(Calendar.HOUR_OF_DAY) + ":"
					+ (c1.get(Calendar.MINUTE) < 10 ? "0" + c1.get(Calendar.MINUTE) : c1.get(Calendar.MINUTE));
		}
		if (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)) {
			return c1.get(Calendar.HOUR_OF_DAY) + ":" + (c1.get(Calendar.MINUTE) < 10 ? "0" + c1.get(Calendar.MINUTE) : c1.get(Calendar.MINUTE));
		}
		return sdf2.format(c1.getTime());
	}

	private static String compare1(Calendar c1) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yy-MM-dd HH:mm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd HH:mm");
		Calendar c2 = Calendar.getInstance(TimeZone.getDefault());
		if (c1.get(Calendar.YEAR) < c2.get(Calendar.YEAR)) {
			return sdf1.format(c1.getTime());
		}
		if (c1.get(Calendar.MONTH) < c2.get(Calendar.MONTH)
				|| (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DAY_OF_MONTH) < c2.get(Calendar.DAY_OF_MONTH))) {
			return sdf2.format(c1.getTime());
		}
		if (c1.get(Calendar.HOUR_OF_DAY) < c2.get(Calendar.HOUR_OF_DAY) - 1) {
			return "今天 " + c1.get(Calendar.HOUR_OF_DAY) + ":"
					+ (c1.get(Calendar.MINUTE) < 10 ? "0" + c1.get(Calendar.MINUTE) : c1.get(Calendar.MINUTE));
		}
		if (c1.get(Calendar.HOUR_OF_DAY) == c2.get(Calendar.HOUR_OF_DAY) - 1) {
			if (c1.get(Calendar.MINUTE) > c2.get(Calendar.MINUTE)) {
				return c2.get(Calendar.MINUTE) + (60 - c1.get(Calendar.MINUTE)) + "分钟前";
			}
			return "今天 " + c1.get(Calendar.HOUR_OF_DAY) + ":"
					+ (c1.get(Calendar.MINUTE) < 10 ? "0" + c1.get(Calendar.MINUTE) : c1.get(Calendar.MINUTE));
		}
		if (c1.get(Calendar.MINUTE) < c2.get(Calendar.MINUTE) - 1) {
			return c2.get(Calendar.MINUTE) - c1.get(Calendar.MINUTE) + "分钟前";
		}
		return "刚刚";
	}

	private static String compare2(Calendar c1) {
		Calendar c2 = Calendar.getInstance(TimeZone.getDefault());
		if (c1.get(Calendar.YEAR) < c2.get(Calendar.YEAR) - 1) {
			return c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR) + "年前";
		}
		if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) - 1) {
			if (c1.get(Calendar.DAY_OF_YEAR) < c2.get(Calendar.DAY_OF_YEAR)) {
				return c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR) + "年前";
			}
			return c2.get(Calendar.DAY_OF_YEAR) + (365 - c1.get(Calendar.DAY_OF_YEAR)) + "天前";
		}
		if (c1.get(Calendar.DAY_OF_YEAR) < c2.get(Calendar.DAY_OF_YEAR) - 1) {
			return c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR) + "天前";
		}
		if (c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR) - 1) {
			if (c1.get(Calendar.DAY_OF_YEAR) < c2.get(Calendar.DAY_OF_YEAR)) {
				return c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR) + "天前";
			}
			return c2.get(Calendar.DAY_OF_YEAR) + (24 - c1.get(Calendar.DAY_OF_YEAR)) + "小时前";
		}
		if (c1.get(Calendar.HOUR_OF_DAY) < c2.get(Calendar.HOUR_OF_DAY) - 1) {
			return c2.get(Calendar.HOUR_OF_DAY) - c1.get(Calendar.HOUR_OF_DAY) + "小时前";
		}
		if (c1.get(Calendar.HOUR_OF_DAY) == c2.get(Calendar.HOUR_OF_DAY) - 1) {
			if (c1.get(Calendar.MINUTE) > c2.get(Calendar.MINUTE)) {
				return c2.get(Calendar.MINUTE) + (60 - c1.get(Calendar.MINUTE)) + "分钟前";
			}
			return c2.get(Calendar.HOUR_OF_DAY) - c1.get(Calendar.HOUR_OF_DAY) + "小时前";
		}
		if (c1.get(Calendar.MINUTE) < c2.get(Calendar.MINUTE) - 1) {
			return c2.get(Calendar.MINUTE) - c1.get(Calendar.MINUTE) + "分钟前";
		}
		return "1分钟前";
	}

	public static String getScheduleTime(int start_time, int end_time) {
		if (start_time == 0 || end_time == 0) {
			return null;
		}
		return getTime(start_time) + "-" + getTime(end_time);
	}

	public static String getTime(int time) {
		int hours = time / 3600; // needs to be an integer division
		int leaves = time - hours * 3600;
		int minutes = leaves / 60;
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 01, 01, hours, minutes, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(calendar.getTime());
	}

	public static String getDate(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date(timestamp));
	}
}
