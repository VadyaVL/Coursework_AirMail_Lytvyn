package com.aimail.providers;

import org.json.JSONException;
import org.json.JSONObject;

//http://admin:1111@192.168.1.119:8000/api/v1/profile/?format=json

public class ProfileProvider {
	
	public String avatarUrl;
	public int countDialog, countMess, id, karma;
		
	public ProfileProvider(JSONObject obj) throws JSONException{
		avatarUrl = obj.getString("avatar");
		countDialog = Integer.parseInt(obj.getString("countDialog"));
		countMess = Integer.parseInt(obj.getString("countMess"));
		id = Integer.parseInt(obj.getString("id"));
		karma = Integer.parseInt(obj.getString("karma"));
	}
}
