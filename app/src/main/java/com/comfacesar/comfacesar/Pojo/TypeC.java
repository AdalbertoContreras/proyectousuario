package com.comfacesar.comfacesar.Pojo;

import com.comfacesar.comfacesar.Interface.ListItem;


public class TypeC implements ListItem {

    public int imagen;
    public String text;

    public int imagen2;
    public String text2;

    public TypeC(int imagen, String text, int imagen2, String text2) {
        this.imagen = imagen;
        this.text = text;
        this.imagen2 = imagen2;
        this.text2 = text2;
    }

    public int getImagen2() {
        return imagen2;
    }

    public void setImagen2(int imagen2) {
        this.imagen2 = imagen2;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }




    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

   //////

    @Override
    public int getListItemType() {
        return ListItem.TYPE_C;
    }
}
