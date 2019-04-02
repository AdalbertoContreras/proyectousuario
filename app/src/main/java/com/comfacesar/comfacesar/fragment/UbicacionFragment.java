package com.comfacesar.comfacesar.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.comfacesar.comfacesar.MapsActivity;
import com.comfacesar.comfacesar.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class UbicacionFragment extends Fragment {


    View view;


    public ImageView AuxImagen;
    public ImageView AuxImagen1;
    public ImageView AuxImagen2;
    public ImageView AuxImagen3;
    public ImageView AuxImagen4;
    public ImageView AuxImagen5;


    public UbicacionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_ubicacion, container, false);

        AuxImagen= view.findViewById(R.id.ImagenSedes);
        AuxImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("id",1);
                getActivity().startActivity(intent);
            }
        });

        AuxImagen1= view.findViewById(R.id.CentroServicios);
        AuxImagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("id",2);
                getActivity().startActivity(intent);
            }
        });

        AuxImagen2= view.findViewById(R.id.CrispinVillazon);
        AuxImagen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("id",3);
                getActivity().startActivity(intent);
            }
        });

        AuxImagen3= view.findViewById(R.id.FondoVivienda);
        AuxImagen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("id",4);
                getActivity().startActivity(intent);
            }
        });

        AuxImagen4= view.findViewById(R.id.Instecom);
        AuxImagen4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("id",5);
                getActivity().startActivity(intent);
            }
        });

        AuxImagen5= view.findViewById(R.id.Colegio);
        AuxImagen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("id",6);
                getActivity().startActivity(intent);
            }
        });
        return  view  ;
    }



}
