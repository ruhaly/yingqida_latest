package com.yingqida.richplay.baseapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final String[] zodiacArr = { "猴", "鸡", "狗", "猪", "鼠", "牛",
			"虎", "兔", "龙", "蛇", "马", "羊" };

	public static final String[] constellationArr = { "水瓶座", "双鱼座", "牡羊座",
			"金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座" };

	public static final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22,
			23, 23, 23, 23, 22, 22 };

	public static final String FMT_HMS = "HH:mm:ss";

	public static final String FMT_MS = "mm:ss";

	public static final String FMT_YMDHMS = "yyyyMMddHHmmss";

	public static final String FMT_YMD = "yyyy-MM-dd";

	public static final String FMT_MAIL_TIME = "yyyy-MM-dd HH:mm:ss";

	public static final String FMTYMD = "yyyyMMdd";

	public static String format(long millSec, String format) {
		return new SimpleDateFormat(format).format(new Date(millSec));
	}

	public static String format(String format, int year, int month, int day) {
		return new SimpleDateFormat(format).format(new Date(year - 1900,
				month - 1, day));
	}

	public static String getAge(String dateStr) {
		Date date = null;
		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		try {
			date = new SimpleDateFormat(FMT_YMDHMS).parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			return String.valueOf(curYear - year);
		} catch (Exception e) {
			try {
				date = new SimpleDateFormat(FMTYMD).parse(dateStr);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
				return String.valueOf(curYear - year);
			} catch (Exception e1) {
				return "";
			}
		}
	}

	public static String formatLastLoginTime(String src, String format) {
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(src);
		} catch (ParseException e) {
			return src;
		}
		return new SimpleDateFormat(FMT_MAIL_TIME).format(date);
	}

	public static int getSeconds(String dateStr) {
		Date date = null;
		long curYear = Calendar.getInstance().getTimeInMillis();
		try {
			date = new SimpleDateFormat(FMT_MAIL_TIME).parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			long year = calendar.getTimeInMillis();
			return (int) ((curYear - year) / 1000);
		} catch (Exception e) {
			return -1;
		}
	}

	public static long getmailtime(String src, String format) {
		try {
			return new SimpleDateFormat(format).parse(src).getTime();
		} catch (ParseException e) {
			return 0;
		}
	}

	public static String formatChange(String src, String srcFormat,
			String retFormat) {
		Date date = null;
		try {
			date = new SimpleDateFormat(srcFormat).parse(src);
		} catch (Exception e) {
			return "";
		}
		return new SimpleDateFormat(retFormat).format(date);
	}

	public static String getChineseZodic(String dateStr) {
		Date date = null;
		try {
			date = new SimpleDateFormat(FMT_YMDHMS).parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			return zodiacArr[year % 12];
		} catch (Exception e) {
			try {
				date = new SimpleDateFormat(FMTYMD).parse(dateStr);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
				return zodiacArr[year % 12];
			} catch (ParseException e1) {
				return null;
			}
		}
	}

	public static String getConstellation(String dateStr) {
		Date date = null;
		try {
			date = new SimpleDateFormat(FMT_YMDHMS).parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return date2Constellation(calendar);
		} catch (Exception e) {
			try {
				date = new SimpleDateFormat(FMTYMD).parse(dateStr);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				return date2Constellation(calendar);
			} catch (ParseException e1) {
				return null;
			}
		}
	}

	public static String date2Constellation(Calendar time) {
		int month = time.get(Calendar.MONTH);
		int day = time.get(Calendar.DAY_OF_MONTH);
		if (day < constellationEdgeDay[month]) {
			month = month - 1;
		}
		if (month >= 0) {
			return constellationArr[month];
		}
		// default to return 魔羯
		return constellationArr[11];
	}

	public static String getTodayDate() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	/**
	 * 生日转换 yyyy-MM-dd
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String getDate(String dateStr) {
		Date date = null;
		try {
			date = new SimpleDateFormat(FMT_YMDHMS).parse(dateStr);

			return new SimpleDateFormat(FMT_YMD).format(date);

		} catch (Exception e) {
			try {
				date = new SimpleDateFormat(FMTYMD).parse(dateStr);
				return new SimpleDateFormat(FMT_YMD).format(date);
			} catch (Exception e1) {
				return "";
			}
		}
	}

}
