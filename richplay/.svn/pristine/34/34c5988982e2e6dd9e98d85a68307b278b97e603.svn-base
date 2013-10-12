package com.yingqida.richplay.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivity;
import com.yingqida.richplay.fragment.FayanFragment;
import com.yingqida.richplay.fragment.MyFragmentPagerAdapter;
import com.yingqida.richplay.fragment.SuperFragment;
import com.yingqida.richplay.fragment.WoguanzhuFragment;
import com.yingqida.richplay.fragment.YonghuFragment;
import com.yingqida.richplay.widget.MyViewPager;

public class PCenterActivity extends SuperActivity {

	private TextView textFy;
	private TextView textWgz;
	private TextView textGzw;
	private MyViewPager mPager;
	private ArrayList<Fragment> fragmentsList;

	private MyFragmentPagerAdapter adapter;

	@Override
	public void initData() {

	}

	@Override
	public void initLayout() {
		setContentView(R.layout.pcenter_layout);
		textFy = (TextView) findViewById(R.id.text_fy);
		textWgz = (TextView) findViewById(R.id.text_wgz);
		textGzw = (TextView) findViewById(R.id.text_gzw);
		findViewById(R.id.btn_setting).setVisibility(View.VISIBLE);
		updateNum();
		initViewPager();
	}

	public void updateNum() {
		textFy.setText(Html.fromHtml(String.format(getString(R.string.fayan),
				"<font color=\"#000000\">" + 15211 + "</font>")));
		textWgz.setText(Html.fromHtml(String.format(
				getString(R.string.woguanzhu),
				"<font color=\"#000000\">252</font>")));
		textGzw.setText(Html.fromHtml(String.format(
				getString(R.string.guanzhuwo),
				"<font color=\"#000000\">4</font>")));

		textFy.setLayoutParams(new LayoutParams((getScreenW() - 2) / 3,
				LayoutParams.WRAP_CONTENT));
		textWgz.setLayoutParams(new LayoutParams((getScreenW() - 2) / 3,
				LayoutParams.WRAP_CONTENT));
		textGzw.setLayoutParams(new LayoutParams((getScreenW() - 2) / 3,
				LayoutParams.WRAP_CONTENT));
	}

	@Override
	public void clearData() {

	}

	public void btnSettin(View view) {
		startActivity(new Intent(getBaseContext(), SettingActivity.class));
	}

	private void initViewPager() {
		mPager = (MyViewPager) findViewById(R.id.vPager);
		fragmentsList = new ArrayList<Fragment>();

		SuperFragment fragment_fayan = FayanFragment
				.getInstance("FayanFragment");
		SuperFragment fragment_woguanzhu = WoguanzhuFragment
				.getInstance("WoguanzhuFragment");
		SuperFragment fragment_guanzhuwo = YonghuFragment
				.getInstance("YonghuFragment");
		fragmentsList.add(fragment_fayan);
		fragmentsList.add(fragment_woguanzhu);
		fragmentsList.add(fragment_guanzhuwo);
		if (null == adapter) {
			adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),
					fragmentsList);
			mPager.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			setBg(arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	public class MyOnClickListener implements View.OnClickListener {

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	private int index = 0;

	public void setBg(int index) {
		mPager.setCurrentItem(index);
		if (0 == index) {
			findViewById(R.id.text_fy)
					.setBackgroundResource(R.drawable.fayan_g);
			findViewById(R.id.text_wgz).setBackgroundResource(
					R.drawable.fayan_p);
			findViewById(R.id.text_gzw).setBackgroundResource(
					R.drawable.fayan_p);
		} else if (1 == index) {
			findViewById(R.id.text_fy)
					.setBackgroundResource(R.drawable.fayan_p);
			findViewById(R.id.text_wgz).setBackgroundResource(
					R.drawable.fayan_g);
			findViewById(R.id.text_gzw).setBackgroundResource(
					R.drawable.fayan_p);
		} else {
			findViewById(R.id.text_fy)
					.setBackgroundResource(R.drawable.fayan_p);
			findViewById(R.id.text_wgz).setBackgroundResource(
					R.drawable.fayan_p);
			findViewById(R.id.text_gzw).setBackgroundResource(
					R.drawable.fayan_g);
		}
	}

	public static final int FAYAN = 0;
	public static final int WOGUANZHU = 1;
	public static final int GUANZHUWO = 2;

	public void textClick(View view) {
		switch (view.getId()) {
		case R.id.text_fy: {
			setBg(FAYAN);
			break;
		}

		case R.id.text_wgz: {
			setBg(WOGUANZHU);
			break;
		}
		case R.id.text_gzw: {
			setBg(GUANZHUWO);
			break;
		}
		default:
			break;
		}
	}
}
