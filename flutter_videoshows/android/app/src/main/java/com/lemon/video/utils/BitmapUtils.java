package com.lemon.video.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.os.StatFs;
import android.util.Log;
import android.view.View;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * 图片解析工具类
 * 
 * @author blueming.wu
 * 
 */
public class BitmapUtils {

	/**
	 * 根据指定的宽高，保持纵横比，缩小读取到的图片信息
	 * 
	 * @param bytes
	 *            读取到的图片信息为字节数组
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getBitmap(byte[] bytes, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
		options.inJustDecodeBounds = false;
		int scaleX = options.outWidth / width;
		int scaleY = options.outHeight / height;
		int scale = scaleX > scaleY ? scaleX : scaleY;
		options.inSampleSize = scale;
		bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
		return bitmap;
	}

	/**
	 * 把图片流转化成字节数组
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;

	}

	private static Bitmap getWhiteLayout(int w, int h) {
		Bitmap bitLayout = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvasLayout = new Canvas(bitLayout);
		canvasLayout.drawColor(Color.WHITE);
		canvasLayout.save();
		canvasLayout.restore();
		return bitLayout;
	}

	/**
	 * 在大图指定位置合成小图
	 * 
	 * @param bit_big
	 * @param bit_small
	 * @param pos_x
	 * @param pos_y
	 * @return
	 */
	public static Bitmap getNewBitmap(Bitmap bit_big, Bitmap bit_small, int pos_x, int pos_y) {
		Bitmap bit_new = null;
		bit_new = getWhiteLayout(bit_big.getWidth(), bit_big.getHeight());
		Canvas canvas = new Canvas(bit_new);
		Paint paint = new Paint();
		canvas.drawBitmap(bit_big, 0, 0, paint);
		canvas.drawBitmap(bit_small, pos_x, pos_y, paint);
		canvas.save();
		canvas.restore();
		return bit_new;
	}

	/**
	 * 根据输入流按纵横比缩放图片
	 * 
	 * @author hongchang.liu
	 * @date 2012-09-21
	 * @param in
	 * @param width
	 * @param height
	 * @return
	 */

	public static Bitmap getBitmapFromStream(InputStream in, int width, int height) {
		Bitmap bitmap = null;

		BitmapFactory.Options options = new BitmapFactory.Options();

		// 内部转换,将流变为字节数组
		byte[] bytes = BitmapUtils.getByteFromByte(in);
		/**
		 * 意思就是说如果该值设为true那么将不返回实际的bitmap不给其分配内存空间而里面只包括一些解码边界信息即图片大小信息，
		 * 那么相应的方法也就出来了，通过设置inJustDecodeBounds为true，获取到outHeight(图片原始高度)和
		 * outWidth(图片的原始宽度)，
		 * 然后计算一个inSampleSize(缩放值)，然后就可以取图片了，这里要注意的是，inSampleSize 可能小于0，必须做判断。
		 * 
		 * */
		if (bytes != null) {
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
			options.inJustDecodeBounds = false;
			int scaleX = options.outWidth / width;
			int scaleY = options.outHeight / height;
			int scale = scaleX > scaleY ? scaleX : scaleY;
			options.inSampleSize = scale;

			bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return bitmap;

	}

	/**
	 * 图片缩放
	 * 
	 * @author mingsong.zhang
	 * @date 2012-10-19
	 * 
	 * @param bitmap
	 *            对象
	 * @param w
	 *            要缩放的宽度
	 * @param h
	 *            要缩放的高度
	 * @return newBmp 新 Bitmap对象
	 */
	public static Bitmap matrixBitmap(Bitmap bitmap, int w, int h) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		if (newBmp != bitmap) {
			bitmap.recycle();
		}
		return newBmp;
	}

	/**
	 * 图片缩放
	 * 
	 * @author mingsong.zhang
	 * @date 2012-10-19
	 * 
	 * @param bitmap
	 *            对象
	 * @param w
	 *            要缩放的宽度
	 * @param h
	 *            要缩放的高度
	 * @return newBmp 新 Bitmap对象
	 */
	public static Bitmap resizeBitmap(Bitmap bitmap, int w, int h) {
		return resizeBitmap(bitmap, w, h, true);
	}

