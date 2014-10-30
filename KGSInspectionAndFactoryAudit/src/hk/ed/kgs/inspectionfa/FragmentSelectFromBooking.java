package hk.ed.kgs.inspectionfa;

import hk.ed.androidframework.EverydayFragment;
import hk.ed.kgs.inspectionfa.data.InspectionReport;
import hk.ed.kgs.inspectionfa.data.MockupDataPool;
import hk.ed.kgs.inspectionfa.data.ReportForm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;

public class FragmentSelectFromBooking extends EverydayFragment implements
		OnClickListener, OnItemClickListener, OnItemSelectedListener,
		TextWatcher {

	public static FragmentSelectFromBooking setFragment(
			FragmentActivity master, int resId) {
		FragmentSelectFromBooking fragment = new FragmentSelectFromBooking();
		FragmentTransaction ft = master.getSupportFragmentManager()
				.beginTransaction();
		ft.replace(resId, fragment);
		ft.commit();
		return fragment;
	}

	private ViewHolder.ff_select_from_booking vh;
	private MySpinnerAdapter typeFilterAdapter;
	private MySpinnerAdapter brandFilterAdapter;
	private MySpinnerAdapter supplierFilterAdapter;
	private InspectionReportAdapter adapter;
	private Calendar selectedFromFilter;
	private Calendar selectedToFilter;
	private DialogInspectionWithShipments currentDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ff_select_from_booking,
				container, false);
		vh = new ViewHolder.ff_select_from_booking(view);
		vh.btnMyBooking.setOnClickListener(this);
		vh.btnTeamBooking.setOnClickListener(this);
		vh.btnFromFilter.setOnClickListener(this);
		vh.btnToFilter.setOnClickListener(this);
		typeFilterAdapter = new MySpinnerAdapter(getActivity());
		typeFilterAdapter.addAll(MySpinnerAdapter.getTypeFilterList());
		vh.spType.setAdapter(typeFilterAdapter);
		vh.spType.setOnItemSelectedListener(this);
		brandFilterAdapter = new MySpinnerAdapter(getActivity());
		brandFilterAdapter.addAll(MySpinnerAdapter.getBrandFilterList());
		vh.spBrand.setAdapter(brandFilterAdapter);
		vh.spBrand.setOnItemSelectedListener(this);
		supplierFilterAdapter = new MySpinnerAdapter(getActivity());
		supplierFilterAdapter.addAll(MySpinnerAdapter.getSupplierFilterList());
		vh.spSupplier.setAdapter(supplierFilterAdapter);
		vh.spSupplier.setOnItemSelectedListener(this);
		vh.etPurchaseOrderWildcard.addTextChangedListener(this);
		vh.etStyleNumberWildCard.addTextChangedListener(this);
		adapter = new InspectionReportAdapter(getActivity());
		adapter.addAll(MockupDataPool.bookingReports);
		vh.lvReports.setAdapter(adapter);
		vh.lvReports.setOnItemClickListener(this);
		vh.btnMyBooking.performClick();
		return view;
	}

	private SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MMM-yy",
			Locale.ENGLISH);

	protected void changeSelectedFromFilter(Calendar selectedFrom) {
		selectedFromFilter = selectedFrom;
		if (selectedFrom == null) {
			vh.btnFromFilter.setText("From");
		} else {
			vh.btnFromFilter.setText(sdfDate.format(selectedFrom.getTime()));
		}
		reloadFilteredList();
	}

	protected void changeSelectedToFilter(Calendar selectedTo) {
		selectedToFilter = selectedTo;
		if (selectedTo == null) {
			vh.btnToFilter.setText("To");
		} else {
			vh.btnToFilter.setText(sdfDate.format(selectedTo.getTime()));
		}
		reloadFilteredList();
	}

	public void saveEditedReportForm(ReportForm form) {
		currentDialog.saveEditedReportForm(form);
	}

	@Override
	public void onClick(View v) {
		if (v == vh.btnMyBooking) {
			if (!vh.btnMyBooking.isChecked()) {
				vh.btnMyBooking.setChecked(true);
				vh.btnTeamBooking.setChecked(false);
				reloadFilteredList();
			}
		} else if (v == vh.btnTeamBooking) {
			if (!vh.btnTeamBooking.isChecked()) {
				vh.btnTeamBooking.setChecked(true);
				vh.btnMyBooking.setChecked(false);
				reloadFilteredList();
			}
		} else if (v == vh.btnFromFilter) {
			Calendar cStarting = selectedFromFilter == null ? Calendar
					.getInstance() : selectedFromFilter;
			DatePickerDialog dialog = new DatePickerDialog(getActivity(),
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							Calendar selectedFromFilter = Calendar
									.getInstance();
							selectedFromFilter.set(Calendar.YEAR, year);
							selectedFromFilter.set(Calendar.MONTH, monthOfYear);
							selectedFromFilter.set(Calendar.DAY_OF_MONTH,
									dayOfMonth);
							changeSelectedFromFilter(selectedFromFilter);
						}
					}, cStarting.get(Calendar.YEAR), cStarting
							.get(Calendar.MONTH), cStarting
							.get(Calendar.DAY_OF_MONTH));
			dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					changeSelectedFromFilter(null);
				}
			});
			dialog.show();
		} else if (v == vh.btnToFilter) {
			Calendar cStarting = selectedToFilter == null ? Calendar
					.getInstance() : selectedToFilter;
			DatePickerDialog dialog = new DatePickerDialog(getActivity(),
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							Calendar selectedToFilter = Calendar.getInstance();
							selectedToFilter.set(Calendar.YEAR, year);
							selectedToFilter.set(Calendar.MONTH, monthOfYear);
							selectedToFilter.set(Calendar.DAY_OF_MONTH,
									dayOfMonth);
							changeSelectedToFilter(selectedToFilter);
						}
					}, cStarting.get(Calendar.YEAR),
					cStarting.get(Calendar.MONTH),
					cStarting.get(Calendar.DAY_OF_MONTH));
			dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					changeSelectedToFilter(null);
				}
			});
			dialog.show();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		currentDialog = new DialogInspectionWithShipments(
				getEverydayActivity(), adapter.getItem(arg2),
				new DialogInspectionWithShipments.ReportActionListener() {

					@Override
					public void onDialogDismiss(InspectionReport report) {
						adapter.notifyDataSetChanged();
						currentDialog = null;
					}

					@Override
					public void onReportDelete(InspectionReport report) {
						MockupDataPool.bookingReports.remove(report);
						adapter.remove(report);
						currentDialog = null;
						toast("Deletion was successful");
					}
				});
		currentDialog.show();
	}

	private void reloadFilteredList() {
		List<InspectionReport> allReports = new ArrayList<InspectionReport>();
		boolean isMyBooking = vh.btnMyBooking.isChecked();
		if (isMyBooking) {
			allReports.addAll(InspectionReport.getMyBookingDemoList());
		} else {
			allReports.addAll(InspectionReport.getTeamBookingDemoList());
		}
		if (selectedFromFilter != null) {
			// TODO
		}
		if (selectedToFilter != null) {
			// TODO
		}
		if (vh.spType.getSelectedItem() != null
				&& vh.spType.getSelectedItemPosition() != 0) {
			String type = typeFilterAdapter.getItem(vh.spType
					.getSelectedItemPosition());
			for (int i = allReports.size() - 1; i >= 0; i--) {
				InspectionReport report = allReports.get(i);
				if (!report.typeShort.equals(type)) {
					allReports.remove(report);
				}
			}
		}
		if (vh.spBrand.getSelectedItem() != null
				&& vh.spBrand.getSelectedItemPosition() != 0) {
			String brand = brandFilterAdapter.getItem(vh.spBrand
					.getSelectedItemPosition());
			for (int i = allReports.size() - 1; i >= 0; i--) {
				InspectionReport report = allReports.get(i);
				if (!report.brand.equals(brand)) {
					allReports.remove(report);
				}
			}
		}
		if (vh.spSupplier.getSelectedItem() != null
				&& vh.spSupplier.getSelectedItemPosition() != 0) {
			String supplier = supplierFilterAdapter.getItem(vh.spSupplier
					.getSelectedItemPosition());
			for (int i = allReports.size() - 1; i >= 0; i--) {
				InspectionReport report = allReports.get(i);
				if (!report.supplier.equals(supplier)) {
					allReports.remove(report);
				}
			}
		}
		String po = vh.etPurchaseOrderWildcard.getText().toString();
		if (po != null && !po.isEmpty()) {
			for (int i = allReports.size() - 1; i >= 0; i--) {
				InspectionReport report = allReports.get(i);
				if (!report.po.toLowerCase().contains(po.toLowerCase())) {
					allReports.remove(report);
				}
			}
		}
		String style = vh.etStyleNumberWildCard.getText().toString();
		if (style != null && !style.isEmpty()) {
			for (int i = allReports.size() - 1; i >= 0; i--) {
				InspectionReport report = allReports.get(i);
				if (!report.style.toLowerCase().contains(style.toLowerCase())) {
					allReports.remove(report);
				}
			}
		}
		adapter.clear();
		adapter.addAll(allReports);
	}
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		reloadFilteredList();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	@Override
	public void afterTextChanged(Editable arg0) {
		reloadFilteredList();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}
}
