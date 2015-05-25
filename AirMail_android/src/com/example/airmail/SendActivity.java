package com.example.airmail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aimail.providers.JSONParser;
import com.airmail.service.AirMailService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SendActivity extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send, menu);
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
	public void onClick(View v) {
		Intent intent;
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnSend:
			// Отримуємо нове і в діалог, якщо ні то на головну

			if (AirMailService.currentIDDialog != 0
					&& AirMailService.messA != null) {

				AirMailService.messA.finish();

				String url = "http://"
						+ AirMailService.name
						+ ":"
						+ AirMailService.password
						+ "@"
						+ AirMailService.ip
						+ ":"
						+ AirMailService.port
						+ "/"
						+ AirMailService.urlKey
								.get(AirMailService.AirMailObjects.MESSAGE);
				EditText et = (EditText) findViewById(R.id.etMess);
				// Нада відправити
				JSONParser.POSTMess(et.getText().toString(),
						AirMailService.currentIDDialog,
						AirMailService.currentIDUser, url);

				url = "http://" + AirMailService.ip + ":" + AirMailService.port
						+ "/api/v1/profile/" + AirMailService.profile.id
						+ "/?format=json";

				JSONParser.upDateProfile(url);

				url = "http://" + AirMailService.ip + ":" + AirMailService.port
						+ "/api/v1/dialogue/" + AirMailService.currentIDDialog
						+ "/?format=json";

				JSONParser.upDateDialog(url);

				intent = new Intent(SendActivity.this, HeadActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				AirMailService.currentIDDialog = 0;
				AirMailService.messA = null;
				this.finish();

			} else {

				// Тут відправляємо нове. І отримуємо старе. Хоча ні. Не
				// отримуємо
				// Створили діалог,
				String query = "http://"
						+ AirMailService.ip
						+ ":"
						+ AirMailService.port
						+ "/"
						+ AirMailService.urlKey
								.get(AirMailService.AirMailObjects.DIALOG);

				JSONParser.POSTDialog(AirMailService.currentIDUser, query);
				// Створили повідомлення.

				query = "http://" + AirMailService.ip + ":"
						+ AirMailService.port + "/api/v1/dialogue1"
						+ "/?format=json&Creator_id="
						+ AirMailService.currentIDUser + "&Receiver_id="
						+ AirMailService.currentIDUser;

				JSONParser jp = new JSONParser();
				JSONObject dialogs = jp.getJson(query);
				JSONArray arr = null;
				try {
					 arr = dialogs.getJSONArray("objects");
				} catch (Exception e) {
					
				}
				
				String url = "http://"
						+ AirMailService.name
						+ ":"
						+ AirMailService.password
						+ "@"
						+ AirMailService.ip
						+ ":"
						+ AirMailService.port
						+ "/"
						+ AirMailService.urlKey
								.get(AirMailService.AirMailObjects.MESSAGE);
				EditText et = (EditText) findViewById(R.id.etMess);

				Log.e("url", arr.length()+"");
				try {
					JSONParser.POSTMess(et.getText().toString(), arr.getJSONObject(arr.length()-1).getInt("id"), AirMailService.currentIDUser, url);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Log.e("url", url);
				
				HeadActivity.head.finish();
				intent = new Intent(SendActivity.this, HeadActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				this.finish();
			}

			break;
		}
	}
}
