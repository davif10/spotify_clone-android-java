package com.davisilvaprojetos.spotifyclone.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davisilvaprojetos.spotifyclone.api.ApiService;
import com.davisilvaprojetos.spotifyclone.helper.ApiConfig;
import com.davisilvaprojetos.spotifyclone.helper.RetrofitConfig;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

import com.davisilvaprojetos.spotifyclone.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {
    private Retrofit retrofit;
    private String resultado;
    String pes ="skank";

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        retrofit = RetrofitConfig.getRetrofit();


        //recuperarDadosArtista(pes);
        //recuperarDadosMusica(pes);



        return view;
    }

    private void recuperarDadosArtista(String pesquisa){
        String q = pesquisa.replaceAll(" ","-");
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.recuperarDadosArtista(
                ApiConfig.CHAVE_API,
                pesquisa,
                "1"

        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    resultado = response.body();
                    System.out.println("Resultado: "+resultado);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("Erro: "+t.getMessage());
            }
        });
    }


    private void recuperarDadosMusica(String pesquisa){
        String q = pesquisa.replaceAll(" ","-");
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.recuperarDadosArtista(
                ApiConfig.CHAVE_API,
                q,
                "5"

        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    resultado = response.body();
                    System.out.println("Resultado: "+resultado);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("Erro: "+t.getMessage());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onStop() {
        super.onStop();
    }
}