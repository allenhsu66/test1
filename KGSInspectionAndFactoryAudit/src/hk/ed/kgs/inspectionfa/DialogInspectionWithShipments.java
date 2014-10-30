package hk.ed.kgs.inspectionfa;

import hk.ed.kgs.inspectionfa.data.InspectionReport;
import hk.ed.kgs.inspectionfa.data.ReportForm;
import hk.ed.kgs.inspectionfa.data.Shipline;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class DialogInspectionWithShipments {
	
	public static interface ReportActionListener {
		/**
		 * Callback when dialog dismiss either by pressing back or pressing close
		 * @param report the edited inspection report
		 */
		public void onDialogDismiss(InspectionReport report);
		
		public void onReportDelete(InspectionReport report);
	}
	
	private AlertDialog dialog;
	private ArrayAdapter<ReportForm>  formAdapter;
	private ShiplineAdapter shiplineAdapter;
	private ReportForm editingForm = null;
	public DialogInspectionWithShipments(FragmentActivity context, InspectionReport report, ReportActionListener listener) {
		dialog = getInstance(context, report, listener);
	}
	
	public void saveEditedReportForm(ReportForm form) {
		if (editingForm != null) {
			editingForm.copy(form);
			formAdapter.notifyDataSetChanged();
			editingForm = null;
		}
	}
	
	public void show() {
		dialog.show();
	}
	
	private AlertDialog getInstance(final FragmentActivity context, final InspectionReport report, final ReportActionListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		View v = context.getLayoutInflater().inflate(R.layout.dialog_inspection_1, null);
		ViewHolder.dialog_inspection_1 vh = new ViewHolder.dialog_inspection_1(v);
		shiplineAdapter = new ShiplineAdapter(context, report);
		vh.lvShiplines.setAdapter(shiplineAdapter);
		shiplineAdapter.addAll(report.shiplines);
		formAdapter = new ReportFormAdapter(context);
		vh.lvForms.setAdapter(formAdapter);
		formAdapter.addAll(report.forms);
		vh.lvForms.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ReportForm item = formAdapter.getItem(arg2);
				editingForm = item;
				ActivityFormEditor.launchActivityForResult(context, item, ActivityFormEditor.CODE_FORM_EDITOR);
			}
			
		});
		builder.setView(v).setCancelable(true);
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				boolean allDone = true;
				for (ReportForm form : report.forms)
					if (!form.status.equals(InspectionReport.STATUS_DONE)) {
						allDone = false;
						break;
					}
				if (allDone) {
					report.status = InspectionReport.STATUS_DONE;
				} else {
					report.status = InspectionReport.STATUS_DRAFT;
				}
				if (listener != null) {
					listener.onDialogDismiss(report);
				}
			}
		});
		final AlertDialog dialog = builder.create();
		vh.btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
			
		});
		vh.btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.onReportDelete(report);
					dialog.dismiss();
				}
			}
			
		});
		vh.btnAddShipline.setOnClickListener(new OnClickListener() {
			
			private Shipline selectedShipline = null;

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				View view = context.getLayoutInflater().inflate(R.layout.dialog_add_shipline, null);
				builder.setView(view);
				final ViewHolder.dialog_add_shipline vh = new ViewHolder.dialog_add_shipline(view);
				final ArrayAdapter<Shipline> addShiplineAdapter = new ArrayAdapter<Shipline>(context, R.layout.i_shipline) {
					@Override
					public View getView(int position, View convertView, ViewGroup parent) {
						if (convertView == null) {
							convertView = context.getLayoutInflater().inflate(R.layout.i_shipline, null);
						}
						ViewHolder.i_shipline vhItem = new ViewHolder.i_shipline(convertView);
						Shipline item = getItem(position);
						vhItem.tvBookDate.setText(item.bookingDate);
						vhItem.tvMode.setText(item.mode);
						vhItem.tvPo.setText(item.po);
						vhItem.tvQuantity.setText(item.quantity + "");
						vhItem.tvShipDate.setText(item.shippingDate);
						vhItem.tvShipId.setText(item.shipId);
						vhItem.tvStyle.setText(item.style);
						return convertView;
					}
				};
				vh.lvShiplines.setAdapter(addShiplineAdapter);
				addShiplineAdapter.addAll(Shipline.getDemoList());
				vh.lvShiplines.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						selectedShipline = addShiplineAdapter.getItem(arg2);
						vh.tvBookDate.setText(selectedShipline.bookingDate);
						vh.tvPoNumber.setText(selectedShipline.po);
						vh.tvShipDate.setText(selectedShipline.shippingDate);
						vh.tvShipId.setText(selectedShipline.shipId);
						vh.tvShipMode.setText(selectedShipline.mode);
						vh.tvShipQuantity.setText(selectedShipline.quantity + "");
						vh.tvStyleNumber.setText(selectedShipline.style);
					}
					
				});
				vh.btnManualInput.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(context);
						View view = context.getLayoutInflater().inflate(R.layout.dialog_manual_input_shipline, null);
						builder.setView(view);
						final ViewHolder.dialog_manual_input_shipline vhManualShipline = new ViewHolder.dialog_manual_input_shipline(view);
						builder.setNegativeButton("Cancel", null);
						builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Shipline sl = new Shipline();
								sl.bookingDate = vhManualShipline.etBookingDate.getText().toString();
								sl.mode = vhManualShipline.etMode.getText().toString();
								sl.po = vhManualShipline.etPo.getText().toString();
								try {
									sl.quantity = Integer.parseInt(vhManualShipline.etQuantity.getText().toString());
								} catch (Exception e) {
									e.printStackTrace();
									sl.quantity = 0;
								}
								sl.shipId = vhManualShipline.etShipId.getText().toString();
								sl.shippingDate = vhManualShipline.etShippingDate.getText().toString();
								sl.style = vhManualShipline.etStyle.getText().toString();
								selectedShipline = sl;
								vh.tvBookDate.setText(selectedShipline.bookingDate);
								vh.tvPoNumber.setText(selectedShipline.po);
								vh.tvShipDate.setText(selectedShipline.shippingDate);
								vh.tvShipId.setText(selectedShipline.shipId);
								vh.tvShipMode.setText(selectedShipline.mode);
								vh.tvShipQuantity.setText(selectedShipline.quantity + "");
								vh.tvStyleNumber.setText(selectedShipline.style);
							}
						});
						builder.create().show();
					}
					
				});
				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) {
						if (selectedShipline != null) {
							shiplineAdapter.add(selectedShipline);
							report.shiplines.add(selectedShipline);
						}
						selectedShipline = null;
					}
				});
				final AlertDialog dialog = builder.create();
				vh.btnClose.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.cancel();
					}
					
				});
				dialog.show();
			}
			
		});
		return dialog;
	}
	
	private static class ShiplineAdapter extends ArrayAdapter<Shipline> {

		private FragmentActivity context;
		private InspectionReport report;
		private OnClickListener onDeleteClicked = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Shipline item = (Shipline) v.getTag();
				if (item != null) {
					remove(item);
					report.shiplines.remove(item);
				}
			}
			
		};
		private OnClickListener onViewClicked = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Shipline item = (Shipline) v.getTag();
				if (item != null) {
					showShiplineDetail(item);
				}
			}
			
		};
		public ShiplineAdapter(FragmentActivity context, InspectionReport report) {
			super(context, R.layout.i_shipline_summary);
			this.context = context;
			this.report = report;
		}
		
		protected void showShiplineDetail(Shipline item) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			View v = context.getLayoutInflater().inflate(R.layout.dialog_inspection_task_summary, null);
			ViewHolder.dialog_inspection_task_summary vhDialog = new ViewHolder.dialog_inspection_task_summary(v);
