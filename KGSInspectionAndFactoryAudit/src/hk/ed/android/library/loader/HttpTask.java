package hk.ed.android.library.loader;

import hk.ed.android.library.loader.HttpHelper.API;
import hk.ed.android.library.loader.HttpHelper.EverydayNameValuePair;

import java.lang.ref.WeakReference;
import java.util.List;

import android.os.AsyncTask;

public class HttpTask<I, D, V> extends AsyncTask<I, Void, D> {

	public interface HttpTaskHandler<D, V> {
		public abstract void onHttpSuccess(D result, V v);

		public abstract void onHttpFail(Exception e, V v);
	}

	private final WeakReference<V>	viewRef;
	private API<D>				api;
	private HttpTaskHandler<D, V>	handler;

	public static <I, D, V> HttpTask<I, D, V> createTask(API<D> api, V v, HttpTaskHandler<D, V> handler) {
		return new HttpTask<I, D, V>(api, v, handler);
	}
	
	public HttpTask(API<D> api, V v, HttpTaskHandler<D, V> handler) {
		this.api = api;
		this.handler = handler;
		viewRef = new WeakReference<V>(v);
	}

	@Override
	protected D doInBackground(I... params) {

		V v = viewRef.get();
		if (v != null) {
			try {
				I param = params[0];
				if (param instanceof List<?>) {
					return new HttpHelper().postFormData(api, (List<EverydayNameValuePair>)param);
				} else {
					return new HttpHelper().postJson(api, params[0]);
				}
			} catch (Exception e) {
				// e.printStackTrace();
				handler.onHttpFail(e, v);
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(D result) {
		if (result == null) {
			return;
		}

		V v = viewRef.get();
		if (v != null) {
			handler.onHttpSuccess(result, v);
		}
	}

}