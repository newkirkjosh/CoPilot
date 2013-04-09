package com.copilot.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;

public class MenuFragment extends SherlockListFragment {

	public static final String LOG_TAG = "MenuFragment";

	private static final String ARG_POSITION = "position";

	private int mCurrentPosition = -1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

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
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Resources mResources = getSherlockActivity().getResources();

		SlideMenuAdapter adapter = new SlideMenuAdapter(getSherlockActivity());

		String[] slideOptions = mResources
				.getStringArray(R.array.slide_menu_options);

		SlideMenuItem home = new SlideMenuItem(slideOptions[0],
				R.drawable.home_a);
		SlideMenuItem log = new SlideMenuItem(slideOptions[1],
				R.drawable.list_a);
		SlideMenuItem emergency = new SlideMenuItem(slideOptions[2],
				R.drawable.tri_a);
		SlideMenuItem incident = new SlideMenuItem(slideOptions[3],
				R.drawable.check_a);
		SlideMenuItem settings = new SlideMenuItem(slideOptions[4],
				R.drawable.gear_a);

		adapter.add(home);
		adapter.add(log);
		adapter.add(emergency);
		adapter.add(incident);
		adapter.add(settings);

		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent temp = null;
		Class<?> tempClass = null;

		switch (position) {
		// Home
		case 0:
			if (getSherlockActivity().getClass() != CoPilotMainActivity.class) {
				tempClass = CoPilotMainActivity.class;
			}
			Toast.makeText(getSherlockActivity(), "Item: Home was pressed.",
					Toast.LENGTH_SHORT).show();
			break;
		// Log
		case 1:
			tempClass = LoggingActivity.class;
			Toast.makeText(getSherlockActivity(), "Item: Log was pressed.",
					Toast.LENGTH_SHORT).show();
			break;
		// Emergency
		case 2:
			tempClass = EmergencyActivity.class;
			Toast.makeText(getSherlockActivity(),
					"Item: Emergency was pressed.", Toast.LENGTH_SHORT).show();
			break;
		// Incident Assistant
		case 3:
			// tempClass = IncidentActivity.class;
			Toast.makeText(getSherlockActivity(),
					"Item: Incident Assistant was pressed.", Toast.LENGTH_SHORT)
					.show();
			break;
		// Settings
		case 4:
			// tempClass = SettingsActivity.class;
			Toast.makeText(getSherlockActivity(),
					"Item: Settings was pressed.", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}

		if (tempClass != null) {
			temp = new Intent(getSherlockActivity(), tempClass);
			startActivity(temp);
		}
	}

	private class SlideMenuItem {

		public String text;
		public int iconRes;

		public SlideMenuItem(String text, int iconRes) {
			this.text = text;
			this.iconRes = iconRes;
		}
	}

	private class SlideMenuAdapter extends ArrayAdapter<SlideMenuItem> {

		public SlideMenuAdapter(Context context) {
			super(context, 0);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.slide_menu_item, null);
			}

			ImageView icon = (ImageView) convertView
					.findViewById(R.id.slide_item_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView
					.findViewById(R.id.slide_item_title);
			title.setText(getItem(position).text);

			return convertView;
		}
	}
}
