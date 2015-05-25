package com.example.airmail;

import java.util.ArrayList;
import java.util.List;

import com.airmail.service.AirMailService;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MenuFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		int position = getArguments().getInt("position");
		Intent intent;
		String[] options = getResources().getStringArray(R.array.Options);
		View v = null;

		if (position == 0) {
			intent = new Intent(HeadActivity.head.getApplicationContext(),
					DialogActivity.class);
			//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			//		| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		} else if (position == 1) {
			intent = new Intent(HeadActivity.head.getApplicationContext(),
					SendActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			//HeadActivity.head.finish();
		} else if (position == 2) {
			AirMailService.clear();
			intent = new Intent(HeadActivity.head.getApplicationContext(),
					LoginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			HeadActivity.head.finish();
		}

		getActivity().getActionBar().setTitle("Μενώ");

		return v;
	}
}
