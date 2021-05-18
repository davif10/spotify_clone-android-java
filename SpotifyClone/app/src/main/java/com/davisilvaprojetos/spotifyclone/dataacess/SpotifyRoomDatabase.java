package com.davisilvaprojetos.spotifyclone.dataacess;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.davisilvaprojetos.spotifyclone.model.Artistas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Artistas.class}, version = 1, exportSchema = false)
abstract class SpotifyRoomDatabase extends RoomDatabase {

    public abstract ArtistsDao artistsDao();

    private static volatile SpotifyRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static SpotifyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SpotifyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SpotifyRoomDatabase.class, "spotify-database.db")
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
