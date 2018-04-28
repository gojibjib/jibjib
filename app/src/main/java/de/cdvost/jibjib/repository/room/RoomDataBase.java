package de.cdvost.jibjib.repository.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import de.cdvost.jibjib.repository.room.model.dao.BirdDao;
import de.cdvost.jibjib.repository.room.model.entity.Bird;

@Database(entities = {Bird.class}, version = 1)
public abstract class RoomDataBase extends RoomDatabase {

    private static RoomDataBase INSTANCE;

    public abstract BirdDao birdDao();

    public static RoomDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, RoomDataBase.class, "jibjibDB")
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