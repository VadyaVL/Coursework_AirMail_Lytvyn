package com.airmail.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aimail.providers.Dialog;
import com.aimail.providers.InfoProvider;
import com.aimail.providers.JSONParser;
import com.aimail.providers.Mess;
import com.aimail.providers.ProfileProvider;
import com.example.airmail.MessActivity;

import android.util.Log;

public class AirMailService {

	public static String name, password; // User data
	public static String ip, port; // Server data
	public static boolean logged; // Для інформування успішного залогінення
	public static Map<AirMailObjects, String> urlKey = new HashMap<AirMailObjects, String>();
	public static ProfileProvider profile = null;
	public static InfoProvider information = null;
	public static ArrayList<Dialog> myList = new ArrayList<Dialog>();
	public static ArrayList<Mess> myListMess = new ArrayList<Mess>();
	public static int currentIDDialog = 0;
	public static int currentIDUser = 0;

	public static MessActivity messA = null;

	public static enum AirMailObjects {
		DIALOG, MESSAGE, USER, PROFILE, INFORMATION
	}

	private static String[] urls = new String[] { "api/v1/dialogue/",
			"api/v1/message/", "api/v1/user/", "api/v1/profile/",
			"api/v1/information/" };
	private static String param = "?format=json";

	public static void clear() {
		AirMailService.profile = null;
		AirMailService.logged = false;
		AirMailService.information = null;
		AirMailService.myList = new ArrayList<Dialog>();
		ArrayList<Mess> myListMess = new ArrayList<Mess>();
		currentIDDialog = 0;
		currentIDUser = 0;
	}

	static {
		ip = "192.168.1.119";
		port = "8000";

		name = "admin";
		password = "1111";

		urlKey.put(AirMailObjects.DIALOG, urls[0] + param);
		urlKey.put(AirMailObjects.MESSAGE, urls[1] + param);
		urlKey.put(AirMailObjects.USER, urls[2] + param);
		urlKey.put(AirMailObjects.PROFILE, urls[3] + param);
		urlKey.put(AirMailObjects.INFORMATION, urls[4] + param);
	}

	public static void login() {
		boolean ok = false;
		String loginURL, profileURL; // Одночасно залогінюємося і беремо профайл
										// юзера

		// Тут перевірка залогінення
		loginURL = "http://"
				+ AirMailService.ip
				+ ":"
				+ AirMailService.port
				+ "/"
				+ AirMailService.urlKey
						.get(AirMailService.AirMailObjects.PROFILE);

		profileURL = "http://"
				+ AirMailService.ip
				+ ":"
				+ AirMailService.port
				+ "/"
				+ AirMailService.urlKey
						.get(AirMailService.AirMailObjects.INFORMATION);

		JSONParser jsp = new JSONParser();
		JSONObject jsO = jsp.getJson(loginURL);
		JSONObject inf = jsp.getJson(profileURL);
		JSONArray arr;
		try {
			arr = jsO.getJSONArray("objects");
			profile = new ProfileProvider(arr.getJSONObject(0));

			arr = inf.getJSONArray("objects");
			information = new InfoProvider(arr.getJSONObject(0));
			ok = true;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (ok) {
			logged = true;
			Log.d("Логін", "Залогінено");

			String u = "http://"
					+ AirMailService.ip
					+ ":"
					+ AirMailService.port
					+ "/"
					+ AirMailService.urlKey
							.get(AirMailService.AirMailObjects.USER) + "&username=" + name;
			jsp = new JSONParser();
			jsO = jsp.getJson(u);
			try {
				arr = jsO.getJSONArray("objects");
				currentIDUser = arr.getJSONObject(0).getInt("id");
				Log.e("id", currentIDUser + "");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			logged = false;
			Log.d("Логін", "Не залогінено");
		}

	}

	public void AirMailService() {

	}
}
