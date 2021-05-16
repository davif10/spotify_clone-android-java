package com.davisilvaprojetos.spotifyclone.repository;

import com.davisilvaprojetos.spotifyclone.helper.RetrofitConfig;
import com.davisilvaprojetos.spotifyclone.model.Artistas;
import com.davisilvaprojetos.spotifyclone.model.Type;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ArtistRepository{
    private final RetrofitConfig retrofitConfig;
    private final List<Artistas> artistList = new ArrayList<>();

    public ArtistRepository(RetrofitConfig retrofitConfig) {
        this.retrofitConfig = retrofitConfig;
    }


    public List<Artistas> getArtistList(){
        try {
            Response request = retrofitConfig.retrofitApi().recuperarDadosArtista().execute();
            if(request.isSuccessful()){
                Type type = (Type) request.body();
                if(type != null){
                    artistList.addAll(type.getArtist());
                }
            }else{
                System.out.println("Erro: "+ request.errorBody());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return artistList;
    }

}
