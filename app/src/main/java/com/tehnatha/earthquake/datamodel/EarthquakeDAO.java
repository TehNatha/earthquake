package com.tehnatha.earthquake.datamodel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface EarthquakeDAO {
    @Query("SELECT * FROM earthquake")
    LiveData<List<Earthquake>> getAll();

    @Query("SELECT * FROM earthquake WHERE eqid == (:id)")
    LiveData<List<Earthquake>> get(String id);

    @Query("SELECT * FROM earthquake WHERE eqid IN (:ids)")
    LiveData<List<Earthquake>> getAllByIds(String[] ids);

    @Insert(onConflict = REPLACE)
    void insertAll(Earthquake... earthquakes);

    @Delete
    void delete(Earthquake earthquake);
}

