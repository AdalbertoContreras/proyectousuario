package com.comfacesar.comfacesar.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.Adaptador.Adapter_Mensajes_chat_asesoria;
import com.comfacesar.comfacesar.ContainerActivity;
import com.comfacesar.comfacesar.ContainertwoActivity;
import com.comfacesar.comfacesar.R;
import com.comfacesar.comfacesar.fragment.ChatActivosFragment;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_administrador;
import com.example.gestion.Gestion_chat_asesoria;
import com.example.gestion.Gestion_especialidad;
import com.example.gestion.Gestion_mensaje_chat_asesoria;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Administrador;
import com.example.modelo.Chat_asesoria;
import com.example.modelo.Especialidad;
import com.example.modelo.Mensaje_chat_asesoria;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatAsesoria extends AppCompatActivity {
    public static Administrador administrador;
    public static Chat_asesoria chat_asesoria;
    private RecyclerView recyclerView_chat_asesoria;
    private EditText mensajeEditText;
    private Button enviarButton;
    private ArrayList<Mensaje_chat_asesoria> mensaje_chat_asesorias;
    private Adapter_Mensajes_chat_asesoria adapter_mensajes_chat_asesoria;
    private String ultima_fecha = "";
    private String ultima_hora = "";
    private boolean consultando = false;
    private boolean mensaje_enviado;
    private boolean estoy_activo = false;
    private TextView nombreAdministradorTextView;
    private int id_chat = 0;
    private int esNotificacion;
    private String titulo2;
    public static CambioEstado cambioEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat_asesoria);
        mensaje_chat_asesorias = new ArrayList<>();
        recyclerView_chat_asesoria = findViewById(R.id.mensajes_chat_asesoria_recyclerview_chat_Assoria);
        recyclerView_chat_asesoria.setLayoutManager(new GridLayoutManager(getBaseContext(),1));
        nombreAdministradorTextView = findViewById(R.id.nombreAdministradortextView);

        Toolbar toolbar = findViewById(R.id.toolbar_chat);
        toolbar.setBackgroundResource(R.color.Gris3);

        id_chat = 0;
        esNotificacion = 0;
        try
        {
            id_chat = getIntent().getExtras().getInt("chat");
        }
        catch (NullPointerException exc)
        {

        }
        try
        {
            esNotificacion = getIntent().getExtras().getInt("notificacion");
        }
        catch (NullPointerException exc)
        {

        }
        if(id_chat > 0)
        {
            chat_asesoria = ContainerActivity.chat_asesoria_por_id(id_chat);
            ArrayList<Administrador> administradors = new Gestion_administrador().generar_json(chat_asesoria.administrador);
            if(!administradors.isEmpty())
            {
                administrador = administradors.get(0);
                nombreAdministradorTextView.setText(administrador.nombre_cuenta_administrador);
            }
            ArrayList<Especialidad> especialidads = new Gestion_especialidad().generar_json(chat_asesoria.especialidad);
            if(!especialidads.isEmpty())
            {
                ChatActivosFragment.tipoAsesoria = especialidads.get(0).id_especialidad;
            }
        }
        else
        {
            nombreAdministradorTextView.setText(administrador.nombre_cuenta_administrador);
            titulo2= nombreAdministradorTextView.getText().toString();
        }
        ShowToolbar(administrador.nombre_cuenta_administrador,true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mensajeEditText = findViewById(R.id.mensajeEdittextChatAsesoria);
        enviarButton = findViewById(R.id.enviarButton_chatAesoria);

        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mensajeEditText.getText().toString().isEmpty())
                {
                    if(chat_asesoria != null)
                    {
                        Mensaje_chat_asesoria mensaje_chat_asesoria = new Mensaje_chat_asesoria();
                        mensaje_chat_asesoria.chat_mensaje_chat_asesoria = chat_asesoria.id_chat_asesoria;
                        mensaje_chat_asesoria.id_creador_mensaje_chat_asesoria = Gestion_usuario.getUsuario_online().id_usuario;
                        mensaje_chat_asesoria.contenido_mensaje_chat_asesoria = mensajeEditText.getText().toString();
                        mensaje_chat_asesoria.tipo_creador_mensaje_chat_asesoria = 1;
                        HashMap<String,String> params = new Gestion_mensaje_chat_asesoria().registrar_mensaje_chat_asesoria(mensaje_chat_asesoria);
                        Response.Listener<String> stringListener = new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                mensaje_enviado = true;
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
                    mensajeEditText.setText("");
                }
            }
        });
        Gestion_chat_asesoria.chat_abiero(chat_asesoria.id_chat_asesoria);
    }

    public void ShowToolbar(String Tittle, boolean upButton)
    {
        Toolbar toolbar = findViewById(R.id.toolbar_chat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    private void chatVisto()
    {
        HashMap<String,String> params = new Gestion_chat_asesoria().vista_por_usuario(chat_asesoria.id_chat_asesoria);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                consultando = false;
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        estoy_activo = false;
        chatVisto();
        chat_asesoria = null;
        if(cambioEstado != null)
        {
            cambioEstado.cambio(false, this);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        estoy_activo = true;
        mensaje_enviado = false;
        iniciar_conexion_chat();
        if(cambioEstado != null)
        {
            cambioEstado.cambio(true, this);
        }
    }

    public void hilo_actualizacion_mensajes()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(estoy_activo)
                {
                    if(!consultando)
                    {
                        consultando = true;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                consultar_mensajes_nuevos();
                            }
                        });
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void iniciar_conexion_chat()
    {
        HashMap<String,String> params = new Gestion_chat_asesoria().consultar_chat_asesoria_por_usuario_administrador_y_especialidad(administrador.id_administrador, Gestion_usuario.getUsuario_online().id_usuario, ChatActivosFragment.tipoAsesoria);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                ArrayList<Chat_asesoria> chat_asesorias = new Gestion_chat_asesoria().generar_json(response);
                if(!chat_asesorias.isEmpty())
                {
                    chat_asesoria = chat_asesorias.get(0);
                    consultar_mensajes();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                consultando = false;
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }

    private void consultar_mensajes()
    {
        HashMap<String,String> params = new Gestion_mensaje_chat_asesoria().mensajes_asesoria_por_asesoria(chat_asesoria.id_chat_asesoria);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                cargar_mensajes_de_inicio(response);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                consultando = false;
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }

    private void cargar_mensajes_de_inicio(String response)
    {
        consultando = true;
        mensaje_chat_asesorias = new Gestion_mensaje_chat_asesoria().generar_json(response);
        //Collections.reverse(mensaje_chat_asesorias);
        adapter_mensajes_chat_asesoria = new Adapter_Mensajes_chat_asesoria(mensaje_chat_asesorias, chat_asesoria);
        cambioEstado.cambio(true, this);
        if(!mensaje_chat_asesorias.isEmpty())
        {
            recyclerView_chat_asesoria.smoothScrollToPosition(mensaje_chat_asesorias.size() - 1);
            ultima_fecha = mensaje_chat_asesorias.get(mensaje_chat_asesorias.size() - 1).fecha_envio_mensaje_chat_asesoria;
            ultima_hora = mensaje_chat_asesorias.get(mensaje_chat_asesorias.size() - 1).hora_envio_mensaje_asesoria;
        }
        recyclerView_chat_asesoria.setAdapter(adapter_mensajes_chat_asesoria);
        recyclerView_chat_asesoria.setHasFixedSize(true);
        consultando = false;
        hilo_actualizacion_mensajes();
    }

    private void consultar_mensajes_nuevos()
    {
        try
        {
            HashMap<String,String> params;
            if(!mensaje_chat_asesorias.isEmpty())
            {
                params = new Gestion_mensaje_chat_asesoria().mensaje_chat_asesoria_por_chat_mayor(ultima_fecha, ultima_hora, chat_asesoria.id_chat_asesoria);
            }
            else
            {
                params = new Gestion_mensaje_chat_asesoria().mensajes_asesoria_por_asesoria(chat_asesoria.id_chat_asesoria);
            }
            Response.Listener<String> stringListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    cargar_mensajes_nuevo(response);
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    consultando = false;
                }
            };
            StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
            MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
        }
        catch(NullPointerException exc)
        {

        }

    }

    private void cargar_mensajes_nuevo(String response)
    {
        ArrayList<Mensaje_chat_asesoria> mensaje_chat_asesorias_aux = new Gestion_mensaje_chat_asesoria().generar_json(response);
        if(!mensaje_chat_asesorias_aux.isEmpty())
        {
            ultima_fecha = mensaje_chat_asesorias_aux.get(mensaje_chat_asesorias_aux.size() - 1).fecha_envio_mensaje_chat_asesoria;
            ultima_hora = mensaje_chat_asesorias_aux.get(mensaje_chat_asesorias_aux.size() - 1).hora_envio_mensaje_asesoria;
            mensaje_chat_asesorias.addAll(mensaje_chat_asesorias_aux);
            adapter_mensajes_chat_asesoria.notifyItemInserted(mensaje_chat_asesorias.size() - 1 );
            if(mensaje_enviado)
            {
                recyclerView_chat_asesoria.smoothScrollToPosition(mensaje_chat_asesorias.size() - 1);
                mensaje_enviado = false;
            }
        }
        consultando = false;
    }

    @Override
    public void onBackPressed() {
        if(esNotificacion == 1)
        {
            Intent intent = new Intent(getBaseContext(), ContainerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else
        {
            finish();
        }
    }

    public interface CambioEstado
    {
        void cambio(boolean estado, Activity activity);
    }

}
