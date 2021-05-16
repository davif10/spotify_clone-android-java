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
import android.widget.SearchView;

import com.davisilvaprojetos.spotifyclone.R;
import com.davisilvaprojetos.spotifyclone.activity.DetalhesArtistaActivity;
import com.davisilvaprojetos.spotifyclone.adapter.AdapterArtista;
import com.davisilvaprojetos.spotifyclone.helper.RecyclerItemClickListener;
import com.davisilvaprojetos.spotifyclone.model.Artistas;
import com.davisilvaprojetos.spotifyclone.viewmodel.ArtistListViewModel;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    private ArtistListViewModel artistListViewModel = new ArtistListViewModel();
    private List<Artistas> art = new ArrayList<>();
    private final List<Artistas> artistsData = new ArrayList<>();
    private AdapterArtista adapterArtista;

    public SearchFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        configuracoesIniciais(view);
        initObserver();

        return view;
    }

    private void searchArtist(String text) {
        artistsData.clear();
        if (!text.equals("")) {
            for (Artistas artistas : art) {
                if (artistas.getName().toUpperCase().contains(text)) {
                    artistsData.add(artistas);
                }
            }
            adapterArtista.notifyDataSetChanged();
        }
    }

    private void initObserver() {
        artistListViewModel.get_artistList().observe(this, new Observer<List<Artistas>>() {
            @Override
            public void onChanged(List<Artistas> artistas) {
                if (!artistas.isEmpty()) {
                    art = artistas;
                }
            }
        });

    }

    private void searchListeners(SearchView searchViewData){
        searchViewData.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String typedText = newText.toUpperCase();
                searchArtist(typedText);
                return true;
            }
        });

        searchViewData.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                artistsData.clear();
                adapterArtista.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void clickRecycler(RecyclerView recyclerSearch){
        recyclerSearch.addOnItemTouchListener(new RecyclerItemClickListener(
                getActivity(),
                recyclerSearch,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Artistas artista = artistsData.get(position);
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

    private void configuracoesIniciais(View view){
        SearchView searchViewData = view.findViewById(R.id.searchViewData);
        searchViewData.setQueryHint("Buscar artistas");

        adapterArtista = new AdapterArtista(artistsData, "search");
        RecyclerView recyclerSearch = view.findViewById(R.id.recyclerSearch);
        recyclerSearch.setHasFixedSize(true);
        recyclerSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerSearch.setAdapter(adapterArtista);

        ViewModelProvider.NewInstanceFactory newInstanceFactory = new ViewModelProvider.NewInstanceFactory();
        artistListViewModel = newInstanceFactory.create(artistListViewModel.getClass());
        artistListViewModel.init();

        clickRecycler(recyclerSearch);
        searchListeners(searchViewData);

    }

    private void abrirTelaDetalhesArtista(Artistas artista){
        Intent i = new Intent(getActivity(), DetalhesArtistaActivity.class);
        i.putExtra("artista",artista);
        startActivity(i);
    }
}