package com.example.pawrescue;

import java.io.Serializable;

/*private static final String TABLE_ADOPTION_CREATE =
        "CREATE TABLE " + NotDefterimContract.AdoptionEntry.TABLE_NAME + " (" +
        NotDefterimContract.AdoptionEntry._ID + " INTEGER PRIMARY KEY," +
        NotDefterimContract.AdoptionEntry.COLUMN_STATUS+ " TEXT NOT NULL," +
        NotDefterimContract.AdoptionEntry.COLUMN_PET_ID + " INTEGER NOT NULL," +
        "FOREIGN KEY (" + NotDefterimContract.AdoptionEntry.COLUMN_PET_ID + ") REFERENCES pet(_ID)" +
        ");";*/

public class Adoption implements Serializable {
    public long id;
    public String status;
    public int petID;



    public Adoption(long id, String status, int petID) {
        this.id = id;
        this.status = status;
        this.petID = petID;
    }


}