package com.comfacesar.comfacesar.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.comfacesar.comfacesar.ContainertwoActivity;
import com.comfacesar.comfacesar.R;

public class FiltrarNoticiaDialog extends DialogFragment {
    public String mensaje;
    public FragmentManager fragmentManager;
    public EscuchadoEvento escuchadoEvento;
    public static MensajeInicioSesionDialog nuevaUbstancia(String mensaje)
    {
        MensajeInicioSesionDialog mensajeInicioSesionDialog = new MensajeInicioSesionDialog();
        mensajeInicioSesionDialog.mensaje = mensaje;
        return mensajeInicioSesionDialog;
    }

    public FiltrarNoticiaDialog()
    {

    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.mensaje_iniciar_sesion, null);

        EditText claveFiltroEditText;
        TextView limpiarFiltroTextView;
        TextView filtrarTextView;
        claveFiltroEditText = v.findViewById(R.id.claveFiltroEditText);
        limpiarFiltroTextView = v.findViewById(R.id.limpiarFiltroTextView);
        filtrarTextView = v.findViewById(R.id.filtrarTextView);
        limpiarFiltroTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        limpiarFiltroTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        builder.setView(v);
        return builder.create();
    }

    public interface EscuchadoEvento
    {
        void limpiarFiltro();
        void filtrarNoticias(String clave);
    }
}
