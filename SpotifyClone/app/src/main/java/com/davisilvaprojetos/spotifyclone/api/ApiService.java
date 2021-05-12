package com.davisilvaprojetos.spotifyclone.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    /*
    	https://api.spotify.com/v1/recommendations/available-genre-seeds
     */

    @GET("search.art")
    Call<String>recuperarDadosArtista(
            @Query("apikey") String apikey,
            @Query("q") String q,
            @Query("limit") String limit
    );


}
