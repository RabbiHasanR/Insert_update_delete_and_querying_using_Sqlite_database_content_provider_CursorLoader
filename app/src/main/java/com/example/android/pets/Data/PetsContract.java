package com.example.android.pets.Data;

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

public final class PetsContract {
    // To prevent someone from accidentally instantiating the contract class,

    //initialize content authority for content uri
    public static final String CONTENT_AUTHORITY = "com.example.android.pets";
    //initialize base content uri for content uri
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    //initialize the content uri paths(table name) for content uri
    public static final String PATH_PETS = "pets";

    // give it an empty constructor.
    private PetsContract(){}

    /** URI matcher code for the content URI for the pets table */
    public static final int PETS = 100;

    /** URI matcher code for the content URI for a single pet in the pets table */
    public static final int PET_ID = 101;

    /**
     * Inner class that defines constant values for the pets database table.
     * Each entry in the table represents a single pet.
     */
     public static final class PetsEntry implements BaseColumns{
        //initialize the complete content uri
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        public static final String TABLE_NAME="pets";

         public static final String COLUMN_ID=BaseColumns._ID;
         public static final String COLUMN_NAME="name";
         public static final String COLUMN_BREED="breed";
         public static final String COLUMN_GENDER="gender";
         public static final String COLUMN_WEIGHT="weight";


         /**
          * possible values for the gender of the pets
          */

         public static final int GENDER_MALE=1;
         public static final int GENDER_FEMALE=2;
         public static final int GENDER_UNKNOWN=0;

        /**
         * Returns whether or not the given gender is {@link #GENDER_UNKNOWN}, {@link #GENDER_MALE},
         * or {@link #GENDER_FEMALE}.
         */
        public static boolean isValidGender(int gender) {
            if (gender == GENDER_UNKNOWN || gender == GENDER_MALE || gender == GENDER_FEMALE) {
                return true;
            }
            return false;
        }
     }
}
