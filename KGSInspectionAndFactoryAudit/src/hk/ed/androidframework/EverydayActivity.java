package hk.ed.androidframework;

import hk.ed.androidframework.api.EverydayApiHelper;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

public class EverydayActivity extends Activity {

	// sample code
	// private static final String SAMPLE_NAME = "sampleName";
	// public static void launchActivity(Context from, String params) {
	// Intent intent = new Intent(from, EverydayActivity.class);
	// intent.putExtra(SAMPLE_NAME, params);
	// from.startActivity(intent);
	// }
//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// sample code
		// String params = getIntent().getStringExtra(SAMPLE_NAME);
	}

	private EverydayApiHelper apiHelper;

	public EverydayApiHelper getWebApiHelper() {
		if (apiHelper == null) {
			apiHelper = new EverydayApiHelper();
		}
		return apiHelper;
	}

	private EverydaySharedPreferences sharedPreferences;

	public EverydaySharedPreferences getSharedPreferences() {
		if (sharedPreferences == null) {
			sharedPreferences = new EverydaySharedPreferences(this);
		}
		return sharedPreferences;
	}

	public void toast(final int resId) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(EverydayActivity.this, resId, Toast.LENGTH_SHORT)
						.show();
			}

		});
	}

	public void toast(final String text) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(EverydayActivity.this, text, Toast.LENGTH_SHORT)
						.show();
			}

		});
	}

	private ProgressDialog progressDialog;

	public ProgressDialog showProgressDialog(String title, String message) {
		progressDialog = ProgressDialog.show(this, title, message);
		return progressDialog;
	}

	public ProgressDialog showProgressDialog() {
		return showProgressDialog("Loading...", "Loading...");
	}

	public void dismissProgressDialog() {
		dismissProgressDialog(progressDialog);
	}

	public void dismissProgressDialog(ProgressDialog dialog) {
		if (dialog != null) {
			dialog.dismiss();
		}
	}
	
	// sample code
	// private Typeface customFont;
	// public Typeface getCustomFont() {
	// if (customFont == null) {
	// customFont = Typeface.createFromAsset(getAssets(), "Oranienbaum.ttf");
	// }
	// return customFont;
	// }
}
