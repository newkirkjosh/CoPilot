package com.copilot.app;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import android.widget.Toast;

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

public class IncidentActivity extends CoPilotMainActivity implements
		OnGroupExpandListener {

	public static final String LOG_TAG = "IncidentActivity";

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	private ExpandableListView mExpandableList;
	private CoPilotIncidentAdapter mAdapter;

	/*
	 * Helper Class to save state of adapter resources we need when the activity
	 * restarts due to configuration changes
	 */
	class Resources {
		ImageButton lastPressedImageButton = CoPilotIncidentAdapter.lastImageButton;
		View photosView = CoPilotIncidentAdapter.photos;
	}

	/**
	 * Activity Methods
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
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

		final Resources data = (Resources) getLastCustomNonConfigurationInstance();
		if (data != null) {
			CoPilotIncidentAdapter.photos = data.photosView;
			CoPilotIncidentAdapter.lastImageButton = data.lastPressedImageButton;

		}

	}

	@Override
	public Object onRetainCustomNonConfigurationInstance() {
		// restore all your data here
		final Resources data = new Resources();
		return data;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
		super.onActivityResult(requestCode, resultCode, data);
		Log.v("OnActivityResult", " OnActivityResult ");
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {

				CameraManager cameraManager = CameraManager.getInstance(this,
						IncidentActivity.this);
				ImageButton img = CoPilotIncidentAdapter.lastImageButton;

				BitmapDrawable bitmap = null;

				if (data == null) {
					// from camera

					bitmap = new BitmapDrawable(getResources(),
							cameraManager.retrieveImageFromPath());

				} else {
					// from gallery
					try {
						bitmap = new BitmapDrawable(
								cameraManager.decodeUri(data.getData()));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}

				}

				if (bitmap.getBitmap() == null) {
					bitmap = new BitmapDrawable(BitmapFactory.decodeResource(
							getResources(), R.drawable.camera));
					Toast.makeText(
							this,
							"Picture must be from your phone, make sure you're not chosing from an online source your phone is linked to",
							Toast.LENGTH_LONG).show();
				}

				if (bitmap != null) {
					img.setBackgroundDrawable(bitmap);
				}

			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Request Canceled", Toast.LENGTH_LONG)
						.show();
			}
		}

	}

	/**
	 * Private Methods specific to {@link IncidentActivity}.
	 */

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
