package com.copilot.listeners;

import java.lang.reflect.Method;

import android.content.Context;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

public class PhoneCallStateListener extends PhoneStateListener {

	private static final String LOG_TAG = "PhoneListener";

	private Context context;

	public PhoneCallStateListener(Context context) {
		this.context = context;
	}

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {

		switch (state) {
		case TelephonyManager.CALL_STATE_RINGING:
			Log.v(LOG_TAG, "RINGING, number: " + incomingNumber);

			AudioManager audioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
			audioManager.setStreamMute(AudioManager.STREAM_RING, true);

			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			try {
				Class clazz = Class.forName(telephonyManager.getClass()
						.getName());
				Method method = clazz.getDeclaredMethod("getITelephony");
				method.setAccessible(true);
				ITelephony telephonyService = (ITelephony) method
						.invoke(telephonyManager);
				telephonyService.silenceRinger();
				telephonyService.endCall();

			} catch (Exception e) {
				Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
			}

			// Turn off the mute
			audioManager.setStreamMute(AudioManager.STREAM_RING, false);
			break;
		default:
			break;
		}

		super.onCallStateChanged(state, incomingNumber);
	}
}
