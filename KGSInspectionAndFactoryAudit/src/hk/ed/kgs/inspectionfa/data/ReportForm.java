package hk.ed.kgs.inspectionfa.data;

import java.util.ArrayList;
import java.util.List;

public class ReportForm {
	
	public static final String TYPE_FINAL_INSPECTION = "Final Inspection";
	public static final String TYPE_INLINE_INSPECTION = "Inline Inspection";
	public static final String TYPE_SAMPLE_CHECK = "Sample Check";
	public static final String TYPE_FACTORY_AUDIT = "Factory Audit";
	
	public static final String SUBTYPE_FOOTWEAR = "Footwear";
	public static final String SUBTYPE_APPAREL = "Apparel";
	public static final String SUBTYPE_FURNITURE_AND_HARD_GOODS = "Furniture & Hard Goods";
	public static final String SUBTYPE_SHIPMENT_RELEASE = "Shipment Release";
	
	public String type;
	public String subtype;
	public String status;
	public List<Attachment> attachments;
	
	public void copy(ReportForm form) {
		this.type = form.type;
		this.subtype = form.subtype;
		this.status = form.status;
		this.attachments = form.attachments;
	}
	
	public static ReportForm getDemoInstance1() {
		ReportForm form = new ReportForm();
		form.status = InspectionReport.STATUS_NEW;
		form.type = TYPE_FINAL_INSPECTION;
		form.subtype = SUBTYPE_FOOTWEAR;
		form.attachments = new ArrayList<Attachment>();
		return form;
	}
	public static ReportForm getDemoInstance2() {
		ReportForm form = new ReportForm();
		form.status = InspectionReport.STATUS_NEW;
		form.type = TYPE_FINAL_INSPECTION;
		form.subtype = SUBTYPE_APPAREL;
		form.attachments = new ArrayList<Attachment>();
		return form;
	}
	public static ReportForm getDemoInstance3() {
		ReportForm form = new ReportForm();
		form.status = InspectionReport.STATUS_NEW;
		form.type = TYPE_FINAL_INSPECTION;
		form.subtype = SUBTYPE_FURNITURE_AND_HARD_GOODS;
		form.attachments = new ArrayList<Attachment>();
		return form;
	}
	public static ReportForm getDemoInstance4() {
		ReportForm form = new ReportForm();
		form.status = InspectionReport.STATUS_NEW;
		form.type = TYPE_INLINE_INSPECTION;
		form.subtype = SUBTYPE_FOOTWEAR;
		form.attachments = new ArrayList<Attachment>();
		return form;
	}
	public static ReportForm getDemoInstance5() {
		ReportForm form = new ReportForm();
		form.status = InspectionReport.STATUS_NEW;
		form.type = TYPE_INLINE_INSPECTION;
		form.subtype = SUBTYPE_APPAREL;
		form.attachments = new ArrayList<Attachment>();
		return form;
	}
	public static ReportForm getDemoInstance6() {
		ReportForm form = new ReportForm();
		form.status = InspectionReport.STATUS_NEW;
		form.type = TYPE_INLINE_INSPECTION;
		form.subtype = SUBTYPE_FURNITURE_AND_HARD_GOODS;
		form.attachments = new ArrayList<Attachment>();
		return form;
	}
	public static ReportForm getDemoInstance7() {
		ReportForm form = new ReportForm();
		form.status = InspectionReport.STATUS_NEW;
		form.type = TYPE_SAMPLE_CHECK;
		form.subtype = SUBTYPE_FOOTWEAR;
		form.attachments = new ArrayList<Attachment>();
		return form;
	}
	public static ReportForm getDemoInstance8() {
		ReportForm form = new ReportForm();
		form.status = InspectionReport.STATUS_NEW;
		form.type = TYPE_FINAL_INSPECTION;
		form.subtype = SUBTYPE_FURNITURE_AND_HARD_GOODS;
		form.attachments = new ArrayList<Attachment>();
		return form;
	}
	public static ReportForm getDemoInstance9() {
		ReportForm form = new ReportForm();
		form.status = InspectionReport.STATUS_NEW;
		form.type = TYPE_FACTORY_AUDIT;
		form.subtype = SUBTYPE_FOOTWEAR;
		form.attachments = new ArrayList<Attachment>();
		return form;
	}
	public static ReportForm getDemoInstance10() {
		ReportForm form = new ReportForm();
		form.status = InspectionReport.STATUS_NEW;
		form.type = TYPE_FACTORY_AUDIT;
		form.subtype = SUBTYPE_APPAREL;
		form.attachments = new ArrayList<Attachment>();
		return form;
	}
	public static ReportForm getDemoInstance11() {
		ReportForm form = new ReportForm();
		form.status = InspectionReport.STATUS_NEW;
		form.type = TYPE_FACTORY_AUDIT;
		form.subtype = SUBTYPE_FURNITURE_AND_HARD_GOODS;
		form.attachments = new ArrayList<Attachment>();
		return form;
	}
	public static ReportForm getDemoInstance12() {
		ReportForm form = new ReportForm();
		form.status = InspectionReport.STATUS_NEW;
		form.type = TYPE_FACTORY_AUDIT;
		form.subtype = SUBTYPE_SHIPMENT_RELEASE;
		form.attachments = new ArrayList<Attachment>();
		return form; 
	}
	public static List<ReportForm> getDemoList() {
		List<ReportForm> list = new ArrayList<ReportForm>();
		list.add(getDemoInstance1());
		list.add(getDemoInstance2());
		list.add(getDemoInstance3());
		list.add(getDemoInstance4());
		list.add(getDemoInstance12());
		return list;
	}

}
