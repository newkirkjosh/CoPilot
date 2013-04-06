package com.copilot.app;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ReceiveSMSActivity extends CoPilotMainActivity {

	static TextView messageBox;
	static TextView autoMessageBox;
	static Context mContext;
	static Context mPackageContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receive_sms_activity);

		mContext = getBaseContext();
		mPackageContext = getApplicationContext();

		messageBox = (TextView) findViewById(R.id.messages);
		messageBox.setText("Messages will go in here\n");
		messageBox.setClickable(false);
		
		autoMessageBox = (TextView)findViewById(R.id.auto_msg_box);
		autoMessageBox.setHint("Enter response here!");

//		Button sendButton = (Button) findViewById(R.id.sendButton);
//		sendButton.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				mPrefsEditor.putString(KEY_AUTOMATED_RESPONSE,
//						messageBox.getText().toString()).commit();
//				send();
//			}
//		});
	}

	public static void updateMessageBox(String msg) {
		messageBox.append(msg);
		send();
	}

	// this is the function that gets called when you click the button
	public static void send() {
		Log.d("Begin send", "confirmed");
		/*
		// get the phone number from the phone number text field
		// String phoneNumber = phoneTextField.getText().toString();
		String phoneNumber = mSharedPrefs.getString(KEY_INCOMING_NUMBER,
				"8598020785");

		// get the message from the message text box
		// String msg = msgTextField.getText().toString();
		String msg = mSharedPrefs.getString(KEY_AUTOMATED_RESPONSE,
				"Default automated response!");

		// make sure the fields are not empty
		if (phoneNumber.length() > 0 && msg.length() > 0) {
			// call the sms manager
//			PendingIntent pi = PendingIntent.getActivity(mPackageContext, 0,
//					new Intent(mPackageContext, SendSMSActivity.class), 0);
			SmsManager sms = SmsManager.getDefault();
			// this is the function that does all the magic
			sms.sendTextMessage(phoneNumber, null, msg, null, null);
			Log.d("passed test", "confirmed");
		} else {
			// display message if text fields are empty
			Toast.makeText(mContext, "All field are required",
					Toast.LENGTH_SHORT).show();
			Log.d("failed test", "confirmed");
		}
		
		*/

	}
}
