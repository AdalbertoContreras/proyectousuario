package com.comfacesar.comfacesar.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.comfacesar.comfacesar.R;
import com.example.modelo.Alerta_temprana;
import com.example.modelo.Asunto;

public class AlertaTempranaDialog extends DialogFragment {
    public static Alerta_temprana alerta_temprana;
    public static Asunto asunto;
    public AlertaTempranaDialog()
    {

    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.detallealertedialog, null);

        TextView numeroTelefonoTextView, direccionTextView, descripionTextView;
        descripionTextView = v.findViewById(R.id.descripcionTextView);
        //asuntoTextView.setText(asunto.nombre_asunto);
        numeroTelefonoTextView = v.findViewById(R.id.numeroTelefonoTextView);
        direccionTextView = v.findViewById(R.id.direccionTextView);
        descripionTextView.setText(alerta_temprana.descripcion_alerta_temprana);
        numeroTelefonoTextView.setText(alerta_temprana.numero_telefono_alerta_temprana);
        direccionTextView.setText(alerta_temprana.direccion_alerta_temprana);
        //fechaTextView.setText(alerta_temprana.fecha_alerta_temprana);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.detallealertedialog, null))
                // Add action buttons
                .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
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
