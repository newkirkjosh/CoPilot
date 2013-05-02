package com.copilot.app.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;

public class Incident {

	public static final String IMAGE_PATHS_KEY = "imagePaths";

	private String incidentId;
	private String associatedAccount;
	private String incidentReport;
	private String incidentImagePaths;

	public Incident() {
	}

	public Incident(Cursor c) {
		incidentId = c.getString(c.getColumnIndex("incidentId"));
		associatedAccount = c.getString(c.getColumnIndex("associatedAccount"));
		incidentReport = c.getString(c.getColumnIndex("incidentReport"));
		incidentImagePaths = c
				.getString(c.getColumnIndex("incidentImagePaths"));
	}

	public String getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}

	public String getAssociatedAccount() {
		return associatedAccount;
	}

	public void setAssociatedAccount(String associatedAccount) {
		this.associatedAccount = associatedAccount;
	}

	public String getIncidentReport() {
		return incidentReport;
	}

	public void setIncidentReport(String incidentReport) {
		this.incidentReport = incidentReport;
	}

	public String getIncidentImagePaths() {
		return incidentImagePaths;
	}

	public void setIncidentImagePaths(String incidentImagePaths) {
		this.incidentImagePaths = incidentImagePaths;
	}

	public void setIncidentImagePathsFromArrayList(ArrayList<String> paths) {
		JSONObject json = new JSONObject();
		try {
			json.put(IMAGE_PATHS_KEY, new JSONArray(paths));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.incidentImagePaths = json.toString();
	}

}
