package hk.ed.android.library.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.crypto.CipherOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

public class Utils {
	public static Bitmap fileToBitmap(String file, BitmapFactory.Options bmpFactoryOptions, boolean encoded) {
		if (encoded) {
			try {
				byte[] bytes = Security.AES128DecryptionFromByteArray(fileToByteArray(new File(file)));
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bmpFactoryOptions);
				// ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
				// return BitmapFactory.decodeStream(bis, null,
				// bmpFactoryOptions);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return BitmapFactory.decodeFile(file, bmpFactoryOptions);
		}
	}

	public static void saveBitmap(String file, Bitmap bitmap, Bitmap.CompressFormat format, int quality) {
		try {
			File parent = new File(file).getParentFile();
			parent.mkdirs();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(format, quality, baos);
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(baos.toByteArray());
			bos.flush();
			bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final int	BUFFER_SIZE	= 0x1000;	// 4K

	public static byte[] fileToByteArray(File file) throws IOException {

		long time = System.currentTimeMillis();
		FileInputStream in = new FileInputStream(file);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[BUFFER_SIZE];
		while (true) {
			int r = in.read(buf);
			if (r == -1) {
				break;
			}
			out.write(buf, 0, r);
		}
		in.close();
		byte[] bytes = out.toByteArray();
		out.close();
		Log.d("DataProfiling", "File to byte[] time: " + (System.currentTimeMillis() - time) + "ms");
		return bytes;
	}

	public static String bitmapToBase64(Bitmap bitmap) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

		return encoded;
	}

	public static Bitmap base64ToBitmap(String encoded) {
		byte[] decodedString = Base64.decode(encoded, Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

		return decodedByte;
	}

}
