package hk.ed.kgs.inspectionfa;

import java.util.ArrayList;
import java.util.List;

import hk.ed.androidframework.EverydayActivity;
import hk.ed.androidframework.EverydayFragmentActivity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MySpinnerAdapter extends ArrayAdapter<String>{
	public MySpinnerAdapter(Context context) {
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
		((TextView) convertView).setText(getItem(position));
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
		((TextView) convertView).setText(getItem(position));
		return convertView;
	}
	
	public static List<String> getTypeFilterList() {
		List<String> list = new ArrayList<String>();
		list.add("All Types");
		list.add("FR");
		list.add("IR");
		list.add("SC");
		list.add("FA");
		list.add("FR");
		list.add("IR");
		return list;
	}
	
	public static List<String> getBrandFilterList() {
		List<String> list = new ArrayList<String>();
		list.add("All Brands");
		list.add("Sample Brand A");
		list.add("Sample Brand B");
		list.add("Sample Brand C");
		list.add("Sample Brand D");
		list.add("Sample Brand E");
		list.add("Sample Brand F");
		list.add("Sample Brand G");
		list.add("Sample Brand H");
		list.add("Sample Brand I");
		list.add("Sample Brand J");
		return list;
	}
	
	public static List<String> getSupplierFilterList() {
		List<String> list = new ArrayList<String>();
		list.add("All Suppliers");
		list.add("Sample Supplier A");
		list.add("Sample Supplier B");
		list.add("Sample Supplier C");
		list.add("Sample Supplier D");
		list.add("Sample Supplier E");
		list.add("Sample Supplier F");
		list.add("Sample Supplier G");
		list.add("Sample Supplier H");
		list.add("Sample Supplier I");
		list.add("Sample Supplier J");
		return list;
	}
}
