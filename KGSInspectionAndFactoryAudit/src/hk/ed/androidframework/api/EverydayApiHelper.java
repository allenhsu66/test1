package hk.ed.androidframework.api;

import hk.ed.android.library.loader.HttpHelper.API;
import hk.ed.android.library.loader.HttpHelper.EverydayNameValuePair;
import hk.ed.android.library.loader.HttpTask;
import hk.ed.android.library.loader.HttpTask.HttpTaskHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class EverydayApiHelper {
	public static final String BASE_URL = "http://www.beetree.com.hk:8081/educationplatform/api";

	public static class WebStaffObject {
		public String token;
		public int userId;
		public String department;
		public String name;
		public String id;
	}

	public static class LoginPageOutput {
		public String status;
		public String message;
		public WebStaffObject data;
	}

	// sample code
	public static class ApiLoginPage extends API<LoginPageOutput> {
		public ApiLoginPage() {
			super(BASE_URL + "/userLogin", LoginPageOutput.class);
		}
	}
	public static class DownloadApiLoginPage extends API<File> {
		public DownloadApiLoginPage() {
			super(BASE_URL + "/userLogin", File.class);
		}
	}

	// sample code
	public void loginPage(String name, String password, Activity v,
			HttpTaskHandler<LoginPageOutput, Activity> handler) {
		List<EverydayNameValuePair> nvp = new ArrayList<EverydayNameValuePair>();
		nvp.add(new EverydayNameValuePair("loginName", name));
		nvp.add(new EverydayNameValuePair("password", password));
		HttpTask.createTask(new ApiLoginPage(), v, handler).execute(nvp);
	}
	
	public void downloadLoginPage(String name, String password, Activity v,
			HttpTaskHandler<File, Activity> handler) {
		List<EverydayNameValuePair> nvp = new ArrayList<EverydayNameValuePair>();
		nvp.add(new EverydayNameValuePair("loginName", name));
		nvp.add(new EverydayNameValuePair("password", password));
		HttpTask.createTask(new DownloadApiLoginPage(), v, handler).execute(nvp);
	}

}
