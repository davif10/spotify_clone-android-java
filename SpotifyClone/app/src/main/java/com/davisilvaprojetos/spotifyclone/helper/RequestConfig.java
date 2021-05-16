package com.davisilvaprojetos.spotifyclone.helper;

import com.davisilvaprojetos.spotifyclone.api.ApiService;
import com.davisilvaprojetos.spotifyclone.model.Artistas;
import com.davisilvaprojetos.spotifyclone.model.Type;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RequestConfig {
    private Type typeMusic;
    private final List<Artistas> listArtist = new ArrayList<>();


    public List<Artistas> recoverArtistData() {
        Retrofit retrofit = RetrofitConfig.getRetrofit();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.recuperarDadosArtista(

        ).enqueue(new Callback<Type>() {
            @Override
            public void onResponse(Call<Type> call, Response<Type> response) {
                if (response.isSuccessful()) {
                    typeMusic = response.body();
                    if (typeMusic != null) {
                        listArtist.addAll(typeMusic.getArtist());
                    }
                }
            }

            @Override
            public void onFailure(Call<Type> call, Throwable t) {
                System.out.println("Erro: " + t.getMessage());
            }
        });

        return listArtist;
    }
}
