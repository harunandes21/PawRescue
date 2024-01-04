package com.example.pawrescue.data;

import android.provider.BaseColumns;

public class ProviderContract {

    public static final class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_POINT = "point";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_ADOPTION_ID = "adoptionId";
        public static final String COLUMN_AVATAR_INDEX = "avatarIndex";

    }

    public static final class PetEntry implements BaseColumns {
        public static final String TABLE_NAME = "pet";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SPECIES = "species";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_HEALTH = "health";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_OWNER_NAME = "ownerName";
        public static final String COLUMN_IMAGE_URL = "url";
    }

    public static final class AdoptionEntry implements BaseColumns {
        public static final String TABLE_NAME = "adoption";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_PET_ID = "pet_id";

    }
}
