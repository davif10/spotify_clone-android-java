package com.davisilvaprojetos.spotifyclone.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.davisilvaprojetos.spotifyclone.activity.DetalhesArtistaActivity;
import com.davisilvaprojetos.spotifyclone.adapter.AdapterArtista;
import com.davisilvaprojetos.spotifyclone.adapter.AdapterGenero;
import com.davisilvaprojetos.spotifyclone.helper.RecyclerItemClickListener;
import com.davisilvaprojetos.spotifyclone.model.Artistas;

import com.davisilvaprojetos.spotifyclone.R;
import com.davisilvaprojetos.spotifyclone.viewmodel.ArtistListViewModel;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private List<Artistas> listArtist = new ArrayList<>();
    private final List<Artistas> listDifferentGender = new ArrayList<>();
    private final List<Artistas> listGenre = new ArrayList<>();
    private RecyclerView recyclerArtist, recyclerDifferentGender, recyclerGenre, recyclerArtistGenre;
    private ArtistListViewModel artistListViewModel = new ArtistListViewModel();

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        configuracoesIniciais(view);
        initObserver();
        configRecyclerViewGeneros();
        return view;
    }

    private void initObserver() {
        artistListViewModel.get_artistList().observe(this, new Observer<List<Artistas>>() {
            @Override
            public void onChanged(List<Artistas> artistas) {
                if (!artistas.isEmpty()) {
                    listArtist = artistas;
                    configRecyclerViewArtist(listArtist,recyclerArtist);
                    criarGeneroDiferente(listArtist);
                }
            }
        });

    }

    public void configRecyclerViewArtist(List<Artistas> listaArtistas, RecyclerView recyclerArtist) {
        AdapterArtista adapterArtista = new AdapterArtista(listaArtistas,"home");
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
                        Artistas artista = listaArtistas.get(position);
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

        configRecyclerViewArtist(listDifferentGender, recyclerDifferentGender);
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
        configRecyclerViewArtist(listGenre, recyclerArtistGenre);
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

        ViewModelProvider.NewInstanceFactory newInstanceFactory = new ViewModelProvider.NewInstanceFactory();
        artistListViewModel = newInstanceFactory.create(artistListViewModel.getClass());
        artistListViewModel.init();

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