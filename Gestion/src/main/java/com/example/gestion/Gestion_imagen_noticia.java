package com.example.gestion;

import com.example.modelo.Chat_asesoria;
import com.example.modelo.Imagen_noticia;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_imagen_noticia {
    //############################################################################################\\
    //###############################PROPIEDADES DE CATEGORIA NOTICIA MANUAL#######################\\
    private final String ID_IMAGEN_NOTICIA = "id_imagen_noticia";
    private final String URL_IMAGEN_NOTICIA = "url_imagen_noticia";
    private final String FECHA_REGISTRO_IMAGEN_NOTICIA = "fecha_registro_imagen_noticia";
    private final String HORA_REGISTRO_IMAGEN_NOTICIA = "hora_registro_imagen_noticia";
    private final String NOTICIA_IMAGEN_NOTICIA = "noticia_imagen_noticia";

    public ArrayList<Imagen_noticia> generar_json(String respuesta)
    {
        ArrayList<Imagen_noticia> lista_elementos = new ArrayList<>();
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

    private Imagen_noticia agregar_elemento(final JsonObject jsonObject)
    {
        return new Imagen_noticia(){{
            try {
                id_imagen_noticia = jsonObject.get(ID_IMAGEN_NOTICIA).getAsInt();
                url_imagen_noticia = jsonObject.get(URL_IMAGEN_NOTICIA).getAsString();
                fecha_registro_imagen_noticia = jsonObject.get(FECHA_REGISTRO_IMAGEN_NOTICIA).getAsString();
                hora_registro_imagen_noticia = jsonObject.get(HORA_REGISTRO_IMAGEN_NOTICIA).getAsString();
                noticia_imagen_noticia = jsonObject.get(NOTICIA_IMAGEN_NOTICIA).getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }
}
