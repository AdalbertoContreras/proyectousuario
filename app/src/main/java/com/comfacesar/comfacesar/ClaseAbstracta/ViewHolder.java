package com.comfacesar.comfacesar.ClaseAbstracta;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.comfacesar.comfacesar.Interface.ListItem;
import com.comfacesar.comfacesar.Item.ItemNoticia;


public abstract class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bindType(ItemNoticia item);


}
