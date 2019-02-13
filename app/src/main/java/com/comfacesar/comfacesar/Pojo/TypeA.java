package com.comfacesar.comfacesar.Pojo;

import com.comfacesar.comfacesar.Interface.ListItem;
import com.example.modelo.Imagen_noticia;
import com.example.modelo.Noticia;


public class TypeA implements ListItem {
    private String imagen;
    private String text;
    private Noticia noticia;
    private Imagen_noticia imagen_noticia;

    public TypeA(Noticia noticia, Imagen_noticia imagen_noticia) {
        this.imagen=imagen_noticia.url_imagen_noticia;
        this.text = noticia.titulo_noticia;
        this.noticia =  noticia;
        this.imagen_noticia = imagen_noticia;
    }

    public TypeA(Noticia noticia) {
        this.imagen="";
        this.text = noticia.titulo_noticia;
        this.noticia =  noticia;
        imagen_noticia = null;
    }

    public Noticia getNoticia() {
        return noticia;
    }

    public Imagen_noticia getImagen_noticia() {
        return imagen_noticia;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public int getListItemType() {
        // retorna 1
        return ListItem.TYPE_A;
    }
}
