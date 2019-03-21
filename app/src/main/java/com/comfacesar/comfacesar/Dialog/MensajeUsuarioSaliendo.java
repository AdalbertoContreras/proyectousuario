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
import android.widget.TextView;

import com.comfacesar.comfacesar.ContainertwoActivity;
import com.comfacesar.comfacesar.R;

public class MensajeUsuarioSaliendo extends DialogFragment {
    public String mensaje;
    public FragmentManager fragmentManager;
    public CerrarAplicacion cerrarAplicacion;
    public interface CerrarAplicacion
    {
        void usuarioAcepto(MensajeUsuarioSaliendo mensajeUsuarioSaliendo);
        void usuarioNoAcepto(MensajeUsuarioSaliendo mensajeUsuarioSaliendo);
    }
    public static MensajeUsuarioSaliendo nuevaUbstancia(CerrarAplicacion cerrarAplicacion)
    {
        MensajeUsuarioSaliendo mensajeInicioSesionDialog = new MensajeUsuarioSaliendo();
        mensajeInicioSesionDialog.mensaje = "¿Desea seguir ejecutando la aplicacion en segundo plano para recibir nuevas notificaciones?";
        mensajeInicioSesionDialog.cerrarAplicacion = cerrarAplicacion;
        return mensajeInicioSesionDialog;
    }

    public MensajeUsuarioSaliendo()
    {

    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.mensaje_iniciar_sesion, null);

        TextView mensajeTextView;
        mensajeTextView = v.findViewById(R.id.mensajeTextView);
        mensajeTextView.setText(mensaje);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.mensaje_iniciar_sesion, null))
                // Add action buttons
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cerrarAplicacion.usuarioAcepto(MensajeUsuarioSaliendo.this);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        cerrarAplicacion.usuarioNoAcepto(MensajeUsuarioSaliendo.this);
                    }
                });
        builder.setView(v);
        return builder.create();
    }


}
