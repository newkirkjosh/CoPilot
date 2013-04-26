package com.copilot.app;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class EmergencyActivity extends SherlockListActivity {

	public static final String LOG_TAG = "EmergencyActivity";

	private static final String[] contactNames = { "Andrew Acree",
			"Makayla Schultz", "Josh Newkirk", "Dejan Ristic" };
	private static final String[] contactNumbers = { "859-445-2133",
			"859-750-7405", "859-802-0785", "859-866-9061" };
	private static final int[] contactImages = { R.drawable.andrew,
			R.drawable.makayla, R.drawable.josh, 0 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(LOG_TAG, "onCreate hit");
		setContentView(R.layout.emergency_list);

		List<ContactItem> contacts = new ArrayList<ContactItem>();

		for (int i = 0; i < 4; i++) {
			Log.v(LOG_TAG, "Adding contacts to list");
			contacts.add(new ContactItem(contactNames[i], contactNumbers[i],
					contactImages[i]));
		}

		ContactMenuAdapter adapter = new ContactMenuAdapter(this,
				R.layout.emergency_contact_item, contacts);
		setListAdapter(adapter);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.v(LOG_TAG, "List item clicked at position " + position);

		RelativeLayout expand = (RelativeLayout) v
				.findViewById(R.id.contact_item_container);

		// When the user presses the top item it should either expand or
		// collapse the bottom section
		if (expand.getVisibility() == View.VISIBLE) {
			expand.setVisibility(View.GONE);
		} else {
			expand.setVisibility(View.VISIBLE);
		}
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
			Log.d(LOG_TAG, "ActionBar back pressed");
			finish();
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private class ContactItem {
		public String fullName;
		public String phoneNum;
		public int imageRes;

		public ContactItem(String name, String phoneNum, int imageRes) {
			this.fullName = name;
			this.phoneNum = phoneNum;
			this.imageRes = imageRes;
		}
	}

	private class ContactMenuAdapter extends ArrayAdapter<ContactItem> {

		private List<ContactItem> contacts;
		private int layoutRes;

		public ContactMenuAdapter(Context context) {
			super(context, 0);
		}

		public ContactMenuAdapter(Context context, int viewResource,
				List<ContactItem> contacts) {
			super(context, viewResource, contacts);
			this.contacts = contacts;
			this.layoutRes = viewResource;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						layoutRes, null);
			}

			final RelativeLayout expand = (RelativeLayout) convertView
					.findViewById(R.id.contact_item_container);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (expand.getVisibility() == View.GONE) {
						expand.setVisibility(View.VISIBLE);
					} else {
						expand.setVisibility(View.GONE);
					}
				}
			});

			// Get the current item based on the position in list
			final ContactItem temp = contacts.get(position);

			// Get views to be changed for each item
			final ImageView icon = (ImageView) convertView
					.findViewById(R.id.contact_image);
			final TextView name = (TextView) convertView
					.findViewById(R.id.contact_name);
			final TextView phone = (TextView) convertView
					.findViewById(R.id.contact_phone_num);
			final EditText editPhone = (EditText) convertView
					.findViewById(R.id.contact_view_num);
			final ImageView call = (ImageView) convertView
					.findViewById(R.id.contact_call);
			final ImageView message = (ImageView) convertView
					.findViewById(R.id.contact_message);

			icon.setImageResource(temp.imageRes);
			name.setText(temp.fullName);
			phone.setText(temp.phoneNum);
			editPhone.setText(temp.phoneNum);
			editPhone.setOnEditorActionListener(new OnEditorActionListener() {
				
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					
					if( event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER ){
						phone.setText(v.getText());
						return true;
					}
					return false;
				}
			});
			
			call.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String number = "tel:" + phone.getText().toString().trim();
					Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
					startActivity(callIntent);
				}
			});
			
			message.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent messageIntent = new Intent(Intent.ACTION_VIEW);
					messageIntent.setData(Uri.parse("sms:" + phone.getText().toString().trim()));
					startActivity(messageIntent);
				}
			});

			return convertView;
		}
	}
	
	
}
