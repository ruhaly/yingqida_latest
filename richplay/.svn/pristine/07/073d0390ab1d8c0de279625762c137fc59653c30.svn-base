package com.yingqida.richplay.baseapi.file;

import com.yingqida.richplay.baseapi.jzlib.Deflater;
import com.yingqida.richplay.baseapi.jzlib.GZIPException;
import com.yingqida.richplay.baseapi.jzlib.Inflater;
import com.yingqida.richplay.baseapi.jzlib.JZlib;

public class ZipUtil {
	public static void test() {
		byte[] hello = "hello".getBytes();
		int err;
		int comprLen = 40000;
		int uncomprLen = comprLen;
		byte[] compr = new byte[comprLen];
		byte[] uncompr = new byte[uncomprLen];
		Deflater deflater = null;
		try {
			deflater = new Deflater(JZlib.Z_DEFAULT_COMPRESSION);
		} catch (GZIPException e) {
			// never happen, because argument is valid.
		}
		deflater.setInput(hello);
		deflater.setOutput(compr);
		while (deflater.total_in != hello.length
				&& deflater.total_out < comprLen) {
			deflater.avail_in = deflater.avail_out = 1;
			// force small buffers
			err = deflater.deflate(JZlib.Z_NO_FLUSH);
		}
		while (true) {
			deflater.avail_out = 1;
			err = deflater.deflate(JZlib.Z_FINISH);
			if (err == JZlib.Z_STREAM_END)
				break;
		}
		err = deflater.end();
		Inflater inflater = new Inflater();
		inflater.setInput(compr);
		inflater.setOutput(uncompr);
		err = inflater.init();
		while (inflater.total_out < uncomprLen && inflater.total_in < comprLen) {
			inflater.avail_in = inflater.avail_out = 1;
			/* force small buffers */
			err = inflater.inflate(JZlib.Z_NO_FLUSH);
			if (err == JZlib.Z_STREAM_END)
				break;
		}
		err = inflater.end();
		int i = 0;
		for (; i < hello.length; i++)
			if (hello[i] == 0)
				break;
		int j = 0;
		for (; j < uncompr.length; j++)
			if (uncompr[j] == 0)
				break;
		if (i == j) {
			for (i = 0; i < j; i++)
				if (hello[i] != uncompr[i])
					break;
			if (i == j) {
				// System.out.println("inflate(): " + new String(uncompr, 0,
				// j));
				return;
			}
		} else {
			// System.out.println("bad inflate");
		}
	}

	public static byte[] zlibCompress(byte[] src) {
		byte[] cmp = new byte[40960];
		Deflater deflater = null;
		try {
			deflater = new Deflater(JZlib.Z_DEFAULT_COMPRESSION);
		} catch (GZIPException e) {
			// never happen, because argument is valid.
		}
		deflater.setInput(src);
		deflater.setOutput(cmp);
		while (deflater.total_in != src.length
				&& deflater.total_out <= cmp.length) {
			deflater.avail_in = deflater.avail_out = 1;
			// force small buffers
			deflater.deflate(JZlib.Z_NO_FLUSH);
		}
		int err = 0;
		while (true) {
			deflater.avail_out = 1;
			err = deflater.deflate(JZlib.Z_FINISH);
			if (err == JZlib.Z_STREAM_END)
				break;
		}
		err = deflater.end();
		byte[] ret = new byte[(int) deflater.getTotalOut()];
		System.arraycopy(cmp, 0, ret, 0, ret.length);
		cmp = null;
		return ret;
	}

	public static byte[] zlibUmcompress(byte[] src) {
		byte[] uncompr = new byte[40960];
		Inflater inflater = new Inflater();
		inflater.setInput(src);
		inflater.setOutput(uncompr);
		inflater.init();
		int err = 0;
		while (inflater.total_out < uncompr.length
				&& inflater.total_in < src.length) {
			inflater.avail_in = inflater.avail_out = 1;
			/* force small buffers */
			err = inflater.inflate(JZlib.Z_NO_FLUSH);
			if (err == JZlib.Z_STREAM_END)
				break;
		}
		err = inflater.end();
		inflater.getTotalOut();
		// System.out.println("zip out length " + inflater.getTotalOut());
		return uncompr;
	}
}
