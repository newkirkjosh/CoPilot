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

	public static final Class<?>[] ACTIVITIES = { CoPilotMainActivity.class,
			LoggingActivity.class, EmergencyActivity.class,
			IncidentActivity.class, SettingsActivity.class };

	public static final String LOG_TAG = MenuFragment.class.getName();

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

		int[] icons = { R.drawable.home_a, R.drawable.list_a, R.drawable.tri_a,
				R.drawable.check_a, R.drawable.gear_a };

		for (int i = 0; i < slideOptions.length; i++)
			adapter.add(new SlideMenuItem(slideOptions[i], icons[i]));

		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Class<?> nextActivity = ACTIVITIES[position];

		Toast.makeText(
				getSherlockActivity(),
				"The following activity was pressed: " + nextActivity.getName(),
				Toast.LENGTH_SHORT).show();

		startActivity(new Intent(getSherlockActivity(), nextActivity));
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
