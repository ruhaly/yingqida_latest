package com.yingqida.richplay.packet;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class BaseXml {
	public static XmlPullParser xmlParser;

	static {
		initXmlParser();
	}

	public static void initXmlParser() {
		try {
			xmlParser = XmlPullParserFactory.newInstance().newPullParser();
			return;
		} catch (XmlPullParserException localXmlPullParserException) {
			while (true) {
				xmlParser = null;
				localXmlPullParserException.printStackTrace();
			}
		}
	}

	public static void closeInputStream(InputStream stream) {
		if (null != stream) {
			try {
				stream.close();
				stream = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

/*
 * Location: E:\apk\ Qualified Name: com.pengrun.realme.packet.BaseXml JD-Core
 * Version: 0.6.0
 */