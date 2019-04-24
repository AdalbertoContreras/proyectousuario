package com.example.gestion;

import com.example.modelo.Chat_asesoria;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Gestion_chat_asesoria {
    //############################################################################################\\
    //###############################PROPIEDADES GLOBALES##########################################\\
    private final String LLAVE_CHAT_ASESORIA= Propiedades.LLAVE_CHAT_ASESORIA;
    private final String TIPO_CONSULTA = Propiedades.TIPO_CONSULTA;
    private final String LLAVE_WS = Propiedades.LLAVE_WS;
    private final String JSON = Propiedades.JSON;
    private final String TOKEN = Propiedades.TOKEN;
    //############################################################################################\\
    //###############################PROPIEDADES DE CATEGORIA NOTICIA MANUAL#######################\\

    private final String ID_CHAT_ASESORIA = "A";
    private final String FECHA_INICIO_ASESORIA = "B";
    private final String HORA_INICIO_ASESORIA = "C";
    private final String FECHA_CIERRE_ASESORIA = "D";
    private final String HORA_CIERRA_CHAT_ASESORIA = "E";
    private final String ADMINISTRADOR_CHAT_ASESORIA = "F";
    private final String USUARIO_CHAT_ASESORIA = "G";
    private final String ESTADO_CERRADO = "H";
    private final String TIEMPO_SESION_CHAT_ASESORIA = "I";
    private final String ESPECIALIZACION_CHAT_ASESORIA = "J";
    private final String ULTIMA_FECHA_ADMINISTRADOR_CHAT_ASESORIA = "K";
    private final String ULTIMA_HORA_ADMINISTRADOR_CHAT_ASESORIA = "L";
    private final String ULTIMO_MENSAJE_ADMINISTRADOR_CHAT_ASESORIA = "M";
    private final String ULTIMA_FECHA_USUARIO_CHAT_ASESORIA = "N";
    private final String ULTIMA_HORA_USUARIO_CHAT_ASESORIA = "O";
    private final String ULTIMO_MENSAJE_USUARIO_CHAT_ASESORIA = "P";
    private final String ULTIMA_FECHA_VISTA_ADMINISTRADOR_CHAT_ASESORIA = "Q";
    private final String ULTIMA_HORA_VISTA_ADMINISTRADOR_CHAT_ASESORIA = "R";
    private final String ULTIMA_FECHA_VISTA_USUARIO_CHAT_ASESORIA = "S";
    private final String ULTIMA_HORA_VISTA_USUARIO_CHAT_ASESORIA = "T";
    private final String ULTIMO_MENSAJE_CHAT_ASESORIA = "U";
    private final String ULTIMA_FECHA_CHAT_ASESORIA = "V";
    private final String ULTIMA_HORA_CHAT_ASESORIA = "X";
    //############################################################################################\\
    //###############################PROPIEDADES RELACIONES#######################################\\
    private final String USUARIO = "usuario";
    private final String ADMINISTRADOR = "administrador";
    private final String ESPECIALIDAD = "especialidad";
    //############################################################################################\\
    //###############################CONSULTA#####################################################\\
    private final String CONSULTAR_CHAT_ASESORIA_POR_USUARIO_ADMINISTRADOR_Y_ESPECIALIDAD = "consultar_chat_asesoria_por_usuario_administrador_y_especialidad";
    private final String VISTA_POR_USUARIO = "vista_por_usuario";
    private final String CONSULTAR_POR_USUARIO = "consultar_por_usuario";
    private final String NUMERO_POR_USUARIO = "numero_por_usuario";
    private String tipo_consulta;

    private static ArrayList<Chat_asesoria> chat_asesorias = null;
    private static Chat_asesoria aux = new Chat_asesoria();
    public static ArrayChatCambiado arrayChatCambiado;
    public interface ArrayChatCambiado
    {
        void chatCambiado();
    }
    private  static ChatAbierto chatAbierto;
    public interface ChatAbierto
    {
        void abierto(int id_chat);
    }

    public static void chat_abiero(int id_chat)
    {
        chatAbierto.abierto(id_chat);
    }

    public static void setChatAbierto(ChatAbierto chatAbierto) {
        Gestion_chat_asesoria.chatAbierto = chatAbierto;
    }

    public HashMap<String, String> consultar_chat_asesoria_por_usuario_administrador_y_especialidad(int administrador, int usuario, int especialidad)
    {
        aux.administrador_chat_asesoria = administrador;
        aux.usuario_chat_asesoria = usuario;
        aux.especializacion_chat_asesoria = especialidad;
        tipo_consulta = CONSULTAR_CHAT_ASESORIA_POR_USUARIO_ADMINISTRADOR_Y_ESPECIALIDAD;
        return construir_parametros(aux);
    }

    public HashMap<String, String> vista_por_usuario( int id_chat)
    {
        aux.id_chat_asesoria = id_chat;
        tipo_consulta = VISTA_POR_USUARIO;
        return construir_parametros(aux);
    }

    public HashMap<String, String> consultar_por_usuario( int usuario)
    {
        aux.usuario_chat_asesoria = usuario;
        tipo_consulta = CONSULTAR_POR_USUARIO;
        return construir_parametros(aux);
    }

    public HashMap<String, String> numero_por_usuario( int usuario)
    {
        aux.usuario_chat_asesoria = usuario;
        tipo_consulta = NUMERO_POR_USUARIO;
        return construir_parametros(aux);
    }

    public ArrayList<Chat_asesoria> generar_json(String respuesta)
    {
        ArrayList<Chat_asesoria> lista_elementos = new ArrayList<>();
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

    private Chat_asesoria agregar_elemento(final JsonObject jsonObject)
    {
        return new Chat_asesoria(){{
            try {
                id_chat_asesoria = jsonObject.get(ID_CHAT_ASESORIA).getAsInt();
                fecha_inicio_asesoria = jsonObject.get(FECHA_INICIO_ASESORIA).getAsString();
                hora_inicio_asesoria = jsonObject.get(HORA_INICIO_ASESORIA).getAsString();
                if(!jsonObject.get(FECHA_CIERRE_ASESORIA).isJsonNull())
                {
                    fecha_cierre_asesoria = jsonObject.get(FECHA_CIERRE_ASESORIA).getAsString();
                }
                else
                {
                    fecha_cierre_asesoria = "";
                }
                if(!jsonObject.get(HORA_CIERRA_CHAT_ASESORIA).isJsonNull())
                {
                    hora_cierra_chat_asesoria = jsonObject.get(HORA_CIERRA_CHAT_ASESORIA).getAsString();
                }
                else
                {
                    hora_cierra_chat_asesoria = "";
                }
                administrador_chat_asesoria = jsonObject.get(ADMINISTRADOR_CHAT_ASESORIA).getAsInt();
                usuario_chat_asesoria = jsonObject.get(USUARIO_CHAT_ASESORIA).getAsInt();
                estado_cerrado = jsonObject.get(ESTADO_CERRADO).getAsInt();
                tiempo_sesion_chat_asesoria = jsonObject.get(TIEMPO_SESION_CHAT_ASESORIA).getAsString();
                especializacion_chat_asesoria = jsonObject.get(ESPECIALIZACION_CHAT_ASESORIA).getAsInt();
                if(!jsonObject.get(ULTIMA_FECHA_ADMINISTRADOR_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_fecha_administrador_chat_asesoria = jsonObject.get(ULTIMA_FECHA_ADMINISTRADOR_CHAT_ASESORIA).getAsString();
                }
                else
                {
                    ultima_fecha_administrador_chat_asesoria = "-1";
                }
                if(!jsonObject.get(ULTIMA_HORA_ADMINISTRADOR_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_hora_administrador_chat_asesoria = jsonObject.get(ULTIMA_HORA_ADMINISTRADOR_CHAT_ASESORIA).getAsString();
                }
                else
                {
                    ultima_hora_administrador_chat_asesoria = "-1";
                }
                if(!jsonObject.get(ULTIMO_MENSAJE_ADMINISTRADOR_CHAT_ASESORIA).isJsonNull())
                {
                    ultimo_mensaje_administrador_chat_asesoria = jsonObject.get(ULTIMO_MENSAJE_ADMINISTRADOR_CHAT_ASESORIA).getAsString();
                }
                if(!jsonObject.get(ULTIMA_FECHA_USUARIO_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_fecha_usuario_chat_asesoria = jsonObject.get(ULTIMA_FECHA_USUARIO_CHAT_ASESORIA).getAsString();
                }
                else
                {
                    ultima_fecha_usuario_chat_asesoria = "-1";
                }
                if(!jsonObject.get(ULTIMA_HORA_USUARIO_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_hora_usuario_chat_asesoria = jsonObject.get(ULTIMA_HORA_USUARIO_CHAT_ASESORIA).getAsString();
                }
                else
                {
                    ultima_hora_usuario_chat_asesoria = "-1";
                }
                if(!jsonObject.get(ULTIMO_MENSAJE_USUARIO_CHAT_ASESORIA).isJsonNull())
                {
                    ultimo_mensaje_usuario_chat_asesoria = jsonObject.get(ULTIMO_MENSAJE_USUARIO_CHAT_ASESORIA).getAsString();
                }
                if(!jsonObject.get(ULTIMA_FECHA_VISTA_ADMINISTRADOR_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_fecha_vista_administrador_chat_asesoria = jsonObject.get(ULTIMA_FECHA_VISTA_ADMINISTRADOR_CHAT_ASESORIA).getAsString();
                }
                else
                {
                    ultima_fecha_vista_administrador_chat_asesoria = "-1";
                }
                if(!jsonObject.get(ULTIMA_HORA_VISTA_ADMINISTRADOR_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_hora_vista_administrador_chat_asesoria = jsonObject.get(ULTIMA_HORA_VISTA_ADMINISTRADOR_CHAT_ASESORIA).getAsString();
                }
                else
                {
                    ultima_hora_vista_administrador_chat_asesoria = "-1";
                }
                if(!jsonObject.get(ULTIMA_FECHA_VISTA_USUARIO_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_fecha_vista_usuario_chat_asesoria = jsonObject.get(ULTIMA_FECHA_VISTA_USUARIO_CHAT_ASESORIA).getAsString();
                }
                else
                {
                    ultima_fecha_vista_usuario_chat_asesoria = "-1";
                }
                if(!jsonObject.get(ULTIMA_HORA_VISTA_USUARIO_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_hora_vista_usuario_chat_asesoria = jsonObject.get(ULTIMA_HORA_VISTA_USUARIO_CHAT_ASESORIA).getAsString();
                }
                else
                {
                    ultima_hora_vista_usuario_chat_asesoria = "-1";
                }
                if(!jsonObject.get(USUARIO).isJsonNull())
                {
                    usuario = jsonObject.get(USUARIO).getAsString();
                }
                if(!jsonObject.get(ADMINISTRADOR).isJsonNull())
                {
                    administrador = jsonObject.get(ADMINISTRADOR).getAsString();
                }
                if(!jsonObject.get(ESPECIALIDAD).isJsonNull())
                {
                    especialidad = jsonObject.get(ESPECIALIDAD).getAsString();
                }
                if(!jsonObject.get(ULTIMO_MENSAJE_CHAT_ASESORIA).isJsonNull())
                {
                    ultimo_mensaje_chat_asesoria = jsonObject.get(ULTIMO_MENSAJE_CHAT_ASESORIA).getAsString();
                }
                if(!jsonObject.get(ULTIMA_FECHA_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_fecha_chat_asesoria = jsonObject.get(ULTIMA_FECHA_CHAT_ASESORIA).getAsString();
                }
                if(!jsonObject.get(ULTIMA_HORA_CHAT_ASESORIA).isJsonNull())
                {
                    ultima_hora_chat_asesoria = jsonObject.get(ULTIMA_HORA_CHAT_ASESORIA).getAsString();
                }
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }};
    }

    private HashMap<String,String> construir_parametros(Chat_asesoria elemento)
    {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty(ID_CHAT_ASESORIA, elemento.id_chat_asesoria);
            obj.addProperty(FECHA_INICIO_ASESORIA, elemento.fecha_inicio_asesoria);
            obj.addProperty(HORA_INICIO_ASESORIA, elemento.hora_inicio_asesoria);
            obj.addProperty(FECHA_CIERRE_ASESORIA, elemento.fecha_cierre_asesoria);
            obj.addProperty(HORA_CIERRA_CHAT_ASESORIA, elemento.hora_cierra_chat_asesoria);
            obj.addProperty(ADMINISTRADOR_CHAT_ASESORIA, elemento.administrador_chat_asesoria);
            obj.addProperty(USUARIO_CHAT_ASESORIA, elemento.usuario_chat_asesoria);
            obj.addProperty(ESTADO_CERRADO, elemento.estado_cerrado);
            obj.addProperty(TIEMPO_SESION_CHAT_ASESORIA, elemento.tiempo_sesion_chat_asesoria);
            obj.addProperty(ESPECIALIZACION_CHAT_ASESORIA, elemento.especializacion_chat_asesoria);
            obj.addProperty(TIPO_CONSULTA,tipo_consulta);
            obj.addProperty(LLAVE_WS,LLAVE_CHAT_ASESORIA);
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

    public static class listaChatNoVisto
    {
        private static ArrayList<Chat_asesoria> chat_asesoriasNoVisto = new ArrayList<>();
        private static int numChatNoVisto;

        static void agregarChatNoVisto(Chat_asesoria chat_asesoria)
        {
            for(Chat_asesoria item : chat_asesoriasNoVisto)
            {
                if(item.id_chat_asesoria == chat_asesoria.id_chat_asesoria)
                {
                    return;
                }
            }
            chat_asesoriasNoVisto.add(chat_asesoria);
            numChatNoVisto ++;
        }

        static void quitarChatNoVisto(Chat_asesoria chat_asesoria)
        {
            int pos = -1;
            int indice = 0;
            for(Chat_asesoria item : chat_asesoriasNoVisto)
            {
                if(item.id_chat_asesoria == chat_asesoria.id_chat_asesoria)
                {
                    pos = indice;
                }
                indice ++;
            }
            if(pos != -1)
            {
                chat_asesoriasNoVisto.remove(pos);
                numChatNoVisto --;
            }
        }

        static Chat_asesoria existeChatNoVisto(Chat_asesoria chat_asesoria)
        {
            for(Chat_asesoria item : chat_asesoriasNoVisto)
            {
                if(item.id_chat_asesoria == chat_asesoria.id_chat_asesoria)
                {
                    return item;
                }
            }
            return null;
        }

        static void quitarTodosChatNoVisto()
        {
            chat_asesoriasNoVisto = new ArrayList<>();
            numChatNoVisto = 0;
        }
    }

    public static void setChat_asesorias(ArrayList<Chat_asesoria> chat_asesorias_aux)
    {
        boolean cambio = false;
        if(chat_asesorias_aux != null)
        {
            if(chat_asesorias != null)
            {
                for(Chat_asesoria item :  chat_asesorias_aux)
                {
                    Chat_asesoria chat_asesoria = buscarChatAsesoria(item.id_chat_asesoria);
                    if(chat_asesoria != null)
                    {
                        try
                        {
                            if(!chat_asesoria.ultima_fecha_chat_asesoria.equals(item.ultima_fecha_chat_asesoria) || !chat_asesoria.ultima_hora_chat_asesoria.equals(item.ultima_hora_chat_asesoria))
                            {
                                cambio = true;
                                listaChatNoVisto.agregarChatNoVisto(item);
                                /**
                                 *  si chat.fechaVista < ahora()
                                        si vistaChat.estaAbierto
                                            listaChatNoVisto.quitarTodosChatNoVisto()
                                        else
                                            ListaChatNoVistp.agregarChatNoVisto()
                                        fin_si
                                   fin_si

                                 */
                            }
                        }
                        catch(NullPointerException exc)
                        {

                        }
                    }
                }
            }
            chat_asesorias = new ArrayList<>();
            for(Chat_asesoria item : chat_asesorias_aux)
            {
                addChat_asesoria(item);
            }
            if(cambio)
            {
                if(arrayChatCambiado != null && Gestion_usuario.getUsuario_online() != null)
                {
                    arrayChatCambiado.chatCambiado();
                }
            }
        }
        else
        {
            chat_asesorias = null;
        }
    }

    public static void addChat_asesoria(Chat_asesoria chat_asesoria) {
        if (buscarChatAsesoria(chat_asesoria.id_chat_asesoria) == null) {
            chat_asesorias.add(chat_asesoria);
        }
    }
    public static ArrayList<Chat_asesoria> getChat_asesorias()
    {
        return chat_asesorias;
    }

    public static void limpiarChatAsesoria()
    {
        chat_asesorias.clear();
        chat_asesorias = null;
    }

    private static Chat_asesoria buscarChatAsesoria(int id)
    {
        if(chat_asesorias != null)
        {
            for(Chat_asesoria item : chat_asesorias)
            {
                if(item.id_chat_asesoria == id)
                {
                    return item;
                }
            }
        }
        return  null;
    }
}
