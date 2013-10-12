package com.yingqida.richplay.packet;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import com.yingqida.richplay.baseapi.AppLog;
import com.yingqida.richplay.baseapi.common.RichResource;
import com.yingqida.richplay.entity.Comment;
import com.yingqida.richplay.entity.RleasePicEntity;
import com.yingqida.richplay.entity.WallPicInfo;
import com.yingqida.richplay.entity.WallPicItem;
import com.yingqida.richplay.logic.WallPicLogic;

public class HttpXml extends BaseXml {

	private static final String MY_TAG = "HttpXml";

	public static synchronized String createSectionRequestXml(String sessionId,
			String user, int currentpage, int pagenum, String find,
			String sortCondition, String bnameCondition, int type) {
		StringBuffer tempBuffer = new StringBuffer();
		tempBuffer.append("<root>");

		tempBuffer.append("<req user='");

		tempBuffer.append(user);
		tempBuffer.append("' currentpage='");

		tempBuffer.append(currentpage);
		tempBuffer.append("' pagenum='");

		tempBuffer.append(pagenum);

		tempBuffer.append("</req>");

		tempBuffer.append("</root>");

		return tempBuffer.toString();
	}

	public static String createReleasePicReqeustXml(RleasePicEntity entity) {

		StringBuffer tempBuffer = new StringBuffer();
		tempBuffer.append("<root>");
		tempBuffer.append("<uid>").append("ruhaly").append("</uid>");
		tempBuffer.append("<file>").append(entity.getImgStream())
				.append("</file>");
		tempBuffer.append("<albumid>").append(entity.getAlbumid())
				.append("</albumid>");
		tempBuffer.append("<location>").append(entity.getLocation())
				.append("</location>");
		tempBuffer.append("<picdesc>").append(entity.getPicDesc())
				.append("</picdesc>");
		tempBuffer.append("</root>");
		return tempBuffer.toString();
	}

	public static String createWallPicInofRequestXml(String pid) {
		StringBuffer tempBuffer = new StringBuffer();
		tempBuffer.append("<root>");
		tempBuffer.append("<pid>");
		tempBuffer.append(pid);
		tempBuffer.append("</pid>");
		tempBuffer.append("</root>");

		return tempBuffer.toString();
	}

	public static String createWallPicRequestXml(String uid, int curp, int pnum) {
		StringBuffer tempBuffer = new StringBuffer();
		tempBuffer.append("<root>");
		tempBuffer.append("<uid>").append(uid).append("</uid>");
		tempBuffer.append("<curp>").append(curp).append("</curp>");
		tempBuffer.append("<pnum>").append(pnum).append("</pnum>");
		tempBuffer.append("</root>");

		return tempBuffer.toString();
	}

	public static WallPicInfo parseWallPicInfoXML(byte[] bytes) {
		if (null == bytes || null == xmlParser)
			return null;
		WallPicInfo wpi = new WallPicInfo();
		ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
		try {
			xmlParser.setInput(bi, RichResource.ENCODE_UTF);
			String tagName = null;
			Comment c = null;
			int eventType = xmlParser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					tagName = xmlParser.getName().toLowerCase();
					if ("pid".equals(tagName)) {
						wpi.setPid(xmlParser.nextText());
					} else if ("purl".equals(tagName)) {
						wpi.setPurl(xmlParser.nextText());
					} else if ("uid".equals(tagName)) {
						c = new Comment();
						c.setUid(xmlParser.nextText());
					} else if ("".equals(tagName)) {
						c.setUname(xmlParser.nextText());
					} else if ("".equals(tagName)) {
						c.setTime(xmlParser.nextText());
						wpi.getList().add(c);
					}
				}
				eventType = xmlParser.next();
			}
		} catch (Exception e) {
			AppLog.out(MY_TAG, e.getMessage(), AppLog.LEVEL_ERROR);
		}

		return wpi;
	}

	public static List<WallPicItem> parseWallPicXML(byte[] bytes) {
		if (null == bytes || null == xmlParser)
			return null;
		List<WallPicItem> list = new ArrayList<WallPicItem>();
		ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
		try {
			xmlParser.setInput(bi, RichResource.ENCODE_UTF);
			String tagName = null;
			WallPicItem wpi = null;
			int eventType = xmlParser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					tagName = xmlParser.getName().toLowerCase();
					if ("total".equals(tagName)) {
						wpi = new WallPicItem();
						WallPicLogic.getIns().total = Integer.valueOf(xmlParser
								.nextText());
					} else if ("pid".equals(tagName)) {
						wpi = new WallPicItem();
						wpi.setId(xmlParser.nextText());
					} else if ("pdesc".equals(tagName)) {
						wpi.setName(xmlParser.nextText());
					} else if ("purl".equals(tagName)) {
						wpi.setPicUrl(xmlParser.nextText());
						list.add(wpi);
					}
				}
				eventType = xmlParser.next();
			}
		} catch (Exception e) {
			AppLog.out(MY_TAG, e.getMessage(), AppLog.LEVEL_ERROR);
		}

		return list;
	}
}