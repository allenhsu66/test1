package hk.ed.android.library.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.Executor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class FileUtils {

	public static class WriteFileSpec {
		public File file;
		public int bufferSize;
		public InputStream inputStream;
		public Integer totalSize;

		public WriteFileSpec(File file, int bufferSize, InputStream is,
				Integer totalSize) {
			this.file = file;
			this.bufferSize = bufferSize;
			this.inputStream = is;
			this.totalSize = totalSize;
		}
	}

	public static class WriteFileTask extends
			AsyncTask<WriteFileSpec, Float, Exception> {

		private OnWriteFileListener listener;

		public WriteFileTask(OnWriteFileListener listener) {
			this.listener = listener;
		}

		@Override
		protected Exception doInBackground(WriteFileSpec... params) {
			if (params != null) {
				for (WriteFileSpec spec : params) {
					try {
						writeFile(spec, listener);
						if (listener != null) {
							listener.onSuccess(spec.file);
						}
					} catch (IOException e) {
						e.printStackTrace();
						if (spec.file.exists()) {
							LoggingUtils.log(
									"FileUtils",
									"Deleting the corrupted file: "
											+ spec.file.getPath() + ".");
							spec.file.delete();
						}
						LoggingUtils.log("FileUtils", "Write file failed");
						if (listener != null) {
							listener.onFail(e);
						}
					}
				}
			}
			return null;
		}

	}

	public static interface OnWriteFileListener {

		public void onSuccess(File file);

		public void onFail(Exception e);

		/**
		 * Callback when async writing file.
		 * 
		 * @param progress
		 *            A number indicating the progress ranging from 0 to 1, or
		 *            null if indetermined
		 */
		public void onProgressUpdate(Float progress);

		public void onCancel();
	}
	
	public static File getWritableFile(String path) {
		File f = new File(path);
		if (!f.getParentFile().exists())
			f.mkdirs();
		return f;
	}

	public static void writeFile(WriteFileSpec spec,
			OnWriteFileListener listener) throws IOException {
		FileOutputStream fos = new FileOutputStream(spec.file);
		byte[] buffer = new byte[spec.bufferSize];
		int len = 0;
		if (listener != null) {
			listener.onProgressUpdate(0f);
		}
		int progress = 0;
		while ((len = spec.inputStream.read(buffer, 0, spec.bufferSize)) > 0) {
			fos.write(buffer, 0, len);
			if (listener != null && spec.totalSize != null) {
				progress += len;
				listener.onProgressUpdate((float) progress
						/ (float) spec.totalSize);
			}
		}
		fos.close();
		listener.onProgressUpdate(1f);
		LoggingUtils.log("FileUtils", "Write file succeded");
	}

	public static WriteFileTask writeFileAsync(WriteFileSpec spec,
			OnWriteFileListener listener, Executor executor) {
		WriteFileTask task = new WriteFileTask(listener);
		task.executeOnExecutor(executor, spec);
		return task;
	}

	public static WriteFileTask writeFileAsync(WriteFileSpec spec,
			OnWriteFileListener listener) {
		return writeFileAsync(spec, listener, AsyncTask.SERIAL_EXECUTOR);
	}

	public static WriteFileTask writeStringAsync(File file, int bufferSize,
			String content, Charset charset, OnWriteFileListener listener) {
		byte[] bytes = content.getBytes(charset);
		WriteFileSpec spec = new WriteFileSpec(file, bufferSize,
				new ByteArrayInputStream(bytes), bytes.length);
		return writeFileAsync(spec, listener);
	}

	public static WriteFileTask writeBitmapAsync(
			File file, int bufferSize, final Bitmap content,
			Bitmap.CompressFormat format, int quality,
			final OnWriteFileListener listener) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		content.compress(format, quality, baos);
		byte[] bytes = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		WriteFileSpec spec = new WriteFileSpec(file, bufferSize, bais, bytes.length);
		return writeFileAsync(spec, listener);
	}
	
	public static String readTextFile(File file, Charset charset, int bufferSize) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		StringBuffer sb = new StringBuffer();
		byte[] buffer = new byte[bufferSize];
		while (fis.read(buffer) > 0) {
			sb.append(new String(buffer, charset));
		}
		fis.close();
		return sb.toString();
	}
	
	public static Bitmap readBitmapFile(File file, int requestWidth, int requestHeight, boolean justBigger) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
		int sampling = 1;
		while (opts.outHeight / sampling > requestHeight || opts.outWidth / sampling > requestWidth) {
			sampling *= 2;
		}
		if (sampling != 1 && justBigger) {
			sampling /= 2;
		}
		opts.inSampleSize = sampling;
		opts.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
	}

}
