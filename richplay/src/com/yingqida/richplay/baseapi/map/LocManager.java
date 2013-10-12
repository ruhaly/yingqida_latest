/**
 * ��Ŀ��     rimi
 * �ļ���     LocationManager.java
 * �ļ������� 
 * ���ߣ�         wjd
 * ����ʱ�䣺  2012-2-7
 * ��Ȩ���� �� Copyright (C) 2008-2010 RichPeak
 *
 */
package com.yingqida.richplay.baseapi.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.yingqida.richplay.baseapi.AppLog;

/**
 * ����ƣ� LocationManager ���ߣ� wjd ����ʱ�䣺 2012-2-7 ����6:22:33 �������� λ�ù���
 * ��Ȩ���� �� Copyright (C) 2008-2010 RichPeak �޸�ʱ�䣺 2012-2-7 ����6:22:33
 * 
 */
public class LocManager {

	private static final String MY_TAG = "LocManager";

	private LocationManager locationManager;

	private LocationListener locListener;

	public static LocManager instance = new LocManager();

	private Map<String, Location> locations = new HashMap<String, Location>();

	/**
	 * ������ƣ� startLocationListener ���ߣ� wjd ���������� ��ʼλ�ü��� �������
	 * �������ͣ� void
	 */
	public void startLocationListener() {
		if (null == locationManager) {
			return;
		}
		locListener = new LocationListener() {
			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				AppLog.out(MY_TAG, provider + " onStatusChanged",
						AppLog.LEVEL_INFO);
			}

			@Override
			public void onProviderEnabled(String provider) {
				AppLog.out(MY_TAG, provider + " onProviderEnabled",
						AppLog.LEVEL_INFO);
			}

			@Override
			public void onProviderDisabled(String provider) {
				AppLog.out(MY_TAG, provider + " onProviderDisabled",
						AppLog.LEVEL_INFO);
			}

			@Override
			public void onLocationChanged(Location location) {
				AppLog.out(MY_TAG,
						location.getProvider() + "|==|" + location.toString(),
						AppLog.LEVEL_INFO);
				locations.put(location.getProvider(), location);
			}
		};
		List<String> providers = locationManager.getAllProviders();
		for (int i = 0; i < providers.size(); ++i) {
			locationManager.requestLocationUpdates(providers.get(i), 0, 0,
					locListener);
		}
	}

	/**
	 * ������ƣ� stopLocationListener ���ߣ� wjd ���������� ֹͣλ�ü��� �������
	 * �������ͣ� void
	 */
	public void stopLocationListener() {
		if (null == locationManager || null == locListener) {
			return;
		}
		locationManager.removeUpdates(locListener);
	}

	/**
	 * ������ƣ� getCurLocation ���ߣ� wjd ���������� ��õ�ǰλ����Ϣ ������� @return
	 * �������ͣ� Location
	 */
	public Location getCurLocation() {
		if (null != locations.get(LocationManager.GPS_PROVIDER)) {
			return locations.get(LocationManager.GPS_PROVIDER);
		}
		if (null != locations.get(LocationManager.NETWORK_PROVIDER)) {
			return locations.get(LocationManager.NETWORK_PROVIDER);
		}
		if (null != locationManager) {
			return locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		return null;
	}

	public String getCurLocationStr() {
		Location curlLocation = getCurLocation();
		if (null == curlLocation) {
			return "";
		} else {
			return curlLocation.getLatitude() + ","
					+ curlLocation.getLongitude();
		}
	}

	/**
	 * ������ƣ� setLocationManager ���ߣ� wjd ���������� �ֶ����ͣ� LocationManager
	 * �����ֶΣ� ���� locationManager �� locationManager ��ע��
	 */
	public void setLocationManager(LocationManager locationManager) {
		this.locationManager = locationManager;
	}
}
