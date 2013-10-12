package com.yingqida.richplay.packet;

import com.yingqida.richplay.baseapi.DateUtil;
import com.yingqida.richplay.baseapi.StringUtil;
import com.yingqida.richplay.baseapi.udp.UdpMessage;
import com.yingqida.richplay.baseapi.udp.UdpProxy;
import com.yingqida.richplay.baseapi.udp.UdpRequest;

/**
 * Udp Sender
 * 
 * @author wjd
 * 
 */
public class UdpSender implements UdpCommond {

	/**
	 * ENTER ROOM
	 * 
	 * @param proxy
	 * @param request
	 * @param session
	 * @param confId
	 * @param nickname
	 * @param role
	 * @param tag
	 * @param password
	 */
	public static void enterRoomRequest(UdpRequest request, long session,
			String frm, String to, String roomId) {
		if (request == null) {
			return;
		}
		UdpMessage message = new UdpMessage();
		message.setModuleId(YJZQ_MODULE_ID);
		message.setCommond(YJZQ_03_ACCEPT_INVITE);
		message.setSession(session);
		message.setBody(
				new StringBuilder("<iq><frm nm=\"").append(frm)
						.append("\" id=\"").append(roomId)
						.append("\"/><to nm=\"").append(to)
						.append("\" /></iq>\n").toString(), request.key, false);
		message.setLength((short) message.getBody().getBytes().length);
		request.setMessage(message);
		UdpProxy.newInstance().sendUdpMessage(request, null);
	}

	/**
	 * EXIT ROOM
	 * 
	 * @param proxy
	 * @param request
	 * @param session
	 * @param confId
	 */
	public static void exitRoomRequest(UdpRequest request, long session,
			String confId) {
		if (request == null) {
			return;
		}
		UdpMessage message = new UdpMessage();
		message.setModuleId(MLG_MODULE_ID);
		message.setCommond(MLG_30_EXIT_CHAT_ROOM);
		message.setSession(session);
		message.setBody(new StringBuilder("<iq confid=\"").append(confId)
				.append("\" />\n").toString(), request.key, false);
		message.setLength((short) message.getBody().getBytes().length);
		request.setMessage(message);
		UdpProxy.newInstance().sendUdpMessage(request, null);
	}

	public static void subscribeMediaRequest(UdpRequest request, long session,
			int idx) {
		if (request == null) {
			return;
		}
		UdpMessage message = new UdpMessage();
		message.setModuleId(PDU_MODULE_ID);
		message.setCommond(SUBSCRIBE_MEDIA);
		message.setSession(session);
		message.setBody(
				new StringBuilder("<iq idx=\"").append(idx).append("\"/>\n")
						.toString(), request.key, false);
		message.setLength((short) message.getBody().getBytes().length);
		request.setMessage(message);
		UdpProxy.newInstance().sendUdpMessage(request, null);
	}

	public static void sendHeartBeatToPdu(UdpRequest request, long session) {
		if (request == null)
			return;
		UdpMessage message = new UdpMessage();
		message.setModuleId(UdpCommond.PDU_MODULE_ID);
		message.setCommond(PDU_HEART_BEAT);
		message.setSession(session);

		message.setBody("0", request.key, false);

		message.setLength((short) message.getBody().getBytes().length);
		request.setMessage(message);
		UdpProxy.newInstance().sendUdpMessage(request, null);
	}

	public static void rowMediaRequest(UdpRequest request, long session) {
		if (request == null) {
			return;
		}
		UdpMessage message = new UdpMessage();
		message.setModuleId(PDU_MODULE_ID);
		message.setCommond(PDU_05_ROW_MICRO);
		message.setSession(session);
		message.setBody("", request.key, false);
		message.setLength((short) message.getBody().getBytes().length);
		request.setMessage(message);
		UdpProxy.newInstance().sendUdpMessage(request, null);
	}

	public static void grantMicroResponse(UdpRequest request, long session,
			byte status, byte seq) {
		if (request == null)
			return;
		pushMessageResponse(request, session, status, PDU_03_GRANT_MICRO,
				PDU_MODULE_ID, seq);
	}

