package com.comfacesar.comfacesar.Adaptador;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.comfacesar.comfacesar.ClaseAbstracta.ViewHolder;
import com.comfacesar.comfacesar.Interface.ListItem;
import com.comfacesar.comfacesar.Pojo.TypeA;
import com.comfacesar.comfacesar.Pojo.TypeB;
import com.comfacesar.comfacesar.Pojo.TypeC;
import com.comfacesar.comfacesar.R;

import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter<ViewHolder> {


     List<ListItem> mItems;

    public ExampleAdapter( List<ListItem> items) {

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

        View view;

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


            public ViewHolderA(@NonNull View itemView) {
                super(itemView);

                mTextView= itemView.findViewById(R.id.idDatos);
                imagen= itemView.findViewById(R.id.id_imagenA);
            }

            @Override
            public void bindType(ListItem item) {

                mTextView.setText((CharSequence) ((TypeA)item).getText());
                imagen.setImageResource(((TypeA)item).getImagen());


            }
        }


    public class ViewHolderB extends ViewHolder {

        private final TextView mTextView2;
        private final TextView mTextView3;
        private final ImageView imagen;

        public ViewHolderB(@NonNull View itemView) {
            super(itemView);

            mTextView2= itemView.findViewById(R.id.idDatos2);
            mTextView3= itemView.findViewById(R.id.idDatos3);
            imagen= itemView.findViewById(R.id.id_imagen);
        }

        @Override
        public void bindType(ListItem item) {

            mTextView2.setText(((TypeB)item).getText());
            mTextView3.setText(((TypeB)item).getTexto2B());

            imagen.setImageResource(((TypeB)item).getImagen());

        }
    }


    public class ViewHolderC extends ViewHolder {

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
        }

        @Override
        public void bindType(ListItem item) {

            mText.setText(((TypeC)item).getText());
            imagen.setImageResource(((TypeC)item).getImagen());
            mText2.setText(((TypeC)item).getText2());
            imagen2.setImageResource(((TypeC)item).getImagen2());


        }


    }


}
