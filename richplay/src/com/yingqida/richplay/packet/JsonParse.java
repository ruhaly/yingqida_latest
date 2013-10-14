package com.yingqida.richplay.packet;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.os.Build;
import com.yingqida.richplay.baseapi.common.User;

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
}
