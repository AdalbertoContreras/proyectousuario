package com.comfacesar.comfacesar.Adaptador;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.Dialog.AlertaTempranaDialog;
import com.comfacesar.comfacesar.R;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_asunto;
import com.example.modelo.Alerta_temprana;
import com.example.modelo.Asunto;
import com.example.modelo.Chat_asesoria;

import java.util.ArrayList;
import java.util.HashMap;

public class AdaptadorItemChat extends  RecyclerView.Adapter<AdaptadorItemChat.ViewHolderDatos> {
    private ArrayList<Chat_asesoria> alertas;
    public int h;
    private FragmentManager fragmentManager;
    public AdaptadorItemChat(ArrayList<Chat_asesoria> chat_asesoriaArrayList) {
        this.alertas = chat_asesoriaArrayList;
        this.fragmentManager =  fragmentManager;
    }

    @NonNull
    @Override
    public AdaptadorItemChat.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_chat_asesoria, null, false);
        return new AdaptadorItemChat.ViewHolderDatos(view);
    }

    private View view;

    @Override
    public void onBindViewHolder(@NonNull AdaptadorItemChat.ViewHolderDatos viewHolderDatos, int i) {
        viewHolderDatos.setDatos(alertas.get(i));
    }

    @Override
    public int getItemCount() {
        return alertas.size();
    }


    public static class ViewHolderDatos extends RecyclerView.ViewHolder {
        private View view;
        private ImageView logo_asesoria;
        private TextView asesroTextView;
        private TextView ultimo_mensaje_TextView;
        private TextView fechaTextView;
        public ViewHolderDatos(@NonNull final View itemView) {
            super(itemView);
            this.view = itemView;
            logo_asesoria = view.findViewById(R.id.logo_asesoriaimageViewItemChat);
            ultimo_mensaje_TextView = view.findViewById(R.id.ultimo_mensajetextViewItemChat);
            fechaTextView = view.findViewById(R.id.fechatextViewItemChat);
        }

        public void setDatos(final Chat_asesoria chat_asesoria) {
            //consultar ultimo mensaje
            //fecha ultimo mensaje
            //logo segun la asesoria
        }
    }
}
