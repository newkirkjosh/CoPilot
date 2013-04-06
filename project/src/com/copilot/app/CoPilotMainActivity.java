package com.copilot.app;

import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class CoPilotMainActivity extends SlidingFragmentActivity {

	Button _launchButton;
	public static SharedPreferences mSharedPrefs;
	public static SharedPreferences.Editor mPrefsEditor;
	public static SlidingMenu mSlidingMenu;

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
		setBehindContentView(R.layout.slide_menu_list);

		mSharedPrefs = getPreferences(MODE_PRIVATE);
		mPrefsEditor = mSharedPrefs.edit();
		clearPreferences();

		mMsgPicker = (Spinner) findViewById(R.id.spinner_message_picker);
		mActiButton = (ImageButton) findViewById(R.id.acti_button);

		setSwitchProperties();

		final ActionBar ab = getSupportActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setIcon(R.drawable.actionbar_home);
		addSlidingItems();

		// Set up sliding menu
		mSlidingMenu = new SlidingMenu(this);
		mSlidingMenu.setMode(SlidingMenu.LEFT);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		mSlidingMenu.setBehindWidth(240);
		mSlidingMenu.setBehindOffset(50);
		mSlidingMenu.setMenu(R.layout.slide_menu_list);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setSwitchProperties();
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

	private void setSwitchProperties() {

		boolean switchCheck = mSharedPrefs.getBoolean(KEY_BOOL_RESPONSE, true);
		Log.d("properties", switchCheck + " is the current setting");

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

	/*
	 * Used to update a preference from other classes
	 */
	public static void updateStringPreference(String key, String value) {
		mPrefsEditor.putString(key, value).commit();
	}

	public static void updateBooleanPreference(String key, boolean value) {
		mPrefsEditor.putBoolean(key, value).commit();
	}

	/*
	 * Used to clear the preferences or settings stored by the user
	 */
	public void clearPreferences() {
		mSharedPrefs = getPreferences(MODE_PRIVATE);
		mPrefsEditor = mSharedPrefs.edit();
		mPrefsEditor.clear();
		mPrefsEditor.commit();
		updateBooleanPreference(KEY_BOOL_RESPONSE, false);
	}
	
	SpinnerAdapter mainAdapter = new SpinnerAdapter() {
		
		public void unregisterDataSetObserver(DataSetObserver observer) {
			
		}
		
		public void registerDataSetObserver(DataSetObserver observer) {
			
		}
		
		public boolean isEmpty() {
			return false;
		}
		
		public boolean hasStableIds() {
			return false;
		}
		
		public int getViewTypeCount() {
			return 0;
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			View main = null;
			
			main = (Spinner)findViewById(R.id.spinner_message_picker);
			
			return main;
		}
		
		public int getItemViewType(int position) {
			return 0;
		}
		
		public long getItemId(int position) {
			return position;
		}
		
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
	};
}
