package com.aimail.providers;

import org.json.JSONException;
import org.json.JSONObject;

public class InfoProvider {

	int CountDialog, CountUser, id;

	public InfoProvider(JSONObject obj) throws JSONException {
		CountDialog = Integer.parseInt(obj.getString("CountDialog"));
		CountUser = Integer.parseInt(obj.getString("CountUser"));
		id = Integer.parseInt(obj.getString("id"));
	}
}
