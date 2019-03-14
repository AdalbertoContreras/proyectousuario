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
import com.example.gestion.Gestion_administrador;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Administrador;
import com.example.modelo.Alerta_temprana;
import com.example.modelo.Asunto;
import com.example.modelo.Chat_asesoria;
import com.example.modelo.Mensaje_chat_asesoria;
import com.example.modelo.Usuario;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Mensajes_chat_asesoria extends  RecyclerView.Adapter<Adapter_Mensajes_chat_asesoria.ViewHolderDatos> {
    private ArrayList<Mensaje_chat_asesoria> mensaje_chat_asesoriaArrayList;
    private Chat_asesoria chat_asesoria;
    public Adapter_Mensajes_chat_asesoria(ArrayList<Mensaje_chat_asesoria> mensaje_chat_asesorias, Chat_asesoria chat_asesoria) {
        this.mensaje_chat_asesoriaArrayList = mensaje_chat_asesorias;
        this.chat_asesoria = chat_asesoria;
    }

    @Override
    public int getItemViewType(int position) {

        return mensaje_chat_asesoriaArrayList.get(position).tipo_creador_mensaje_chat_asesoria;
    }

    @NonNull
    @Override
    public Adapter_Mensajes_chat_asesoria.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i == 1)
        {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_mensaje_chat_assoria, null, false);
        }
        else
        {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_mensaje_chat_asesoria_recibir, null, false);
        }

        return new Adapter_Mensajes_chat_asesoria.ViewHolderDatos(view);
    }

    private View view;

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos viewHolderDatos, int i) {
        viewHolderDatos.setDatos(mensaje_chat_asesoriaArrayList.get(i), chat_asesoria);
    }

    @Override
    public int getItemCount() {
        return mensaje_chat_asesoriaArrayList.size();
    }


    public static class ViewHolderDatos extends RecyclerView.ViewHolder {
        private TextView contenidoTextView;
        private TextView fechatextView;
        private ConstraintLayout constraintLayout;
        private Chat_asesoria chat_asesoria;
        private View view;
        private CircleImageView fotoPerfilCircleImageView;
        public ViewHolderDatos(@NonNull final View itemView) {
            super(itemView);
            contenidoTextView = itemView.findViewById(R.id.contenidotextView_itemMensaje);
            fechatextView = itemView.findViewById(R.id.fechatextViewItem_mensaje);
            constraintLayout = itemView.findViewById(R.id.cuerpoLinearLayoutItemmensaje);
            fotoPerfilCircleImageView = itemView.findViewById(R.id.fotoPerfilImageView);
            this.view = itemView;
        }

        public void setDatos(final Mensaje_chat_asesoria mensaje_chat_asesoria, Chat_asesoria chat_asesoria)       {
            contenidoTextView.setText(mensaje_chat_asesoria.contenido_mensaje_chat_asesoria);
            fechatextView.setText(mensaje_chat_asesoria.fecha_envio_mensaje_chat_asesoria + " " + mensaje_chat_asesoria.hora_envio_mensaje_asesoria);
            if(mensaje_chat_asesoria.tipo_creador_mensaje_chat_asesoria == 1)
            {
                ArrayList<Usuario> usuarios = new Gestion_usuario().generar_json(chat_asesoria.usuario);
                if(!usuarios.isEmpty())
                {
                    Usuario usuario = usuarios.get(0);
                    Picasso.with(view.getContext()).load(usuario.foto_perfil_usuario).into(fotoPerfilCircleImageView);
                }
            }
            else
            {
                ArrayList<Administrador> administradores = new Gestion_administrador().generar_json(chat_asesoria.administrador);
                if(!administradores.isEmpty())
                {
                    Administrador administrador = administradores.get(0);
                    Picasso.with(view.getContext()).load(administrador.url_foto_perfil_administrador).into(fotoPerfilCircleImageView);
                }
            }
        }
    }
}