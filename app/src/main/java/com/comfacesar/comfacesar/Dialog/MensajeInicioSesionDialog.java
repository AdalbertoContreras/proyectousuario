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

import com.comfacesar.comfacesar.ContainerActivity;
import com.comfacesar.comfacesar.ContainertwoActivity;
import com.comfacesar.comfacesar.R;

public class MensajeInicioSesionDialog extends DialogFragment {
    public String mensaje;
    public FragmentManager fragmentManager;
    public static MensajeInicioSesionDialog nuevaUbstancia(String mensaje)
    {
        MensajeInicioSesionDialog mensajeInicioSesionDialog = new MensajeInicioSesionDialog();
        mensajeInicioSesionDialog.mensaje = mensaje;
        return mensajeInicioSesionDialog;
    }

    public MensajeInicioSesionDialog()
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
                .setPositiveButton("Iniciar sesion", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(v.getContext(), ContainertwoActivity.class);
                        intent.putExtra("id",3);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        dismiss();
                    }
                });
        builder.setView(v);
        return builder.create();
    }
}
