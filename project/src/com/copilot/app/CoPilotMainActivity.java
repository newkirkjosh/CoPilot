package com.copilot.app;

import java.util.ArrayList;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.copilot.app.database.CoPilotDatabase;
import com.copilot.app.models.User;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class CoPilotMainActivity extends SlidingFragmentActivity implements
		AdapterView.OnItemClickListener {

	public static final String LOG_TAG = "CoPilotMainActivity";
	public static final int GOOGLE_SELECT_ACCOUNT_REQUEST_CODE = 14;
	public static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 22;
	public static final int REQ_CODE_SPLASH = 0001;

	public static String KEY_AUTOMATED_RESPONSE = "auto_repsonse";
	public static String KEY_INCOMING_NUMBER = "incoming_number";
	public static int RC_MAIN_SWITCH = 7;

	private ImageButton mActiButton;

	private User currentUser;

	// Custom Spinner
	private ListView spinnerList;
	private LinearLayout spinnerContainer;
	private boolean spinnerEnabled;
	private Button spinnerButton;
	private String spinnerFirstItem;
	private ArrayList<String> spinnerActiveList;

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

		initAccount();
	}

	@Override
	protected void onResume() {
		super.onResume();
		checkPlayServices();
	}

	/**
	 * Initialize Account
	 */

	private void initAccount() {
		if (!checkForExistingAccount()) {
			Intent intent = AccountPicker.newChooseAccountIntent(null, null,
					new String[] { GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE }, true,
					null, null, null, null);

			startActivityForResult(intent, GOOGLE_SELECT_ACCOUNT_REQUEST_CODE);
		}
	}

	private boolean checkForExistingAccount() {
		AccountManager am = AccountManager.get(getApplicationContext());
		Account[] accounts = am
				.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
		boolean valid = false;

		for (Account account : accounts) {
			User user = CoPilotDatabase.getInstance(this).getUserFromDatabase(
					account.name);
			if (user != null) {
				valid = true;
				currentUser = user;
				break;
			}
		}

		return valid;
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		if (requestCode == GOOGLE_SELECT_ACCOUNT_REQUEST_CODE) {
			if (resultCode == RESULT_CANCELED) {
				Intent intent = AccountPicker.newChooseAccountIntent(null,
						null,
						new String[] { GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE },
						true, null, null, null, null);

				startActivityForResult(intent,
						GOOGLE_SELECT_ACCOUNT_REQUEST_CODE);
			} else {
				String mAccountEmail = data
						.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
				User user = new User(mAccountEmail);
				CoPilotDatabase.getInstance(this).addUser(user);
			}
		} else if (resultCode == REQUEST_CODE_RECOVER_PLAY_SERVICES) {
			if (resultCode == RESULT_CANCELED) {
				Toast.makeText(
						this,
						"Google Play Services must be installed and up-to-date.",
						Toast.LENGTH_SHORT).show();
				finish();
			}
			return;
		}
	}

	/**
	 * Google Play Services
	 */

	private boolean checkPlayServices() {
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (status != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
				showErrorDialog(status);
			} else {
				Toast.makeText(this, "This device is not supported.",
						Toast.LENGTH_LONG).show();
				finish();
			}
			return false;
		}
		return true;
	}

	void showErrorDialog(int code) {
		GooglePlayServicesUtil.getErrorDialog(code, this,
				REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
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

		spinnerActiveList.add("Add New");

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

		if (pos == spinnerActiveList.size() - 1) {
			showAlertDialog();
		} else {
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

	}

	private void showAlertDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Replace a messege");
		alert.setMessage("This will replace the first message in the list");
		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				spinnerActiveList.add(0, input.getText().toString());
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});

		alert.show();
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
