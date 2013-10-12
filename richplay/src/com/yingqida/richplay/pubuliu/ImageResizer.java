/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yingqida.richplay.pubuliu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.yingqida.richplay.BuildConfig;
import com.yingqida.richplay.pubuliu.ImageWorker.ICallBack;

/**
 * A simple subclass of {@link ImageWorker} that resizes images from resources
 * given a target width and height. Useful for when the input images might be
 * too large to simply load directly into memory.
 */
public class ImageResizer extends ImageWorker {
	private static final String TAG = "ImageWorker";
	protected int mImageWidth;
	protected int mImageHeight;

	/**
	 * Initialize providing a single target image size (used for both width and
	 * height);
	 * 
	 * @param context
	 * @param imageWidth
	 * @param imageHeight
	 */
	// public ImageResizer(Context context, int imageWidth, int imageHeight) {
	// super(context);
	// setImageSize(imageWidth, imageHeight);
	// }

	/**
	 * Initialize providing a single target image size (used for both width and
	 * height);
	 * 
	 * @param context
	 * @param imageSize
	 */
	// public ImageResizer(Context context, int imageSize) {
	// super(context);
	// setImageSize(imageSize);
	// }

	public ImageResizer(Context context, int imageSize, ICallBack callback) {
		super(context, callback);
		setImageSize(imageSize);
	}

	/**
	 * Set the target image width and height.
	 * 
	 * @param width
	 * @param height
	 */
	public void setImageSize(int width, int height) {
		mImageWidth = width;
		mImageHeight = height;
	}

	/**
	 * Set the target image size (width and height will be the same).
	 * 
	 * @param size
	 */
	public void setImageSize(int size) {
		setImageSize(size, size);
	}

	/**
	 * The main processing method. This happens in a background task. In this
	 * case we are just sampling down the bitmap and returning it from a
	 * resource.
	 * 
	 * @param resId
	 * @return
	 */
	private Bitmap processBitmap(int resId, int width) {
		if (BuildConfig.DEBUG) {
			Log.d(TAG, "processBitmap - " + resId);
		}
		return decodeSampledBitmapFromResource(mContext.getResources(), resId,
				width, mImageHeight);
	}

	@Override
	protected Bitmap processBitmap(Object data, int width) {
		return processBitmap(Integer.parseInt(String.valueOf(data)), width);
	}

	/**
	 * Decode and sample down a bitmap from resources to the requested width and
	 * height.
	 * 
	 * @param res
	 *            The resources object containing the image data
	 * @param resId
	 *            The resource id of the image data
	 * @param reqWidth
	 *            The requested width of the resulting bitmap
	 * @param reqHeight
	 *            The requested height of the resulting bitmap
	 * @return A bitmap sampled down from the original with the same aspect
	 *         ratio and dimensions that are equal to or greater than the
	 *         requested width and height
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);
		reqHeight = Math.round(reqWidth * (float) reqHeight
				/ (float) options.outWidth);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		Bitmap bmp = BitmapFactory.decodeResource(res, resId, options);
		Runtime.getRuntime().gc();
		System.out.println(null == bmp ? "" : bmp.getWidth());
		return bmp;
		// return null == bmp ? bmp : ImageUtil.zoomBmp(bmp, (float) reqWidth
		// / bmp.getWidth(), true);
	}

	/**
	 * Decode and sample down a bitmap from a file to the requested width and
	 * height.
	 * 
	 * @param filename
	 *            The full path of the file to decode
	 * @param reqWidth
	 *            The requested width of the resulting bitmap
	 * @param reqHeight
	 *            The requested height of the resulting bitmap
	 * @return A bitmap sampled down from the original with the same aspect
	 *         ratio and dimensions that are equal to or greater than the
	 *         requested width and height
	 */
	public static synchronized Bitmap decodeSampledBitmapFromFile(
			String filename, int reqWidth, int reqHeight) {
		if ("".equals(filename) || filename == null) {
			return null;
		}
		// return decodeFile(filename, reqWidth);
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filename, options);
		reqHeight = Math.round(reqWidth * (float) reqHeight
				/ (float) options.outWidth);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		Bitmap bmp = BitmapFactory.decodeFile(filename, options);
		Runtime.getRuntime().gc();
		return bmp;
	}

	/**
	 * Calculate an inSampleSize for use in a {@link BitmapFactory.Options}
	 * object when decoding bitmaps using the decode* methods from
	 * {@link BitmapFactory}. This implementation calculates the closest
	 * inSampleSize that will result in the final decoded bitmap having a width
	 * and height equal to or larger than the requested width and height. This
	 * implementation does not ensure a power of 2 is returned for inSampleSize
	 * which can be faster when decoding but results in a larger bitmap which
	 * isn't as useful for caching purposes.
	 * 
	 * @param options
	 *            An options object with out* params already populated (run
	 *            through a decode* method with inJustDecodeBounds==true
	 * @param reqWidth
	 *            The requested width of the resulting bitmap
	 * @param reqHeight
	 *            The requested height of the resulting bitmap
	 * @return The value to be used for inSampleSize
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		// if (height > reqHeight) {
		if (width > reqWidth) {
			inSampleSize = Math.round((float) width / (float) reqWidth);
			// if (width > height) {
			// inSampleSize = Math.round((float) height / (float) reqHeight);
			// }
			//
			// else {
			inSampleSize = Math.round((float) width / (float) reqWidth);

			// This offers some additional logic in case the image has a strange
			// aspect ratio. For example, a panorama may have a much larger
			// width than height. In these cases the total pixels might still
			// end up being too large to fit comfortably in memory, so we should
			// be more aggressive with sample down the image (=larger
			// inSampleSize).

			final float totalPixels = width * height;

			// Anything more than 2x the requested pixels we'll sample down
			// further.
			final float totalReqPixelsCap = reqWidth * reqHeight * 2;

			while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
				inSampleSize++;
			}
		}
		return inSampleSize;
	}

	public static Bitmap decodeFile(String pathName, int dstWidth) {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, options);
		options.inJustDecodeBounds = false;
		options.inSampleSize = 1;
		// options.inSampleSize = calculateSampleSize(options.outWidth,
		// options.outHeight, dstWidth, dstWidth * options.outHeight
		// / options.outWidth);
		Bitmap unscaledBitmap = BitmapFactory.decodeFile(pathName, options);
		return createScaledBitmap(unscaledBitmap, dstWidth, dstWidth
				* options.outHeight / options.outWidth);
	}

	public static int calculateSampleSize(int srcWidth, int srcHeight,
			int dstWidth, int dstHeight) {
		return srcWidth / dstWidth;
	}

	public static Bitmap createScaledBitmap(Bitmap unscaledBitmap,
			int dstWidth, int dstHeight) {
		Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(),
				unscaledBitmap.getHeight(), dstWidth, dstHeight);
		Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(),
				unscaledBitmap.getHeight(), dstWidth, dstHeight);
		Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(),
				dstRect.height(), Config.ARGB_8888);
		Canvas canvas = new Canvas(scaledBitmap);
		canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(
				Paint.FILTER_BITMAP_FLAG));
		return scaledBitmap;
	}

	public static Rect calculateSrcRect(int srcWidth, int srcHeight,
			int dstWidth, int dstHeight) {
		return new Rect(0, 0, srcWidth, srcHeight);
	}

	public static Rect calculateDstRect(int srcWidth, int srcHeight,
			int dstWidth, int dstHeight) {
		return new Rect(0, 0, dstWidth, dstHeight);

	}
}
