package de.cdvost.jibjib.repository.room.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import de.cdvost.jibjib.repository.room.model.entity.Bird;

@Dao
public interface BirdDao {

    @Query("SELECT * FROM bird")
    List<Bird> getAll();

    @Query("SELECT * FROM bird WHERE id IN (:ids)")
    List<Bird> loadAllByIds(int[] ids);

    @Query("SELECT * FROM bird WHERE name LIKE :name LIMIT 1")
    Bird findByName(String name);

    @Insert
    void insertAll(Bird... bird);

    @Delete
    void delete(Bird bird);

    @Query("delete from bird")
    void removeAllBirds();

    @Query("SELECT count(*) FROM bird")
    int count();
}

