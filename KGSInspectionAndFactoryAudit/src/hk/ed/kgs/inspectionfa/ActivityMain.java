package hk.ed.kgs.inspectionfa;

import hk.ed.androidframework.EverydayFragment;
import hk.ed.androidframework.EverydayFragmentActivity;
import hk.ed.kgs.inspectionfa.data.ReportForm;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ActivityMain extends EverydayFragmentActivity implements
		OnClickListener {

	public static void launchActivity(Context from) {
		if (from != null) {
			Intent intent = new Intent(from, ActivityMain.class);
			from.startActivity(intent);
		}
	}

	private ViewHolder.a_main vh;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_main);
		vh = new ViewHolder.a_main(this);
		vh.btnBookingSchedule.setOnClickListener(this);
		vh.btnCheckUpdate.setOnClickListener(this);
		vh.btnInspectionReports.setOnClickListener(this);
		vh.btnLogout.setOnClickListener(this);
		vh.btnCheckUpdate.performClick();
	}

	@Override
	public void onClick(View v) {
		if (v == vh.btnBookingSchedule) {
			if (!vh.btnBookingSchedule.isChecked()) {
				onBookingScheduleChecked();
			}
		} else if (v == vh.btnCheckUpdate) {
			if (!vh.btnCheckUpdate.isChecked()) {
				onCheckUpdateChecked();
			}
		} else if (v == vh.btnInspectionReports) {
			if (!vh.btnInspectionReports.isChecked()) {
				onInspectionRecordChecked();
			}
		} else if (v == vh.btnLogout) {
			onBackPressed();
		}
	}

	private EverydayFragment currentFragment;

	private void onInspectionRecordChecked() {
		vh.btnBookingSchedule.setChecked(false);
		vh.btnCheckUpdate.setChecked(false);
		vh.btnInspectionReports.setChecked(true);
		currentFragment = FragmentInspectionReports.setFragment(this, R.id.frameDetail);
	}

	private void onCheckUpdateChecked() {
		vh.btnBookingSchedule.setChecked(false);
		vh.btnCheckUpdate.setChecked(true);
		vh.btnInspectionReports.setChecked(false);
		currentFragment = FragmentCheckUpdate.setFragment(this, R.id.frameDetail);
		checkUpdate();
	}

	protected void checkUpdate() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = getLayoutInflater().inflate(R.layout.dialog_check_update,
				null);
		final ViewHolder.dialog_check_update vhDialog = new ViewHolder.dialog_check_update(
				view);
		builder.setView(view).setCancelable(false);
		final AlertDialog dialog = builder.create();
		dialog.show();
		vhDialog.btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}

		});
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(500);
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							vhDialog.tvDownloadingUpdates.setVisibility(View.VISIBLE);
						}
						
					});
					for (int i=0; i<15; i++) {
						Thread.sleep(50);
						final int index = i;
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								vhDialog.tvDownloadingUpdates.setText("Downloading Updates (" + index + " of 15)");
							}
							
						});
					}
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							vhDialog.tvDownloadingUpdates.setText("Downloading Updates (15 of 15)");
							vhDialog.tvDonwloadCompleted.setVisibility(View.VISIBLE);
						}
						
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				vhDialog.btnClose.setVisibility(View.VISIBLE);
				dialog.setCancelable(true);
			}
		}.execute();
	}

	private void onBookingScheduleChecked() {
		vh.btnBookingSchedule.setChecked(true);
		vh.btnCheckUpdate.setChecked(false);
		vh.btnInspectionReports.setChecked(false);
		currentFragment = FragmentBookingSchedule.setFragment(this, R.id.frameDetail);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ActivityFormEditor.CODE_FORM_EDITOR) {
			if (resultCode == Activity.RESULT_OK) {
				if (currentFragment == null)
					return;
				if (currentFragment instanceof FragmentInspectionReports) {
					EverydayFragment subFragment = ((FragmentInspectionReports) currentFragment).getCurrentFragment();
					if (subFragment == null)
						return;
					ReportForm form = ActivityFormEditor.getEditedReportForm(data);
					if (subFragment instanceof FragmentReportStatus) {
						((FragmentReportStatus) subFragment).saveEditedReportForm(form);
					} else if (subFragment instanceof FragmentSelectFromBooking) {
						((FragmentSelectFromBooking) subFragment).saveEditedReportForm(form);
					} else if (subFragment instanceof FragmentNewReport) {
						((FragmentNewReport) subFragment).saveEditedReportForm(form);
					} else if (subFragment instanceof FragmentSearchOldReports) {
						// impossible navigation
					}
				}
			}
		}
	}

}
