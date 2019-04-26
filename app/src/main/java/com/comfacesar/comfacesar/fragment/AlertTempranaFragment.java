package com.comfacesar.comfacesar.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.comfacesar.comfacesar.Dialog.DatosAuxiliaresAlertaDialog;
import com.comfacesar.comfacesar.Dialog.MensajeInicioSesionDialog;
import com.comfacesar.comfacesar.R;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Alerta_temprana;
import com.example.modelo.Asunto;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlertTempranaFragment extends Fragment {
    private EditText descripcionTextView;
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
                if(Gestion_usuario.getUsuario_online() != null)
                {
                    registrar_alerta_temprana(validar_alerta_temprana());
                }
                else
                {
                    MensajeInicioSesionDialog detalleAsesorDialog = MensajeInicioSesionDialog.nuevaInstancia("Inicie sesion para poder enviar una alerta.");
                    try {
                        detalleAsesorDialog.show(getActivity().getSupportFragmentManager(), "missiles");
                    } catch (IllegalStateException ignored) {

                    }
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
        if(abusoRadioButton.isChecked())
        {
            alerta_temprana.asunto_alerta_temprana = 4;
        }
        if(violenciaRadioButton.isChecked())
        {
            alerta_temprana.asunto_alerta_temprana = 3;
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
            DatosAuxiliaresAlertaDialog datosAuxiliaresAlertaDialog = DatosAuxiliaresAlertaDialog.newInstancia(alerta_temprana, new DatosAuxiliaresAlertaDialog.AlertaTempranaRegistrada() {
                @Override
                public void alertaTempranaRegistrada(DatosAuxiliaresAlertaDialog datosAuxiliaresAlertaDialog) {
                    abusoRadioButton.setChecked(true);
                    descripcionTextView.setText("");
                    datosAuxiliaresAlertaDialog.dismiss();
                }
            });
            datosAuxiliaresAlertaDialog.show(getFragmentManager(), "missiles");
        }
    }
}
