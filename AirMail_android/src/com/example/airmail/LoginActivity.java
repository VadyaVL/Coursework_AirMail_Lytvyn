package com.example.airmail;

import android.os.StrictMode;
import com.airmail.service.AirMailService;
import com.airmail.service.AirMailService.AirMailObjects;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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

	@SuppressLint("ShowToast")
	@Override
	public void onClick(View v) {
		Intent intent;
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:

			EditText eName = (EditText) findViewById(R.id.etName);
			EditText ePass = (EditText) findViewById(R.id.etPass);

			AirMailService.name = eName.getText().toString();
			AirMailService.password = ePass.getText().toString();

			Log.d("Login", AirMailService.name);
			Log.d("Password", AirMailService.password);
			try {
				AirMailService.login();

				if (AirMailService.logged) {
					intent = new Intent(LoginActivity.this, HeadActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					this.finish();
				} else {
					Toast.makeText(this, "Не вдалося залогінитсь!", 0);
				}
			} catch (Exception e) {
				Toast.makeText(this, "Не вдалося залогінитсь!", 0);
			}
			break;
		case R.id.button2:
			intent = new Intent(LoginActivity.this, RegisterActivity.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
			// Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;
		}
	}
}
