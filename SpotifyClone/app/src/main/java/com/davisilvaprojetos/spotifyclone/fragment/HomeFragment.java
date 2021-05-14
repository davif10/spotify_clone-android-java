package com.davisilvaprojetos.spotifyclone.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.davisilvaprojetos.spotifyclone.activity.DetalhesArtistaActivity;
import com.davisilvaprojetos.spotifyclone.adapter.AdapterArtista;
import com.davisilvaprojetos.spotifyclone.adapter.AdapterGenero;
import com.davisilvaprojetos.spotifyclone.api.ApiService;
import com.davisilvaprojetos.spotifyclone.helper.RecyclerItemClickListener;
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
    private final List<Artistas> listGenre = new ArrayList<>();
    private Type typeMusic;
    private RecyclerView recyclerArtist, recyclerDifferentGender, recyclerGenre, recyclerArtistGenre;
    private LinearLayout layoutGenero;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        configuracoesIniciais(view);

        recuperarDadosArtista();
        configRecyclerViewGeneros();
        return view;
    }

    private void recuperarDadosArtista() {
        listArtist.clear();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.recuperarDadosArtista(

        ).enqueue(new Callback<Type>() {
            @Override
            public void onResponse(Call<Type> call, Response<Type> response) {
                if (response.isSuccessful()) {
                    typeMusic = response.body();
                    if (typeMusic != null) {
                        listArtist.addAll(typeMusic.getArtist());
                        configRecyclerViewArtist();
                        criarGeneroDiferente(listArtist);
                    }
                }
            }

            @Override
            public void onFailure(Call<Type> call, Throwable t) {
                System.out.println("Erro: " + t.getMessage());
            }
        });
    }

    public void configRecyclerViewArtist() {
        AdapterArtista adapterArtista = new AdapterArtista(listArtist, getActivity());
        RecyclerView.LayoutManager layoutManagerHorizontal = layoutHorizontal();

        recyclerArtist.setHasFixedSize(true);
        recyclerArtist.setLayoutManager(layoutManagerHorizontal);
        recyclerArtist.setAdapter(adapterArtista);

        recyclerArtist.addOnItemTouchListener(new RecyclerItemClickListener(
                getActivity(),
                recyclerArtist,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Artistas artista = listArtist.get(position);
                        abrirTelaDetalhesArtista(artista);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));

    }

    private void criarGeneroDiferente(List<Artistas> artistas) {
        for (int i = 0; i < artistas.size(); i++) {
            if (!artistas.get(i).getGenre().equals("pop")) {
                listDifferentGender.add(artistas.get(i));
            }
        }
        configRecyclerViewListaDiferente();
    }

    public void configRecyclerViewListaDiferente() {
        AdapterArtista adapterDifferent = new AdapterArtista(listDifferentGender, getActivity());
        RecyclerView.LayoutManager layoutManagerHorizontal = layoutHorizontal();

        recyclerDifferentGender.setHasFixedSize(true);
        recyclerDifferentGender.setLayoutManager(layoutManagerHorizontal);
        recyclerDifferentGender.setAdapter(adapterDifferent);

        recyclerDifferentGender.addOnItemTouchListener(new RecyclerItemClickListener(
                getActivity(),
                recyclerDifferentGender,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Artistas artista = listDifferentGender.get(position);
                        abrirTelaDetalhesArtista(artista);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));


    }


    public void configRecyclerViewGeneros() {
        String[] generos = {"rock", "rap", "pop", "mpb", "funk", "sertanejo", "reggae"};
        AdapterGenero adapterGenero = new AdapterGenero(generos);
        RecyclerView.LayoutManager layoutManagerHorizontal = layoutHorizontal();

        recyclerGenre.setHasFixedSize(true);
        recyclerGenre.setLayoutManager(layoutManagerHorizontal);
        recyclerGenre.setAdapter(adapterGenero);

        recyclerGenre.addOnItemTouchListener(new RecyclerItemClickListener(
                getActivity(),
                recyclerGenre,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String generoEscolhido = generos[position];
                        //Chamar método que exibe o layout com o genero correto
                        criarListaGenero(listArtist, generoEscolhido);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));


    }

    private void criarListaGenero(List<Artistas> artistas, String genero) {
        listGenre.clear();
        for (int i = 0; i < artistas.size(); i++) {
            if (artistas.get(i).getGenre().equals(genero)) {
                listGenre.add(artistas.get(i));
            }
        }
        configRecyclerViewListaGenero();
    }

    public void configRecyclerViewListaGenero() {
        layoutGenero.setVisibility(View.VISIBLE);
        AdapterArtista adapterArtista = new AdapterArtista(listGenre, getActivity());
        RecyclerView.LayoutManager layoutManagerHorizontal = layoutHorizontal();

        recyclerArtistGenre.setHasFixedSize(true);
        recyclerArtistGenre.setLayoutManager(layoutManagerHorizontal);
        recyclerArtistGenre.setAdapter(adapterArtista);

        recyclerArtistGenre.addOnItemTouchListener(new RecyclerItemClickListener(
                getActivity(),
                recyclerArtistGenre,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Artistas artista = listGenre.get(position);
                        abrirTelaDetalhesArtista(artista);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));


    }

    private void abrirTelaDetalhesArtista(Artistas artista){
        Intent i = new Intent(getActivity(), DetalhesArtistaActivity.class);
        i.putExtra("artista",artista);
        startActivity(i);
    }

    private RecyclerView.LayoutManager layoutHorizontal(){
        return new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
    }

    private void configuracoesIniciais(View view){
        recyclerArtist = view.findViewById(R.id.recyclerArtist);
        recyclerDifferentGender = view.findViewById(R.id.recyclerGenerosDiferentes);
        recyclerGenre = view.findViewById(R.id.recyclerEscolhaGenero);
        recyclerArtistGenre = view.findViewById(R.id.recyclerArtistasGenero);
        layoutGenero = view.findViewById(R.id.layoutGeneroArtistas);

        retrofit = RetrofitConfig.getRetrofit();
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