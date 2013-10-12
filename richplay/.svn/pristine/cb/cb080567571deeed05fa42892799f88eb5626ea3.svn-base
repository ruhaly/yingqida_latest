/**
 * 项目名：     realmestore
 * 文件名：     PicCache.java
 * 文件描述： 
 * 作者：         fengrun
 * 创建时间：  2012-6-28
 * 版权声明 ： Copyright (C) 2008-2010 RichPeak
 *
 */
package com.yingqida.richplay.baseapi.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名称： PicCache 作者： fengrun 创建时间： 2012-6-28 下午8:32:41 类描述： 版权声明 ： Copyright (C)
 * 2008-2010 RichPeak 修改时间： 2012-6-28 下午8:32:41
 * 
 */
public class PicCache {
	public static Map<String, List<String>> picMap = new HashMap();

	/**
	 * 方法名称： getPicMap 作者： fengrun 方法描述： 字段类型： Map<String,List<String>> 返回字段：
	 * picMap 备注：
	 */
	public static Map<String, List<String>> getPicMap() {
		return picMap;
	}

	/**
	 * 方法名称： setPicMap 作者： fengrun 方法描述： 字段类型： Map<String,List<String>> 设置字段： 设置
	 * picMap 给 picMap 备注：
	 */
	public static void setPicMap(Map<String, List<String>> picMap) {
		PicCache.picMap = picMap;
	}

	public static List<String> getPicUrl(String key) {
		if (picMap.keySet().contains(key)) {
			if (picMap.get(key).isEmpty()) {
				return new ArrayList<String>();
			}
			return picMap.get(key);
		}
		return new ArrayList<String>();
	}

}
