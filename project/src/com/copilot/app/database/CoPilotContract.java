package com.copilot.app.database;

import android.provider.BaseColumns;

/**
 * Database contract for {@link CoPilotDatabase} that makes it easier to
 * interact with the database. Setup of tables, associated columns, and queries.
 * 
 * @author Dejan Ristic
 */

public class CoPilotContract {

	// Empty Constructor, Prevents the VirtualPillboxContract from being
	// instantiated.
	private CoPilotContract() {
	}

	/**
	 * User Contract
	 */
	public static abstract class Users implements BaseColumns {
		public static final String TABLE_TAME = "users";
		public static final String COLUMN_ID = BaseColumns._ID;
		public static final String COLUMN_GOOGLE_ACCOUNT = "googleAccountName";
		public static final String COLUMN_NUM_OF_TRIPS = "numOfTrips";
		public static final String COLUMN_NUM_OF_INCIDENTS = "numOfIncidents";
		public static final String COLUMN_ALL_TIME_MISSED_CALLS = "allTimeMissedCalls";
		public static final String COLUMN_ALL_TIME_MISSED_MESSEGES = "allTimeMissedMesseges";
		public static final String COLUMN_AVG_MISSED_CALLS_PER_TRIP = "avgMissedCallsPerTrip";
		public static final String COLUMN_AVG_MISSED_MESSEGES_PER_TRIP = "avgMissedMessegesPerTrip";

		public static String createUserTable() {
			return "CREATE TABLE " + Users.TABLE_TAME + " ("
					+ Users.COLUMN_GOOGLE_ACCOUNT + " TEXT NOT NULL,"
					+ Users.COLUMN_ID + " TEXT NOT NULL,"
					+ Users.COLUMN_NUM_OF_TRIPS + " TEXT NOT NULL,"
					+ Users.COLUMN_NUM_OF_INCIDENTS + " TEXT NOT NULL,"
					+ Users.COLUMN_ALL_TIME_MISSED_CALLS + " TEXT NOT NULL,"
					+ Users.COLUMN_ALL_TIME_MISSED_MESSEGES + " TEXT NOT NULL,"
					+ Users.COLUMN_AVG_MISSED_CALLS_PER_TRIP
					+ " TEXT NOT NULL,"
					+ Users.COLUMN_AVG_MISSED_MESSEGES_PER_TRIP
					+ " TEXT NOT NULL," + "UNIQUE ("
					+ Users.COLUMN_GOOGLE_ACCOUNT + ") ON CONFLICT REPLACE)";
		}

		public static final String QUERY_GET_USER_WITH_GOOGLE_ACCOUNT_NAME = "SELECT * FROM "
				+ Users.TABLE_TAME
				+ " WHERE "
				+ Users.COLUMN_GOOGLE_ACCOUNT
				+ " = ?";
	}

	/**
	 * Trip Contract
	 */

	public static abstract class Trips implements BaseColumns {
		public static final String TABLE_TAME = "trips";
		public static final String COLUMN_TRIP_ID = "tripId";
		public static final String COLUMN_ASSOCIATED_ACCOUNT = "associatedAccount";
		public static final String COLUMN_MISSED_CALLS = "missedCalls";
		public static final String COLUMN_MISSED_MESSEGES = "missedMesseges";
		public static final String COLUMN_DURATION = "duration";
		public static final String COLUMN_INCIDENTS = "incidents";
		public static final String COLUMN_AVG_SPEED = "avgSpeed";
		public static final String COLUMN_DISTANCE_TRAVELED = "distanceTraveled";

		public static String createTripsTable() {
			return "CREATE TABLE " + Trips.TABLE_TAME + " ("
					+ Trips.COLUMN_TRIP_ID + " TEXT NOT NULL,"
					+ Trips.COLUMN_ASSOCIATED_ACCOUNT + " TEXT NOT NULL,"
					+ Trips.COLUMN_MISSED_CALLS + " TEXT NOT NULL,"
					+ Trips.COLUMN_MISSED_MESSEGES + " TEXT NOT NULL,"
					+ Trips.COLUMN_DURATION + " TEXT NOT NULL,"
					+ Trips.COLUMN_INCIDENTS + " TEXT NOT NULL,"
					+ Trips.COLUMN_AVG_SPEED + " TEXT NOT NULL,"
					+ Trips.COLUMN_DISTANCE_TRAVELED + " TEXT NOT NULL,"
					+ "UNIQUE (" + Trips.COLUMN_TRIP_ID
					+ ") ON CONFLICT REPLACE)";
		}

		public static final String QUERY_GET_TRIPS_FOR_ASSOCIATED_GOOGLE_ACCOUNT = "SELECT * FROM "
				+ Trips.TABLE_TAME
				+ " WHERE "
				+ Trips.COLUMN_ASSOCIATED_ACCOUNT + " = ? ";
	}

	/**
	 * Incident Contract
	 */
	public static abstract class Incidents implements BaseColumns {

		public static final String TABLE_TAME = "incidents";
		public static final String COLUMN_INCIDENT_ID = "incidentId";
		public static final String COLUMN_ASSOCIATED_ACCOUNT = "associatedAccount";
		public static final String COLUMN_INCIDENT_REPORT_PATH = "incidentReport";
		public static final String COLUMN_INCIDENT_IMAGE_PATHS = "incidentImagePaths";

		public static String createIncidentTable() {
			return "CREATE TABLE " + Incidents.TABLE_TAME + " ("
					+ Incidents.COLUMN_INCIDENT_ID + " TEXT NOT NULL,"
					+ Incidents.COLUMN_ASSOCIATED_ACCOUNT + " TEXT NOT NULL,"
					+ Incidents.COLUMN_INCIDENT_REPORT_PATH + " TEXT NOT NULL,"
					+ Incidents.COLUMN_INCIDENT_IMAGE_PATHS + " TEXT NOT NULL,"
					+ "UNIQUE (" + Incidents.COLUMN_INCIDENT_ID
					+ ") ON CONFLICT REPLACE)";
		}

		public static final String QUERY_GET_INCIDENTS_FOR_ASSOCIATED_GOOGLE_ACCOUNT = "SELECT * FROM "
				+ Incidents.TABLE_TAME
				+ " WHERE "
				+ Incidents.COLUMN_ASSOCIATED_ACCOUNT + " = ? ";

	}

}