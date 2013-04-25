package com.copilot.app;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.copilot.app.adapters.CoPilotIncidentAdapter;
import com.copilot.app.camera.CameraManager;

/**
 * IncidentActivity
 * 
 * Represents a form to be used when the driver has encountered an accident and
 * would like to document and record the scene.
 * 
 * @author Dejan Ristic
 * 
 */

public class IncidentActivity extends SherlockActivity implements
		OnGroupExpandListener {

	public static final String LOG_TAG = "IncidentActivity";

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	private ExpandableListView mExpandableList;
	private CoPilotIncidentAdapter mAdapter;

	/**
	 * Activity Methods
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.copilot_incident_activity);
		setupUI(findViewById(R.id.parent));
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mExpandableList = (ExpandableListView) findViewById(R.id.expandable_list);
		mExpandableList.setOnGroupExpandListener(this);
		mAdapter = new CoPilotIncidentAdapter(this, getResources()
				.getStringArray(R.array.incident_group_titles), this);
		mExpandableList.setAdapter(mAdapter);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
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
			finish();
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * OnGroupExpand Listener for ExpandableListView
	 */

	@Override
	public void onGroupExpand(int groupPosition) {

		int len = mAdapter.getGroupCount();

		for (int i = 0; i < len; i++) {
			if (i != groupPosition) {
				mExpandableList.collapseGroup(i);
			}
		}
	}

	/**
	 * OnActivityResult from launched camera intent.
	 * 
	 * Will check whether the picture was selected from the camera or gallery.
	 * Those are the only two options. If a photo is chosen from the gallery
	 * that is NOT hosted on the device a toast will notify the user and no
	 * image will replace the ImageButton that was pressed.
	 */

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {

				LinearLayout parent = (LinearLayout) findViewById(R.id.parent);
				ImageButton img = (ImageButton) parent.findViewWithTag(mAdapter
						.getMostRecentImageButtonTag());

				BitmapDrawable bitmap = null;

				if (data == null) {
					// from camera

					bitmap = new BitmapDrawable(getResources(),
							CameraManager.retrieveImageFromPath());
					CameraManager.getInstance(this, IncidentActivity.this);

				} else {
					// from gallery

					bitmap = new BitmapDrawable(
							BitmapFactory.decodeFile(getPath(data.getData())));

				}

				if (bitmap.getBitmap() == null) {
					bitmap = new BitmapDrawable(BitmapFactory.decodeResource(
							getResources(), R.drawable.camera));
					Toast.makeText(
							this,
							"Picture must be from your phone, make sure you're not chosing from an online source your phone is linked to",
							Toast.LENGTH_LONG).show();
				}

				img.setBackgroundDrawable(bitmap);

			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Request Canceled", Toast.LENGTH_LONG)
						.show();
			}
		}

	}

	/**
	 * Private Methods specific to {@link IncidentActivity}.
	 */

	private String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	private void setupUI(View view) {

		// Set up touch listener for non-text box views to hide keyboard.
		if (!(view instanceof EditText)) {

			view.setOnTouchListener(new OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					hideSoftKeyboard(IncidentActivity.this);
					return false;
				}

			});
		}

		// If a layout container, iterate over children and seed recursion.
		if (view instanceof ViewGroup) {

			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

				View innerView = ((ViewGroup) view).getChildAt(i);

				setupUI(innerView);
			}
		}
	}

	private void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
				.getWindowToken(), 0);
	}
}
