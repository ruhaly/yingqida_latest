package com.yingqida.richplay.baseapi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.channels.FileLock;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.yingqida.richplay.baseapi.common.FileUtil;
import com.yingqida.richplay.baseapi.common.RichResource;
import com.yingqida.richplay.baseapi.common.UnSyncImageLoader;

/**
 * 图像工具
 * 
 * @author Administrator
 * 
 */
public class ImageUtil {

	/**
	 * bitmap缓存
	 */
	private static final Map<String, SoftReference<Bitmap>> bitmapCache = new HashMap<String, SoftReference<Bitmap>>();

	/**
	 * drawable缓存
	 */
	private static final Map<String, SoftReference<Drawable>> drawableCache = new HashMap<String, SoftReference<Drawable>>();

	/**
	 * ͷ����
	 */
	public static final byte FACE_COUNT = 24;

	/**
	 * һҳ��ʾ��ͷ����
	 */
	public static final byte FACE_PAGE_COUNT = 8;

	public static Map<String, SoftReference<Bitmap>> imgs = Collections
			.synchronizedMap(new LinkedHashMap<String, SoftReference<Bitmap>>());

	public static void clearImgs() {
		for (int i = 0; i < imgs.size(); ++i) {
			SoftReference<Bitmap> bitmap = imgs.get(i);
			if (null != bitmap && null != bitmap.get())
				bitmap.get().recycle();
		}
		imgs.clear();
		UnSyncImageLoader.stopCount();
	}

	/**
	 * 获得bitmap图片
	 * 
	 * @param resId
	 * @param resource
	 * @return
	 */
	public static Bitmap getBitmap(int resId, Resources resource) {
		String key = String.valueOf(resId);
		SoftReference<Bitmap> bitmap = bitmapCache.get(key);
		if (null == bitmap || null == bitmap.get()) {
			bitmapCache.remove(key);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			Bitmap b = BitmapFactory.decodeResource(resource, resId, options);
			bitmap = new SoftReference<Bitmap>(b);
			b = null;
			bitmapCache.put(key, bitmap);
			AppLog.out("rimi", "getImage from local", AppLog.LEVEL_INFO);
		}
		return bitmap.get();
	}

