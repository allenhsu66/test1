package hk.ed.kgs.inspectionfa.data;


public class Attachment {
	
	public static final String TYPE_PHOTO = "Photo";
	public static final String TYPE_BARCODE = "Barcode";
	public static final String FORMAT_PHOTO = "Photo";
	
	public String file;
	public String type;
	public String description;
	public String barcodeFormat;
	
	public boolean isBarcode() {
		return type.equals(TYPE_BARCODE);
	}
	
	public boolean isPhoto() {
		return type.equals(TYPE_PHOTO);
	}
	
	public String getBarcode() {
		return description;
	}
	
	public String getBarcodeFormat() {
		return barcodeFormat;
	}
	
	public String getPhoto() {
		return file;
	}
	
	public void setBarcode(String barcode, String format) {
		type = TYPE_BARCODE;
		description = barcode;
		barcodeFormat = format;
	}
	
	public void setBarcodeImage(String barcode, String photo) {
		type = TYPE_BARCODE;
		description = barcode;
		barcodeFormat = Attachment.FORMAT_PHOTO;
		file = photo;
	}
	
	public void setPhoto(String photo, String description) {
		type = TYPE_PHOTO;
		file = photo;
		this.description = description;
	}

}
