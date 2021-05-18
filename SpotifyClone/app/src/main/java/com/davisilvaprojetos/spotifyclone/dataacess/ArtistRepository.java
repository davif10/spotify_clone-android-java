package com.davisilvaprojetos.spotifyclone.dataacess;


import android.content.Context;

import androidx.lifecycle.LiveData;

import com.davisilvaprojetos.spotifyclone.helper.RetrofitConfig;
import com.davisilvaprojetos.spotifyclone.model.Artistas;
import com.davisilvaprojetos.spotifyclone.model.Type;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ArtistRepository {
    private RetrofitConfig retrofitConfig = new RetrofitConfig();
    private final List<Artistas> artistList = new ArrayList<>();
    private List<Artistas> artistListWeb = new ArrayList<>();
    private List<Artistas> artistListFinal = new ArrayList<>();
    private Context context;
    private ArtistsDao artistsDao;
    private LiveData<List<Artistas>> allArtists;
    SpotifyRoomDatabase db;

    public ArtistRepository(RetrofitConfig retrofitConfig, Context context) {
        this.retrofitConfig = retrofitConfig;
        this.context = context;
    }

    public ArtistRepository(Context context) {
        this.context = context;
    }

    public List<Artistas> getArtistList() {

        artistListWeb = getArtistListWeb();
        artistListFinal = saveBD(context, artistListWeb);

        return artistListFinal;
    }

    public List<Artistas> getArtistListWeb() {
        try {
            Response request = retrofitConfig.retrofitApi().recuperarDadosArtista().execute();
            if (request.isSuccessful()) {
                Type type = (Type) request.body();
                if (type != null) {
                    artistList.addAll(type.getArtist());
                }
            } else {
                System.out.println("Erro no Repository: " + request.errorBody());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return artistList;
    }

    public List<Artistas> saveBD(Context context, List<Artistas> list) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                db = SpotifyRoomDatabase.getDatabase(context);
                artistsDao = db.artistsDao();
                allArtists = artistsDao.getAll();
                if (allArtists.getValue() != list) {
                    artistsDao.update(list);
                }
            }
        }.start();

        return list;
    }


}
