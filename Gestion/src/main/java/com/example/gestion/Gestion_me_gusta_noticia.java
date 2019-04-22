package com.example.gestion;

import com.example.modelo.Me_gusta_noticia;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_me_gusta_noticia {
    //############################################################################################\\
    //###############################PROPIEDADES GLOBALES##########################################\\
    private final String llave_ME_GUSTA_NOTICIA= Propiedades.LLAVE_ME_GUSTA_NOTICIA;
    private final String TIPO_CONSULTA = Propiedades.TIPO_CONSULTA;
    private final String LLAVE_WS = Propiedades.LLAVE_WS;
    private final String JSON = Propiedades.JSON;
    private final String TOKEN = Propiedades.TOKEN;
    //############################################################################################\\
    //###############################PROPIEDADES DE CATEGORIA NOTICIA MANUAL#######################\\
    private final String ID_ME_GUSTA_NOTICIA = "id_me_gusta_noticia";
    private final String USUARIO_ME_GUSTA_NOTICIA = "usuario_me_gusta_noticia";
    private final String NOTICIA_ME_GUSTA_NOTICIA = "noticia_me_gusta_noticia";
    //############################################################################################\\
    //###############################CONSULTAS####################################################\\
    private final String ME_GUSTA_NOTICIA_POR_NOTICIA_Y_USUARIO = "me_gusta_noticia_por_noticia_y_usuario";
    private final String INSERT = "insert";

    private static Me_gusta_noticia aux = new Me_gusta_noticia();
    private static String tipo_consulta;

    public HashMap<String, String> me_gusta_por_noticia_y_usuario(int id_usuario, int id_noticia)
    {
        tipo_consulta = ME_GUSTA_NOTICIA_POR_NOTICIA_Y_USUARIO;
        aux.usuario_me_gusta_noticia = id_usuario;
        aux.noticia_me_gusta_noticia = id_noticia;
        return construir_parametros(aux);
    }

    public HashMap<String, String> dar_me_gusta(int id_noticia, int id_usuario)
    {
        tipo_consulta = INSERT;
        aux.noticia_me_gusta_noticia = id_noticia;
        aux.usuario_me_gusta_noticia = id_usuario;
        return construir_parametros(aux);
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
                id_me_gusta_noticia = jsonObject.get(ID_ME_GUSTA_NOTICIA).getAsInt();
                usuario_me_gusta_noticia = jsonObject.get(USUARIO_ME_GUSTA_NOTICIA).getAsInt();
                noticia_me_gusta_noticia = jsonObject.get(NOTICIA_ME_GUSTA_NOTICIA).getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Me_gusta_noticia elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty(ID_ME_GUSTA_NOTICIA, elemento.id_me_gusta_noticia);
            obj.addProperty(USUARIO_ME_GUSTA_NOTICIA, elemento.usuario_me_gusta_noticia);
            obj.addProperty(NOTICIA_ME_GUSTA_NOTICIA, elemento.noticia_me_gusta_noticia);
            obj.addProperty(TIPO_CONSULTA,tipo_consulta);
            obj.addProperty(LLAVE_WS,llave_ME_GUSTA_NOTICIA);
            if(Gestion_usuario.getUsuario_online() != null)
            {
                obj.addProperty(TOKEN,Gestion_usuario.getUsuario_online().token);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(JSON,obj.toString());
        return hashMap;
    }
}
