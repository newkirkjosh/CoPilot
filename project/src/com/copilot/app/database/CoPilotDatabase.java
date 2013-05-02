package com.copilot.app.database;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.copilot.app.database.CoPilotContract.Incidents;
import com.copilot.app.database.CoPilotContract.Trips;
import com.copilot.app.database.CoPilotContract.Users;
import com.copilot.app.models.Incident;
import com.copilot.app.models.Trip;
import com.copilot.app.models.User;

public class CoPilotDatabase extends SQLiteOpenHelper {

	private static CoPilotDatabase mInstance;
	private static final String TAG = CoPilotDatabase.class.getCanonicalName();

	// NOTE: carefully update onUpgrade() when bumping database versions to make
	// sure user data is saved.
	private static final String DATABASE_NAME = "copilot.db";
	private static final int VER_SESSION_TYPE = 26;
	private static final int DATABASE_VERSION = VER_SESSION_TYPE;

	public static CoPilotDatabase getInstance(Context c) {
		if (mInstance == null)
			mInstance = new CoPilotDatabase(c.getApplicationContext());

		return mInstance;
	}

	public CoPilotDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * SQLiteOpenHelper Overrides
	 */

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Users.createUserTable());
		db.execSQL(Trips.createTripsTable());
		db.execSQL(Incidents.createIncidentTable());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public boolean addUser(User user) {
		SQLiteDatabase db = mInstance.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();

		values.put(Users.COLUMN_ID, user.getGoogleAccountName());
		values.put(Users.COLUMN_GOOGLE_ACCOUNT, user.getGoogleAccountName());
		values.put(Users.COLUMN_NUM_OF_TRIPS, user.getNumOfTrips());
		values.put(Users.COLUMN_NUM_OF_INCIDENTS, user.getNumOfIncidents());
		values.put(Users.COLUMN_ALL_TIME_MISSED_CALLS,
				user.getAllTimeMissedCalls());
		values.put(Users.COLUMN_ALL_TIME_MISSED_MESSEGES,
				user.getAllTimeMissedMesseges());
		values.put(Users.COLUMN_AVG_MISSED_CALLS_PER_TRIP,
				user.getAvgMissedCallsPerTrip());
		values.put(Users.COLUMN_AVG_MISSED_MESSEGES_PER_TRIP,
				user.getAvgMissedMessegesPerTrip());

		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(Users.TABLE_TAME, null, values);

		if (newRowId != -1) {
			Log.v("INSERT SUCCESSFULL", "User entity added to database");
			return true;
		} else {
			Log.e("INSERT ERROR",
					"There was an error while trying to insert a User entity into the database");
			return false;
		}
	}

	public boolean addTrip(Trip trip) {
		SQLiteDatabase db = mInstance.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();

		values.put(Trips.COLUMN_TRIP_ID, trip.getTripId());
		values.put(Trips.COLUMN_ASSOCIATED_ACCOUNT, trip.getAssociatedAccount());
		values.put(Trips.COLUMN_MISSED_CALLS, trip.getMissedCalls());
		values.put(Trips.COLUMN_MISSED_MESSEGES, trip.getMissedMesseges());
		values.put(Trips.COLUMN_DURATION, trip.getDuration());
		values.put(Trips.COLUMN_INCIDENTS, trip.getIncidents());
		values.put(Trips.COLUMN_AVG_SPEED, trip.getAvgSpeed());
		values.put(Trips.COLUMN_DISTANCE_TRAVELED, trip.getDistanceTraveled());

		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(Trips.TABLE_TAME, null, values);

		if (newRowId != -1) {
			Log.v("INSERT SUCCESSFULL", "Trip entity added to database");
			return true;
		} else {
			Log.e("INSERT ERROR",
					"There was an error while trying to insert a Trip entity into the database");
			return false;
		}
	}

	public boolean addIncident(Incident incident) {
		SQLiteDatabase db = mInstance.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();

		values.put(Incidents.COLUMN_INCIDENT_ID, incident.getIncidentId());
		values.put(Incidents.COLUMN_ASSOCIATED_ACCOUNT,
				incident.getAssociatedAccount());
		values.put(Incidents.COLUMN_INCIDENT_REPORT_PATH,
				incident.getIncidentReport());
		values.put(Incidents.COLUMN_INCIDENT_IMAGE_PATHS,
				incident.getIncidentImagePaths());

		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(Incidents.TABLE_TAME, null, values);

		if (newRowId != -1) {
			Log.v("INSERT SUCCESSFULL", "Incident entity added to database");
			return true;
		} else {
			Log.e("INSERT ERROR",
					"There was an error while trying to insert a Incident entity into the database");
			return false;
		}
	}

	/**
	 * Retrieval Methods
	 */

	/**
	 * Retrieve user from database
	 * 
	 * @param associatedGoogleAccount
	 * @return {@link User)
	 */
	public User getUserFromDatabase(String associatedGoogleAccount) {
		try {
			return new getUserTask().execute(associatedGoogleAccount).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Retrieve List of Trips from database for the given user
	 * 
	 * @param associatedGoogleAccount
	 * @return ArrayList<Trip>
	 */
	public ArrayList<Trip> getAllTripsForAccount(String associatedGoogleAccount) {
		try {
			return new getAllTripsTask().execute(associatedGoogleAccount).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Retrieve List of Incidents from database for the given user
	 * 
	 * @param associatedGoogleAccount
	 * @return ArrayList<Incident>
	 */
	public ArrayList<Incident> getAllIncidentsForAccount(
			String associatedGoogleAccount) {
		try {
			return new getAllIncidentsTask().execute(associatedGoogleAccount)
					.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Retrieval Tasks
	 */

	/**
	 * Task associated with retrieving user objects from database
	 */
	class getUserTask extends AsyncTask<String, Void, User> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected User doInBackground(String... params) {
			SQLiteDatabase db = mInstance.getReadableDatabase();
			String query = Users.QUERY_GET_USER_WITH_GOOGLE_ACCOUNT_NAME;
			Cursor c = db.rawQuery(query, params);

			// c.moveToFirst();
			Log.v(TAG, String.valueOf(c.getPosition()));
			while (c.moveToNext() != false) {
				Log.v(TAG, "User Retrieved");
				User user = new User(c);
				Log.v(TAG, user.getGoogleAccountName());
				return user;
			}

			Log.v("Retrieve Failed", TAG);
			return null;
		}

		@Override
		protected void onPostExecute(User result) {
			super.onPostExecute(result);

		}

	}

	/**
	 * Task associated with retrieving a list of Trip objects from database
	 */
	class getAllTripsTask extends AsyncTask<String, Void, ArrayList<Trip>> {

		private ArrayList<Trip> trips;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected ArrayList<Trip> doInBackground(String... params) {
			SQLiteDatabase db = mInstance.getReadableDatabase();
			String query = Trips.QUERY_GET_TRIPS_FOR_ASSOCIATED_GOOGLE_ACCOUNT;
			Cursor c = db.rawQuery(query, params);

			if (c != null && c.moveToFirst()) {
				trips = new ArrayList<Trip>();
				while (c.moveToNext() != false) {
					Log.v(TAG, "Trip Retrieved");
					Trip trip = new Trip(c);
					trips.add(trip);
				}
			}
			return trips;
		}

		@Override
		protected void onPostExecute(ArrayList<Trip> result) {
			super.onPostExecute(result);
		}
	}

	/**
	 * Task associated with retrieving a list of Incident objects from database
	 */
	class getAllIncidentsTask extends
			AsyncTask<String, Void, ArrayList<Incident>> {

		private ArrayList<Incident> incidents;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected ArrayList<Incident> doInBackground(String... params) {
			SQLiteDatabase db = mInstance.getReadableDatabase();
			String query = Incidents.QUERY_GET_INCIDENTS_FOR_ASSOCIATED_GOOGLE_ACCOUNT;
			Cursor c = db.rawQuery(query, params);

			if (c != null && c.moveToFirst()) {
				incidents = new ArrayList<Incident>();
				while (c.moveToNext() != false) {
					Log.v(TAG, "Incident Retrieved");
					Incident incident = new Incident(c);
					incidents.add(incident);
				}
			}
			return incidents;
		}

		@Override
		protected void onPostExecute(ArrayList<Incident> result) {
			super.onPostExecute(result);
		}
	}
}
