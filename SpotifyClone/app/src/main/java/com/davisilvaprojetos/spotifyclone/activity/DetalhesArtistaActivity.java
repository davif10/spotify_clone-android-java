package com.davisilvaprojetos.spotifyclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.davisilvaprojetos.spotifyclone.R;
import com.davisilvaprojetos.spotifyclone.model.Artistas;
import com.squareup.picasso.Picasso;

public class DetalhesArtistaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_artista);
        ImageView imageArtist = findViewById(R.id.imageArtist);
        TextView textNameArtist = findViewById(R.id.textNameArtist);
        TextView textGenreArtist = findViewById(R.id.textGenreArtist);
        TextView textBibliographyArtist = findViewById(R.id.textBibliographyArtist);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            Artistas artista = (Artistas) bundle.getSerializable("artista");
            Picasso.get().load(artista.getImage()).into(imageArtist);
            textNameArtist.setText(artista.getName());
            textGenreArtist.setText(artista.getGenre().toUpperCase());
            textBibliographyArtist.setText(artista.getBibliography());
        }
    }
}