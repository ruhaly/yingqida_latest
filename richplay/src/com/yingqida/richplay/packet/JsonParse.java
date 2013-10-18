package com.yingqida.richplay.packet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.os.Build;

import com.yingqida.richplay.baseapi.common.User;
import com.yingqida.richplay.entity.Yuansu;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class JsonParse extends BaseJson {

	public static User parseLoginRes(String response) {
		if (null == response || response.isEmpty()) {
			return null;
		}
		User user = new User();
		try {
			JSONObject job = new JSONObject(response).getJSONObject("data");
			user.setUid(job.getString("uid"));
			user.setRemarkToken(job.getString("remark_token"));
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
			JSONArray jobarray = new JSONObject(response).getJSONArray("data");
			for (int i = 0; i < jobarray.length(); i++) {
				JSONObject job = jobarray.getJSONObject(i);
				Yuansu y = new Yuansu();
				y.setContent(job.getString("content"));
				y.setTime(job.getString("create_time"));
				y.getUser().setRemarkToken(job.getString("remark_id"));
				y.getUser().setUid(job.getString("uid"));
				y.getUser().setName(job.getString("uid"));
				list.add(y);
			}
		} catch (JSONException e) {
			list = null;
			e.printStackTrace();
		}
		return list;
	}
}
