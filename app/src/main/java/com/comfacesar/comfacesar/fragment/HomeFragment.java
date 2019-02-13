package com.comfacesar.comfacesar.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comfacesar.comfacesar.Adaptador.ExampleAdapter;
import com.comfacesar.comfacesar.Interface.ListItem;
import com.comfacesar.comfacesar.Pojo.TypeA;
import com.comfacesar.comfacesar.Pojo.TypeB;
import com.comfacesar.comfacesar.Pojo.TypeC;
import com.comfacesar.comfacesar.R;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    List<ListItem> list;
    RecyclerView recycle;
    ExampleAdapter exampleAdapter;
    View view;
    Context context;


    public String textoAux1="Con la participación de más de 800 personas de nacionalidad venezolana, " +
            "la Caja de Compensación Familiar del Cesar (COMFACESAR), realizó a través de la Agencia de Gestión y" +
            " Colocación de Empleo, la Feria de Atención Integral a Migrantes, en el Colegio “Rodolfo Campo Soto”.";

    public String textoAux2= "Embarazos prematuros una problematica palpable en la Ciudad de Valledupar";



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_home, container, false);

        recycle= view.findViewById(R.id.Recycle_IdHome);
        recycle.setLayoutManager(new LinearLayoutManager((context)));


        list= new ArrayList<ListItem>();


        list.add(new TypeA(R.drawable.imagen_1,"COMFACESAR EMPRENDE LA ESTRATEGIA JUNTOS"));
        list.add(new TypeB(R.drawable.imagen_2,"COMFACESAR PROMUEVE EL TRABAJO DIGNO PARA POBLACIÓN MIGRANTE",textoAux1));
        list.add(new TypeC(R.drawable.imagen_3,"MÁS DE 5 MIL AFILIADOS ASISTIERON AL CONCURSO DE IMITADORES EN LA PEDREGOSA",
                R.drawable.imagen_4,"COMFACESAR CERRÓ CICLO DE INICIACIÓN MUSICAL CON 105 NIÑOS DE VALLEDUPAR, CODAZZI Y ATÁNQUEZ"));

        list.add(new TypeA(R.drawable.imagen_1,"COMFACESAR EMPRENDE LA ESTRATEGIA JUNTOS"));
        list.add(new TypeC(R.drawable.imagen_3,"MÁS DE 5 MIL AFILIADOS ASISTIERON AL CONCURSO DE IMITADORES EN LA PEDREGOSA",
                R.drawable.imagen_4,"COMFACESAR CERRÓ CICLO DE INICIACIÓN MUSICAL CON 105 NIÑOS DE VALLEDUPAR, CODAZZI Y ATÁNQUEZ"));
        list.add(new TypeB(R.drawable.imagen_2,"COMFACESAR PROMUEVE EL TRABAJO DIGNO PARA POBLACIÓN MIGRANTE",textoAux1));


        exampleAdapter= new ExampleAdapter(list);
        recycle.setAdapter(exampleAdapter);

        return view;

    }


}
