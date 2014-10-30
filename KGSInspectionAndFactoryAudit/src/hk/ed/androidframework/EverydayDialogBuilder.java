package hk.ed.androidframework;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class EverydayDialogBuilder {

	public static class ToastDialog {
		public static void show(Context context, int strResId) {
			Toast.makeText(context, context.getString(strResId),
					Toast.LENGTH_LONG).show();
		}

		public static void show(Context context, String msg) {
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}
	}

	public static class ProgressDialogFragment extends DialogFragment {
		public static ProgressDialogFragment createDialog() {
			ProgressDialogFragment frag = new ProgressDialogFragment();
			// frag.constructDialog();
			return frag;
		}

		public static ProgressDialogFragment createDialog(int theme) {
			ProgressDialogFragment frag = new ProgressDialogFragment();
			// frag.constructDialog(theme);
			return frag;
		}

		/**
		 * Show the ProgressDialog in given Title and Message. Default:
		 * Cancelable, Unindeterminable, No Cancel Listener
		 * 
		 * @param manager
		 * @param tag
		 * @param title
		 * @param message
		 * @return
		 */
		public static ProgressDialogFragment show(FragmentManager manager,
				String tag, CharSequence title, CharSequence message) {
			return show(manager, tag, title, message, false);
		}

		public static ProgressDialogFragment show(FragmentManager manager,
				String tag, CharSequence title, CharSequence message,
				boolean indeterminate) {
			return show(manager, tag, title, message, indeterminate, true);
		}

		public static ProgressDialogFragment show(FragmentManager manager,
				String tag, CharSequence title, CharSequence message,
				boolean indeterminate, boolean cancelable) {
			return show(manager, tag, title, message, indeterminate,
					cancelable, null);
		}

		public static ProgressDialogFragment show(FragmentManager manager,
				String tag, CharSequence title, CharSequence message,
				boolean indeterminate, boolean cancelable,
				DialogInterface.OnCancelListener cancelListener) {
			ProgressDialogFragment frag = createDialog();
			frag.setTitle(title);
			frag.setMessage(message);
			frag.setIndeterminate(indeterminate);
			frag.setCanDialogCancelable(cancelable);
			frag.setOnCancelListener(cancelListener);
			frag.show(manager, tag);
			return frag;
		}

		/**
		 * showAllowingStateLoss can used with AsyncTask that, it prevents
		 * illegalStateException while the activity is being put to background.
		 * No Transaction.commit() is required inside the Activity
		 * 
		 * @param transaction
		 * @param tag
		 * @param title
		 * @param message
		 * @return
		 */
		public static ProgressDialogFragment showAllowingStateLoss(
				FragmentTransaction transaction, String tag,
				CharSequence title, CharSequence message) {
			return showAllowingStateLoss(transaction, tag, title, message,
					false);
		}

		public static ProgressDialogFragment showAllowingStateLoss(
				FragmentTransaction transaction, String tag,
				CharSequence title, CharSequence message, boolean indeterminate) {
			return showAllowingStateLoss(transaction, tag, title, message,
					indeterminate, true);
		}

		public static ProgressDialogFragment showAllowingStateLoss(
				FragmentTransaction transaction, String tag,
				CharSequence title, CharSequence message,
				boolean indeterminate, boolean cancelable) {
			return showAllowingStateLoss(transaction, tag, title, message,
					indeterminate, cancelable, null);
		}

		/**
		 * To dismiss, initialize fragmentTransaction, then call the transaction
		 * to remove the dialog. and commit allow state loss. finally, call the
		 * dialog to dismiss allow state loss.
		 * 
		 * @param transaction
		 * @param tag
		 * @param title
		 * @param message
		 * @param indeterminate
		 * @param cancelable
		 * @param cancelListener
		 * @return
		 */

		public static ProgressDialogFragment showAllowingStateLoss(
				FragmentTransaction transaction, String tag,
				CharSequence title, CharSequence message,
				boolean indeterminate, boolean cancelable,
				DialogInterface.OnCancelListener cancelListener) {
			ProgressDialogFragment frag = createDialog();
			frag.setTitle(title);
			frag.setMessage(message);
			frag.setIndeterminate(indeterminate);
			frag.setCanDialogCancelable(cancelable);
			frag.setOnCancelListener(cancelListener);
			transaction.add(frag, null);
			transaction.commitAllowingStateLoss();
			return frag;
		}

		private ProgressDialog dialog;
		private DialogInterface.OnCancelListener cancelListener = null;
		private CharSequence title = "";
		private int titleId = 0;// 0 is invalid for resource
		private CharSequence message = "";
		private boolean indeterminate = false;
		private boolean cancelable = true;

		public ProgressDialogFragment() {
			// required empty constructor
		}

		public boolean isIndeterminate() {
			return this.indeterminate;
		}

		public void setIndeterminate(boolean indeterminate) {
			this.indeterminate = indeterminate;
		}

		public void setMessage(CharSequence message) {
			if (dialog != null) {
				dialog.setMessage(message);
			} else {
				this.message = message;
			}
		}

		/**
		 * Renamed from setCancelable to avoid override
		 * 
		 * @param cancelable
		 */
		public void setCanDialogCancelable(boolean cancelable) {
			this.cancelable = cancelable;
		}

		public void setTitle(int resId) {
			if (dialog != null) {
				dialog.setTitle(resId);
			} else {
				this.titleId = resId;
			}
		}

		public void setTitle(CharSequence title) {
			if (dialog != null) {
				dialog.setTitle(title);
			} else {
				this.title = title;
			}
		}

		// public void setProgress(int value) {
		// dialog.setProgress(value);
		// }
		//
		// public void setProgressDrawable(Drawable d) {
		// dialog.setProgressDrawable(d);
		// }

		public void setOnCancelListener(
				DialogInterface.OnCancelListener cancelListener) {
			this.cancelListener = cancelListener;
		}

		/**
		 * Change the format of the small text showing current and maximum units
		 * of progress. The default is "%1d/%2d". Should not be called during
		 * the number is progressing.
		 * 
		 * @param format
		 *            A string passed to String.format(); use "%1d" for the
		 *            current number and "%2d" for the maximum. If null, nothing
		 *            will be shown.
		 * @return
		 **/
		// public void setProgressNumberFormat(String format) {
		// dialog.setProgressNumberFormat(format);
		// }

		/**
		 * Change the format of the small text showing the percentage of
		 * progress. The default is NumberFormat.getPercentageInstnace(). Should
		 * not be called during the number is progressing.
		 * 
		 * @param format
		 *            An instance of a NumberFormat to generate the percentage
		 *            text. If null, nothing will be shown.
		 * @return
		 */

		@Override
		public ProgressDialog onCreateDialog(Bundle savedInstanceState) {
			dialog = new ProgressDialog(getActivity());
			dialog.setIndeterminate(indeterminate);
			dialog.setMessage(message);
			if (titleId != 0) {
				dialog.setTitle(titleId);
			} else {
				dialog.setTitle(title);
			}
			this.setCancelable(cancelable);
			return dialog;

		}

		@Override
		public void onCancel(DialogInterface dialog) {
			super.onCancel(dialog);
			if (cancelListener != null) {
				cancelListener.onCancel(dialog);
			}
		}

	}

	public static class MessageDialogFragment extends DialogFragment {
		public static MessageDialogFragment createDialog() {
			return new MessageDialogFragment();
		}

		// private AlertDialog.Builder builder;
		private CharSequence title = "";
		private CharSequence message;
		private boolean cancelable = true;

		private boolean shouldSetPositiveButton = false;
		private CharSequence positiveButtonLabel = "";
		private DialogInterface.OnClickListener positiveButtonOnClickListener = null;

		private boolean shouldSetNeutralButton = false;
		private CharSequence neutralButtonLabel = "";
		private DialogInterface.OnClickListener neutralButtonOnClickListener = null;

		private boolean shouldSetNegativeButton = false;
		private CharSequence negativeButtonLabel = "";
		private DialogInterface.OnClickListener negativeButtonOnClickListener = null;

		private boolean shouldSetSingleChoiceItems = false;
		private CharSequence[] singleChoiceItems;
		private int singleChoiceItemsCheckedItem;
		private DialogInterface.OnClickListener singleChoiceItemsOnClickListener = null;

		private DialogInterface.OnCancelListener cancelListener;

		public MessageDialogFragment() {
			// required empty constructor
		}

		public boolean getIsCancelable() {
			return this.isCancelable();
		}

		public void setTitle(CharSequence title) {

			this.title = title;
		}

		public void setMessage(CharSequence message) {
			this.message = message;
		}

		public void setCanDialogCancelable(boolean cancelable) {
			this.cancelable = cancelable;
		}

		public void setPositiveButton(CharSequence Label,
				DialogInterface.OnClickListener positiveButtonOnClickListener) {

			shouldSetPositiveButton = true;
			this.positiveButtonLabel = Label;
			this.positiveButtonOnClickListener = positiveButtonOnClickListener;
		}

		public void setNeutralButton(CharSequence Label,
				DialogInterface.OnClickListener neutralButtonOnClickListener) {

			shouldSetNeutralButton = true;
			this.neutralButtonLabel = Label;
			this.neutralButtonOnClickListener = neutralButtonOnClickListener;
		}

		public void setNegativeButton(CharSequence Label,
				DialogInterface.OnClickListener negativeButtonOnClickListener) {

			shouldSetNegativeButton = true;
			this.negativeButtonLabel = Label;
			this.negativeButtonOnClickListener = negativeButtonOnClickListener;
		}

		public void setSingleChoiceItems(CharSequence[] singleChoiceItems,
				int singleChoiceItemsCheckedItem,
				DialogInterface.OnClickListener singleChoiceItemsOnClickListener) {

			shouldSetSingleChoiceItems = true;
			this.singleChoiceItems = singleChoiceItems;
			this.singleChoiceItemsCheckedItem = singleChoiceItemsCheckedItem;
			this.singleChoiceItemsOnClickListener = singleChoiceItemsOnClickListener;
		}

		public void setOnCancelListener(OnCancelListener cancelListener) {

			this.cancelListener = cancelListener;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
			this.setCancelable(cancelable);
			dialog.setTitle(title);
			dialog.setMessage(message);
			if (shouldSetPositiveButton) {
				dialog.setPositiveButton(positiveButtonLabel,
						positiveButtonOnClickListener);
			}
			if (shouldSetNeutralButton) {
				dialog.setNeutralButton(neutralButtonLabel,
						neutralButtonOnClickListener);
			}
			if (shouldSetNegativeButton) {
				dialog.setNegativeButton(negativeButtonLabel,
						negativeButtonOnClickListener);
			}
			if (shouldSetSingleChoiceItems) {
				dialog.setSingleChoiceItems(singleChoiceItems,
						singleChoiceItemsCheckedItem,
						singleChoiceItemsOnClickListener);
			}
			return dialog.create();
		}

		@Override
		public void onCancel(DialogInterface dialog) {
			super.onCancel(dialog);
			if (cancelListener != null) {
				cancelListener.onCancel(dialog);
			}
		}

	}

	public static class DatePickerDialogFragment extends DialogFragment {
		// = new DialogFragment() {
		public static DatePickerDialogFragment createDialog() {
			return new DatePickerDialogFragment();
		}

		private DatePickerDialog.OnDateSetListener onDateSetListener = null;

		public DatePickerDialogFragment() {

		}

		public void setOnDateSetListener(
				DatePickerDialog.OnDateSetListener onDateSetListener) {
			this.onDateSetListener = onDateSetListener;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(getActivity(), onDateSetListener, year,
					month, day);
		}

	}

	public abstract static class CustomViewDialogFragment<ViewHolderClass>
			extends DialogFragment {
		private AlertDialog.Builder customDialog;
		private ViewHolderClass vh;
		private boolean isOnProduction = true;
		private int dialogLayoutResId;

		public CustomViewDialogFragment() {
			// Required empty public constructor
		}

		public void setDialogLayout(int resId) {
			this.dialogLayoutResId = resId;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			this.setCancelable(false);
			LayoutInflater inflater = getActivity().getLayoutInflater();
			customDialog = new AlertDialog.Builder(getActivity());
			View layout = inflater.inflate(dialogLayoutResId, null);
			customDialog.setView(layout);
			doLayoutConfiguration(vh, layout);
			return customDialog.create();
		}

		/**
		 * Do initialization of the ViewHolder inside this method. Abstract
		 * class wont do it for you.
		 * 
		 * @param vh
		 * @param layout
		 */
		public abstract void doLayoutConfiguration(ViewHolderClass vh,
				View layout);

		@Override
		public void onDetach() {
			super.onDetach();
		}

	}

}
