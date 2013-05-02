package com.copilot.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class EmergencyDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View v = inflater.inflate(R.layout.emergency_dialog, null, false);

		final EditText name = (EditText) v.findViewById(R.id.contact_name);
		final EditText phoneNum = (EditText) v.findViewById(R.id.contact_phone);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Add an emergency contact");
		builder.setMessage("Please enter the information for the emergency contact");
		builder.setView(v).setPositiveButton("Ok", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				ContactItem newContact = new ContactItem(name.getText()
						.toString(), phoneNum.getText().toString(), 0);

				EmergencyActivity.addContactToAdapter(newContact);
				EmergencyActivity.notifyContactAdapter();
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				EmergencyDialogFragment.this.getDialog().dismiss();
			}
		});

		return builder.create();
	}
}
