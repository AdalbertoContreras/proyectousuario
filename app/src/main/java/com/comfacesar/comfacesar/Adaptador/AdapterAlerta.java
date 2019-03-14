package com.comfacesar.comfacesar.Adaptador;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.ContainertwoActivity;
import com.comfacesar.comfacesar.Dialog.AlertaTempranaDialog;
import com.comfacesar.comfacesar.R;
import com.comfacesar.comfacesar.fragment.HistorialAlertasFragment;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_asunto;
import com.example.modelo.Alerta_temprana;
import com.example.modelo.Asunto;
import com.example.modelo.Mensaje_chat_asesoria;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterAlerta extends  RecyclerView.Adapter<AdapterAlerta.ViewHolderDatos> {
    private ArrayList<Alerta_temprana> alertas;
    public int h;
    private FragmentManager fragmentManager;
    public AdapterAlerta(ArrayList<Alerta_temprana> alerta_tempranas, FragmentManager fragmentManager) {
        this.alertas = alerta_tempranas;
        this.fragmentManager =  fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_alertas, null, false);
        return new ViewHolderDatos(view);
    }

    private View view;

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos viewHolderDatos, int i) {
        viewHolderDatos.setDatos(alertas.get(i), fragmentManager);
    }

    @Override
    public int getItemCount() {
        return alertas.size();
    }


    public static class ViewHolderDatos extends RecyclerView.ViewHolder {
        private TextView nombreAsuntoTextView;
        private TextView fechTextview;
        private Alerta_temprana alerta_temprana;
        private Asunto asunto;
        private View view;

        public ViewHolderDatos(@NonNull final View itemView) {
            super(itemView);
            nombreAsuntoTextView = itemView.findViewById(R.id.asuntoTextViewAlertaTempranaItem);
            fechTextview = itemView.findViewById(R.id.fechaTextViewAlertaTempranaItem);
            this.view = itemView;
        }

        public void setDatos(final Alerta_temprana Alerta_temprana , final FragmentManager fragmentManager) {
            fechTextview.setText(Alerta_temprana.fecha_alerta_temprana);
            consultar_asunto(Alerta_temprana);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    AlertaTempranaDialog.alerta_temprana = Alerta_temprana;
                    AlertaTempranaDialog.asunto = asunto;
                    AlertaTempranaDialog alertaTempranaDialog = new AlertaTempranaDialog();
                    alertaTempranaDialog.setCancelable(false);
                    try {
                        alertaTempranaDialog.show(HistorialAlertasFragment.getFragmentManager_(), "missiles");
                    } catch (IllegalStateException ignored) {

                    }
                }
            });
        }

        private void consultar_asunto(final Alerta_temprana alerta_temprana) {
            ArrayList<Asunto> asuntos = new Gestion_asunto().generar_json(alerta_temprana.asunto);
            if(asuntos.isEmpty())
            {
                nombreAsuntoTextView.setText("");
            }
            else
            {
                asunto = asuntos.get(0);
                nombreAsuntoTextView.setText(asuntos.get(0).nombre_asunto);
            }
        }
    }
}