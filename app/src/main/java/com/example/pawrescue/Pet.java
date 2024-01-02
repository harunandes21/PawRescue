package com.example.pawrescue;

import java.io.Serializable;

/*    private static final String TABLE_PET_CREATE =
            "CREATE TABLE " + NotDefterimContract.PetEntry.TABLE_NAME + " (" +
                    NotDefterimContract.PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NotDefterimContract.PetEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                    NotDefterimContract.PetEntry.COLUMN_SPECIES + " TEXT NOT NULL, " +
                    NotDefterimContract.PetEntry.COLUMN_GENDER + " TEXT, " +
                    NotDefterimContract.PetEntry.COLUMN_HEALTH + " TEXT, " +
                    NotDefterimContract.PetEntry.COLUMN_LOCATION + " TEXT, " +
                    NotDefterimContract.PetEntry.COLUMN_IMAGE_URL + " TEXT, " +
                    NotDefterimContract.PetEntry.COLUMN_OWNER_NAME + " TEXT, " +
                    NotDefterimContract.PetEntry.COLUMN_AGE + " TEXT NOT NULL" +
                    ");";*/

public class Pet implements Serializable {
    public long id;
    public String name;
    public String species;
    public String gender;
    public String health;
    public String location;
    public String url;
    public String ownerName;
    public String age;



    public Pet(long id,String name, String species,String age) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.age = age;
    }


}