//			vhDialog.tvQcName.setText(item.);
//			vhDialog.tvBookingId.setText("Booking ID: " + item.);
//			vhDialog.tvInspectionDate.setText("Inspection Date: " + item.inspectionDate);
			vhDialog.tvBookingDate.setText("Booking Date: " + item.bookingDate);
//			vhDialog.tvFactory.setText(item.factory);
//			vhDialog.tvBrand.setText(item.brand);
//			vhDialog.tvSupplier.setText(item.supplier);
			vhDialog.tvPoNumber.setText("PO Number: " + item.po);
			vhDialog.tvShiplineNumber.setText("Shipline Number: " + item.shipId);
			vhDialog.tvShiplineQuantity.setText("Shipline Quantity: " + item.quantity);
			vhDialog.tvShipMode.setText("Ship Mode: " + item.mode);
			vhDialog.tvStyleNumber.setText("Style Number: " + item.style);
			builder.setView(v).setCancelable(true);
			final AlertDialog dialog = builder.create();
			vhDialog.btnClose.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
				
			});
			dialog.show();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = context.getLayoutInflater().inflate(R.layout.i_shipline_summary, null);
			}
			ViewHolder.i_shipline_summary vhItem = new ViewHolder.i_shipline_summary(convertView);
			Shipline item = getItem(position);
			vhItem.tvSummary.setText(getShiplineSummaryText(item));
			vhItem.btnDelete.setTag(item);
			vhItem.btnDelete.setOnClickListener(onDeleteClicked);
			vhItem.btnView.setTag(item);
			vhItem.btnView.setOnClickListener(onViewClicked);
			return convertView;
		}
		
	}
	
	private static class ReportFormAdapter extends ArrayAdapter<ReportForm> {

		private FragmentActivity context;
		public ReportFormAdapter(FragmentActivity context) {
			super(context, R.layout.i_shipline_summary);
			this.context = context;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = context.getLayoutInflater().inflate(R.layout.i_report_form_status, null);
			}
			ViewHolder.i_report_form_status vhItem = new ViewHolder.i_report_form_status(convertView);
			ReportForm item = getItem(position);
			vhItem.tvFormName.setText(item.subtype);
			if (item.subtype.equals(ReportForm.SUBTYPE_SHIPMENT_RELEASE)) {
				vhItem.ctvNew.setVisibility(View.GONE);
				vhItem.ctvDraft.setText("Not Ready");
				vhItem.ctvDone.setText("Ready");
				if (item.status.equals(InspectionReport.STATUS_NEW)) {
					vhItem.ctvNew.setChecked(false);
					vhItem.ctvDraft.setChecked(true);
					vhItem.ctvDone.setChecked(false);
				} else if (item.status.equals(InspectionReport.STATUS_DRAFT)) {
					vhItem.ctvNew.setChecked(false);
					vhItem.ctvDraft.setChecked(true);
					vhItem.ctvDone.setChecked(false);
				} else {
					vhItem.ctvNew.setChecked(false);
					vhItem.ctvDraft.setChecked(false);
					vhItem.ctvDone.setChecked(true);
				}
			} else {
				vhItem.ctvNew.setVisibility(View.VISIBLE);
				vhItem.ctvDraft.setText("Draft");
				vhItem.ctvDone.setText("Done");
				if (item.status.equals(InspectionReport.STATUS_NEW)) {
					vhItem.ctvNew.setChecked(true);
					vhItem.ctvDraft.setChecked(false);
					vhItem.ctvDone.setChecked(false);
				} else if (item.status.equals(InspectionReport.STATUS_DRAFT)) {
					vhItem.ctvNew.setChecked(false);
					vhItem.ctvDraft.setChecked(true);
					vhItem.ctvDone.setChecked(false);
				} else {
					vhItem.ctvNew.setChecked(false);
					vhItem.ctvDraft.setChecked(false);
					vhItem.ctvDone.setChecked(true);
				}
			}
			return convertView;
		}
		
	}

	private static String getShiplineSummaryText(Shipline item) {
		return item.po + " / " + item.style + " / " + item.shipId + " / " + item.quantity + " / " + item.mode + " / " + item.shippingDate;
	}

}
