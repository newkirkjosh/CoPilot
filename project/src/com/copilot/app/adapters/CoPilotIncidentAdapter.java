package com.copilot.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.copilot.app.R;
import com.copilot.app.camera.CameraManager;

/**
 * Represents an adapter that is used in {@link IncidentActivity} for the very
 * custom {@link ExpandableListView} used for the incident form.
 * 
 * Each group in the {@link ExpandableListView} contains only one child, though
 * each child is a specially inflated layout to fit each part of the form.
 * 
 * @author Dejan Ristic
 * 
 */

public class CoPilotIncidentAdapter extends BaseExpandableListAdapter {

	private Context context;
	private String[] parents;
	private Activity activity;
	private String mostRecentImageTag;
	private View first;
	private View docs;
	private View desc;
	private View photos;
	private View submit;

	private static final int[] ICONS = { R.drawable.one, R.drawable.two,
			R.drawable.three, R.drawable.four, R.drawable.five };

	/**
	 * Public constructor for the adapter
	 * 
	 * @param context
	 * @param parents
	 * @param activity
	 */

	public CoPilotIncidentAdapter(Context context, String[] parents,
			Activity activity) {
		this.context = context;
		this.parents = parents;
		this.activity = activity;
	}

	/**
	 * Overridden Methods from {@link BaseExpandableListAdapter}
	 */

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
		submit = inflateSubmit(submit, parent);

		switch (groupPosition) {
		case 0:
			return first;
		case 1:
			return docs;
		case 2:
			return desc;
		case 3:
			return photos;
		case 4:
			return submit;
		}
		return null;
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
			convertView = inflator.inflate(R.layout.copilot_incident_parent,
					parent, false);
		}

		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.parent_icon);
		TextView textView = (TextView) convertView
				.findViewById(R.id.parent_text);

		imageView.setImageResource(ICONS[groupPosition]);

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

	/**
	 * Public methods that can be accessed by classes containing an instance of
	 * this adapter.
	 */

	public String getMostRecentImageButtonTag() {
		return this.mostRecentImageTag;
	}

	/**
	 * Private Methods to be used only by this adapter.
	 */

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
			docView = inflator.inflate(R.layout.copilot_incident_documentation,
					parent, false);
		}

		// TODO store values to txt document/db

		return docView;

	}

	private View inflateDescription(View descVew, ViewGroup parent) {
		LayoutInflater inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (descVew == null) {
			descVew = inflator.inflate(R.layout.copilot_incident_description,
					parent, false);
		}

		return descVew;
	}

	private View inflatePhotos(View photosView, ViewGroup parent) {
		LayoutInflater inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (photosView == null) {
			photosView = inflator.inflate(R.layout.copilot_incident_photos,
					parent, false);
			setupImageClickListeners(photosView);
		}

		return photosView;
	}

	private View inflateSubmit(View submitView, ViewGroup parent) {

		LayoutInflater inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (submitView == null) {
			submitView = inflator.inflate(R.layout.copilot_incident_submit,
					parent, false);
		}
		return submitView;
	}

	private void setupImageClickListeners(View view) {

		// Set up touch listener for non-text box views to hide keyboard.
		if ((view instanceof ImageButton)) {

			view.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					CameraManager.getInstance(context, activity);
					CameraManager.startCaptureIntent();
					mostRecentImageTag = (String) v.getTag();

				}
			});
		}

		// If a layout container, iterate over children and seed recursion.
		if (view instanceof ViewGroup) {

			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

				View innerView = ((ViewGroup) view).getChildAt(i);

				setupImageClickListeners(innerView);
			}
		}
	}
}