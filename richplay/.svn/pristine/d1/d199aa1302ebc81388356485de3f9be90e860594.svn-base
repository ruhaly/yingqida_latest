package com.yingqida.richplay.baseapi.file;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Handler;

import com.yingqida.richplay.baseapi.AppLog;

public class FileUpLoader extends
		AsyncTask<UploadData, UploadProgress, UploadResult> {

	/**
	 * �ļ��ϴ����
	 */
	public static final int EVENT_LOADFILE_PROGRESS = 51111;

	/**
	 * �ļ��ϴ�����
	 */
	public static final int EVENT_LOADFILE_END = 61111;

	/**
	 * ������Ϣ��handler���
	 */
	private Handler handler;

	public FileUpLoader(Handler handler) {
		this.handler = handler;
	}

	@Override
	protected UploadResult doInBackground(UploadData... params) {
		int length = params.length;
		if (length < 1)
			return null;
		for (int i = 0; i < length; ++i) {
			try {
				int ret = upload(params[i]);
				AppLog.out("FileUpLoader", "ret " + ret, AppLog.LEVEL_INFO);
				if (ret != 0)
					return new UploadResult(params[i].getType(), false);
			} catch (Exception e) {
				return new UploadResult(params[i].getType(), false);
			}
		}
		return new UploadResult(params[0].getType(), true);
	}

	@Override
	protected void onProgressUpdate(UploadProgress... values) {
		if (values.length < 1)
			return;
		handler.obtainMessage(EVENT_LOADFILE_PROGRESS, values[0].getProgress(),
				values[0].getType()).sendToTarget();
	}

	@Override
	protected void onPostExecute(UploadResult result) {
		if (null == result)
			return;
		handleUploadResult(result);
	}

	public void handleUploadResult(UploadResult result) {
		handler.obtainMessage(EVENT_LOADFILE_END, result).sendToTarget();
	}

	/**
	 * �ϴ��ļ�ʵ��
	 * 
	 * @param src
	 */
	public int upload(UploadData entity) throws Exception {

		String end = "\r\n";
		String twoHen = "--";
		String boundary = "wjd_" + java.util.UUID.randomUUID().toString()
				+ "_wjd";
		int idx = entity.getUrl().indexOf("/file");
		String preUrl = entity.getUrl().substring(0, idx) + "/file/upLoad";
		String fileName = entity.getUrl().substring(idx);
		AppLog.out("FileUploader", "url     " + preUrl
				+ "       fileName      " + fileName, AppLog.LEVEL_INFO);
		URL url = new URL(preUrl);
		// if (RLoveApplication
		// .getIns()
		// .getSharedPreferences(
		// "com.ferun.rlove" + ShareKey.SETTING_SHARE_NAME,
		// Context.MODE_PRIVATE)
		// .getBoolean(ShareKey.SETTING_TONE, false))
		// {
		// url = new URL(entity.getUrl());
		// }
		AppLog.out("FileUploader", entity.toString(), AppLog.LEVEL_INFO);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		/* ����Input��Output����ʹ��Cache */
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		/* ���ô��͵�method=POST */
		con.setRequestMethod("POST");
		/* setRequestProperty */
		con.setRequestProperty("Accept",
				"text/html, application/xhtml+xml, image/jpeg,*/*");
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		con.setRequestProperty("Content-Type", "multipart/form-data;boundary="
				+ boundary);
		con.setRequestProperty("Accept-Encoding", "gzip, deflate");
		// con.setRequestProperty("ptype", String.valueOf(entity.getType()));
		// con.setRequestProperty("puser", entity.getUid());
		/* ����DataOutputStream */
		DataOutputStream ds = new DataOutputStream(con.getOutputStream());
		ds.writeBytes(twoHen + boundary + end);
		ds.writeBytes("Content-Disposition:form-data; "
				+ "name=\"f\";filename=\"" + fileName + "\"" + end);
		ds.writeBytes("Content-Type:" + entity.getContent_type() + end + end);
		/* ȡ���ļ���FileInputStream */
		ByteArrayInputStream bis = new ByteArrayInputStream(entity.getData());
		/* ����ÿ��д��1024bytes */
		byte[] buffer = new byte[1024];

		int count = 0;
		int length = -1;
		/* ���ļ���ȡ����{����� */
		while ((length = bis.read(buffer)) != -1) {
			// ���׽��
			if (count > 0)
				publishProgress(new UploadProgress(100 * count
						/ entity.getData().length, entity.getType()));
			/* ������д��DataOutputStream�� */
			ds.write(buffer, 0, length);
			count += length;
		}

		/* close streams */
		bis.close();

		ds.writeBytes(end);
		ds.writeBytes(twoHen + boundary + twoHen);
		ds.writeBytes(end);
		ds.flush();
		int rspCode = con.getResponseCode();
		if (HttpURLConnection.HTTP_OK == rspCode) {
			return con.getHeaderFieldInt("returnCode", 0);
		} else {
			return -1;
		}
	}
}