	public static void sendPrivateMessage(UdpRequest request, long session,
			String fm, String to, String nn, String msgBody, int type) {
		if (request == null) {
			return;
		}
		UdpMessage message = new UdpMessage();
		message.setModuleId(PDU_MODULE_ID);
		message.setCommond(PDU_31_PRIVATE_MSG);
		message.setSession(session);
		message.setBody(
				new StringBuilder("<iq fm='").append(fm).append("' nn='")
						.append(nn).append("' to='").append(to).append("' >")
						.append("<msg><![CDATA[").append(msgBody)
						.append("]]></msg>").append("<type>").append(type)
						.append("</type></iq>\n").toString(), request.key,
				false);
		message.setLength((short) message.getBody().getBytes().length);
		request.setMessage(message);
		UdpProxy.newInstance().sendUdpMessage(request, null);
	}

	public static void sendConsumeMessage(UdpRequest request, long session,
			String fm, String to, String nn, String num, String type) {
		if (request == null) {
			return;
		}
		UdpMessage message = new UdpMessage();
		message.setModuleId(PDU_MODULE_ID);
		message.setCommond(PDU_32_CONSUME_MSG);
		message.setSession(session);
		message.setBody(
				new StringBuilder("<iq fm=\"").append(fm).append("\" nn=\"")
						.append(nn).append("\" to=\"").append(to)
						.append("\" pid = \"").append(3).append("\" type=\"")
						.append(type).append("\" num = \"").append(num)
						.append("\" />\n").toString(), request.key, false);
		message.setLength((short) message.getBody().getBytes().length);
		request.setMessage(message);
		UdpProxy.newInstance().sendUdpMessage(request, null);
	}

	/**
	 * ������������
	 * 
	 * @param request
	 * @param session
	 * @param username
	 * @param gps
	 * @param cellid
	 * @param key
	 */
	public static void sendHeartBeat(UdpRequest request, long session,
			String username, String loc, String key, byte moduleId,
			byte commondId, String pid) {
		if (request == null)
			return;
		UdpMessage message = new UdpMessage();
		message.setModuleId(moduleId);
		message.setCommond(commondId);
		message.setSession(session);
		// message.setBody(
		// new StringBuffer("<root><user>").append(username)
		// .append("</user>").append("<loc lat=\'")
		// .append(loc.split(",")[0]).append("\' lgt=\'")
		// .append(loc.split(",")[1]).append("\'>").append(loc)
		// .append("</loc>").append("<lt>").append("2")
		// .append("</lt>").append("</root>\n").toString(),
		// request.key, false);

		if (!StringUtil.isNull(loc))
			message.setBody(
					new StringBuffer("<root><user>").append(username)
							.append("</user>").append("<loc lat=\'")
							.append(loc.split(",")[0]).append("\' lgt=\'")
							.append(loc.split(",")[1]).append("\'>")
							.append(loc).append("</loc>").append("<proid>")
							.append(pid).append("</proid>").append("<lt>")
							.append(2).append("</lt>").append("</root>\n")
							.toString(), request.key, false);
		else
			message.setBody(new StringBuffer("<root><user>").append(username)
					.append("</user>").append("<lt>").append(2).append("</lt>")
					.append("</root>\n").toString(), request.key, false);

		message.setLength((short) message.getBody().getBytes().length);
		request.setMessage(message);
		UdpProxy.newInstance().sendUdpMessage(request, null);
	}

	public static void sendRetriveAppListRequest(UdpRequest request,
			long session, int curPage, int pageSize) {
		if (request == null)
			return;
		UdpMessage message = new UdpMessage();
		message.setModuleId(UdpCommond.AA_MODULE_ID);
		message.setCommond(UdpCommond.AA_11_MORE_APP);
		message.setSession(session);
		message.setBody(
				new StringBuffer("<Root><ts>")
						.append(DateUtil.format(System.currentTimeMillis(),
								DateUtil.FMT_YMDHMS)).append("</ts><capp>")
						.append(2).append("</capp><ctype>10</ctype>")
						.append("<pnum>").append(curPage).append("</pnum>")
						.append("<psize>").append(pageSize).append("</psize>")
						.append("</Root>\n").toString(), request.key, false);
		message.setLength((short) message.getBody().getBytes().length);
		request.setMessage(message);
		UdpProxy.newInstance().sendUdpMessage(request, null);
	}

