package com.comfacesar.comfacesar.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.Dialog.MensajeInicioSesionDialog;
import com.comfacesar.comfacesar.R;
import com.example.extra.Config;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_alerta_temprana;
import com.example.gestion.Gestion_asunto;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Alerta_temprana;
import com.example.modelo.Asunto;
import com.example.modelo.Movil;
import com.example.modelo.Usuario;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlertTempranaFragment extends Fragment {
    private EditText nombreTextView, apellidoTextView, direccionTextView, telefonoTextView, descripcionTextView;
    private Button enviar_alerta_tempranaButton;
    private View view;
    private ArrayList<Asunto> asuntoArrayList;
    private Asunto asunto_selecionado;
    private RadioButton abusoRadioButton;
    private RadioButton maltratoRadioButton;
    private RadioButton acosoRadioButton;
    private RadioButton violenciaRadioButton;
    public AlertTempranaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_alert_temprana, container, false);
        descripcionTextView = view.findViewById(R.id.descripcionEditTextRegiostrarAlerta);
        enviar_alerta_tempranaButton = view.findViewById(R.id.enviarButtonRegistrarAlertatemprana);
        abusoRadioButton = view.findViewById(R.id.abusoRadioButton);
        acosoRadioButton = view.findViewById(R.id.acosoRadioButton);
        violenciaRadioButton = view.findViewById(R.id.violenciaRadioButton);
        maltratoRadioButton = view.findViewById(R.id.maltratoRadioButton);
        enviar_alerta_tempranaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(Config.getImei() != null)
            {
                if(Gestion_usuario.getUsuario_online() != null)
                {
                    registrar_alerta_temprana(validar_alerta_temprana());
                }
                else
                {
                    MensajeInicioSesionDialog detalleAsesorDialog = MensajeInicioSesionDialog.nuevaUbstancia("Inicie sesionpara poder enviar una alerta.");
                    try {
                        detalleAsesorDialog.show(getActivity().getSupportFragmentManager(), "missiles");
                    } catch (IllegalStateException ignored) {

                    }
                }

            }
            else
            {
                Toast.makeText(view.getContext(),"Acepte los permisos primero", Toast.LENGTH_LONG).show();
            }
            }
        });
        descripcionTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            }
        });
        return view;
    }

    private Alerta_temprana validar_alerta_temprana()
    {
        Alerta_temprana alerta_temprana = new Alerta_temprana();
        if(acosoRadioButton.isChecked())
        {
            alerta_temprana.asunto_alerta_temprana = 1;
        }
        if(maltratoRadioButton.isChecked())
        {
            alerta_temprana.asunto_alerta_temprana = 2;
        }
        if(acosoRadioButton.isChecked())
        {
            alerta_temprana.asunto_alerta_temprana = 3;
        }
        if(violenciaRadioButton.isChecked())
        {
            alerta_temprana.asunto_alerta_temprana = 4;
        }
        if(descripcionTextView.getText().toString().isEmpty())
        {
            Toast.makeText(view.getContext(), "Ingrese la descripcion de la alerta.", Toast.LENGTH_LONG).show();
            return  null;
        }
        else
        {
            alerta_temprana.descripcion_alerta_temprana = descripcionTextView.getText().toString();
        }
        alerta_temprana.usuario_alerta_temprana = Gestion_usuario.getUsuario_online().id_usuario;
        return alerta_temprana;
    }

    private void registrar_alerta_temprana(Alerta_temprana alerta_temprana)
    {
        if(alerta_temprana != null)
        {
            Gestion_alerta_temprana gestion_alerta_temprana = new Gestion_alerta_temprana();
            HashMap<String, String> hashMap = gestion_alerta_temprana.registrar_alerta_temprana(alerta_temprana);
            Response.Listener<String> stringListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    if(response.equals("1"))
                    {
                        Toast.makeText(view.getContext(), "Alerta enviada correctamente", Toast.LENGTH_LONG).show();
                        limpiar();
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
    }

    private void limpiar()
    {
        descripcionTextView.setText("");
        abusoRadioButton.setChecked(true);
    }
}
