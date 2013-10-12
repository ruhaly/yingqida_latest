package com.yingqida.richplay.baseapi.common;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

import com.yingqida.richplay.RichPlayApplication;
import com.yingqida.richplay.baseapi.AppLog;

public final class RealmeUtil {

	public static final String FMT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 表情正则式
	 */
	private static final String EMOTION_PATTERN = "(/:D|/:\\)|/:\\*"
			+ "|/:8|/D~|/\\-\\(|/\\-O|/:\\$|/CO|/YD|/;\\)|/;P"
			+ "|/:!|/:0|/GB|/:S|/:\\?|/:Z|/88|/SX"
			+ "|/TY|/OT|/NM|/\\:X|/DR|/:<|/ZB|/BH|/HL"
			+ "|/XS|/YH|/KI|/DX|/KF|/KL|/LW|/PG|/XG"
			+ "|/CF|/TQ|/DH|/\\*\\*|/@@|/:\\{|/FN|/0\\(|/;>"
			+ "|/FD|/ZC|/JC|/ZK|/:\\(|/LH|/SK|/\\$D|/CY"
			+ "|/\\%S|/LO|/PI|/DB|/MO|/YY|/FF|/ZG|/;I"
			+ "|/XY|/MA|/GO|/\\%@|/ZD|/SU|/MI|/BO"
			+ "|/GI|/DS|/YS|/DY|/SZ|/DP|/:\\\\|/00)";

	/**
	 * 生成随机字符串的原始字符串
	 */
	private static final String RANDOM_STR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	/**
	 * 图片缓存
	 */
	private static final Map<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();

	/**
	 * drawableCache缓存
	 */
	private static final Map<String, SoftReference<Drawable>> drawableCache = new HashMap<String, SoftReference<Drawable>>();

	/**
	 * 构造方法 输入参数：
	 */
	private RealmeUtil() {

	}

	public static Bitmap getImage(int resId) {
		String key = String.valueOf(resId);
		SoftReference<Bitmap> bitmap = imageCache.get(key);
		if (null == bitmap || null == bitmap.get()) {
			imageCache.remove(key);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			Bitmap b = BitmapFactory.decodeResource(RichPlayApplication
					.getIns().getResources(), resId, options);
			bitmap = new SoftReference<Bitmap>(b);
			b = null;
			imageCache.put(key, bitmap);
			AppLog.out("rimi", "getImage from local", AppLog.LEVEL_INFO);
		}
		return bitmap.get();
	}

	/**
	 * 方法名称： getTimeByMinSecs 作者： wjd 方法描述： 根據毫秒獲得指定格式的字符串(hh:mm:ss) 输入参数： @param
	 * minSecs 输入参数： @return 返回类型： String
	 */
	public static String getTimeByMinSecs(int minSecs) {
		int hour = minSecs / 1000 / 3600;
		int min = (minSecs / 1000 - 3600 * hour) / 60;
		int sec = (minSecs / 1000) % 60;
		return String.format("%1$02d:%2$02d:%3$02d", hour, min, sec);
	}

	/**
	 * 方法名称： getDateByString 作者： wjd 方法描述： 输入参数： @param dateStr 输入参数： @param
	 * format 输入参数： @return 返回类型： Date
	 */
	public static String formatDateStr(String dateStr, String format) {
		Timestamp ts = new Timestamp(Long.valueOf(dateStr));
		return new SimpleDateFormat(format).format(ts);
	}

	private static void releaseImage(int resId) {
		String key = String.valueOf(resId);
		SoftReference<Bitmap> bitmap = null;
		if (imageCache.containsKey(key)) {
			bitmap = imageCache.get(key);
			if (null != bitmap) {
				Bitmap b = bitmap.get();
				if (null != b && !b.isRecycled()) {
					b.recycle();
					b = null;
				}
				bitmap.clear();
			}
			imageCache.remove(key);
		}
	}

