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

public class MensajeDarMeGustaDialog extends DialogFragment {
    public String mensaje;
    public FragmentManager fragmentManager;
    public static MensajeDarMeGustaDialog nuevaUbstancia(String mensaje)
    {
        MensajeDarMeGustaDialog mensajeInicioSesionDialog = new MensajeDarMeGustaDialog();
        mensajeInicioSesionDialog.mensaje = mensaje;
        return mensajeInicioSesionDialog;
    }

    public MensajeDarMeGustaDialog()
    {

    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.mensaje_dar_me_gusta, null);

        TextView mensajeTextView;
        mensajeTextView = v.findViewById(R.id.mensajeTextView);
        mensajeTextView.setText(mensaje);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.mensaje_dar_me_gusta, null))
                // Add action buttons
                .setPositiveButton("Iniciar sesion", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(v.getContext(), ContainertwoActivity.class);
                        intent.putExtra("id",3);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Crear cuenta", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(v.getContext(), ContainertwoActivity.class);
                        intent.putExtra("id",4);
                        startActivity(intent);
                    }
                });
        builder.setView(v);
        return builder.create();
    }
}
