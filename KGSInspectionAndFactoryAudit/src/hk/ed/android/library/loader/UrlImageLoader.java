/**
 * Copyright (c) 2011 Delicious Computer(r)
 * 
 * Permission is hereby granted to Social Media Broadcasts and associated
 * documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify,
 * merge, publish and with strict prohibition on any distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 * @author Yeung Hoi Hang <chrisyeung@deliciouscomputer.com>
 * @copyright 2011 Deilicious Computer
 * @version 1
 * 
 */
package hk.ed.android.library.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.util.Base64;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * Asynchronized loader for loading picture to a {@code ImageView}.
 */
public class UrlImageLoader {
	private class ImageLoadingTask extends AsyncTask<Void, Order, Void> {
		private Order mark = null;

		@Override
		protected Void doInBackground(Void... params) {
			for (Order o = orders.poll(); o != null; o = orders.poll()) {

				// Log.d("image", "order counts: " + orders.size());

				ImageView view = o.view.get();

				if (view == null) {
					if (mark == o) {
						mark = null;
					}
				} else if (view.isShown() || (mark == o)) {
					if (mark == o) {
						mark = null;
					}
					Bitmap buf = loadPicture(o);
					o.setBufferedBitmap(buf);
					publishProgress(o);
					Thread.yield();
				} else {
					if (mark == null) {
						mark = o;
					}
					orders.add(o);
				}

			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Order... values) {
			Order content = values[0];
			ImageView view = content.view.get();
			if (view != null) {
				Bitmap currentBitmap = content.getBufferedBitmap();
				if (currentBitmap != null) {
					ScaleType scaleType = content.spec.getScaleType();
					if (scaleType != null) {
						view.setScaleType(scaleType);
					}
					view.setImageBitmap(currentBitmap);
					Animation anim = content.spec.getOnLoadAnimation();
					if ((anim != null) && view.isShown()) {
						view.startAnimation(anim);
					}
				} else {
					// view.setScaleType(ScaleType.CENTER_INSIDE);
					view.setScaleType(content.spec.noImageIconScaleType);
					view.setImageResource(content.spec.getNoImageIconId());
				}
			}
			content.setBufferedBitmap(null);
		}
	}

	private static class Order {
		final UrlImage img;
		final UrlImageSpec spec;
		final WeakReference<ImageView> view;
		private Bitmap bufferedBitmap;

		Order(UrlImage img, UrlImageSpec spec, ImageView view) {
			this.img = img;
			this.spec = spec;
			this.view = new WeakReference<ImageView>(view);
		}

		public Bitmap getBufferedBitmap() {
			return bufferedBitmap;
		}

		public void setBufferedBitmap(Bitmap bufferedBitmap) {
			this.bufferedBitmap = bufferedBitmap;
		}
	}

	public static final UrlImageSpec DEFAULT_SPEC = new UrlImageSpec();

	private static final CompressFormat CACHE_FORMAT = CompressFormat.JPEG;
	private static WeakReference<UrlImageLoader> instance = null;

	private static Bitmap createReflection(Bitmap originalImage) {
		// The gap we want between the reflection and the original image
		final int reflectionGap = 4;

		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		// This will not scale but will flip on the Y axis
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		// Create a Bitmap with the flip matrix applied to it.
		// We only want the bottom half of the image
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2,
				matrix, false);

