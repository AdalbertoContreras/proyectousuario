package com.comfacesar.comfacesar.Adaptador;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.comfacesar.comfacesar.R;
import com.comfacesar.comfacesar.fragment.ChatActivosFragment;
import com.comfacesar.comfacesar.fragment.Chat_asesoriaFragment;
import com.example.gestion.Gestion_administrador;
import com.example.gestion.Gestion_especialidad;
import com.example.modelo.Administrador;
import com.example.modelo.Chat_asesoria;
import com.example.modelo.Especialidad;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_historia_chat_asesoria extends RecyclerView.Adapter<Adapter_historia_chat_asesoria.ViewHolderDatos> {
    private ArrayList<Chat_asesoria> chat_asesorias;
    private FragmentManager fragmentManager;
    public Adapter_historia_chat_asesoria(ArrayList<Chat_asesoria> chat_asesorias, FragmentManager fragmentManager) {
            this.chat_asesorias = chat_asesorias;
            this.fragmentManager = fragmentManager;
            }

    @NonNull
    @Override
    public Adapter_historia_chat_asesoria.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            view = LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.item_historial_chat_asesoria, null, false);
            return new Adapter_historia_chat_asesoria.ViewHolderDatos(view);
            }

    private View view;

    @Override
    public void onBindViewHolder(@NonNull Adapter_historia_chat_asesoria.ViewHolderDatos viewHolderDatos, int i) {
            viewHolderDatos.setDatos(chat_asesorias.get(i), fragmentManager);
            }

    @Override
    public int getItemCount() {
            return chat_asesorias.size();
            }


    public static class ViewHolderDatos extends RecyclerView.ViewHolder {
        private View view;
        private TextView nombre_asesorTextView;
        private TextView ultimo_mensajeTextView;
        private TextView tipo_asesoriaTextView;
        private TextView fecha_ultimo_mensajeTextView;
        private CircleImageView foto_perfil_asesorImageView;
        public ViewHolderDatos(@NonNull final View itemView) {
            super(itemView);
            this.view = itemView;
            nombre_asesorTextView = view.findViewById(R.id.nombre_asesorTextView);
            ultimo_mensajeTextView = view.findViewById(R.id.ultimo_mensajeTextView);
            fecha_ultimo_mensajeTextView = view.findViewById(R.id.fecha_ultimo_mensajeTextView);
            tipo_asesoriaTextView = view.findViewById(R.id.tipo_especialidadTextView);
            foto_perfil_asesorImageView = view.findViewById(R.id.imagen_asesorImageView);
        }

        public void setDatos(final Chat_asesoria chat_asesoria, final FragmentManager fragmentManager) {
            final ArrayList<Administrador> administradores = new Gestion_administrador().generar_json(chat_asesoria.administrador);
            final ArrayList<Especialidad> especialidades = new Gestion_especialidad().generar_json(chat_asesoria.especialidad);
            if(!administradores.isEmpty())
            {
                nombre_asesorTextView.setText(administradores.get(0).nombre_cuenta_administrador);
                Picasso.with(view.getContext()).load(administradores.get(0).url_foto_perfil_administrador).into(foto_perfil_asesorImageView);
            }
            if(!especialidades.isEmpty())
            {
                tipo_asesoriaTextView.setText(especialidades.get(0).nombre_especialidad);
            }
            ultimo_mensajeTextView.setText(chat_asesoria.ultimo_mensaje);
            fecha_ultimo_mensajeTextView.setText(chat_asesoria.ultima_fecha_chat_asesoria + " " + chat_asesoria.ultima_hora_chat_asesoria);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(administradores.isEmpty())
                    {

                    }
                    else
                    {
                        Fragment fragment = new Chat_asesoriaFragment();
                        Chat_asesoriaFragment.administrador = administradores.get(0);
                        ChatActivosFragment.tipoAsesoria = especialidades.get(0).id_especialidad;
                        fragmentManager.beginTransaction().replace(R.id.container2,fragment).commit();
                    }
                }
            });
        }
    }
}