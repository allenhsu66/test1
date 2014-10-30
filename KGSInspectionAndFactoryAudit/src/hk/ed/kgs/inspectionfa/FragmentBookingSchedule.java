package hk.ed.kgs.inspectionfa;

import hk.ed.androidframework.EverydayFragment;
import hk.ed.kgs.inspectionfa.data.InspectionReport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnDrawListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;

public class FragmentBookingSchedule extends EverydayFragment implements OnClickListener, OnItemClickListener, OnItemSelectedListener, TextWatcher {
	
	public static FragmentBookingSchedule setFragment(FragmentActivity master, int resId) {
		FragmentBookingSchedule fragment = new FragmentBookingSchedule();
		FragmentTransaction ft = master.getSupportFragmentManager().beginTransaction();
		ft.replace(resId, fragment);
		ft.commit();
		return fragment;
	}
	
	private ViewHolder.f_booking_schedule vh;
	private MySpinnerAdapter typeFilterAdapter;
	private MySpinnerAdapter brandFilterAdapter;
	private MySpinnerAdapter supplierFilterAdapter;
	private ArrayAdapter<InspectionReport> taskAdapter;
	private Calendar currentSelectedDate;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.f_booking_schedule, container, false);
		vh = new ViewHolder.f_booking_schedule(view);
		vh.btnPreviousMonth.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				currentMonth.add(Calendar.MONTH, -1);
				navigateToMonth(currentMonth);
			}
			
		});
		vh.btnNextMonth.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				currentMonth.add(Calendar.MONTH, 1);
				navigateToMonth(currentMonth);
			}
			
		});
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
		taskAdapter = new ArrayAdapter<InspectionReport>(getActivity(), R.layout.i_scheduled_task) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.i_scheduled_task, null);
				ViewHolder.i_scheduled_task vhItem = new ViewHolder.i_scheduled_task(convertView);
				InspectionReport item = getItem(position);
				vhItem.tvType.setText(item.typeShort);
				vhItem.tvFactory.setText(item.factory);
				vhItem.tvBrand.setText(item.brand);
				vhItem.tvSupplier.setText(item.supplier);
				vhItem.tvPO.setText(item.po);
				vhItem.tvStyle.setText(item.style);
				vhItem.tvQC.setText(item.qc);
				vhItem.tvInsp.setText(item.inspectionDate);
				vhItem.tvBook.setText(item.bookingDate);
				return convertView;
			}
		};
		vh.lvScheduledTask.setAdapter(taskAdapter);
		vh.lvScheduledTask.setOnItemClickListener(this);
		setOnCalendarDateCheckedListener(new OnCalendarDateCheckedListener() {

			@Override
			public void onDateChecked(View v, Calendar date) {
				currentSelectedDate = date;
				reloadFilteredList();
			}

			@Override
			public void onMonthChangedListener(Calendar whichMonth) {
				taskAdapter.clear();
				currentSelectedDate = null;
				changeSelectedFromFilter(null);
				changeSelectedToFilter(null);
			}

			@Override
			public void onDateUnchecked(View v, Calendar date) {
				currentSelectedDate = null;
				reloadFilteredList();
			}
			
		});
		navigateToMonth(Calendar.getInstance()); 
		vh.btnMyBooking.setOnClickListener(this);
		vh.btnTeamBooking.setOnClickListener(this);
		vh.btnMyBooking.performClick();
		vh.btnFromFilter.setOnClickListener(this);
		vh.btnToFilter.setOnClickListener(this);
		return view;
	}

	protected List<InspectionReport> getDateTask(Calendar date) {
		return InspectionReport.getDemoList();
	}

	private Calendar currentMonth;
	private OnCalendarDateCheckedListener dateCheckedListener;
	public void setOnCalendarDateCheckedListener(OnCalendarDateCheckedListener listener) {
		dateCheckedListener = listener;
	}
	
	private class RenderData {
		public static final int rowNumber = 6;
		public static final int colNumber = 7;
		public boolean[] rowVisible = new boolean[rowNumber];
		public boolean[][] dateVisible = new boolean[rowNumber][colNumber];
		public String[][][] sz = new String[rowNumber][colNumber][2];
		public int[][] dateNumber = new int[rowNumber][colNumber];
		public Calendar[][] c = new Calendar[rowNumber][colNumber];
	}
	
	private SimpleDateFormat sdfMonth = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
	public void navigateToMonth(final Calendar whichMonth) {
		unselectDate();
		getEverydayActivity().showProgressDialog();
		new AsyncTask<Void, Void, RenderData>() {

			@Override
			protected RenderData doInBackground(Void... params) {
				currentMonth = Calendar.getInstance();
				currentMonth.set(Calendar.DAY_OF_MONTH, whichMonth.get(Calendar.DAY_OF_MONTH));
				currentMonth.set(Calendar.MONTH, whichMonth.get(Calendar.MONTH));
				currentMonth.set(Calendar.YEAR, whichMonth.get(Calendar.YEAR));
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						vh.tvCurrentMonth.setText(sdfMonth.format(whichMonth.getTime()));
					}
					
				});
				Calendar iterator = Calendar.getInstance();
				iterator.set(Calendar.DAY_OF_MONTH, 1);
				iterator.set(Calendar.MONTH, whichMonth.get(Calendar.MONTH));
				iterator.set(Calendar.YEAR, whichMonth.get(Calendar.YEAR));
				int weekday = iterator.get(Calendar.DAY_OF_WEEK);
				final RenderData data = new RenderData();
				int viewIndex = 0;
				data.rowVisible[0] = true;
				for (int i=1; i<weekday; i++) {
					data.dateVisible[0][viewIndex] = false;
					viewIndex++;
				}
				for (int i=weekday; i<=7; i++) {
					int dayOfMonth = iterator.get(Calendar.DAY_OF_MONTH);
					Calendar c = Calendar.getInstance();
					c.set(Calendar.DAY_OF_MONTH, iterator.get(Calendar.DAY_OF_MONTH));
					c.set(Calendar.MONTH, iterator.get(Calendar.MONTH));
					c.set(Calendar.YEAR, iterator.get(Calendar.YEAR));
					data.c[0][viewIndex] = c;
					data.dateVisible[0][viewIndex] = true;
					data.dateNumber[0][viewIndex] = dayOfMonth;
					data.sz[0][viewIndex][0] = "1FA";
					data.sz[0][viewIndex][1] = "1F 2I 1S";
					viewIndex++;
					iterator.add(Calendar.DAY_OF_MONTH, 1);
				}
				int week = 1;
				while (iterator.get(Calendar.DAY_OF_MONTH) != 1) {
					weekday = iterator.get(Calendar.DAY_OF_WEEK);
					if (weekday == Calendar.SUNDAY) {
						week++;
						data.rowVisible[week - 1] = true;
						viewIndex = 0;
					}
					Calendar c = Calendar.getInstance();
					c.set(Calendar.DAY_OF_MONTH, iterator.get(Calendar.DAY_OF_MONTH));
					c.set(Calendar.MONTH, iterator.get(Calendar.MONTH));
					c.set(Calendar.YEAR, iterator.get(Calendar.YEAR));
					data.c[week-1][viewIndex] = c;
					data.dateVisible[week-1][viewIndex] = true;
					int dayOfMonth = iterator.get(Calendar.DAY_OF_MONTH);
					data.dateNumber[week-1][viewIndex] = dayOfMonth;
					data.sz[week-1][viewIndex][0] = "1FA";
					data.sz[week-1][viewIndex][1] = "1F 2I 1S";
					viewIndex++;
					iterator.add(Calendar.DAY_OF_MONTH, 1);
				}
				weekday = iterator.get(Calendar.DAY_OF_WEEK);
				if (weekday != Calendar.SUNDAY) {
					for (int i=weekday; i<=7; i++) {
						data.dateVisible[week-1][viewIndex] = false;
						viewIndex++;
					}
				}
				for (int i=week; i<6;i++) {
					data.rowVisible[i] = false;
				}
				return data;
			}
			
			@Override
			public void onPostExecute(RenderData data) {
				for (int i=0; i<RenderData.rowNumber; i++) {
					if (!data.rowVisible[i]) {
						vh.llWeeks.getChildAt(i).setVisibility(View.GONE);
					} else {
						vh.llWeeks.getChildAt(i).setVisibility(View.VISIBLE);
						for (int j=0; j<RenderData.colNumber; j++) {
							View target = ((LinearLayout) vh.llWeeks.getChildAt(i)).getChildAt(j);
							target.setOnClickListener(FragmentBookingSchedule.this);
							ViewHolder.i_monthly_calendar_date vhTarget = new ViewHolder.i_monthly_calendar_date(target);
							if (!data.dateVisible[i][j]) {
								vhTarget.tvDateNumber.setVisibility(View.GONE);
								vhTarget.tvFaNumber.setVisibility(View.GONE);
								vhTarget.tvTaskSummary.setVisibility(View.GONE);
								target.setTag(null);
							} else {
								vhTarget.tvDateNumber.setVisibility(View.VISIBLE);
								vhTarget.tvFaNumber.setVisibility(View.VISIBLE);
								vhTarget.tvTaskSummary.setVisibility(View.VISIBLE);
								target.setTag(data.c[i][j]);
								vhTarget.tvDateNumber.setText(data.dateNumber[i][j] + "");
								vhTarget.tvFaNumber.setText(data.sz[i][j][0]);
								vhTarget.tvTaskSummary.setText(data.sz[i][j][1]);
							}
						}
					}
				}
				vh.llWeeks.getChildAt(vh.llWeeks.getChildCount() - 1).getViewTreeObserver().addOnDrawListener(new OnDrawListener() {

					@Override
					public void onDraw() {
						getEverydayActivity().dismissProgressDialog();
						vh.llWeeks.getChildAt(vh.llWeeks.getChildCount() - 1).getViewTreeObserver().removeOnDrawListener(this);
					}
					
				});
				if (dateCheckedListener != null) {
					dateCheckedListener.onMonthChangedListener(currentMonth);
				}
			}
			
		}.execute();
		
	}
	
	private Calendar selectedFromFilter;
	private Calendar selectedToFilter;
	private View previouslySelectedDate;
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
				vh.btnMyBooking.setChecked(false);
				vh.btnTeamBooking.setChecked(true);
				reloadFilteredList();
			}
		} else if (v == vh.btnFromFilter) {
			Calendar cStarting = selectedFromFilter == null ? currentMonth : selectedFromFilter;
			DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					Calendar selectedFromFilter = Calendar.getInstance();
					selectedFromFilter.set(Calendar.YEAR, year);
					selectedFromFilter.set(Calendar.MONTH, monthOfYear);
					selectedFromFilter.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					changeSelectedFromFilter(selectedFromFilter);
				}
			}, cStarting.get(Calendar.YEAR), cStarting.get(Calendar.MONTH), cStarting.get(Calendar.DAY_OF_MONTH));
			dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
				
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
			Calendar cStarting = selectedToFilter == null ? currentMonth : selectedToFilter;
			DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					Calendar selectedToFilter = Calendar.getInstance();
					selectedToFilter.set(Calendar.YEAR, year);
					selectedToFilter.set(Calendar.MONTH, monthOfYear);
					selectedToFilter.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					changeSelectedToFilter(selectedToFilter);
				}
			}, cStarting.get(Calendar.YEAR), cStarting.get(Calendar.MONTH), cStarting.get(Calendar.DAY_OF_MONTH));
			dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
				
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
		} else {
			toggleDate(v);
		}
	}
	
	private SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
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

	private void toggleDate(View v) {
		Calendar c = (Calendar) v.getTag();
		if (c != null) {
			ViewHolder.i_monthly_calendar_date vhDate;
			if (previouslySelectedDate != null) {
				vhDate = new ViewHolder.i_monthly_calendar_date(previouslySelectedDate);
				vhDate.tvDateNumber.setChecked(false);
//				if (dateCheckedListener != null) {
//					dateCheckedListener.onDateUnchecked(v, (Calendar)previouslySelectedDate.getTag());
//				}
//				if (previouslySelectedDate == v) {
//					return;
//				}
			}
			vhDate = new ViewHolder.i_monthly_calendar_date(v);
			vhDate.tvDateNumber.setChecked(true);
			previouslySelectedDate = v;
			if (dateCheckedListener != null) {
				dateCheckedListener.onDateChecked(v, c);
			}
		}
	}
	private void unselectDate() {
		if (previouslySelectedDate != null) {
			ViewHolder.i_monthly_calendar_date vhDate = new ViewHolder.i_monthly_calendar_date(previouslySelectedDate);
			vhDate.tvDateNumber.setChecked(false);
			previouslySelectedDate = null;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		InspectionReport item = taskAdapter.getItem(position);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_inspection_task_summary, null);
		ViewHolder.dialog_inspection_task_summary vhDialog = new ViewHolder.dialog_inspection_task_summary(v);
		vhDialog.tvQcName.setText(item.qc);
		vhDialog.tvBookingId.setText("Booking ID: " + item.bookingId);
		vhDialog.tvInspectionDate.setText("Inspection Date: " + item.inspectionDate);
		vhDialog.tvBookingDate.setText("Booking Date: " + item.bookingDate);
		vhDialog.tvFactory.setText(item.factory);
		vhDialog.tvBrand.setText(item.brand);
		vhDialog.tvSupplier.setText(item.supplier);
		vhDialog.tvPoNumber.setText("PO Number: " + item.po);
		vhDialog.tvShiplineNumber.setText("Shipline Number: " + item.shiplineNumber);
		vhDialog.tvShiplineQuantity.setText("Shipline Quantity: " + item.shiplineQuantity);
		vhDialog.tvShipMode.setText("Ship Mode: " + item.shipMode);
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

	private void reloadFilteredList() {
		List<InspectionReport> allReports = new ArrayList<InspectionReport>();
		boolean isMyBooking = vh.btnMyBooking.isChecked();
		if (isMyBooking) {
			allReports.addAll(InspectionReport.getMyBookingDemoList());
		} else {
			allReports.addAll(InspectionReport.getTeamBookingDemoList());
		}
		if (currentSelectedDate != null) {
			// TODO
		}
		if (selectedFromFilter != null) {
			// TODO
		}
		if (selectedToFilter != null) {
			// TODO
		}
		if (vh.spType.getSelectedItem() != null && vh.spType.getSelectedItemPosition() != 0) {
			String type = typeFilterAdapter.getItem(vh.spType.getSelectedItemPosition());
			for (int i=allReports.size() - 1; i>=0; i--) {
				InspectionReport report = allReports.get(i);
				if (!report.typeShort.equals(type)) {
					allReports.remove(report);
				}
			}
		}
		if (vh.spBrand.getSelectedItem() != null && vh.spBrand.getSelectedItemPosition() != 0) {
			String brand = brandFilterAdapter.getItem(vh.spBrand.getSelectedItemPosition());
			for (int i=allReports.size() - 1; i>=0; i--) {
				InspectionReport report = allReports.get(i);
				if (!report.brand.equals(brand)) {
					allReports.remove(report);
				}
			}
		}
		if (vh.spSupplier.getSelectedItem() != null && vh.spSupplier.getSelectedItemPosition() != 0) {
			String supplier = supplierFilterAdapter.getItem(vh.spSupplier.getSelectedItemPosition());
			for (int i=allReports.size() - 1; i>=0; i--) {
				InspectionReport report = allReports.get(i);
				if (!report.supplier.equals(supplier)) {
					allReports.remove(report);
				}
			}
		}
		String po = vh.etPurchaseOrderWildcard.getText().toString();
		if (po != null && !po.isEmpty()) {
			for (int i=allReports.size() - 1; i>=0; i--) {
				InspectionReport report = allReports.get(i);
				if (!report.po.toLowerCase().contains(po.toLowerCase())) {
					allReports.remove(report);
				}
			}
		}
		String style = vh.etStyleNumberWildCard.getText().toString();
		if (style != null && !style.isEmpty()) {
			for (int i=allReports.size() - 1; i>=0; i--) {
				InspectionReport report = allReports.get(i);
				if (!report.style.toLowerCase().contains(style.toLowerCase())) {
					allReports.remove(report);
				}
			}
		}
		taskAdapter.clear();
		taskAdapter.addAll(allReports);
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
