package com.yingqida.richplay.baseapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;

public class AppLog {
	public static final byte LEVEL_DEBUG = 0;
	public static final byte LEVEL_ERROR = 2;
	public static final byte LEVEL_INFO = 1;
	// private static final String LOG_TAG = "realme";
	private static boolean fileDebug = true;

	public static void logToFile(String time, Object msg) {
		if (null == msg) {
			return;
		}
		if (fileDebug) {
			writeToSDCard("richplay.log",
					"[" + time + "]" + "----->" + msg.toString() + "\n");
		}
	}

	public static void out(String tag, Object msg, byte logLevel) {
		// if (android.util.Log.isLoggable(LOG_TAG, android.util.Log.DEBUG))
		{
			if (fileDebug) {
				if (null != msg && null != tag) {
					switch (logLevel) {
					case LEVEL_DEBUG: {
						android.util.Log.d(tag, msg.toString());
						break;
					}
					case LEVEL_INFO: {
						android.util.Log.i(tag, msg.toString());
						break;
					}
					case LEVEL_ERROR: {
						android.util.Log.e(tag, msg.toString());
						break;
					}
					default: {
						android.util.Log.i(tag, msg.toString());
						break;
					}
					}
				}
			}
		}
	}

	private static void writeToSDCard(String fileName, String text) {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			FileOutputStream fileOutputStream = null;
			FileInputStream fileInputStream = null;
			try {
				File file = new File(Environment.getExternalStorageDirectory(),
						fileName);

				fileOutputStream = new FileOutputStream(file, true);
				fileOutputStream.write(text.getBytes());
			} catch (Exception e) {

			} finally {
				if (fileOutputStream != null) {
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fileInputStream != null) {
					try {
						fileInputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
}