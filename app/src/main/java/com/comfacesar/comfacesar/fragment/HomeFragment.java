package com.comfacesar.comfacesar.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.Adaptador.AdapterNoticia;
import com.comfacesar.comfacesar.Item.ItemNoticia;
import com.comfacesar.comfacesar.R;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_imagen_noticia;
import com.example.gestion.Gestion_me_gusta_noticia;
import com.example.gestion.Gestion_noticia;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Imagen_noticia;
import com.example.modelo.Me_gusta_noticia;
import com.example.modelo.Noticia;
import com.example.modelo.Usuario;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private List<ItemNoticia> list_items;
    private RecyclerView recycle;
    private AdapterNoticia exampleAdapter;
    private Context context;
    private ArrayList<Noticia> noticias;
    private boolean cargando_consulta = true;
    private View view_permantente;
    private int num_articulos = 0;
    private ArrayList<Noticia> noticias_actuales;
    private Usuario usuario_anterior = null;
    private Usuario usuario_nuevo = null;
    public static boolean seguir = true;
    public static boolean creado = false;

    public String textoAux1="Con la participación de más de 800 personas de nacionalidad venezolana, " +
            "la Caja de Compensación Familiar del Cesar (COMFACESAR), realizó a través de la Agencia de Gestión y" +
            " Colocación de Empleo, la Feria de Atención Integral a Migrantes, en el Colegio “Rodolfo Campo Soto”.";

    public String textoAux2= "Embarazos prematuros una problematica palpable en la Ciudad de Valledupar";

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
        seguir = true;
        view_permantente = inflater.inflate(R.layout.fragment_home, container, false);
        recycle = view_permantente.findViewById(R.id.Recycle_IdHome);
        recycle.setLayoutManager(new LinearLayoutManager((context)));
        return view_permantente;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(new Runnable()
        {
            public void run() {
                try {
                    for(;;) {
                        if(seguir)
                        {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    consultar_num_noticias();
                                }
                            });
                            Thread.sleep(5000);
                        }
                        else
                        {
                            return;
                        }

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void consultar_noticias()
    {
        if(!generando_conmsulta)
        {
            generando_conmsulta = true;
            final Gestion_noticia gestion_noticia = new Gestion_noticia();
            //tomo los parametros del controlador
            HashMap<String,String> params = gestion_noticia.consultar_con_imagenes_y_m_primero();
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
        System.out.println("Estouy consultando");
        final Gestion_noticia gestion_noticia = new Gestion_noticia();
        //tomo los parametros del controlador
        HashMap<String,String> params = gestion_noticia.consultar_num_noticia();
        Log.d("Parametros", params.toString());
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                int val = 0;
                try
                {
                    val = Integer.parseInt(response);
                    if(usuario_anterior == usuario_nuevo)
                    {
                        if(val != num_articulos)
                        {
                            consultar_noticias();
                        }
                    }

                }
                catch (NumberFormatException exc)
                {
                    Log.d("Error", exc.toString());
                }
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, MySocialMediaSingleton.errorListener());
        MySocialMediaSingleton.getInstance(view_permantente.getContext()).addToRequestQueue(stringRequest);
    }

    private void generar_comsulta(ArrayList<Noticia> list)
    {
        list_items = new ArrayList<>();
        num_articulos = list.size();
        iniciar_imagenes_a_noticias(list);
        cargar_noticias(noticias);
        exampleAdapter= new AdapterNoticia(list_items, getFragmentManager());
        recycle.setAdapter(exampleAdapter);
        recycle.setHasFixedSize(true);
        generando_conmsulta = false;

    }

    private void iniciar_imagenes_a_noticias(ArrayList<Noticia> list) {
        noticias = new ArrayList<>();
        for (Noticia noticia : list) {
            if (noticia.tipo_creacion_noticia == 1) {
                noticias.add(noticia);
            }
        }
    }

    private void cargar_noticias(ArrayList<Noticia> noticias)
    {
        int contador = 1;
        //cargar noticias de la pagina
        int num_items = noticias.size();
        for(int i = 0; i < num_items; i ++){
            //cargar imagen por noticia
            Gestion_imagen_noticia gestion_imagen_noticia = new Gestion_imagen_noticia();
            ArrayList<Imagen_noticia> imagen_noticias = gestion_imagen_noticia.generar_json(noticias.get(i).json_imagenes);
            Imagen_noticia imagen_noticia = null;
            if(!imagen_noticias.isEmpty())
            {
                imagen_noticia = imagen_noticias.get(0);
            }
            if(imagen_noticia != null)
            {
                list_items.add(new ItemNoticia(noticias.get(i), imagen_noticia));
            }
            else
            {
                list_items.add(new ItemNoticia(noticias.get(i)));
            }
        }
    }

}
