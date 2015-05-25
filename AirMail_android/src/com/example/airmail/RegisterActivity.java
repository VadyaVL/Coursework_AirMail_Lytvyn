package com.example.airmail;

import com.aimail.providers.JSONParser;
import com.airmail.service.AirMailService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
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

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnRegOK:

			EditText eN = (EditText) findViewById(R.id.etRN);
			EditText eP = (EditText) findViewById(R.id.etRP);
			EditText eE = (EditText) findViewById(R.id.etRE);

			String name = eN.getText().toString();
			String pass = eP.getText().toString();
			String email = eE.getText().toString();
			
			
			
			if (name.length() != 0 && pass.length() != 0 && email.length() != 0) {
				String url = "http://"
						//+ AirMailService.name
						//+ ":"
						//+ AirMailService.password
						//+ "@"
						+ AirMailService.ip
						+ ":"
						+ AirMailService.port
						+ "/"
						+ AirMailService.urlKey
								.get(AirMailService.AirMailObjects.USER);
				
				JSONParser.POSTUser(name, email, pass, url);
				Intent intent = new Intent(RegisterActivity.this,
						LoginActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
			else{

				Toast.makeText(this, "Не вдалося зареєструватися!", 0);
			}
			break;
		}
	}
}
