package com.example.javamaps.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.javamaps.model.Place;

import java.util.List;

@Dao
public interface PlaceDao {

    @Query("SELECT * FROM Place") //filtreleme yapılabilir.
    List<Place> getAll(); //liste döndürecek.

    @Insert
    void insert(Place place);

    @Delete
    void delete(Place place);

}
