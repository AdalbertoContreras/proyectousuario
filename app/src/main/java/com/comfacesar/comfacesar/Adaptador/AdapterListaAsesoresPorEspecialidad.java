package com.comfacesar.comfacesar.Adaptador;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.comfacesar.comfacesar.R;
import com.comfacesar.comfacesar.fragment.Chat_asesoriaFragment;
import com.example.modelo.Administrador;

import java.util.ArrayList;
public class AdapterListaAsesoresPorEspecialidad extends  RecyclerView.Adapter<AdapterListaAsesoresPorEspecialidad.ViewHolderDatos> {
    private ArrayList<Administrador> administradores;
    private FragmentManager fragmentManager;
    public AdapterListaAsesoresPorEspecialidad(ArrayList<Administrador> alerta_tempranas, FragmentManager fragmentManager) {
        this.administradores = alerta_tempranas;
        this.fragmentManager = fragmentManager;
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
        viewHolderDatos.setDatos(administradores.get(i), fragmentManager);
    }

    @Override
    public int getItemCount() {
        return administradores.size();
    }


    public static class ViewHolderDatos extends RecyclerView.ViewHolder {
        private View view;
        private TextView nombre_administrador_TextView;
        public ViewHolderDatos(@NonNull final View itemView) {
            super(itemView);
            this.view = itemView;
            nombre_administrador_TextView = view.findViewById(R.id.nombre_administradortextView_item_administrador);
        }

        public void setDatos(final Administrador administrador, final FragmentManager fragmentManager) {
            nombre_administrador_TextView.setText(administrador.nombres_administrador + " " + administrador.apellidos_administrador);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new Chat_asesoriaFragment();
                    Chat_asesoriaFragment.administrador = administrador;
                    fragmentManager.beginTransaction().replace(R.id.container2,fragment).commit();
                }
            });
        }
    }
}
