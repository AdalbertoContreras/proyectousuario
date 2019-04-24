package com.comfacesar.comfacesar.Activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.comfacesar.comfacesar.R;

public class HistorialChatVacioActivity extends AppCompatActivity {

    private Button enviarAlertaButton;
    public static EnviarAsesoria enviarAsesoria;
    public static boolean estoyActivo;
    public interface EnviarAsesoria
    {
        void enviarAsesoria();
    }
    public HistorialChatVacioActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_vacio);

        enviarAlertaButton = findViewById(R.id.irAsesoriaButton);
        enviarAlertaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                enviarAsesoria.enviarAsesoria();
            }
        });
    }

    public void ShowToolbar(String Tittle, boolean upButton)
    {
        Toolbar toolbar = findViewById(R.id.toolbar_detalle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        CollapsingToolbarLayout collapsing=  findViewById(R.id.collapsin_id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        estoyActivo = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        estoyActivo = false;
    }
}
