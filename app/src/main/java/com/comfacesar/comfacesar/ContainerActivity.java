package com.comfacesar.comfacesar;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;


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
import com.example.extra.Config;
import com.example.gestion.Gestion_usuario;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class ContainerActivity extends AppCompatActivity {
    private Menu menu;
    private FloatingActionButton chat_asesoriaFloatingActionButton;
    private FloatingActionButton iniciar_sesionFloatingActionButton;
    private FloatingActionButton historial_alertasFloatingActionButton;
    private FloatingActionButton cerrar_sesionFloatingActionButton;
    private FloatingActionButton registrarmeFloatingActionButton;
    private FloatingActionMenu floatingActionMenu;



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
        iniciar_sesionFloatingActionButton = findViewById(R.id.iniciar_sesionButton);
        historial_alertasFloatingActionButton = findViewById(R.id.historial_alertasButton);
        cerrar_sesionFloatingActionButton = findViewById(R.id.cerrar_sesionButton);
        registrarmeFloatingActionButton = findViewById(R.id.registrarmeButton);
        floatingActionMenu =  findViewById(R.id.menu_floatingActionMenu);
        floatingActionMenu.setClosedOnTouchOutside(true);
        historial_alertasFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContainerActivity.this, ContainertwoActivity.class);
                floatingActionMenu.close(true);
                intent.putExtra("id",1);
                startActivity(intent);
            }
        });

        chat_asesoriaFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ContainerActivity.this, ContainertwoActivity.class);
                floatingActionMenu.close(true);
                intent.putExtra("id",6);
                startActivity(intent);
            }
        });
        iniciar_sesionFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                Intent intent = new Intent(ContainerActivity.this, ContainertwoActivity.class);
                intent.putExtra("id",3);
                startActivity(intent);
            }
        });
        cerrar_sesionFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //floatingActionMenu.close(true);
                Gestion_usuario.setUsuario_online(null);
                cambiar_estado_action_menu();
            }
        });
        registrarmeFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //floatingActionMenu.close(true);
                floatingActionMenu.close(true);
                Intent intent = new Intent(ContainerActivity.this, ContainertwoActivity.class);
                intent.putExtra("id",4);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        cambiar_estado_action_menu();
    }

    private void cambiar_estado_action_menu()
    {
        if(Gestion_usuario.getUsuario_online() != null)
        {
            iniciar_sesionFloatingActionButton.setVisibility(View.GONE);
            registrarmeFloatingActionButton.setVisibility(View.GONE);
            historial_alertasFloatingActionButton.setVisibility(View.VISIBLE);
            chat_asesoriaFloatingActionButton.setVisibility(View.VISIBLE);
            cerrar_sesionFloatingActionButton.setVisibility(View.VISIBLE);
        }
        else
        {
            iniciar_sesionFloatingActionButton.setVisibility(View.VISIBLE);
            registrarmeFloatingActionButton.setVisibility(View.VISIBLE);
            historial_alertasFloatingActionButton.setVisibility(View.GONE);
            chat_asesoriaFloatingActionButton.setVisibility(View.GONE);
            cerrar_sesionFloatingActionButton.setVisibility(View.GONE);
            floatingActionMenu.open(true);
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cerrar_floatingbutton();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    public void cerrar_floatingbutton()
    {
        floatingActionMenu.close(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

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
        Intent intent = new Intent(ContainerActivity.this, ContainertwoActivity.class);
        boolean selecionado = false;
        //Fragment mifragment=null;
        switch (item.getItemId())
        {
            case R.id.historialAlertasMenu:
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

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }
}




