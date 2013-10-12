package com.yingqida.richplay.baseapi.db;

public class NoticeDaoImpl {

	// static final String TB_NAME = "noticetb";
	//
	// private static final int INDEX_ID = 0;
	//
	// private static final int INDEX_CONTENT = 1;
	//
	// // private static final int INDEX_OWNER = 2;
	//
	// // private static final int INDEX_STATUS = 3;
	//
	// private static final int INDEX_TITLE = 4;
	//
	// private static final int INDEX_TYPE = 5;
	//
	// private static final int INDEX_LADTE = 6;
	//
	// private static final int INDEX_RTIME = 7;
	//
	// public static final String ID = "id";
	//
	// public static final String CONTENT = "content";
	//
	// public static final String OWNER = "owner";
	//
	// public static final String STATUS = "status";
	//
	// public static final String TYPE = "type";
	//
	// public static final String TITLE = "title";
	//
	// public static final String LDATE = "ldate";
	//
	// public static final String REAL_TIME = "real_time";
	//
	// public static void insertNotice(NoticeEntity notice,
	// SQLiteDatabase database, String owner) {
	// if (null == notice || null == database)
	// return;
	// ContentValues value = new ContentValues();
	// value.put(CONTENT, notice.getContent());
	// value.put(OWNER, owner);
	// value.put(TYPE, notice.getType());
	// value.put(TITLE, notice.getTitle());
	// value.put(LDATE, notice.getTime());
	// value.put(REAL_TIME, notice.getRealTime());
	// database.insert(TB_NAME, null, value);
	// value.clear();
	// value = null;
	// }
	//
	// public static void getNotices(List<NoticeEntity> ret, SQLiteDatabase db,
	// String cdt) {
	// if (null == db)
	// return;
	// Cursor cursor = db.rawQuery("select * from " + TB_NAME + " where "
	// + cdt + " order by " + ID + " desc", null);
	//
	// if (null != cursor && cursor.moveToFirst()) {
	// if (null == ret)
	// ret = new ArrayList<NoticeEntity>(cursor.getCount());
	// else
	// ret.clear();
	// do {
	// NoticeEntity notice = new NoticeEntity();
	// notice.setId(cursor.getInt(INDEX_ID));
	// notice.setContent(cursor.getString(INDEX_CONTENT));
	// notice.setType(cursor.getInt(INDEX_TYPE));
	// notice.setTitle(cursor.getString(INDEX_TITLE));
	// notice.setTime(cursor.getString(INDEX_LADTE));
	// notice.setRealTime(cursor.getLong(INDEX_RTIME));
	// ret.add(notice);
	// } while (cursor.moveToNext());
	// }
	// if (null != cursor) {
	// cursor.close();
	// cursor = null;
	// }
	// }
	//
	// public static int getNoticeCount(SQLiteDatabase db, String cdt) {
	// int count = 0;
	// if (null == db)
	// return count;
	// Cursor cursor = db.rawQuery("select * from " + TB_NAME + " where "
	// + cdt + " order by " + ID + " desc", null);
	//
	// if (null != cursor && cursor.moveToFirst()) {
	// count = cursor.getCount();
	// }
	// if (null != cursor) {
	// cursor.close();
	// cursor = null;
	// }
	// return count;
	// }
	//
	// public static int getUnreadCount(SQLiteDatabase db, String uid) {
	// int ret = 0;
	// if (null == db)
	// return 0;
	// String sql = String.format(
	// "select * from  %s where %s='%s' and %s = %d", TB_NAME, OWNER,
	// uid, STATUS, 0);
	// Cursor cursor = db.rawQuery(sql, null);
	//
	// if (null != cursor && cursor.moveToFirst()) {
	// ret = cursor.getCount();
	// }
	// if (null != cursor) {
	// cursor.close();
	// cursor = null;
	// }
	// return ret;
	// }
	//
	// public static void indicateRead(SQLiteDatabase db, String uid, String
	// cdt) {
	// String sql = String.format("update  %s set %s = %d where  %s", TB_NAME,
	// STATUS, 1, cdt);
	// db.execSQL(sql);
	// }
	//
	// public static void delNotice(int id, SQLiteDatabase database) {
	// if (null == database)
	// return;
	// database.execSQL(String.format("delete from %s where id=%d", TB_NAME,
	// id));
	// }
	//
	// public static void getEarlierNotices(List<List<NoticeEntity>> ret,
	// SQLiteDatabase db, String cdt, String uid) {
	// if (null == db)
	// return;
	// String sql =
	// "select distinct(%s) from %s where %s group by %s order by %s desc";
	//
	// Cursor cursor = db.rawQuery(
	// String.format(sql, LDATE, TB_NAME, cdt, LDATE, LDATE), null);
	//
	// if (null != cursor && cursor.moveToFirst()) {
	// for (int i = 0; i < cursor.getCount(); ++i) {
	// ret.add(new ArrayList<NoticeEntity>());
	// }
	// int i = 0;
	// do {
	// cdt = String.format(" %s = '%s' and %s = '%s' ",
	// NoticeDaoImpl.LDATE, cursor.getString(0),
	// NoticeDaoImpl.OWNER, uid);
	// getNotices(ret.get(i), db, cdt);
	// i++;
	// } while (cursor.moveToNext());
	// }
	// if (null != cursor) {
	// cursor.close();
	// cursor = null;
	// }
	// }
}
