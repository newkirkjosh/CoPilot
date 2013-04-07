package com.copilot.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class CoPilotMainActivity extends SlidingFragmentActivity implements
		OnItemClickListener {

	public static final String LOG_TAG = "CoPilotMainActivity";

	public static String KEY_AUTOMATED_RESPONSE = "auto_repsonse";
	public static String KEY_INCOMING_NUMBER = "incoming_number";
	public static String KEY_BOOL_RESPONSE = "bool_switch";
	public static int RC_MAIN_SWITCH = 7;

	private ImageButton mActiButton;

	// Custom Spinner
	private ListView spinnerList;
	private LinearLayout spinnerContainer;
	private boolean spinnerEnabled;
	private Button spinnerButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.copilot_main);
		setBehindContentView(R.layout.sliding_menu_frame);
		initCustomSpinner();

		FragmentTransaction t = this.getSupportFragmentManager()
				.beginTransaction();
		SherlockListFragment mFrag = new MenuFragment();
		t.replace(R.id.sliding_menu_frame, mFrag);
		t.commit();

		mActiButton = (ImageButton) findViewById(R.id.acti_button);

		// Set up sliding menu
		SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);

		getSupportActionBar().setIcon(R.drawable.ic_launcher);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("Resume", "called");
	}

	/**
	 * Private CustomSpinner Methods
	 */
	private void initCustomSpinner() {
		spinnerEnabled = false;
		spinnerButton = (Button) findViewById(R.id.spinner);
		spinnerButton.setText(getResources().getStringArray(
				R.array.spinner_message_array)[0]);
		spinnerContainer = (LinearLayout) findViewById(R.id.container);
		spinnerList = (ListView) findViewById(R.id.options);
		ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
				R.layout.custom_textview, getResources().getStringArray(
						R.array.spinner_message_array));

		spinnerList.setAdapter(adp);
		spinnerList.setOnItemClickListener(this);
	}

	private void closeList() {
		spinnerButton.setSelected(false);
		spinnerContainer.setBackgroundColor(Color.TRANSPARENT);
		spinnerList.setVisibility(View.GONE);
		spinnerEnabled = false;
	}

	private void openList() {
		spinnerButton.setSelected(true);
		spinnerContainer.setBackgroundResource(R.drawable.border);
		spinnerList.setVisibility(View.VISIBLE);
		spinnerEnabled = true;
	}

	public void buttonPressed(View view) {
		if (spinnerEnabled)
			closeList();
		else
			openList();
	}

	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		TextView t = (TextView) view.findViewById(R.id.listText);
		spinnerButton.setText(t.getText());
		spinnerButton.setSelected(false);
		spinnerContainer.setBackgroundColor(Color.BLACK);
		spinnerList.setVisibility(View.GONE);
		spinnerEnabled = false;
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
