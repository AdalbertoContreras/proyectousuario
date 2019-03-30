package com.comfacesar.comfacesar.Adaptador;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.comfacesar.comfacesar.Activities.ChatAsesoria;
import com.comfacesar.comfacesar.R;
import com.example.extra.Calculo;
import com.example.gestion.Gestion_administrador;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Administrador;
import com.example.modelo.Chat_asesoria;
import com.example.modelo.Mensaje_chat_asesoria;
import com.example.modelo.Usuario;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Mensajes_chat_asesoria extends  RecyclerView.Adapter<Adapter_Mensajes_chat_asesoria.ViewHolderDatos> {
    private ArrayList<Mensaje_chat_asesoria> mensaje_chat_asesoriaArrayList;
    private Chat_asesoria chat_asesoria;
    private static boolean seguir;
    private static Activity activity;
    public Adapter_Mensajes_chat_asesoria(ArrayList<Mensaje_chat_asesoria> mensaje_chat_asesorias, Chat_asesoria chat_asesoria) {
        this.mensaje_chat_asesoriaArrayList = mensaje_chat_asesorias;
        this.chat_asesoria = chat_asesoria;
        ChatAsesoria.cambioEstado = new ChatAsesoria.CambioEstado() {
            @Override
            public void cambio(boolean estado, Activity activity) {
                Adapter_Mensajes_chat_asesoria.this.seguir = estado;
                Adapter_Mensajes_chat_asesoria.this.activity = activity;
            }
        };
        seguir = true;
    }

    @Override
    public int getItemViewType(int position) {

        return mensaje_chat_asesoriaArrayList.get(position).tipo_creador_mensaje_chat_asesoria;
    }

    @NonNull
    @Override
    public Adapter_Mensajes_chat_asesoria.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i == 1)
        {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_mensaje_chat_assoria, null, false);
        }
        else
        {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_mensaje_chat_asesoria_recibir, null, false);
        }
        return new Adapter_Mensajes_chat_asesoria.ViewHolderDatos(view);
    }

    private View view;
    @Override
    public void onBindViewHolder(@NonNull final ViewHolderDatos viewHolderDatos, int i) {

        viewHolderDatos.setDatos(mensaje_chat_asesoriaArrayList.get(i), chat_asesoria);
        Thread thread = viewHolderDatos.iniciarCambioFecha(mensaje_chat_asesoriaArrayList.get(i), activity);
        thread.start();
    }

    @Override
    public int getItemCount() {
        return mensaje_chat_asesoriaArrayList.size();
    }


    public static class ViewHolderDatos extends RecyclerView.ViewHolder {
        public TextView contenidoTextView;
        private TextView fechatextView;
        private View view;
        private CircleImageView fotoPerfilCircleImageView;
        private String fecha2;
        private Calendar inicioCalendar;
        private Calendar finCalendar;
        private final int SUMA_SEGUNDO = 500;
        private final int SUMA_MINUTO = 5000;
        private final int SUMA_HORA= 60000;
        private final int SUMA_DIA= 60000;
        private final int CICLO_SEGUNDO = 1000;
        private final int CICLO_MINUTO = 60000;
        private final int CICLO_HORA = 3600000;
        private final int CICLO_DIA =  86400000;
        private boolean porSegundo = false;
        private boolean porMinuto = false;
        private boolean porHora = false;
        private boolean porDia = false;
        public ViewHolderDatos(@NonNull final View itemView) {
            super(itemView);
            contenidoTextView = itemView.findViewById(R.id.contenidotextView_itemMensaje);
            fechatextView = itemView.findViewById(R.id.fechatextViewItem_mensaje);
            fotoPerfilCircleImageView = itemView.findViewById(R.id.fotoPerfilImageView);
            this.view = itemView;
        }

        public void setDatos(final Mensaje_chat_asesoria mensaje_chat_asesoria, Chat_asesoria chat_asesoria)
        {
            contenidoTextView.setText(mensaje_chat_asesoria.contenido_mensaje_chat_asesoria);
            if(mensaje_chat_asesoria.tipo_creador_mensaje_chat_asesoria == 1)
            {
                ArrayList<Usuario> usuarios = new Gestion_usuario().generar_json(chat_asesoria.usuario);
                if(!usuarios.isEmpty())
                {
                    Usuario usuario = usuarios.get(0);
                    Picasso.with(view.getContext())
                            .load(usuario.foto_perfil_usuario)
                            .placeholder(R.drawable.ic_iconousuario)
                            .error(R.drawable.ic_iconousuario)
                            .into(fotoPerfilCircleImageView);
                }
                else
                {
                    Picasso.with(view.getContext()).load(R.drawable.ic_iconousuario).into(fotoPerfilCircleImageView);
                }
            }
            else
            {
                ArrayList<Administrador> administradores = new Gestion_administrador().generar_json(chat_asesoria.administrador);
                if(!administradores.isEmpty())
                {
                    Administrador administrador = administradores.get(0);
                    Picasso.with(view.getContext())
                            .load(administrador.url_foto_perfil_administrador)
                            .placeholder(R.drawable.ic_iconousuario)
                            .error(R.drawable.ic_iconousuario)
                            .into(fotoPerfilCircleImageView);
                }
                else
                {
                    Picasso.with(view.getContext())
                            .load(R.drawable.ic_iconousuario)
                            .into(fotoPerfilCircleImageView);
                }
            }
        }
        private int cont = 0;
        private int ciclo = 0;
        public Thread iniciarCambioFecha(Mensaje_chat_asesoria mensaje_chat_asesoria, final Activity activity)
        {
            finCalendar = new Calculo().String_a_Date(mensaje_chat_asesoria.fecha_envio_mensaje_chat_asesoria, mensaje_chat_asesoria.hora_envio_mensaje_asesoria);
            final String fecha1 = finCalendar.get (Calendar.YEAR)  + "-" + finCalendar.get (Calendar.MONTH)  + "-" + finCalendar.get (Calendar.DAY_OF_MONTH)  + "-" + finCalendar.get (Calendar.HOUR) + "-"+ finCalendar.get (Calendar.MINUTE)  + "-" + finCalendar.get (Calendar.SECOND)+ "- "  + finCalendar.get (Calendar.AM_PM);
            darFecha(activity);
            return new Thread(new Runnable() {
                @Override
                public void run()
                {
                    while(Adapter_Mensajes_chat_asesoria.seguir && porSegundo)
                    {
                        try {
                            Thread.sleep(SUMA_SEGUNDO);
                            cont += SUMA_SEGUNDO;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(Adapter_Mensajes_chat_asesoria.activity != null && cont >= CICLO_SEGUNDO)
                        {
                            darFecha(activity);
                            cont = 0;
                        }
                    }
                    darFecha(activity);
                    while(Adapter_Mensajes_chat_asesoria.seguir && porMinuto)
                    {
                        try {
                            Thread.sleep(SUMA_MINUTO);
                            cont += SUMA_MINUTO;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(Adapter_Mensajes_chat_asesoria.activity != null && cont >= CICLO_MINUTO)
                        {
                            darFecha(activity);
                            cont = 0;
                        }
                    }
                    darFecha(activity);
                    while(Adapter_Mensajes_chat_asesoria.seguir && porHora)
                    {
                        try {
                            Thread.sleep(SUMA_HORA);
                            cont += SUMA_HORA;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(Adapter_Mensajes_chat_asesoria.activity != null && cont >= CICLO_HORA)
                        {
                            darFecha(activity);
                            cont = 0;
                        }
                    }
                    darFecha(activity);
                    while(Adapter_Mensajes_chat_asesoria.seguir && porDia)
                    {
                        try {
                            Thread.sleep(SUMA_DIA);
                            cont += SUMA_DIA;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(Adapter_Mensajes_chat_asesoria.activity != null && cont >= CICLO_DIA)
                        {
                            darFecha(activity);
                            cont = 0;
                        }
                    }
                }
            });
        }

        private void darFecha(Activity activity)
        {
            activity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    Calendar c = Calendar.getInstance();
                    calendarActual(c);
                    Calculo calculo = new Calculo();
                    if(calculo.por(inicioCalendar, finCalendar, c) == calculo.SEGUNDO)
                    {
                        porSegundo = true;
                        porMinuto = false;
                        porHora = false;
                        porDia = false;
                    }
                    if(calculo.por(inicioCalendar, finCalendar, c) == calculo.MINUTO)
                    {
                        porSegundo = false;
                        porMinuto = true;
                        porHora = false;
                        porDia = false;
                    }
                    if(calculo.por(inicioCalendar, finCalendar, c) == calculo.HORA)
                    {
                        porSegundo = false;
                        porMinuto = false;
                        porHora = true;
                        porDia = false;
                    }
                    if(calculo.por(inicioCalendar, finCalendar, c) == calculo.DIA)
                    {
                        porSegundo = false;
                        porMinuto = false;
                        porHora = false;
                        porDia = true;
                    }
                    fechatextView.setText( new Calculo().fechaFormatoHace(inicioCalendar, finCalendar, c));
                    ciclo ++;
                }
            });
        }

        private void calendarActual(Calendar c)
        {
            inicioCalendar = new GregorianCalendar();
            inicioCalendar.set(c.get (Calendar.YEAR), c.get (Calendar.MONTH) + 1, c.get (Calendar.DAY_OF_MONTH), c.get (Calendar.HOUR_OF_DAY), c.get (Calendar.MINUTE), c.get (Calendar.SECOND));
        }
    }
}