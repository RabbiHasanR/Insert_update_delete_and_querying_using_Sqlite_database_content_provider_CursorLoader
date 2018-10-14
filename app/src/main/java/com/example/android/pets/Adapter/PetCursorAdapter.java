package com.example.android.pets.Adapter;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.pets.Data.PetsContract;
import com.example.android.pets.R;

public class PetCursorAdapter extends CursorAdapter {

    public PetCursorAdapter(Context context,Cursor cursor){
        super(context,cursor,0);

    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView petNameTextView = (TextView) view.findViewById(R.id.pet_name);
        TextView petBreedTextView = (TextView) view.findViewById(R.id.pet_breed);

        // Find the columns of pet attributes that we're interested in
                int nameColumnIndex = cursor.getColumnIndex(PetsContract.PetsEntry.COLUMN_NAME);
                int breedColumnIndex = cursor.getColumnIndex(PetsContract.PetsEntry.COLUMN_BREED);
        // Read the pet attributes from the Cursor for the current pet
                String petName = cursor.getString(nameColumnIndex);
                String petBreed = cursor.getString(breedColumnIndex);

        // If the pet breed is empty string or null, then use some default text
        // that says "Unknown breed", so the TextView isn't blank.
        if (TextUtils.isEmpty(petBreed)) {
            petBreed = context.getString(R.string.unknown_breed);
        }
                // Update the TextViews with the attributes for the current pet
        petNameTextView.setText(petName);
        petBreedTextView.setText(petBreed);

    }
}
