package com.example.airmail;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aimail.providers.Dialog;
import com.aimail.providers.JSONParser;
import com.aimail.providers.Mess;
import com.airmail.service.AirMailService;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.view.Menu;
import android.view.MenuItem;

public class MessActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String messURL; // Одночасно залогінюємося і беремо профайл юзера

		// Тут перевірка залогінення
		messURL = "http://"
				+ AirMailService.ip
				+ ":"
				+ AirMailService.port
				+ "/"
				+ AirMailService.urlKey
						.get(AirMailService.AirMailObjects.MESSAGE)
				+ "&Dialogue_id=" + AirMailService.currentIDDialog;

		JSONParser jsp = new JSONParser();
		JSONObject dialogs = jsp.getJson(messURL);
		try {
			JSONArray arr = dialogs.getJSONArray("objects");

			AirMailService.myListMess = new ArrayList<Mess>();
			for (int i = 0; i < arr.length(); i++) {
				AirMailService.myListMess.add(new Mess(arr.getJSONObject(i)));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.e("Lenght", AirMailService.myListMess.size() + "");

		ListAdapter adapter = new SimpleAdapter(this,
				AirMailService.myListMess, R.layout.row,
				new String[] { Mess.Text }, new int[] { R.id.name });
		setListAdapter(adapter);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case 1:
			intent = new Intent(this, SendActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			AirMailService.messA = this;
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// Создание меню
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Добавляем пункты меню
		menu.add(Menu.NONE, 1, Menu.NONE, "Відповісти").setAlphabeticShortcut(
				'o');
		return (super.onCreateOptionsMenu(menu));
	}
}
