package com.comfacesar.comfacesar;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
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
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.Activities.ChatAsesoria;
import com.comfacesar.comfacesar.Activities.HistorialAlertaVacioActivity;
import com.comfacesar.comfacesar.Activities.HistorialChatVacioActivity;
import com.comfacesar.comfacesar.adapterViewpager.MyPagerAdapter;
import com.comfacesar.comfacesar.fragment.AsesoriaFragment;
import com.comfacesar.comfacesar.fragment.ChatActivosFragment;
import com.comfacesar.comfacesar.fragment.HistorialAsesoriasFragment;
import com.example.extra.Calculo;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Escuchadores.EscuchadorUsuario;
import com.example.gestion.Gestion_administrador;
import com.example.gestion.Gestion_alerta_temprana;
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
    private ArrayList<Chat_asesoria> chat_asesorias_remoto;
    public static int id = 0;
    private MyPagerAdapter myPagerAdapter;
    private ViewPager viewPager;
    private boolean mostrar_mensaje_conexion = false;
    private Gestion_chat_asesoria.CambiarEstadoChat cambiarEstadoChat;
    //este id contara las veces que se le ha advertido al usuario cuando se halla perdio la conexion
    private int contador_perdida_conexion = 0;

    public static ActualizarLista actualizarLista;
    public interface ActualizarLista
    {
        void actualizar();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mostrar_mensaje_conexion = true;
        setContentView(R.layout.activity_container2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pageSelecionado != 0)
                {
                    if(searchView != null)
                    {
                       // Toast.makeText(getBaseContext(), "Toolbar clickeado", Toast.LENGTH_SHORT).show();
                        searchView.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        toolbar.setTitle("ServiAmigo");
        toolbar.setBackgroundResource(R.color.colorPrimaryDark);
        setSupportActionBar(toolbar);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), getBaseContext());
        viewPager = (ViewPager) findViewById(R.id.pager);
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
            Gestion_usuario.escuchadorParaActivityPrincipal = new EscuchadorUsuario() {
                @Override
                public void usuarioCambiado(Usuario usuario) {
                    contador_perdida_conexion = 0;
                    if(usuario != null)
                    {
                        hilo_notificaciones_activo = true;
                        iniciar_hilo_notificaciones();
                    }
                    else
                    {
                        hilo_notificaciones_activo = false;
                        if(Gestion_chat_asesoria.getChat_asesorias() != null)
                        {
                            notificationManagerCompat = NotificationManagerCompat.from(ContainerActivity.this);
                            for(Chat_asesoria item : Gestion_chat_asesoria.getChat_asesorias())
                            {

                                notificationManagerCompat.cancel(item.id_chat_asesoria);
                            }
                            Gestion_chat_asesoria.limpiarChatAsesoria();
                        }
                    }
                }
            };
        }
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);

        if(menu != null)
        {
            onCreateOptionsMenu(menu);
        }
        fragmentManager = getSupportFragmentManager();
        if(id > 0)
        {
            if(id == 3)
            {
                viewPager.setCurrentItem(2);
            }
            id = 0;
        }
        Gestion_chat_asesoria.setChatAbierto(new Gestion_chat_asesoria.ChatAbierto() {
            @Override
            public void abierto(int id_chat) {
                /***
                 * Cierra la notificacion segun el chat abierto por el usuario
                 */
                notificationManagerCompat = NotificationManagerCompat.from(ContainerActivity.this);
                notificationManagerCompat.cancel(id_chat);
            }
        });
        iniciar_hilo_aviso_conexion();
        if(actualizarLista != null)
        {
            actualizarLista.actualizar();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mostrar_mensaje_conexion = false;
    }

    private void iniciar_hilo_notificaciones()
    {
        Gestion_chat_asesoria.setChat_asesorias(null, HistorialAsesoriasFragment.estoyAbierto);
        actualizar_notificaciones_chat();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int time = 1000;
                while (hilo_notificaciones_activo)
                {
                    if(time >= 1000)
                    {

                        time = 0;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                comprobar_conexion();
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

    private void iniciar_hilo_aviso_conexion()
    {
        Gestion_chat_asesoria.setChat_asesorias(null, HistorialAsesoriasFragment.estoyAbierto);
        actualizar_notificaciones_chat();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int time = 3000;
                while (true)
                {
                    if(time >= 5000)
                    {

                        time = 0;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(mostrar_mensaje_conexion)
                                {
                                    comprobar_conexion();
                                }
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

    private void comprobar_conexion()
    {
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(contador_perdida_conexion < 15 && (contador_perdida_conexion%3) == 0)
                {
                    Toast.makeText(getBaseContext(),"Se ha perdido la conexion con el servidor", Toast.LENGTH_LONG).show();
                }
                contador_perdida_conexion ++;
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),new HashMap<String, String>(),stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }

    private void actualizar_notificaciones_chat()
    {
        if(Gestion_usuario.getUsuario_online() != null)
        {
            HashMap<String,String> params = new Gestion_chat_asesoria().consultar_por_usuario(Gestion_usuario.getUsuario_online().id_usuario);
            Response.Listener<String> stringListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    chat_asesorias_remoto = new Gestion_chat_asesoria().generar_json(response);
                    if(!chat_asesorias_remoto.isEmpty())
                    {
                        if(Gestion_chat_asesoria.getChat_asesorias() == null)
                        {
                            //Gestion_chat_asesoria.setChat_asesorias(chat_asesorias_remoto);
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
                                //Gestion_chat_asesoria.addChat_asesoria(item);
                                if(!administradors.isEmpty() && !especialidads.isEmpty())
                                {
                                    if(item.usuario_respondio_chat_asesoria == 0)
                                    {
                                        aux(item, titulo);
                                    }
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
                                    Gestion_chat_asesoria.addChat_asesoria(item);
                                    if(!administradors.isEmpty() && !especialidads.isEmpty())
                                    {
                                        if(item.usuario_respondio_chat_asesoria == 0)
                                        {
                                            aux(item, titulo);
                                        }
                                    }
                                }
                                else
                                {
                                    String fecha1 = item.ultima_fecha_administrador_chat_asesoria + item.ultima_hora_administrador_chat_asesoria;
                                    String fecha2 = chat_asesoria.ultima_fecha_administrador_chat_asesoria + chat_asesoria.ultima_hora_administrador_chat_asesoria;
                                    if(!fecha1.equals(fecha2) && item.usuario_respondio_chat_asesoria == 0)
                                    {
                                        aux(item, titulo);
                                    }
                                    else
                                    {

                                    }
                                }
                            }
                        }
                        Gestion_chat_asesoria.setChat_asesorias(chat_asesorias_remoto, HistorialAsesoriasFragment.estoyAbierto);
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
            agregar_notificacion(item, titulo);
            /*if(!item.ultima_fecha_vista_usuario_chat_asesoria.equals("-1"))
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
                Calendar calendar = new Calculo().String_a_Date( date1, time1);
                Calendar calendar2 = new Calculo().String_a_Date(date2, time2);
                if(!(date1 + time1).equals(date2+time2))
                {
                    if(Calculo.compararCalendar(calendar,calendar2) == 1)
                    {
                        agregar_notificacion(item, titulo);
                    }
                }
            }*/
        }
    }

    private void agregar_notificacion(Chat_asesoria item, String titulo)
    {
        createNotificationChanel();
        createNotification(item.ultimo_mensaje_administrador_chat_asesoria, titulo, item.id_chat_asesoria);
    }

    public static Chat_asesoria chat_asesoria_por_id(int id)
    {
        if(Gestion_chat_asesoria.getChat_asesorias() != null)
        {
            for(Chat_asesoria item : Gestion_chat_asesoria.getChat_asesorias())
            {
                if(item.id_chat_asesoria == id)
                {
                    return item;
                }
            }
        }
        return null;
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
        resultIntent.putExtra("notificacion", 1);
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
        if(hilo_notificaciones_activo)
        {
            builder.setContentIntent(resultPendingIntent);
            notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(id, builder.build());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Gestion_chat_asesoria.setCambiarEstadoChat( new Gestion_chat_asesoria.CambiarEstadoChat() {
            @Override
            public void chatCambiarEstado(Chat_asesoria chat_asesoria) {
                HashMap<String, String> hashMap = new Gestion_chat_asesoria().registrar_vista(chat_asesoria.id_chat_asesoria);
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
                StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
                MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
            }

            @Override
            public void barridoCambioEstadoTerminado(boolean huboCambio) {
                /*
                si hubo cambio actualizo la lista de chat en la vista de historial de chat
                 */
                if(huboCambio)
                {
                    //actualizo la lista
                    int numChat = Gestion_chat_asesoria.listaChatNoVisto.getNumChatNoVisto();
                    floatingActionButton.setImageDrawable(ContextCompat.getDrawable(ContainerActivity.this, R.drawable.logo_chat));
                    switch (numChat)
                    {
                        case 0:
                            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                            break;
                        case 1:
                            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.verde)));
                            break;
                        case 2:
                            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.verde)));
                            break;
                    }
                    if(actualizarLista != null && HistorialAsesoriasFragment.estoyAbierto)
                    {
                        actualizarLista.actualizar();
                    }
                }
            }
        });
        if(Gestion_usuario.getUsuario_online() == null)
        {
            recuperarSesion();
        }
        else
        {
            aux();
        }
    }

    private void aux()
    {
        mostrar_mensaje_conexion = true;
        contador_perdida_conexion = 0;
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
                public void onClick(View v)
                {
                    tengoChat();
                }
            });
        }
        if(escuchadorCambioFiltro != null)
        {
            escuchadorCambioFiltro.filtroCambiado("", searchView);
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
        this.menu = menu;
        cambiarMenu();
        return true;
    }

    private void cambiarMenu()
    {
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
            final MenuItem searchItem = menu.findItem(R.id.action_buscar);
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
            ImageView icon = (ImageView)searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    escuchadorCambioFiltro.filtroCambiado(s, searchItem.getActionView());
                    return false;
                }
            });
        }
        else
        {
            //escuchadorCambioFiltro.filtroCambiado("");
        }
    }

    public interface escuchador{
        void filtroCambiado(String textoNuevo, View view);
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
                tengoAlertaTempranas();
                break;
            case  R.id.historialAsesoriasMenu:
                tengoChat();
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
                cerrarSesion();
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

    private void cerrarSesion()
    {
        HashMap<String,String> params = new Gestion_usuario().cerrarSesion();
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try
                {
                    int val = Integer.parseInt(response);
                    if(val > 0)
                    {
                        Gestion_usuario.setUsuario_online(null);
                        SharedPreferences.Editor myEditor = prefs.edit();
                        myEditor.putString("TOKEN", "-1");
                        myEditor.commit();
                        recreate();
                        Toast.makeText(getBaseContext(), "Sesion finalizada", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                    }
                }
                catch(NumberFormatException exc)
                {

                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }

    private void tengoAlertaTempranas()
    {
        HashMap<String,String> params = new Gestion_alerta_temprana().consultar_num_alertas_por_usuario(Gestion_usuario.getUsuario_online().id_usuario);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Intent intent;
                try
                {
                    int val = Integer.parseInt(response);
                    if(val > 0)
                    {
                        intent = new Intent(ContainerActivity.this, ContainertwoActivity.class);
                        intent.putExtra("id",1);
                    }
                    else
                    {
                        intent = new Intent(ContainerActivity.this, HistorialAlertaVacioActivity.class);
                        HistorialAlertaVacioActivity.enviarAlerta = new HistorialAlertaVacioActivity.EnviarAlerta() {
                            @Override
                            public void enviarAlerta() {
                                viewPager.setCurrentItem(2,true);
                            }
                        };
                    }
                }
                catch(NumberFormatException exc)
                {
                    intent = new Intent(ContainerActivity.this, HistorialAlertaVacioActivity.class);
                    HistorialAlertaVacioActivity.enviarAlerta = new HistorialAlertaVacioActivity.EnviarAlerta() {
                        @Override
                        public void enviarAlerta() {
                            viewPager.setCurrentItem(2,true);
                        }
                    };
                }
                startActivity(intent);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent;
                intent = new Intent(ContainerActivity.this, HistorialAlertaVacioActivity.class);
                HistorialAlertaVacioActivity.enviarAlerta = new HistorialAlertaVacioActivity.EnviarAlerta() {
                    @Override
                    public void enviarAlerta() {
                        viewPager.setCurrentItem(2,true);
                    }
                };
                startActivity(intent);
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }

    private void tengoChat()
    {
        HashMap<String,String> params = new Gestion_chat_asesoria().numero_por_usuario(Gestion_usuario.getUsuario_online().id_usuario);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Intent intent;
                try
                {
                    int val = Integer.parseInt(response);
                    if(val > 0)
                    {
                        intent = new Intent(ContainerActivity.this, ContainertwoActivity.class);
                        intent.putExtra("id",11);
                    }
                    else
                    {
                        intent = new Intent(ContainerActivity.this, HistorialChatVacioActivity.class);
                        HistorialChatVacioActivity.enviarAsesoria = new HistorialChatVacioActivity.EnviarAsesoria() {
                            @Override
                            public void enviarAsesoria() {
                                viewPager.setCurrentItem(1,true);
                            }
                        };
                    }
                }
                catch(NumberFormatException exc)
                {
                    intent = new Intent(ContainerActivity.this, HistorialChatVacioActivity.class);
                    HistorialChatVacioActivity.enviarAsesoria = new HistorialChatVacioActivity.EnviarAsesoria() {
                        @Override
                        public void enviarAsesoria() {
                            viewPager.setCurrentItem(1,true);
                        }
                    };
                }
                startActivity(intent);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent = new Intent(ContainerActivity.this, HistorialChatVacioActivity.class);
                HistorialChatVacioActivity.enviarAsesoria = new HistorialChatVacioActivity.EnviarAsesoria() {
                    @Override
                    public void enviarAsesoria() {
                        viewPager.setCurrentItem(1,true);
                    }
                };
                startActivity(intent);
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onDestroy() {
        if(Gestion_chat_asesoria.getChat_asesorias() != null)
        {
            for(Chat_asesoria item : Gestion_chat_asesoria.getChat_asesorias())
            {
                notificationManagerCompat = NotificationManagerCompat.from(this);
                notificationManagerCompat.cancel(item.id_chat_asesoria);
            }
        }
        super.onDestroy();
    }

    private SharedPreferences prefs;
    private void recuperarSesion()
    {
        prefs = getSharedPreferences("SESION_USER", Context.MODE_PRIVATE);
        String token = prefs.getString("TOKEN", "-1");
        if(!token.equals("-1") )
        {
            Usuario usuario = new Usuario();
            usuario.token = token;
            validarTokenObtenerUsuario(usuario);
        }
        else
        {
            aux();
        }
    }

    private void validarTokenObtenerUsuario(Usuario usuario)
    {
        HashMap<String, String> params = new Gestion_usuario().validarTokenObtenerusuario(usuario);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try
                {
                    Integer.parseInt(response);
                    aux();
                }
                catch(NumberFormatException exc)
                {
                    ArrayList<Usuario> usuarios = new Gestion_usuario().generar_json(response);
                    if(usuarios.isEmpty())
                    {
                        aux();
                    }
                    else {
                        Gestion_usuario.setUsuario_online(usuarios.get(0));
                        cambiarMenu();
                    }
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                aux();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }
}




