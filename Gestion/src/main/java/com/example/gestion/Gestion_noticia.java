package com.example.gestion;

import com.example.modelo.Movil_registro;
import com.example.modelo.Noticia;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_noticia {
    //############################################################################################\\
    //###############################PROPIEDADES GLOBALES##########################################\\
    private final String LLAVE_NOTICIA= Propiedades.LLAVE_NOTICIA;
    private final String TIPO_CONSULTA = Propiedades.TIPO_CONSULTA;
    private final String LLAVE_WS = Propiedades.LLAVE_WS;
    private final String JSON = Propiedades.JSON;
    private final String TOKEN = Propiedades.TOKEN;
    //############################################################################################\\
    //###############################PROPIEDADES DE CATEGORIA NOTICIA MANUAL#######################\\
    private final String ID_NOTITICIA = "id_notiticia";
    private final String ID_GENERACION_NOTICIA = "id_generacion_noticia";
    private final String TITULO_NOTICIA = "titulo_noticia";
    private final String CONTENIDO_NOTICIA = "contenido_noticia";
    private final String NUM_IMAGENES_NOTICIA = "num_imagenes_noticia";
    private final String FECHA_REGISTRO_NOTICIA = "fecha_registro_noticia";
    private final String HORA_REGISTRO_NOTICIA = "hora_registro_noticia";
    private final String TIPO_CREACION_NOTICIA = "tipo_creacion_noticia";
    private final String NUMERO_VISISTAS_NOTICIA = "numero_visistas_noticia";
    private final String ADMINISTRADOR_NOTICIA = "administrador_noticia";
    private final String CATEGORIA_NOTICIA_MANUAL_NOTICIA = "categoria_noticia_manual_noticia";
    private final String NUMERO_ME_GUSTA = "numero_me_gusta";
    private final String JSON_IMAGENES = "json_imagenes";
    //############################################################################################\\
    //###############################CONSULTAS#######################\\
    private final String NOTICIA_CONSULTAR_MAYORES = "noticia_consultar_mayores";
    private final String NOTICIA_POR_ID = "noticia_por_id";
    private final String CONSULTAR_CON_IMAGENES_Y_M_PRIMERO = "consultar_con_imagenes_y_m_primero";

    private static Noticia aux = new Noticia();
    private static String tipo_consulta;
    private JsonObject obj;

    public HashMap<String, String> noticia_consultar_mayores(int id_noticia)
    {
        tipo_consulta = NOTICIA_CONSULTAR_MAYORES;
        aux.id_notiticia = id_noticia;
        return construir_parametros(aux);
    }

    public HashMap<String, String> noticia_por_id(int id_noticia)
    {
        tipo_consulta = NOTICIA_POR_ID;
        aux.id_notiticia = id_noticia;
        return construir_parametros(aux);
    }

    public HashMap<String,String> consultar_con_imagenes_y_m_primero()
    {
        tipo_consulta = CONSULTAR_CON_IMAGENES_Y_M_PRIMERO;
        return  construir_parametros(aux);
    }

    public ArrayList<Noticia> generar_json(String respuesta)
    {
        ArrayList<Noticia> lista_elementos = new ArrayList<>();
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

    private Noticia agregar_elemento(final JsonObject jsonObject)
    {
        return new Noticia(){{
            try {
                id_notiticia = jsonObject.get(ID_NOTITICIA).getAsInt();
                id_generacion_noticia = jsonObject.get(ID_GENERACION_NOTICIA).getAsInt();
                titulo_noticia = jsonObject.get(TITULO_NOTICIA).getAsString();
                contenido_noticia = jsonObject.get(CONTENIDO_NOTICIA).getAsString();
                num_imagenes_noticia = jsonObject.get(NUM_IMAGENES_NOTICIA).getAsInt();
                fecha_registro_noticia = jsonObject.get(FECHA_REGISTRO_NOTICIA).getAsString();
                hora_registro_noticia = jsonObject.get(HORA_REGISTRO_NOTICIA).getAsString();
                tipo_creacion_noticia = jsonObject.get(TIPO_CREACION_NOTICIA).getAsInt();
                numero_visistas_noticia = jsonObject.get(NUMERO_VISISTAS_NOTICIA).getAsInt();
                administrador_noticia = jsonObject.get(ADMINISTRADOR_NOTICIA).getAsInt();
                categoria_noticia_manual_noticia = jsonObject.get(CATEGORIA_NOTICIA_MANUAL_NOTICIA).getAsInt();
                numero_me_gusta = jsonObject.get(NUMERO_ME_GUSTA).getAsInt();
                if(!jsonObject.get(JSON_IMAGENES).isJsonNull())
                {
                    json_imagenes = jsonObject.get(JSON_IMAGENES).getAsString();
                }
                else
                {
                    json_imagenes = "";
                }

            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Noticia elemento)
    {
        obj = new JsonObject();
        try {
            obj.addProperty(ID_NOTITICIA, elemento.id_notiticia);
            obj.addProperty(ID_GENERACION_NOTICIA, elemento.id_generacion_noticia);
            obj.addProperty(TITULO_NOTICIA, elemento.titulo_noticia);
            obj.addProperty(CONTENIDO_NOTICIA, elemento.contenido_noticia);
            obj.addProperty(NUM_IMAGENES_NOTICIA, elemento.num_imagenes_noticia);
            obj.addProperty(FECHA_REGISTRO_NOTICIA, elemento.fecha_registro_noticia);
            obj.addProperty(HORA_REGISTRO_NOTICIA, elemento.hora_registro_noticia);
            obj.addProperty(TIPO_CREACION_NOTICIA, elemento.tipo_creacion_noticia);
            obj.addProperty(NUMERO_VISISTAS_NOTICIA, elemento.numero_visistas_noticia);
            obj.addProperty(ADMINISTRADOR_NOTICIA, elemento.administrador_noticia);
            obj.addProperty(CATEGORIA_NOTICIA_MANUAL_NOTICIA, elemento.categoria_noticia_manual_noticia);
            adjuntarAcceso();
            obj.addProperty(TIPO_CONSULTA,tipo_consulta);
            obj.addProperty(LLAVE_WS,LLAVE_NOTICIA);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(JSON,obj.toString());
        return hashMap;
    }

    private void adjuntarAcceso()
    {
        if(Gestion_usuario.getUsuario_online() != null)
        {
            obj.addProperty(TOKEN,Gestion_usuario.getUsuario_online().token);
        }
    }
}
