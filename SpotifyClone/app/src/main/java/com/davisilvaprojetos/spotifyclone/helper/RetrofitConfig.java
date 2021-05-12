package com.davisilvaprojetos.spotifyclone.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    public static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(ApiConfig.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
