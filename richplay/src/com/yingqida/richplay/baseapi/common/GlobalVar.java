package com.yingqida.richplay.baseapi.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.yingqida.richplay.baseapi.common.RichResource.ShareKey;
import com.yingqida.richplay.baseapi.db.DatabaseManager;

public class GlobalVar {
	public static GlobalVar ins = new GlobalVar();

	private User user;

	private DatabaseManager dbm;

	public void initDbm(Context context) {
		dbm = new DatabaseManager(context);
	}

	public DatabaseManager getDbm(Context context) {
		if (null == dbm)
			initDbm(context);
		return dbm;
	}

	public User getUser(SharedPreferences share) {
		user = restoreUser(share);
		return user;
	}

	/**
	 * 将用户信息存储到文件
	 * 
	 * @param share
	 */
	public void storeUser(SharedPreferences share, User user) {
		share.edit().putString(ShareKey.REMARK_TOKEN, user.remarkToken)
				.putString(ShareKey.UID, user.uid)
				.putString(ShareKey.ACCOUNT, user.account)
				.putString(ShareKey.PWD, user.pwd)
				.putString(ShareKey.NICKNAME, user.nickName)
				.putInt(ShareKey.SEX, user.sex).commit();
	}

	/**
	 * 从属性文件中恢复user信息
	 * 
	 * @param share
	 * @return
	 */
	public User restoreUser(SharedPreferences share) {
		User user = new User();
		user.remarkToken = share.getString(ShareKey.REMARK_TOKEN, "");
		user.uid = share.getString(ShareKey.UID, "");
		user.account = share.getString(ShareKey.ACCOUNT, "");
		user.pwd = share.getString(ShareKey.PWD, "");
		user.nickName = share.getString(ShareKey.NICKNAME, "");
		user.sex = share.getInt(ShareKey.SEX, 1);
		return user;
	}

	/**
	 * 将文件中的用户信息删除
	 * 
	 * @param share
	 */
	public void clearUserDate(SharedPreferences share) {
		share.edit().putString(ShareKey.REMARK_TOKEN, "")
				.putString(ShareKey.UID, "").putString(ShareKey.ACCOUNT, "")
				.putString(ShareKey.PWD, "").putString(ShareKey.NICKNAME, "")
				.putInt(ShareKey.SEX, 1).commit();
	}

	public void setUser(User user) {
		this.user = user;
	}
}
