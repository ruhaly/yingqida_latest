/**
 * ��Ŀ��     rimi
 * �ļ���     UnSyncImageLoader.java
 * �ļ����� 
 * ���ߣ�         wjd
 * ����ʱ�䣺  2011-12-8
 * ��Ȩ���� �� Copyright (C) 2008-2010 RichPeak
 *
 */
package com.yingqida.richplay.baseapi.common;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;

import com.yingqida.richplay.baseapi.AppLog;
import com.yingqida.richplay.baseapi.ImageUtil;
import com.yingqida.richplay.baseapi.StringUtil;

public class UnSyncImageLoader {

	private static final String MY_TAG = "UnSyncImageLoader";

	private static List<String> dUrls = new ArrayList<String>();

	private ICallBack callback;

	public UnSyncImageLoader(ICallBack callback) {
		this.callback = callback;
	}

	public interface ICallBack {
		public void invoke(String name, int code);
	}

	public class DownloadImagesTask extends
			AsyncTask<DownloadEntity, Integer, Integer> {
		@Override
		protected Integer doInBackground(DownloadEntity... params) {
			int size = params.length;
			for (int i = 0; i < size; ++i) {
				if (null != params[i])
					executeImpl(params[i].path, params[i].type);
			}
			return params.length;
		}

		private void executeImpl(String name, int type) {
			if (StringUtil.isNull(name))
				return;
			if (dUrls.contains(name))
				return;
			if (ImageUtil.imgs.containsKey(name))
				return;
			if (ImageUtil.photoExist(RichResource.PIC_PATH,
					ImageUtil.getPhotoName(name)))
				return;
			synchronized (lock2) {
				dUrls.add(name);
				counter.put(name, 0);
			}
			startCount();
			AppLog.out(MY_TAG, "download picUrl : " + name, AppLog.LEVEL_INFO);
			URL url = null;
			try {
				url = new URL(name);
				HttpURLConnection cn = (HttpURLConnection) url.openConnection();
				cn.setDoInput(true);
				cn.setConnectTimeout(30000);
				cn.setDoOutput(false);
				cn.connect();
				if (cn.getResponseCode() == 200) {
					int ret = 0;
					InputStream is = cn.getInputStream();
					if (null == is)
						return;
					boolean m = is.available() < Runtime.getRuntime()
							.freeMemory();
					if (type == DownloadEntity.TYPE_HEAD && m) {
						if (FileUtil.externalStorageAvilable()) {
							ret = ImageUtil.saveLifePhoto(is, name) ? 0 : -1;
						} else {
							Bitmap map = BitmapFactory.decodeStream(is);
							ImageUtil.imgs.put(name, new SoftReference<Bitmap>(
									ImageUtil.getRoundedCornerBitmap(map)));
						}
					} else if (type == DownloadEntity.TYPE_PHOTO && m) {
						if (FileUtil.externalStorageAvilable()) {
							ret = ImageUtil.saveLifePhoto(is, name) ? 0 : -1;
						} else {
							Options p = new Options();
							p.inSampleSize = 1;
							ImageUtil.imgs.put(name, new SoftReference<Bitmap>(
									BitmapFactory.decodeStream(is, null, p)));
						}
					} else if (type == DownloadEntity.TYPE_VIDEO && m) {
					}
					is.close();
					is = null;
					if (null != callback)
						callback.invoke(name, ret);
				}
			} catch (IOException e) {
				AppLog.out(MY_TAG, "194: " + e.getMessage() + "; name == "
						+ name, AppLog.LEVEL_ERROR);
				if (null != callback)
					callback.invoke(name, -1);
			} finally {
				synchronized (lock2) {
					dUrls.remove(name);
					counter.remove(name);
				}
			}
		}
	}

	private static Map<String, Integer> counter = new HashMap<String, Integer>();

	byte[] lock = new byte[0];

	static byte[] lock2 = new byte[0];

	private static boolean running = false;

	private void startCount() {
		if (!running) {
			running = true;
			new Thread(new DownloadCounter(), "picture download counter")
					.start();
		}
		synchronized (lock) {
			lock.notify();
		}
	}

	public static void stopCount() {
		running = false;
	}

	class DownloadCounter implements Runnable {
		@Override
		public void run() {
			while (running) {
				if (counter.isEmpty()) {
					synchronized (lock) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
						}
					}
				} else {
					try {
						Thread.sleep(995l);
					} catch (InterruptedException e) {
					}
					synchronized (lock2) {
						doCount();
					}
				}
			}
		}
	}

	private void doCount() {
		List<String> keys = new ArrayList<String>();
		Iterator<String> keyIt = counter.keySet().iterator();
		while (keyIt.hasNext()) {
			String key = keyIt.next();
			Integer value = counter.get(key);
			if (++value >= 30) {
				dUrls.remove(key);
				keys.add(key);
			} else {
				counter.put(key, value);
			}
		}
		for (int i = 0; i < keys.size(); ++i) {
			counter.remove(keys.get(i));
		}
		keys.clear();
		keys = null;
	}

}
