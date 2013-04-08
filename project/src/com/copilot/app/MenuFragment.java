package com.copilot.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

public class MenuFragment extends SherlockListFragment {

	public static final String LOG_TAG = "MenuFragment";

	private static final String ARG_POSITION = "position";

	private int mCurrentPosition = -1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = null;
		
		v = (ListView)container.findViewById(R.id.listview_slide_menu);
		v.setOnClickListener((OnClickListener) this);
		
		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
		}

		return inflater.inflate(R.layout.sliding_menu_list, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();

		Bundle args = getArguments();

		if (args != null) {

		} else if (mCurrentPosition != -1) {

		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(ARG_POSITION, mCurrentPosition);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		switch (position) {
		// Home
		case 0:
			
			break;
		//Log
		case 1:
			
			break;
		// Emergency	
		case 2:
			
			break;
		// Incident Assistant
		case 3:
			
			break;
		// Settings
		case 4:
			
			break;

		default:
			break;
		}
	}
}
