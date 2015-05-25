package com.aimail.providers;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class Mess extends HashMap<String, String>  {
	
	public static final String DateSent = "DateSent";
	public static final String Text = "Text";
	public static final String id = "id";

	
	public Mess(JSONObject obj) throws JSONException {
		super();
		super.put(DateSent, obj.getString("DateSent"));
		super.put(Text, obj.getString("Text"));
		super.put(id, obj.getString("id"));
	}
}
