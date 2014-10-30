package hk.ed.kgs.inspectionfa;

//import hk.ed.android.library.imagemarkup.ActivityDrawingPad;
import hk.ed.androidframework.EverydayActivity;
import hk.ed.androidframework.EverydayFragmentActivity;
import hk.ed.kgs.inspectionfa.data.Attachment;
import hk.ed.kgs.inspectionfa.data.InspectionReport;
import hk.ed.kgs.inspectionfa.data.ReportForm;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.ITFWriter;
import com.google.zxing.oned.UPCAWriter;

public class ActivityFormEditor extends EverydayActivity implements
		OnClickListener {

	public static final int CODE_FORM_EDITOR = 123;

	private static final String REPORT = "report";
	private static final int CODE_BARCODE = 234;
	private static final int CODE_CHOOSE_PHOTO = 345;
	private static final int CODE_TAKE_PHOTO = 456;
	private static final int CODE_PHOTO_MARKUP = 567;
	private static final int CODE_TAKE_BARCODE_PHOTO = 678;

	public static void launchActivityForResult(Activity from,
			ReportForm report, int requestCode) {
		Intent intent = new Intent(from, ActivityFormEditor.class);
		Gson gson = new Gson();
		intent.putExtra(REPORT, gson.toJson(report));
		from.startActivityForResult(intent, requestCode);
	}

	public static ReportForm getEditedReportForm(Intent data) {
		Gson gson = new Gson();
		return gson.fromJson(data.getStringExtra(REPORT), ReportForm.class);
	}

	private static Intent getReturnIntent(ReportForm report) {
		Intent data = new Intent();
		Gson gson = new Gson();
		data.putExtra(REPORT, gson.toJson(report));
		return data;
	}

	private ViewHolder.a_form_editor vh;
	private ReportForm report;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_form_editor);
		vh = new ViewHolder.a_form_editor(this);
		String szReport = getIntent().getStringExtra(REPORT);
		Gson gson = new Gson();
		report = gson.fromJson(szReport, ReportForm.class);
		vh.btnSave.setOnClickListener(this);
		vh.btnAddBarcode.setOnClickListener(this);
		vh.btnAddPhoto.setOnClickListener(this);
		vh.btnDone.setOnClickListener(this);
		vh.btnEditAttachment.setOnClickListener(this);
		vh.btnExit.setOnClickListener(this);
		vh.wvFormEditor.loadUrl("file:///android_asset/forms/Apparel Factory Audit Report Blank v2.html");
		vh.wvFormEditor.getSettings().setSupportZoom(true);
		vh.wvFormEditor.getSettings().setBuiltInZoomControls(true);
	}

	private AlertDialog currentDialog;
	private View currentDialogView;
	private String currentBarcodeContent;
	private String currentBarcodeFormat;
	private String currentPhoto;
	private boolean isCurrentBarcodePhoto = false;

	@Override
	public void onClick(View v) {
		if (v == vh.btnExit) {
			setResult(Activity.RESULT_OK, getReturnIntent(report));
			finish();
		} else if (v == vh.btnDone) {
			report.status = InspectionReport.STATUS_DONE;
			toast("Save was successful");
			vh.btnExit.performClick();
		} else if (v == vh.btnSave) {
			toast("Save was successful");
		} else if (v == vh.btnAddBarcode) {
			showAddBarcodeDialog();
		} else if (v == vh.btnAddPhoto) {
			showAddPhotoDialog();
		} else if (v == vh.btnEditAttachment) {
			showEditAttachmentDialog();
		}
	}

	public void showEditAttachmentDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = getLayoutInflater().inflate(R.layout.dialog_edit_attachment, null);
		ViewHolder.dialog_edit_attachment vhDialog = new ViewHolder.dialog_edit_attachment(view);
		builder.setView(view);
		final AlertDialog dialog = builder.create();
		vhDialog.btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
			
		});
		ArrayAdapter<Attachment> adapter = new ArrayAdapter<Attachment>(this, R.layout.i_attachment) {
			
			private OnClickListener deleteListener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					Attachment item = (Attachment) v.getTag();
					if (item != null) {
						remove(item);
					}
				}
			};
			private OnClickListener viewListener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					Attachment item = (Attachment) v.getTag();
					viewAttachment(item);
				}

				private void viewAttachment(final Attachment item) {
					if (item.isBarcode()) {
						showAddBarcodeDialog();
						currentDialogView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

							@Override
							public void onGlobalLayout() {
								currentDialogView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
								if (item.barcodeFormat.equals(Attachment.FORMAT_PHOTO)) {
									loadBarcodeImageIntoDialog(item.file, item.description);
								} else {
									loadBarcodeIntoDialog(item.description, item.barcodeFormat);
								}
							}
						});
						ViewHolder.dialog_add_barcode vhDialog = new ViewHolder.dialog_add_barcode(currentDialogView);
						vhDialog.etDescription.setEnabled(false);
						vhDialog.btnNext.setVisibility(View.INVISIBLE);
						vhDialog.btnSave.setVisibility(View.INVISIBLE);
						vhDialog.btnAddBarcodeImage.setVisibility(View.INVISIBLE);
						vhDialog.btnScanBarcode.setVisibility(View.INVISIBLE);
					} else if (item.isPhoto()) {
						showAddPhotoDialog();
						currentDialogView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

							@Override
							public void onGlobalLayout() {
								currentDialogView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
								loadPhotoIntoDialog(item.file, item.description);
							}
						});
						ViewHolder.dialog_add_photo vhDialog = new ViewHolder.dialog_add_photo(currentDialogView);
						vhDialog.etDescription.setEnabled(false);
						vhDialog.btnChoosePhoto.setVisibility(View.INVISIBLE);
						vhDialog.btnEditPhoto.setVisibility(View.INVISIBLE);
						vhDialog.btnNext.setVisibility(View.INVISIBLE);
						vhDialog.btnRetakePhoto.setVisibility(View.INVISIBLE);
						vhDialog.btnSave.setVisibility(View.INVISIBLE);
					}
				}
			};
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = getLayoutInflater().inflate(R.layout.i_attachment, null);
				}
				final ViewHolder.i_attachment vhItem = new ViewHolder.i_attachment(convertView);
				final Attachment item = getItem(position);
				vhItem.btnView.setTag(item);
				vhItem.btnDelete.setTag(item);
				vhItem.btnView.setOnClickListener(viewListener);
				vhItem.btnDelete.setOnClickListener(deleteListener);
				if (item.isBarcode()) {
					vhItem.tvType.setText("Barcode");
					if (vhItem.imgPhoto.getWidth() == 0) {
						vhItem.imgPhoto.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

							@Override
							public void onGlobalLayout() {
								vhItem.imgPhoto.getViewTreeObserver().removeGlobalOnLayoutListener(this);
								if (item.barcodeFormat.equals(Attachment.FORMAT_PHOTO)) {
									loadScaledImageIntoView(vhItem.imgPhoto, item.file);
								} else {
									vhItem.imgPhoto.setImageBitmap(getBarcodeBitmap(item.description, item.barcodeFormat, vhItem.imgPhoto.getWidth(), vhItem.imgPhoto.getHeight()));
								}
							}
							
						});
					} else {
						if (item.barcodeFormat.equals(Attachment.FORMAT_PHOTO)) {
							loadScaledImageIntoView(vhItem.imgPhoto, item.file);
						} else {
							vhItem.imgPhoto.setImageBitmap(getBarcodeBitmap(item.description, item.barcodeFormat, vhItem.imgPhoto.getWidth(), vhItem.imgPhoto.getHeight()));
						}
					}
					
				} else if (item.isPhoto()) {
					vhItem.tvType.setText("Photo");
					if (vhItem.imgPhoto.getWidth() == 0) {
						vhItem.imgPhoto.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

							@Override
							public void onGlobalLayout() {
								vhItem.imgPhoto.getViewTreeObserver().removeGlobalOnLayoutListener(this);
								loadScaledImageIntoView(vhItem.imgPhoto, item.file);
							}
							
						});
					} else {
						loadScaledImageIntoView(vhItem.imgPhoto, item.file);
					}
				}
				
				vhItem.tvDescription.setText(item.description);
				return convertView;
			}
		};
		vhDialog.lvAttachments.setAdapter(adapter);
		adapter.addAll(report.attachments);
		dialog.show();
	}

	public void showAddPhotoDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		currentDialogView = getLayoutInflater().inflate(
				R.layout.dialog_add_photo, null);
		builder.setView(currentDialogView);
		final ViewHolder.dialog_add_photo vhDialog = new ViewHolder.dialog_add_photo(
				currentDialogView);
		currentDialog = builder.create();
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				currentDialog = null;
				currentDialogView = null;
				currentPhoto = null;
			}
		});
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v == vhDialog.btnBack) {
					currentDialog.cancel();
				} else if (v == vhDialog.btnChoosePhoto) {
					launchImageChooser();
				} else if (v == vhDialog.btnEditPhoto) {
					if (currentPhoto != null) {
//						ActivityDrawingPad.launchActivityForResult(ActivityFormEditor.this, currentPhoto, Color.RED, 4, CODE_PHOTO_MARKUP);
					} else {
						toast("Please take or select a photo first");
					}
				} else if (v == vhDialog.btnNext) {
					if (savePhotoToAttachment(report, currentPhoto, vhDialog.etDescription.getText().toString())) {
						currentPhoto = null;
						vhDialog.imgPhoto.setImageResource(R.drawable.attachement_photo);
						vhDialog.etDescription.setText("");
					} else {
						toast("Error occured when saving photo to attachment");
					}
				} else if (v == vhDialog.btnRetakePhoto) {
					launchCamera(CODE_TAKE_PHOTO);
				} else if (v == vhDialog.btnSave) {
					if (savePhotoToAttachment(report, currentPhoto, vhDialog.etDescription.getText().toString())) {
						currentDialog.cancel();
					} else {
						toast("Error occured when saving photo to attachment");
					}
				}
			}

		};
		vhDialog.btnBack.setOnClickListener(listener);
		vhDialog.btnChoosePhoto.setOnClickListener(listener);
		vhDialog.btnEditPhoto.setOnClickListener(listener);
		vhDialog.btnNext.setOnClickListener(listener);
		vhDialog.btnRetakePhoto.setOnClickListener(listener);
		vhDialog.btnSave.setOnClickListener(listener);
		currentDialog.show();
	}

	public void showAddBarcodeDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		currentDialogView = getLayoutInflater().inflate(
				R.layout.dialog_add_barcode, null);
		builder.setView(currentDialogView);
		final ViewHolder.dialog_add_barcode vhDialog = new ViewHolder.dialog_add_barcode(
				currentDialogView);
		currentDialog = builder.create();
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				currentDialog = null;
				currentDialogView = null;
				currentBarcodeContent = null;
			}
		});
		OnClickListener listener = new OnClickListener() {
			
			class BarcodeFormatAdapter extends ArrayAdapter<BarcodeFormat> {
				
				public BarcodeFormat[] supportedBarcodeFormat = new BarcodeFormat[]{
					BarcodeFormat.CODE_128,
					BarcodeFormat.ITF,
					BarcodeFormat.UPC_A,
					BarcodeFormat.EAN_13
				};

				public BarcodeFormatAdapter(Context context) {
					super(context, R.layout.header_spinner);
				}
				
				@Override
				public View getView(int position, View convertView, ViewGroup parent) {
					if (convertView == null) {
						if (getContext() instanceof EverydayActivity) {
							convertView = ((EverydayActivity)getContext()).getLayoutInflater().inflate(R.layout.header_spinner, null);
						} else if (getContext() instanceof EverydayFragmentActivity) {
							convertView = ((EverydayFragmentActivity)getContext()).getLayoutInflater().inflate(R.layout.header_spinner, null);
						}
					}
					BarcodeFormat item = getItem(position);
					TextView tv = (TextView) convertView;
					switch (item) {
					case CODE_128:
						tv.setText("Code 128");
						break;
					case ITF:
						tv.setText("Interleave 2 of 5 (ITF)");
						break;
					case UPC_A:
						tv.setText("UPC-A");
						break;
					case UPC_E:
						tv.setText("UPC-E");
						break;
					case UPC_EAN_EXTENSION:
						tv.setText("UPC EAN Extension");
						break;
					case EAN_13:
						tv.setText("EAN-13");
						break;
					}
					return convertView;
				}
				
				@Override
				public View getDropDownView(int position, View convertView, ViewGroup parent) {
					if (convertView == null) {
						if (getContext() instanceof EverydayActivity) {
							convertView = ((EverydayActivity)getContext()).getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
						} else if (getContext() instanceof EverydayFragmentActivity) {
							convertView = ((EverydayFragmentActivity)getContext()).getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
						}
					}
					BarcodeFormat item = getItem(position);
					TextView tv = (TextView) convertView;
					switch (item) {
					case CODE_128:
						tv.setText("Code 128");
						break;
					case ITF:
						tv.setText("Interleave 2 of 5 (ITF)");
						break;
					case UPC_A:
						tv.setText("UPC-A");
						break;
					case UPC_E:
						tv.setText("UPC-E");
						break;
					case UPC_EAN_EXTENSION:
						tv.setText("UPC EAN Extension");
						break;
					case EAN_13:
						tv.setText("EAN-13");
						break;
					}
					return convertView;
				}
				
			}

			@Override
			public void onClick(View v) {
				if (v == vhDialog.btnAddBarcodeImage) {
					launchCamera(CODE_TAKE_BARCODE_PHOTO);
				} else if (v == vhDialog.btnScanBarcode) {
					launchQRCodeScanner();
				} else if (v == vhDialog.btnBack) {
					currentDialog.cancel();
				} else if (v == vhDialog.btnSave) {
					if (!isCurrentBarcodePhoto) {
						if (saveBarcodeToAttachment(report, currentBarcodeContent, currentBarcodeFormat)) {
							currentDialog.cancel();
						} else {
							toast("Error occured when saving barcode to attachment");
						}
					} else {
						if (saveBarcodeImageToAttachment(report, vhDialog.etDescription.getText().toString(), currentPhoto)) {
							currentDialog.cancel();
						} else {
							toast("Error occured when saving barcode to attachment");
						}
					}
				} else if (v == vhDialog.btnNext) {
					if (!isCurrentBarcodePhoto) {
						if (saveBarcodeToAttachment(report, currentBarcodeContent, currentBarcodeFormat)) {
							currentBarcodeContent = null;
							vhDialog.etDescription.setText("");
							vhDialog.imgPhoto
							.setImageResource(R.drawable.attachement_photo);
						} else {
							toast("Error occured when saving barcode to attachment");
						}
					} else {
						if (saveBarcodeImageToAttachment(report, vhDialog.etDescription.getText().toString(), currentPhoto)) {
							currentBarcodeContent = null;
							vhDialog.etDescription.setText("");
							vhDialog.imgPhoto
							.setImageResource(R.drawable.attachement_photo);
						} else {
							toast("Error occured when saving barcode to attachment");
						}
					}
				}
			}

		};
		vhDialog.btnBack.setOnClickListener(listener);
		vhDialog.btnScanBarcode.setOnClickListener(listener);
		vhDialog.btnAddBarcodeImage.setOnClickListener(listener);
		vhDialog.btnSave.setOnClickListener(listener);
		vhDialog.btnNext.setOnClickListener(listener);
		currentDialog.show();
	}
	
	private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    File storageDir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_DCIM);
	    File image = File.createTempFile(
	        imageFileName,  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );

	    // Save a file: path for use with ACTION_VIEW intents
	    currentPhoto = image.getAbsolutePath();
	    return image;
	}
	
	private void launchCamera(int requestCode) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    // Ensure that there's a camera activity to handle the intent
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        // Create the File where the photo should go
	        File photoFile = null;
	        try {
	            photoFile = createImageFile();
	        } catch (IOException ex) {
	            // Error occurred while creating the File
	        	toast("Something wrong with the camera, try choose photo from gallery instead");
	        }
	        // Continue only if the File was successfully created
	        if (photoFile != null) {
	            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
	                    Uri.fromFile(photoFile));
	            startActivityForResult(takePictureIntent, requestCode);
	        }
	    }
	}
	
	private void galleryAddPic(String path) {
	    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    File f = new File(path);
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    this.sendBroadcast(mediaScanIntent);
	}

	private void launchImageChooser() {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, CODE_CHOOSE_PHOTO);
	}
	
	private boolean savePhotoToAttachment(ReportForm report, String photo, String description) {
		if (photo != null) {
			Attachment attachment = new Attachment();
			attachment.setPhoto(photo, description);
			report.attachments.add(attachment);
			return true;
		} else {
			return false;
		}
	}

	private boolean saveBarcodeToAttachment(ReportForm report, String barcode, String format) {
		if (barcode != null) {
			Attachment attachment = new Attachment();
			attachment.setBarcode(barcode, format);
			report.attachments.add(attachment);
			return true;
		} else {
			return false;
		}
	}
	
	private boolean saveBarcodeImageToAttachment(ReportForm report, String barcode, String photo) {
		if (barcode != null) {
			Attachment attachment = new Attachment();
			attachment.setBarcodeImage(barcode, photo);
			report.attachments.add(attachment);
			return true;
		} else {
			return false;
		}
	}

	private void launchQRCodeScanner() {
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		startActivityForResult(intent, CODE_BARCODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CODE_BARCODE) {
			if (resultCode == Activity.RESULT_OK) {
				currentBarcodeContent = data.getStringExtra("SCAN_RESULT");
				currentBarcodeFormat = data.getStringExtra("SCAN_RESULT_FORMAT");
				isCurrentBarcodePhoto = false;
				loadBarcodeIntoDialog(currentBarcodeContent, currentBarcodeFormat);
			}
		} else if (requestCode == CODE_CHOOSE_PHOTO) {
			if (resultCode == Activity.RESULT_OK) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(
						selectedImage, filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();
				currentPhoto = picturePath;
				loadPhotoIntoDialog(currentPhoto, null);
			}
		} else if (requestCode == CODE_TAKE_PHOTO) {
			if (resultCode == Activity.RESULT_OK) {
				galleryAddPic(currentPhoto);
				loadPhotoIntoDialog(currentPhoto, null);
			} else {
				File tempFile = new File(currentPhoto);
				if (tempFile.exists() && tempFile.length() == 0) {
					tempFile.delete();
				}
			}
		} else if (requestCode == CODE_PHOTO_MARKUP) {
			if (resultCode == Activity.RESULT_OK) {
				galleryAddPic(currentPhoto);
				loadPhotoIntoDialog(currentPhoto, null);
			}
		} else if (requestCode == CODE_TAKE_BARCODE_PHOTO) {
			if (resultCode == Activity.RESULT_OK) {
				galleryAddPic(currentPhoto);
				isCurrentBarcodePhoto = true;
				loadBarcodeImageIntoDialog(currentPhoto, "");
			} else {
				File tempFile = new File(currentPhoto);
				if (tempFile.exists() && tempFile.length() == 0) {
					tempFile.delete();
				}
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	public void loadPhotoIntoDialog(String photo, String description) {
		ViewHolder.dialog_add_photo vhDialog = new ViewHolder.dialog_add_photo(currentDialogView);
		loadScaledImageIntoView(vhDialog.imgPhoto, photo);
		if (description != null) {
			vhDialog.etDescription.setText(description);
		}
	}

	public void loadBarcodeImageIntoDialog(String photo, String description) {
		ViewHolder.dialog_add_barcode vhDialog = new ViewHolder.dialog_add_barcode(currentDialogView);
		loadScaledImageIntoView(vhDialog.imgPhoto, photo);
		vhDialog.etDescription.setEnabled(true);
		if (description != null) {
			vhDialog.etDescription.setText(description);
		}
	}

	public void loadBarcodeIntoDialog(String barcode, String format) {
		ViewHolder.dialog_add_barcode vhDialog = new ViewHolder.dialog_add_barcode(
				currentDialogView);
		vhDialog.etDescription.setText(barcode);
		vhDialog.etDescription.setEnabled(false);
		Bitmap bmpBarcode = getBarcodeBitmap(barcode, format);
		if (bmpBarcode == null) {
			toast("Error when generating barcode image from the scanned barcode");
		} else {
			vhDialog.imgPhoto.setImageBitmap(bmpBarcode);
		}
	}

	private boolean justBigger = true;
	public void loadScaledImageIntoView(ImageView view, String path) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		Bitmap photo = BitmapFactory.decodeFile(path, opts);
		int picWidth = opts.outWidth;
		int picHeight = opts.outHeight;
		int viewWidth = view.getWidth();
		int viewHeight = view.getHeight();
		int sampleSize = 1;
		while (picWidth > viewWidth || picHeight > viewHeight) {
			picWidth /= 2;
			picHeight /= 2;
			sampleSize *= 2;
		}
		if (sampleSize != 1 && justBigger) {
			sampleSize /= 2;
		}
		opts.inJustDecodeBounds = false;
		opts.inSampleSize = sampleSize;
		photo = BitmapFactory.decodeFile(path, opts);
		view.setImageBitmap(photo);
	}
	
	private Bitmap getBarcodeBitmap(String barcode, String format) {
		return getBarcodeBitmap(barcode, format, 350, 350);
	}

	private Bitmap getBarcodeBitmap(String barcode, String format, int width, int height) {
		Writer writer = null;
		if (format.equals(BarcodeFormat.CODE_128.name())) {
			writer = new Code128Writer();
		} else if (format.equals(BarcodeFormat.UPC_A.name())) {
			writer = new UPCAWriter();
		} else if (format.equals(BarcodeFormat.EAN_13.name())) {
			writer = new EAN13Writer();
		} else if (format.equals(BarcodeFormat.ITF.name())) {
			writer = new ITFWriter();
		} else {
			return null;
		}
		String finaldata = Uri.encode(barcode, "ISO-8859-1");
		Bitmap bitmap = null;
		try {
			BarcodeFormat bfFormat = BarcodeFormat.valueOf(format);
			BitMatrix bm = writer.encode(finaldata, bfFormat, width,
					height);
			bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					bitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK
							: Color.WHITE);
				}
			}
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (Exception e) {
			toast(e.getMessage());
		}
		return bitmap;
	}

}
