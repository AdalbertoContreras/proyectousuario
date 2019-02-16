package com.comfacesar.comfacesar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;


import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.comfacesar.comfacesar.adapterViewpager.MyPagerAdapter;
import com.comfacesar.comfacesar.fragment.asesoriaFragment;
import com.example.extra.Config;
import com.example.gestion.Gestion_usuario;

public class ContainerActivity extends AppCompatActivity {
    private Menu menu;
    private FloatingActionButton chat_asesoriaFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container2);
        new Config().iniciar_config(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("ServiAmigo");
        toolbar.setBackgroundResource(R.color.Gris3);
        setSupportActionBar(toolbar);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), getBaseContext());
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        chat_asesoriaFloatingActionButton = findViewById(R.id.chat_asesoriasButton);
        if(Gestion_usuario.getUsuario_online() != null)
        {
            chat_asesoriaFloatingActionButton.setEnabled(true);
        }
        else
        {
            chat_asesoriaFloatingActionButton.setEnabled(false);
        }
        chat_asesoriaFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asesoriaFragment asesoriaFragment = new asesoriaFragment();
                Intent intent= new Intent(ContainerActivity.this, ContainertwoActivity.class);
                intent.putExtra("id",6);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //añadir color  a menu
        for(int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.Gris)), 0,     spanString.length(), 0); //fix the color to white
            item.setTitle(spanString);
        }
        this.menu = menu;
        return true;
    }

    private void cambiar_menu()
    {
        menu.clear();
        menu.add(10, 11, 0, "Crear anuncio");
        menu.add(10, 12, 1, "Modificar anuncio");
        menu.add(10, 13, 2, "Eliminar anuncio");
        menu.add(10, 14, 3, "Actualizar");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent = new Intent(ContainerActivity.this, ContainertwoActivity.class);;
        boolean selecionado = false;
        //Fragment mifragment=null;
        switch (item.getItemId())
        {
            case R.id.historialAlertasMenu:
                cambiar_menu();
                intent.putExtra("id",1);
                selecionado = true;
                break;

            case  R.id.historialAsesoriasMenu:
                intent.putExtra("id",2);
                selecionado = true;
                break;
            case  R.id.iniciarSesionMenu:
                intent.putExtra("id",3);
                selecionado = true;
                break;
            case  R.id.registrarmeMenu:
                intent.putExtra("id",4);
                selecionado = true;
                break;
            case  R.id.acercaDeMenu:
                intent.putExtra("id",5);
                selecionado = true;
                break;
        }
        if(selecionado)
        {
            startActivity(intent);
        }
        return true;
    }
}




