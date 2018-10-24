Insert_update_delete_and_querying_using_Sqlite_database_content_provider_CursorLoader
===================================

This app displays a list of pets and their related data that the user inputs.
Used in a Udacity course in the Android Basics Nanodegree by Google.

Pre-requisites
--------------

- Android SDK v27
- Android Build Tools v28.0.3
- Android Support Repository v27.1.1

Getting Started
---------------

This sample uses the Gradle build system. To build this project, use the
"gradlew build" command or use "Import Project" in Android Studio.
Usages
----------
### Add below code in your manifest.xml file.
```
<application
  
..............
  <provider
            android:authorities="com.example.android.pets"
            android:name=".Data.PetProvider"
            android:exported="false"></provider>
            </application>
```

