package com.copilot.app.models;

import android.database.Cursor;

public class User {

	private String googleAccountName;
	private String numOfTrips;
	private String numOfIncidents;
	private String allTimeMissedCalls;
	private String allTimeMissedMesseges;
	private String avgMissedCallsPerTrip;
	private String avgMissedMessegesPerTrip;

	public User() {
	}

	public User(Cursor c) {
		googleAccountName = c.getString(c.getColumnIndex("googleAccountName"));
		numOfTrips = c.getString(c.getColumnIndex("numOfTrips"));
		numOfIncidents = c.getString(c.getColumnIndex("numOfIncidents"));
		allTimeMissedCalls = c
				.getString(c.getColumnIndex("allTimeMissedCalls"));
		allTimeMissedMesseges = c.getString(c
				.getColumnIndex("allTimeMissedMesseges"));
		avgMissedCallsPerTrip = c.getString(c
				.getColumnIndex("avgMissedCallsPerTrip"));
		avgMissedMessegesPerTrip = c.getString(c
				.getColumnIndex("avgMissedMessegesPerTrip"));
	}

	/**
	 * Used for a brand new account, All values set to zero
	 * 
	 * @param googleEmailAddress
	 */
	public User(String googleEmailAddress) {
		this.googleAccountName = googleEmailAddress;
		this.numOfTrips = "0";
		this.numOfIncidents = "0";
		this.allTimeMissedCalls = "0";
		this.allTimeMissedMesseges = "0";
		this.avgMissedCallsPerTrip = "0";
		this.avgMissedMessegesPerTrip = "0";
	}

	public String getGoogleAccountName() {
		return googleAccountName;
	}

	public void setGoogleAccountName(String googleAccountName) {
		this.googleAccountName = googleAccountName;
	}

	public String getNumOfTrips() {
		return numOfTrips;
	}

	public void setNumOfTrips(String numOfTrips) {
		this.numOfTrips = numOfTrips;
	}

	public String getNumOfIncidents() {
		return numOfIncidents;
	}

	public void setNumOfIncidents(String numOfIncidents) {
		this.numOfIncidents = numOfIncidents;
	}

	public String getAllTimeMissedCalls() {
		return allTimeMissedCalls;
	}

	public void setAllTimeMissedCalls(String allTimeMissedCalls) {
		this.allTimeMissedCalls = allTimeMissedCalls;
	}

	public String getAllTimeMissedMesseges() {
		return allTimeMissedMesseges;
	}

	public void setAllTimeMissedMesseges(String allTimeMissedMesseges) {
		this.allTimeMissedMesseges = allTimeMissedMesseges;
	}

	public String getAvgMissedCallsPerTrip() {
		return avgMissedCallsPerTrip;
	}

	public void setAvgMissedCallsPerTrip(String avgMissedCallsPerTrip) {
		this.avgMissedCallsPerTrip = avgMissedCallsPerTrip;
	}

	public String getAvgMissedMessegesPerTrip() {
		return avgMissedMessegesPerTrip;
	}

	public void setAvgMissedMessegesPerTrip(String avgMissedMessegesPerTrip) {
		this.avgMissedMessegesPerTrip = avgMissedMessegesPerTrip;
	}
}
