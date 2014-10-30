package hk.ed.kgs.inspectionfa;

import android.app.Activity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewHolder {
	public static final class a_login {
		public final ImageView imageView1;
		public final TextView textView1;
		public final EditText etUsername;
		public final EditText etPassword;
		public final Button btnLogin;

		public a_login(View v) {
			imageView1 = (ImageView) v.findViewById(R.id.imageView1);
			textView1 = (TextView) v.findViewById(R.id.textView1);
			etUsername = (EditText) v.findViewById(R.id.etUsername);
			etPassword = (EditText) v.findViewById(R.id.etPassword);
			btnLogin = (Button) v.findViewById(R.id.btnLogin);
		}

		public a_login(Activity a) {
			imageView1 = (ImageView) a.findViewById(R.id.imageView1);
			textView1 = (TextView) a.findViewById(R.id.textView1);
			etUsername = (EditText) a.findViewById(R.id.etUsername);
			etPassword = (EditText) a.findViewById(R.id.etPassword);
			btnLogin = (Button) a.findViewById(R.id.btnLogin);
		}
	}

	public static final class a_main {
		public final Button btnLogout;
		public final CheckedTextView btnCheckUpdate;
		public final CheckedTextView btnBookingSchedule;
		public final CheckedTextView btnInspectionReports;
		public final FrameLayout frameDetail;

		public a_main(View v) {
			btnLogout = (Button) v.findViewById(R.id.btnLogout);
			btnCheckUpdate = (CheckedTextView) v
					.findViewById(R.id.btnCheckUpdate);
			btnBookingSchedule = (CheckedTextView) v
					.findViewById(R.id.btnBookingSchedule);
			btnInspectionReports = (CheckedTextView) v
					.findViewById(R.id.btnInspectionReports);
			frameDetail = (FrameLayout) v.findViewById(R.id.frameDetail);
		}

		public a_main(Activity a) {
			btnLogout = (Button) a.findViewById(R.id.btnLogout);
			btnCheckUpdate = (CheckedTextView) a
					.findViewById(R.id.btnCheckUpdate);
			btnBookingSchedule = (CheckedTextView) a
					.findViewById(R.id.btnBookingSchedule);
			btnInspectionReports = (CheckedTextView) a
					.findViewById(R.id.btnInspectionReports);
			frameDetail = (FrameLayout) a.findViewById(R.id.frameDetail);
		}
	}

	public static final class dialog_check_update {
		public final TextView tvCheckingForUpdates;
		public final TextView tvDownloadingUpdates;
		public final TextView tvDonwloadCompleted;
		public final Button btnClose;

		public dialog_check_update(View v) {
			tvCheckingForUpdates = (TextView) v
					.findViewById(R.id.tvCheckingForUpdates);
			tvDownloadingUpdates = (TextView) v
					.findViewById(R.id.tvDownloadingUpdates);
			tvDonwloadCompleted = (TextView) v
					.findViewById(R.id.tvDonwloadCompleted);
			btnClose = (Button) v.findViewById(R.id.btnClose);
		}

		public dialog_check_update(Activity a) {
			tvCheckingForUpdates = (TextView) a
					.findViewById(R.id.tvCheckingForUpdates);
			tvDownloadingUpdates = (TextView) a
					.findViewById(R.id.tvDownloadingUpdates);
			tvDonwloadCompleted = (TextView) a
					.findViewById(R.id.tvDonwloadCompleted);
			btnClose = (Button) a.findViewById(R.id.btnClose);
		}
	}

	public static final class f_booking_schedule {
		public final LinearLayout llCalendar;
		public final TextView tvCurrentMonth;
		public final ImageButton btnPreviousMonth;
		public final ImageButton btnNextMonth;
		public final LinearLayout llWeeks;
		public final CheckedTextView btnMyBooking;
		public final CheckedTextView btnTeamBooking;
		public final Button btnFromFilter;
		public final Button btnToFilter;
		public final Spinner spType;
		public final Spinner spBrand;
		public final Spinner spSupplier;
		public final EditText etPurchaseOrderWildcard;
		public final EditText etStyleNumberWildCard;
		public final ListView lvScheduledTask;

		public f_booking_schedule(View v) {
			llCalendar = (LinearLayout) v.findViewById(R.id.llCalendar);
			tvCurrentMonth = (TextView) v.findViewById(R.id.tvCurrentMonth);
			btnPreviousMonth = (ImageButton) v
					.findViewById(R.id.btnPreviousMonth);
			btnNextMonth = (ImageButton) v.findViewById(R.id.btnNextMonth);
			llWeeks = (LinearLayout) v.findViewById(R.id.llWeeks);
			btnMyBooking = (CheckedTextView) v.findViewById(R.id.btnMyBooking);
			btnTeamBooking = (CheckedTextView) v
					.findViewById(R.id.btnTeamBooking);
			btnFromFilter = (Button) v.findViewById(R.id.btnFromFilter);
			btnToFilter = (Button) v.findViewById(R.id.btnToFilter);
			spType = (Spinner) v.findViewById(R.id.spType);
			spBrand = (Spinner) v.findViewById(R.id.spBrand);
			spSupplier = (Spinner) v.findViewById(R.id.spSupplier);
			etPurchaseOrderWildcard = (EditText) v
					.findViewById(R.id.etPurchaseOrderWildcard);
			etStyleNumberWildCard = (EditText) v
					.findViewById(R.id.etStyleNumberWildCard);
			lvScheduledTask = (ListView) v.findViewById(R.id.lvScheduledTask);
		}

		public f_booking_schedule(Activity a) {
			llCalendar = (LinearLayout) a.findViewById(R.id.llCalendar);
			tvCurrentMonth = (TextView) a.findViewById(R.id.tvCurrentMonth);
			btnPreviousMonth = (ImageButton) a
					.findViewById(R.id.btnPreviousMonth);
			btnNextMonth = (ImageButton) a.findViewById(R.id.btnNextMonth);
			llWeeks = (LinearLayout) a.findViewById(R.id.llWeeks);
			btnMyBooking = (CheckedTextView) a.findViewById(R.id.btnMyBooking);
			btnTeamBooking = (CheckedTextView) a
					.findViewById(R.id.btnTeamBooking);
			btnFromFilter = (Button) a.findViewById(R.id.btnFromFilter);
			btnToFilter = (Button) a.findViewById(R.id.btnToFilter);
			spType = (Spinner) a.findViewById(R.id.spType);
			spBrand = (Spinner) a.findViewById(R.id.spBrand);
			spSupplier = (Spinner) a.findViewById(R.id.spSupplier);
			etPurchaseOrderWildcard = (EditText) a
					.findViewById(R.id.etPurchaseOrderWildcard);
			etStyleNumberWildCard = (EditText) a
					.findViewById(R.id.etStyleNumberWildCard);
			lvScheduledTask = (ListView) a.findViewById(R.id.lvScheduledTask);
		}
	}

	public static final class i_monthly_calendar_date {
		public final CheckedTextView tvDateNumber;
		public final TextView tvFaNumber;
		public final TextView tvTaskSummary;

		public i_monthly_calendar_date(View v) {
			tvDateNumber = (CheckedTextView) v.findViewById(R.id.tvDateNumber);
			tvFaNumber = (TextView) v.findViewById(R.id.tvFaNumber);
			tvTaskSummary = (TextView) v.findViewById(R.id.tvTaskSummary);
		}

		public i_monthly_calendar_date(Activity a) {
			tvDateNumber = (CheckedTextView) a.findViewById(R.id.tvDateNumber);
			tvFaNumber = (TextView) a.findViewById(R.id.tvFaNumber);
			tvTaskSummary = (TextView) a.findViewById(R.id.tvTaskSummary);
		}
	}

	public static final class i_scheduled_task {
		public final TextView tvType;
		public final TextView tvFactory;
		public final TextView tvBrand;
		public final TextView tvSupplier;
		public final TextView tvPO;
		public final TextView tvStyle;
		public final TextView tvQC;
		public final TextView tvInsp;
		public final TextView tvBook;

		public i_scheduled_task(View v) {
			tvType = (TextView) v.findViewById(R.id.tvType);
			tvFactory = (TextView) v.findViewById(R.id.tvFactory);
			tvBrand = (TextView) v.findViewById(R.id.tvBrand);
			tvSupplier = (TextView) v.findViewById(R.id.tvSupplier);
			tvPO = (TextView) v.findViewById(R.id.tvPO);
			tvStyle = (TextView) v.findViewById(R.id.tvStyle);
			tvQC = (TextView) v.findViewById(R.id.tvQC);
			tvInsp = (TextView) v.findViewById(R.id.tvInsp);
			tvBook = (TextView) v.findViewById(R.id.tvBook);
		}

		public i_scheduled_task(Activity a) {
			tvType = (TextView) a.findViewById(R.id.tvType);
			tvFactory = (TextView) a.findViewById(R.id.tvFactory);
			tvBrand = (TextView) a.findViewById(R.id.tvBrand);
			tvSupplier = (TextView) a.findViewById(R.id.tvSupplier);
			tvPO = (TextView) a.findViewById(R.id.tvPO);
			tvStyle = (TextView) a.findViewById(R.id.tvStyle);
			tvQC = (TextView) a.findViewById(R.id.tvQC);
			tvInsp = (TextView) a.findViewById(R.id.tvInsp);
			tvBook = (TextView) a.findViewById(R.id.tvBook);
		}
	}

	public static final class dialog_inspection_task_summary {
		public final TextView tvQcName;
		public final TextView tvBookingId;
		public final TextView tvInspectionDate;
		public final TextView tvBookingDate;
		public final TextView tvFactory;
		public final TextView tvBrand;
		public final TextView tvSupplier;
		public final TextView tvPoNumber;
		public final TextView tvShiplineNumber;
		public final TextView tvShiplineQuantity;
		public final TextView tvShipMode;
		public final TextView tvStyleNumber;
		public final Button btnClose;

		public dialog_inspection_task_summary(View v) {
			tvQcName = (TextView) v.findViewById(R.id.tvQcName);
			tvBookingId = (TextView) v.findViewById(R.id.tvBookingId);
			tvInspectionDate = (TextView) v.findViewById(R.id.tvInspectionDate);
			tvBookingDate = (TextView) v.findViewById(R.id.tvBookingDate);
			tvFactory = (TextView) v.findViewById(R.id.tvFactory);
			tvBrand = (TextView) v.findViewById(R.id.tvBrand);
			tvSupplier = (TextView) v.findViewById(R.id.tvSupplier);
			tvPoNumber = (TextView) v.findViewById(R.id.tvPoNumber);
			tvShiplineNumber = (TextView) v.findViewById(R.id.tvShiplineNumber);
			tvShiplineQuantity = (TextView) v
					.findViewById(R.id.tvShiplineQuantity);
			tvShipMode = (TextView) v.findViewById(R.id.tvShipMode);
			tvStyleNumber = (TextView) v.findViewById(R.id.tvStyleNumber);
			btnClose = (Button) v.findViewById(R.id.btnClose);
		}

		public dialog_inspection_task_summary(Activity a) {
			tvQcName = (TextView) a.findViewById(R.id.tvQcName);
			tvBookingId = (TextView) a.findViewById(R.id.tvBookingId);
			tvInspectionDate = (TextView) a.findViewById(R.id.tvInspectionDate);
			tvBookingDate = (TextView) a.findViewById(R.id.tvBookingDate);
			tvFactory = (TextView) a.findViewById(R.id.tvFactory);
			tvBrand = (TextView) a.findViewById(R.id.tvBrand);
			tvSupplier = (TextView) a.findViewById(R.id.tvSupplier);
			tvPoNumber = (TextView) a.findViewById(R.id.tvPoNumber);
			tvShiplineNumber = (TextView) a.findViewById(R.id.tvShiplineNumber);
			tvShiplineQuantity = (TextView) a
					.findViewById(R.id.tvShiplineQuantity);
			tvShipMode = (TextView) a.findViewById(R.id.tvShipMode);
			tvStyleNumber = (TextView) a.findViewById(R.id.tvStyleNumber);
			btnClose = (Button) a.findViewById(R.id.btnClose);
		}
	}

	public static final class f_inspection_reports {
		public final CheckedTextView btnReportStatus;
		public final CheckedTextView btnSelectFromBooking;
		public final CheckedTextView btnNewReport;
		public final CheckedTextView btnSearchOldReports;
		public final FrameLayout frameFragmentContainer;

		public f_inspection_reports(View v) {
			btnReportStatus = (CheckedTextView) v
					.findViewById(R.id.btnReportStatus);
			btnSelectFromBooking = (CheckedTextView) v
					.findViewById(R.id.btnSelectFromBooking);
			btnNewReport = (CheckedTextView) v.findViewById(R.id.btnNewReport);
			btnSearchOldReports = (CheckedTextView) v
					.findViewById(R.id.btnSearchOldReports);
			frameFragmentContainer = (FrameLayout) v
					.findViewById(R.id.frameFragmentContainer);
		}

		public f_inspection_reports(Activity a) {
			btnReportStatus = (CheckedTextView) a
					.findViewById(R.id.btnReportStatus);
			btnSelectFromBooking = (CheckedTextView) a
					.findViewById(R.id.btnSelectFromBooking);
			btnNewReport = (CheckedTextView) a.findViewById(R.id.btnNewReport);
			btnSearchOldReports = (CheckedTextView) a
					.findViewById(R.id.btnSearchOldReports);
			frameFragmentContainer = (FrameLayout) a
					.findViewById(R.id.frameFragmentContainer);
		}

		public static final class i_report_with_status {
			public final TextView tvType;
			public final TextView tvFactory;
			public final TextView tvBrand;
			public final TextView tvSupplier;
			public final TextView tvPO;
			public final TextView tvStyle;
			public final TextView tvInsp;
			public final TextView tvBook;
			public final TextView tvStatus;

			public i_report_with_status(View v) {
				tvType = (TextView) v.findViewById(R.id.tvType);
				tvFactory = (TextView) v.findViewById(R.id.tvFactory);
				tvBrand = (TextView) v.findViewById(R.id.tvBrand);
				tvSupplier = (TextView) v.findViewById(R.id.tvSupplier);
				tvPO = (TextView) v.findViewById(R.id.tvPO);
				tvStyle = (TextView) v.findViewById(R.id.tvStyle);
				tvInsp = (TextView) v.findViewById(R.id.tvInsp);
				tvBook = (TextView) v.findViewById(R.id.tvBook);
				tvStatus = (TextView) v.findViewById(R.id.tvStatus);
			}

			public i_report_with_status(Activity a) {
				tvType = (TextView) a.findViewById(R.id.tvType);
				tvFactory = (TextView) a.findViewById(R.id.tvFactory);
				tvBrand = (TextView) a.findViewById(R.id.tvBrand);
				tvSupplier = (TextView) a.findViewById(R.id.tvSupplier);
				tvPO = (TextView) a.findViewById(R.id.tvPO);
				tvStyle = (TextView) a.findViewById(R.id.tvStyle);
				tvInsp = (TextView) a.findViewById(R.id.tvInsp);
				tvBook = (TextView) a.findViewById(R.id.tvBook);
				tvStatus = (TextView) a.findViewById(R.id.tvStatus);
			}
		}

	}

	public static final class ff_report_status {
		public final Button btnUploadReports;
		public final ListView lvReports;

		public ff_report_status(View v) {
			btnUploadReports = (Button) v.findViewById(R.id.btnUploadReports);
			lvReports = (ListView) v.findViewById(R.id.lvReports);
		}

		public ff_report_status(Activity a) {
			btnUploadReports = (Button) a.findViewById(R.id.btnUploadReports);
			lvReports = (ListView) a.findViewById(R.id.lvReports);
		}
	}

	public static final class dialog_upload_reports {
		public final TextView tvUploadingReports;
		public final TextView tvUploadCompleted;
		public final Button btnClose;

		public dialog_upload_reports(View v) {
			tvUploadingReports = (TextView) v
					.findViewById(R.id.tvUploadingReports);
			tvUploadCompleted = (TextView) v
					.findViewById(R.id.tvUploadCompleted);
			btnClose = (Button) v.findViewById(R.id.btnClose);
		}

		public dialog_upload_reports(Activity a) {
			tvUploadingReports = (TextView) a
					.findViewById(R.id.tvUploadingReports);
			tvUploadCompleted = (TextView) a
					.findViewById(R.id.tvUploadCompleted);
			btnClose = (Button) a.findViewById(R.id.btnClose);
		}
	}

	public static final class i_report_with_status {
		public final TextView tvType;
		public final TextView tvFactory;
		public final TextView tvBrand;
		public final TextView tvSupplier;
		public final TextView tvPO;
		public final TextView tvStyle;
		public final TextView tvInsp;
		public final TextView tvBook;
		public final TextView tvStatus;

		public i_report_with_status(View v) {
			tvType = (TextView) v.findViewById(R.id.tvType);
			tvFactory = (TextView) v.findViewById(R.id.tvFactory);
			tvBrand = (TextView) v.findViewById(R.id.tvBrand);
			tvSupplier = (TextView) v.findViewById(R.id.tvSupplier);
			tvPO = (TextView) v.findViewById(R.id.tvPO);
			tvStyle = (TextView) v.findViewById(R.id.tvStyle);
			tvInsp = (TextView) v.findViewById(R.id.tvInsp);
			tvBook = (TextView) v.findViewById(R.id.tvBook);
			tvStatus = (TextView) v.findViewById(R.id.tvStatus);
		}

		public i_report_with_status(Activity a) {
			tvType = (TextView) a.findViewById(R.id.tvType);
			tvFactory = (TextView) a.findViewById(R.id.tvFactory);
			tvBrand = (TextView) a.findViewById(R.id.tvBrand);
			tvSupplier = (TextView) a.findViewById(R.id.tvSupplier);
			tvPO = (TextView) a.findViewById(R.id.tvPO);
			tvStyle = (TextView) a.findViewById(R.id.tvStyle);
			tvInsp = (TextView) a.findViewById(R.id.tvInsp);
			tvBook = (TextView) a.findViewById(R.id.tvBook);
			tvStatus = (TextView) a.findViewById(R.id.tvStatus);
		}
	}

	public static final class ff_select_from_booking {
		public final Button btnFromFilter;
		public final Button btnToFilter;
		public final Spinner spType;
		public final Spinner spBrand;
		public final Spinner spSupplier;
		public final EditText etPurchaseOrderWildcard;
		public final EditText etStyleNumberWildCard;
		public final CheckedTextView btnMyBooking;
		public final CheckedTextView btnTeamBooking;
		public final ListView lvReports;

		public ff_select_from_booking(View v) {
			btnFromFilter = (Button) v.findViewById(R.id.btnFromFilter);
			btnToFilter = (Button) v.findViewById(R.id.btnToFilter);
			spType = (Spinner) v.findViewById(R.id.spType);
			spBrand = (Spinner) v.findViewById(R.id.spBrand);
			spSupplier = (Spinner) v.findViewById(R.id.spSupplier);
			etPurchaseOrderWildcard = (EditText) v
					.findViewById(R.id.etPurchaseOrderWildcard);
			etStyleNumberWildCard = (EditText) v
					.findViewById(R.id.etStyleNumberWildCard);
			btnMyBooking = (CheckedTextView) v.findViewById(R.id.btnMyBooking);
			btnTeamBooking = (CheckedTextView) v
					.findViewById(R.id.btnTeamBooking);
			lvReports = (ListView) v.findViewById(R.id.lvReports);
		}

		public ff_select_from_booking(Activity a) {
			btnFromFilter = (Button) a.findViewById(R.id.btnFromFilter);
			btnToFilter = (Button) a.findViewById(R.id.btnToFilter);
			spType = (Spinner) a.findViewById(R.id.spType);
			spBrand = (Spinner) a.findViewById(R.id.spBrand);
			spSupplier = (Spinner) a.findViewById(R.id.spSupplier);
			etPurchaseOrderWildcard = (EditText) a
					.findViewById(R.id.etPurchaseOrderWildcard);
			etStyleNumberWildCard = (EditText) a
					.findViewById(R.id.etStyleNumberWildCard);
			btnMyBooking = (CheckedTextView) a.findViewById(R.id.btnMyBooking);
			btnTeamBooking = (CheckedTextView) a
					.findViewById(R.id.btnTeamBooking);
			lvReports = (ListView) a.findViewById(R.id.lvReports);
		}
	}

	public static final class dialog_inspection_1 {
		public final TextView tvType;
		public final TextView tvSubtype;
		public final TextView tvQcName;
		public final TextView tvBookingId;
		public final TextView tvInspectionDate;
		public final TextView tvBookingDate;
		public final ListView lvShiplines;
		public final ImageButton btnAddShipline;
		public final ListView lvForms;
		public final Button btnDelete;
		public final Button btnClose;

		public dialog_inspection_1(View v) {
			tvType = (TextView) v.findViewById(R.id.tvType);
			tvSubtype = (TextView) v.findViewById(R.id.tvSubtype);
			tvQcName = (TextView) v.findViewById(R.id.tvQcName);
			tvBookingId = (TextView) v.findViewById(R.id.tvBookingId);
			tvInspectionDate = (TextView) v.findViewById(R.id.tvInspectionDate);
			tvBookingDate = (TextView) v.findViewById(R.id.tvBookingDate);
			lvShiplines = (ListView) v.findViewById(R.id.lvShiplines);
			btnAddShipline = (ImageButton) v.findViewById(R.id.btnAddShipline);
			lvForms = (ListView) v.findViewById(R.id.lvForms);
			btnDelete = (Button) v.findViewById(R.id.btnDelete);
			btnClose = (Button) v.findViewById(R.id.btnClose);
		}

		public dialog_inspection_1(Activity a) {
			tvType = (TextView) a.findViewById(R.id.tvType);
			tvSubtype = (TextView) a.findViewById(R.id.tvSubtype);
			tvQcName = (TextView) a.findViewById(R.id.tvQcName);
			tvBookingId = (TextView) a.findViewById(R.id.tvBookingId);
			tvInspectionDate = (TextView) a.findViewById(R.id.tvInspectionDate);
			tvBookingDate = (TextView) a.findViewById(R.id.tvBookingDate);
			lvShiplines = (ListView) a.findViewById(R.id.lvShiplines);
			btnAddShipline = (ImageButton) a.findViewById(R.id.btnAddShipline);
			lvForms = (ListView) a.findViewById(R.id.lvForms);
			btnDelete = (Button) a.findViewById(R.id.btnDelete);
			btnClose = (Button) a.findViewById(R.id.btnClose);
		}
	}

	public static final class i_shipline_summary {
		public final ImageButton btnDelete;
		public final TextView tvSummary;
		public final Button btnView;

		public i_shipline_summary(View v) {
			btnDelete = (ImageButton) v.findViewById(R.id.btnDelete);
			tvSummary = (TextView) v.findViewById(R.id.tvSummary);
			btnView = (Button) v.findViewById(R.id.btnView);
		}

		public i_shipline_summary(Activity a) {
			btnDelete = (ImageButton) a.findViewById(R.id.btnDelete);
			tvSummary = (TextView) a.findViewById(R.id.tvSummary);
			btnView = (Button) a.findViewById(R.id.btnView);
		}
	}

	public static final class ff_new_report {
		public final Button btnFinalInspectionFootwear;
		public final Button btnInlineInspectionFootwear;
		public final Button btnSampleCheckFootwear;
		public final Button btnFactoryAuditFootwear;
		public final Button btnFinalInspectionApparel;
		public final Button btnInlineInspectionApparel;
		public final Button btnFactoryAuditApparel;
		public final Button btnFinalInspectionFurnitureAndHardgoods;
		public final Button btnInlineInspectionFurnitureAndHardgoods;
		public final Button btnSampleCheckFurnitureAndHardgoods;
		public final Button btnFactoryAuditFurnitureAndHardgoods;

		public ff_new_report(View v) {
			btnFinalInspectionFootwear = (Button) v
					.findViewById(R.id.btnFinalInspectionFootwear);
			btnInlineInspectionFootwear = (Button) v
					.findViewById(R.id.btnInlineInspectionFootwear);
			btnSampleCheckFootwear = (Button) v
					.findViewById(R.id.btnSampleCheckFootwear);
			btnFactoryAuditFootwear = (Button) v
					.findViewById(R.id.btnFactoryAuditFootwear);
			btnFinalInspectionApparel = (Button) v
					.findViewById(R.id.btnFinalInspectionApparel);
			btnInlineInspectionApparel = (Button) v
					.findViewById(R.id.btnInlineInspectionApparel);
			btnFactoryAuditApparel = (Button) v
					.findViewById(R.id.btnFactoryAuditApparel);
			btnFinalInspectionFurnitureAndHardgoods = (Button) v
					.findViewById(R.id.btnFinalInspectionFurnitureAndHardgoods);
			btnInlineInspectionFurnitureAndHardgoods = (Button) v
					.findViewById(R.id.btnInlineInspectionFurnitureAndHardgoods);
			btnSampleCheckFurnitureAndHardgoods = (Button) v
					.findViewById(R.id.btnSampleCheckFurnitureAndHardgoods);
			btnFactoryAuditFurnitureAndHardgoods = (Button) v
					.findViewById(R.id.btnFactoryAuditFurnitureAndHardgoods);
		}

		public ff_new_report(Activity a) {
			btnFinalInspectionFootwear = (Button) a
					.findViewById(R.id.btnFinalInspectionFootwear);
			btnInlineInspectionFootwear = (Button) a
					.findViewById(R.id.btnInlineInspectionFootwear);
			btnSampleCheckFootwear = (Button) a
					.findViewById(R.id.btnSampleCheckFootwear);
			btnFactoryAuditFootwear = (Button) a
					.findViewById(R.id.btnFactoryAuditFootwear);
			btnFinalInspectionApparel = (Button) a
					.findViewById(R.id.btnFinalInspectionApparel);
			btnInlineInspectionApparel = (Button) a
					.findViewById(R.id.btnInlineInspectionApparel);
			btnFactoryAuditApparel = (Button) a
					.findViewById(R.id.btnFactoryAuditApparel);
			btnFinalInspectionFurnitureAndHardgoods = (Button) a
					.findViewById(R.id.btnFinalInspectionFurnitureAndHardgoods);
			btnInlineInspectionFurnitureAndHardgoods = (Button) a
					.findViewById(R.id.btnInlineInspectionFurnitureAndHardgoods);
			btnSampleCheckFurnitureAndHardgoods = (Button) a
					.findViewById(R.id.btnSampleCheckFurnitureAndHardgoods);
			btnFactoryAuditFurnitureAndHardgoods = (Button) a
					.findViewById(R.id.btnFactoryAuditFurnitureAndHardgoods);
		}
	}

	public static final class dialog_add_shipline {
		public final TextView tvBookDate;
		public final TextView tvPoNumber;
		public final TextView tvStyleNumber;
		public final TextView tvShipId;
		public final TextView tvShipQuantity;
		public final TextView tvShipMode;
		public final TextView tvShipDate;
		public final EditText etPurchaseOrderWildcard;
		public final EditText etStyleNumberWildCard;
		public final Button btnManualInput;
		public final Button btnClose;
		public final ListView lvShiplines;

		public dialog_add_shipline(View v) {
			tvBookDate = (TextView) v.findViewById(R.id.tvBookDate);
			tvPoNumber = (TextView) v.findViewById(R.id.tvPoNumber);
			tvStyleNumber = (TextView) v.findViewById(R.id.tvStyleNumber);
			tvShipId = (TextView) v.findViewById(R.id.tvShipId);
			tvShipQuantity = (TextView) v.findViewById(R.id.tvShipQuantity);
			tvShipMode = (TextView) v.findViewById(R.id.tvShipMode);
			tvShipDate = (TextView) v.findViewById(R.id.tvShipDate);
			etPurchaseOrderWildcard = (EditText) v
					.findViewById(R.id.etPurchaseOrderWildcard);
			etStyleNumberWildCard = (EditText) v
					.findViewById(R.id.etStyleNumberWildCard);
			btnManualInput = (Button) v.findViewById(R.id.btnManualInput);
			btnClose = (Button) v.findViewById(R.id.btnClose);
			lvShiplines = (ListView) v.findViewById(R.id.lvShiplines);
		}

		public dialog_add_shipline(Activity a) {
			tvBookDate = (TextView) a.findViewById(R.id.tvBookDate);
			tvPoNumber = (TextView) a.findViewById(R.id.tvPoNumber);
			tvStyleNumber = (TextView) a.findViewById(R.id.tvStyleNumber);
			tvShipId = (TextView) a.findViewById(R.id.tvShipId);
			tvShipQuantity = (TextView) a.findViewById(R.id.tvShipQuantity);
			tvShipMode = (TextView) a.findViewById(R.id.tvShipMode);
			tvShipDate = (TextView) a.findViewById(R.id.tvShipDate);
			etPurchaseOrderWildcard = (EditText) a
					.findViewById(R.id.etPurchaseOrderWildcard);
			etStyleNumberWildCard = (EditText) a
					.findViewById(R.id.etStyleNumberWildCard);
			btnManualInput = (Button) a.findViewById(R.id.btnManualInput);
			btnClose = (Button) a.findViewById(R.id.btnClose);
			lvShiplines = (ListView) a.findViewById(R.id.lvShiplines);
		}
	}

	public static final class i_shipline {
		public final TextView tvBookDate;
		public final TextView tvPo;
		public final TextView tvStyle;
		public final TextView tvShipId;
		public final TextView tvQuantity;
		public final TextView tvMode;
		public final TextView tvShipDate;

		public i_shipline(View v) {
			tvBookDate = (TextView) v.findViewById(R.id.tvBookDate);
			tvPo = (TextView) v.findViewById(R.id.tvPo);
			tvStyle = (TextView) v.findViewById(R.id.tvStyle);
			tvShipId = (TextView) v.findViewById(R.id.tvShipId);
			tvQuantity = (TextView) v.findViewById(R.id.tvQuantity);
			tvMode = (TextView) v.findViewById(R.id.tvMode);
			tvShipDate = (TextView) v.findViewById(R.id.tvShipDate);
		}

		public i_shipline(Activity a) {
			tvBookDate = (TextView) a.findViewById(R.id.tvBookDate);
			tvPo = (TextView) a.findViewById(R.id.tvPo);
			tvStyle = (TextView) a.findViewById(R.id.tvStyle);
			tvShipId = (TextView) a.findViewById(R.id.tvShipId);
			tvQuantity = (TextView) a.findViewById(R.id.tvQuantity);
			tvMode = (TextView) a.findViewById(R.id.tvMode);
			tvShipDate = (TextView) a.findViewById(R.id.tvShipDate);
		}
	}

	public static final class ff_search_old_report {
		public final Button btnFromFilter;
		public final Button btnToFilter;
		public final Spinner spType;
		public final Spinner spBrand;
		public final Spinner spSupplier;
		public final EditText etPurchaseOrderWildcard;
		public final EditText etStyleNumberWildCard;
		public final ListView lvReports;

		public ff_search_old_report(View v) {
			btnFromFilter = (Button) v.findViewById(R.id.btnFromFilter);
			btnToFilter = (Button) v.findViewById(R.id.btnToFilter);
			spType = (Spinner) v.findViewById(R.id.spType);
			spBrand = (Spinner) v.findViewById(R.id.spBrand);
			spSupplier = (Spinner) v.findViewById(R.id.spSupplier);
			etPurchaseOrderWildcard = (EditText) v
					.findViewById(R.id.etPurchaseOrderWildcard);
			etStyleNumberWildCard = (EditText) v
					.findViewById(R.id.etStyleNumberWildCard);
			lvReports = (ListView) v.findViewById(R.id.lvReports);
		}

		public ff_search_old_report(Activity a) {
			btnFromFilter = (Button) a.findViewById(R.id.btnFromFilter);
			btnToFilter = (Button) a.findViewById(R.id.btnToFilter);
			spType = (Spinner) a.findViewById(R.id.spType);
			spBrand = (Spinner) a.findViewById(R.id.spBrand);
			spSupplier = (Spinner) a.findViewById(R.id.spSupplier);
			etPurchaseOrderWildcard = (EditText) a
					.findViewById(R.id.etPurchaseOrderWildcard);
			etStyleNumberWildCard = (EditText) a
					.findViewById(R.id.etStyleNumberWildCard);
			lvReports = (ListView) a.findViewById(R.id.lvReports);
		}
	}

	public static final class dialog_inspection_status_detail {
		public final TextView tvType;
		public final TextView tvSubtype;
		public final TextView tvQcName;
		public final TextView tvBookingId;
		public final TextView tvInspectionDate;
		public final TextView tvBookingDate;
		public final TextView tvFactory;
		public final TextView tvBrand;
		public final TextView tvSupplier;
		public final TextView tvPoNumber;
		public final TextView tvShiplineNumber;
		public final TextView tvShiplineQuantity;
		public final TextView tvShipMode;
		public final TextView tvStyleNumber;
		public final ListView lvForms;
		public final Button btnClose;
		public final Button btnSaveToWork;

		public dialog_inspection_status_detail(View v) {
			tvType = (TextView) v.findViewById(R.id.tvType);
			tvSubtype = (TextView) v.findViewById(R.id.tvSubtype);
			tvQcName = (TextView) v.findViewById(R.id.tvQcName);
			tvBookingId = (TextView) v.findViewById(R.id.tvBookingId);
			tvInspectionDate = (TextView) v.findViewById(R.id.tvInspectionDate);
			tvBookingDate = (TextView) v.findViewById(R.id.tvBookingDate);
			tvFactory = (TextView) v.findViewById(R.id.tvFactory);
			tvBrand = (TextView) v.findViewById(R.id.tvBrand);
			tvSupplier = (TextView) v.findViewById(R.id.tvSupplier);
			tvPoNumber = (TextView) v.findViewById(R.id.tvPoNumber);
			tvShiplineNumber = (TextView) v.findViewById(R.id.tvShiplineNumber);
			tvShiplineQuantity = (TextView) v
					.findViewById(R.id.tvShiplineQuantity);
			tvShipMode = (TextView) v.findViewById(R.id.tvShipMode);
			tvStyleNumber = (TextView) v.findViewById(R.id.tvStyleNumber);
			lvForms = (ListView) v.findViewById(R.id.lvForms);
			btnClose = (Button) v.findViewById(R.id.btnClose);
			btnSaveToWork = (Button) v.findViewById(R.id.btnSaveToWork);
		}

		public dialog_inspection_status_detail(Activity a) {
			tvType = (TextView) a.findViewById(R.id.tvType);
			tvSubtype = (TextView) a.findViewById(R.id.tvSubtype);
			tvQcName = (TextView) a.findViewById(R.id.tvQcName);
			tvBookingId = (TextView) a.findViewById(R.id.tvBookingId);
			tvInspectionDate = (TextView) a.findViewById(R.id.tvInspectionDate);
			tvBookingDate = (TextView) a.findViewById(R.id.tvBookingDate);
			tvFactory = (TextView) a.findViewById(R.id.tvFactory);
			tvBrand = (TextView) a.findViewById(R.id.tvBrand);
			tvSupplier = (TextView) a.findViewById(R.id.tvSupplier);
			tvPoNumber = (TextView) a.findViewById(R.id.tvPoNumber);
			tvShiplineNumber = (TextView) a.findViewById(R.id.tvShiplineNumber);
			tvShiplineQuantity = (TextView) a
					.findViewById(R.id.tvShiplineQuantity);
			tvShipMode = (TextView) a.findViewById(R.id.tvShipMode);
			tvStyleNumber = (TextView) a.findViewById(R.id.tvStyleNumber);
			lvForms = (ListView) a.findViewById(R.id.lvForms);
			btnClose = (Button) a.findViewById(R.id.btnClose);
			btnSaveToWork = (Button) a.findViewById(R.id.btnSaveToWork);
		}
	}

	public static final class i_report_form_status {
		public final TextView tvFormName;
		public final CheckedTextView ctvNew;
		public final CheckedTextView ctvDraft;
		public final CheckedTextView ctvDone;

		public i_report_form_status(View v) {
			tvFormName = (TextView) v.findViewById(R.id.tvFormName);
			ctvNew = (CheckedTextView) v.findViewById(R.id.ctvNew);
			ctvDraft = (CheckedTextView) v.findViewById(R.id.ctvDraft);
			ctvDone = (CheckedTextView) v.findViewById(R.id.ctvDone);
		}

		public i_report_form_status(Activity a) {
			tvFormName = (TextView) a.findViewById(R.id.tvFormName);
			ctvNew = (CheckedTextView) a.findViewById(R.id.ctvNew);
			ctvDraft = (CheckedTextView) a.findViewById(R.id.ctvDraft);
			ctvDone = (CheckedTextView) a.findViewById(R.id.ctvDone);
		}
	}

	public static final class a_form_editor {
		public final WebView wvFormEditor;
		public final Button btnAddPhoto;
		public final Button btnAddBarcode;
		public final Button btnEditAttachment;
		public final Button btnDone;
		public final Button btnSave;
		public final Button btnExit;

		public a_form_editor(View v) {
			wvFormEditor = (WebView) v.findViewById(R.id.wvFormEditor);
			btnAddPhoto = (Button) v.findViewById(R.id.btnAddPhoto);
			btnAddBarcode = (Button) v.findViewById(R.id.btnAddBarcode);
			btnEditAttachment = (Button) v.findViewById(R.id.btnEditAttachment);
			btnDone = (Button) v.findViewById(R.id.btnDone);
			btnSave = (Button) v.findViewById(R.id.btnSave);
			btnExit = (Button) v.findViewById(R.id.btnExit);
		}

		public a_form_editor(Activity a) {
			wvFormEditor = (WebView) a.findViewById(R.id.wvFormEditor);
			btnAddPhoto = (Button) a.findViewById(R.id.btnAddPhoto);
			btnAddBarcode = (Button) a.findViewById(R.id.btnAddBarcode);
			btnEditAttachment = (Button) a.findViewById(R.id.btnEditAttachment);
			btnDone = (Button) a.findViewById(R.id.btnDone);
			btnSave = (Button) a.findViewById(R.id.btnSave);
			btnExit = (Button) a.findViewById(R.id.btnExit);
		}
	}

	public static final class dialog_add_barcode {
		public final Button btnBack;
		public final Button btnNext;
		public final Button btnSave;
		public final Button btnScanBarcode;
		public final Button btnAddBarcodeImage;
		public final ImageView imgPhoto;
		public final EditText etDescription;

		public dialog_add_barcode(View v) {
			btnBack = (Button) v.findViewById(R.id.btnBack);
			btnNext = (Button) v.findViewById(R.id.btnNext);
			btnSave = (Button) v.findViewById(R.id.btnSave);
			btnScanBarcode = (Button) v.findViewById(R.id.btnScanBarcode);
			btnAddBarcodeImage = (Button) v
					.findViewById(R.id.btnAddBarcodeImage);
			imgPhoto = (ImageView) v.findViewById(R.id.imgPhoto);
			etDescription = (EditText) v.findViewById(R.id.etDescription);
		}

		public dialog_add_barcode(Activity a) {
			btnBack = (Button) a.findViewById(R.id.btnBack);
			btnNext = (Button) a.findViewById(R.id.btnNext);
			btnSave = (Button) a.findViewById(R.id.btnSave);
			btnScanBarcode = (Button) a.findViewById(R.id.btnScanBarcode);
			btnAddBarcodeImage = (Button) a
					.findViewById(R.id.btnAddBarcodeImage);
			imgPhoto = (ImageView) a.findViewById(R.id.imgPhoto);
			etDescription = (EditText) a.findViewById(R.id.etDescription);
		}
	}

	public static final class dialog_add_photo {
		public final Button btnBack;
		public final Button btnNext;
		public final Button btnSave;
		public final Button btnRetakePhoto;
		public final Button btnChoosePhoto;
		public final Button btnEditPhoto;
		public final ImageView imgPhoto;
		public final EditText etDescription;

		public dialog_add_photo(View v) {
			btnBack = (Button) v.findViewById(R.id.btnBack);
			btnNext = (Button) v.findViewById(R.id.btnNext);
			btnSave = (Button) v.findViewById(R.id.btnSave);
			btnRetakePhoto = (Button) v.findViewById(R.id.btnRetakePhoto);
			btnChoosePhoto = (Button) v.findViewById(R.id.btnChoosePhoto);
			btnEditPhoto = (Button) v.findViewById(R.id.btnEditPhoto);
			imgPhoto = (ImageView) v.findViewById(R.id.imgPhoto);
			etDescription = (EditText) v.findViewById(R.id.etDescription);
		}

		public dialog_add_photo(Activity a) {
			btnBack = (Button) a.findViewById(R.id.btnBack);
			btnNext = (Button) a.findViewById(R.id.btnNext);
			btnSave = (Button) a.findViewById(R.id.btnSave);
			btnRetakePhoto = (Button) a.findViewById(R.id.btnRetakePhoto);
			btnChoosePhoto = (Button) a.findViewById(R.id.btnChoosePhoto);
			btnEditPhoto = (Button) a.findViewById(R.id.btnEditPhoto);
			imgPhoto = (ImageView) a.findViewById(R.id.imgPhoto);
			etDescription = (EditText) a.findViewById(R.id.etDescription);
		}
	}

	public static final class dialog_edit_attachment {
		public final Button btnBack;
		public final ListView lvAttachments;

		public dialog_edit_attachment(View v) {
			btnBack = (Button) v.findViewById(R.id.btnBack);
			lvAttachments = (ListView) v.findViewById(R.id.lvAttachments);
		}

		public dialog_edit_attachment(Activity a) {
			btnBack = (Button) a.findViewById(R.id.btnBack);
			lvAttachments = (ListView) a.findViewById(R.id.lvAttachments);
		}
	}

	public static final class i_attachment {
		public final TextView tvType;
		public final ImageView imgPhoto;
		public final TextView tvDescription;
		public final Button btnView;
		public final Button btnDelete;

		public i_attachment(View v) {
			tvType = (TextView) v.findViewById(R.id.tvType);
			imgPhoto = (ImageView) v.findViewById(R.id.imgPhoto);
			tvDescription = (TextView) v.findViewById(R.id.tvDescription);
			btnView = (Button) v.findViewById(R.id.btnView);
			btnDelete = (Button) v.findViewById(R.id.btnDelete);
		}

		public i_attachment(Activity a) {
			tvType = (TextView) a.findViewById(R.id.tvType);
			imgPhoto = (ImageView) a.findViewById(R.id.imgPhoto);
			tvDescription = (TextView) a.findViewById(R.id.tvDescription);
			btnView = (Button) a.findViewById(R.id.btnView);
			btnDelete = (Button) a.findViewById(R.id.btnDelete);
		}
	}
	public static final class dialog_manual_input_barcode {
		public final Spinner spBarcodeFormat;
		public final EditText etBarcodeContent;
		public dialog_manual_input_barcode(View v) {
			spBarcodeFormat = (Spinner) v.findViewById(R.id.spBarcodeFormat);
			etBarcodeContent = (EditText) v.findViewById(R.id.etBarcodeContent);
		}
		public dialog_manual_input_barcode(Activity a) {
			spBarcodeFormat = (Spinner) a.findViewById(R.id.spBarcodeFormat);
			etBarcodeContent = (EditText) a.findViewById(R.id.etBarcodeContent);
		}
	}
	public static final class dialog_manual_input_shipline {
		public final EditText etBookingDate;
		public final EditText etPo;
		public final EditText etStyle;
		public final EditText etShipId;
		public final EditText etQuantity;
		public final EditText etMode;
		public final EditText etShippingDate;
		public dialog_manual_input_shipline(View v) {
			etBookingDate = (EditText) v.findViewById(R.id.etBookingDate);
			etPo = (EditText) v.findViewById(R.id.etPo);
			etStyle = (EditText) v.findViewById(R.id.etStyle);
			etShipId = (EditText) v.findViewById(R.id.etShipId);
			etQuantity = (EditText) v.findViewById(R.id.etQuantity);
			etMode = (EditText) v.findViewById(R.id.etMode);
			etShippingDate = (EditText) v.findViewById(R.id.etShippingDate);
		}
		public dialog_manual_input_shipline(Activity a) {
			etBookingDate = (EditText) a.findViewById(R.id.etBookingDate);
			etPo = (EditText) a.findViewById(R.id.etPo);
			etStyle = (EditText) a.findViewById(R.id.etStyle);
			etShipId = (EditText) a.findViewById(R.id.etShipId);
			etQuantity = (EditText) a.findViewById(R.id.etQuantity);
			etMode = (EditText) a.findViewById(R.id.etMode);
			etShippingDate = (EditText) a.findViewById(R.id.etShippingDate);
		}
	}
}
