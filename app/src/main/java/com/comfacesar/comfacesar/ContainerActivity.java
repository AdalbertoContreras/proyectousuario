package com.comfacesar.comfacesar;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.Activities.ChatAsesoria;
import com.comfacesar.comfacesar.adapterViewpager.MyPagerAdapter;
import com.comfacesar.comfacesar.fragment.AsesoriaFragment;
import com.comfacesar.comfacesar.fragment.ChatActivosFragment;
import com.example.extra.Calculo;
import com.example.extra.Config;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Escuchadores.EscuchadorUsuario;
import com.example.gestion.Gestion_administrador;
import com.example.gestion.Gestion_chat_asesoria;
import com.example.gestion.Gestion_especialidad;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Administrador;
import com.example.modelo.Chat_asesoria;
import com.example.modelo.Especialidad;
import com.example.modelo.Usuario;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ContainerActivity extends AppCompatActivity implements AsesoriaFragment.OnFragmentInteractionListener, ChatActivosFragment.OnFragmentInteractionListener{
    private Menu menu;
    public static String texto_buscar = "";
    public static FragmentManager fragmentManager;
    private FloatingActionButton floatingActionButton;
    private android.support.v7.widget.SearchView searchView;
    private int pageSelecionado = 0;
    private static boolean hilo_notificaciones_activo = false;
    private final String CHANEL_ID = "NOTIFICACION";
    private NotificationManagerCompat notificationManagerCompat;
    private static ArrayList<Chat_asesoria> chat_asesorias_local;
    private ArrayList<Chat_asesoria> chat_asesorias_remoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_container2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pageSelecionado != 0)
                {
                    if(searchView != null)
                    {
                        Toast.makeText(getBaseContext(), "Toolbar clickeado", Toast.LENGTH_SHORT).show();
                        searchView.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        toolbar.setTitle("ServiAmigo");
        toolbar.setBackgroundResource(R.color.Gris3);
        setSupportActionBar(toolbar);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), getBaseContext());
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                pageSelecionado = i;
                if(menu != null)
                {
                    onCreateOptionsMenu(menu);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        TabLayout tabLayout = findViewById(R.id.tabs);
        floatingActionButton = findViewById(R.id.misChatsFloatingActionButton);
        if(Gestion_usuario.escuchadorParaActivityPrincipal == null)
        {
            notificationManagerCompat = NotificationManagerCompat.from(this);
            Gestion_usuario.escuchadorParaActivityPrincipal = new EscuchadorUsuario() {
                @Override
                public void usuarioCambiado(Usuario usuario) {
                    if(usuario != null)
                    {
                        hilo_notificaciones_activo = true;
                        iniciar_hilo_notificaciones();
                    }
                    else
                    {
                        hilo_notificaciones_activo = false;
                        if(chat_asesorias_local != null)
                        {
                            for(Chat_asesoria item : chat_asesorias_local)
                            {
                                notificationManagerCompat.cancel(item.id_chat_asesoria);
                            }
                            chat_asesorias_local.clear();
                            chat_asesorias_local = null;
                        }
                    }
                }
            };
        }
        tabLayout.setupWithViewPager(viewPager);
        if(menu != null)
        {
            onCreateOptionsMenu(menu);
        }
        fragmentManager = getSupportFragmentManager();
    }

    private void iniciar_hilo_notificaciones()
    {
        chat_asesorias_local = null;
        actualizar_notificaciones_chat();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int time = 3000;
                while (hilo_notificaciones_activo)
                {
                    if(time >= 3000)
                    {
                        time = 0;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                actualizar_notificaciones_chat();
                            }
                        });
                    }
                    try {
                        Thread.sleep(100);
                        time += 100;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void actualizar_notificaciones_chat()
    {
        if(Gestion_usuario.getUsuario_online() != null)
        {
            HashMap<String,String> params = new Gestion_chat_asesoria().consultar_por_usuario(Gestion_usuario.getUsuario_online().id_usuario);
            Log.d("parametros", params.toString());
            Response.Listener<String> stringListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    chat_asesorias_remoto = new Gestion_chat_asesoria().generar_json(response);
                    if(chat_asesorias_remoto != null)
                    {
                        if(chat_asesorias_local == null)
                        {
                            chat_asesorias_local = new ArrayList<>();
                            chat_asesorias_local.addAll(chat_asesorias_remoto);
                            for(Chat_asesoria item :  chat_asesorias_remoto)
                            {
                                ArrayList<Administrador> administradors = new Gestion_administrador().generar_json(item.administrador);
                                ArrayList<Especialidad> especialidads = new Gestion_especialidad().generar_json(item.especialidad);
                                String titulo = "";
                                if(!administradors.isEmpty() && !especialidads.isEmpty())
                                {
                                    Administrador administrador = administradors.get(0);
                                    Especialidad especialidad = especialidads.get(0);
                                    titulo = administrador.nombre_cuenta_administrador + " <<" + especialidad.nombre_especialidad + ">>";
                                }
                                chat_asesorias_local.add(item);
                                if(!administradors.isEmpty() && !especialidads.isEmpty())
                                {
                                    aux(item, titulo);
                                }
                            }
                        }
                        else
                        {
                            for(Chat_asesoria item :  chat_asesorias_remoto)
                            {
                                Chat_asesoria chat_asesoria = chat_asesoria_por_id(item.id_chat_asesoria);
                                ArrayList<Administrador> administradors = new Gestion_administrador().generar_json(item.administrador);
                                ArrayList<Especialidad> especialidads = new Gestion_especialidad().generar_json(item.especialidad);
                                String titulo = "";
                                if(!administradors.isEmpty() && !especialidads.isEmpty())
                                {
                                    Administrador administrador = administradors.get(0);
                                    Especialidad especialidad = especialidads.get(0);
                                    titulo = administrador.nombre_cuenta_administrador + " <<" + especialidad.nombre_especialidad + ">>";
                                }
                                if(chat_asesoria == null)
                                {
                                    chat_asesorias_local.add(item);
                                    if(!administradors.isEmpty() && !especialidads.isEmpty())
                                    {
                                        aux(item, titulo);
                                    }
                                }
                                else
                                {
                                    if(!(item.ultima_fecha_administrador_chat_asesoria + item.ultima_hora_administrador_chat_asesoria).equals(chat_asesoria.ultima_fecha_administrador_chat_asesoria + chat_asesoria.ultima_hora_administrador_chat_asesoria))
                                    {
                                        aux(item, titulo);
                                    }
                                }
                            }
                        }
                    }
                }
            };
            StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, MySocialMediaSingleton.errorListener());
            MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
        }
    }

    private void aux(Chat_asesoria item, String titulo)
    {
        boolean valido = false;
        if(!item.ultima_fecha_administrador_chat_asesoria.equals("-1"))
        {
            if(!item.ultima_fecha_vista_usuario_chat_asesoria.equals("-1"))
            {
                valido = true;
            }
            if(!valido)
            {
                agregar_notificacion(item, titulo);
            }
            else
            {
                String date1 = item.ultima_fecha_administrador_chat_asesoria;
                String time1 = item.ultima_hora_administrador_chat_asesoria;
                String date2 = item.ultima_fecha_vista_usuario_chat_asesoria;
                String time2 = item.ultima_hora_vista_usuario_chat_asesoria;
                Calendar calendar = Calculo.String_a_Date( date1, time1);
                Calendar calendar2 = Calculo.String_a_Date(date2, time2);
                if(!(date1 + time1).equals(date2+time2))
                {
                    if(Calculo.compararCalendar(calendar,calendar2) == 1)
                    {
                        agregar_notificacion(item, titulo);
                    }
                }
            }
        }
    }

    private void agregar_notificacion(Chat_asesoria item, String titulo)
    {
        reemplazar_chat_local(item);
        createNotificationChanel();
        createNotification(item.ultimo_mensaje_administrador_chat_asesoria, titulo, item.id_chat_asesoria);
    }

    public static Chat_asesoria chat_asesoria_por_id(int id)
    {
        for(Chat_asesoria item : chat_asesorias_local)
        {
            if(item.id_chat_asesoria == id)
            {
                return item;
            }
        }
        return null;
    }

    public void reemplazar_chat_local(Chat_asesoria chat_asesoria)
    {
        int tam = chat_asesorias_local.size();
        for(int i = 0; i < tam; i ++)
        {
            if(chat_asesorias_local.get(i).id_chat_asesoria == chat_asesoria.id_chat_asesoria)
            {
                chat_asesorias_local.add(i, chat_asesoria);
            }
        }
    }

    private void createNotificationChanel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotification(String mensaje, String titulo, int id)
    {
        Intent resultIntent = new Intent(this, ChatAsesoria.class);
        resultIntent.putExtra("chat", id);

// Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle(titulo);
        builder.setContentText(mensaje);
        builder.setColor(Color.RED);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.RED, 1000, 1000);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setAutoCancel(true);
        builder.setContentIntent(resultPendingIntent);

        notificationManagerCompat.notify(id, builder.build());
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Config().iniciar_config(this);
        if(menu != null)
        {
            onCreateOptionsMenu(menu);
        }
        if(Gestion_usuario.getUsuario_online() == null)
        {
            floatingActionButton.hide();
        }
        else
        {
            floatingActionButton.show();
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ContainerActivity.this, ContainertwoActivity.class);
                    intent.putExtra("id",11);
                    startActivity(intent);
                }
            });
        }
        Gestion_chat_asesoria.chatAbierto = new Gestion_chat_asesoria.ChatAbierto() {
            @Override
            public void abierto(int id_chat) {
                notificationManagerCompat.cancel(id_chat);
            }
        };
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
        menu.removeItem(R.id.action_buscar);
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
            if(item.getItemId() == R.id.action_buscar && pageSelecionado != 0)
            {
                menu.removeItem(item.getItemId());

            }
            if(removido)
            {
                SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
                spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.Gris)), 0,     spanString.length(), 0); //fix the color to white
                item.setTitle(spanString);
                i--;
            }
        }
        if(pageSelecionado == 0)
        {
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
        }

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

    @Override
    protected void onDestroy() {
        if(chat_asesorias_local != null)
        {
            for(Chat_asesoria item : chat_asesorias_local)
            {
                notificationManagerCompat.cancel(item.id_chat_asesoria);
            }
        }
        super.onDestroy();
    }
}




