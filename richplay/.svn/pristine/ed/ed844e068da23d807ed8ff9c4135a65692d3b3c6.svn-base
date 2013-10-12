package com.yingqida.richplay.baseapi.common;

import java.io.UnsupportedEncodingException;

/**
 * 类名称：Base64 作者： 创建时间：2010-8-13 类描述：base64编码 版权声明 : Copyright (C) 2008-2010
 * 华为技术有限公司(Huawei Tech.Co.,Ltd) 修改时间：
 * 
 */
public final class Base64 {
	/**
	 * US-ASCII 编码
	 */
	private static final String ASCII = "US-ASCII";

	/**
	 * ISO-8859-1 编码
	 */
	private static final String ISO = "ISO-8859-1";

	/**
	 * 定义base64编码基本字符
	 */
	private static final char[] BASE64ENCODECHARS = new char[] { 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H',

			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',

			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',

			'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',

			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',

			'o', 'p', 'q', 'r', 's', 't', 'u', 'v',

			'w', 'x', 'y', 'z', '0', '1', '2', '3',

			'4', '5', '6', '7', '8', '9', '+', '/' };

	/**
	 * 定义base64解码基本字符
	 */
	private static final byte[] BASE64DECODECHARS = new byte[] { -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,

			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,

			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,

			52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,

			-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,

			15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,

			-1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,

			41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1 };

	/**
	 * <默认构造函数>
	 */
	private Base64() {
	}

	/**
	 * 方法名称：decode 作者：wujingdong 方法描述：解码 输入参数：@param str 输入字符串 输入参数：@return
	 * 输入参数：@throws UnsupportedEncodingException 返回类型：byte[]：二进制解码流 备注：
	 */
	public static byte[] decode(String str) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();

		byte[] data = str.getBytes(ASCII);

		int len = data.length;

		int i = 0;

		int b1, b2, b3, b4;

		while (i < len) {
			/* b1 */

			do {
				b1 = BASE64DECODECHARS[data[i++]];
			} while ((i < len) && (b1 == -1));

			if (b1 == -1) {
				break;
			}

			/* b2 */

			do {
				b2 = BASE64DECODECHARS[data[i++]];
			} while ((i < len) && (b2 == -1));

			if (b2 == -1) {
				break;
			}

			sb.append((char) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

			/* b3 */

			do {
				b3 = data[i++];

				if (b3 == 61) {
					return sb.toString().getBytes(ISO);
				}

				b3 = BASE64DECODECHARS[b3];
			} while ((i < len) && (b3 == -1));

			if (b3 == -1) {
				break;
			}

			sb.append((char) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

			/* b4 */

			do {
				b4 = data[i++];

				if (b4 == 61) {
					return sb.toString().getBytes(ISO);
				}

				b4 = BASE64DECODECHARS[b4];
			} while ((i < len) && (b4 == -1));

			if (b4 == -1) {
				break;
			}

			sb.append((char) (((b3 & 0x03) << 6) | b4));
		}

		return sb.toString().getBytes(ISO);
	}

	/**
	 * 方法名称：encode 作者：wujingdong 方法描述：编码 输入参数：@param data 二进制流 输入参数：@return
	 * 返回类型：String：编码字符串 备注：
	 */
	public static String encode(byte[] data) {
		StringBuffer sb = new StringBuffer();

		int len = data.length;

		int i = 0;

		int b1, b2, b3;

		while (i < len) {
			b1 = data[i++] & 0xff;

			if (i == len) {
				sb.append(BASE64ENCODECHARS[b1 >>> 2]);
				sb.append(BASE64ENCODECHARS[(b1 & 0x3) << 4]);
				sb.append("==");

				break;
			}

			b2 = data[i++] & 0xff;

			if (i == len) {
				sb.append(BASE64ENCODECHARS[b1 >>> 2]);
				sb.append(BASE64ENCODECHARS[((b1 & 0x03) << 4)
						| ((b2 & 0xf0) >>> 4)]);
				sb.append(BASE64ENCODECHARS[(b2 & 0x0f) << 2]);
				sb.append('=');

				break;
			}

			b3 = data[i++] & 0xff;

			sb.append(BASE64ENCODECHARS[b1 >>> 2]);
			sb.append(BASE64ENCODECHARS[((b1 & 0x03) << 4)
					| ((b2 & 0xf0) >>> 4)]);
			sb.append(BASE64ENCODECHARS[((b2 & 0x0f) << 2)
					| ((b3 & 0xc0) >>> 6)]);
			sb.append(BASE64ENCODECHARS[b3 & 0x3f]);
		}

		return sb.toString();
	}
}
