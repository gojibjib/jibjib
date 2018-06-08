package de.cdvost.jibjib.repository.room;

import android.content.Context;

import java.util.List;

import de.cdvost.jibjib.repository.room.model.entity.Bird;

public interface IRoomDataBaseRepository {
    public List<Bird> getListOfBirds(Context context);
    public void storeBird(Bird bird, Context context);
    public boolean exists(int id, Context context);
    public Bird loadBirdById(int id, Context context);
}
