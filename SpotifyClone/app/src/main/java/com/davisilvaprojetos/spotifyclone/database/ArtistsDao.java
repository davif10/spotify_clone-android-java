package com.davisilvaprojetos.spotifyclone.database;

import androidx.room.Query;

import com.davisilvaprojetos.spotifyclone.model.Artistas;

import java.util.List;

public interface ArtistsDao {
    @Query("SELECT * FROM artists_table")
    List<Artistas> getAll();

    @Query("SELECT * FROM artists_table WHERE name LIKE :nameArtists")
    List<Artistas> findByName(String nameArtists);

}
