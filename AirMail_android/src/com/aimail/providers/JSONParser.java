package com.aimail.providers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.airmail.service.AirMailService;

import android.util.Base64;
import android.util.Log;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	// constructor
	public JSONParser() {

	}

	private String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

	private String getASCIIContentFromEntity(HttpEntity entity)
			throws IllegalStateException, IOException {
		InputStream in = entity.getContent();

		StringBuffer out = new StringBuffer();
		int n = 1;
		while (n > 0) {
			byte[] b = new byte[4096];
			n = in.read(b);

			if (n > 0)
				out.append(new String(b, 0, n));
		}

		return out.toString();
	}

	public JSONObject getJson(String url) {
		JSONObject jsO = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet(url);
		String text = null;
		try {

			String credentials = AirMailService.name + ":"
					+ AirMailService.password;
			String base64EncodedCredentials = Base64.encodeToString(
					credentials.getBytes(), Base64.NO_WRAP);
			httpGet.addHeader("Authorization", "Basic "
					+ base64EncodedCredentials);

			HttpResponse response = httpClient.execute(httpGet, localContext);

			HttpEntity entity = response.getEntity();
			text = getASCIIContentFromEntity(entity);
			jsO = new JSONObject(text);

		} catch (Exception e) {
			Log.e("va0", e.toString());
		}

		return jsO;
	}

	public static boolean POSTUser(String name, String email, String password,
			String url) {
		InputStream inputStream = null;
		boolean result = false;

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			String json = "";
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("username", name);
			jsonObject.accumulate("email", email);
			jsonObject.accumulate("password", password);
			json = jsonObject.toString();
			StringEntity se = new StringEntity(json);
			httpPost.setEntity(se);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			HttpResponse httpResponse = httpclient.execute(httpPost);
			inputStream = httpResponse.getEntity().getContent();
			if (inputStream != null)
				result = true;
			else
				result = false;

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		return result;
	}

	public static boolean POSTMess(String text, int idD, int idU, String url) {
		InputStream inputStream = null;
		boolean result = false;

		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm");
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			String credentials = AirMailService.name + ":"
					+ AirMailService.password;
			String base64EncodedCredentials = Base64.encodeToString(
					credentials.getBytes(), Base64.NO_WRAP);
			httpPost.addHeader("Authorization", "Basic "
					+ base64EncodedCredentials);
			String json = "";
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("Text", text);
			jsonObject.accumulate("Dialogue_id", idD);
			jsonObject.accumulate("User_id", idU);
			jsonObject.accumulate("DateSent",
					dateFormat.format(Calendar.getInstance().getTime()));
			json = jsonObject.toString();
			StringEntity se = new StringEntity(json);
			httpPost.setEntity(se);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			HttpResponse httpResponse = httpclient.execute(httpPost);
			inputStream = httpResponse.getEntity().getContent();
			if (inputStream != null)
				result = true;
			else
				result = false;

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		return result;
	}

	public static boolean POSTDialog(int idU, String url) {
		InputStream inputStream = null;
		boolean result = false;

		try {

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			String credentials = AirMailService.name + ":"
					+ AirMailService.password;
			String base64EncodedCredentials = Base64.encodeToString(
					credentials.getBytes(), Base64.NO_WRAP);
			httpPost.addHeader("Authorization", "Basic "
					+ base64EncodedCredentials);
			String json = "";
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("Creator_id", idU);
			jsonObject.accumulate("Receiver_id", idU);

			json = jsonObject.toString();
			StringEntity se = new StringEntity(json);
			httpPost.setEntity(se);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			HttpResponse httpResponse = httpclient.execute(httpPost);
			inputStream = httpResponse.getEntity().getContent();
			if (inputStream != null)
				result = true;
			else
				result = false;

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		return result;
	}

	public static void upDateProfile(String url) {
		InputStream inputStream = null;
		boolean result = false;

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPut httpPost = new HttpPut(url);
			String credentials = AirMailService.name + ":"
					+ AirMailService.password;
			String base64EncodedCredentials = Base64.encodeToString(
					credentials.getBytes(), Base64.NO_WRAP);
			httpPost.addHeader("Authorization", "Basic "
					+ base64EncodedCredentials);

			String json = "";
			JSONObject jsonObject = new JSONObject();
			AirMailService.profile.countMess++;
			jsonObject
					.accumulate("countMess", AirMailService.profile.countMess);
			// jsonObject.accumulate("id", AirMailService.profile.id);

			Log.e("AirMailService.profile.id", "" + AirMailService.profile.id);
			Log.e("AirMailService.profile.countMess", ""
					+ AirMailService.profile.countMess);

			json = jsonObject.toString();
			StringEntity se = new StringEntity(json);
			httpPost.setEntity(se);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			HttpResponse httpResponse = httpclient.execute(httpPost);
			inputStream = httpResponse.getEntity().getContent();
			if (inputStream != null)
				result = true;
			else
				result = false;

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

	}

	public static void upDateDialog(String url) {
		InputStream inputStream = null;
		boolean result = false;

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPut httpPost = new HttpPut(url);

			String credentials = AirMailService.name + ":"
					+ AirMailService.password;
			String base64EncodedCredentials = Base64.encodeToString(
					credentials.getBytes(), Base64.NO_WRAP);
			httpPost.addHeader("Authorization", "Basic "
					+ base64EncodedCredentials);

			String json = "";
			JSONObject jsonObject = new JSONObject();
			boolean newb = false;
			;
			for (int i = 0; i < AirMailService.myList.size(); i++) {
				if (Integer.parseInt(AirMailService.myList.get(i).get("id")) == AirMailService.currentIDDialog) {
					newb = !Boolean.parseBoolean(AirMailService.myList.get(i)
							.get("ForReceiver"));
					break;
				}
			}

			jsonObject.accumulate("ForReceiver", newb);

			AirMailService.currentIDDialog = 0;

			json = jsonObject.toString();
			StringEntity se = new StringEntity(json);
			httpPost.setEntity(se);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			HttpResponse httpResponse = httpclient.execute(httpPost);
			inputStream = httpResponse.getEntity().getContent();
			if (inputStream != null)
				result = true;
			else
				result = false;

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

	}

}
