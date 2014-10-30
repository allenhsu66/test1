package hk.ed.kgs.inspectionfa;

import hk.ed.androidframework.EverydayFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class FragmentInspectionReports extends EverydayFragment implements OnClickListener {
	
	public static FragmentInspectionReports setFragment(
			FragmentActivity master, int resId) {
		FragmentInspectionReports fragment = new FragmentInspectionReports();
		FragmentTransaction ft = master.getSupportFragmentManager()
				.beginTransaction();
		ft.replace(resId, fragment);
		ft.commit();
		return fragment;
	}

	private ViewHolder.f_inspection_reports vh;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.f_inspection_reports, container, false);
		vh = new ViewHolder.f_inspection_reports(view);
		vh.btnReportStatus.setOnClickListener(this);
		vh.btnSelectFromBooking.setOnClickListener(this);
		vh.btnNewReport.setOnClickListener(this);
		vh.btnSearchOldReports.setOnClickListener(this);
		vh.btnReportStatus.performClick();
		return view;
	}
	@Override
	public void onClick(View v) {
		if (v == vh.btnReportStatus) {
			if (!vh.btnReportStatus.isChecked()) {
				onReportStatusClicked();
			}
		} else if (v == vh.btnSelectFromBooking) {
			if (!vh.btnSelectFromBooking.isChecked()) {
				onSelectFromBookingClicked();
			}
		} else if (v == vh.btnNewReport) {
			if (!vh.btnNewReport.isChecked()) {
				onNewReportClicked();
			}
		} else if (v == vh.btnSearchOldReports) {
			if (!vh.btnSearchOldReports.isChecked()) {
				onSearchOldReportsClicked();
			}
		}
	}
	private EverydayFragment currentFragment = null;
	public EverydayFragment getCurrentFragment() {
		return currentFragment;
	}
	private void onSearchOldReportsClicked() {
		vh.btnReportStatus.setChecked(false);
		vh.btnSelectFromBooking.setChecked(false);
		vh.btnNewReport.setChecked(false);
		vh.btnSearchOldReports.setChecked(true);
		currentFragment = FragmentSearchOldReports.setFragment(getEverydayActivity(), R.id.frameFragmentContainer);
	}
	private void onNewReportClicked() {
		vh.btnReportStatus.setChecked(false);
		vh.btnSelectFromBooking.setChecked(false);
		vh.btnNewReport.setChecked(true);
		vh.btnSearchOldReports.setChecked(false);
		currentFragment = FragmentNewReport.setFragment(getEverydayActivity(), R.id.frameFragmentContainer);
	}
	private void onSelectFromBookingClicked() {
		vh.btnReportStatus.setChecked(false);
		vh.btnSelectFromBooking.setChecked(true);
		vh.btnNewReport.setChecked(false);
		vh.btnSearchOldReports.setChecked(false);
		currentFragment = FragmentSelectFromBooking.setFragment(getEverydayActivity(), R.id.frameFragmentContainer);
	}
	private void onReportStatusClicked() {
		vh.btnReportStatus.setChecked(true);
		vh.btnSelectFromBooking.setChecked(false);
		vh.btnNewReport.setChecked(false);
		vh.btnSearchOldReports.setChecked(false);
		currentFragment = FragmentReportStatus.setFragment(getEverydayActivity(), R.id.frameFragmentContainer);
	}
	
}
