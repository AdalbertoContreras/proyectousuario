package com.comfacesar.comfacesar.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.Adaptador.AdapterAlerta;
import com.comfacesar.comfacesar.R;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_alerta_temprana;
import com.example.gestion.Gestion_noticia;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Alerta_temprana;
import com.example.modelo.Usuario;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistorialAlertasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistorialAlertasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistorialAlertasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static FragmentManager fragmentManager;

    private OnFragmentInteractionListener mListener;

    public HistorialAlertasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistorialAlertasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistorialAlertasFragment newInstance(String param1, String param2) {
        HistorialAlertasFragment fragment = new HistorialAlertasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private static View view_permanente;
    private static Usuario usuario_anterior = null;
    private ArrayList<Alerta_temprana> alerta_tempranaArrayList;
    private static  int num_alertas;
    private RecyclerView recyclerView_alertas_tempranas;
    private int cont_m;
    private boolean generando_consulta;
    private boolean seguir;
    private int id_max;
    private boolean agregando_nuevas_alertas;
    private AdapterAlerta adapterItemCliente;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment{
        alerta_tempranaArrayList = new ArrayList<>();
        generando_consulta = false;
        cont_m = 0;
        if(view_permanente == null)
        {
            view_permanente =  inflater.inflate(R.layout.fragment_historial_alertas, container, false);
        }
        return view_permanente;
    }


    private void comparar_num_alertas()
    {
        HashMap<String, String> hashMap = new Gestion_alerta_temprana().num_alertas();
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                //aqui llega la respuesta, dependiendo del tipo de la consulta la proceso
                int aux = 0;
                try
                {
                    aux = Integer.parseInt(response);
                    if(aux != num_alertas)
                    {
                        num_alertas = aux;
                        consultar_alertas_tempranas();
                    }
                }
                catch(NumberFormatException exc)
                {

                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, MySocialMediaSingleton.errorListener());
        MySocialMediaSingleton.getInstance(view_permanente.getContext()).addToRequestQueue(stringRequest);
    }
    public static FragmentManager getFragmentManager_()
    {
        return fragmentManager;
    }
    @Override
    public void onResume() {
        super.onResume();
        fragmentManager = getActivity().getSupportFragmentManager();
        seguir = true;
        cont_m = 10000;
        comparar_num_alertas();
    }

    @Override
    public void onStop() {
        super.onStop();
        seguir = false;
    }

    private void consultar_alertas_tempranas()
    {
        //tomo los parametros del controlador
        if(Gestion_usuario.getUsuario_online() != null && !generando_consulta)
        {
            generando_consulta = true;
            HashMap<String,String> params;
            if(alerta_tempranaArrayList.isEmpty())
            {
                params = new Gestion_alerta_temprana().consultar_alertas_tempranas_por_usuario(Gestion_usuario.getUsuario_online().id_usuario);
            }
            else
            {
                agregando_nuevas_alertas = true;
                params = new Gestion_alerta_temprana().consultar_por_usuario_mayor(id_max, Gestion_usuario.getUsuario_online().id_usuario);
            }
            Response.Listener<String> stringListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    llenar_alertas_tempranas(response);
                }
            };
            StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, MySocialMediaSingleton.errorListener());
            MySocialMediaSingleton.getInstance(view_permanente.getContext()).addToRequestQueue(stringRequest);
        }
    }

    private void llenar_alertas_tempranas(String json)
    {
        if(agregando_nuevas_alertas)
        {
            ArrayList<Alerta_temprana> alerta_tempranas = new Gestion_alerta_temprana().generar_json(json);
            if(alerta_tempranas.isEmpty())
            {
                alerta_tempranaArrayList = alerta_tempranas;
            }
            else
            {
                alerta_tempranaArrayList.addAll(0, alerta_tempranas);
                id_max = alerta_tempranaArrayList.get(0).id_alerta_temprana;
            }
            adapterItemCliente.notifyItemInserted(0);
        }
        else
        {
            alerta_tempranaArrayList = new Gestion_alerta_temprana().generar_json(json);
            //num_alertas = alerta_tempranaArrayList.size();
            recyclerView_alertas_tempranas = view_permanente.findViewById(R.id.historialAlertasTempranasRecyclerView);
            recyclerView_alertas_tempranas.setLayoutManager(new GridLayoutManager(view_permanente.getContext(),1));
            adapterItemCliente = new AdapterAlerta(alerta_tempranaArrayList, getFragmentManager());
            recyclerView_alertas_tempranas.setAdapter(adapterItemCliente);
            recyclerView_alertas_tempranas.setHasFixedSize(true);
        }
        generando_consulta = false;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
