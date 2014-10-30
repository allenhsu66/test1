package hk.ed.kgs.inspectionfa.data;

import java.util.ArrayList;
import java.util.List;

public class InspectionReport {
	public static final String STATUS_NEW = "New";
	public static final String STATUS_DRAFT = "Draft";
	public static final String STATUS_DONE = "Done";
	
	public String type;
	public String subtype;
	public String typeShort;
	public String factory;
	public String brand;
	public String supplier;
	public String po;
	public String style;
	public String qc;
	public String inspectionDate;
	public String bookingId;
	public String bookingDate;
	public String shiplineNumber;
	public int shiplineQuantity;
	public String shipMode;
	public List<ReportForm> forms;
	public String status;
	public List<Shipline> shiplines;
	
	public static InspectionReport getDemoInstance1() {
		InspectionReport item = new InspectionReport();
		item.type = "Factory Audit";
		item.subtype = "Apparel";
		item.typeShort = "FR";
		item.factory = "Sample Factory A";
		item.brand = "Sample Brand A";
		item.supplier = "Sample Supplier A";
		item.po = "PO-10000";
		item.style = "ST-20000";
		item.qc = "QC name 30000";
		item.inspectionDate = "Mar 27";
		item.bookingId = "30000";
		item.bookingDate = "Mar 27";
		item.shiplineNumber = "001";
		item.shiplineQuantity = 100;
		item.shipMode = "Air";
		item.forms = ReportForm.getDemoList();
		item.status = STATUS_DONE;
		for (ReportForm form : item.forms) {
			form.status = InspectionReport.STATUS_DONE;
		}
		item.shiplines = Shipline.getDemoList();
		return item;
	}
	
	public static InspectionReport getDemoInstance2() {
		InspectionReport item = new InspectionReport();
		item.type = "Factory Audit";
		item.subtype = "Apparel";
		item.typeShort = "IR";
		item.factory = "Sample Factory B";
		item.brand = "Sample Brand B";
		item.supplier = "Sample Supplier B";
		item.po = "PO-10001";
		item.style = "ST-20001";
		item.qc = "QC name 30001";
		item.inspectionDate = "Mar 27";
		item.bookingId = "30001";
		item.bookingDate = "Mar 27";
		item.shiplineNumber = "002";
		item.shiplineQuantity = 101;
		item.shipMode = "Air";
		item.forms = ReportForm.getDemoList();
		item.status = STATUS_DONE;
		for (ReportForm form : item.forms) {
			form.status = InspectionReport.STATUS_DONE;
		}
		item.shiplines = Shipline.getDemoList();
		return item;
	}
	
	public static InspectionReport getDemoInstance3() {
		InspectionReport item = new InspectionReport();
		item.type = "Sample Check";
		item.subtype = "Apparel";
		item.typeShort = "SC";
		item.factory = "Sample Factory C";
		item.brand = "Sample Brand C";
		item.supplier = "Sample Supplier C";
		item.po = "PO-10002";
		item.style = "ST-20002";
		item.qc = "QC name 30002";
		item.inspectionDate = "Mar 27";
		item.bookingId = "30002";
		item.bookingDate = "Mar 27";
		item.shiplineNumber = "003";
		item.shiplineQuantity = 102;
		item.shipMode = "Air";
		item.forms = ReportForm.getDemoList();
		item.status = STATUS_DRAFT;
		item.shiplines = Shipline.getDemoList();
		return item;
	}
	
	public static InspectionReport getDemoInstance4() {
		InspectionReport item = new InspectionReport();
		item.type = "Factory Audit";
		item.subtype = "Apparel";
		item.typeShort = "FA";
		item.factory = "Sample Factory D";
		item.brand = "Sample Brand D";
		item.supplier = "Sample Supplier D";
		item.po = "PO-10003";
		item.style = "ST-20003";
		item.qc = "QC name 30003";
		item.inspectionDate = "Mar 27";
		item.bookingId = "30003";
		item.bookingDate = "Mar 27";
		item.shiplineNumber = "004";
		item.shiplineQuantity = 103;
		item.shipMode = "Air";
		item.forms = ReportForm.getDemoList();
		item.status = STATUS_DRAFT;
		item.shiplines = Shipline.getDemoList();
		return item;
	}
	
	public static InspectionReport getDemoInstance5() {
		InspectionReport item = new InspectionReport();
		item.type = "Factory Audit";
		item.subtype = "Apparel";
		item.typeShort = "FR";
		item.factory = "Sample Factory E";
		item.brand = "Sample Brand E";
		item.supplier = "Sample Supplier E";
		item.po = "PO-10004";
		item.style = "ST-20004";
		item.qc = "QC name 30004";
		item.inspectionDate = "Mar 27";
		item.bookingId = "30004";
		item.bookingDate = "Mar 27";
		item.shiplineNumber = "005";
		item.shiplineQuantity = 104;
		item.shipMode = "Air";
		item.forms = ReportForm.getDemoList();
		item.status = STATUS_DRAFT;
		item.shiplines = Shipline.getDemoList();
		return item;
	}
	
