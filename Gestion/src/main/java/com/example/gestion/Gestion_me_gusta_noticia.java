package com.example.gestion;

import com.example.modelo.Lugar;
import com.example.modelo.Me_gusta_noticia;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

import sun.rmi.runtime.Log;

public class Gestion_me_gusta_noticia {
    private static Me_gusta_noticia aux = new Me_gusta_noticia();
    private static String llave_ws = "me_gusta_noticia";
    private static String fecha1;
    private static String fecha2;
    private static String tipo_consulta;

    private static void iniciar_axu()
    {
        aux = new Me_gusta_noticia();
    }

    public HashMap<String, String> me_gusta_por_noticia_y_usuario(int id_usuario, int id_noticia)
    {
        tipo_consulta = "me_gusta_noticia_por_noticia_y_usuario";
        aux.usuario_me_gusta_noticia = id_usuario;
        aux.noticia_me_gusta_noticia = id_noticia;
        return construir_parametros(aux);
    }

    public HashMap<String, String> me_gusta_por_usuario(int id_usuario)
    {
        tipo_consulta = "me_gusta_por_usuario";
        aux.usuario_me_gusta_noticia = id_usuario;
        return construir_parametros(aux);
    }

    public HashMap<String, String> dar_me_gusta(int id_noticia, int id_usuario)
    {
        tipo_consulta = "insert";
        aux.noticia_me_gusta_noticia = id_noticia;
        aux.usuario_me_gusta_noticia = id_usuario;
        return construir_parametros(aux);
    }

    public HashMap<String, String> quitar_me_gusta(Me_gusta_noticia me_gusta_noticia)
    {
        tipo_consulta = "delete";
        return construir_parametros(me_gusta_noticia);
    }

    public ArrayList<Me_gusta_noticia> generar_json(String respuesta)
    {
        ArrayList<Me_gusta_noticia> lista_elementos = new ArrayList<>();
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

    private Me_gusta_noticia agregar_elemento(final JsonObject jsonObject)
    {
        return new Me_gusta_noticia(){{
            try {
                id_me_gusta_noticia = jsonObject.get("id_me_gusta_noticia").getAsInt();
                usuario_me_gusta_noticia = jsonObject.get("usuario_me_gusta_noticia").getAsInt();
                noticia_me_gusta_noticia = jsonObject.get("noticia_me_gusta_noticia").getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Me_gusta_noticia elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id_me_gusta_noticia", elemento.id_me_gusta_noticia);
            obj.addProperty("usuario_me_gusta_noticia", elemento.usuario_me_gusta_noticia);
            obj.addProperty("noticia_me_gusta_noticia", elemento.noticia_me_gusta_noticia);
            obj.addProperty("fecha1",fecha1);
            obj.addProperty("fecha2",fecha2);
            obj.addProperty("tipo_consulta",tipo_consulta);
            obj.addProperty("llave_ws",llave_ws);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("json",obj.toString());
        return hashMap;
    }
}
