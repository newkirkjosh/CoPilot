package com.copilot.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ReceiveSMSActivity extends Activity{
	
	static TextView messageBox;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_sms_activity);
        messageBox=(TextView)findViewById(R.id.messages);
        messageBox.setText("Messages will go in here\n");
    }
    
    public static void updateMessageBox(String msg)
    {
    	messageBox.append(msg);
    }

}
