package com.yingqida.richplay.baseapi.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.os.Environment;

public class FileUtil {

	public static void initDir() {
		if (externalStorageAvilable()) {
			File rstoreDir = new File(
					Environment.getExternalStorageDirectory(),
					RichResource.F_PATH);
			if (!rstoreDir.exists())
				rstoreDir.mkdir();
			File lifePhotoDir = new File(
					Environment.getExternalStorageDirectory(),
					RichResource.PIC_PATH);
			if (!lifePhotoDir.exists())
				lifePhotoDir.mkdir();
		}
	}

	/**
	 * 根據文件相对路径查找文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static File getFile(String fileName) {
		File file = null;
		if (externalStorageAvilable()) {
			file = new File(Environment.getExternalStorageDirectory(), fileName);
		} else {
			file = new File(Environment.getDataDirectory(), fileName);
		}
		return file;
	}

	public static File getFile(String fileName, boolean needToCreate) {
		File file = null;
		if (externalStorageAvilable()) {
			file = new File(Environment.getExternalStorageDirectory(), fileName);
			if (!file.exists() && needToCreate)
				try {
					file.createNewFile();
				} catch (IOException e) {
					file = null;
				}
		}
		return file;
	}

	public static boolean externalStorageAvilable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	public static String getBasePath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public static void removeFile(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}

		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}

			for (int i = 0; i < childFiles.length; i++) {
				removeFile(childFiles[i]);
			}
			file.delete();
		}
	}

	public static String getVSignPath(String url) {
		String ret = null;
		try {
			String[] rets = url.split("/");
			ret = rets[rets.length - 1];
		} catch (Exception e) {
			ret = null;
		}
		return ret;
	}

	public static void delDirectory(String dir) {
		File file = FileUtil.getFile(dir, false);
		if (null != file && file.exists() && file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				if (f.isFile())
					f.delete();
			}
		}
	}

	public static boolean isSchool(Context context) {
		boolean ret = false;
		try {
			InputStream is = context.getAssets().open("config");
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			if (reader.readLine().charAt(0) == '1')
				ret = true;
			reader.close();
			is.close();
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}
}
