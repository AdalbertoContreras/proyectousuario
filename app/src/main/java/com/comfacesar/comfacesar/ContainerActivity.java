package com.comfacesar.comfacesar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.comfacesar.comfacesar.adapterViewpager.MyPagerAdapter;
import com.comfacesar.comfacesar.fragment.AsesoriaFragment;
import com.comfacesar.comfacesar.fragment.ChatActivosFragment;
import com.example.extra.Config;
import com.example.gestion.Gestion_usuario;
import com.github.clans.fab.FloatingActionMenu;

public class ContainerActivity extends AppCompatActivity implements AsesoriaFragment.OnFragmentInteractionListener, ChatActivosFragment.OnFragmentInteractionListener{
    private Menu menu;
    public static String texto_buscar = "";
    public static FragmentManager fragmentManager;
    private FloatingActionButton floatingActionButton;
    private android.support.v7.widget.SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_container2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("ServiAmigo");
        toolbar.setBackgroundResource(R.color.Gris3);
        setSupportActionBar(toolbar);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), getBaseContext());
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        floatingActionButton = findViewById(R.id.misChatsFloatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContainerActivity.this, ContainertwoActivity.class);
                intent.putExtra("id",10);
                startActivity(intent);
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        if(menu != null)
        {
            onCreateOptionsMenu(menu);
        }
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Config().iniciar_config(this);
        if(menu != null)
        {
            onCreateOptionsMenu(menu);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //añadir color  a menu
        int item_agregado = 0;
        menu.removeItem(R.id.registrarmeMenu);
        menu.removeItem(R.id.iniciarSesionMenu);
        menu.removeItem(R.id.cerrarSesionMenu);
        menu.removeItem(R.id.modificarMisDatos);
        menu.removeItem(R.id.historialAlertasMenu);
        menu.removeItem(R.id.historialAsesoriasMenu);
        menu.removeItem(R.id.action_buscar);
        menu.removeItem(R.id.acercaDeMenu);
        menu.removeItem(R.id.modificarMiContraseña);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        int tamaño_menu = menu.size();
        for(int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            boolean removido = false;
            if(item.getItemId() == R.id.registrarmeMenu && Gestion_usuario.getUsuario_online() != null)
            {
                menu.removeItem(item.getItemId());
                removido = true;
            }
            if(item.getItemId() == R.id.iniciarSesionMenu && Gestion_usuario.getUsuario_online() != null)
            {
                menu.removeItem(item.getItemId());
                removido = true;
            }
            if(item.getItemId() == R.id.cerrarSesionMenu && Gestion_usuario.getUsuario_online() == null)
            {
                menu.removeItem(item.getItemId());
                removido = true;
            }
            if(item.getItemId() == R.id.modificarMisDatos && Gestion_usuario.getUsuario_online() == null)
            {
                menu.removeItem(item.getItemId());
                removido = true;
            }
            if(item.getItemId() == R.id.historialAlertasMenu && Gestion_usuario.getUsuario_online() == null)
            {
                menu.removeItem(item.getItemId());
                removido = true;
            }
            if(item.getItemId() == R.id.historialAsesoriasMenu && Gestion_usuario.getUsuario_online() == null)
            {
                menu.removeItem(item.getItemId());
                removido = true;
            }
            if(item.getItemId() == R.id.modificarMiContraseña && Gestion_usuario.getUsuario_online() == null)
            {
                menu.removeItem(item.getItemId());
                removido = true;
            }
            if(removido)
            {
                SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
                spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.Gris)), 0,     spanString.length(), 0); //fix the color to white
                item.setTitle(spanString);
                i--;
            }
        }


        MenuItem searchItem = menu.findItem(R.id.action_buscar);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    searchView.setIconified(true);
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                escuchadorCambioFiltro.filtroCambiado(s);
                return false;
            }
        });
        /*searchView = (EditText) searchItem.getActionView();
        searchView.setSingleLine(true);
        searchView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER)
                {
                    escuchadorCambioFiltro.filtroCambiado(searchView.getText().toString());
                }
                return false;
            }
        });
        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    searchView.setSelection(0, searchView.getText().toString().length());
                }
            }
        });*/

        this.menu = menu;
        return true;
    }

    public interface escuchador{
        void filtroCambiado(String textoNuevo);
    }

    public static escuchador escuchadorCambioFiltro;

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
            case  R.id.cerrarSesionMenu:
                Gestion_usuario.setUsuario_online(null);
                recreate();
                break;
            case  R.id.acercaDeMenu:
                intent.putExtra("id",5);
                selecionado = true;
                break;
            case  R.id.modificarMisDatos:
                intent.putExtra("id",8);
                selecionado = true;
                break;
            case  R.id.modificarMiContraseña:
                intent.putExtra("id",9);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}




