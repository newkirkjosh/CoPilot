package com.copilot.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.actionbarsherlock.view.Menu;

public class SettingsActivity extends CoPilotMainActivity {

	public static final String LOG_TAG = "SettingsActivity";

	private ListView settingsList;
	private int timeFormat;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.copilot_settings_activity);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		settingsList = (ListView) findViewById(R.id.settingsList);
		String[] settings = getResources()
				.getStringArray(R.array.settings_list);

		ArrayAdapter<String> settingsAdapter = new ArrayAdapter<String>(this,
				R.layout.settings_item, R.id.settingsItem, settings);
		settingsList.setAdapter(settingsAdapter);

		settingsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch (position) {
				case 0:
					showTimeDialog();
					break;
				default:
					break;
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_co_pilot_main, menu);
		return true;
	}

	private void showTimeDialog() {
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		View dialog_layout = getLayoutInflater().inflate(
				R.layout.settings_time_dialog, null);

		RadioGroup radioGroup = (RadioGroup) dialog_layout
				.findViewById(R.id.radioGroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.normal)
					timeFormat = 0;
				else
					timeFormat = 1;
			}
		});

		alertDialog.setTitle("Time");
		alertDialog.setView(dialog_layout);

		alertDialog.setButton("Save", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		alertDialog.show();
	}

}
