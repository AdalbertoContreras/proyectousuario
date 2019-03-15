package com.comfacesar.comfacesar.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.R;
import com.example.extra.Config;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Usuario;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ModificarDatosCuentaUsuarioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ModificarDatosCuentaUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarDatosCuentaUsuarioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ModificarDatosCuentaUsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModificarDatosCuentaUsuarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModificarDatosCuentaUsuarioFragment newInstance(String param1, String param2) {
        ModificarDatosCuentaUsuarioFragment fragment = new ModificarDatosCuentaUsuarioFragment();
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
    private EditText nombreCuentaEditText;
    private EditText contraseñaCuentaEditText;
    private EditText contraseñaCuentaAnteriorEditText;
    private EditText contraseñaCuentaVerificadaEditText;
    private Button modificar_usuario;
    private Usuario usuario_espejo;
    private String contraseña_anterior;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view_permanente = inflater.inflate(R.layout.fragment_modificar_datos_cuenta_usuario, container, false);
        nombreCuentaEditText = view_permanente.findViewById(R.id.nombreCuentaUsuarioEditText);
        contraseñaCuentaEditText = view_permanente.findViewById(R.id.contraseñaCuentaUsuarioEditText);
        contraseñaCuentaAnteriorEditText = view_permanente.findViewById(R.id.contraseñaCuentaAnteriorUsuarioEditText);
        contraseñaCuentaVerificadaEditText = view_permanente.findViewById(R.id.contraseñaCuentaVerificadaUsuarioEditText);
        modificar_usuario = view_permanente.findViewById(R.id.modificarUsuarioButton);
        usuario_espejo = new Usuario();
        usuario_espejo.id_usuario = Gestion_usuario.getUsuario_online().id_usuario;
        nombreCuentaEditText.setText(Gestion_usuario.getUsuario_online().nombres_usuario);
        evento_modificar_usuario();
        cargar_datos_usuario();
        return view_permanente;
    }

    private void evento_modificar_usuario()
    {
        modificar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Config.getImei() == null)
                {
                    Toast.makeText(view_permanente.getContext(), "Acepte los permiso primero antes de modificar los datos de su cuenta.", Toast.LENGTH_LONG).show();
                    return;
                }
                if(contraseñaCuentaAnteriorEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(view_permanente.getContext(), "Ingrese la contraseña de esta cuenta.", Toast.LENGTH_LONG).show();
                    return;
                }
                validar_cuenta();
            }
        });
    }

    private void validar_cuenta()
    {
        contraseña_anterior = Gestion_usuario.getUsuario_online().contrasena_usuario;
        Usuario usuario_con_contraseña_validad = Gestion_usuario.getUsuario_online();
        usuario_con_contraseña_validad.contrasena_usuario = contraseñaCuentaAnteriorEditText.getText().toString();
        Gestion_usuario.setUsuario_online(usuario_con_contraseña_validad);
        HashMap<String, String> hashMap = new Gestion_usuario().validar_usuario(usuario_con_contraseña_validad);
        Log.d("parametros", hashMap.toString());
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.d("Response : ", response);
                int val = 0;
                try
                {
                    val = Integer.parseInt(response);
                    if(val > 0)
                    {
                        if(contraseñaCuentaEditText.getText().toString().isEmpty())
                        {
                            Toast.makeText(view_permanente.getContext(), "Ingrese la nueva contraseña de su cuenta.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(contraseñaCuentaVerificadaEditText.getText().toString().isEmpty())
                        {
                            Toast.makeText(view_permanente.getContext(), "Ingrese de nuevo la nueva contraseña.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(!contraseñaCuentaEditText.getText().toString().equals(contraseñaCuentaVerificadaEditText.getText().toString()))
                        {
                            Toast.makeText(view_permanente.getContext(), "Las contraseñas ingresadas no coinciden.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else
                        {
                            actualizar_contraseña();
                        }
                    }
                    else
                    {
                        Toast.makeText(view_permanente.getContext(), "No cuenta con acceso a cambiar la contraseña de esta cuenta", Toast.LENGTH_LONG).show();
                        Gestion_usuario.getUsuario_online().contrasena_usuario = contraseña_anterior;
                    }
                }
                catch (NumberFormatException exc)
                {
                    Toast.makeText(view_permanente.getContext(), "No cuenta con acceso a cambiar la contraseña de esta cuenta", Toast.LENGTH_LONG).show();
                    Gestion_usuario.getUsuario_online().contrasena_usuario = contraseña_anterior;
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view_permanente.getContext(),"Ha ocurrido un error en el servidor", Toast.LENGTH_LONG).show();
                Gestion_usuario.getUsuario_online().contrasena_usuario = contraseña_anterior;
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(view_permanente.getContext()).addToRequestQueue(stringRequest);
    }

    public void actualizar_contraseña()
    {
        usuario_espejo.contrasena_usuario = contraseñaCuentaEditText.getText().toString();
        Gestion_usuario.getUsuario_online().contrasena_usuario = contraseña_anterior;
        HashMap<String, String> hashMap = new Gestion_usuario().actualizar_contrasena_usuario(usuario_espejo);
        Log.d("parametros", hashMap.toString());
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.d("Response : ", response);
                int val = 0;
                try
                {
                    val = Integer.parseInt(response);
                    if(val > 0)
                    {
                        Gestion_usuario.getUsuario_online().contrasena_usuario = usuario_espejo.contrasena_usuario;
                        Toast.makeText(view_permanente.getContext(),"Datos de la cuenta actualizados", Toast.LENGTH_LONG).show();
                    }
                }
                catch (NumberFormatException exc)
                {
                    Toast.makeText(view_permanente.getContext(),"Error al actualizar datos de la cuenta", Toast.LENGTH_LONG).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view_permanente.getContext(),"Ha ocurrido un error en el servidor", Toast.LENGTH_LONG).show();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(view_permanente.getContext()).addToRequestQueue(stringRequest);
    }

    private void cargar_datos_usuario()
    {
        nombreCuentaEditText.setText(usuario_espejo.nombre_cuenta_usuario);
        contraseñaCuentaEditText.setText("");
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
