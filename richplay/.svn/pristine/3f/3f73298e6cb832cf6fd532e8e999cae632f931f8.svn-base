package com.yingqida.richplay.packet;

import com.yingqida.richplay.baseapi.http.HttpChannel;
import com.yingqida.richplay.baseapi.http.HttpRequest;
import com.yingqida.richplay.entity.RleasePicEntity;
import com.yingqida.richplay.service.NetWorkProxy;

public class HttpSender implements HttpAction {

	/**
	 * 
	 * requestReleasePic(发布图片请求)
	 * 
	 * @param proxy
	 * @param reqeust
	 * @param entity
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void requestReleasePic(NetWorkProxy proxy, String session,
			HttpRequest request, RleasePicEntity entity) {
		if (null == proxy) {
			return;
		}
		// request.setBody(HttpXml.createReleasePicReqeustXml(entity));
		request.setEntity(HttpClient.createReleasePicEntity(entity));
		proxy.sendHttpRequest(ACTION_RELEASE_PIC, request,
				HttpChannel.METHOD_POST, true);

	}

	/**
	 * 图片墙详情 requestWallPicInfo()
	 * 
	 * @param proxy
	 * @param session
	 * @param request
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void requestWallPicInfo(NetWorkProxy proxy, String session,
			HttpRequest request, String pid) {
		if (null == proxy) {
			return;
		}
		request.setBody(HttpXml.createWallPicInofRequestXml(pid));
		proxy.sendHttpRequest(ACTION_WALL_PIC_INFO, request,
				HttpChannel.METHOD_POST, false);
	}

	public static void requestWallPic(NetWorkProxy proxy, String session,
			HttpRequest request, String uid, int curp, int pnum) {
		if (null == proxy) {
			return;
		}
		request.setBody(HttpXml.createWallPicRequestXml(uid, curp, pnum));
		proxy.sendHttpRequest(ACTION_WALL_PIC, request,
				HttpChannel.METHOD_POST, false);
	}
}