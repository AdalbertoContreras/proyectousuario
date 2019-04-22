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
    //############################################################################################\\
    //###############################PROPIEDADES GLOBALES##########################################\\
    private final String LLAVE_ALERTA_TEMPRANA= Propiedades.LLAVE_ALERTA_TEMPRANA;
    private final String TIPO_CONSULTA = Propiedades.TIPO_CONSULTA;
    private final String LLAVE_WS = Propiedades.LLAVE_WS;
    private final String JSON = Propiedades.JSON;
    private final String TOKEN = Propiedades.TOKEN;
    //############################################################################################\\
    //###############################PROPIEDADES DE CATEGORIA NOTICIA MANUAL#######################\\
    private final String ID_ALERTA_TEMPRANA = "id_alerta_temprana";
    private final String ASUNTO_ALERTA_TEMPRANA = "asunto_alerta_temprana";
    private final String DESCRIPCION_ALERTA_TEMPRANA = "descripcion_alerta_temprana";
    private final String FECHA_ALERTA_TEMPRANA = "fecha_alerta_temprana";
    private final String HORA_ALERTA_TEMPRANA = "hora_alerta_temprana";
    private final String USUARIO_ALERTA_TEMPRANA = "usuario_alerta_temprana";
    private final String ESTADO_ATENDIDO = "estado_atendido";
    private final String NUMERO_VISITAS = "numero_visitas";
    private final String ATENDIDO_POR = "atendido_por";
    private final String ASUNTO = "asunto";
    private final String ESTADO_VISTO = "estado_visto";
    //############################################################################################\\
    //###############################CONSULTA#######################\\
    private final String NUM_ALERTAS = "num_alertas";
    private final String CONSULTAR_POR_USUARIO_MAYOR = "consultar_por_usuario_mayor";
    private final String CONSULTAR_NUM_ALERTAS_POR_USUARIO = "consultar_num_alertas_por_usuario";
    private final String CONSULTAR_ALERTAS_TEMPRANAS_POR_USUARIO = "consultar_alertas_tempranas_por_usuario";
    private final String INSERT = "insert";

    private static Alerta_temprana aux = new Alerta_temprana();
    private static String tipo_consulta;
    private JsonObject obj;


    public HashMap<String, String> num_alertas()
    {
        tipo_consulta = NUM_ALERTAS;
        return construir_parametros();
    }

    public HashMap<String, String> consultar_por_usuario_mayor(int id_alerta, int id_usuario)
    {
        tipo_consulta = CONSULTAR_POR_USUARIO_MAYOR;
        aux.usuario_alerta_temprana = id_usuario;
        aux.id_alerta_temprana = id_alerta;
        return construir_parametros();
    }

    public HashMap<String, String> consultar_num_alertas_por_usuario(int usuario)
    {
        tipo_consulta = CONSULTAR_NUM_ALERTAS_POR_USUARIO;
        aux.usuario_alerta_temprana = usuario;
        return construir_parametros(aux);
    }

    public HashMap<String, String> consultar_alertas_tempranas_por_usuario(int usuario)
    {
        tipo_consulta = CONSULTAR_ALERTAS_TEMPRANAS_POR_USUARIO;
        aux.usuario_alerta_temprana = usuario;
        return construir_parametros(aux);
    }

    public HashMap<String, String> registrar_alerta_temprana(Alerta_temprana alerta_temprana)
    {
        tipo_consulta = INSERT;
        return construir_parametros(alerta_temprana);
    }

    public ArrayList<Alerta_temprana> generar_json(String respuesta)
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
                id_alerta_temprana = jsonObject.get(ID_ALERTA_TEMPRANA).getAsInt();
                asunto_alerta_temprana = jsonObject.get(ASUNTO_ALERTA_TEMPRANA).getAsInt();
                descripcion_alerta_temprana = jsonObject.get(DESCRIPCION_ALERTA_TEMPRANA).getAsString();
                fecha_alerta_temprana = jsonObject.get(FECHA_ALERTA_TEMPRANA).getAsString();
                hora_alerta_temprana = jsonObject.get(HORA_ALERTA_TEMPRANA).getAsString();
                usuario_alerta_temprana = jsonObject.get(USUARIO_ALERTA_TEMPRANA).getAsInt();
                estado_atendido = jsonObject.get(ESTADO_ATENDIDO).getAsInt();
                numero_visitas = jsonObject.get(NUMERO_VISITAS).getAsInt();
                if(!jsonObject.get(ATENDIDO_POR).isJsonNull())
                {
                    atendido_por = jsonObject.get(ATENDIDO_POR).getAsInt();
                }
                else
                {
                    atendido_por = -1;
                }
                if(!jsonObject.get(ASUNTO).isJsonNull())
                {
                    asunto = jsonObject.get(ASUNTO).getAsString();
                }
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Alerta_temprana elemento)
    {
        obj = new JsonObject();
        try {
            obj.addProperty(ID_ALERTA_TEMPRANA, elemento.id_alerta_temprana);
            obj.addProperty(ASUNTO_ALERTA_TEMPRANA, elemento.asunto_alerta_temprana);
            obj.addProperty(DESCRIPCION_ALERTA_TEMPRANA, elemento.descripcion_alerta_temprana);
            obj.addProperty(FECHA_ALERTA_TEMPRANA, elemento.fecha_alerta_temprana);
            obj.addProperty(HORA_ALERTA_TEMPRANA, elemento.hora_alerta_temprana);
            obj.addProperty(USUARIO_ALERTA_TEMPRANA, elemento.usuario_alerta_temprana);
            obj.addProperty(ESTADO_VISTO, elemento.estado_visto);
            obj.addProperty(NUMERO_VISITAS, elemento.numero_visitas);
            obj.addProperty(ESTADO_ATENDIDO, elemento.estado_atendido);
            obj.addProperty(ATENDIDO_POR, elemento.atendido_por);
            /*$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
            obj.addProperty(TIPO_CONSULTA,tipo_consulta);
            obj.addProperty(LLAVE_WS,LLAVE_ALERTA_TEMPRANA);
            adjuntarAceso();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(JSON,obj.toString());
        return hashMap;
    }

    private HashMap<String,String> construir_parametros()
    {
        obj = new JsonObject();
        try {
            /*$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
            obj.addProperty(TIPO_CONSULTA,tipo_consulta);
            obj.addProperty(LLAVE_WS,LLAVE_ALERTA_TEMPRANA);
            adjuntarAceso();

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(JSON,obj.toString());
        return hashMap;
    }

    private void adjuntarAceso()
    {
        if(Gestion_usuario.getUsuario_online() != null)
        {
            obj.addProperty(TOKEN,Gestion_usuario.getUsuario_online().token);
        }
    }
}
