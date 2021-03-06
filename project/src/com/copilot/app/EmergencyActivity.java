package com.copilot.app;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class EmergencyActivity extends SherlockFragmentActivity implements
		OnItemClickListener {

	public static final String LOG_TAG = EmergencyActivity.class.getName();

	private static final String[] contactNames = { "Andrew Acree",
			"Makayla Schultz", "Josh Newkirk", "Dejan Ristic" };
	private static final String[] contactNumbers = { "859-445-2133",
			"859-750-7405", "859-802-0785", "859-866-9061" };
	private static final int[] contactImages = { R.drawable.andrew,
			R.drawable.makayla, R.drawable.josh, 0 };

	public static ContactMenuAdapter contactAdapter;

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

		contactAdapter = new ContactMenuAdapter(this,
				R.layout.emergency_contact_item, contacts);

		ListView list = (ListView) findViewById(R.id.emergency_contact_list);
		list.setAdapter(contactAdapter);
		list.setOnItemClickListener(this);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
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
		getSupportMenuInflater().inflate(R.menu.emergency_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			Log.d(LOG_TAG, "ActionBar back pressed");
			finish();
			break;
		case R.id.add_contact:
			FragmentManager manager = getSupportFragmentManager();
			EmergencyDialogFragment fragment = new EmergencyDialogFragment();
			fragment.show(manager, "add_contact");
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	public static void addContactToAdapter(ContactItem contact) {
		Log.v(LOG_TAG, "Contact: " + contact.toString());
		contactAdapter.add(contact);
	}

	public static void notifyContactAdapter() {
		contactAdapter.notifyDataSetChanged();
	}

	public class ContactMenuAdapter extends ArrayAdapter<ContactItem> {

		private List<ContactItem> contacts;
		private int layoutRes = R.layout.emergency_contact_item;

		public ContactMenuAdapter(Context context) {
			super(context, 0);
		}

		public ContactMenuAdapter(Context context, int viewResource,
				List<ContactItem> contacts) {
			super(context, viewResource, contacts);
			this.contacts = contacts;
			this.layoutRes = viewResource;
		}

		public ContactMenuAdapter getMenuAdapterState(Context context) {
			ContactMenuAdapter adapter = new ContactMenuAdapter(context,
					layoutRes, contacts);
			return adapter;
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
			final ImageView editNum = (ImageView) convertView
					.findViewById(R.id.contact_edit_num);

			icon.setImageResource(temp.imageRes);
			name.setText(temp.fullName);
			phone.setText(temp.phoneNum);
			editPhone.setText(temp.phoneNum);
			editPhone.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {

					if (event.getAction() == KeyEvent.ACTION_DOWN
							&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
						phone.setText(editPhone.getText().toString());
						Log.v(LOG_TAG, "Enter is pressed");
						return true;
					}

					return false;
				}
			});

			call.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String number = "tel:" + phone.getText().toString().trim();
					Intent callIntent = new Intent(Intent.ACTION_CALL, Uri
							.parse(number));
					startActivity(callIntent);
				}
			});

			message.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent messageIntent = new Intent(Intent.ACTION_VIEW);
					messageIntent.setData(Uri.parse("sms:"
							+ phone.getText().toString().trim()));
					startActivity(messageIntent);
				}
			});

			editNum.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					editPhone.requestFocus();
					((InputMethodManager) getSystemService(EmergencyActivity.INPUT_METHOD_SERVICE))
							.showSoftInputFromInputMethod(
									editPhone.getWindowToken(),
									InputMethodManager.SHOW_IMPLICIT);
				}
			});

			return convertView;
		}
	}
}
