package com.comfacesar.comfacesar.Pojo;

import com.comfacesar.comfacesar.Interface.ListItem;
import com.example.modelo.Imagen_noticia;
import com.example.modelo.Noticia;


public class TypeB implements ListItem {
    private String  imagen;
    private String text;
    private String texto2B;
    private Noticia noticia;
    private Imagen_noticia imagen_noticia;

    public TypeB(Noticia noticia, Imagen_noticia imagen_noticia) {
        this.imagen=imagen_noticia.url_imagen_noticia;
        if(noticia.contenido_noticia.length() >= 200)
        {
            this.texto2B=noticia.contenido_noticia.substring(0,200) + "...";
        }
        else
        {
            this.texto2B= noticia.contenido_noticia + "...";
        }
        this.text = noticia.titulo_noticia;
        this.noticia = noticia;
        this.imagen_noticia =  imagen_noticia;
    }

    public TypeB(Noticia noticia) {
        this.imagen="";
        if(noticia.contenido_noticia.length() >= 200)
        {
            this.texto2B=noticia.contenido_noticia.substring(0,200) + "...";
        }
        else
        {
            this.texto2B= noticia.contenido_noticia + "...";
        }
        this.text = noticia.titulo_noticia;
        this.noticia = noticia;
        this.imagen_noticia =  null;
    }

    public Noticia getNoticia() {
        return noticia;
    }

    public Imagen_noticia getImagen_noticia() {
        return imagen_noticia;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTexto2B() {
        return texto2B;
    }

    public void setTexto2B(String texto2B) {
        this.texto2B = texto2B;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int getListItemType() {
        // retorna 2
        return ListItem.EMBARAZO;
    }
}
