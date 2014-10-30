package hk.ed.kgs.inspectionfa;

import hk.ed.androidframework.EverydayFragment;
import hk.ed.kgs.inspectionfa.data.InspectionReport;
import hk.ed.kgs.inspectionfa.data.MockupDataPool;
import hk.ed.kgs.inspectionfa.data.ReportForm;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class FragmentReportStatus extends EverydayFragment implements OnClickListener, OnItemClickListener {
	
	public static FragmentReportStatus setFragment(FragmentActivity master, int resId) {
		FragmentReportStatus fragment = new FragmentReportStatus();
		FragmentTransaction ft = master.getSupportFragmentManager().beginTransaction();
		ft.replace(resId, fragment);
		ft.commit();
		return fragment;
	}
	
	private ViewHolder.ff_report_status vh;
	private ArrayAdapter<InspectionReport> adapter;
	private DialogInspectionWithShipments currentDialog;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ff_report_status, container, false);
		vh = new ViewHolder.ff_report_status(view);
		vh.btnUploadReports.setOnClickListener(this);
		adapter = new InspectionReportAdapter(getActivity());
		vh.lvReports.setAdapter(adapter);
		adapter.addAll(MockupDataPool.savedReports);
		vh.lvReports.setOnItemClickListener(this);
		return view;
	}
	@Override
	public void onClick(View v) {
		if (v == vh.btnUploadReports) {
			final List<InspectionReport> reportsToDelete = new ArrayList<InspectionReport>();
			for (InspectionReport report : MockupDataPool.savedReports) {
				if (report.status.equals(InspectionReport.STATUS_DONE))
					reportsToDelete.add(report);
			}
			if (reportsToDelete.size() == 0) {
				toast("There is no report with the status \"Done\"");
				return;
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_upload_reports, null);
			final ViewHolder.dialog_upload_reports vhDialog = new ViewHolder.dialog_upload_reports(view);
			builder.setCancelable(false).setView(view);
			final AlertDialog dialog = builder.create();
			vhDialog.btnClose.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
				
			});
			dialog.show();
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					try {
						for (int i=0; i<reportsToDelete.size(); i++) {
							Thread.sleep(200);
							final int index = i;
							getActivity().runOnUiThread(new Runnable() {

								@Override
								public void run() {
									vhDialog.tvUploadingReports.setText("Uploading Reports (" + index + " of " + reportsToDelete.size() + ")");
								}
								
							});
						}
						getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {
								vhDialog.tvUploadingReports.setText("Uploading Reports (" + reportsToDelete.size() + " of " + reportsToDelete.size() + ")");
								vhDialog.tvUploadCompleted.setVisibility(View.VISIBLE);
								vhDialog.btnClose.setVisibility(View.VISIBLE);
								for (InspectionReport report : reportsToDelete) {
									MockupDataPool.savedReports.remove(report);
									adapter.remove(report);
								}
							}
							
						});
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return null;
				}
				
			}.execute();
		}
	}
	
	public void saveEditedReportForm(ReportForm form) {
		currentDialog.saveEditedReportForm(form);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		InspectionReport item = adapter.getItem(position);
		currentDialog = new DialogInspectionWithShipments(getEverydayActivity(), item, new DialogInspectionWithShipments.ReportActionListener() {
			
			@Override
			public void onDialogDismiss(InspectionReport report) {
				adapter.notifyDataSetChanged();
				currentDialog = null;
			}

			@Override
			public void onReportDelete(InspectionReport report) {
				MockupDataPool.savedReports.remove(report);
				adapter.remove(report);
				currentDialog = null;
				toast("Deletion was successful");
			}
		});
		currentDialog.show();
	}

}
