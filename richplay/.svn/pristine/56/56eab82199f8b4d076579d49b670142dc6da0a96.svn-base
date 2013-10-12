package com.yingqida.richplay.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yingqida.richplay.R;

public class WoguanzhuFragment extends SuperFragment {

	private ViewPager vPager;
	private MyFragmentPagerAdapter adapter;
	private List<Fragment> fragmentList;

	public static SuperFragment getInstance(String name) {
		return new WoguanzhuFragment();
	}

	public WoguanzhuFragment() {
		super();
	}

	@Override
	public void updateView() {

	}

	@Override
	public void initData() {

	}

	View convertView;

	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		convertView = inflater.inflate(R.layout.woguanzhu_layout, container,
				false);
		convertView.findViewById(R.id.btnFayan).setOnClickListener(this);
		convertView.findViewById(R.id.btnYonghu).setOnClickListener(this);
		vPager = (ViewPager) convertView.findViewById(R.id.vPager);
		fragmentList = new ArrayList<Fragment>();
		SuperFragment fragment_yuansu = YuansuFragment
				.getInstance("YuansuFragment");
		SuperFragment fragment_yonghu = YonghuFragment
				.getInstance("YonghuFragment");
		fragmentList.add(fragment_yuansu);
		fragmentList.add(fragment_yonghu);
		if (null == adapter) {
			adapter = new MyFragmentPagerAdapter(getChildFragmentManager(),
					fragmentList);
			vPager.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		vPager.setCurrentItem(0);
		vPager.setOnPageChangeListener(new MyOnPageChangeListener());
		return convertView;
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

	public static final int YUANSU = 0;
	public static final int YONGHU = 1;

	public void setBg(int index) {
		vPager.setCurrentItem(index);
		if (0 == index) {
			convertView.findViewById(R.id.btnFayan).setBackgroundResource(
					R.drawable.fayan_g);
			convertView.findViewById(R.id.btnYonghu).setBackgroundResource(
					R.drawable.fayan_p);
		} else if (1 == index) {
			convertView.findViewById(R.id.btnFayan).setBackgroundResource(
					R.drawable.fayan_p);
			convertView.findViewById(R.id.btnYonghu).setBackgroundResource(
					R.drawable.fayan_g);
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnFayan: {
			setBg(YUANSU);
			break;
		}

		case R.id.btnYonghu: {
			setBg(YONGHU);
			break;
		}
		default:
			break;
		}

	}
}
