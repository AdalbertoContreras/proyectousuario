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
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
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
    private Spinner asuntoSpinner;
    private View view;
    private ArrayList<Asunto> asuntoArrayList;
    private Asunto asunto_selecionado;
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
        asuntoSpinner = view.findViewById(R.id.asuntoSpinnerRegistrarAlertaTemprana);
        consultar_asuntos();
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
                    Toast.makeText(view.getContext(),"Inicie sesion antes de realizar una alerta temprana", Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(view.getContext(),"Acepte los permisos primero", Toast.LENGTH_LONG).show();
            }
            }
        });
        asuntoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0)
                {
                    asunto_selecionado = asuntoArrayList.get(position -1);
                }
                else
                {
                    asunto_selecionado = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        descripcionTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            }
        });
        return view;
    }

    private void consultar_asuntos()
    {
        asuntoArrayList = new ArrayList<>();
        Gestion_asunto gestion_asunto = new Gestion_asunto();
        HashMap<String, String> hashMap = gestion_asunto.consultar_asuntos();
        Log.d("Parametros", hashMap.toString());
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                llenar_y_cargar_asuntos(response);
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, MySocialMediaSingleton.errorListener());
        MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
    }

    private void llenar_y_cargar_asuntos(String json_asuntos)
    {
        asuntoArrayList = new Gestion_asunto().generar_json(json_asuntos);
        String[] asuntosArray;
        if(!asuntoArrayList.isEmpty())
        {
            asuntosArray = asuntos_a_array_string();
        }
        else
        {
            asuntosArray= asuntos_a_array_string_vacio();

        }
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_spinner_item, asuntosArray);
        asuntoSpinner.setAdapter(arrayAdapter);
    }

    private String[] asuntos_a_array_string()
    {
        String[] array = new String[asuntoArrayList.size() + 1];
        array[0] = "Selecione un asunto";
        int cont = 1;
        for(Asunto item : asuntoArrayList)
        {
            array[cont] = item.nombre_asunto;
            cont ++;
        }
        return array;
    }
    private String[] asuntos_a_array_string_vacio()
    {
        String[] array = new String[asuntoArrayList.size() + 1];
        array[0] = "No hay asunto";
        return array;
    }

    private Alerta_temprana validar_alerta_temprana()
    {
        Alerta_temprana alerta_temprana = new Alerta_temprana();
        if(asunto_selecionado == null)
        {
            Toast.makeText(view.getContext(), "Selecione el asunto de su alerta.", Toast.LENGTH_LONG).show();
            return  null;
        }
        else
        {
            alerta_temprana.asunto_alerta_temprana = asunto_selecionado.id_asunto;
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
            Log.d("parametros",hashMap.toString());
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
        asuntoSpinner.setSelection(0);
        descripcionTextView.setText("");
    }
}
