package com.comfacesar.comfacesar.Adaptador;


import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.comfacesar.comfacesar.ClaseAbstracta.ViewHolder;
import com.comfacesar.comfacesar.Interface.ListItem;
import com.comfacesar.comfacesar.Pojo.TypeA;
import com.comfacesar.comfacesar.Pojo.TypeB;
import com.comfacesar.comfacesar.Pojo.TypeC;
import com.comfacesar.comfacesar.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter<ViewHolder> {


    List<ListItem> mItems;
    View view;

    public ExampleAdapter(List<ListItem> items) {

        this.mItems = items;
    }

    ///////////////////////////////////////////
    @Override
    public int getItemViewType(int position) {

        return mItems.get(position).getListItemType();
    }

///////////////////////////////////////////////////////

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup contexto, int type) {

        // inflamos la vista y la enviamos al view holder
        switch (type)
        {
            case ListItem.TYPE_A:
                view=LayoutInflater.from(contexto.getContext()).inflate(R.layout.type_a,null,false);
                return new ViewHolderA(view);
            case   ListItem.TYPE_B:
                view= LayoutInflater.from(contexto.getContext()).inflate(R.layout.type_b,null,false);
                return new ViewHolderB(view);
            case ListItem.TYPE_C:
                view= LayoutInflater.from(contexto.getContext()).inflate(R.layout.type_c,null,false);
                return new ViewHolderC(view);
        }
        return null;
    }

    //////////////////////////////////////////
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ListItem item = mItems.get(i);
        viewHolder.bindType(item);
    }
    /////////////////////
    @Override
    public int getItemCount() {
        return mItems.size();
    }
    //////////////////////
    public class ViewHolderA extends ViewHolder {

        private final TextView mTextView;
        private final ImageView imagen;
        private View view;
        private FrameLayout frameLayout;
        public ViewHolderA(@NonNull View itemView) {
            super(itemView);
            mTextView= itemView.findViewById(R.id.idDatos);
            imagen= itemView.findViewById(R.id.id_imagenA);
            view = itemView;
            this.frameLayout = frameLayout;

        }

        @Override
        public void bindType(final ListItem item) {

            mTextView.setText(((TypeA)item).getText());
            Log.d("imagen" ,((TypeA) item).getImagen());
            if(((TypeA) item).getImagen() != "")
            {
                Picasso.with(view.getContext()).load(((TypeA) item).getImagen()).into(imagen);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((TypeA) item).getNoticia().tipo_creacion_noticia == 2)
                    {
                        Uri uri = Uri.parse("http://www.comfacesar.com/articulo.aspx?idc=" + ((TypeA) item).getNoticia().id_generacion_noticia);
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, uri);
                        ContextCompat.startActivity(view.getContext(),myIntent,null);
                    }
                }
            });
        }
    }


    public class ViewHolderB extends ViewHolder {

        private final TextView tituoTextView;
        private final TextView contenidoTextView;
        private final ImageView imagen;
        private View view;

        public ViewHolderB(@NonNull View itemView) {
            super(itemView);

            tituoTextView = itemView.findViewById(R.id.idDatos3);
            contenidoTextView = itemView.findViewById(R.id.idDatos2);
            imagen= itemView.findViewById(R.id.id_imagen);
            view = itemView;
        }

        @Override
        public void bindType(final ListItem item) {

            tituoTextView.setText(((TypeB)item).getText());
            contenidoTextView.setText(((TypeB)item).getTexto2B());
            if(((TypeB) item).getImagen() != "")
            {
                Picasso.with(view.getContext()).load(((TypeB) item).getImagen()).into(imagen);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((TypeB) item).getNoticia().tipo_creacion_noticia == 2)
                    {
                        Uri uri = Uri.parse("http://www.comfacesar.com/articulo.aspx?idc=" + ((TypeB) item).getNoticia().id_generacion_noticia);
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, uri);
                        ContextCompat.startActivity(view.getContext(),myIntent,null);
                    }
                }
            });
        }
    }


    public class ViewHolderC extends ViewHolder {
        private View view;
        private final TextView mText;
        private final ImageView imagen;

        private final TextView mText2;
        private final ImageView imagen2;

        public ViewHolderC(@NonNull View itemView) {
            super(itemView);
            mText= itemView.findViewById(R.id.id_textC);
            imagen= itemView.findViewById(R.id.id_imagenC);

            mText2= itemView.findViewById(R.id.id_textC2);
            imagen2= itemView.findViewById(R.id.id_imagenC2);
            view = itemView;
        }

        @Override
        public void bindType(final ListItem item) {
            mText2.setText(((TypeC)item).getText2());
            mText.setText(((TypeC)item).getText());
            if(((TypeC) item).getImagen2() != "")
            {
                Picasso.with(view.getContext()).load(((TypeC) item).getImagen2()).into(imagen2);
            }
            if(((TypeC) item).getImagen() != "")
            {
                Picasso.with(view.getContext()).load(((TypeC) item).getImagen()).into(imagen);
            }
            imagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((TypeC) item).getNoticia1().tipo_creacion_noticia == 2)
                    {
                        Uri uri = Uri.parse("http://www.comfacesar.com/articulo.aspx?idc=" + ((TypeC) item).getNoticia1().id_generacion_noticia);
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, uri);
                        ContextCompat.startActivity(view.getContext(),myIntent,null);
                    }
                }
            });
            imagen2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((TypeC) item).getNoticia2().tipo_creacion_noticia == 2)
                    {
                        Uri uri = Uri.parse("http://www.comfacesar.com/articulo.aspx?idc=" + ((TypeC) item).getNoticia2().id_generacion_noticia);
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, uri);
                        ContextCompat.startActivity(view.getContext(),myIntent,null);
                    }
                }
            });
        }
    }
}
