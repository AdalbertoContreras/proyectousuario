package com.example.gestion;

import com.example.modelo.Alerta_temprana;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_alerta_temprana {
    private static Alerta_temprana aux = new Alerta_temprana();
    private static String llave_ws = "alerta_temprana";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Alerta_temprana();
    }

    public HashMap<String, String> consultar_alerta_temprana()
    {
        tipo_consulta = "consultar_alerta_temprana";
        return construir_parametros(aux);
    }

    public HashMap<String, String> registrar_alerta_temprana(Alerta_temprana alerta_temprana)
    {
        tipo_consulta = "insert";
        return construir_parametros(alerta_temprana);
    }

    private ArrayList<Alerta_temprana> generar_json(String respuesta)
    {
        ArrayList<Alerta_temprana> lista_elementos = new ArrayList<>();
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

    private Alerta_temprana agregar_elemento(final JsonObject jsonObject)
    {
        return new Alerta_temprana(){{
            try {
                id_alerta_temprana = jsonObject.get("id_alerta_temprana").getAsInt();
                asunto_alerta_temprana = jsonObject.get("asunto_alerta_temprana").getAsInt();
                descripcion_alerta_temprana = jsonObject.get("descripcion_alerta_temprana").getAsString();
                fecha_alerta_temprana = jsonObject.get("fecha_alerta_temprana").getAsString();
                hora_alerta_temprana = jsonObject.get("hora_alerta_temprana").getAsString();
                usuario_alerta_temprana = jsonObject.get("usuario_alerta_temprana").getAsInt();
                estado_atendido = jsonObject.get("estado_atendido").getAsInt();
                numero_visitas = jsonObject.get("numero_visitas").getAsInt();
                estado_atendido = jsonObject.get("estado_atendido").getAsInt();
                if(!jsonObject.get("estado_atendido").isJsonNull())
                {
                    atendido_por = jsonObject.get("atendido_por").getAsInt();
                }
                else
                {
                    atendido_por = -1;
                }
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Alerta_temprana elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_alerta_temprana", elemento.id_alerta_temprana);
            obj.addProperty("asunto_alerta_temprana", elemento.asunto_alerta_temprana);
            obj.addProperty("descripcion_alerta_temprana", elemento.descripcion_alerta_temprana);
            obj.addProperty("fecha_alerta_temprana", elemento.fecha_alerta_temprana);
            obj.addProperty("hora_alerta_temprana", elemento.hora_alerta_temprana);
            obj.addProperty("usuario_alerta_temprana", elemento.usuario_alerta_temprana);
            obj.addProperty("estado_visto", elemento.estado_visto);
            obj.addProperty("numero_visitas", elemento.numero_visitas);
            obj.addProperty("estado_atendido", elemento.estado_atendido);
            obj.addProperty("atendido_por", elemento.atendido_por);
            /*$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
            obj.addProperty("fecha1",fecha1);
            obj.addProperty("fecha2",fecha2);
            obj.addProperty("tipo_consulta",tipo_consulta);
            obj.addProperty("llave_ws",llave_ws);
            obj.addProperty("usuario_ol",Gestion_usuario.getUsuario_online().nombre_cuenta_usuario);
            obj.addProperty("contrasena_ol",Gestion_usuario.getUsuario_online().contrasena_usuario);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("json",obj.toString());
        return hashMap;
    }
}
