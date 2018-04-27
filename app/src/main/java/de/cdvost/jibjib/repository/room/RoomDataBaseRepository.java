package de.cdvost.jibjib.repository.room;

import java.util.Arrays;

public class RoomDataBaseRepository implements IRoomDataBaseRepository {
    @Override
    public Object getListOfBirds() {
        return Arrays.asList("Meise", "Spatz", "Amsel");
    }
}