		// Create a new bitmap with same width but taller to fit
		// reflection
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + (height / 2)),
				Config.ARGB_8888);

		// Create a new Canvas with the bitmap that's big enough for
		// the image plus gap plus reflection
		Canvas canvas = new Canvas(bitmapWithReflection);
		// Draw in the original image
		canvas.drawBitmap(originalImage, 0, 0, null);
		// Draw in the gap
		Paint deafaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
		// Draw in the reflection
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		// Create a shader that is a linear gradient that covers the
		// reflection
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff,
				TileMode.CLAMP);
		// Set the paint to use this shader (linear gradient)
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

		return bitmapWithReflection;
	}

	public static synchronized UrlImageLoader getInstance(Context cacheOwner) {
		if (instance != null) {
			UrlImageLoader loader = instance.get();
			if ((loader != null)
					&& (loader.cacheDir == cacheOwner.getApplicationContext().getCacheDir())) {
				return loader;
			}
		}
		UrlImageLoader loader = new UrlImageLoader(cacheOwner);
		instance = new WeakReference<UrlImageLoader>(loader);
		return loader;
	}

	public static void clearCache(Context cacheOwner) {
		File cacheDir = cacheOwner.getApplicationContext().getCacheDir();
		File[] files = cacheDir.listFiles();
		for (File file : files) {
			file.delete();
		}
	}

	private int mTimeout = 10000;

	private final File cacheDir;

	private ConcurrentLinkedQueue<Order> orders = new ConcurrentLinkedQueue<Order>();

	private ImageLoadingTask currentTask = null;

	private UrlImageLoader(Context context) {
		// Log.d(this.getClass().getSimpleName(), "init");
		cacheDir = context.getApplicationContext().getCacheDir();
		// start();
	}

	/**
	 * Get the timeout for the laoding
	 * 
	 * @return
	 */
	public int getTimeout() {
		return mTimeout;
	}

	/**
	 * Load the web picture to the view with default specification
	 * 
	 * @param image
	 * @param toView
	 */
	public synchronized void load(UrlImage image, ImageView toView) {
		load(image, DEFAULT_SPEC, toView);
	}

	/**
	 * Load the web picture to the view with specification
	 * 
	 * @param image
	 * @param spec
	 * @param toView
	 */
	public synchronized void load(UrlImage image, UrlImageSpec spec, ImageView toView) {
		// if (image == null) {
		// return;
		// }
		Bitmap bitmap = image.getBitmap();
		if (bitmap != null) {
			if (spec.scaleType != null) {
				toView.setScaleType(spec.scaleType);
			}
			toView.setImageBitmap(bitmap);
			// Animation ani = spec.getOnLoadAnimation();
			// if (ani != null) {
			// toView.startAnimation(ani);
			// }
			return;
		}
		int oSize = orders.size();
		orders.add(new Order(image, spec, toView));
		int iconResId = spec.getLoadingIconId();
		try {
			if (spec.getLoadingIconId() != 0) {
				toView.setScaleType(ScaleType.CENTER_INSIDE);
			}
			toView.setImageResource(iconResId);
		} catch (Exception e) {
			// no such image
			e.printStackTrace();
		}
		if ((oSize == 0) || (currentTask == null) || (currentTask.getStatus() == Status.FINISHED)) {
			currentTask = new ImageLoadingTask();
			currentTask.execute();
		}
	}

	private Bitmap loadPicture(Order o) {
		UrlImage img = o.img;
		UrlImageSpec spec = o.spec;
		URL url = img.url;

		File cacheFile = null;
		Bitmap bitmap = null;

		if ((cacheDir != null) && spec.isCacheSupport()) {
			String hash;
			if (img.urlPreLoad == null) {
				hash = url.toString();
			} else {
				hash = img.urlPreLoad.getHashCode();
			}

			// cacheFile = new File(cacheDir, hash);
			// String encode = URLEncoder.encode(cacheFile.getName());
			// String encode = Base64.encode(cacheFile.getName());
			String encode = Base64.encodeToString(hash.getBytes(), Base64.DEFAULT).replaceAll(
					"[^0-9a-zA-Z]", "");

			cacheFile = new File(cacheDir, encode);
			// cacheFile = new File(cacheDir, cacheFile.getName());
			// System.out.println(cacheFile.getAbsolutePath());

			if (cacheFile.exists() && cacheFile.lastModified() >= spec.getLastModifiedDate()) {
				bitmap = readFromCache(cacheFile);
			}
			if (bitmap != null) {
				Log.v(this.getClass().getSimpleName(), "load from cache");
				img.setBitmap(bitmap);
				return bitmap;
			}
		}

		if (url == null) {
			// new
			// NullPointerException("img.url cannot be null").printStackTrace();
			// return null;
			try {
				url = img.urlPreLoad.call();
				img.url = url;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		// TODO more ways to load
		bitmap = readFromNet(url);
		Log.v(this.getClass().getSimpleName(), "load from network");

		if (bitmap != null) {
			if (spec.isReflectionSupport()) {
				bitmap = createReflection(bitmap);
			}
			// Log.v(this.getClass().getSimpleName(), "load from net");
			if ((cacheFile != null) && spec.isCacheSupport()) {
				writeToCache(cacheFile, bitmap);
			}
			img.setBitmap(bitmap);
			return bitmap;
		} else {

			return null;
		}
	}

	private void writeToCache(File cacheFile, Bitmap bitmap) {
		// write into the cache:
		try {
			FileOutputStream fout = new FileOutputStream(cacheFile);
			bitmap.compress(CACHE_FORMAT, 100, fout);
			// Log.v(this.getClass().getSimpleName(),
			// "save to cache");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Bitmap readFromCache(File cacheFile) {
		// try read cache file
		Bitmap bitmap = BitmapFactory.decodeFile(cacheFile.getAbsolutePath());

		return bitmap;
	}

	private Bitmap readFromNet(URL url) {
		Bitmap bitmap = null;
		try {
			// TODO added time delay for testing
			// try {
			// Thread.sleep(1000);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }

			URLConnection conn = url.openConnection();
			conn.setDoInput(true);
			conn.setConnectTimeout(mTimeout);
			conn.setReadTimeout(mTimeout);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			// Log.e("image", img.url.toString());
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	/**
	 * Set the timeout for laoding
	 * 
	 * @param mTimeout
	 */
	public void setTimeout(int mTimeout) {
		this.mTimeout = mTimeout;
	}
}
