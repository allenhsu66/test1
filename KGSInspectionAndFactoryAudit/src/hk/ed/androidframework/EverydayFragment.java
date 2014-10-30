package hk.ed.androidframework;

import hk.ed.androidframework.api.EverydayApiHelper;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class EverydayFragment extends Fragment {
	
	public EverydayFragmentActivity getEverydayActivity() {
		return (EverydayFragmentActivity) getActivity();
	}
	
	public EverydayApiHelper getWebApiHelper() {
		if (getEverydayActivity() == null) {
			return null;
		}
		return getEverydayActivity().getWebApiHelper();
	}
	
	public EverydaySharedPreferences getSharedPreferences() {
		if (getEverydayActivity() == null) {
			return null;
		}
		return getEverydayActivity().getSharedPreferences();
	}
	
	public void toast(final int resId) {
		if (getEverydayActivity() != null) {
			getEverydayActivity().toast(resId);
		}
	}
	
	public void toast(final String text) {
		if (getEverydayActivity() != null) {
			getEverydayActivity().toast(text);
		}
	}
	
	public ProgressDialog showProgressDialog(String title, String message) {
		if (getEverydayActivity() == null) {
			return null;
		}
		return getEverydayActivity().showProgressDialog(title, message);
	}
	public ProgressDialog showProgressDialog() {
		if (getEverydayActivity() == null) {
			return null;
		}
		return getEverydayActivity().showProgressDialog();
	}
	public void dismissProgressDialog() {
		if (getEverydayActivity() != null) {
			getEverydayActivity().dismissProgressDialog();
		}
	}
	public void dismissProgressDialog(ProgressDialog dialog) {
		if (getEverydayActivity() != null) {
			getEverydayActivity().dismissProgressDialog(dialog);
		}
	}
}
