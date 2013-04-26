package com.copilot.app;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class CoPilotMainActivity extends SlidingFragmentActivity implements
		OnItemClickListener {

	public static final String LOG_TAG = "CoPilotMainActivity";
	public static final int REQ_CODE_SPLASH = 0001;

	public static String KEY_AUTOMATED_RESPONSE = "auto_repsonse";
	public static String KEY_INCOMING_NUMBER = "incoming_number";
	public static int RC_MAIN_SWITCH = 7;

	private ImageButton mActiButton;

	// Custom Spinner
	private ListView spinnerList;
	private LinearLayout spinnerContainer;
	private boolean spinnerEnabled;
	private Button spinnerButton;
	private String spinnerFirstItem;
	private List<String> spinnerActiveList;

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
		mActiButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent splash = new Intent(CoPilotMainActivity.this,
						SplashActivity.class);
				splash.putExtra(KEY_AUTOMATED_RESPONSE, spinnerFirstItem);
				startActivity(splash);
			}
		});

		// Set up sliding menu
		SlidingMenu menu = getSlidingMenu();
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);

		getSupportActionBar().setIcon(R.drawable.launcher_maybe);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.v(LOG_TAG, "Pause called.");
	}

	/**
	 * Private CustomSpinner Methods
	 */
	private void initCustomSpinner() {

		String[] options = getResources().getStringArray(
				R.array.spinner_message_array);

		spinnerActiveList = new ArrayList<String>();

		for (String s : options)
			spinnerActiveList.add(s);

		spinnerFirstItem = spinnerActiveList.get(0);

		spinnerEnabled = false;
		spinnerButton = (Button) findViewById(R.id.spinner);
		spinnerButton.setText(spinnerFirstItem);
		spinnerContainer = (LinearLayout) findViewById(R.id.container);
		spinnerList = (ListView) findViewById(R.id.options);
		ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
				R.layout.custom_textview, spinnerActiveList);

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

		// Update spinnerList
		spinnerActiveList.add(spinnerFirstItem);
		spinnerActiveList.remove(pos);

		TextView t = (TextView) view.findViewById(R.id.listText);
		spinnerButton.setText(t.getText());
		spinnerFirstItem = t.getText().toString();
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
			toggle();
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
