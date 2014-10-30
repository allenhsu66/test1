package hk.ed.kgs.inspectionfa;

import hk.ed.androidframework.EverydayActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ActivityLogin extends EverydayActivity implements OnClickListener {
	
	private ViewHolder.a_login vh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_login);
		vh = new ViewHolder.a_login(this);
		vh.btnLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == vh.btnLogin) {
			showProgressDialog();
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return null;
				}
				
				@Override
				protected void onPostExecute(Void result) {
					dismissProgressDialog();
					ActivityMain.launchActivity(ActivityLogin.this);
			     }
				
			}.execute();
		}
	}
}