	public static InspectionReport getDemoInstance6() {
		InspectionReport item = new InspectionReport();
		item.type = "Inline Inspection";
		item.subtype = "Apparel";
		item.typeShort = "IR";
		item.factory = "Sample Factory F";
		item.brand = "Sample Brand F";
		item.supplier = "Sample Supplier F";
		item.po = "PO-10005";
		item.style = "ST-20005";
		item.qc = "QC name 30005";
		item.inspectionDate = "Mar 27";
		item.bookingId = "30005";
		item.bookingDate = "Mar 27";
		item.shiplineNumber = "006";
		item.shiplineQuantity = 105;
		item.shipMode = "Air";
		item.forms = ReportForm.getDemoList();
		item.status = STATUS_DRAFT;
		item.shiplines = Shipline.getDemoList();
		return item;
	}
	
	public static InspectionReport getDemoInstance7() {
		InspectionReport item = new InspectionReport();
		item.type = "Sample Check";
		item.subtype = "Apparel";
		item.typeShort = "SC";
		item.factory = "Sample Factory G";
		item.brand = "Sample Brand G";
		item.supplier = "Sample Supplier G";
		item.po = "PO-10006";
		item.style = "ST-20006";
		item.qc = "QC name 30006";
		item.inspectionDate = "Mar 27";
		item.bookingId = "30006";
		item.bookingDate = "Mar 27";
		item.shiplineNumber = "007";
		item.shiplineQuantity = 106;
		item.shipMode = "Air";
		item.forms = ReportForm.getDemoList();
		item.status = STATUS_DRAFT;
		item.shiplines = Shipline.getDemoList();
		return item;
	}
	
	public static InspectionReport getDemoInstance8() {
		InspectionReport item = new InspectionReport();
		item.type = "Factory Audit";
		item.subtype = "Apparel";
		item.typeShort = "FA";
		item.factory = "Sample Factory H";
		item.brand = "Sample Brand H";
		item.supplier = "Sample Supplier H";
		item.po = "PO-10007";
		item.style = "ST-20007";
		item.qc = "QC name 30007";
		item.inspectionDate = "Mar 27";
		item.bookingId = "30007";
		item.bookingDate = "Mar 27";
		item.shiplineNumber = "008";
		item.shiplineQuantity = 107;
		item.shipMode = "Air";
		item.forms = ReportForm.getDemoList();
		item.status = STATUS_DONE;
		for (ReportForm form : item.forms) {
			form.status = InspectionReport.STATUS_DONE;
		}
		item.shiplines = Shipline.getDemoList();
		return item;
	}
	
	public static InspectionReport getDemoInstance9() {
		InspectionReport item = new InspectionReport();
		item.type = "Factory Audit";
		item.subtype = "Apparel";
		item.typeShort = "FR";
		item.factory = "Sample Factory I";
		item.brand = "Sample Brand I";
		item.supplier = "Sample Supplier I";
		item.po = "PO-10008";
		item.style = "ST-20008";
		item.qc = "QC name 30008";
		item.inspectionDate = "Mar 27";
		item.bookingId = "30008";
		item.bookingDate = "Mar 27";
		item.shiplineNumber = "009";
		item.shiplineQuantity = 108;
		item.shipMode = "Air";
		item.forms = ReportForm.getDemoList();
		item.status = STATUS_DRAFT;
		item.shiplines = Shipline.getDemoList();
		return item;
	}
	
	public static InspectionReport getDemoInstance10() {
		InspectionReport item = new InspectionReport();
		item.type = "Inline Inspection";
		item.subtype = "Apparel";
		item.typeShort = "IR";
		item.factory = "Sample Factory J";
		item.brand = "Sample Brand J";
		item.supplier = "Sample Supplier J";
		item.po = "PO-10009";
		item.style = "ST-20009";
		item.qc = "QC name 30009";
		item.inspectionDate = "Mar 27";
		item.bookingId = "30009";
		item.bookingDate = "Mar 27";
		item.shiplineNumber = "010";
		item.shiplineQuantity = 109;
		item.shipMode = "Air";
		item.forms = ReportForm.getDemoList();
		item.status = STATUS_DRAFT;
		item.shiplines = Shipline.getDemoList();
		return item;
	}
	
	public static List<InspectionReport> getDemoList() {
		List<InspectionReport> list = new ArrayList<InspectionReport>();
		list.add(getDemoInstance1());
		list.add(getDemoInstance2());
		list.add(getDemoInstance3());
		list.add(getDemoInstance4());
		list.add(getDemoInstance5());
		list.add(getDemoInstance6());
		list.add(getDemoInstance7());
		list.add(getDemoInstance8());
		list.add(getDemoInstance9());
		list.add(getDemoInstance10());
		return list;
	}
	
	public static List<InspectionReport> getMyBookingDemoList() {
		List<InspectionReport> list = new ArrayList<InspectionReport>();
		list.add(getDemoInstance1());
		list.add(getDemoInstance3());
		list.add(getDemoInstance5());
		list.add(getDemoInstance7());
		list.add(getDemoInstance9());
		return list;
	}
	
	public static List<InspectionReport> getTeamBookingDemoList() {
		List<InspectionReport> list = new ArrayList<InspectionReport>();
		list.add(getDemoInstance2());
		list.add(getDemoInstance4());
		list.add(getDemoInstance6());
		list.add(getDemoInstance8());
		list.add(getDemoInstance10());
		return list;
	}
	
}
