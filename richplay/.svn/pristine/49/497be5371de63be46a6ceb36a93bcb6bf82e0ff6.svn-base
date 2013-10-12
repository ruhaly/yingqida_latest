package com.yingqida.richplay.packet;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import com.yingqida.richplay.baseapi.common.RichResource;
import com.yingqida.richplay.entity.RleasePicEntity;

public class HttpClient {

	public static MultipartEntity createReleasePicEntity(RleasePicEntity entity) {
		MultipartEntity et = new MultipartEntity();
		try {
			et.addPart(
					"uid",
					new StringBody("ruhaly", Charset
							.forName(RichResource.ENCODE_UTF)));
			et.addPart("file[0]", new FileBody(new File(entity.getFpath())));
			et.addPart(
					"albumid",
					new StringBody(entity.getAlbumid(), Charset
							.forName(RichResource.ENCODE_UTF)));
			et.addPart(
					"location",
					new StringBody(entity.getLocation(), Charset
							.forName(RichResource.ENCODE_UTF)));
			et.addPart(
					"picdesc",
					new StringBody(entity.getPicDesc(), Charset
							.forName(RichResource.ENCODE_UTF)));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return et;
	}
}
