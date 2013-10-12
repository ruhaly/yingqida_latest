package com.yingqida.richplay.baseapi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

	public static final int DB_VERSION = 2;

	public static final String MLG_DB_NAME = "MLG_DB_NAME";

	public DatabaseManager(Context context) {
		super(context, MLG_DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		initWallPicTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	/**
	 * 
	 * initWallPicTable(这里用一句话描述这个方法的作用)
	 * 
	 * @param db
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private static void initWallPicTable(SQLiteDatabase db) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("create table " + WallPicDaoImpl.TB_NAME + "(");
		buffer.append(WallPicDaoImpl.UUID
				+ " integer primary key autoincrement,");
		buffer.append(WallPicDaoImpl.ID + " varchar,");
		buffer.append(WallPicDaoImpl.PIC_URL + " varchar ,");
		buffer.append(WallPicDaoImpl.HEAD_URL + " varchar not null,");
		buffer.append(WallPicDaoImpl.NAME + " varchar,");
		buffer.append(WallPicDaoImpl.MSG + " varchar,");
		buffer.append(WallPicDaoImpl.OWNER + " varchar)");
		db.execSQL(buffer.toString());
		buffer = null;
	}

}
