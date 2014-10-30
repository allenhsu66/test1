package hk.ed.android.library.loader;

import hk.ed.android.library.util.LoggingUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HttpHelper {

	private static boolean		enableLog	= true;
	private static final String	LOG_TAG		= "POST";
	
	public static class EverydayNameValuePair {
		private String name;
		private Object value;
		
		public EverydayNameValuePair(String name, Object value) {
			if (!(value instanceof String) && !(value instanceof File)) {
				throw new IllegalArgumentException("value can only be java.io.File or java.lang.String");
			}
			this.name = name;
			this.value = value;
		}
		
		public String getName() {
			return name;
		}
		
		public Object getValue() {
			return value;
		}
		
	}

	public static class API<O> {
		public String	url;
		public Class<O>	resultClass;

		public API(String url, Class<O> resultClass) {
			super();
			this.url = url;
			this.resultClass = resultClass;
		}

	}

	private static class SSLSocketFactoryEx extends SSLSocketFactory {

		SSLContext	sslContext	= SSLContext.getInstance("TLS");

		public SSLSocketFactoryEx(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {
				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws java.security.cert.CertificateException {
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws java.security.cert.CertificateException {
				}
			};
			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException,
				UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}

	private static final DefaultHttpClient	httpClient	= getNewHttpClient();

	private static DefaultHttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams hp = new BasicHttpParams();
			HttpProtocolParams.setVersion(hp, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(hp, HTTP.UTF_8);
			HttpConnectionParams.setConnectionTimeout(hp, 60000);
			HttpConnectionParams.setSoTimeout(hp, 60000);

			hp.setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.RFC_2109);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(hp, registry);

			return new DefaultHttpClient(ccm, hp);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	public static void disableHttpsValidation() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		HostnameVerifier hostnameVerifier = new HostnameVerifier() {
			@Override
			public boolean verify(String string, SSLSession ssls) {
				return true;
			}
		};
		// Install the all-trusting trust manager
		try {
			HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isConnectedWithWifi(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return mWifi.isConnected();
	}

	public static boolean isConnectedWithMobileNetwork(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return mMobile.isConnected();
	}

	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public static boolean isEnableLog() {
		return enableLog;
	}

	public static void setEnableLog(boolean enableLog) {
		HttpHelper.enableLog = enableLog;
	}

	private Gson		gson;
	private HttpPost	httpPost;

	public void clearCookies() {
		httpClient.getCookieStore().clear();
	}

	public HttpHelper() {
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
	}

	public void abort() {
		try {
			if (httpClient != null) {
				httpPost.abort();
			}
		} catch (Exception e) {
			System.out.println("HTTPHelp : Abort Exception : " + e);
		}
	}

	public HttpResponse postJson(String url, Object parameters) throws IOException {

		httpPost = new HttpPost(url);

		String json = gson.toJson(parameters);
		if (enableLog) {
			Log.d(LOG_TAG, url);
			Log.d(LOG_TAG, json);
		}

		httpPost.addHeader("Content-Type", "application/json");
		StringEntity inEntity = new StringEntity(json, "UTF8");
		httpPost.setEntity(inEntity);

		HttpResponse httpResponse = httpClient.execute(httpPost);
				return httpResponse;

	}
	
	public HttpResponse postFormData(String url, List<EverydayNameValuePair> nvp) throws ClientProtocolException, IOException {
		httpPost = new HttpPost(url);
//		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		if (enableLog) {
			LoggingUtils.log(LOG_TAG, url);
			for (EverydayNameValuePair pair : nvp) {
				LoggingUtils.log(LOG_TAG, pair.getName() + ": " + pair.getValue().toString());
			}
		}
		MultipartEntity multipartEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		if (nvp != null) {
			for (EverydayNameValuePair pair : nvp) {
				Object value = pair.getValue();
				if (value instanceof String) {
//					httpPost.addHeader(pair.getName(), (String) value);
					multipartEntity.addPart(pair.getName(),
							new StringBody((String) value));
				} else if (value instanceof File) {
					multipartEntity.addPart(pair.name, new FileBody((File) value));
				}
			}
		}
		httpPost.setEntity(multipartEntity);
		for (Header header : httpPost.getAllHeaders()) {
			LoggingUtils.log("Header", header.getName() + ": " + header.getValue());
		}
		HttpResponse httpResponse = httpClient.execute(httpPost);
				return httpResponse;
	}

	/**
	 * Make a post request to specific URL and auto-parse the result to the result class.
	 * If the result class is java.io.File, it will automatically download the file to a temp location
	 * @param url
	 * @param parameters
	 * @param resultClass The received response will 
	 * @return
	 * @throws Exception
	 */
	public <O> O postJson(String url, Object parameters, Class<O> resultClass) throws Exception {
		HttpResponse response = postJson(url, parameters);
		if (resultClass.equals(java.io.File.class)) {
			String value = response.getFirstHeader("Content-Type").getValue();
			if (!value.equals("application/json") && !value.equals("text/html")) {
				File file = File.createTempFile("temp", ".tmp", Environment.getDownloadCacheDirectory());
				FileOutputStream fos = new FileOutputStream(file);
				response.getEntity().writeTo(fos);
				return (O) file;
			} else {
				// some error has occurred, it intended to download a file while it returns a json / html
				throw new Exception(EntityUtils.toString(response.getEntity()));
			}
		} else {
			HttpEntity httpEntity = response.getEntity();
			
			String content = EntityUtils.toString(httpEntity);
			
			if (enableLog) {
				LoggingUtils.log(LOG_TAG, "Result of post to " + url);
				LoggingUtils.log(LOG_TAG, content);
			}
			
			JsonObject apiResult = new JsonParser().parse(content).getAsJsonObject();
			
			O dataObject = gson.fromJson(apiResult, resultClass);
			
			return dataObject;
		}
	}
	
	public <O> O postFormData(String url, List<EverydayNameValuePair> parameters, Class<O> resultClass) throws Exception {
		HttpResponse response = postFormData(url, parameters);
		if (enableLog) {
			LoggingUtils.log(LOG_TAG, "StatusCode: " + response.getStatusLine().getStatusCode());
		}
		if (resultClass.equals(java.io.File.class)) {
			String value = response.getFirstHeader("Content-Type").getValue();
			LoggingUtils.log("CheckingFile", value);
			if (!value.contains("application/json") && !value.contains("text/html")) {
				File file = File.createTempFile("temp", ".tmp", Environment.getExternalStorageDirectory());
				FileOutputStream fos = new FileOutputStream(file);
				response.getEntity().writeTo(fos);
				return (O) file;
			} else {
				// some error has occurred, it intended to download a file while it returns a json / html
				throw new Exception(EntityUtils.toString(response.getEntity()));
			}
		} else {
			HttpEntity httpEntity = response.getEntity();
			
			String content = EntityUtils.toString(httpEntity);
			
			if (enableLog) {
				LoggingUtils.log(LOG_TAG, "Result of post to " + url);
				LoggingUtils.log(LOG_TAG, content);
			}
			
			JsonObject apiResult = new JsonParser().parse(content).getAsJsonObject();
			
			O dataObject = gson.fromJson(apiResult, resultClass);
			
			return dataObject;
		}
	}

	public <I, O> O postJson(API<O> api, I parameters) throws Exception {
		return postJson(api.url, parameters, api.resultClass);
	}
	
	public <O> O postFormData(API<O> api, List<EverydayNameValuePair> parameters) throws Exception {
		return postFormData(api.url, parameters, api.resultClass);
	}

}