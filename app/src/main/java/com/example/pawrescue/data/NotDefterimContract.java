package com.example.pawrescue.data;

import android.provider.BaseColumns;

public class NotDefterimContract {

    public static final class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_NEIGHBORHOOD = "neighborhood";



    }
}
