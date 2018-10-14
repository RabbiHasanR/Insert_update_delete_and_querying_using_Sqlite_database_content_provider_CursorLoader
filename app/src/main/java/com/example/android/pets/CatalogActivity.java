/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.util.Log;
import android.widget.AdapterView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.pets.Adapter.PetCursorAdapter;
import com.example.android.pets.Data.PetsContract.PetsEntry;
import com.example.android.pets.Data.PetsReaderDbHelper;


/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int PET_LOADER=0;
    private PetCursorAdapter petCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        // Find the ListView which will be populated with the pet data
        ListView listView=(ListView)findViewById(R.id.list_view);
        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);
        petCursorAdapter=new PetCursorAdapter(this,null);
        listView.setAdapter(petCursorAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //create new intent to go to new activity {@link EditorActivity}
                Intent intent=new Intent(CatalogActivity.this,EditorActivity.class);
                //from the content uri that represents the specific pet that was clicked on,
                //by appending the id (passed as input to this method) on to the link {@link PetsEntry#ContentURI}
                //for example uri would be "content://com.example.android.pets/pets/2"
                //if the pet with id 2 was clicked on
                Uri currentPetUri= ContentUris.withAppendedId(PetsEntry.CONTENT_URI,id);
                //set the uri on the data field of the intent
                intent.setData(currentPetUri);
                //launch the {@link EditorActivity}
                startActivity(intent);
            }
        });

        //kick of the loader
        getLoaderManager().initLoader(PET_LOADER,null,this);


    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
  /*  private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.


        // Create and/or open a database to read from it
        //SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
       // Cursor cursor = db.rawQuery("SELECT * FROM " + PetsEntry.TABLE_NAME, null);

        // Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection={PetsEntry.COLUMN_ID,
                PetsEntry.COLUMN_NAME,
                PetsEntry.COLUMN_BREED,
                };
        //String selection=PetsEntry.COLUMN_GENDER+"=?";
        //String[]selectionArgs={"1"};
        /*Cursor cursor=db.query(PetsEntry.TABLE_NAME,
                projection
                ,null,
                null,
                null,
                null,
                null);
                */

        // Find the ListView which will be populated with the pet data
        /*ListView listView=(ListView)findViewById(R.id.list_view);
        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);
        Cursor cursor=getContentResolver().query(PetsEntry.CONTENT_URI,projection,null,null,null);
        PetCursorAdapter petCursorAdapter=new PetCursorAdapter(this,cursor);
        listView.setAdapter(petCursorAdapter);
        /*TextView displayView = (TextView) findViewById(R.id.text_view_pet);
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            displayView.setText("Number of rows in pets database table: " + cursor.getCount());
            displayView.append("\n"+PetsEntry._ID + " - " +
                    PetsEntry.COLUMN_NAME + " - "+
                    PetsEntry.COLUMN_BREED+" - "+
                    PetsEntry.COLUMN_GENDER+" - "+
                    PetsEntry.COLUMN_WEIGHT+"\n");

            // Figure out the index of each column
            int idColumnIndex=cursor.getColumnIndex(PetsEntry.COLUMN_ID);
            int nameColumnIndex=cursor.getColumnIndex(PetsEntry.COLUMN_NAME);
            int breedColumnIndex=cursor.getColumnIndex(PetsEntry.COLUMN_BREED);
            int genderColumnIndex=cursor.getColumnIndex(PetsEntry.COLUMN_GENDER);
            int weightColumnIndex=cursor.getColumnIndex(PetsEntry.COLUMN_WEIGHT);
            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()){

                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentId=cursor.getInt(idColumnIndex);
                String currentName=cursor.getString(nameColumnIndex);
                String currentBreed=cursor.getString(breedColumnIndex);
                int currentGender=cursor.getInt(genderColumnIndex);
                int currentWeight=cursor.getInt(weightColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" +currentId + " - " +
                        currentName+ " - " +currentBreed+ " - " +currentGender+ " - " +currentWeight));


            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }*/

    private void insertPet(){
        //add data in database example
        ContentValues values = new ContentValues();
        values.put(PetsEntry.COLUMN_NAME, "Garfield");
        values.put(PetsEntry.COLUMN_BREED, "Tabby");
        values.put(PetsEntry.COLUMN_GENDER, PetsEntry.GENDER_MALE);
        values.put(PetsEntry.COLUMN_WEIGHT, 7);
        Uri uri=getContentResolver().insert(PetsEntry.CONTENT_URI,values);

        if(uri==null){
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, getString(R.string.editor_insert_pet_failed), Toast.LENGTH_SHORT).show();
        }
        else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, getString(R.string.editor_insert_pet_successful), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Helper method to delete all pets in the database.
     */
    private void deleteAllPets() {
        int rowsDeleted = getContentResolver().delete(PetsEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteAllPets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        //define projection that specific the column from the table we care about.
        String[] projection={PetsEntry.COLUMN_ID,
                PetsEntry.COLUMN_NAME,
                PetsEntry.COLUMN_BREED,
        };

        //this loader will execute the ContentProvider query method on a background method
        return new CursorLoader(this
                ,PetsEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        petCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        petCursorAdapter.swapCursor(null);

    }



}
