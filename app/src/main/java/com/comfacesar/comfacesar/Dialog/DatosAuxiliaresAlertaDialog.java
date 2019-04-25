package com.comfacesar.comfacesar.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.R;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_alerta_temprana;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Alerta_temprana;

import java.util.HashMap;

public class DatosAuxiliaresAlertaDialog extends DialogFragment {
    Alerta_temprana alerta_temprana;
    private View view;
    private AlertaTempranaRegistrada alertaTempranaRegistrada;

    public interface  AlertaTempranaRegistrada
    {
        void alertaTempranaRegistrada(DatosAuxiliaresAlertaDialog datosAuxiliaresAlertaDialog);
    }

    public DatosAuxiliaresAlertaDialog()
    {

    }

    public static DatosAuxiliaresAlertaDialog newInstancia(Alerta_temprana alerta_temprana, AlertaTempranaRegistrada alertaTempranaRegistrada)
    {
        DatosAuxiliaresAlertaDialog datosAuxiliaresAlertaDialog = new DatosAuxiliaresAlertaDialog();
        datosAuxiliaresAlertaDialog.alerta_temprana = alerta_temprana;
        datosAuxiliaresAlertaDialog.alertaTempranaRegistrada = alertaTempranaRegistrada;
        return datosAuxiliaresAlertaDialog;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.datos_extras_alerta_temprana_dialog, null);

        final TextView direccionTextView, numeroTelefonoTextView;
        Button enviarButton, cancelarButton;
        direccionTextView = view.findViewById(R.id.direccionEditText);
        numeroTelefonoTextView = view.findViewById(R.id.numeroTelefonoEditText);
        direccionTextView.setText(Gestion_usuario.getUsuario_online().direccion_usuario);
        numeroTelefonoTextView.setText(Gestion_usuario.getUsuario_online().telefono_usuario);
        enviarButton = view.findViewById(R.id.enviarButton);
        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numeroTelefonoTextView.getText().toString().isEmpty())
                {
                    Toast.makeText(v.getContext(), "Ingrese el numero de telefono, donde quiere que nos comuniquemos", Toast.LENGTH_LONG).show();
                    return;
                }
                if(direccionTextView.getText().toString().isEmpty())
                {
                    Toast.makeText(v.getContext(), "Ingrese una direccion donde ubicar el lugar de la alerta", Toast.LENGTH_LONG).show();
                    return;
                }
                alerta_temprana.direccion_alerta_temprana = direccionTextView.getText().toString();
                alerta_temprana.numero_telefono_alerta_temprana = numeroTelefonoTextView.getText().toString();
                Gestion_alerta_temprana gestion_alerta_temprana = new Gestion_alerta_temprana();
                HashMap<String, String> hashMap = gestion_alerta_temprana.registrar_alerta_temprana(alerta_temprana);
                Response.Listener<String> stringListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("1"))
                        {
                            Toast.makeText(view.getContext(), "Alerta enviada correctamente", Toast.LENGTH_LONG).show();
                            alertaTempranaRegistrada.alertaTempranaRegistrada(DatosAuxiliaresAlertaDialog.this);
                        }
                        else
                        {
                            Toast.makeText(view.getContext(), "Error al enviar alerta", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, MySocialMediaSingleton.errorListener());
                MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
            }
        });
        builder.setView(view);
        return builder.create();
    }
}