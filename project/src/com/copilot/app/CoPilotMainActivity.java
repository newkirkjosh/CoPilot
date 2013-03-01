package com.copilot.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class CoPilotMainActivity extends Activity {
	
	
	Button _launchButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co_pilot_main);
        _launchButton = (Button)findViewById( R.id.button_launch_autoreply );
        _launchButton.setOnClickListener( startCoPilot );
    }
    
    /*
     *  Handler that launches CoPilot once our button is clicked
     */
    View.OnClickListener startCoPilot = new View.OnClickListener() {
    	/*
    	 * (non-Javadoc)
    	 * @see android.view.View.OnClickListener#onClick(android.view.View)
    	 */
		public void onClick(View launchButton) {
			Intent intent = new Intent(launchButton.getContext(), ReceiveSMSActivity.class );
			startActivity(intent);
		}
	};
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_co_pilot_main, menu);
        return true;
    }
}
