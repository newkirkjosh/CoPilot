package com.copilot.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;

public class CoPilotMainActivity extends SherlockActivity {

	Button _launchButton;
	public static SharedPreferences mSharedPrefs;
	public static SharedPreferences.Editor mPrefsEditor;
	public static SlidingMenu mSlidingMenu;
	
	public static String KEY_AUTOMATED_RESPONSE = "auto_repsonse";
	public static String KEY_INCOMING_NUMBER = "incoming_number";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_co_pilot_main);
		
		mSharedPrefs = getPreferences(MODE_PRIVATE);
		mPrefsEditor = mSharedPrefs.edit();
		
		_launchButton = (Button) findViewById(R.id.button_launch_autoreply);
		_launchButton.setOnClickListener(startCoPilot);
		
		final ActionBar ab = getSupportActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setIcon(R.drawable.actionbar_home);
		
		// Set up sliding menu
		mSlidingMenu = new SlidingMenu(this);
		mSlidingMenu.setMode(SlidingMenu.LEFT);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		mSlidingMenu.setShadowWidth(15);
		mSlidingMenu.setBehindOffset(60);
		mSlidingMenu.setFadeDegree(0.35f);
		mSlidingMenu.setMenu(R.layout.slide_menu_list);
	}

	/*
	 * Handler that launches CoPilot once our button is clicked
	 */
	View.OnClickListener startCoPilot = new View.OnClickListener() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		public void onClick(View launchButton) {
			Intent intent = new Intent(launchButton.getContext(),
					ReceiveSMSActivity.class);
			startActivity(intent);
		}
	};

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
				mSlidingMenu.toggle();
				break;
			default:
				break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	/*
	 * Used to update a preference from other classes
	 */
	public static void updatePreferences(String key, String value){
		mPrefsEditor.putString(key, value).commit();
	}
	
	/*
	 * Used to clear the preferences or settings stored by the user
	 */
	public void clearPreferences(){
		mSharedPrefs = getPreferences(MODE_PRIVATE);
		mPrefsEditor = mSharedPrefs.edit();
		mPrefsEditor.clear();
		mPrefsEditor.commit();
	}
}
