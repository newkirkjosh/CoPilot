package com.copilot.app;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CoPilotMainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co_pilot_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_co_pilot_main, menu);
        return true;
    }
}
