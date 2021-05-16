package com.davisilvaprojetos.spotifyclone.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.davisilvaprojetos.spotifyclone.helper.RetrofitConfig;
import com.davisilvaprojetos.spotifyclone.model.Artistas;
import com.davisilvaprojetos.spotifyclone.repository.ArtistRepository;

import java.util.List;

public class ArtistListViewModel extends ViewModel {
    private RetrofitConfig retrofitConfig = new RetrofitConfig();
    private ArtistRepository artistRepository = new ArtistRepository(retrofitConfig);

    private final MutableLiveData<List<Artistas>> _artistList = new MutableLiveData<>();
     LiveData<List<Artistas>> artistList = get_artistList();

    public void init(){
        getAllArtists();
    }

    private void getAllArtists() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    _artistList.postValue(artistRepository.getArtistList());

                }catch (Exception e){
                    System.out.println("Erro: "+e.getMessage());
                }
            }
        }.start();

    }

    public MutableLiveData<List<Artistas>> get_artistList() {
        return _artistList;
    }
}
