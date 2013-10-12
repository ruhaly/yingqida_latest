package com.yingqida.richplay.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yingqida.richplay.R;
import com.yingqida.richplay.baseapi.common.User;

public class YonghuFragment extends SuperFragment {

	private ListView list_yonghu;
	Adapter adapter;

	private List<User> list = new ArrayList<User>();

	public static SuperFragment getInstance(String name) {
		return new YonghuFragment();
	}

	@Override
	public void updateView() {
		if (null != adapter) {
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void initData() {
		for (int i = 0; i < 20; i++) {
			User c = new User();
			c.setNickname("user" + i);
			c.setFayan_num("" + i);
			c.setBeiguanzhu("" + i * 2);
			list.add(c);
		}
	}

	View convertView;

	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		convertView = inflater
				.inflate(R.layout.yonghu_layout, container, false);
		list_yonghu = (ListView) convertView.findViewById(R.id.list_yonghu);
		if (null == adapter) {
			adapter = new Adapter();
			list_yonghu.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		return convertView;
	}

	class Adapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public User getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (null == convertView) {
				holder = new Holder();
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.user_item_layout, null);
				holder.text_name = (TextView) convertView
						.findViewById(R.id.text_name);
				holder.text_fy = (TextView) convertView
						.findViewById(R.id.text_fy);
				holder.text_bgz = (TextView) convertView
						.findViewById(R.id.text_bgz);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			holder.text_name.setText(getItem(position).getNickname());
			holder.text_fy.setText(Html.fromHtml(String.format(
					getString(R.string.fayan), "<font color=\"#000000\">"
							+ getItem(position).getFayan_num() + "</font>")));
			holder.text_bgz.setText(Html.fromHtml(String.format(
					getString(R.string.guanzhu2), "<font color=\"#000000\">"
							+ getItem(position).getBeiguanzhu() + "</font>")));
			return convertView;
		}

		class Holder {
			TextView text_name;
			TextView text_fy;
			TextView text_bgz;

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
