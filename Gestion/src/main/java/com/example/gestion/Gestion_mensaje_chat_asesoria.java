package com.example.gestion;

import com.example.modelo.Chat_asesoria;
import com.example.modelo.Lugar;
import com.example.modelo.Mensaje_chat_asesoria;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_mensaje_chat_asesoria {
    private static Mensaje_chat_asesoria aux = new Mensaje_chat_asesoria();
    private static String llave_ws = "mensaje_chat_asesoria";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;
    private static final String ID_MENSAJE_CHAT_ASESORIA = "A";
    private static final String FECHA_ENVIO_MENSAJE_CHAT_ASESORIA = "B";
    private static final String HORA_ENVIO_MENSAJE_ASESORIA = "C";
    private static final String CONTENIDO_MENSAJE_CHAT_ASESORIA = "D";
    private static final String CHAT_MENSAJE_CHAT_ASESORIA = "E";
    private static final String ID_CREADOR_MENSAJE_CHAT_ASESORIA = "F";
    private static final String TIPO_CREADOR_MENSAJE_CHAT_ASESORIA = "G";
    private static final String NOMBRE_USUARIO = "NU";
    private static final String CONTRASENA_USUARIO = "CU";
    private static final String TIPO_CONSULTA = "TC";

    private static void iniciar_axu()
    {
        aux = new Mensaje_chat_asesoria();
    }

    public HashMap<String, String> registrar_mensaje_chat_asesoria(Mensaje_chat_asesoria mensaje_chat_asesoria)
    {
        tipo_consulta = "registrar_mensaje_chat_asesoria";
        return construir_parametros(mensaje_chat_asesoria);
    }

    public HashMap<String, String> mensajes_asesoria_por_asesoria(int asesoria)
    {
        aux.chat_mensaje_chat_asesoria = asesoria;
        tipo_consulta = "mensajes_asesoria_por_asesoria";
        return construir_parametros(aux);
    }

    public HashMap<String, String> mensaje_chat_asesoria_por_chat_mayor(int id,int id_chat)
    {
        aux.id_mensaje_chat_asesoria = id;
        aux.chat_mensaje_chat_asesoria = id_chat;
        tipo_consulta = "mensaje_chat_asesoria_por_chat_mayor";
        return construir_parametros(aux);
    }

    public ArrayList<Mensaje_chat_asesoria> generar_json(String respuesta)
    {
        ArrayList<Mensaje_chat_asesoria> lista_elementos = new ArrayList<>();
        try {
            JsonArray array = new JsonParser().parse(respuesta).getAsJsonArray();
            for(JsonElement element : array )
            {
                lista_elementos.add (agregar_elemento(element.getAsJsonObject()));
            }
        }
        catch(JsonSyntaxException | IllegalStateException | NullPointerException e)
        {
            lista_elementos = new ArrayList<>();
        }
        return lista_elementos;
    }

    private Mensaje_chat_asesoria agregar_elemento(final JsonObject jsonObject)
    {
        return new Mensaje_chat_asesoria(){{
            try
            {
                id_mensaje_chat_asesoria = jsonObject.get(ID_MENSAJE_CHAT_ASESORIA).getAsInt();
                fecha_envio_mensaje_chat_asesoria = jsonObject.get(FECHA_ENVIO_MENSAJE_CHAT_ASESORIA).getAsString();
                hora_envio_mensaje_asesoria = jsonObject.get(HORA_ENVIO_MENSAJE_ASESORIA).getAsString();
                contenido_mensaje_chat_asesoria = jsonObject.get(CONTENIDO_MENSAJE_CHAT_ASESORIA).getAsString();
                chat_mensaje_chat_asesoria = jsonObject.get(CHAT_MENSAJE_CHAT_ASESORIA).getAsInt();
                id_creador_mensaje_chat_asesoria = jsonObject.get(ID_CREADOR_MENSAJE_CHAT_ASESORIA).getAsInt();
                tipo_creador_mensaje_chat_asesoria = jsonObject.get(TIPO_CREADOR_MENSAJE_CHAT_ASESORIA).getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Mensaje_chat_asesoria elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty(ID_CREADOR_MENSAJE_CHAT_ASESORIA, elemento.id_mensaje_chat_asesoria);
            obj.addProperty(FECHA_ENVIO_MENSAJE_CHAT_ASESORIA, elemento.fecha_envio_mensaje_chat_asesoria);
            obj.addProperty(HORA_ENVIO_MENSAJE_ASESORIA, elemento.hora_envio_mensaje_asesoria);
            obj.addProperty(CONTENIDO_MENSAJE_CHAT_ASESORIA, elemento.contenido_mensaje_chat_asesoria);
            obj.addProperty(CHAT_MENSAJE_CHAT_ASESORIA, elemento.chat_mensaje_chat_asesoria);
            obj.addProperty(ID_CREADOR_MENSAJE_CHAT_ASESORIA, elemento.id_creador_mensaje_chat_asesoria);
            obj.addProperty(TIPO_CREADOR_MENSAJE_CHAT_ASESORIA, elemento.tipo_creador_mensaje_chat_asesoria);
            obj.addProperty("fecha1",fecha1);
            obj.addProperty("fecha2",fecha2);
            obj.addProperty(TIPO_CONSULTA,tipo_consulta);
            obj.addProperty("llave_ws",llave_ws);
            if(Gestion_usuario.getUsuario_online() != null)
            {
                obj.addProperty(NOMBRE_USUARIO,Gestion_usuario.getUsuario_online().nombre_cuenta_usuario);
                obj.addProperty(CONTRASENA_USUARIO,Gestion_usuario.getUsuario_online().contrasena_usuario);
            }
            else
            {
                obj.addProperty(NOMBRE_USUARIO,"");
                obj.addProperty(CONTRASENA_USUARIO,"");
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("json",obj.toString());
        return hashMap;
    }
}
