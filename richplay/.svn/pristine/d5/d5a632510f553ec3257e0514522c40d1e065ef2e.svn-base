package com.yingqida.richplay.baseapi.common;

import java.lang.Thread.UncaughtExceptionHandler;
import java.sql.Timestamp;

import com.yingqida.richplay.baseapi.AppLog;

public class CrashHandler implements UncaughtExceptionHandler {

	public static CrashHandler handler = new CrashHandler();

	private UncaughtExceptionHandler defaultHandler;

	private String version = "v1.0.0";

	private CrashHandler() {

	}

	public void init(String version) {
		this.version = version;
		defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		StringBuilder builder = new StringBuilder(version);
		builder.append(ex.toString() + "\n");
		builder.append(ex.getLocalizedMessage() + "\n");
		StackTraceElement[] stack = ex.getStackTrace();
		for (StackTraceElement element : stack) {
			builder.append(element.toString() + "\n");
		}
		Throwable throwable = ex.getCause();
		while (null != throwable) {
			StackTraceElement[] currentStack = throwable.getStackTrace();
			for (StackTraceElement element : currentStack) {
				builder.append(element.toString() + "\n");
			}
			throwable = throwable.getCause();
		}
		AppLog.logToFile(new Timestamp(System.currentTimeMillis()).toString(),
				builder);
		defaultHandler.uncaughtException(thread, ex);
	}
}
