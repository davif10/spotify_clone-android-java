package com.davisilvaprojetos.spotifyclone.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davisilvaprojetos.spotifyclone.R;
import com.davisilvaprojetos.spotifyclone.model.Artistas;
import com.davisilvaprojetos.spotifyclone.viewmodel.ArtistListViewModel;

import java.util.List;


public class SearchFragment extends Fragment {
    private ArtistListViewModel artistListViewModel = new ArtistListViewModel();

    public SearchFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ViewModelProvider.NewInstanceFactory newInstanceFactory = new ViewModelProvider.NewInstanceFactory();
        artistListViewModel = newInstanceFactory.create(artistListViewModel.getClass());
        artistListViewModel.init();
        initObserver();

        return view;
    }

    private void initObserver(){
        artistListViewModel.get_artistList().observe(this, new Observer<List<Artistas>>() {
            @Override
            public void onChanged(List<Artistas> artistas) {
                if(!artistas.isEmpty()){
                    System.out.println("TEMOS OS DADOS: "+artistas.toString());
                }
            }
        });

    }
}