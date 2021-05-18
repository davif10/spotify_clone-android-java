package com.davisilvaprojetos.spotifyclone.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.davisilvaprojetos.spotifyclone.helper.RetrofitConfig;
import com.davisilvaprojetos.spotifyclone.model.Artistas;
import com.davisilvaprojetos.spotifyclone.dataacess.ArtistRepository;

import java.util.List;

public class ArtistListViewModel extends ViewModel {
    private Context context;
    private RetrofitConfig retrofitConfig = new RetrofitConfig();
    private ArtistRepository artistRepository;


    private final MutableLiveData<List<Artistas>> _artistList = new MutableLiveData<>();

    public ArtistListViewModel() {
    }

    public ArtistListViewModel(Context context) {
        this.context = context;
        artistRepository = new ArtistRepository(context);
    }

    public void init(Context context){
        getAllArtists(context);
    }


    private void getAllArtists(Context context) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    artistRepository = new ArtistRepository(context);
                    _artistList.postValue(artistRepository.getArtistList());
                }catch (Exception e){
                    System.out.println("Erro no View Model: "+e.getMessage());
                }
            }
        }.start();

    }

    public MutableLiveData<List<Artistas>> get_artistList() {
        return _artistList;
    }
}
