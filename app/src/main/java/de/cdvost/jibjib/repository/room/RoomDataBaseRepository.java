package de.cdvost.jibjib.repository.room;

import android.content.Context;

import java.util.List;

import de.cdvost.jibjib.repository.room.model.entity.Bird;

public class RoomDataBaseRepository implements IRoomDataBaseRepository {

    @Override
    public List<Bird> getListOfBirds(Context context) {
        RoomDB roomDB = RoomDB.getInstance(context);
        return roomDB.birdDao().getAll();
    }

    @Override
    public void storeBird(Bird bird, Context context) {
        RoomDB roomDB = RoomDB.getInstance(context);
        roomDB.birdDao().insertAll(bird);
    }

    @Override
    public boolean exists(int id, Context context){
        RoomDB roomDB = RoomDB.getInstance(context);
        List<Bird> birds = roomDB.birdDao().loadAllByIds(new int[]{id});
        return !birds.isEmpty();
    }

    @Override
    public Bird loadBirdById(int id, Context context){
        RoomDB roomDB = RoomDB.getInstance(context);
        List<Bird> birds = roomDB.birdDao().loadAllByIds(new int[]{id});
        if(birds.size()==1){
            return birds.iterator().next();
        }
        return null;
    }
}
