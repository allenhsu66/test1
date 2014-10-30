package hk.ed.kgs.inspectionfa.data;

import java.util.ArrayList;
import java.util.List;

public class MockupDataPool {
	
	public static List<InspectionReport> savedReports = getSavedReportsInstance();
	private static List<InspectionReport> getSavedReportsInstance() {
		List<InspectionReport> list = new ArrayList<InspectionReport>();
		list.addAll(InspectionReport.getDemoList());
		return list;
	}
	
	public static List<InspectionReport> oldOnlineReports = getOldOnlineReportsInstance();
	private static List<InspectionReport> getOldOnlineReportsInstance() {
		List<InspectionReport> list = new ArrayList<InspectionReport>();
		list.addAll(InspectionReport.getDemoList());
		for (InspectionReport r : list) {
			r.status = InspectionReport.STATUS_DONE;
		}
		return list;
	}
	
	public static List<InspectionReport> bookingReports = getBookingReportsInstance();
	private static List<InspectionReport> getBookingReportsInstance() {
		List<InspectionReport> list = new ArrayList<InspectionReport>();
		list.addAll(InspectionReport.getDemoList());
		for (InspectionReport r : list) {
			r.status = InspectionReport.STATUS_DONE;
		}
		return list;
	}

}
