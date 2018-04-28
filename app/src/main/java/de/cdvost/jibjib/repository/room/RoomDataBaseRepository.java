package de.cdvost.jibjib.repository.room;

import android.content.Context;

import java.util.Arrays;
import java.util.List;

import de.cdvost.jibjib.repository.room.model.entity.Bird;

public class RoomDataBaseRepository implements IRoomDataBaseRepository {

    @Override
    public List<Bird> getListOfBirds(Context context) {
        RoomDB roomDB = RoomDB.getInstance(context);
        List<Bird> birds = roomDB.birdDao().getAll();
        return birds;
    }
}
