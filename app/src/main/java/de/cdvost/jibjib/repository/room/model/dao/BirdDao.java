package de.cdvost.jibjib.repository.room.model.dao;

import java.util.List;

import de.cdvost.jibjib.repository.room.model.entity.Bird;

@Dao
public class BirdDao {

    @Query("SELECT * FROM bird")
    List<Person> getAll();

    @Query("SELECT * FROM bird WHERE id IN (:ids)")
    List<Person> loadAllByIds(int[] ids);

    @Query("SELECT * FROM bird WHERE name LIKE :name LIMIT 1")
    Person findByName(String name);

    @Insert
    void insertAll(Bird... bird);

    @Delete
    void delete(Bird bird);

    @Query("delete from bird")
    void removeAllBirds();

    @Query("SELECT count(*) FROM bird")
    int count();
}

}
