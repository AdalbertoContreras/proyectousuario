package com.comfacesar.comfacesar.Pojo;

import com.comfacesar.comfacesar.Interface.ListItem;
import com.example.modelo.Imagen_noticia;
import com.example.modelo.Noticia;


public class TypeC implements ListItem {
    private String  imagen;
    private String text;

    private String imagen2;
    private String text2;

    private Imagen_noticia imagen_noticia1;
    private Imagen_noticia imagen_noticia2;
    private Noticia noticia1;
    private Noticia noticia2;

    public TypeC(Noticia noticia1, Imagen_noticia imagen_noticia1, Noticia noticia2, Imagen_noticia imagen_noticia2) {
        this.imagen = imagen_noticia1.url_imagen_noticia;
        this.text = noticia1.titulo_noticia;
        this.imagen2 = imagen_noticia2.url_imagen_noticia;
        this.text2 = noticia2.titulo_noticia;
        this.noticia1 = noticia1;
        this.noticia2 = noticia2;
        this.imagen_noticia1 = imagen_noticia1;
        this.imagen_noticia2 = imagen_noticia2;
    }

    public TypeC(Noticia noticia1, Noticia noticia2, Imagen_noticia imagen_noticia2) {
        this.imagen = "";
        this.text = noticia1.titulo_noticia;
        this.imagen2 = imagen_noticia2.url_imagen_noticia;
        this.text2 = noticia2.titulo_noticia;
        this.noticia1 = noticia1;
        this.noticia2 = noticia2;
        this.imagen_noticia1 = null;
        this.imagen_noticia2 = imagen_noticia2;
    }

    public TypeC(Noticia noticia1, Imagen_noticia imagen_noticia1, Noticia noticia2) {
        this.imagen = imagen_noticia1.url_imagen_noticia;
        this.text = noticia1.titulo_noticia;
        this.imagen2 = "";
        this.text2 = noticia2.titulo_noticia;
        this.noticia1 = noticia1;
        this.noticia2 = noticia2;
        this.imagen_noticia1 = imagen_noticia1;
        this.imagen_noticia2 = null;
    }

    public TypeC(Noticia noticia1, Noticia noticia2) {
        this.imagen = "";
        this.text = noticia1.titulo_noticia;
        this.imagen2 = "";
        this.text2 = noticia2.titulo_noticia;
        this.noticia1 = noticia1;
        this.noticia2 = noticia2;
        this.imagen_noticia1 = null;
        this.imagen_noticia2 = null;
    }

    public Imagen_noticia getImagen_noticia1() {
        return imagen_noticia1;
    }

    public Imagen_noticia getImagen_noticia2() {
        return imagen_noticia2;
    }

    public Noticia getNoticia1() {
        return noticia1;
    }

    public Noticia getNoticia2() {
        return noticia2;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getImagen2() {
        return imagen2;
    }

    public void setImagen2(String imagen2) {
        this.imagen2 = imagen2;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int getListItemType() {
        return ListItem.VIOLENCIA_DE_GENERO;
    }
}
