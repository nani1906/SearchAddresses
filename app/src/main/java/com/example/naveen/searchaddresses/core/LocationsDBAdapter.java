package com.example.naveen.searchaddresses.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class LocationsDBAdapter {
    public static final String ROW_ID = "_id";
    public static final String ID = "id";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String DESCRIPTION = "description";
    public static final String TITLE = "title";
    private static final String DATABASE_TABLE = "locations";
    public DatabaseHelper mDbHelper;
    public SQLiteDatabase mDb;
    private final Context mCtx;

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * @param ctx
     * the Context within which to work
     */
    public LocationsDBAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the cars database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     *
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException
     *             if the database could be neither opened or created
     */
    public LocationsDBAdapter open(DatabaseHelper dbHelper, SQLiteDatabase db) throws SQLException {
        Log.e("LocationDBA","=============OPEN");
        this.mDbHelper = dbHelper;
        this.mDb = db;
        return this;
    }

    /**
     * close return type: void
     */
    public void close() {
        this.mDbHelper.close();
    }

    /**
     * Create a new car. If the car is successfully created return the new
     * rowId for that car, otherwise return a -1 to indicate failure.
     *
     * @return rowId or -1 if failed
     */
    public long createLocation(int id, Double latitude, Double longitude, String description, String title){
        ContentValues initialValues = new ContentValues();
        initialValues.put(ID, id);
        initialValues.put(LATITUDE, latitude);
        initialValues.put(LONGITUDE, longitude);
        initialValues.put(DESCRIPTION, description);
        initialValues.put(TITLE, title);
        return this.mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Return a Cursor over the list of all cars in the database
     *
     * @return Cursor over all cars
     */

    public ArrayList<SelectedLocation> getAllLocations() {
        ArrayList<SelectedLocation> locationsList = new ArrayList<SelectedLocation>();
        Cursor cursor = this.mDb.query(DATABASE_TABLE, new String[] { ID, LATITUDE, LONGITUDE, DESCRIPTION, TITLE }, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                // Adding location to list
                locationsList.add(new SelectedLocation(Integer.parseInt(cursor.getString(0)),
                        cursor.getDouble(1),
                        cursor.getDouble(2),
                        cursor.getString(3),
                        cursor.getString(4)));
            } while (cursor.moveToNext());
        }

        // return locations list
        return locationsList;
    }

    /**
     * Return a Cursor positioned at the car that matches the given rowId
     * @return Cursor positioned to matching car, if found
     * @throws SQLException if car could not be found/retrieved
     */

    public SelectedLocation getLocation(int id) throws SQLException {

        Cursor mCursor =

                this.mDb.query(true, DATABASE_TABLE, new String[] { ID, LATITUDE, LONGITUDE, DESCRIPTION, TITLE}, ID + "=" + id, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return new SelectedLocation(Integer.parseInt(mCursor.getString(0)),
                mCursor.getDouble(1),
                mCursor.getDouble(2),
                mCursor.getString(3),
                mCursor.getString(4));
    }
}