	/**
	 * 指定数据图片缩放
	 * 
	 * @param bitmap
	 * @param w
	 * @param h
	 * @param isRecycle
	 * @return
	 */
	public static Bitmap resizeBitmap(Bitmap bitmap, int w, int h, boolean isRecycle) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) w / width);
		float scaleHeight = ((float) h / height);
		float scale = Math.max(scaleWidth, scaleHeight);
		matrix.postScale(scale, scale);
		Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		if (isRecycle && newBmp != bitmap) {
			bitmap.recycle();
		}
		return newBmp;
	}

	/**
	 * @author hongchang.liu
	 * @date 2012-09-21
	 * @param is
	 *            图片的输入字节流
	 * 
	 *            android 开发中，经常会获取图片的操作，前段时间做联网的图片读取操作时，
	 *            偶然发现了个问题。经上网查阅资料，得知，这个android 的一个bug 。 在android 2.2 以下（包括2.2）
	 *            用 BitmapFactory.decodeStream() 这个方法， 会出现概率性的解析失败的异常。而在高版本中，eg
	 *            2.3 则不会出现这种异常。解决方法如下：getByte(InputStream is)
	 * 
	 * */
	@SuppressWarnings("finally")
	private static byte[] getByteFromByte(InputStream is) {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

		byte[] b = null;
		byte[] myByte = null;
		try {

			b = new byte[is.available()];
			int len = 0;
			while ((len = is.read(b, 0, b.length)) != -1) {

				arrayOutputStream.write(b, 0, len);
				arrayOutputStream.flush();

			}
			myByte = arrayOutputStream.toByteArray();
			arrayOutputStream.close();
			is.close();

		} catch (Exception e) {

			throw new RuntimeException();
		} finally {
			return myByte;
		}

	}

	/**
	 * 以最省内存的方式读取本地资源的图片
	 * 
	 * @author hongchang.liu
	 * @date 2012-09-21
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Config.RGB_565;
		// 如果它被设置为true,那么产生的图将分配其像素,这样他们可以被净化,如果系统需要回收内存。
		opt.inPurgeable = true;
		// 与inPurgeable同时设置
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/**
	 * 从WEBVIEW缓存获取图片
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Bitmap getPictureFromCache(File cacheDir) throws FileNotFoundException {
		Bitmap bitmap = null;
		File file = new File(cacheDir + "/webviewCacheChromium/f_000001");
		FileInputStream inStream = new FileInputStream(file);
		bitmap = BitmapFactory.decodeStream(inStream);
		return bitmap;
	}

	/**
	 * 获取bitmap的字节大小
	 * 
	 * @param bitmap
	 * @return
	 */
	public static int getBitmapSize(Bitmap bitmap) {
		// 每行字节大小乘以高度像素
		return bitmap.getRowBytes() * bitmap.getHeight();
	}

	/**
	 * 获取文件路径空间大小
	 * 
	 * @param path
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static long getUsableSpace(File path) {

		long availableSize = 0;
		try {
			final StatFs stats = new StatFs(path.getPath());
			if (stats != null) {
				long blockSize = stats.getBlockSize();
				long alBlock = stats.getAvailableBlocks();
				availableSize = blockSize * alBlock;
			}
		} catch (Exception e) {
		}

		return availableSize;
	}

	/**
	 * @author blueming.wu
	 * 
	 *         取资源中的图片资源压缩成指定宽高
	 * @param res
	 * @param resId
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPurgeable = true;
		BitmapFactory.decodeResource(res, resId, options);

		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	/**
	 * @author blueming.wu
	 * 
	 *         取文件路径下的图片压缩
	 * @param filename
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromFile(String filename, int reqWidth, int reqHeight) {

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPurgeable = true;
		BitmapFactory.decodeFile(filename, options);

		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filename, options);
	}

	/**
	 * 解析句柄不作压缩
	 * 
	 * @param fileDescriptor
	 * @return
	 */
	public static Bitmap decodeFileDescriptor(FileDescriptor fileDescriptor) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inInputShareable = true;
		try {
			return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
		} catch (Throwable e) {
			return null;
		}
	}

	/**
	 * @author blueming.wu
	 * 
	 *         根据具体要压缩的宽高，算出压缩比率
	 * @param options
	 * @param reqWidth
	 *            需要压缩的宽
	 * @param reqHeight
	 *            需要压缩的高
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			// 如果原图宽大于高
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}

			final float totalPixels = width * height;

			final float totalReqPixelsCap = reqWidth * reqHeight * 2;

			while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
				inSampleSize++;
			}
		}
		return inSampleSize;
	}

	/**
	 * 指定宽度输出图片
	 * 
	 * @param out
	 * @param length
	 * @return
	 */
	public static Bitmap compress(ByteArrayOutputStream out, int length) {
		Bitmap image = null;
		try {
			image = BitmapFactory.decodeByteArray(out.toByteArray(), 0, length);
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
			try {
				BitmapFactory.Options opts = new BitmapFactory.Options();
				opts.inSampleSize = 3;
				image = BitmapFactory.decodeByteArray(out.toByteArray(), 0, length, opts);
			} catch (OutOfMemoryError e1) {
				// TODO: handle exception
				return null;
			}

		}

		return image;

	}

	/**
	 * 图片按比例压缩
	 * 
	 * @param in
	 * @return
	 */
	public static Bitmap compress(InputStream in, float ww, float hh) {
		Bitmap image = null;
		try {
			image = BitmapFactory.decodeStream(in);
		} catch (OutOfMemoryError e) {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inSampleSize = 3;
			image = BitmapFactory.decodeStream(in, null, opts);
		}

		if (image == null)
			return null;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

		int options = 99;
		while (baos.toByteArray().length / 1024 > 100) {
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
			if (options > 10)
				options -= 10;// 每次都减少10

		}

		try {
			return getCroppedBitmap(image, ww, hh);// 压缩好比例大小后再进行质量压缩
		} catch (OutOfMemoryError e) {
			return null;
		}

	}

	/**
	 * 指定宽高输出bitmap
	 * 
	 * @param bmp
	 * @param ww
	 * @param hh
	 * @return
	 */
	private static Bitmap getCroppedBitmap(Bitmap bmp, float ww, float hh) {
		Bitmap sbmp;
		int w = (int) ww;
		int h = (int) hh;
		// 确定图片尺寸
		if (bmp.getWidth() != w || bmp.getHeight() != h) {
			bmp = splitBitmap(bmp, (float) w / (float) h);
			sbmp = Bitmap.createScaledBitmap(bmp, w, h, false);
		} else {
			sbmp = bmp;
		}
		// 自定义bitmap
		Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		// 画布
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(Color.parseColor("#ffffff"));

		final Rect rect = new Rect(1, 1, w - 1, h - 1);

		canvas.drawRect(rect, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(sbmp, rect, rect, paint);

		return output;
	}

	/**
	 * 图片切割
	 * 
	 * @param bitmap
	 * @paramw切割的宽度
	 * @paramh切割的高度
	 * @paramrate长宽比率
	 * @return
	 */
	private static Bitmap splitBitmap(Bitmap bitmap, float rate) {

		Bitmap map;
		int x = 0;
		int y = 0;
		int w = 0;
		int h = 0;
		int bw = bitmap.getWidth();
		int bh = bitmap.getHeight();
		if ((float) bw / (float) bh > rate) {
			w = (int) (bh * rate);
			x = (bw - w) / 2;
			h = bh;
		} else if ((float) bw / (float) bh < rate) {
			h = (int) ((float) bw / (float) rate);
			w = bw;
		} else {
			h = bh;
			w = bw;
		}
		map = Bitmap.createBitmap(bitmap, x, y, w, h);
		return map;
	}

	public static Bitmap decodeByteArray(byte[] data) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inInputShareable = true;
		try {
			return BitmapFactory.decodeByteArray(data, 0, data.length, options);
		} catch (Throwable e) {
			return null;
		}
	}

	// 获取指定Activity的截屏原图
	@SuppressWarnings("deprecation")
	public static Bitmap takeScreenShot(Activity activity) {
		// View是你需要截图的View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		// 获取状态栏高度
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		// 获取屏幕长和高
		int dWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
		int dHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
		Log.i("lhc", "状态栏高度：" + statusBarHeight + "屏幕宽高：" + dWidth + "x" + dHeight);
		// 去掉标题栏
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, dWidth, dHeight - statusBarHeight);

		//
		view.destroyDrawingCache();
		return b;
	}

	/**
	 * bitmap to bytearray
	 * 
	 * @param bmp
	 * @param needRecycle
	 * @return
	 */
	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;

	public static Bitmap extractThumbByte(final byte[] btyes, final int height, final int width, final boolean crop) {

		BitmapFactory.Options options = new BitmapFactory.Options();

		try {
			options.inJustDecodeBounds = true;
			Bitmap tmp = BitmapFactory.decodeByteArray(btyes, 0, btyes.length, options);
			if (tmp != null) {
				tmp.recycle();
				tmp = null;
			}

			Log.d("lhc", "extractThumbNail: round=" + width + "x" + height + ", crop=" + crop);
			final double beY = options.outHeight * 1.0 / height;
			final double beX = options.outWidth * 1.0 / width;
			Log.d("lhc", "extractThumbNail: extract beX = " + beX + ", beY = " + beY);
			options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
			if (options.inSampleSize <= 1) {
				options.inSampleSize = 1;
			}

			// NOTE: out of memory error
			while (options.outHeight * options.outWidth / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
				options.inSampleSize++;
			}

			int newHeight = height;
			int newWidth = width;
			if (crop) {
				if (beY > beX) {
					newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
				} else {
					newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
				}
			} else {
				if (beY < beX) {
					newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
				} else {
					newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
				}
			}

			options.inJustDecodeBounds = false;

			Log.i("lhc", "bitmap required size=" + newWidth + "x" + newHeight + ", orig=" + options.outWidth + "x"
					+ options.outHeight + ", sample=" + options.inSampleSize);
			Bitmap bm = BitmapFactory.decodeByteArray(btyes, 0, btyes.length, options);
			if (bm == null) {
				Log.e("lhc", "bitmap decode failed");
				return null;
			}

			Log.i("lhc", "bitmap decoded size=" + bm.getWidth() + "x" + bm.getHeight());
			final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
			if (scale != null) {
				bm.recycle();
				bm = scale;
			}

			if (crop) {
				final Bitmap cropped = Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1,
						(bm.getHeight() - height) >> 1, width, height);
				if (cropped == null) {
					return bm;
				}

				bm.recycle();
				bm = cropped;
				Log.i("lhc", "bitmap croped size=" + bm.getWidth() + "x" + bm.getHeight());
			}
			return bm;

		} catch (final OutOfMemoryError e) {
			Log.e("exception", "decode bitmap failed: " + e.getMessage());
			options = null;
		}

		return null;
	}

	/**
	 * 将彩色图转换为灰度图
	 * 
	 * @param img
	 *            位图
	 * @return 返回转换好的位图
	 */
	public static Bitmap convertGreyImg(Bitmap img) {
		int width = img.getWidth(); // 获取位图的宽
		int height = img.getHeight(); // 获取位图的高

		int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

		img.getPixels(pixels, 0, width, 0, 0, width, height);
		int alpha = 0xFF << 24;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int grey = pixels[width * i + j];

				int red = ((grey & 0x00FF0000) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);

				grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
				grey = alpha | (grey << 16) | (grey << 8) | grey;
				pixels[width * i + j] = grey;
			}
		}
		Bitmap result = Bitmap.createBitmap(width, height, Config.RGB_565);
		result.setPixels(pixels, 0, width, 0, 0, width, height);
		return result;
	}

	/**
	 * 把bitmap存入指定的路径中
	 * 
	 * @param path
	 * @param bitmap
	 * @throws IOException
	 */
	public static boolean saveBitmap(String path, Bitmap bitmap) throws IOException {

		if (path != null && bitmap != null) {
			File file = new File(path);
			if (!file.exists()) {
				// 如果不存在，也有可能图片文件目录也不存在
				file.getParentFile().mkdirs();
				file.createNewFile();
			} else
				return true;
			OutputStream out = new FileOutputStream(file);
			String name = file.getName();
			String postfix = name.substring(name.lastIndexOf(".") + 1);
			if ("png".equals(postfix) || "+".equals(postfix))
				return bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			else
				return bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
		}
		return false;
	}

	/**
	 * 把bitmap存入指定的路径中
	 * 
	 * @parampath
	 * @param bitmap
	 * @throws IOException
	 */
	public static boolean saveBitmap(File file, Bitmap bitmap) throws IOException {

		if (file == null || bitmap == null) {
			return false;
		}
		// 如果不存在，也有可能图片文件目录也不存在
		file.getParentFile().mkdirs();
		file.createNewFile();

		OutputStream out = new FileOutputStream(file);
		String name = file.getName();
		String postfix = name.substring(name.lastIndexOf(".") + 1);
		if ("png".equals(postfix) || "+".equals(postfix))
			return bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		else
			return bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

	}

	/**
	 * 把bitmap存入指定的路径中
	 * 
	 * @parampath
	 * @parambitmap
	 * @throws IOException
	 */
	public static boolean saveGif(File file, byte[] bs) throws IOException {

		if (file == null || bs == null) {
			return false;
		}
		// 如果不存在，也有可能图片文件目录也不存在
		file.getParentFile().mkdirs();
		file.createNewFile();

		OutputStream out = new FileOutputStream(file);
		out.write(bs, 0, bs.length);
		out.close();
		return true;
	}

	public static Bitmap parseBitmap(byte[] data, int mMaxWidth, int mMaxHeight) {
		// TODO 采集流量,暂时删除，后续需要再添加
		// CollectionBiz.newInstance().collecFlows(object.length / 1024);
		BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
		Bitmap bitmap = null;
		if (mMaxWidth == 0 && mMaxHeight == 0) {
			decodeOptions.inPreferredConfig = Config.RGB_565;
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
		} else {

			// If we have to resize this image, first get the natural bounds.
			decodeOptions.inJustDecodeBounds = true;
			decodeOptions.inPurgeable = true;
			decodeOptions.inInputShareable = true;
			decodeOptions.inPreferredConfig = Config.RGB_565;
			BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
			int actualWidth = decodeOptions.outWidth;
			int actualHeight = decodeOptions.outHeight;

			// Then compute the dimensions we would ideally like to decode to.
			int desiredWidth = getResizedDimension(mMaxWidth, mMaxHeight, actualWidth, actualHeight);
			int desiredHeight = getResizedDimension(mMaxHeight, mMaxWidth, actualHeight, actualWidth);

			// Decode to the nearest power of two scaling factor.
			decodeOptions.inJustDecodeBounds = false;
			// TODO(ficus): Do we need this or is it okay since API 8 doesn't
			// support it?
			// decodeOptions.inPreferQualityOverSpeed =
			// PREFER_QUALITY_OVER_SPEED;
			decodeOptions.inSampleSize = findBestSampleSize(actualWidth, actualHeight, desiredWidth, desiredHeight);
			Bitmap tempBitmap = BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);

			// If necessary, scale down to the maximal acceptable size.
			if (tempBitmap != null && (tempBitmap.getWidth() > desiredWidth || tempBitmap.getHeight() > desiredHeight)) {
				bitmap = Bitmap.createScaledBitmap(tempBitmap, desiredWidth, desiredHeight, true);
				tempBitmap.recycle();
			} else {
				bitmap = tempBitmap;
			}
		}

		return bitmap;
	}

	/**
	 * 得到ResizedDimension
	 * 
	 * @param maxPrimary
	 * @param maxSecondary
	 * @param actualPrimary
	 * @param actualSecondary
	 * @return
	 */
	private static int getResizedDimension(int maxPrimary, int maxSecondary, int actualPrimary, int actualSecondary) {
		// If no dominant value at all, just return the actual.
		if (maxPrimary == 0 && maxSecondary == 0) {
			return actualPrimary;
		}

		// If primary is unspecified, scale primary to match secondary's scaling
		// ratio.
		if (maxPrimary == 0) {
			double ratio = (double) maxSecondary / (double) actualSecondary;
			return (int) (actualPrimary * ratio);
		}

		if (maxSecondary == 0) {
			return maxPrimary;
		}

		double ratio = (double) actualSecondary / (double) actualPrimary;
		int resized = maxPrimary;
		if (resized * ratio > maxSecondary) {
			resized = (int) (maxSecondary / ratio);
		}
		return resized;
	}

	/**
	 * 查找最佳比例
	 * 
	 * @param actualWidth
	 * @param actualHeight
	 * @param desiredWidth
	 * @param desiredHeight
	 * @return
	 */
	static int findBestSampleSize(int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
		double wr = (double) actualWidth / desiredWidth;
		double hr = (double) actualHeight / desiredHeight;
		double ratio = Math.min(wr, hr);
		float n = 1.0f;
		while ((n * 2) <= ratio) {
			n *= 2;
		}

		return (int) n;
	}
	
	/**
	* @Title: toRoundBitmap
	* @Description: 将bitmap转化成圆形的bitmap
	* @param @param bitmap
	* @param @return    设定文件
	* @return Bitmap    返回类型
	* @throws
	*/
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	/**
	 * ADD BY YYK
     * get the orientation of the bitmap {@link ExifInterface}
     * @param path
     * @return
     */
    public final static int getDegress(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                degree = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                degree = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                degree = 270;
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
     
    /**
     * ADD BY YYK
     * 
     * rotate the bitmap 
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress); 
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }
	
}
