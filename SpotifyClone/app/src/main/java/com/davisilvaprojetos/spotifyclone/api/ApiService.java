package com.davisilvaprojetos.spotifyclone.api;

import com.davisilvaprojetos.spotifyclone.model.Artistas;
import com.davisilvaprojetos.spotifyclone.model.Type;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    /*
    	https://api.spotify.com/v1/recommendations/available-genre-seeds
     */

    @GET("davif10/spotify_clone-android-java/main/musica")
    Call<Type>recuperarDadosArtista();


}
