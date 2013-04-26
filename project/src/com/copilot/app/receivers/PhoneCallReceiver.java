package com.copilot.app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.copilot.listeners.PhoneCallStateListener;

public class PhoneCallReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		PhoneCallStateListener phoneStateListener = new PhoneCallStateListener(context);
		telephonyManager.listen(phoneStateListener, PhoneCallStateListener.LISTEN_CALL_STATE);
	}

}
