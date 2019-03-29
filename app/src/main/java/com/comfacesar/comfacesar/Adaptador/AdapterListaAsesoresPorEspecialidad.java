package com.comfacesar.comfacesar.Adaptador;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.Activities.ChatAsesoria;
import com.comfacesar.comfacesar.R;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_chat_asesoria;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Administrador;
import com.example.modelo.Chat_asesoria;
import com.example.modelo.Especialidad;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterListaAsesoresPorEspecialidad extends  RecyclerView.Adapter<AdapterListaAsesoresPorEspecialidad.ViewHolderDatos> {
    private ArrayList<Administrador> administradores;
    private FragmentManager fragmentManager;
    private int especialidad;
    public AdapterListaAsesoresPorEspecialidad(ArrayList<Administrador> alerta_tempranas, FragmentManager fragmentManager, int especialidad) {
        this.administradores = alerta_tempranas;
        this.fragmentManager = fragmentManager;
        this.especialidad = especialidad;
    }

    @NonNull
    @Override
    public AdapterListaAsesoresPorEspecialidad.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_administrador, null, false);
        return new AdapterListaAsesoresPorEspecialidad.ViewHolderDatos(view);
    }

    private View view;

    @Override
    public void onBindViewHolder(@NonNull AdapterListaAsesoresPorEspecialidad.ViewHolderDatos viewHolderDatos, int i) {
        viewHolderDatos.setDatos(administradores.get(i), fragmentManager, especialidad);
    }

    @Override
    public int getItemCount() {
        return administradores.size();
    }


    public static class ViewHolderDatos extends RecyclerView.ViewHolder {
        private View view;
        private TextView nombre_administrador_TextView;
        private CircleImageView fotoPerfilCircleImageView;
        private Administrador administrador;
        private int especialidad;
        public ViewHolderDatos(@NonNull final View itemView) {
            super(itemView);
            this.view = itemView;
            nombre_administrador_TextView = view.findViewById(R.id.nombre_administradortextView_item_administrador);
            fotoPerfilCircleImageView = view.findViewById(R.id.foto_pefilImageView);
        }

        public void setDatos(final Administrador administrador, final FragmentManager fragmentManager, int especialidad) {
            this.administrador = administrador;
            this.especialidad = especialidad;
            nombre_administrador_TextView.setText(administrador.nombres_administrador + " " + administrador.apellidos_administrador);
            Picasso.with(view.getContext()).load(administrador.url_foto_perfil_administrador).placeholder(R.drawable.ic_iconousuario)
                    .error(R.drawable.ic_iconousuario).into(fotoPerfilCircleImageView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buscar_crear_chat();
                }
            });
        }

        public void buscar_crear_chat()
        {
            HashMap<String,String> params = new Gestion_chat_asesoria().consultar_chat_asesoria_por_usuario_administrador_y_especialidad(administrador.id_administrador, Gestion_usuario.getUsuario_online().id_usuario, especialidad);
            Response.Listener<String> stringListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    if(!response.equals(""))
                    {
                        ArrayList<Chat_asesoria> chat_asesorias = new Gestion_chat_asesoria().generar_json(response);
                        if(!chat_asesorias.isEmpty())
                        {
                            Chat_asesoria chat_asesoria = chat_asesorias.get(0);
                            ChatAsesoria.administrador = administrador;
                            ChatAsesoria.chat_asesoria = chat_asesoria;
                            Intent intent = new Intent(view.getContext(), ChatAsesoria.class);
                            (view.getContext()).startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(view.getContext(), "Error en el servidor, intente de nuevo mas tarde", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            };
            StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
            MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
        }
    }
}
