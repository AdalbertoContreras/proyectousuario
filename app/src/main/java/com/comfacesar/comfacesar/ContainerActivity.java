package com.comfacesar.comfacesar;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.ServiAmigo.Extra.Config;
import com.comfacesar.ServiAmigo.Extra.MySocialMediaSingleton;
import com.comfacesar.comfacesar.adapterViewpager.MyPagerAdapter;
import com.comfacesar.comfacesar.fragment.registrarUsuarioFragment;
import com.example.gestion.Gestion_movil;
import com.example.gestion.Gestion_movil_registro;
import com.example.modelo.Movil;
import com.roughike.bottombar.BottomBar;

import java.util.HashMap;

public class ContainerActivity extends AppCompatActivity implements InicioSesionFragment.OnFragmentInteractionListener, registrarUsuarioFragment.OnFragmentInteractionListener {

    public ViewPager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container2);
        new Config().iniciar_config(this);
        registrar_movil();
       /* BottomBar bottomBar=  findViewById(R.id.id_bottombar);
        bottomBar.setDefaultTab(R.id.id_Home);

       /* viewpager= findViewById(R.id.IdViewPagerInformacion);
        llenarviewpager(viewpager);

        viewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("ServiAmigo");

        toolbar.setBackgroundResource(R.color.Gris3);
        setSupportActionBar(toolbar);

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(myPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);
    }

    private void registrar_movil()
    {
        if(Config.getImei() != null)
        {
            Movil movil = new Movil();
            movil.imei = Config.getImei();
            movil.modelo_movil = "";
            HashMap<String, String> hashMap = new Gestion_movil().registrar(movil);
            Response.Listener<String> stringListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            };
            StringRequest stringRequest = com.comfacesar.ServiAmigo.Extra.MySocialMediaSingleton.volley_consulta(com.comfacesar.ServiAmigo.Extra.WebService.getUrl(),hashMap,stringListener, errorListener);
            MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
        }
    }

    @Override
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Fragment fragment = null;
        boolean selecionado = false;
        switch (item.getItemId())
        {
            case R.id.historialAlertasMenu:
                Intent intent = new Intent(ContainerActivity.this, ContainerActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);
                break;
            case  R.id.historialAsesoriasMenu:
                Toast.makeText(this, "buscar", Toast.LENGTH_SHORT).show();
                break;
            case  R.id.iniciarSesionMenu:
                fragment = new InicioSesionFragment();
                selecionado = true;
                break;
            case  R.id.registrarmeMenu:
                fragment = new registrarUsuarioFragment();
                selecionado = true;
                break;
        }
        if(selecionado)
        {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,fragment)
                .commit();
        }
        return true;
    }


    public boolean onNavigationItemSelected(MenuItem item) {

        return true;
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}




