package com.davisilvaprojetos.spotifyclone.dataacess;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.davisilvaprojetos.spotifyclone.model.Artistas;

import java.util.List;

@Dao
public interface ArtistsDao {
    @Query("SELECT * FROM artists_table")
    LiveData<List<Artistas>> getAll();

    @Query("SELECT * FROM artists_table WHERE name LIKE :nameArtists")
    LiveData<List<Artistas>> findByName(String nameArtists);

    @Insert
    void insert(List<Artistas> list);

    @Update
    void update(List<Artistas> list);

}
