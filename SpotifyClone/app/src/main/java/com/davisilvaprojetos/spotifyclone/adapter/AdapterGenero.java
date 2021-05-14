package com.davisilvaprojetos.spotifyclone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.davisilvaprojetos.spotifyclone.R;

public class AdapterGenero extends RecyclerView.Adapter<AdapterGenero.MyViewHolder>{
    private String[] generos;

    public AdapterGenero(String[] generos) {
        this.generos = generos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_escolha_genero,parent,false);
        return new AdapterGenero.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGenero.MyViewHolder holder, int position) {
        String genero = generos[position];
        holder.buttonGenre.setText(genero);
        int color = colorButton(genero);
        holder.buttonGenre.setBackgroundResource(color);
    }

    @Override
    public int getItemCount() {
        return generos.length;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder{
        Button buttonGenre;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonGenre = itemView.findViewById(R.id.buttonGenero);
        }
    }

    private int colorButton(String genero){
        int color;
        switch (genero){
            case "rock": color = R.color.rock;
            break;
            case "sertanejo": color = R.color.sertanejo;
                break;
            case "pop": color = R.color.pop;
                break;
            case "funk": color = R.color.funk;
                break;
            case "rap": color = R.color.rap;
                break;
            case "mpb": color = R.color.mpb;
                break;
            case "reggae": color = R.color.reggae;
                break;

            default:color = R.color.rock;
            break;
        }

        return color;
    }
}
