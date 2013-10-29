package com.yingqida.richplay.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivityForFragment;

public class GuanyuFragment extends SuperFragment {

	private static GuanyuFragment ins;

	public synchronized static GuanyuFragment getIns() {
		if (null == ins) {
			ins = new GuanyuFragment();
		}
		return ins;
	}

	@Override
	public void updateView() {

	}

	@Override
	public void initData() {

	}

	@OnClick(R.id.btnToggle)
	public void btnToggleClick(View view) {
		((SuperActivityForFragment) getActivity()).toggle();
	}

	View convertView;

	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (convertView == null)
			convertView = inflater.inflate(R.layout.guanyu_layout, container,
					false);
		ViewUtils.inject(this, convertView);
		return convertView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
