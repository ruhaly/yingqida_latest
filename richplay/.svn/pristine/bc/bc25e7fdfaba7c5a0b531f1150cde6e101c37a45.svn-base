/*
 * 文 件 名: DataEncryption.java
 * 版 权: Huawei Technologies Co., Ltd. Copyright YYYY-YYYY, All rights reserved
 * 描 述: <描述>
 * 修 改 人: wangbin
 * 修改时间: 2009-8-19
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package com.yingqida.richplay.baseapi.common;

import com.yingqida.richplay.baseapi.AppLog;

/**
 * 类名称：DataEncryption 作者： Administrator 创建时间：2010-10-18 类描述：加密类 版权声明 : Copyright
 * (C) 2008-2010 华为技术有限公司(Huawei Tech.Co.,Ltd) 修改时间：
 * 
 */
public final class DataEncryption {

	/**
	 * tag
	 */
	private static final String MY_TAG = "DataEncryption";

	/**
	 * 密钥长度
	 */
	private static final int KEYLENGTH = 16;

	/**
	 * <默认构造函数>
	 */
	private DataEncryption() {
	}

	/**
	 * 方法名称：encryption 作者：wangjian 方法描述：加密 输入参数：@param sourceData 输入参数：@param
	 * key 输入参数：@return 返回类型：byte[]： 备注：
	 */
	public static byte[] encrypt(byte[] bSourceData, String key) {
		byte[] szBuffer = null;
		try {
			byte[] bKey = key.getBytes();

			szBuffer = new byte[bSourceData.length];

			int k = 0;
			for (int i = 0; i < bSourceData.length; i++) {
				szBuffer[i] = (byte) (bSourceData[i] ^ bKey[k]);
				k++;
				k = k % bKey.length;
			}
		} catch (Exception e) {
			AppLog.out(MY_TAG, e.getMessage(), AppLog.LEVEL_ERROR);
		}
		return szBuffer;
	}

	/**
	 * 方法名称：unEncryption 作者：Administrator 方法描述：解密 输入参数：@param encryptData
	 * 输入参数：@param key 输入参数：@return 返回类型：String： 备注：
	 */
	public static byte[] unEncrypt(byte[] encryptData, String key) {
		byte[] bKey = key.getBytes();

		byte[] szBuffer = new byte[encryptData.length];
		int k = 0;
		for (int i = 0; i < encryptData.length; i++) {
			szBuffer[i] = (byte) (encryptData[i] ^ bKey[k]);
			k++;
			k = k % bKey.length;
		}
		return szBuffer;
		// String context = null;
		// try
		// {
		// context = new String(szBuffer);
		// }
		// catch (Exception e)
		// {
		// AppLog.out(MY_TAG, e.getMessage(), AppLog.LEVEL_ERROR);
		// }
		// return context;
	}

	/**
	 * 方法名称：getEncryptionKey 作者：Administrator 方法描述：计算密钥默认为16为长度 输入参数：@return
	 * 返回类型：String： 备注：
	 */
	private static String getEncryptionKey(final String source) {
		return getEncryptionKey(source, KEYLENGTH);
	}

	/**
	 * 方法名称：getEncryptionKey 作者：wujingdong 方法描述：指定密钥长度计算密钥 输入参数：@param source
	 * 输入参数：@param keyLength 输入参数：@return 返回类型：String： 备注：
	 */
	private static String getEncryptionKey(final String source,
			final int keyLength) {

		// 登陆信息加密
		String key = "";
		// 设置密钥为16个字节
		if (null != source) {
			int length = source.length();

			if (length > keyLength) {
				key = source.substring(0, keyLength);
			} else if (length < keyLength) {
				StringBuffer blank = new StringBuffer("");

				for (int i = 0; i < (keyLength - length); i++) {
					blank.append(source.charAt(i % length));
				}

				key = source + blank.toString();
			} else {
				key = source;
			}
		}

		return key;
	}

	/**
	 * 方法名称：encrypt 作者：wangjian 方法描述：加密数据 输入参数：@param source 需要加密数据 输入参数：@param
	 * key 加密密钥 输入参数：@return 返回类型：byte[]：加密后的数据字节数组 备注：
	 */
	public static byte[] encryption(byte[] source, final String key) {
		if (null == source || "".equals(source) || null == key
				|| "".equals(key)) {
			return null;
		}

		String tmpKey = getEncryptionKey(key);
		if (null == tmpKey || "".equals(tmpKey) || KEYLENGTH != tmpKey.length()) {
			return null;
		}
		return encrypt(source, tmpKey);
	}

	/**
	 * 方法名称：unEncrypt 作者：wangjian 方法描述：解密数据 输入参数：@param source 需要解密的数据
	 * 输入参数：@param key 解密密钥 输入参数：@return 返回类型：String：解密后字符串 备注：
	 */
	public static byte[] unEncryption(byte[] source, final String key) {
		if (null == source || source.length <= 0 || null == key
				|| "".equals(key)) {
			return null;
		}
		String tmpKey = getEncryptionKey(key);
		if (null == tmpKey || KEYLENGTH != tmpKey.length()) {
			return null;
		}
		return unEncrypt(source, tmpKey);
	}
}
