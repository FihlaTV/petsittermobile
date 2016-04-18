package zekisanmobile.petsitter.model;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = PetSitterDatabase.NAME, version = PetSitterDatabase.VERSION,
        foreignKeysSupported = true)
public class PetSitterDatabase {

    public static final String NAME = "PetSitter";

    public static final int VERSION = 1;

}
