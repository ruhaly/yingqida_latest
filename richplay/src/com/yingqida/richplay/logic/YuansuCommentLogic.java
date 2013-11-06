package com.yingqida.richplay.logic;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.client.RequestParams;
import com.yingqida.richplay.baseapi.http.HttpSenderUtils;
import com.yingqida.richplay.baseapi.http.ResponseCode;
import com.yingqida.richplay.entity.Comment;
import com.yingqida.richplay.entity.Yuansu;
import com.yingqida.richplay.packet.HttpAction;
import com.yingqida.richplay.packet.JsonParse;
import com.yingqida.richplay.packet.RequestId;

public class YuansuCommentLogic extends SuperLogic implements HttpAction {
	private static YuansuCommentLogic ins;

	private HttpHandler<String> httpHanlder;

	public int curPage = 1;
	public String perPage = "10";
	public int targetCurPage = 1;
	// 0刷新；1加载更多
	public int type = 0;

	public synchronized static YuansuCommentLogic getInstance() {
		if (null == ins) {
			ins = new YuansuCommentLogic();
		}
		return ins;
	}

	public Yuansu ys = new Yuansu();

	@Override
	public void handleHttpResponse(String response, int rspCode, int requestId) {

	}

	@Override
	public void handleHttpResponse(String response, int requestId,
			InputStream is) {

		switch (requestId) {
		case RequestId.YUANSU_COMMENT: {
			httpYuansuCommentResponse(response);
			break;
		}
		default:
			break;
		}

	}

	public void sendYuansuCommentRequest(String remark_token, String remark_id,
			int type) {
		this.type = type;
		if (type == 0) {
			targetCurPage = 1;
		} else {
			targetCurPage = curPage + 1;
		}
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("remark_token", remark_token);
		params.addQueryStringParameter("remark_id", remark_id);
		params.addQueryStringParameter("cur_page", targetCurPage + "");
		params.addQueryStringParameter("per_page", perPage);
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_YUANSU_COMMENT,
				params, HttpSenderUtils.METHOD_GET, httpUtils,
				RequestId.YUANSU_COMMENT, this, false);

	}

	public void stopReqeust() {
		httpHanlder.stop();
	}

	public void httpYuansuCommentResponse(String response) {

		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				Yuansu temp = JsonParse.parseYuansuCommentRes(response);
				if (null != temp && temp.getCommentList() != null
						&& !temp.getCommentList().isEmpty()) {
					ys.setFollowState(temp.getFollowState());
					ys.setId(temp.getId());
					if (type == 0)
						ys.getCommentList().clear();
					ys.getCommentList().addAll(temp.getCommentList());
				}
				if (null == ys.getCommentList()) {
					ys.commentList = new ArrayList<Comment>();
				}
				if (!temp.getCommentList().isEmpty()) {
					curPage = targetCurPage;
				}
				handler.sendEmptyMessage(YUANSU_COMMENT_SUCCESS_MSGWHAT);
			} else {
				handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
		}

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

}
