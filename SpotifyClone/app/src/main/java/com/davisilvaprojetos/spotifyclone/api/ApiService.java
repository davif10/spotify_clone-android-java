package com.davisilvaprojetos.spotifyclone.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    /*
    https://api.vagalume.com.br/search.art?q=Skank&limit=5
    https://api.vagalume.com.br/search.excerpt?q=vamos%20fugir&limit=5
    https://api.vagalume.com.br/search.artmus?q=Skank%20Vamos%20Fugir&limit=5
    https://api.vagalume.com.br/search.alb?q=U218%20Singles&limit=5
     */

    @GET("search.art")
    Call<String>recuperarDadosArtista();
}
