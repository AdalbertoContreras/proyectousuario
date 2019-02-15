package com.comfacesar.comfacesar;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.comfacesar.comfacesar.fragment.HistorialAlertasFragment;
import com.comfacesar.comfacesar.fragment.HistorialAsesoriasFragment;
import com.comfacesar.comfacesar.fragment.InicioSesionFragment;
import com.comfacesar.comfacesar.fragment.registrarUsuarioFragment;


public class ContainertwoActivity extends AppCompatActivity implements HistorialAsesoriasFragment.OnFragmentInteractionListener, HistorialAlertasFragment.OnFragmentInteractionListener, InicioSesionFragment.OnFragmentInteractionListener, registrarUsuarioFragment.OnFragmentInteractionListener, AcercaDeFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_containertwo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundResource(R.color.Gris3);
        setSupportActionBar(toolbar);

        int cadena = getIntent().getExtras().getInt("id");
        String aux = Integer.toString(cadena);

        Fragment mifragment=null;

        switch (aux)
        {
            case "1":
                mifragment= new HistorialAlertasFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container2,mifragment).commit();
                ShowToolbar("Historial Alertas",true);
                break;
            case "2":
                mifragment= new HistorialAsesoriasFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container2,mifragment).commit();
                ShowToolbar("Historial Asesorias",true);
                break;
            case "3":
                mifragment= new InicioSesionFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container2,mifragment).commit();
                ShowToolbar("Inicio Sesion",true);
                break;
            case "4":
                mifragment= new registrarUsuarioFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container2,mifragment).commit();
                ShowToolbar("Registro Usuario",true);
                break;
            case "5":
                mifragment= new AcercaDeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container2,mifragment).commit();
                ShowToolbar("",true);
                break;
        }
    }
    public void ShowToolbar(String Tittle, boolean upButton)
    {
        Toolbar toolbar =  (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
