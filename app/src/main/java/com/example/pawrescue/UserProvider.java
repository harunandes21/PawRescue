package com.example.pawrescue;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pawrescue.data.NotDefterimContract;

import java.util.regex.Matcher;

public class UserProvider extends ContentProvider {
    SQLiteDatabase db;
    static final String CONTENT_AUTHORITY="pawrescue.com.userprovider";
    static final String PATH_USER="user";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_USER);
    static final UriMatcher matcher;
    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CONTENT_AUTHORITY,PATH_USER,1);
        matcher.addURI(CONTENT_AUTHORITY, PATH_USER + "/#", 2);
    }


    public static final String DATABASE_NAME="pawrescue.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_USER_CREATE =
            "CREATE TABLE " + NotDefterimContract.UserEntry.TABLE_NAME + " (" +
                    NotDefterimContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NotDefterimContract.UserEntry.COLUMN_USERNAME + " TEXT NOT NULL, " +
                    NotDefterimContract.UserEntry.COLUMN_PASSWORD + " TEXT NOT NULL, " +
                    NotDefterimContract.UserEntry.COLUMN_CITY + " TEXT, " +
                    NotDefterimContract.UserEntry.COLUMN_NEIGHBORHOOD + " TEXT);";
    @Override
    public boolean onCreate() {
        DatabaseHelper helper = new DatabaseHelper(getContext());
        db = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (matcher.match(uri)){
            case 1:
                Cursor cursor = db.query(
                        NotDefterimContract.UserEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );
                return cursor;
            case 2:
                String usernameToCheck = uri.getLastPathSegment();
                Cursor usernameCursor = db.query(
                        NotDefterimContract.UserEntry.TABLE_NAME,
                        projection,
                        NotDefterimContract.UserEntry.COLUMN_USERNAME + "=?",
                        new String[]{usernameToCheck},
                        null,
                        null,
                        null
                );
                if (usernameCursor != null) { // Check for null
                    return usernameCursor;
                } else {
                    return null;
                }
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (matcher.match(uri)){
            case 1:
                try {
                    long newRowId = db.insert(NotDefterimContract.UserEntry.TABLE_NAME, null, values);

                    if (newRowId > 0) {
                        Uri _uri = ContentUris.withAppendedId(CONTENT_URI,newRowId);
                        return _uri;
                    } else {
                        // Eklerken bir hata oluştu
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Hata işleme kodları
                }
        }

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_USER_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
