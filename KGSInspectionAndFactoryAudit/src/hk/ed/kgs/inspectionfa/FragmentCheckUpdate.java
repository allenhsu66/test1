package hk.ed.kgs.inspectionfa;

import hk.ed.androidframework.EverydayFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentCheckUpdate extends EverydayFragment {
	
	public static FragmentCheckUpdate setFragment(FragmentActivity master, int resId) {
		FragmentCheckUpdate fragment = new FragmentCheckUpdate();
		FragmentTransaction ft = master.getSupportFragmentManager().beginTransaction();
		ft.replace(resId, fragment);
		ft.commit();
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.f_check_update, container, false);
		return view;
	}

}
