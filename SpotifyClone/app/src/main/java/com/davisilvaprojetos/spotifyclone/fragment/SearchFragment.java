package com.davisilvaprojetos.spotifyclone.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.davisilvaprojetos.spotifyclone.R;
import com.davisilvaprojetos.spotifyclone.adapter.AdapterArtista;
import com.davisilvaprojetos.spotifyclone.model.Artistas;
import com.davisilvaprojetos.spotifyclone.viewmodel.ArtistListViewModel;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    private ArtistListViewModel artistListViewModel = new ArtistListViewModel();
    private List<Artistas> art = new ArrayList<>();
    private List<Artistas> artistsData = new ArrayList<>();
    private RecyclerView recyclerSearch;
    private SearchView searchViewData;

    public SearchFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerSearch = view.findViewById(R.id.recyclerSearch);
        searchViewData = view.findViewById(R.id.searchViewData);

        ViewModelProvider.NewInstanceFactory newInstanceFactory = new ViewModelProvider.NewInstanceFactory();
        artistListViewModel = newInstanceFactory.create(artistListViewModel.getClass());
        artistListViewModel.init();
        initObserver();

        searchViewData.setQueryHint("Buscar artistas");
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

                return true;
            }
        });


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
            populateList();
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

    private void populateList() {
        AdapterArtista adapterArtista = new AdapterArtista(artistsData, "search");
        recyclerSearch.setHasFixedSize(true);
        recyclerSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerSearch.setAdapter(adapterArtista);


    }
}