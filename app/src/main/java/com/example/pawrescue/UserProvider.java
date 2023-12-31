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

import com.example.pawrescue.data.ProviderContract;

public class UserProvider extends ContentProvider {
    SQLiteDatabase db;
    static final String CONTENT_AUTHORITY="pawrescue.com.userprovider";
    static final String PATH_USER="user";
    static final String PATH_PET="pet";
    static final String PATH_ADOPTION="adoption";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    static final Uri CONTENT_URI_USER = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_USER);
    static final Uri CONTENT_URI_PET = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_PET);
    static final Uri CONTENT_URI_ADOPTION = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_ADOPTION);
    static final UriMatcher matcher;
    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CONTENT_AUTHORITY,PATH_USER,1);
        matcher.addURI(CONTENT_AUTHORITY, PATH_USER + "/#", 2);
        matcher.addURI(CONTENT_AUTHORITY, PATH_PET , 3);
        matcher.addURI(CONTENT_AUTHORITY, PATH_ADOPTION , 4);
    }


    public static final String DATABASE_NAME="pawrescue.db";
    private static final int DATABASE_VERSION=1;

    private static final String TABLE_USER_CREATE =
            "CREATE TABLE " + ProviderContract.UserEntry.TABLE_NAME + " (" +
                    ProviderContract.UserEntry._ID + " INTEGER PRIMARY KEY," +
                    ProviderContract.UserEntry.COLUMN_USERNAME + " TEXT NOT NULL," +
                    ProviderContract.UserEntry.COLUMN_PASSWORD + " TEXT NOT NULL," +
                    ProviderContract.UserEntry.COLUMN_POINT + " INTEGER NOT NULL," +
                    ProviderContract.UserEntry.COLUMN_CITY + " TEXT NOT NULL," +
                    ProviderContract.UserEntry.COLUMN_AVATAR_INDEX + " INTEGER NOT NULL," +
                    ProviderContract.UserEntry.COLUMN_ADOPTION_ID + " INTEGER," +
                    "FOREIGN KEY (" + ProviderContract.UserEntry.COLUMN_ADOPTION_ID + ") REFERENCES adoption(_ID)" +
                    ");";


    private static final String TABLE_PET_CREATE =
            "CREATE TABLE " + ProviderContract.PetEntry.TABLE_NAME + " (" +
                    ProviderContract.PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ProviderContract.PetEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                    ProviderContract.PetEntry.COLUMN_SPECIES + " TEXT NOT NULL, " +
                    ProviderContract.PetEntry.COLUMN_GENDER + " TEXT, " +
                    ProviderContract.PetEntry.COLUMN_HEALTH + " TEXT, " +
                    ProviderContract.PetEntry.COLUMN_LOCATION + " TEXT, " +
                    ProviderContract.PetEntry.COLUMN_IMAGE_URL + " TEXT, " +
                    ProviderContract.PetEntry.COLUMN_OWNER_NAME + " TEXT, " +
                    ProviderContract.PetEntry.COLUMN_AGE + " TEXT NOT NULL" +
                    ");";

    private static final String TABLE_ADOPTION_CREATE =
            "CREATE TABLE " + ProviderContract.AdoptionEntry.TABLE_NAME + " (" +
                    ProviderContract.AdoptionEntry._ID + " INTEGER PRIMARY KEY," +
                    ProviderContract.AdoptionEntry.COLUMN_STATUS+ " TEXT NOT NULL," +
                    ProviderContract.AdoptionEntry.COLUMN_PET_ID + " INTEGER NOT NULL," +
                    "FOREIGN KEY (" + ProviderContract.AdoptionEntry.COLUMN_PET_ID + ") REFERENCES pet(_ID)" +
                    ");";

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
                        ProviderContract.UserEntry.TABLE_NAME,
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
                        ProviderContract.UserEntry.TABLE_NAME,
                        projection,
                        ProviderContract.UserEntry.COLUMN_USERNAME + "=?",
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
            case 3:
                Cursor cursorPet = db.query(
                        ProviderContract.UserEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );
                return cursorPet;
            case 4:
                Cursor cursorAdoption = db.query(
                        ProviderContract.UserEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );
                return cursorAdoption;
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
                    long newRowId = db.insert(ProviderContract.UserEntry.TABLE_NAME, null, values);

                    if (newRowId > 0) {
                        Uri _uri = ContentUris.withAppendedId(CONTENT_URI_USER,newRowId);
                        return _uri;
                    } else {
                        // Eklerken bir hata oluştu
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Hata işleme kodları
                }
            case 2:
                try {
                    long newRowId = db.insert(ProviderContract.UserEntry.TABLE_NAME, null, values);

                    if (newRowId > 0) {
                        Uri _uri = ContentUris.withAppendedId(CONTENT_URI_USER,newRowId);
                        return _uri;
                    } else {
                        // Eklerken bir hata oluştu
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Hata işleme kodları
                }
            case 3:
                try {
                    long newRowId = db.insert(ProviderContract.PetEntry.TABLE_NAME, null, values);

                    if (newRowId > 0) {
                        Uri _uri = ContentUris.withAppendedId(CONTENT_URI_PET,newRowId);
                        return _uri;
                    } else {
                        // Eklerken bir hata oluştu
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Hata işleme kodları
                }
            case 4:
                try {
                    long newRowId = db.insert(ProviderContract.AdoptionEntry.TABLE_NAME, null, values);

                    if (newRowId > 0) {
                        Uri _uri = ContentUris.withAppendedId(CONTENT_URI_ADOPTION,newRowId);
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

        int rowsUpdated;

        // URI'nin eşleştiği tabloya bağlı olarak güncelleme işlemi gerçekleştirilir
        int uriType = matcher.match(uri);
        switch (uriType) {
            case 1:
                // Tüm verileri güncelleme
                rowsUpdated = db.update(ProviderContract.UserEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 2:
                // Tüm verileri güncelleme
                rowsUpdated = db.update(ProviderContract.UserEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 3:
                // Tüm verileri güncelleme
                rowsUpdated = db.update(ProviderContract.PetEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 4:
                // Tüm verileri güncelleme
                rowsUpdated = db.update(ProviderContract.AdoptionEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        // Veritabanındaki veri değiştiğinde, bu değişikliği dinleyenleri bilgilendir
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
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
            db.execSQL(TABLE_PET_CREATE);
            db.execSQL(TABLE_ADOPTION_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
