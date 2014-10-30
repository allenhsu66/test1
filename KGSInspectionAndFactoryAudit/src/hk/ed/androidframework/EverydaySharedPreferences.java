package hk.ed.androidframework;

import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

public class EverydaySharedPreferences {

	private static final String EXAMPLE = "Example";
	protected static final String EVERYDAY = "Everyday";
	private SharedPreferences sharedPreferences;

	public EverydaySharedPreferences(Context context) {
		sharedPreferences = context.getSharedPreferences(EVERYDAY, 0);
	}

	public String getExample(String defValue) {
		return sharedPreferences.getString(EXAMPLE, defValue);
	}

	public void setExample(String value) {
		savePreferences(EXAMPLE, value);
	}

	private void savePreferences(String key, boolean value) {
		sharedPreferences.edit().putBoolean(key, value).commit();
	}

	private void savePreferences(String key, int value) {
		sharedPreferences.edit().putInt(key, value).commit();
	}

	private void savePreferences(String key, String value) {
		sharedPreferences.edit().putString(key, value).commit();
	}

	private void savePreferences(String key, float value) {
		sharedPreferences.edit().putFloat(key, value).commit();
	}

	private void savePreferences(String key, long value) {
		sharedPreferences.edit().putLong(key, value).commit();
	}

	private void savePreferences(String key, Set<String> value) {
		sharedPreferences.edit().putStringSet(key, value).commit();
	}

}
