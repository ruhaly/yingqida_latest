package com.yingqida.richplay.packet;

import java.io.ByteArrayInputStream;

import org.xmlpull.v1.XmlPullParser;

import com.yingqida.richplay.baseapi.AppLog;
import com.yingqida.richplay.baseapi.common.RichResource;

public class UdpXml extends BaseXml {
	private static final String MY_TAG = "UdpXml";

	public static synchronized int parseBalancePushMessage(byte[] bytes) {
		int coin = -1;
		if (null == bytes || null == xmlParser)
			return coin;

		ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
		try {
			xmlParser.setInput(bai, RichResource.ENCODE_UTF);
			String tagName = null;
			int eventType = xmlParser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					tagName = xmlParser.getName().toLowerCase();
					if ("coin".equals(tagName)) {
						coin = Integer.valueOf(xmlParser.nextText());
					}
				}
				eventType = xmlParser.next();
			}
		} catch (Exception e) {
			AppLog.out(MY_TAG, e.getMessage(), AppLog.LEVEL_ERROR);
		} finally {
			closeInputStream(bai);
		}
		return coin;
	}

}
