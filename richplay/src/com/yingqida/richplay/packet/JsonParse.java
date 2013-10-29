package com.yingqida.richplay.packet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.os.Build;

import com.yingqida.richplay.baseapi.common.User;
import com.yingqida.richplay.entity.Comment;
import com.yingqida.richplay.entity.Yuansu;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class JsonParse extends BaseJson {

	public static User parseLoginRes(String response) {
		if (null == response || response.isEmpty()) {
			return null;
		}
		User user = new User();
		try {
			JSONObject job = new JSONObject(response).optJSONObject("data");
			if (null != job) {
				user.setUid(job.optString("uid"));
				user.setRemarkToken(job.optString("remark_token"));
			}
		} catch (JSONException e) {
			user = null;
			e.printStackTrace();
		}
		return user;
	}

	public static List<Yuansu> parsePageHomeYuansuRes(String response) {
		if (null == response || response.isEmpty()) {
			return null;
		}
		List<Yuansu> list = new ArrayList<Yuansu>();
		try {
			JSONArray jobarray = new JSONObject(response).optJSONArray("data");
			if (null != jobarray) {
				for (int i = 0; i < jobarray.length(); i++) {
					JSONObject job = jobarray.getJSONObject(i);
					Yuansu y = new Yuansu();
					y.setId(job.optString("remark_id"));
					y.setRemarkContent(job.optString("remark_content"));
					y.setTime(job.optString("create_time"));
					y.setLabel(job.optString("label"));
					y.getUser().setUid(job.optString("user_id"));
					y.getUser().setName(job.optString("username"));
					y.getUser().setComment_content(
							job.optString("comment_content"));
					list.add(y);
				}
			}
		} catch (JSONException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}

	public static List<User> parseGetUserRes(String response) {
		List<User> list = new ArrayList<User>();
		if (null == response || response.isEmpty()) {
			return null;
		}
		try {
			JSONArray jsonArray = new JSONObject(response).optJSONArray("data");
			if (null != jsonArray)
				for (int i = 0; i < jsonArray.length(); i++) {
					User user = new User();
					JSONObject job = jsonArray.getJSONObject(i);
					user.setUid(job.optString("uid"));
					user.setName(job.optString("username"));
					user.setIs_avatar(job.optString("is_avatar"));
					list.add(user);
				}
		} catch (JSONException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}

	public static List<Comment> parseYuansuCommentRes(String response) {
		List<Comment> list = new ArrayList<Comment>();
		if (null == response || response.isEmpty()) {
			return null;
		}
		try {
			JSONArray jsonArray = new JSONObject(response).optJSONArray("data");
			if (null != jsonArray)
				for (int i = 0; i < jsonArray.length(); i++) {
					Comment c = new Comment();
					JSONObject job = jsonArray.getJSONObject(i);
					c.setCid(job.optString("comment_id"));
					c.setUname(job.getString("username"));
					c.setUid(job.optString("uid"));
					c.setContent(job.optString("comment_content"));
					c.setHeaderUrl(job.optString("hurl"));
					c.setTime(job.optString("create_time"));
					list.add(c);
				}
		} catch (JSONException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}

	public static List<Yuansu> parseFayanRes(String response) {
		if (null == response || response.isEmpty()) {
			return null;
		}
		List<Yuansu> list = new ArrayList<Yuansu>();
		try {
			JSONArray jobarray = new JSONObject(response).optJSONArray("data");
			if (null != jobarray) {
				for (int i = 0; i < jobarray.length(); i++) {
					JSONObject job = jobarray.getJSONObject(i);
					Yuansu y = new Yuansu();
					y.setId(job.optString("remark_id"));
					y.setRemarkContent(job.optString("remark_content"));
					y.setTime(job.optString("create_time"));
					y.setLabel(job.optString("label"));
					y.getUser().setUid(job.optString("user_id"));
					y.getUser().setName(job.optString("username"));
					y.getUser().setComment_content(
							job.optString("comment_content"));
					list.add(y);
				}
			}
		} catch (JSONException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}

	public static String parseRemarkTokenRes(String response) {
		String remarkToken = "";
		if (null == response || response.isEmpty()) {
			return null;
		}
		try {
			JSONObject job = new JSONObject(response).optJSONObject("data");
			if (null != job) {
				remarkToken = job.optString("remark_token");
			}
		} catch (JSONException e) {
			remarkToken = null;
			e.printStackTrace();
		}
		return remarkToken;
	}

	public static List<User> parseFollowerRes(String response) {
		List<User> list = new ArrayList<User>();
		if (null == response || response.isEmpty()) {
			return null;
		}
		try {
			JSONArray jsonArray = new JSONObject(response).optJSONArray("data");
			if (null != jsonArray)
				for (int i = 0; i < jsonArray.length(); i++) {
					User user = new User();
					JSONObject job = jsonArray.getJSONObject(i);
					user.setUid(job.optString("uid"));
					user.setName(job.optString("username"));
					user.setIs_avatar(job.optString("is_avatar"));
					list.add(user);
				}
		} catch (JSONException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}

	public static List<Yuansu> parseGuanzhuYuansuRes(String response) {
		if (null == response || response.isEmpty()) {
			return null;
		}
		List<Yuansu> list = new ArrayList<Yuansu>();
		try {
			JSONArray jobarray = new JSONObject(response).optJSONArray("data");
			if (null != jobarray) {
				for (int i = 0; i < jobarray.length(); i++) {
					JSONObject job = jobarray.getJSONObject(i);
					Yuansu y = new Yuansu();
					y.setId(job.optString("remark_id"));
					y.setRemarkContent(job.optString("remark_content"));
					y.setLabel(job.optString("label"));
					list.add(y);
				}
			}
		} catch (JSONException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}
}
