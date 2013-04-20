package com.copilot.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class IncidentActivity extends SherlockActivity implements
		OnGroupExpandListener {

	public static final String LOG_TAG = "IncidentActivity";
	private ExpandableListView mExpandableList;
	private ExpandableListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.copilot_incident_activity);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mExpandableList = (ExpandableListView) findViewById(R.id.expandable_list);
		mExpandableList.setOnGroupExpandListener(this);
		mAdapter = new ExpandableListAdapter(this, getResources()
				.getStringArray(R.array.incident_group_titles));
		mExpandableList.setAdapter(mAdapter);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
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
			Log.d("actionbarhome", "pressed");
			finish();
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	public void onGroupExpand(int groupPosition) {
		int len = mAdapter.getGroupCount();

		for (int i = 0; i < len; i++) {
			if (i != groupPosition) {
				mExpandableList.collapseGroup(i);
			}
		}

	}

	class ExpandableListAdapter extends BaseExpandableListAdapter {

		private Context context;
		private String[] parents;
		private View first;
		private View docs;
		private View desc;
		private View photos;

		private int[] icons = { R.drawable.one, R.drawable.two,
				R.drawable.three, R.drawable.four };

		public ExpandableListAdapter(Context context, String[] parents) {
			this.context = context;
			this.parents = parents;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {

			first = inflateFirst(first, parent);
			docs = inflateDocumentation(docs, parent);
			desc = inflateDescription(desc, parent);
			photos = inflatePhotos(photos, parent);

			switch (groupPosition) {
			case 0:
				return first;
			case 1:
				return docs;
			case 2:
				return desc;
			case 3:
				return photos;
			}
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return 1;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return parents[groupPosition];
		}

		@Override
		public int getGroupCount() {
			return parents.length;
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			LayoutInflater inflator = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				convertView = inflator.inflate(
						R.layout.copilot_incident_parent, parent, false);
			}

			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.parent_icon);
			TextView textView = (TextView) convertView
					.findViewById(R.id.parent_text);

			imageView.setImageResource(icons[groupPosition]);

			textView.setText(getGroup(groupPosition).toString());
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}

		private View inflateFirst(View convertView, ViewGroup parent) {
			LayoutInflater inflator = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				convertView = inflator.inflate(R.layout.copilot_incident_first,
						parent, false);
			}

			return convertView;
		}

		private View inflateDocumentation(View docView, ViewGroup parent) {
			LayoutInflater inflator = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (docView == null) {
				docView = inflator.inflate(
						R.layout.copilot_incident_documentation, parent, false);
			}

			// EditText datePicker = (EditText) docView
			// .findViewById(R.id.selectDate);

			return docView;

		}

		private View inflateDescription(View descVew, ViewGroup parent) {
			LayoutInflater inflator = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (descVew == null) {
				descVew = inflator.inflate(
						R.layout.copilot_incident_description, parent, false);
			}

			return descVew;
		}

		private View inflatePhotos(View photosView, ViewGroup parent) {
			LayoutInflater inflator = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (photosView == null) {
				photosView = inflator.inflate(R.layout.copilot_incident_photos,
						parent, false);
			}

			return photosView;
		}
	}
}
