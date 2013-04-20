package com.copilot.app;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class SplashActivity extends SherlockActivity implements OnTouchListener{
	
	public static final String LOG_TAG = "SplashActivity";
	
	private static final String TYPEFACE_ROBOTO = "/fonts/Roboto-Light.ttf";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_layout);
		
		View view = (View)findViewById(R.id.splash_layout);
		final ImageView logo = (ImageView)findViewById(R.id.splash_logo);
		logo.setOnTouchListener(this);
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

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		LayoutParams params = (LayoutParams)v.getLayoutParams();
		View parent = (View) v.getParent();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			// On release
			if( event.getAxisValue(MotionEvent.AXIS_Y) >= v.getY() - 200 ){
				v.setVisibility(View.GONE);
				finish();
			}
			
			break;
		case MotionEvent.ACTION_DOWN:
			// On pressed
			break;
		case MotionEvent.ACTION_MOVE:
			// While pressed
			
			int x = (int)event.getRawX();
			int y = (int)event.getRawY();
			
			params.leftMargin = x - v.getWidth();
			params.topMargin = y - v.getHeight();
			params.rightMargin = parent.getRight();
			
			if( event.getAxisValue(MotionEvent.AXIS_X) <= v.getX() - params.leftMargin || event.getAxisValue(MotionEvent.AXIS_X) <= v.getX() + params.rightMargin ){
				v.setTranslationX(event.getAxisValue(MotionEvent.AXIS_X));
				v.setTranslationY(event.getAxisValue(MotionEvent.AXIS_Y));
			}
			
			break;
		default:
			break;
		}
		
		return true;
	}
}
