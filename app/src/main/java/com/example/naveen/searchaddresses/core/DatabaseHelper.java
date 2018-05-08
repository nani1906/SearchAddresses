package com.example.naveen.searchaddresses.core;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "pickup_points"; //$NON-NLS-1$

    public static int DATABASE_VERSION = 1;

    private static DatabaseHelper instance;

    private static final String CREATE_TABLE_LOCATIONS =
            "create table locations (_id integer primary key autoincrement, " //$NON-NLS-1$
                    + LocationsDBAdapter.ID+ " INT," //$NON-NLS-1$
                    + LocationsDBAdapter.LATITUDE+ " REAL," //$NON-NLS-1$
                    + LocationsDBAdapter.LONGITUDE+ " REAL," //$NON-NLS-1$
                    + LocationsDBAdapter.DESCRIPTION+ " TEXT," //$NON-NLS-1$
                    + LocationsDBAdapter.TITLE+ " TEXT" + ");"; //$NON-NLS-1$ //$NON-NLS-2$

    private Context context;
    public SQLiteDatabase db;

    /**
     * Constructor
     * @param context
     */

    public static DatabaseHelper getInstance(Context context){
        if(instance == null) {
            instance = new DatabaseHelper(context);
            try {
                instance.open();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_LOCATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion)
    {
        // Adding any table mods to this guy here
        // Drop older table if existed
        Log.i("newVersion","onUpgrade================> "+newVersion);
        Log.i("oldVersion","onUpgrade================> "+oldVersion);
        if(newVersion < oldVersion){
            return;
        }
        Log.i("DBAdapter","================> ON UPGRADE");
        db.execSQL("DROP TABLE IF EXISTS locations");

        // Create tables again
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        return;
    }

    /**
     * open the db
     * @return this
     * @throws SQLException
     * return type: DatabaseHelper
     */
    public DatabaseHelper open() throws Exception
    {
        Log.i("DBAdapter","===========OPENING Local DB");
        this.db = this.getWritableDatabase();
        return this;
    }

    /**
     * close the db
     * return type: void
     */
    public void close()
    {
        this.close();
    }
}

