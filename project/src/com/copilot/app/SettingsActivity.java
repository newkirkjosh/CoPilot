package com.copilot.app;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;

public class SettingsActivity extends CoPilotMainActivity {

	public static final String LOG_TAG = "SettingsActivity";

	private ListView settingsList;

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
				android.R.layout.simple_list_item_1, settings);
		settingsList.setAdapter(settingsAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_co_pilot_main, menu);
		return true;
	}
}
