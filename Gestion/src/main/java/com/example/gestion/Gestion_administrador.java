package com.example.gestion;

import com.example.modelo.Administrador;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_administrador{
    //############################################################################################\\
    //###############################PROPIEDADES GLOBALES##########################################\\
    private final String LLAVE_ADMINISTRADOR= Propiedades.LLAVE_ADMINISTRADOR;
    private final String TIPO_CONSULTA = Propiedades.TIPO_CONSULTA;
    private final String LLAVE_WS = Propiedades.LLAVE_WS;
    private final String JSON = Propiedades.JSON;
    private final String TOKEN = Propiedades.TOKEN;
    //############################################################################################\\
    //###############################PROPIEDADES DE CATEGORIA NOTICIA MANUAL#######################\\
    private final String ID_ADMINISTRADOR = "id_administrador";
    private final String TIPO_ADMINISTRADOR = "tipo_administrador";
    private final String NOMBRE_CUENTA_ADMINISTRADOR = "nombre_cuenta_administrador";
    private final String NOMBRES_ADMINISTRADOR = "nombres_administrador";
    private final String APELLIDOS_ADMINISTRADOR = "apellidos_administrador";
    private final String FECHA_NACIMIENTO_ADMINISTRADOR = "fecha_nacimiento_administrador";
    private final String SEXO_ADMINISTRADOR = "sexo_administrador";
    private final String ESTADO_ADMINISTRADOR = "estado_administrador";
    private final String FECHA_REGISTRO_ADMINISTRADOR = "fecha_registro_administrador";
    private final String HORA_REGISTRO_ADMINISTRADOR = "hora_registro_administrador";
    private final String URL_FOTO_PERFIL_ADMINISTRADOR = "url_foto_perfil_administrador";
    private final String NUMERO_ASESORIAS_DADAS_ADMINISTRADOR = "numero_asesorias_dadas_administrador";
    private final String NUMERO_ASESORIAS_DADAS_PRIMERA_INFANCIA_ADMINISTRADOR = "numero_asesorias_dadas_primera_infancia_administrador";
    private final String NUMERO_ASESORIAS_DADAS_INFANCIA_ADMINISTRADOR = "numero_asesorias_dadas_infancia_administrador";
    private final String NUMERO_ASESORIAS_DADAS_ADOLECENCIA_ADMINISTRADOR = "numero_asesorias_dadas_adolecencia_administrador";
    private final String NUMERO_ASESORIAS_DADAS_JUVENTUD_ADMINISTRADOR = "numero_asesorias_dadas_juventud_administrador";
    private final String NUMERO_ASESORIAS_DADAS_ADULTEZ_ADMINISTRADOR = "numero_asesorias_dadas_adultez_administrador";
    private final String NUMERO_ASESORIAS_DADAS_MAYOR_ADMINISTRADOR = "numero_asesorias_dadas_mayor_administrador";
    private final String NUMERO_ASESORIAS_DADAS_PRIMERA_M_INFANCIA_ADMINISTRADOR = "numero_asesorias_dadas_primera_m_infancia_administrador";
    private final String NUMERO_ASESORIAS_DADAS_PRIMERA_F_INFANCIA_ADMINISTRADOR = "numero_asesorias_dadas_primera_f_infancia_administrador";
    private final String NUMERO_ASESORIAS_DADAS_INFANCIA_M_ADMINISTRADOR = "numero_asesorias_dadas_infancia_m_administrador";
    private final String NUMERO_ASESORIAS_DADAS_INFANCIA_F_ADMINISTRADOR = "numero_asesorias_dadas_infancia_f_administrador";
    private final String NUMERO_ASESORIAS_DADAS_ADOLECENCIA_M_ADMINISTRADOR = "numero_asesorias_dadas_adolecencia_m_administrador";
    private final String NUMERO_ASESORIAS_DADAS_ADOLECENCIA_F_ADMINISTRADOR = "numero_asesorias_dadas_adolecencia_f_administrador";
    private final String NUMERO_ASESORIAS_DADAS_JUVENTUD_M_ADMINISTRADOR = "numero_asesorias_dadas_juventud_m_administrador";
    private final String NUMERO_ASESORIAS_DADAS_JUVENTUD_F_ADMINISTRADOR = "numero_asesorias_dadas_juventud_f_administrador";
    private final String NUMERO_ASESORIAS_DADAS_ADULTEZ_M_ADMINISTRADOR = "numero_asesorias_dadas_adultez_m_administrador";
    private final String NUMERO_ASESORIAS_DADAS_ADULTEZ_F_ADMINISTRADOR = "numero_asesorias_dadas_adultez_f_administrador";
    private final String NUMERO_ASESORIAS_DADAS_MAYOR_M_ADMINISTRADOR = "numero_asesorias_dadas_mayor_m_administrador";
    private final String NUMERO_ASESORIAS_DADAS_MAYOR_F_ADMINISTRADOR = "numero_asesorias_dadas_mayor_f_administrador";
    //############################################################################################\\
    //###############################PROPIEDADES RELACION#########################################\\
    private final String ESPECIALIDAD = "especialidad";
    //############################################################################################\\
    //###############################PROPIEDADES RELACION#########################################\\
    private final String CONSULTAR_POR_ESPECIALIDAD = "consultar_por_especialidad";

    private int especialidad;
    private String tipo_consulta;
    JsonObject obj;

    public HashMap<String, String> consultar_administradores_por_especialidad(int _especialidad)
    {
        tipo_consulta = CONSULTAR_POR_ESPECIALIDAD;
        especialidad = _especialidad;
        return construir_parametros();
    }

    public ArrayList<Administrador> generar_json(String respuesta)
    {
        ArrayList<Administrador> lista_elementos = new ArrayList<>();
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

    private Administrador agregar_elemento(final JsonObject jsonObject)
    {
        return new Administrador(){{
            try {
                id_administrador = jsonObject.get(ID_ADMINISTRADOR).getAsInt();
                tipo_administrador = jsonObject.get(TIPO_ADMINISTRADOR).getAsInt();
                nombre_cuenta_administrador = jsonObject.get(NOMBRE_CUENTA_ADMINISTRADOR).getAsString();
                nombres_administrador = jsonObject.get(NOMBRES_ADMINISTRADOR).getAsString();
                apellidos_administrador = jsonObject.get(APELLIDOS_ADMINISTRADOR).getAsString();
                fecha_nacimiento_administrador = jsonObject.get(FECHA_NACIMIENTO_ADMINISTRADOR).getAsString();
                sexo_administrador = jsonObject.get(SEXO_ADMINISTRADOR).getAsInt();
                estado_administrador = jsonObject.get(ESTADO_ADMINISTRADOR).getAsInt();
                fecha_registro_administrador = jsonObject.get(FECHA_REGISTRO_ADMINISTRADOR).getAsString();
                hora_registro_administrador= jsonObject.get(HORA_REGISTRO_ADMINISTRADOR).getAsString();
                url_foto_perfil_administrador= jsonObject.get(URL_FOTO_PERFIL_ADMINISTRADOR).getAsString();
                numero_asesorias_dadas_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_ADMINISTRADOR).getAsInt();
                numero_asesorias_dadas_primera_infancia_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_PRIMERA_INFANCIA_ADMINISTRADOR).getAsInt();
                numero_asesorias_dadas_infancia_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_INFANCIA_ADMINISTRADOR).getAsInt();
                numero_asesorias_dadas_adolecencia_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_ADOLECENCIA_ADMINISTRADOR).getAsInt();
                numero_asesorias_dadas_juventud_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_JUVENTUD_ADMINISTRADOR).getAsInt();
                numero_asesorias_dadas_adultez_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_ADULTEZ_ADMINISTRADOR).getAsInt();
                numero_asesorias_dadas_mayor_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_MAYOR_ADMINISTRADOR).getAsInt();
                numero_asesorias_dadas_primera_m_infancia_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_PRIMERA_M_INFANCIA_ADMINISTRADOR).getAsInt();
                numero_asesorias_dadas_primera_f_infancia_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_PRIMERA_F_INFANCIA_ADMINISTRADOR).getAsInt();
                numero_asesorias_dadas_infancia_m_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_INFANCIA_M_ADMINISTRADOR).getAsInt();
                numero_asesorias_dadas_infancia_f_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_INFANCIA_F_ADMINISTRADOR).getAsInt();
                numero_asesorias_dadas_adolecencia_m_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_ADOLECENCIA_M_ADMINISTRADOR).getAsInt();
                numero_asesorias_dadas_adolecencia_f_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_ADOLECENCIA_F_ADMINISTRADOR).getAsInt();
                numero_asesorias_dadas_juventud_m_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_JUVENTUD_M_ADMINISTRADOR).getAsInt();
                numero_asesorias_dadas_juventud_f_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_JUVENTUD_F_ADMINISTRADOR).getAsInt();
                numero_asesorias_dadas_adultez_m_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_ADULTEZ_M_ADMINISTRADOR).getAsInt();
                numero_asesorias_dadas_adultez_f_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_ADULTEZ_F_ADMINISTRADOR).getAsInt();
                numero_asesorias_dadas_mayor_m_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_MAYOR_M_ADMINISTRADOR).getAsInt();
                numero_asesorias_dadas_mayor_f_administrador = jsonObject.get(NUMERO_ASESORIAS_DADAS_MAYOR_F_ADMINISTRADOR).getAsInt();
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros()
    {
        obj = new JsonObject();
        try {
            /*$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
            obj.addProperty(TIPO_CONSULTA,tipo_consulta);
            obj.addProperty(LLAVE_WS,LLAVE_ADMINISTRADOR);
            obj.addProperty(ESPECIALIDAD,especialidad);
            adjuntarAcceso();
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
