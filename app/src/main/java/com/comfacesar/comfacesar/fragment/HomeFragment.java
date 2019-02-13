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


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.Adaptador.ExampleAdapter;
import com.comfacesar.comfacesar.Interface.ListItem;
import com.comfacesar.comfacesar.Pojo.TypeA;
import com.comfacesar.comfacesar.Pojo.TypeB;
import com.comfacesar.comfacesar.Pojo.TypeC;
import com.comfacesar.comfacesar.R;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_imagen_noticia;
import com.example.gestion.Gestion_noticia;
import com.example.modelo.Imagen_noticia;
import com.example.modelo.Noticia;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private List<ListItem> list_items;
    private RecyclerView recycle;
    private ExampleAdapter exampleAdapter;
    private Context context;
    private ArrayList<Noticia> noticias_manuales;
    private ArrayList<Noticia> noticias_automaticas;
    private boolean cargando_consulta = true;
    private static View view_permantente;
    private static int num_articulos = 0;
    private ArrayList<Noticia> noticias_actuales;


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
        if(view_permantente == null)
        {
            view_permantente = inflater.inflate(R.layout.fragment_home, container, false);
            recycle= view_permantente.findViewById(R.id.Recycle_IdHome);
            recycle.setLayoutManager(new LinearLayoutManager((context)));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(;;)
                    {
                        try {
                            consultar_num_noticias();
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).start();
            //generar_comsulta(new ArrayList<Noticia>());
        }
        consultar_num_noticias();
        return view_permantente;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void consultar_noticias()
    {
        if(!generando_conmsulta)
        {
            generando_conmsulta = true;
            final Gestion_noticia gestion_noticia = new Gestion_noticia();
            //tomo los parametros del controlador
            HashMap<String,String> params = gestion_noticia.consultar_con_imagenes_y_m_primero();
            Log.d("parametros", params.toString());
            Response.Listener<String> stringListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    ArrayList<Noticia> list = gestion_noticia.generar_json(response);
                    generar_comsulta(list);
                }
            };
            StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, MySocialMediaSingleton.errorListener());
            MySocialMediaSingleton.getInstance(view_permantente.getContext()).addToRequestQueue(stringRequest);
        }
    }
    private boolean generando_conmsulta = false;

    private void consultar_num_noticias()
    {
        final Gestion_noticia gestion_noticia = new Gestion_noticia();
        //tomo los parametros del controlador
        HashMap<String,String> params = gestion_noticia.consultar_num_noticia();
        Log.d("parametros", params.toString());
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                int val = 0;
                try
                {
                    val = Integer.parseInt(response);
                    if(val > num_articulos)
                    {
                        consultar_noticias();
                    }
                }
                catch (NumberFormatException exc)
                {

                }
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, MySocialMediaSingleton.errorListener());
        MySocialMediaSingleton.getInstance(view_permantente.getContext()).addToRequestQueue(stringRequest);
    }

    private void generar_comsulta(ArrayList<Noticia> list)
    {
        list_items = new ArrayList<>();
        //Toast.makeText(view.getContext(),list2.size() + "",Toast.LENGTH_LONG).show();
        num_articulos = list.size();
        llenar_noticias_automaticas(list);
        llenar_noticias_manuales(list);
        cargar_noticias(noticias_manuales);
        cargar_noticias(noticias_automaticas);
        exampleAdapter= new ExampleAdapter(list_items);
        recycle.setAdapter(exampleAdapter);
        generando_conmsulta = false;
    }

    private void llenar_noticias_manuales(ArrayList<Noticia> list)
    {
        noticias_manuales = new ArrayList<>();
        int cont = 0;
        for(Noticia noticia : list)
        {
            if(noticia.tipo_creacion_noticia == 1)
            {
                noticias_manuales.add(noticia);
            }
        }
    }

    private void llenar_noticias_automaticas(ArrayList<Noticia> list)
    {
        noticias_automaticas = new ArrayList<>();
        for(Noticia noticia : list)
        {
            if(noticia.tipo_creacion_noticia == 2)
            {
                noticias_automaticas.add(noticia);
            }
        }
    }

    private void cargar_noticias(ArrayList<Noticia> noticias)
    {
        int contador = 1;
        //cargar noticias de la pagina
        //
        //
        int num_items = noticias.size();
        Log.d("num_items", num_items + "");
        for(int i = 0; i < num_items; i ++){
            //cargar imagen por noticia
            //
            //
            Gestion_imagen_noticia gestion_imagen_noticia = new Gestion_imagen_noticia();
            ArrayList<Imagen_noticia> imagen_noticias = gestion_imagen_noticia.generar_json(noticias.get(i).json_imagenes);
            Imagen_noticia imagen_noticia = null;
            if(!imagen_noticias.isEmpty())
            {
                imagen_noticia = imagen_noticias.get(0);
            }
            switch(contador)
            {
                case 1:
                    if(imagen_noticia != null)
                    {
                        list_items.add(new TypeA(noticias.get(i),imagen_noticia));
                    }
                    else
                    {
                        list_items.add(new TypeA(noticias.get(i)));
                    }

                    break;
                case 2:
                    if(imagen_noticia != null)
                    {
                        list_items.add(new TypeB(noticias.get(i), imagen_noticia));
                    }
                    else
                    {
                        list_items.add(new TypeB(noticias.get(i)));
                    }

                    break;
                case 3:
                    if((i + 1) < num_items)
                    {
                        Log.d("i + 1 =",(i + 1) + "");
                        ArrayList<Imagen_noticia> imagen_noticias_2 = gestion_imagen_noticia.generar_json(noticias.get(i + 1).json_imagenes);
                        Imagen_noticia  imagen_noticia_2 = null;
                        if(!imagen_noticias_2.isEmpty())
                        {
                            imagen_noticia_2 = imagen_noticias_2.get(0);
                        }
                        if(imagen_noticia != null && imagen_noticia_2 != null)
                        {
                            if(imagen_noticia_2 != null)
                            {
                                list_items.add(new TypeC(noticias.get(i), imagen_noticia, noticias.get(i + 1), imagen_noticia_2));
                            }
                            else {
                                list_items.add(new TypeC(noticias.get(i), imagen_noticia, noticias.get(i + 1)));
                            }
                        }
                        else
                        {
                            if(imagen_noticia_2 != null)
                            {
                                list_items.add(new TypeC(noticias.get(i), noticias.get(i + 1), imagen_noticia_2));
                            }
                            else {
                                list_items.add(new TypeC(noticias.get(i), imagen_noticia, noticias.get(i + 1)));
                            }
                        }
                        //cargar imagen por noticia, segunda;

                        i ++;
                    }
                    else
                    {

                        if(imagen_noticia != null)
                        {
                            list_items.add(new TypeB(noticias.get(i), imagen_noticia));
                        }
                        else
                        {
                            list_items.add(new TypeB(noticias.get(i)));
                        }
                    }
                    contador = 0;
                    break;
            }
            contador ++;
        }
    }

}
