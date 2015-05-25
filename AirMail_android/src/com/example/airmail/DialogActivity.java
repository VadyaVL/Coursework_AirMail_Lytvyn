package com.example.airmail;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aimail.providers.Dialog;
import com.aimail.providers.JSONParser;
import com.airmail.service.AirMailService;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class DialogActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String dialogsURL; // Одночасно залогінюємося і беремо профайл юзера

		// Тут перевірка залогінення
		dialogsURL = "http://"
				+ AirMailService.ip
				+ ":"
				+ AirMailService.port
				+ "/"
				+ AirMailService.urlKey
						.get(AirMailService.AirMailObjects.DIALOG);

		JSONParser jsp = new JSONParser();
		JSONObject dialogs = jsp.getJson(dialogsURL);
		try {
			JSONArray arr = dialogs.getJSONArray("objects");

			AirMailService.myList = new ArrayList<Dialog>();
			for (int i = 0; i < arr.length(); i++) {
				AirMailService.myList.add(new Dialog(arr.getJSONObject(i)));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		Log.e("Lenght", AirMailService.myList.size() + "");

		ListAdapter adapter = new SimpleAdapter(this, AirMailService.myList,
				R.layout.row, new String[] { Dialog.Established, Dialog.id },
				new int[] { R.id.name, R.id.ids });
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dialog, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		TextView tw = (TextView) v.findViewById(R.id.ids);
		AirMailService.currentIDDialog = (int) Integer.parseInt(tw.getText().toString());
		Log.e("AirMailService.currentIDDialog",""+AirMailService.currentIDDialog);
		Intent intent = new Intent(this, MessActivity.class);
		// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
		// | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);

	}
}
