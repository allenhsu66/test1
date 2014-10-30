package hk.ed.kgs.inspectionfa;

import java.util.Calendar;

import android.view.View;

public interface OnCalendarDateCheckedListener {
	
	public void onDateChecked(View v, Calendar date);
	public void onDateUnchecked(View v, Calendar date);
	public void onMonthChangedListener(Calendar whichMonth);

}
