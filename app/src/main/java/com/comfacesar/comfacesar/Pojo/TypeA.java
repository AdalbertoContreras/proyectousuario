package com.comfacesar.comfacesar.Pojo;

import com.comfacesar.comfacesar.Interface.ListItem;


public class TypeA implements ListItem {



    public int imagen;


    public String text;


    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }



    public TypeA(int imagen,String text) {
        this.imagen=imagen;
        this.text = text;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    @Override
    public int getListItemType() {
        // retorna 1
        return ListItem.TYPE_A;
    }
}
