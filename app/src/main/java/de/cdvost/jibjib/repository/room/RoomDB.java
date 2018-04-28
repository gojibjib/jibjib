package de.cdvost.jibjib.repository.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import de.cdvost.jibjib.repository.room.model.dao.BirdDao;
import de.cdvost.jibjib.repository.room.model.entity.Bird;

@Database(entities = {Bird.class}, version = 1)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB INSTANCE;

    public abstract BirdDao birdDao();

    public static RoomDB getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, RoomDB.class, "jibjibDB")
                    // recreate the database if necessary
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}