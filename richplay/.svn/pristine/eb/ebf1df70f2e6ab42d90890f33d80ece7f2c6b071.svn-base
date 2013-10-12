package com.yingqida.richplay.baseapi.common;

import android.content.Context;
import android.content.SharedPreferences;

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
		if (null == user && null != share)
			user = User.restoreUser(share);
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
