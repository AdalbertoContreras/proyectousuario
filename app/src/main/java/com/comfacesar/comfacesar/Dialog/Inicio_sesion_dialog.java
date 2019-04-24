package com.comfacesar.comfacesar.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.R;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Usuario;

import java.util.ArrayList;
import java.util.HashMap;

public class Inicio_sesion_dialog extends DialogFragment {
    private View view;
    private ProgressDialog dialog;
    private TextView nombreCuenta, contraseñaCuenta;
    private IniciarSesion iniciar;
    public interface IniciarSesion
    {
        void sesionIniciada(Inicio_sesion_dialog inicio_sesion_dialog);
    }
    public Inicio_sesion_dialog()
    {

    }

    public static Inicio_sesion_dialog newInstancia(IniciarSesion iniciarSesion)
    {
        Inicio_sesion_dialog inicio_sesion_dialog = new Inicio_sesion_dialog();
        inicio_sesion_dialog.iniciar = iniciarSesion;
        return inicio_sesion_dialog;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.inicio_sesion_dialog, null);


        final Button iniciarSesion;
        nombreCuenta = view.findViewById(R.id.nombreCuentaEditText);
        contraseñaCuenta = view.findViewById(R.id.contraseñaEditText);
        iniciarSesion =view.findViewById(R.id.iniciarSesionButton);
        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombreCuenta.getText().toString().isEmpty())
                {
                    Toast.makeText(view.getContext(), "Ingrese su nombre de cuenta", Toast.LENGTH_LONG).show();
                }
                if(contraseñaCuenta.getText().toString().isEmpty())
                {
                    Toast.makeText(view.getContext(), "Ingrese la contraseña de su cuenta", Toast.LENGTH_LONG).show();
                }
                dialog = new ProgressDialog(view.getContext());
                dialog.show();
                dialog.setCancelable(false);
                Usuario usuario = new Usuario();
                usuario.nombre_cuenta_usuario = nombreCuenta.getText().toString().toLowerCase();
                usuario.contrasena_usuario = contraseñaCuenta.getText().toString();
                validarUsuario(usuario);

            }
        });
        builder.setView(view);
        return builder.create();
    }

    private void validarUsuario(Usuario usuario)
    {

        HashMap<String, String> params = new Gestion_usuario().validar_cuenta_y_generar_token(usuario);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try
                {
                    Integer.parseInt(response);
                    dialog.dismiss();
                    Toast.makeText(view.getContext(), "Datos de usuario " +
                            "incorrecto", Toast.LENGTH_LONG).show();
                }
                catch(NumberFormatException exc)
                {
                    ArrayList<Usuario> usuarios = new Gestion_usuario().generar_json(response);
                    if(usuarios.isEmpty())
                    {
                        Toast.makeText(view.getContext(), "Error en el sistema",
                                Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                    else
                    {
                        usuarios.get(0).contrasena_usuario = contraseñaCuenta.getText().toString();
                        Gestion_usuario.setUsuario_online(usuarios.get(0));
                        dialog.dismiss();
                        Toast.makeText(view.getContext(), "Sesion iniciada",
                                Toast.LENGTH_LONG).show();
                        iniciar.sesionIniciada(Inicio_sesion_dialog.this);
                    }
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(view.getContext(), "Datos de usuario " +
                        "incorrecto", Toast.LENGTH_LONG).show();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
    }
}