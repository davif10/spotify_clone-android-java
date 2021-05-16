package com.davisilvaprojetos.spotifyclone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.davisilvaprojetos.spotifyclone.R;
import com.davisilvaprojetos.spotifyclone.model.Artistas;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterArtista extends RecyclerView.Adapter<AdapterArtista.MyViewHolder>{
    private final List<Artistas> artistas;
    private final String screen;

    public AdapterArtista(List<Artistas> artistas, String screen) {
        this.artistas = artistas;
        this.screen = screen;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view;
        if(screen.equals("home")){
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_artista,parent,false);
        }else{
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_artista_pesquisa,parent,false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterArtista.MyViewHolder holder, int position) {
        Artistas artist = artistas.get(position);
        holder.nameArtist.setText(artist.getName());
        Picasso.get().load(artist.getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return artistas.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView nameArtist;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageArtist);
            nameArtist = itemView.findViewById(R.id.textArtist);
        }
    }
}
