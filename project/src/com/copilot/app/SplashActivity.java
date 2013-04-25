package com.copilot.app;

import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.copilot.app.views.DraggableView;

public class SplashActivity extends SherlockActivity {

	public static final String LOG_TAG = "SplashActivity";
	private static final String TYPEFACE_ROBOTO = "/fonts/Roboto-Light.ttf";
	private DraggableView mHiddenView;
	private TextView mResultText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_layout);
		initDraggableView();

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
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
			Log.d(LOG_TAG, "ActionBar home pressed");
			finish();
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Draggable View Methods
	 */

	public void initDraggableView() {
		DraggableView mView = (DraggableView) findViewById(R.id.drag_view);
		mView.setBackgroundResource(R.drawable.big_logo);

		mHiddenView = (DraggableView) findViewById(R.id.drag_view_hidden);

		mResultText = (TextView) findViewById(R.id.drag_result_text);
		mResultText.setOnDragListener(new View.OnDragListener() {
			public boolean onDrag(View v, DragEvent event) {
				final int action = event.getAction();
				switch (action) {
				case DragEvent.ACTION_DRAG_STARTED: {
					// Bring up a fourth draggable view on the fly. Note that it
					// is properly notified about the ongoing drag, and lights
					// up
					// to indicate that it can handle the current content.
					mHiddenView.setVisibility(View.VISIBLE);
				}
					break;

				case DragEvent.ACTION_DRAG_ENDED: {
					// Hide the hidden view again
					mHiddenView.setVisibility(View.INVISIBLE);

					// Report the drop/no-drop result to the user
					final boolean dropped = event.getResult();
					if (dropped) {
						finish();// bad but ok for now
					}

				}
					break;
				}
				return false;
			}
		});
	}

}