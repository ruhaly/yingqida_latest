package com.yingqida.richplay.activity.common;

import android.os.Handler;
import android.os.Message;

public abstract class HandleActivity extends DialogActivity {

	/**
	 * mHandler
	 */
	public MewwHandler mHandler = new MewwHandler();

	public class MewwHandler extends Handler {
		/*
		 * (non-Javadoc) (覆盖方法) 方法名称： handleMessage 作者： ruhaly 方法描述：
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			handleMsg(msg);
		}
	}

	/**
	 * 方法名称： handleMsg 作者： ruhaly 方法描述： 处理消息 输入参数： @param msg 返回类型： void
	 */
	public void handleMsg(Message msg) {

	}
}