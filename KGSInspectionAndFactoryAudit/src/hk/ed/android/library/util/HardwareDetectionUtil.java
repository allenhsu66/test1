package hk.ed.android.library.util;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;

public class HardwareDetectionUtil {
	
	public static Point getDisplaySize(Activity a) {
		Display display = a.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}
	
	public static int dpToPx(int dp, Resources res) {
		final float scale = res.getDisplayMetrics().density;
		int px = (int) (dp * scale + 0.5f);
		return px;
	}

}
