package com.copilot.app.models;

import android.database.Cursor;

public class Trip {

	private String tripId;
	private String associatedAccount;
	private String missedCalls;
	private String missedMesseges;
	private String duration;
	private String incidents;
	private String avgSpeed;
	private String distanceTraveled;

	public Trip() {

	}

	public Trip(Cursor c) {
		tripId = c.getString(c.getColumnIndex("tripId"));
		associatedAccount = c.getString(c.getColumnIndex("associatedAccount"));
		missedCalls = c.getString(c.getColumnIndex("missedCalls"));
		missedMesseges = c.getString(c.getColumnIndex("missedMesseges"));
		duration = c.getString(c.getColumnIndex("duration"));
		incidents = c.getString(c.getColumnIndex("incidents"));
		avgSpeed = c.getString(c.getColumnIndex("avgSpeed"));
		distanceTraveled = c.getString(c.getColumnIndex("distanceTraveled"));
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public String getAssociatedAccount() {
		return associatedAccount;
	}

	public void setAssociatedAccount(String associatedAccount) {
		this.associatedAccount = associatedAccount;
	}

	public String getMissedCalls() {
		return missedCalls;
	}

	public void setMissedCalls(String missedCalls) {
		this.missedCalls = missedCalls;
	}

	public String getMissedMesseges() {
		return missedMesseges;
	}

	public void setMissedMesseges(String missedMesseges) {
		this.missedMesseges = missedMesseges;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getIncidents() {
		return incidents;
	}

	public void setIncidents(String incidents) {
		this.incidents = incidents;
	}

	public String getAvgSpeed() {
		return avgSpeed;
	}

	public void setAvgSpeed(String avgSpeed) {
		this.avgSpeed = avgSpeed;
	}

	public String getDistanceTraveled() {
		return distanceTraveled;
	}

	public void setDistanceTraveled(String distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}

}