	/**
	 * 获得drawable图片
	 * 
	 * @param resId
	 * @param resource
	 * @return
	 */
	public static Drawable getDrawable(int resId, Resources resource) {
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
			Drawable d = new BitmapDrawable(resource,
					getBitmap(resId, resource));
			drawable = new SoftReference<Drawable>(d);
			d = null;
			drawableCache.put(key, drawable);
		}
		return drawable.get();
	}

	/**
	 * 获得文件后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getSuffix(String fileName) {
		if (null == fileName || "".equals(fileName))
			return null;
		int idx = fileName.lastIndexOf(".");
		if (idx >= 0 && idx < fileName.length() - 1)
			return fileName.substring(idx + 1);
		return null;
	}

	public static boolean photoExist(String dir, String name) {
		File file = FileUtil.getFile(dir + "/" + name, false);
		return null != file && file.exists();
	}

	public static String getPhotoName(String url) {
		String ret = null;
		try {
			String[] rets = url.split("/");
			ret = rets[rets.length - 1];
		} catch (Exception e) {
			ret = null;
		}
		return ret;
	}

	/**
	 * 将图片保存到本地文件
	 * 
	 * @param photo
	 */
	public static boolean savePhotoToLocal(InputStream is, String dir,
			String name) {
		if (null == is)
			return false;

		// File file = new File(Environment.getExternalStorageDirectory() +
		// dir);
		File file = FileUtil.getFile(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file + "/" + name);
			byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = is.read(buffer)) > 0) {
				fos.write(buffer, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != fos) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					AppLog.out("FileUtil", e.getMessage(), AppLog.LEVEL_ERROR);
					return false;
				}
			}
		}
		return true;
	}

	// public static Bitmap getPhoto(String dir, String name)
	// {
	// Bitmap photo = null;
	//
	// // File file = new File(Environment.getExternalStorageDirectory() + dir
	// // + "/" + name);
	// File file = FileUtil.getFile(dir + "/" + getPhotoName(name));
	// if (file.exists())
	// {
	// photo = BitmapFactory.decodeFile(file.getPath());
	// }
	// return photo;
	// }

	// public static Bitmap getPhoto2(String dir, String name, int width)
	// {
	//
	// Bitmap photo = null;
	//
	// File file = FileUtil.getFile(dir + "/" + getPhotoName(name), false);
	// if (null != file && file.exists())
	// {
	// Options options = new Options();
	// options.inJustDecodeBounds = true;
	// photo = BitmapFactory.decodeFile(file.getPath(), options);
	// Runtime.getRuntime().gc();
	// Config config = options.inPreferredConfig;
	// int size = 3;
	// if (null != config && config.name().equals(Config.ARGB_8888.name()))
	// size = 4;
	// if (Runtime.getRuntime().freeMemory() < options.outHeight
	// * options.outWidth * size)
	// {
	// return null;
	// }
	// options.outHeight = options.outHeight * (width) / options.outWidth;
	// options.outWidth = width;
	// options.inJustDecodeBounds = false;
	// try
	// {
	// photo = BitmapFactory.decodeFile(file.getPath(), options);
	// }
	// catch (Exception e)
	// {
	// photo = null;
	// }
	// }
	// return photo;
	//
	// // Bitmap photo = null;
	// //
	// // File file = FileUtil.getFile(dir + "/" + name, false);
	// // if (null != file && file.exists())
	// // {
	// // Options options = new Options();
	// // options.inJustDecodeBounds = true;
	// // photo = BitmapFactory.decodeFile(file.getPath(), options);
	// // options.outHeight = options.outHeight * width / options.outWidth;
	// // options.outWidth = width;
	// // options.inJustDecodeBounds = false;
	// // try
	// // {
	// // photo = BitmapFactory.decodeFile(file.getPath(), options);
	// // }
	// // catch (Exception e)
	// // {
	// // photo = null;
	// // }
	// // }
	// // return photo;
	// }

	public static int measureDrawableHeight(Context context, int idx) {
		Bitmap image = getBitmap(idx, context.getResources());
		int height = image.getHeight();
		image.recycle();
		return height;
	}

	public static String getLocName(String src) {
		int idx = src.indexOf("=");
		return src.substring(idx);
	}

	public static Bitmap getThumbPic(String dir, String name, int width) {
		Bitmap photo = null;

		File file = FileUtil.getFile(dir + "/" + name, false);
		if (null != file && file.exists()) {
			Options options = new Options();
			options.inJustDecodeBounds = true;
			photo = BitmapFactory.decodeFile(file.getPath(), options);
			int be = options.outWidth / width;
			if (be <= 0)
				be = 1;
			options.inSampleSize = be;
			options.inJustDecodeBounds = false;
			photo = BitmapFactory.decodeFile(file.getPath(), options);
		}
		return photo;
	}

	/**
	 * @param zoom
	 * @param width
	 * @param round
	 *            TODO
	 * @param name
	 * @full name of the bitmap path
	 * @return
	 */
	public synchronized static Bitmap getDownloadBitmap(String url,
			boolean zoom, int width, boolean round) {
		if (StringUtil.isNull(url))
			return null;
		if (!FileUtil.externalStorageAvilable()) {
			return getBitmap(url);
		}
		if (round) {
			Bitmap ret = getBitmap(url);
			if (null != ret && ret.getWidth() == width)
				return ret;
		}
		Bitmap photo = null;
		File file = FileUtil.getFile(RichResource.PIC_PATH + "/"
				+ getPhotoName(url), false);
		if (null != file && file.exists()) {
			Options options = new Options();
			options.inJustDecodeBounds = true;
			photo = BitmapFactory.decodeFile(file.getPath(), options);
			Runtime.getRuntime().gc();
			int sampleSize = 1;
			if (zoom)
				sampleSize = (int) Math.ceil((float) options.outWidth
						/ (float) width);
			if (sampleSize == 0)
				sampleSize = 1;
			options.inSampleSize = sampleSize;
			while (Runtime.getRuntime().freeMemory() < options.outHeight
					* options.outWidth * 4 / (sampleSize * sampleSize)) {
				sampleSize++;
				if (width > 480) {
					sampleSize = Math.round((float) options.outWidth
							/ (float) 480);
				}
				if (Runtime.getRuntime().freeMemory() < options.outHeight
						* options.outWidth * 4 / (sampleSize * sampleSize))
					return null;
			}
			options.inJustDecodeBounds = false;
			try {
				photo = BitmapFactory.decodeFile(file.getPath(), options);
				if (round) {
					photo = getRoundedCornerBitmap(Bitmap.createScaledBitmap(
							photo, width, width, false));
					imgs.put(url, new SoftReference<Bitmap>(photo));
				}
			} catch (Exception e) {
				photo = null;
			}
		}
		// if (null != file && file.exists()) {
		// Options options = new Options();
		// options.inJustDecodeBounds = true;
		// photo = BitmapFactory.decodeFile(file.getPath(), options);
		// Runtime.getRuntime().gc();
		// int sampleSize = 1;
		// if (zoom)
		// sampleSize = (int) Math.ceil((float) options.outWidth
		// / (float) width);
		// if (sampleSize == 0)
		// sampleSize = 1;
		// options.inSampleSize = sampleSize;
		// while (Runtime.getRuntime().freeMemory() < options.outHeight
		// * options.outWidth * 4 / (sampleSize * sampleSize)) {
		// sampleSize++;
		// }
		// options.inJustDecodeBounds = false;
		// try {
		// photo = BitmapFactory.decodeFile(file.getPath(), options);
		// if (round) {
		// photo = getRoundedCornerBitmap(Bitmap.createScaledBitmap(
		// photo, width, width, false));
		// imgs.put(url, new SoftReference<Bitmap>(photo));
		// }
		// } catch (Exception e) {
		// photo = null;
		// }
		// }
		return photo;
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		if (null == bitmap)
			return null;
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);

		// Canvas canvas1 = new Canvas(output);
		// Rect rec = canvas1.getClipBounds();
		// rec.bottom--;
		// rec.right--;
		// Paint paint1 = new Paint();
		// //设置边框颜色
		// paint1.setColor(Color.GREEN);
		// paint1.setStyle(Paint.Style.STROKE);
		// paint1.setStrokeWidth(10);
		// canvas1.drawRect(rec, paint1);
		//
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);

		// int radio = bitmap.getWidth() / 15;
		int radio = 10;
		canvas.drawRoundRect(rectF, radio, radio, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static Bitmap getBitmap(String name) {
		SoftReference<Bitmap> bitmap = imgs.get(name);
		if (null == bitmap || null == bitmap.get() || bitmap.get().isRecycled()) {
			imgs.remove(name);
			return null;
		}
		return bitmap.get();
	}

	public static boolean saveLifePhoto(InputStream is, String name) {
		if (null == is)
			return false;
		String fileName = null;
		File file = null;
		FileOutputStream fos = null;
		FileLock lock = null;
		File destFile = null;
		try {
			String aname = name.endsWith("480x480") ? getLocName(name)
					: getPhotoName(name);
			fileName = RichResource.PIC_PATH + "/" + aname;
			file = FileUtil.getFile(fileName + "_temp", true);
			fos = new FileOutputStream(file);
			lock = fos.getChannel().lock();
			byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = is.read(buffer)) > 0) {
				fos.write(buffer, 0, length);
			}
			fos.flush();
			destFile = FileUtil.getFile(fileName, true);
			if (null != destFile && destFile.exists())
				file.renameTo(destFile);
		} catch (IOException e) {
			AppLog.out("FileUtil", "82" + e.getMessage(), AppLog.LEVEL_ERROR);
			return false;
		} finally {
			if (null != lock) {
				try {
					lock.release();
				} catch (IOException e) {
				}
				lock = null;
			}
			if (null != fos) {
				try {
					fos.close();
					fos = null;
				} catch (IOException e) {
					AppLog.out("FileUtil", e.getMessage(), AppLog.LEVEL_ERROR);
					return false;
				}
			}
		}
		return true;
	}

	public static void delPhotoDir(String dir) {
		File file = FileUtil.getFile(dir, false);
		if (null != file && file.exists() && file.isDirectory()) {
			File[] photos = file.listFiles();
			for (File photo : photos) {
				photo.delete();
			}
		}
	}

	/**
	 * 
	 * magnifyBmp()
	 * 
	 * @param path
	 * @param n
	 * @param zoom
	 *            true放大 false缩小
	 * @return Bitmap
	 * @exception
	 * @since 1.0.0
	 */
	public static Bitmap zoomBmp(Bitmap bmp, float n, boolean zoom) {
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(n, n);
		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
				bmp.getHeight(), matrix, true);
		bmp.recycle();
		return newbm;
	}
}
