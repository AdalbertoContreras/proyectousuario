package com.comfacesar.comfacesar.Pojo;

import com.comfacesar.comfacesar.Interface.ListItem;


public class TypeB implements ListItem {


    public int imagen;
    public String text;
    public String texto2B;

    public TypeB(int imagen,String texto2B,String text) {
        this.imagen=imagen;
        this.texto2B=texto2B;
        this.text = text;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
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
        return ListItem.TYPE_B;
    }
}
