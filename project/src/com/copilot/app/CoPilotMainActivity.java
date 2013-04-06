package com.copilot.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class CoPilotMainActivity extends SlidingFragmentActivity {

	public static final String LOG_TAG = "CoPilotMainActivity";

	public static String KEY_AUTOMATED_RESPONSE = "auto_repsonse";
	public static String KEY_INCOMING_NUMBER = "incoming_number";
	public static String KEY_BOOL_RESPONSE = "bool_switch";
	public static int RC_MAIN_SWITCH = 7;

	private ImageButton mActiButton;
	Spinner mMsgPicker;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.copilot_main);
		setBehindContentView(R.layout.sliding_menu_list);

		if (findViewById(R.id.fragment_container) != null) {

			if (savedInstanceState != null) {
				return;
			}

			Fragment firstFrag = new Fragment();
			firstFrag.setArguments(getIntent().getExtras());

			getSupportFragmentManager().beginTransaction()
					.add(R.id.fragment_container, firstFrag).commit();
		}

		mMsgPicker = (Spinner) findViewById(R.id.spinner_message_picker);
		mActiButton = (ImageButton) findViewById(R.id.acti_button);

		addSlidingItems();

		// Set up sliding menu
		SlidingMenu menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.setMenu(R.layout.sliding_menu_list);

		getSupportActionBar().setIcon(R.drawable.ic_launcher);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("Resume", "called");
	}

	private void addSlidingItems() {
		ListView slidingList = (ListView) findViewById(R.id.listview_slide_menu);
		String[] slideOptions = getResources().getStringArray(
				R.array.slide_menu_options);
		ArrayAdapter<String> slideAdapter = new ArrayAdapter<String>(this,
				R.layout.slide_menu_item, R.id.slide_item_title, slideOptions);
		slidingList.setAdapter(slideAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_co_pilot_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			Log.d("actionbarhome", "pressed");
			toggle();
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
