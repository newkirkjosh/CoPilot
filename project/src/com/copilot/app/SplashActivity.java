package com.copilot.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.copilot.app.receivers.TextMessageReceiver;
import com.copilot.app.views.DraggableView;

public class SplashActivity extends SherlockActivity {

	public static final String LOG_TAG = "SplashActivity";
	public static String KEY_AUTOMATED_RESPONSE = "auto_repsonse";
	private DraggableView mHiddenView;
	private TextView mResultText;
	private static Context mContext;
	private static Bundle mBundle;
	private TextMessageReceiver mBroadcastReceiver;
	private final Handler mHandler;
	private boolean mActive;
	private SimpleDateFormat sdf;
	private TextView mTimeLabel;

	private final Runnable mRunnable = new Runnable() {
		public void run() {
			if (mActive) {
				if (mTimeLabel != null) {
					mTimeLabel.setText(getTime());
				}
				mHandler.postDelayed(mRunnable, 1000);

			}
		}
	};

	public SplashActivity() {
		mHandler = new Handler();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_layout);

		mTimeLabel = (TextView) findViewById(R.id.splash_clock);
		mContext = getApplicationContext();
		mBundle = getIntent().getExtras();
		mBroadcastReceiver = new TextMessageReceiver();
		mActive = true;
		mHandler.post(mRunnable);

		initDraggableView();
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

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mBroadcastReceiver);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Restore preferences
		SharedPreferences settings = getSharedPreferences("com.copilot.app", 0);

		int timeFormat = settings.getInt("timeFormat", 0);
		if (timeFormat == 1)
			sdf = new SimpleDateFormat("KK:mm", Locale.US);
		else
			sdf = new SimpleDateFormat("hh:mm", Locale.US);

		registerReceiver(mBroadcastReceiver, new IntentFilter(
				"android.provider.Telephony.SMS_RECEIVED"));
	}

	private String getTime() {
		return sdf.format(new Date(System.currentTimeMillis()));
	}

	/**
	 * 
	 * Text-Message Handling
	 * 
	 */

	public static void send(String phoneNum) {
		Log.v(LOG_TAG, "send hit");

		String response = mBundle.getString(KEY_AUTOMATED_RESPONSE);

		if (phoneNum.length() > 0 && response.length() > 0) {
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage(phoneNum, null, response, null, null);
			Log.v(LOG_TAG, "Message sent");
		} else {
			// display that message could not be sent due to constraints
			Toast.makeText(
					mContext,
					"Message could not be sent because either the number could not be reached or the message was null.",
					Toast.LENGTH_SHORT).show();
		}
	}
}
