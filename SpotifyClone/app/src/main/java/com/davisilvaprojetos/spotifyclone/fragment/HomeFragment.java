package com.davisilvaprojetos.spotifyclone.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davisilvaprojetos.spotifyclone.adapter.AdapterArtista;
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
    private  List<Artistas> listArtist = new ArrayList<>();
    private Type typeMusic;
    private AdapterArtista adapterArtista;
    private RecyclerView recyclerArtista;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerArtista = view.findViewById(R.id.recyclerArtist);
        retrofit = RetrofitConfig.getRetrofit();

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
        adapterArtista = new AdapterArtista(listArtist, getActivity()) ;
        RecyclerView.LayoutManager layoutManagerHorizontal = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
        );

        recyclerArtista.setHasFixedSize(true);
        recyclerArtista.setLayoutManager(layoutManagerHorizontal);
        recyclerArtista.setAdapter(adapterArtista);


    }


    @Override
    public void onStart() {
        super.onStart();
        recuperarDadosArtista();
    }


    @Override
    public void onStop() {
        super.onStop();
    }
}