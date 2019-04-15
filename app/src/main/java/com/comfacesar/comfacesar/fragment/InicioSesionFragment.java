package com.comfacesar.comfacesar.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.ContainerActivity;
import com.comfacesar.comfacesar.ContainertwoActivity;
import com.comfacesar.comfacesar.R;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_administrador;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Administrador;
import com.example.modelo.Usuario;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InicioSesionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InicioSesionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioSesionFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressDialog dialog;
    private OnFragmentInteractionListener mListener;
    public Activity actividad;
    private TextView registrarmeTextView;
    private SignInButton googleSignInButton;


    public InicioSesionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioSesionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioSesionFragment newInstance(String param1, String param2) {
        InicioSesionFragment fragment = new InicioSesionFragment();
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
    private EditText contraseñaEditText;
    private Button iniciarSesionButton;
    private GoogleApiClient googleApiClient;
    private final int SIGN_IN_CODE = 777;
    private GoogleSignInClient googleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view_permanente = inflater.inflate(R.layout.fragment_inicio_sesion, container, false);

        registrarmeTextView = view_permanente.findViewById(R.id.registrarmeTextView);
        nombreCuentaEditText = view_permanente.findViewById(R.id.nombreCuentaEditTextInicioSesion);
        contraseñaEditText = view_permanente.findViewById(R.id.contraseñaEditTextInicioSesion);
        iniciarSesionButton = view_permanente.findViewById(R.id.iniciarSesionButton);
                registrarmeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ContainertwoActivity.class);
                intent.putExtra("id",4);
                startActivity(intent);
            }
        });
        iniciarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(view_permanente.getContext());
                dialog.show();
                dialog.setCancelable(false);
                if(nombreCuentaEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(view_permanente.getContext(), "Ingrese el nombre de su cuenta de usuario", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    return;
                }
                if(contraseñaEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(view_permanente.getContext(), "Ingrese la contraseña de su cuenta de usuario", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    return;
                }
                final Usuario usuario = new Usuario();
                usuario.nombre_cuenta_usuario = nombreCuentaEditText.getText().toString().toLowerCase();
                usuario.contrasena_usuario = contraseñaEditText.getText().toString();
                validarUsuario(usuario);
            }
        });
        googleSignInButton = view_permanente.findViewById(R.id.googleSignInButton);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });
        googleSignInClient = GoogleSignIn.getClient(getActivity(), googleSignInOptions);
        return view_permanente;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_CODE)
        {
            handleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
        }
    }

    private void handleSignInResult(GoogleSignInResult googleSignInResult)
    {
        if(googleSignInResult.isSuccess())
        {
            GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
            nombreCuentaEditText.setText(googleSignInAccount.getDisplayName());

            // Google sign out
            googleSignInClient.signOut();
            googleSignInClient.revokeAccess();
        }
        else
        {

        }
    }
    private void validarUsuario(Usuario usuario)
    {
        HashMap<String, String> params = new Gestion_usuario().validar_usuario(usuario);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                int val = 0;
                try
                {
                    val = Integer.parseInt(response);
                    if(val == 0)
                    {
                        dialog.dismiss();
                        Toast.makeText(view_permanente.getContext(), "Datos de usuario " +
                                "incorrecto", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        consultar_usuario_y_agregar_online(val);
                    }
                }
                catch(NumberFormatException exc)
                {
                    dialog.dismiss();
                    Toast.makeText(view_permanente.getContext(), "Datos de usuario " +
                            "incorrecto", Toast.LENGTH_LONG).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(view_permanente.getContext(), "Datos de usuario " +
                        "incorrecto", Toast.LENGTH_LONG).show();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(view_permanente.getContext()).addToRequestQueue(stringRequest);
    }

    private void consultar_usuario_y_agregar_online(int id_usuario)
    {
        Usuario usuario = new Usuario();
        usuario.nombre_cuenta_usuario = nombreCuentaEditText.getText().toString();
        usuario.contrasena_usuario = contraseñaEditText.getText().toString();
        usuario.id_usuario = id_usuario;
        Gestion_usuario.setUsuario_online(usuario);
        HashMap<String, String> hashMap = new Gestion_usuario().consultar_usuario_por_id(usuario);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                ArrayList<Usuario> usuarios = new Gestion_usuario().generar_json(response);
                if(usuarios.isEmpty())
                {
                    Toast.makeText(view_permanente.getContext(), "Error en el sistema",
                            Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
                else
                {
                    usuarios.get(0).contrasena_usuario = contraseñaEditText.getText().toString();
                    Gestion_usuario.setUsuario_online(usuarios.get(0));
                    dialog.dismiss();
                    Toast.makeText(view_permanente.getContext(), "Logueado",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), ContainerActivity.class);
                    salvarSesion();
                    startActivity(intent);
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(view_permanente.getContext(), "Error",
                        Toast.LENGTH_LONG).show();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(view_permanente.getContext()).addToRequestQueue(stringRequest);

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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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


    private void salvarSesion()
    {
        SharedPreferences prefs = getActivity().getSharedPreferences("SESION_USER", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = prefs.edit();
        myEditor.putString("USER", Gestion_usuario.getUsuario_online().nombre_cuenta_usuario);
        myEditor.putString("PASS", Gestion_usuario.getUsuario_online().contrasena_usuario);
        myEditor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Gestion_usuario.getUsuario_online() != null)
        {
            getActivity().finish();
        }
    }
}
