package com.davisilvaprojetos.spotifyclone.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.davisilvaprojetos.spotifyclone.model.Artistas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Artistas.class}, version = 1)
abstract class SpotifyRoomDatabase extends RoomDatabase {

    public abstract ArtistsDao artistsDao();
    private static volatile SpotifyRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static SpotifyRoomDatabase getDatabase(Context context){
        if(INSTANCE == null){
            synchronized (SpotifyRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SpotifyRoomDatabase.class,
                            "spotify_database")
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
