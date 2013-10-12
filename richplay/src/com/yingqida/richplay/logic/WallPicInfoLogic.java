package com.yingqida.richplay.logic;

import com.yingqida.richplay.entity.WallPicInfo;

public class WallPicInfoLogic {

	public int curp = 1;
	public int pnum = 10;

	private static WallPicInfoLogic ins;

	private WallPicInfo wp = new WallPicInfo();

	public WallPicInfo getWp() {
		return wp;
	}

	public void setWp(WallPicInfo wp) {
		this.wp.setLocation(wp.getLocation());
		this.wp.setPurl(wp.getPurl());
		this.wp.setTime(wp.getTime());
		this.wp.getList().addAll(wp.getList());
	}

	public synchronized static WallPicInfoLogic getIns() {
		if (null == ins) {
			ins = new WallPicInfoLogic();
		}
		return ins;
	}
}
