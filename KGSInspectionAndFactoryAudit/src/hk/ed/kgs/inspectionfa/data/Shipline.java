package hk.ed.kgs.inspectionfa.data;

import java.util.ArrayList;
import java.util.List;

public class Shipline {
	
	public String bookingDate;
	public String po;
	public String style;
	public String shipId;
	public int quantity;
	public String mode;
	public String shippingDate;
	
	public static Shipline getDemoInstance1() {
		Shipline shipline = new Shipline();
		shipline.bookingDate = "Mar 27";
		shipline.po = "PO-12345";
		shipline.style = "ST-12345";
		shipline.shipId = "001";
		shipline.quantity = 103;
		shipline.mode = "Air";
		shipline.shippingDate = "Mar 27";
		return shipline;
	}
	
	public static Shipline getDemoInstance2() {
		Shipline shipline = new Shipline();
		shipline.bookingDate = "N/A";
		shipline.po = "PO-12345";
		shipline.style = "ST-12345";
		shipline.shipId = "001";
		shipline.quantity = 103;
		shipline.mode = "Sea";
		shipline.shippingDate = "Mar 27";
		return shipline;
	}
	
	public static List<Shipline> getDemoList() {
		List<Shipline> list = new ArrayList<Shipline>();
		list.add(getDemoInstance1());
		list.add(getDemoInstance2());
		return list;
	}

}
