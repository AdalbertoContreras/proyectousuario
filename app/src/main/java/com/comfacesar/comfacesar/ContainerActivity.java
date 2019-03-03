package com.comfacesar.comfacesar;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;


import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.comfacesar.comfacesar.adapterViewpager.MyPagerAdapter;
import com.comfacesar.comfacesar.fragment.HomeFragment;
import com.example.extra.Config;
import com.example.gestion.Gestion_usuario;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class ContainerActivity extends AppCompatActivity{
    private Menu menu;
    private FloatingActionButton chat_asesoriaFloatingActionButton;
    private FloatingActionButton iniciar_sesionFloatingActionButton;
    private FloatingActionButton historial_alertasFloatingActionButton;
    private FloatingActionButton cerrar_sesionFloatingActionButton;
    private FloatingActionButton registrarmeFloatingActionButton;
    private FloatingActionMenu floatingActionMenu;
    private boolean usuario_dio_click;
    public static String texto_buscar = "";

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
                usuario_dio_click = true;
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
                usuario_dio_click = true;
                startActivity(intent);
            }
        });
        iniciar_sesionFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                Intent intent = new Intent(ContainerActivity.this, ContainertwoActivity.class);
                intent.putExtra("id",3);
                usuario_dio_click = true;
                startActivity(intent);
            }
        });
        cerrar_sesionFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //floatingActionMenu.close(true);
                Gestion_usuario.setUsuario_online(null);
                usuario_dio_click = true;
                cambiar_estado_action_menu();
            }
        });
        registrarmeFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //floatingActionMenu.close(true);
                Intent intent = new Intent(ContainerActivity.this, ContainertwoActivity.class);
                intent.putExtra("id",4);
                usuario_dio_click = true;
                startActivity(intent);
            }
        });
        if(menu != null)
        {
            onCreateOptionsMenu(menu);
        }
        Toast.makeText(getBaseContext(), "onCreateView", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getBaseContext(), "Resumen", Toast.LENGTH_SHORT).show();
        if(menu != null)
        {
            onCreateOptionsMenu(menu);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        usuario_dio_click = false;
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
        }
        floatingActionMenu.open(true);
        new Thread(new Runnable() {
            public void run() {
            int cont_m = 0;
            boolean salir = false;
            while(!usuario_dio_click && !salir)
            {
                try {
                    Thread.sleep(100);
                    cont_m += 100;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(cont_m >= 5000)
                {
                    salir = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cerrar_floatingbutton();
                            usuario_dio_click = false;

                        }
                    });
                }
                if(usuario_dio_click)
                {
                    salir = true;
                }
            }
            usuario_dio_click = false;
            }
        }).start();
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
            if(removido)
            {
                SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
                spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.Gris)), 0,     spanString.length(), 0); //fix the color to white
                item.setTitle(spanString);
                i--;
            }
        }
        MenuItem searchItem = menu.findItem(R.id.action_buscar);
        final EditText searchView = (EditText) searchItem.getActionView();
        searchView.setSingleLine(true);
        searchView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER)
                {
                    texto_buscar = searchView.getText().toString();
                    //searchView.setText("");
                }
                return false;
            }
        });
        /*
        searchView.setQueryHint("Search People");
        //permite modificar el hint que el EditText muestra por defecto
        searchView.setQueryHint("Buscar articulos");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //se oculta el EditText
                Toast.makeText(getBaseContext(),query, Toast.LENGTH_SHORT).show();
                texto_buscar = query;
                if(!HomeFragment.fragmentConsultarNoticiasActivo)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container2,new HomeFragment()).commit();
                }
                searchView.setIconified(true);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals(""))
                {
                    //texto_buscar = "";
                }
                return true;
            }
        });*/
        this.menu = menu;
        return true;
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




