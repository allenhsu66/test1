package hk.ed.android.library.util;

import android.util.Log;

public class LoggingUtils {
	
	private static boolean log = true;
	private static boolean useLogFile = false;
	
	public static void log(String tag, String text) {
		if (log) {
			if (tag == null || tag.equals(""))
				tag = "DroidFramework";
			Log.d(tag, text + "");
			if (useLogFile) {
				// TODO
			}
		}
	}

}