	public static void sendRetriveAppDetailRequest(UdpRequest request,
			long session, String aid) {
		if (request == null)
			return;
		UdpMessage message = new UdpMessage();
		message.setModuleId(UdpCommond.SYS_MODULE_ID);
		message.setCommond(UdpCommond.SYS_01_NOTICE);
		message.setSession(session);
		message.setBody(
				new StringBuffer("<root><aid>").append(aid)
						.append("</aid><ctype>").append(10)
						.append("</ctype></root>\n").toString(), request.key,
				false);
		message.setLength((short) message.getBody().getBytes().length);
		request.setMessage(message);
		UdpProxy.newInstance().sendUdpMessage(request, null);
	}

	public static void sendFeedbackRequest(UdpRequest request, long session,
			String user, String content) {
		if (request == null)
			return;
		UdpMessage message = new UdpMessage();
		message.setModuleId(UdpCommond.AA_MODULE_ID);
		message.setCommond(UdpCommond.AA_13_FEEDBACK);
		message.setSession(session);
		message.setBody(
				new StringBuffer("<root><user>").append(user)
						.append("</user><ctype>").append(10)
						.append("</ctype><pid>DZSW</pid><opin><![CDATA[")
						.append(content).append("]]></opin></root>\n")
						.toString(), request.key, false);
		message.setLength((short) message.getBody().getBytes().length);
		request.setMessage(message);
		UdpProxy.newInstance().sendUdpMessage(request, null);
	}

	public static void sendMailMessage(UdpRequest request, long session,
			String fromId, String fromNickname, String toId, String msg,
			int type, String url) {
		if (request == null)
			return;
		UdpMessage message = new UdpMessage();

		StringBuilder body = new StringBuilder();
		// 如果在房间中，则发送到pdu
		// if (VideoRoomLogic.getIns().isInRoom()) {
		// message.setModuleId(UdpCommond.PDU_MODULE_ID);
		// message.setCommond(UdpCommond.MLG_31_PRIVATE_ROOM_MSG);
		// message.setSession(session);
		// body.append(
		// "<iq fm=\"" + fromId + "\" nn=\"" + fromNickname
		// + "\" to=\"" + toId + "\">")
		// .append("<msg><![CDATA[" + url + "]]></msg>")
		// .append("<type>" + type + "</type>").append("</iq>\n");
		// } else {
		// message.setModuleId(UdpCommond.MLG_MODULE_ID);
		// message.setCommond(UdpCommond.MLG_026_MAIL_MSG);
		// message.setSession(session);
		// body.append(
		// "<iq stm=\""
		// + DateUtil.format(System.currentTimeMillis(),
		// DateUtil.FMT_MAIL_TIME) + "\"" + ">")
		// .append("<frm nm=\"" + fromId + "\"" + " nn=\""
		// + fromNickname + "\"" + "/>")
		// .append("<to nm=\"" + toId + "\"" + " />")
		// .append("<type>" + type + "</type>")
		// .append("<msg><![CDATA[" + msg + "]]></msg>")
		// .append("<url><![CDATA[" + url + "]]></url>")
		// .append("<sbp><![CDATA["
		// + LoginLogic.getIns().get_sex_age_address()
		// + "]]></sbp>").append("</iq>\n");
		// }

		message.setBody(body.toString(), request.key, false);
		message.setLength((short) message.getBody().getBytes().length);
		request.setMessage(message);
		UdpProxy.newInstance().sendUdpMessage(request, null);
	}

	public static void mailMessageResponse(UdpRequest request, long session,
			byte status, byte commond, byte seq) {
		if (request == null)
			return;
		pushMessageResponse(request, session, status, commond, MLG_MODULE_ID,
				seq);
	}

	public static void pushMessageResponse(UdpRequest request, long session,
			byte status, byte commondCode, byte moduleId, byte seq) {
		UdpMessage message = new UdpMessage();
		message.setModuleId(moduleId);
		message.setCommond(commondCode);
		message.setSession(session);
		message.setStatus(status);
		message.setBody("", request.key, false);
		message.setLength((short) message.getBody().getBytes().length);
		request.setMessage(message);
		UdpProxy.newInstance().sendUdpMessage(request, Byte.valueOf(seq));
	}
}
