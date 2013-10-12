package com.yingqida.richplay.baseapi.db;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yingqida.richplay.entity.WallPicItem;

/**
 * 
 * 
 * WallPicDaoImpl user-ruhaly 2013-7-18 下午4:03:49
 * 
 * @version 1.0.0
 * 
 */
public class WallPicDaoImpl {

	static final String TB_NAME = "wallpictb";

	public static final String UUID = "uuid";

	public static final String ID = "id";

	public static final String PIC_URL = "pic_url";

	public static final String HEAD_URL = "head_url";

	public static final String NAME = "name";

	public static final String MSG = "msg";

	public static final String OWNER = "owner";

	/**
	 * 
	 * insertRecord(这里用一句话描述这个方法的作用)
	 * 
	 * @param wpiList
	 * @param database
	 * @param owner
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void insertRecord(List<WallPicItem> wpiList,
			SQLiteDatabase database, String owner) {
		if (null == wpiList || wpiList.isEmpty() || null == database)
			return;

		for (int i = 0; i < wpiList.size(); i++) {
			ContentValues value = new ContentValues();
			value.put(ID, wpiList.get(i).getId());
			value.put(PIC_URL, wpiList.get(i).getPicUrl());
			value.put(HEAD_URL, wpiList.get(i).getHeadUrl());
			value.put(NAME, wpiList.get(i).getName());
			value.put(MSG, wpiList.get(i).getMsg());
			value.put(OWNER, owner);
			database.insert(TB_NAME, null, value);
			value.clear();
			value = null;
		}

	}

	/**
	 * 
	 * getWallPics(这里用一句话描述这个方法的作用)
	 * 
	 * @param ret
	 * @param db
	 * @param cdt
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void getWallPics(List<WallPicItem> ret, SQLiteDatabase db,
			String cdt) {
		ret.clear();
		if (null == db)
			return;
		Cursor cursor = db.rawQuery("select * from " + TB_NAME + " order by "
				+ UUID + " desc", null);

		if (null != cursor && cursor.moveToFirst()) {
			do {
				WallPicItem wpi = new WallPicItem();
				wpi.setId(cursor.getString(cursor.getColumnIndex(ID)));
				wpi.setPicUrl(cursor.getString(cursor.getColumnIndex(PIC_URL)));
				wpi.setHeadUrl(cursor.getString(cursor.getColumnIndex(HEAD_URL)));
				wpi.setName(cursor.getString(cursor.getColumnIndex(NAME)));
				wpi.setMsg(cursor.getString(cursor.getColumnIndex(MSG)));
				ret.add(wpi);
			} while (cursor.moveToNext());
		}

		if (null != cursor) {
			cursor.close();
			cursor = null;
		}
	}

	/**
	 * 
	 * delRecord(这里用一句话描述这个方法的作用)
	 * 
	 * @param id
	 * @param database
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void delRecord(String id, SQLiteDatabase database) {
		if (null == database)
			return;
		database.execSQL(String.format("delete from %s where id=%s", TB_NAME,
				id));
	}

	class Duser implements Observer {

		@Override
		public void update(Observable observable, Object data) {

		}

	}

	class User extends Observable {
		public void notifyDUser() {
			notifyObservers("12");
			user.addObserver(new Duser());
		}

		User user = new User();

	}
}
