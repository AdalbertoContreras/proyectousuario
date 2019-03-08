package com.comfacesar.comfacesar.Adaptador;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.comfacesar.comfacesar.R;
import com.example.modelo.Alerta_temprana;
import com.example.modelo.Asunto;
import com.example.modelo.Mensaje_chat_asesoria;
import java.util.ArrayList;

public class Adapter_Mensajes_chat_asesoria extends  RecyclerView.Adapter<Adapter_Mensajes_chat_asesoria.ViewHolderDatos> {
    private ArrayList<Mensaje_chat_asesoria> mensaje_chat_asesoriaArrayList;
    public Adapter_Mensajes_chat_asesoria(ArrayList<Mensaje_chat_asesoria> mensaje_chat_asesorias) {
        this.mensaje_chat_asesoriaArrayList = mensaje_chat_asesorias;
    }

    @NonNull
    @Override
    public Adapter_Mensajes_chat_asesoria.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_mensaje_chat_assoria, null, false);
        return new Adapter_Mensajes_chat_asesoria.ViewHolderDatos(view);
    }

    private View view;

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos viewHolderDatos, int i) {
        viewHolderDatos.setDatos(mensaje_chat_asesoriaArrayList.get(i));
    }

    @Override
    public int getItemCount() {
        return mensaje_chat_asesoriaArrayList.size();
    }


    public static class ViewHolderDatos extends RecyclerView.ViewHolder {
        private TextView contenidoTextView;
        private TextView fechatextView;
        private ConstraintLayout constraintLayout;
        private View view;

        public ViewHolderDatos(@NonNull final View itemView) {
            super(itemView);
            contenidoTextView = itemView.findViewById(R.id.contenidotextView_itemMensaje);
            fechatextView = itemView.findViewById(R.id.fechatextViewItem_mensaje);
            constraintLayout = itemView.findViewById(R.id.cuerpoLinearLayoutItemmensaje);
            this.view = itemView;
        }

        public void setDatos(final Mensaje_chat_asesoria mensaje_chat_asesoria)       {
            if(mensaje_chat_asesoria.tipo_creador_mensaje_chat_asesoria == 1)
            {
                contenidoTextView.setTextColor(Color.BLUE);
            }
            else
            {
                contenidoTextView.setTextColor(Color.RED);
            }
            contenidoTextView.setText(mensaje_chat_asesoria.contenido_mensaje_chat_asesoria);
            fechatextView.setText(mensaje_chat_asesoria.fecha_envio_mensaje_chat_asesoria + " " + mensaje_chat_asesoria.hora_envio_mensaje_asesoria);
        }
    }
}