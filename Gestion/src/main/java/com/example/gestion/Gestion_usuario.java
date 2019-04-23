package com.example.gestion;

import com.example.gestion.Escuchadores.EscuchadorUsuario;
import com.example.modelo.Usuario;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_usuario{
    //############################################################################################\\
    //###############################PROPIEDADES GLOBALES##########################################\\
    private final String LLAVE_USUARIO = Propiedades.LLAVE_USUARIO;
    private final String TIPO_CONSULTA = Propiedades.TIPO_CONSULTA;
    private final String LLAVE_WS = Propiedades.LLAVE_WS;
    private final String JSON = Propiedades.JSON;
    private final String TOKEN = Propiedades.TOKEN;
    private final String URL_SERVER = Propiedades.URL_SERVER;
    //############################################################################################\\
    //###############################PROPIEDADES DE CATEGORIA NOTICIA MANUAL#######################\\
    private final String ID_USUARIO = "id_usuario";
    private final String NOMBRES_USUARIO = "nombres_usuario";
    private final String APELLIDOS_USUARIO = "apellidos_usuario";
    private final String DIRECCION_USUARIO = "direccion_usuario";
    private final String TELEFONO_USUARIO = "telefono_usuario";
    private final String SEXO_USUARIO = "sexo_usuario";
    private final String FECHA_NACIMIENTO = "fecha_nacimiento";
    private final String CORREO_USUARIO = "correo_usuario";
    private final String NOMBRE_CUENTA_USUARIO = "nombre_cuenta_usuario";
    private final String FOTO_PERFIL_USUARIO = "foto_perfil_usuario";
    private final String CONTRASENA_USUARIO = "contrasena_usuario";
    private final String FOTO_PERFIL_ANTERIOR = "foto_perfil_anterior";
    private final String TIPO_CUENTA = "tipo_cuenta";
    //############################################################################################\\
    //###############################CONSULTAS####################################################\\
    private final String INSERT = "insert";
    private final String CERRARSESION = "cerrarsesion";
    private final String MODIFICAR_DATOS_PERSONALES = "modificar_datos_personales";
    private final String EXISTE_NOMBRE_CUENTA = "existe_nombre_cuenta";
    private final String GENERAR_TOKEN_TIPO_GOOGLE_FACEBOOK = "generar_token_tipo_google_facebook";
    private final String ACTUALIZAR_CONTRASENA_USUARIO = "actualizar_contrasena_usuario";
    private final String VALIDAR_CUENTA_Y_GENERAR_TOKEN = "validar_cuenta_y_generar_token";
    private final String VALIDAR_CUENTA = "validar_cuenta";
    private final String VALIDARTOKENOBTENERUSUARIO = "validartokenobtenerusuario";
    private final String CONSULTAR_USUARIO_POR_ID = "consultar_usuario_por_id";


    private Usuario aux = new Usuario();
    private String tipo_consulta;
    private static Usuario usuario_online = null;
    public static EscuchadorUsuario escuchadorParaHome;
    public static EscuchadorUsuario escuchadorParaActivityPrincipal = null;
    public HashMap<String, String> registrar_usuario(Usuario usuario)
    {
        tipo_consulta = INSERT;
        return construir_parametros(usuario);
    }

    public HashMap<String, String> cerrarSesion()
    {
        tipo_consulta = CERRARSESION;
        return construir_parametros(usuario_online);
    }

    public HashMap<String, String> modificar_datos_personales(Usuario usuario)
    {
        if(usuario.foto_perfil_usuario.contains(URL_SERVER))
        {
            usuario.foto_perfil_usuario = usuario.foto_perfil_usuario.replace(URL_SERVER, "");
        }
        if(usuario.foto_perfil_anterior.contains(URL_SERVER))
        {
            usuario.foto_perfil_anterior = usuario.foto_perfil_anterior.replace(URL_SERVER, "");
        }
        tipo_consulta = MODIFICAR_DATOS_PERSONALES;
        return construir_parametros(usuario);
    }

    public HashMap<String, String> existe_nombre_cuenta(String nombre_cuenta)
    {
        tipo_consulta = EXISTE_NOMBRE_CUENTA;
        aux.nombre_cuenta_usuario = nombre_cuenta;
        return construir_parametros(aux);
    }

    public HashMap<String, String> generar_token_tipo_google_facebook(String correo_electronico, int tipoCuenta)
    {
        tipo_consulta = GENERAR_TOKEN_TIPO_GOOGLE_FACEBOOK;
        aux.correo_usuario = correo_electronico;
        aux.tipo_cuenta = tipoCuenta;
        return construir_parametros(aux);
    }

    public HashMap<String, String> actualizar_contrasena_usuario(Usuario usuario)
    {
        tipo_consulta = ACTUALIZAR_CONTRASENA_USUARIO;
        return construir_parametros(usuario);
    }

    public HashMap<String, String> validar_cuenta_y_generar_token(Usuario usuario)
    {
        tipo_consulta = VALIDAR_CUENTA_Y_GENERAR_TOKEN;
        return construir_parametros(usuario);
    }

    public HashMap<String, String> validar_cuenta(Usuario usuario)
    {
        tipo_consulta = VALIDAR_CUENTA;
        return construir_parametros(usuario);
    }

    public HashMap<String, String> validarTokenObtenerusuario(Usuario usuario)
    {
        tipo_consulta = VALIDARTOKENOBTENERUSUARIO;
        return construir_parametros(usuario);
    }

    public HashMap<String, String> consultar_usuario_por_id(Usuario usuario)
    {
        tipo_consulta = CONSULTAR_USUARIO_POR_ID;
        return construir_parametros(usuario);
    }

    public ArrayList<Usuario> generar_json(String respuesta)
    {
        ArrayList<Usuario> lista_elementos = new ArrayList<>();
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

    private Usuario agregar_elemento(final JsonObject jsonObject)
    {

        return new Usuario(){{
            try {
                id_usuario = jsonObject.get(ID_USUARIO).getAsInt();
                nombres_usuario = jsonObject.get(NOMBRES_USUARIO).getAsString();
                apellidos_usuario = jsonObject.get(APELLIDOS_USUARIO).getAsString();
                direccion_usuario = jsonObject.get(DIRECCION_USUARIO).getAsString();
                telefono_usuario = jsonObject.get(TELEFONO_USUARIO).getAsString();
                sexo_usuario = jsonObject.get(SEXO_USUARIO).getAsInt();
                if(!jsonObject.get(FECHA_NACIMIENTO).isJsonNull())
                {
                    fecha_nacimiento = jsonObject.get(FECHA_NACIMIENTO).getAsString();
                }
                else
                {
                    fecha_nacimiento = "";
                }

                correo_usuario = jsonObject.get(CORREO_USUARIO).getAsString();
                nombre_cuenta_usuario = jsonObject.get(NOMBRE_CUENTA_USUARIO).getAsString();
                foto_perfil_usuario = jsonObject.get(FOTO_PERFIL_USUARIO).getAsString();
                if(jsonObject.has(TOKEN))
                {
                    if(!jsonObject.get(TOKEN).isJsonNull())
                    {
                        token = jsonObject.get(TOKEN).getAsString();
                    }
                    else
                    {
                        token = null;
                    }
                }
                else
                {
                    token = null;
                }
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Usuario elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty(ID_USUARIO, elemento.id_usuario);
            obj.addProperty(NOMBRES_USUARIO, elemento.nombres_usuario);
            obj.addProperty(APELLIDOS_USUARIO, elemento.apellidos_usuario);
            obj.addProperty(DIRECCION_USUARIO, elemento.direccion_usuario);
            obj.addProperty(TELEFONO_USUARIO, elemento.telefono_usuario);
            obj.addProperty(SEXO_USUARIO, elemento.sexo_usuario);
            obj.addProperty(FECHA_NACIMIENTO, elemento.fecha_nacimiento);
            obj.addProperty(CORREO_USUARIO, elemento.correo_usuario);
            obj.addProperty(NOMBRE_CUENTA_USUARIO, elemento.nombre_cuenta_usuario);
            obj.addProperty(CONTRASENA_USUARIO, elemento.contrasena_usuario);
            obj.addProperty(FOTO_PERFIL_USUARIO, elemento.foto_perfil_usuario);
            obj.addProperty(FOTO_PERFIL_ANTERIOR, elemento.foto_perfil_anterior);
            obj.addProperty(TOKEN, elemento.token);
            obj.addProperty(TIPO_CUENTA, elemento.tipo_cuenta);
            obj.addProperty(TIPO_CONSULTA,tipo_consulta);
            obj.addProperty(LLAVE_WS, LLAVE_USUARIO);

            if(usuario_online != null)
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

    public static Usuario getUsuario_online()
    {
        return usuario_online;
    }

    public static void setUsuario_online(Usuario usuario_online) {
        if(Gestion_usuario.getUsuario_online() != usuario_online)
        {
            if(escuchadorParaActivityPrincipal != null)
            {
                escuchadorParaActivityPrincipal.usuarioCambiado(usuario_online);
            }
            if(escuchadorParaHome != null)
            {
                escuchadorParaHome.usuarioCambiado(usuario_online);
            }
        }
        actualizarUsuario(usuario_online);
    }

    public static void actualizarUsuario(Usuario usuario_online)
    {
        Gestion_usuario.usuario_online = usuario_online;
    }
}
