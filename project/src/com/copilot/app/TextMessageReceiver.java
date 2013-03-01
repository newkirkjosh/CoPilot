package com.copilot.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;

/*
 * TODO:  SmsMessage class is deprecated...we need to fix this
 */
public class TextMessageReceiver extends BroadcastReceiver {
	
	public void onReceive(Context context, Intent intent)
	{
		Bundle bundle=intent.getExtras();
		
		Object[] messages=(Object[])bundle.get("pdus");
		SmsMessage[] sms=new SmsMessage[messages.length];
		
		for(int n=0;n<messages.length;n++){
			sms[n]=SmsMessage.createFromPdu((byte[]) messages[n]);
		}
		
		for(SmsMessage msg:sms){
			ReceiveSMSActivity.updateMessageBox("\nFrom: "+msg.getOriginatingAddress()+"\n"+
					"Message: "+msg.getMessageBody()+"\n");
		}
	}
}
