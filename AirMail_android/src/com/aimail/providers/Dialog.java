package com.aimail.providers;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class Dialog extends HashMap<String, String> {

	public static final String CountMess = "CountMess";
	public static final String id = "id";
	public static final String Established = "Established";
	public static final String ForReceiver = "ForReceiver";

	public Dialog(JSONObject obj) throws JSONException {
		super();
		super.put(CountMess, obj.getString("CountMess"));
		super.put(id, obj.getString("id"));
		super.put(Established, obj.getString("Established"));
		super.put(ForReceiver, obj.getString("ForReceiver"));
	}



}
