package hk.ed.kgs.inspectionfa;

import hk.ed.androidframework.EverydayActivity;
import hk.ed.androidframework.EverydayFragmentActivity;
import hk.ed.kgs.inspectionfa.data.InspectionReport;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class InspectionReportAdapter extends ArrayAdapter<InspectionReport> {
	public InspectionReportAdapter(Context context) {
		super(context, R.layout.i_report_with_status);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (getContext() instanceof EverydayActivity) {
			convertView = ((EverydayActivity)getContext()).getLayoutInflater().inflate(R.layout.i_report_with_status, null);
		} else if (getContext() instanceof EverydayFragmentActivity) {
			convertView = ((EverydayFragmentActivity)getContext()).getLayoutInflater().inflate(R.layout.i_report_with_status, null);
		}
		InspectionReport item = getItem(position);
		ViewHolder.i_report_with_status vhItem = new ViewHolder.i_report_with_status(convertView);
		vhItem.tvType.setText(item.typeShort);
		vhItem.tvFactory.setText(item.factory);
		vhItem.tvBrand.setText(item.brand);
		vhItem.tvSupplier.setText(item.supplier);
		vhItem.tvPO.setText(item.po);
		vhItem.tvStyle.setText(item.style);
		vhItem.tvInsp.setText(item.inspectionDate);
		vhItem.tvBook.setText(item.bookingDate);
		vhItem.tvStatus.setText(item.status);
		if (item.status.equals(InspectionReport.STATUS_DONE)) {
			vhItem.tvStatus.setBackgroundResource(R.drawable.shape_bg_status_done);
		} else if (item.status.equals(InspectionReport.STATUS_DRAFT)) {
			vhItem.tvStatus.setBackgroundResource(R.drawable.shape_bg_status_draft);
		} else if (item.status.equals(InspectionReport.STATUS_NEW)) {
			vhItem.tvStatus.setBackgroundResource(R.drawable.shape_bg_status_new);
		} else {
			vhItem.tvStatus.setBackgroundResource(0);
		}
		return convertView;
	}
}
