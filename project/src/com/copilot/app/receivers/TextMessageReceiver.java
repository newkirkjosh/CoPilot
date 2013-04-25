package com.copilot.app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.copilot.app.SplashActivity;

public class TextMessageReceiver extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) { 
		
		Bundle bundle = intent.getExtras();
		String phoneNum = "";

		Object[] messages = (Object[]) bundle.get("pdus");
		SmsMessage[] sms = new SmsMessage[messages.length];

		for (int n = 0; n < messages.length; n++) {
			sms[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
		}

		for (SmsMessage msg : sms) {
			
			if( phoneNum == "" ){
				phoneNum = msg.getOriginatingAddress();
//				CoPilotMainActivity.updateStringPreference(CoPilotMainActivity.KEY_INCOMING_NUMBER, phoneNum);
//				Log.d("phoneNumChanged", phoneNum + " phoneNum value");
			}
			
			SplashActivity.send(phoneNum);
//			ReceiveSMSActivity.updateMessageBox("\nFrom: "
//					+ msg.getOriginatingAddress() + "\n" + "Message: "
//					+ msg.getMessageBody() + "\n");
		}
	}
}
