package com.comfacesar.comfacesar.Item;

import com.comfacesar.comfacesar.Interface.ListItem;
import com.example.modelo.Imagen_noticia;
import com.example.modelo.Noticia;

public class ItemNoticia implements ListItem {
    private String imagen;
    private Noticia noticia;

    public ItemNoticia(Noticia noticia, Imagen_noticia imagen_noticia) {
        this.imagen=imagen_noticia.url_imagen_noticia;
        this.noticia = noticia;
    }

    public ItemNoticia(Noticia noticia) {
        this.imagen="";
        this.noticia = noticia;
    }

    public Noticia getNoticia() {
        return noticia;
    }

    public void setNoticia(Noticia noticia) {
        this.noticia = noticia;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getListItemType() {
        return noticia.categoria_noticia_manual_noticia;
    }
}
