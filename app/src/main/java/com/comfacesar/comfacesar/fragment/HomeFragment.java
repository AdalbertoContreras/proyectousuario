package com.comfacesar.comfacesar.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.Adaptador.AdapterNoticia;
import com.comfacesar.comfacesar.ContainerActivity;
import com.comfacesar.comfacesar.Item.ItemNoticia;
import com.comfacesar.comfacesar.R;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Escuchadores.EscuchadorUsuario;
import com.example.gestion.Gestion_imagen_noticia;
import com.example.gestion.Gestion_noticia;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Imagen_noticia;
import com.example.modelo.Noticia;
import com.example.modelo.Usuario;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static List<ItemNoticia> itemNoticias_general = new ArrayList<>();
    private List<ItemNoticia> itemNoticias_filtrada;
    private RecyclerView recycle;
    private AdapterNoticia exampleAdapter;
    private Context context;
    private ArrayList<Noticia> noticias_nuevas;
    private boolean cargando_consulta = true;
    private View view_permantente;
    private int num_articulos = 0;
    private ArrayList<Noticia> noticias_actuales;
    private Usuario usuario_anterior = null;
    private Usuario usuario_nuevo = null;
    public static boolean fragmentConsultarNoticiasActivo = true;
    public static boolean creado = false;
    private static int id_ultimo = 0;
    private boolean agregar_noticias_nuevas;
    private boolean generandoConsulta = false;
    private int cont = 0;
    private String textoFiltroGuardado = "";
    private String textoFiltroNuevo = "";
    private boolean filtrando_noticias = false;
    public static int id_articulo_selecionado = -1;
    private boolean noticiasFiltradas = false;
    private android.support.v7.widget.SearchView searchView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view_permantente = inflater.inflate(R.layout.fragment_home, container, false);
        itemNoticias_filtrada = new ArrayList<>();
        textoFiltroNuevo = ContainerActivity.texto_buscar;
        textoFiltroGuardado = textoFiltroNuevo;
        return view_permantente;
    }

    @Override
    public void onResume() {
        super.onResume();
        cont = 5000;
        fragmentConsultarNoticiasActivo = true;
        noticias_nuevas = new ArrayList<>();
        filtrando_noticias = false;
        Gestion_usuario.escuchadorParaHome = new EscuchadorUsuario() {
            @Override
            public void usuarioCambiado(Usuario usuario) {
                usuario_anterior = usuario_nuevo = usuario;
                agregar_noticias_nuevas = false;
                while(generandoConsulta)
                {
                    try
                    {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                consultar_noticias();
                exampleAdapter = new AdapterNoticia(getActivity(),itemNoticias_filtrada, getFragmentManager());
                recycle.setAdapter(exampleAdapter);
            }
        };
        ContainerActivity.escuchadorCambioFiltro = new ContainerActivity.escuchador() {
            @Override
            public void filtroCambiado(String textoNuevo, View view) {
                searchView =(SearchView) view;

                textoFiltroNuevo = textoNuevo;
                while(filtrando_noticias)
                {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                filtrando_noticias = true;
                if(textoFiltroNuevo.equals(""))
                {
                    itemNoticias_filtrada = itemNoticias_general;

                    llenar();
                }
                else
                {
                    textoFiltroGuardado = textoNuevo;
                    filtrar_noticias();
                    llenar();
                }
                filtrando_noticias = false;
            }
        };
        hilo_consulta_noticias();
        /*if(id_articulo_selecionado >= 0)
        {
            int cont = 0;
            for(ItemNoticia item : itemNoticias_general)
            {
                if(item.getNoticia().id_notiticia == id_articulo_selecionado)
                {
                    recycle.smoothScrollToPosition(cont);
                    break;
                }
                cont ++;
            }
            id_articulo_selecionado = -1;
        }
        /*if(searchView != null)
        {
            if(!textoFiltroGuardado.equals(""))
            {
                searchView.setQuery(textoFiltroGuardado, false);
                searchView.onActionViewExpanded();
            }
        }*/
    }

    @Override
    public void onPause() {
        super.onPause();
        fragmentConsultarNoticiasActivo = false;
    }

    private void llenar()
    {
        //el id_articulo_selecionado es 0  mayor cuando el usuario seleciona un articulo para vista general
        if(id_articulo_selecionado < 0)
        {
            try
            {
                exampleAdapter = new AdapterNoticia(getActivity(),itemNoticias_filtrada, getFragmentManager());
                recycle.setAdapter(exampleAdapter);
            }
            catch (NullPointerException exc)
            {

            }
            noticiasFiltradas = !(itemNoticias_filtrada.size() == itemNoticias_general.size());
        }
        else// si hay un articulo selecionado
        {

            if(!noticiasFiltradas)
            {
                if(id_articulo_selecionado == -1)
                {
                    try
                    {
                        exampleAdapter = new AdapterNoticia(getActivity(),itemNoticias_filtrada, getFragmentManager());
                        recycle.setAdapter(exampleAdapter);
                    }
                    catch (NullPointerException exc)
                    {

                    }
                }
                else
                {
                    id_articulo_selecionado = -1;
                }
            }
            else
            {
                id_articulo_selecionado = -1;
                try
                {
                    exampleAdapter = new AdapterNoticia(getActivity(),itemNoticias_filtrada, getFragmentManager());
                    recycle.setAdapter(exampleAdapter);
                    noticiasFiltradas = false;
                }
                catch (NullPointerException exc)
                {

                }
                if(searchView != null)
                {
                    if(!textoFiltroGuardado.equals(""))
                    {
                        //searchView.onActionViewExpanded();
                        //searchView.setQuery(textoFiltroGuardado, true);
                    }
                }
            }
        }
    }

    private void hilo_consulta_noticias()
    {
        new Thread(new Runnable()//este hilo sigue vivo mientras este fragmente no se ponga en pausa
        {
            public void run() {
                while (fragmentConsultarNoticiasActivo)
                {
                    if(cont >= 5000 && !generandoConsulta && getActivity() != null)
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cont = 0;
                                consultar_noticias();
                            }
                        });
                    }
                    try {
                        Thread.sleep(100);
                        cont += 100;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void consultar_noticias()
    {
        if(!generandoConsulta)
        {
            generandoConsulta = true;
            final Gestion_noticia gestion_noticia = new Gestion_noticia();
            //tomo los parametros del controlador
            HashMap<String,String> params;
            if(itemNoticias_general.isEmpty())// si la lista de noticias esta vacia, consulto todas las noticias
            {
                params = gestion_noticia.consultar_con_imagenes_y_m_primero();
            }
            else// consulto nuevas noticias
            {
                agregar_noticias_nuevas = true;
                params = gestion_noticia.noticia_consultar_mayores(id_ultimo);
            }
            Response.Listener<String> stringListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    noticias_nuevas = gestion_noticia.generar_json(response);
                    if(!agregar_noticias_nuevas)
                    {
                        if(!noticias_nuevas.isEmpty())
                        {
                            id_ultimo = noticias_nuevas.get(0).id_notiticia;
                        }
                    }
                    else
                    {
                        if(!noticias_nuevas.isEmpty())
                        {
                            id_ultimo = noticias_nuevas.get(0).id_notiticia;
                        }
                    }
                    cargar_noticias(noticias_nuevas);
                    generandoConsulta = false;
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    generandoConsulta = false;
                    Log.d("Reponse.Error",error.toString());
                }
            };
            StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
            MySocialMediaSingleton.getInstance(view_permantente.getContext()).addToRequestQueue(stringRequest);
        }
    }

    private void filtrar_noticias()
    {
        textoFiltroGuardado = textoFiltroNuevo;
        ArrayList<ItemNoticia> itemNoticias_aux = new ArrayList<>();
        for(ItemNoticia item : itemNoticias_general)
        {
            String texto1 = item.getNoticia().titulo_noticia.toUpperCase();
            String texto2 = textoFiltroNuevo.toUpperCase();
            if(texto1.contains(texto2))
            {
                itemNoticias_aux.add(item);
            }
        }
        itemNoticias_filtrada = itemNoticias_aux;
        noticiasFiltradas = !(itemNoticias_general.size() == itemNoticias_filtrada.size());
    }

    private void cargar_noticias(ArrayList<Noticia> noticias)
    {
        int num_items = noticias.size();
        for(int i = 0; i < num_items; i ++)
        {
            Gestion_imagen_noticia gestion_imagen_noticia = new Gestion_imagen_noticia();
            ArrayList<Imagen_noticia> imagen_noticias = gestion_imagen_noticia.generar_json(noticias.get(i).json_imagenes);
            Imagen_noticia imagen_noticia = null;
            if(!imagen_noticias.isEmpty())
            {
                imagen_noticia = imagen_noticias.get(0);
            }
            ItemNoticia itemNoticia;
            if(imagen_noticia != null)
            {
                itemNoticia = new ItemNoticia(noticias.get(i), imagen_noticia);
            }
            else
            {
                itemNoticia = new ItemNoticia(noticias.get(i));
            }
            if(agregar_noticias_nuevas)//si antes se agrego articulos, este es un articulo nuevo
            {
                itemNoticias_general.add(0, itemNoticia);
            }
            else
            {
                itemNoticias_general.add(itemNoticia);
            }
        }
        if(!agregado)// si no hay noticias agregadas
        {
            agregado = true;
            itemNoticias_filtrada = itemNoticias_general;
            exampleAdapter= new AdapterNoticia(getActivity(),itemNoticias_filtrada, getFragmentManager());
            recycle = view_permantente.findViewById(R.id.Recycle_IdHome);
            recycle.setLayoutManager(new GridLayoutManager(getContext(),1));
            recycle.setAdapter(exampleAdapter);
            recycle.setHasFixedSize(true);

        }
        else
        {
            if(!noticias.isEmpty())
            {
                exampleAdapter.notifyItemInserted(0);
            }

        }

    }
    private boolean agregado = false;
}
