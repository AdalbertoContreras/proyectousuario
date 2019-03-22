package com.comfacesar.comfacesar;

import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Detalle_Articulo_Activity extends AppCompatActivity {
    private TextView tituto_textview;
    private ImageView imagen_noticiaImageView;
    private TextView contenido_TextView;
    private CheckBox megusta_CheckBox;
    private TextView numero_megusta_TextView;
    private TextView fecha_textView_itemNoticia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__articulo_);
        ShowToolbar("jean",true);
        Toolbar toolbar = findViewById(R.id.toolbar_detalle);



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade());
        }
        tituto_textview = findViewById(R.id.titulo_textView_itemNoticia);
        contenido_TextView = findViewById(R.id.contenido_textView_itemNoticia);
        numero_megusta_TextView = findViewById(R.id.me_gusta_textView_itemNoticia);
        megusta_CheckBox = findViewById(R.id.me_gusta_checkBox_itemNoticia);
        fecha_textView_itemNoticia = findViewById(R.id.fecha_textView_itemNoticia);
        imagen_noticiaImageView = findViewById(R.id.imagen_imageView_itemNoticia);
        getIntent().getStringExtra("nombre");
        tituto_textview.setText(getIntent().getStringExtra("titulo"));
        contenido_TextView.setText(getIntent().getStringExtra("contenido"));
        fecha_textView_itemNoticia.setText(getIntent().getStringExtra("fecha") + " " + getIntent().getStringExtra("hora"));
        Picasso.with(getBaseContext()).load(getIntent().getStringExtra("contenido")).into(imagen_noticiaImageView);


    }

    public void ShowToolbar(String Tittle, boolean upButton)
    {
        Toolbar toolbar = findViewById(R.id.toolbar_detalle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        CollapsingToolbarLayout collapsing=  findViewById(R.id.collapsin_id);
    }
}
