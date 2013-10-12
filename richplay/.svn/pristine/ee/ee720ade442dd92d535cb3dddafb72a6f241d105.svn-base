package com.yingqida.richplay.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.yingqida.richplay.HomeActivity;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivity;
import com.yingqida.richplay.baseapi.ActivityResultCode;
import com.yingqida.richplay.baseapi.AppLog;
import com.yingqida.richplay.baseapi.common.RequestId;
import com.yingqida.richplay.baseapi.http.HttpRequest;
import com.yingqida.richplay.entity.ImageMock;
import com.yingqida.richplay.entity.WallPicItem;
import com.yingqida.richplay.logic.WallPicLogic;
import com.yingqida.richplay.packet.HttpSender;
import com.yingqida.richplay.packet.HttpXml;

/**
 * 
 * 
 * MainActivity
 * 
 * ruhaly 2013-5-30 下午3:51:11
 * 
 * @version 1.0.0
 * 
 */
public class SampleWallPicActivity extends SuperActivity implements
		ActivityResultCode {

	private Button btn_take;

	private Uri photoUri;

	// 从Intent获取图片路径的KEY
	public static final String KEY_PHOTO_PATH = "photo_path";
	public String picPath = null;

	public static final int UPDATE_PIC = 88803;
	public static final int WALLPICINFO = 2;

	private List<WallPicItem> list = new ArrayList<WallPicItem>();
	private WallPicLogic logic;

	public void initList() {
		for (int i = 0; i < ImageMock.imageThumbUrls.length; i++) {
			WallPicItem wpi = new WallPicItem();
			String id = UUID.randomUUID().toString();
			wpi.setId(id);
			wpi.setName(id);
			wpi.setPicUrl(ImageMock.imageThumbUrls[i]);
			list.add(wpi);
		}
	}

	@Override
	public void initData() {
		initList();
		logic = WallPicLogic.getIns();
	}

	@Override
	public void initLayout() {
		setContentView(R.layout.wall_pic_layout);
		btn_take = (Button) findViewById(R.id.btn_take);
		btn_take.setOnClickListener(this);
		String response = parseXML("com/test.xml");
		handleResponse(RequestId.WALL_PIC, response);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_take: {
			takePhoto();
			break;
		}
		}
	}

	/**
	 * 
	 * takePhoto(调用手机相机拍照) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void takePhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
		/***
		 * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
		 * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
		 */
		ContentValues values = new ContentValues();
		photoUri = getContentResolver().insert(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
		/** ----------------- */
		HomeActivity.ins.startActivityForResult(intent,
				SELECT_PIC_BY_TACK_PHOTO);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (Activity.RESULT_OK == resultCode
				&& SELECT_PIC_BY_TACK_PHOTO == requestCode) {

			String[] pojo = { MediaStore.Images.Media.DATA };
			Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
			if (cursor != null) {
				int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
				cursor.moveToFirst();
				picPath = cursor.getString(columnIndex);
				// 4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
				if (Integer.parseInt(Build.VERSION.SDK) < 14) {
					cursor.close();
				}
			}
			Intent i = new Intent(getBaseContext(), ReleasePicActivity.class);
			i.putExtra(KEY_PHOTO_PATH, picPath);
			startActivity(i);
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		// requestWallPic("");
	}

	@Override
	public void clearData() {

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			imitateEvent();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private HttpRequest requestWP;

	/**
	 * 图片详情查看 requestWallPicInfo()
	 * 
	 * @param pid
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public void requestWallPic(String uid) {
		AppLog.out(TAG, "requestWallPic()", AppLog.LEVEL_INFO);
		requestWP = new HttpRequest(this, this, RequestId.WALL_PIC);
		showProgressDialog("", getString(R.string.send_request_ing), true,
				new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						requestWP.cancel();
						requestWP = null;
					}
				});
		HttpSender.requestWallPic(getService(), getSessionString(), requestWP,
				uid, logic.curp, logic.pnum);

	}

	@Override
	public void handleHttpResponse(String response, int netErrorCode,
			int requestId) {
		if (0 != netErrorCode) {
			super.handleHttpResponse(response, netErrorCode, requestId);
		} else {
			handleResponse(requestId, response);
		}
		dismissProgress();
	}

	public void handleResponse(int requestId, String response) {

		switch (requestId) {
		case RequestId.WALL_PIC:
			List<WallPicItem> list = HttpXml.parseWallPicXML(response
					.getBytes());
			if (null != list && !list.isEmpty()) {
				logic.getList().addAll(list);
			}
			mHandler.sendEmptyMessage(UPDATE_PIC);
			break;
		default:
			break;
		}
	}

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case WALLPICINFO:
			Intent intent = new Intent(getBaseContext(), WallInfoActivity.class);
			intent.putExtra("pid", (String) msg.obj);
			startActivity(intent);
			break;

		case UPDATE_PIC:
			updateView();
			break;

		default:
			break;
		}
		super.handleMsg(msg);
	}

	private void updateView() {
	}
}
