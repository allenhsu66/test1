package hk.ed.kgs.inspectionfa;

import hk.ed.androidframework.EverydayFragment;
import hk.ed.kgs.inspectionfa.data.InspectionReport;
import hk.ed.kgs.inspectionfa.data.MockupDataPool;
import hk.ed.kgs.inspectionfa.data.ReportForm;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class FragmentNewReport extends EverydayFragment implements OnClickListener {
	
	public static FragmentNewReport setFragment(FragmentActivity master, int resId) {
		FragmentNewReport fragment = new FragmentNewReport();
		FragmentTransaction ft = master.getSupportFragmentManager().beginTransaction();
		ft.replace(resId, fragment);
		ft.commit();
		return fragment;
	}
	
	private ViewHolder.ff_new_report vh;
	private DialogInspectionWithShipments currentDialog;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ff_new_report, container, false);
		vh = new ViewHolder.ff_new_report(view);
		vh.btnFactoryAuditApparel.setOnClickListener(this);
		vh.btnFactoryAuditFootwear.setOnClickListener(this);
		vh.btnFactoryAuditFurnitureAndHardgoods.setOnClickListener(this);
		vh.btnFinalInspectionApparel.setOnClickListener(this);
		vh.btnFinalInspectionFootwear.setOnClickListener(this);
		vh.btnFinalInspectionFurnitureAndHardgoods.setOnClickListener(this);
		vh.btnInlineInspectionApparel.setOnClickListener(this);
		vh.btnInlineInspectionFootwear.setOnClickListener(this);
		vh.btnInlineInspectionFurnitureAndHardgoods.setOnClickListener(this);
		vh.btnSampleCheckFootwear.setOnClickListener(this);
		vh.btnSampleCheckFurnitureAndHardgoods.setOnClickListener(this);
		return view;
	}
	
	public void saveEditedReportForm(ReportForm form) {
		currentDialog.saveEditedReportForm(form);
	}
	
	@Override
	public void onClick(View v) {
		InspectionReport report = InspectionReport.getDemoInstance1();
		report.status = InspectionReport.STATUS_NEW;
		for (ReportForm form : report.forms) {
			form.status = InspectionReport.STATUS_NEW;
		}
		currentDialog = new DialogInspectionWithShipments(getEverydayActivity(), report, new DialogInspectionWithShipments.ReportActionListener() {
			
			@Override
			public void onDialogDismiss(InspectionReport report) {
				MockupDataPool.savedReports.add(report);
				toast("Created new report");
				currentDialog = null;
			}

			@Override
			public void onReportDelete(InspectionReport report) {
				toast("Canceled creation of new report");
				currentDialog = null;
			}
		});
		currentDialog.show();
	}

}
