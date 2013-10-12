package com.yingqida.richplay.logic;

import java.util.ArrayList;
import java.util.List;

import com.yingqida.richplay.entity.WallPicItem;

public class WallPicLogic {
	public int curp = 1;
	public int pnum = 8;
	public int total = 0;
	private static WallPicLogic ins;

	public synchronized static WallPicLogic getIns() {
		if (null == ins) {
			ins = new WallPicLogic();
		}
		return ins;
	}

	private List<WallPicItem> list = new ArrayList<WallPicItem>();

	public List<WallPicItem> getList() {
		return list;
	}

	public void setList(List<WallPicItem> list) {
		this.list = list;
	}

}
