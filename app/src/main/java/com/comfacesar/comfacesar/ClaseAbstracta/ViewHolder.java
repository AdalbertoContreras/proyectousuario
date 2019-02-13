package com.comfacesar.comfacesar.ClaseAbstracta;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.comfacesar.comfacesar.Interface.ListItem;


public abstract class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bindType(ListItem item);


}
