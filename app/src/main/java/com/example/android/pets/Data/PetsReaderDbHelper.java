package com.example.android.pets.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.pets.Data.PetsContract.PetsEntry;

public class PetsReaderDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = PetsReaderDbHelper.class.getSimpleName();
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    /** Name of the database file */
    public static final String DATABASE_NAME = "shelter.db";





    /**
     * Drop Table Query for upgrade database
     */
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PetsEntry.TABLE_NAME;

    /**
     *  Constructs a new instance of {@link PetsReaderDbHelper}.
     *
     * @param context of the app
     */
    public PetsReaderDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
                String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + PetsEntry.TABLE_NAME + " ("
                               + PetsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                               + PetsEntry.COLUMN_NAME + " TEXT NOT NULL, "
                                + PetsEntry.COLUMN_BREED + " TEXT, "
                                + PetsEntry.COLUMN_GENDER + " INTEGER NOT NULL, "
                               + PetsEntry.COLUMN_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
          db.execSQL(SQL_DELETE_ENTRIES);
          onCreate(db);
    }
}