	/**
	 * 方法名称： releaseBitmap 作者： fengrun 方法描述： 释放Bitmap资源 输入参数： @param bitmap
	 * 返回类型： void
	 */
	public static void releaseBitmap(Bitmap bitmap) {
		if (null != bitmap && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
	}

	/**
	 * 方法名称： getString 作者： Administrator 方法描述： 获取资源字符串 输入参数： @param resId 输入参数： @return
	 * 返回类型： String
	 */
	public static String getString(int resId) {
		return RichPlayApplication.getIns().getString(resId);
	}

	/**
	 * 方法名称： getDrawable 作者： Administrator 方法描述： 获取资源Drawable 输入参数： @param resId
	 * 输入参数： @return 返回类型： Drawable
	 */
	public static Drawable getDrawable(int resId) {
		String key = String.valueOf(resId);
		SoftReference<Drawable> drawable = drawableCache.get(key);
		Bitmap bitmap = null;
		if (null != drawable) {
			BitmapDrawable d = (BitmapDrawable) drawable.get();
			if (null != d) {
				bitmap = d.getBitmap();
			}

		}
		if (null == bitmap || bitmap.isRecycled()) {
			if (null != drawable) {
				drawable.clear();
			}
			drawableCache.remove(key);
			Drawable d = new BitmapDrawable(RichPlayApplication.getIns()
					.getResources(), getImage(resId));
			drawable = new SoftReference<Drawable>(d);
			d = null;
			drawableCache.put(key, drawable);
		}
		return drawable.get();
	}

	/**
	 * 方法名称： releaseDrawable 作者： Administrator 方法描述： 释放资源 输入参数： @param resId
	 * 返回类型： void
	 */
	public static void releaseDrawable(int resId) {
		String key = String.valueOf(resId);
		SoftReference<Drawable> drawable = drawableCache.get(key);
		if (null != drawable) {
			drawable.clear();
		}
		imageCache.remove(key);
		releaseImage(resId);
	}

	/**
	 * 方法名称： createRichText 作者： Administrator 方法描述： 构建图文字符串 输入参数： @param text
	 * 输入参数： @return 返回类型： SpannableStringBuilder
	 */
	public static SpannableStringBuilder createRichText(String text) {
		SpannableStringBuilder ssb = new SpannableStringBuilder(text);
		/**
		 * 解析图片
		 */
		ArrayList<Tag> ListTag = Tag.getTags(text, EMOTION_PATTERN);
		if (ListTag != null && ListTag.size() > 0) {
			for (Tag t : ListTag) {
				ImageSpan imageSpan = getEmotionImageSpan(t.getTag(),
						ImageSpan.ALIGN_BOTTOM);
				ssb.setSpan(imageSpan, t.getIndexBegin(), t.getIndexEnd(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		return ssb;
	}

	/**
	 * 方法名称： getEmotionImageSpan 作者： Administrator 方法描述： 获取表情mageSpan 输入参数： @param
	 * key 输入参数： @param verticalAlignment 输入参数： @return 返回类型： ImageSpan
	 */
	public static ImageSpan getEmotionImageSpan(String key,
			int verticalAlignment) {
		ImageSpan imageSpan = null;
		// int index = getIndex(key, RealmeResource.EMOTION_CHAR);
		// Drawable drawable = getDrawable(R.drawable.e01 + index);
		// drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
		// drawable.getIntrinsicHeight());
		// imageSpan = new ImageSpan(drawable, verticalAlignment);
		return imageSpan;
	}

	/**
	 * 方法名称： getIndex 作者： Administrator 方法描述： 获取指定资源的序号 输入参数： @param tagName
	 * 输入参数： @param array 输入参数： @return 返回类型： int
	 */
	public static int getIndex(String tagName, String[] array) {
		int length = array.length;
		for (int i = 0; i < length; i++) {
			if (tagName.equals(array[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 类名称： Tag 作者： Administrator 创建时间： 2011-11-9 下午3:30:59 类描述： 标签实体类，
	 * 用来按规则区分String 中的标签 版权声明 ： Copyright (C) 2008-2010 RichPeak 修改时间：
	 * 2011-11-9 下午3:30:59
	 * 
	 */
	private static class Tag {
		int indexBegin;
		int indexEnd;
		String tag;

		public int getIndexBegin() {
			return indexBegin;
		}

		public void setIndexBegin(int indexBegin) {
			this.indexBegin = indexBegin;
		}

		public int getIndexEnd() {
			return indexEnd;
		}

		public void setIndexEnd(int indexEnd) {
			this.indexEnd = indexEnd;
		}

		public String getTag() {
			return tag;
		}

		public void setTag(String tag) {
			this.tag = tag;
		}

		/**
		 * 方法名称： getTags 作者： Administrator 方法描述：用正则表达式匹配字符串，得到标签列表 输入参数： @param
		 * text 输入参数： @param pattern 输入参数： @return 返回类型： ArrayList<Tag>
		 */
		private static ArrayList<Tag> getTags(String text, String pattern) {
			ArrayList<Tag> listTag = null;

			if (text == null || text.length() == 0) {
				return null;
			}

			Pattern urlPattern = Pattern.compile(pattern);

			Matcher urlMatcher = urlPattern.matcher(text);

			while (urlMatcher.find()) {
				int indexBegin = urlMatcher.start();
				int indexEnd = urlMatcher.end();
				if (listTag == null) {
					listTag = new ArrayList<Tag>();
				}
				Tag t = new Tag();
				t.setIndexBegin(indexBegin);
				t.setIndexEnd(indexEnd);
				t.setTag(text.substring(indexBegin, indexEnd));
				if (t.getTag().length() <= 21 && t.getTag().length() >= 3) {
					listTag.add(t);
				}
			}
			return listTag;
		}
	}

	/**
	 * 方法名称：isTonpApp 作者：wangjian 方法描述：判断应用程序是否是最上层的应用 输入参数：@param context
	 * 输入参数：@return 返回类型：boolean： 备注：
	 */
	public static boolean isTopApp(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> TaskList = am.getRunningTasks(2);
		if (TaskList == null || TaskList.isEmpty()) {
			return false;
		}
		RunningTaskInfo rti = TaskList.get(0);
		String tmp = rti.topActivity.getPackageName();
		return tmp.equals(context.getPackageName());
	}

	/**
	 * 方法名称： getRandomBranch 作者： wjd 方法描述： 生成指定长度的随机字符串 输入参数： @param length
	 * 输入参数： @return 返回类型： String
	 */
	public static String getRandomString(int length) {
		StringBuffer buffer = new StringBuffer(length);
		for (int i = 0; i < length; ++i) {
			buffer.append(RANDOM_STR.charAt((int) (Math.random() * 62)));
		}
		return buffer.toString();
	}

	/**
	 * 方法名称：getLocalIpAddress 作者：wangjian 方法描述：获取本机IP 输入参数：@return 返回类型：String：
	 * 备注：
	 */
	public static String getLocalIpAddress() {
		try {
			Enumeration<NetworkInterface> networkInfo = NetworkInterface
					.getNetworkInterfaces();
			for (Enumeration<NetworkInterface> en = networkInfo; en
					.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				Enumeration<InetAddress> intfAddress = intf.getInetAddresses();
				for (Enumeration<InetAddress> enumIpAddr = intfAddress; enumIpAddr
						.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {

		}
		return null;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取网落图片资源
	 * 
	 * @param url
	 * @return
	 */

	public static Bitmap getHttpBitmap(String url) {

		URL myFileURL;

		Bitmap bitmap = null;

		try {

			myFileURL = new URL(url);

			// 获得连接

			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();

			// 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制

			conn.setConnectTimeout(6000);

			// 连接设置获得数据流

			conn.setDoInput(true);

			// 不使用缓存

			conn.setUseCaches(false);

			// 这句可有可无，没有影响

			// conn.connect();

			// 得到数据流

			InputStream is = conn.getInputStream();

			// 解析得到图片

			bitmap = BitmapFactory.decodeStream(is);

			// 关闭数据流

			is.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return bitmap;

	}

}