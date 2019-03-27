package com.comfacesar.comfacesar.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.Adaptador.AdapterListaAsesoresPorEspecialidad;
import com.comfacesar.comfacesar.Adaptador.Adapter_historia_chat_asesoria;
import com.comfacesar.comfacesar.R;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_administrador;
import com.example.gestion.Gestion_chat_asesoria;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Administrador;
import com.example.modelo.Chat_asesoria;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistorialAsesoriasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistorialAsesoriasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistorialAsesoriasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private View view;
    private OnFragmentInteractionListener mListener;

    public HistorialAsesoriasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistorialAsesoriasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistorialAsesoriasFragment newInstance(String param1, String param2) {
        HistorialAsesoriasFragment fragment = new HistorialAsesoriasFragment();
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

    public static EscuchaorOnBackPressed escuchaorOnBackPressed;
    public interface EscuchaorOnBackPressed
    {
        void onBackPressed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_historial_asesorias, container, false);
        recyclerView = view.findViewById(R.id.historial_asesoriasRecyclerView);
        Gestion_chat_asesoria.arrayChatCambiado = new Gestion_chat_asesoria.ArrayChatCambiado() {
            @Override
            public void chatCambiado() {
                generar_consulta("");
            }
        };
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //consultar_chats();
        generar_consulta("");
    }

    private void consultar_chats()
    {
        //tomo los parametros del controlador
        HashMap<String,String> params = new Gestion_chat_asesoria().consultar_por_usuario(Gestion_usuario.getUsuario_online().id_usuario);
        Log.d("parametros", params.toString());
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                generar_consulta(response);
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, MySocialMediaSingleton.errorListener());
        MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
    }

    private void generar_consulta(final String response)
    {
        ArrayList<Chat_asesoria> list = Gestion_chat_asesoria.getChat_asesorias();
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),1));
        Adapter_historia_chat_asesoria adapter_historia_chat_asesoria = new Adapter_historia_chat_asesoria(list, getFragmentManager(), getActivity());
        recyclerView.setAdapter(adapter_historia_chat_asesoria);
        recyclerView.setHasFixedSize(true);
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
