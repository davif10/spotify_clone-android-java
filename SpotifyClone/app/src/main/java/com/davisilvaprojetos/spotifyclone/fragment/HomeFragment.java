package com.davisilvaprojetos.spotifyclone.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davisilvaprojetos.spotifyclone.adapter.AdapterArtista;
import com.davisilvaprojetos.spotifyclone.adapter.AdapterGenero;
import com.davisilvaprojetos.spotifyclone.api.ApiService;
import com.davisilvaprojetos.spotifyclone.helper.RetrofitConfig;
import com.davisilvaprojetos.spotifyclone.model.Artistas;
import com.davisilvaprojetos.spotifyclone.model.Type;

import com.davisilvaprojetos.spotifyclone.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {
    private Retrofit retrofit;
    private final List<Artistas> listArtist = new ArrayList<>();
    private final List<Artistas> listDifferentGender = new ArrayList<>();
    private Type typeMusic;
    private RecyclerView recyclerArtist, recyclerDifferentGender, recyclerGenre;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerArtist = view.findViewById(R.id.recyclerArtist);
        recyclerDifferentGender = view.findViewById(R.id.recyclerGenerosDiferentes);
        recyclerGenre = view.findViewById(R.id.recyclerEscolhaGenero);

        retrofit = RetrofitConfig.getRetrofit();

        recuperarDadosArtista();
        configRecyclerViewGeneros();
        return view;
    }
    private void recuperarDadosArtista(){
        listArtist.clear();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.recuperarDadosArtista(

        ).enqueue(new Callback<Type>() {
            @Override
            public void onResponse(Call<Type> call, Response<Type> response) {
                if(response.isSuccessful()){
                    typeMusic = response.body();
                    if(typeMusic != null){
                        listArtist.addAll(typeMusic.getArtist());
                        configRecyclerViewArtist();
                        criarGeneroDiferente(listArtist);

                    }

                }

            }

            @Override
            public void onFailure(Call<Type> call, Throwable t) {
                System.out.println("Erro: "+t.getMessage());
            }
        });
    }

    public void configRecyclerViewArtist(){
        AdapterArtista adapterArtista = new AdapterArtista(listArtist, getActivity());
        RecyclerView.LayoutManager layoutManagerHorizontal = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
        );

        recyclerArtist.setHasFixedSize(true);
        recyclerArtist.setLayoutManager(layoutManagerHorizontal);
        recyclerArtist.setAdapter(adapterArtista);


    }

    private void criarGeneroDiferente(List<Artistas> artistas){
        for(int i =0; i< artistas.size();i++){
            if(!artistas.get(i).getGenre().equals("pop")){
                listDifferentGender.add(artistas.get(i));
            }
        }
        configRecyclerViewListaDiferente();
    }

    public void configRecyclerViewListaDiferente(){
        AdapterArtista adapterDifferent = new AdapterArtista(listDifferentGender, getActivity());
        RecyclerView.LayoutManager layoutManagerHorizontal = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
        );

        recyclerDifferentGender.setHasFixedSize(true);
        recyclerDifferentGender.setLayoutManager(layoutManagerHorizontal);
        recyclerDifferentGender.setAdapter(adapterDifferent);


    }


    public void configRecyclerViewGeneros(){
        String[] generos = {"rock","rap","pop","mpb","funk","sertanejo","reggae"} ;
        AdapterGenero adapterGenero = new AdapterGenero(generos);
        RecyclerView.LayoutManager layoutManagerHorizontal = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
        );

        recyclerGenre.setHasFixedSize(true);
        recyclerGenre.setLayoutManager(layoutManagerHorizontal);
        recyclerGenre.setAdapter(adapterGenero);


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