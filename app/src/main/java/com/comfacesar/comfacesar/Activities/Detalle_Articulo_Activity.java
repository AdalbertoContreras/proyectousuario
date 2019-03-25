package com.comfacesar.comfacesar.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.Item.ItemNoticia;
import com.comfacesar.comfacesar.R;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_me_gusta_noticia;
import com.example.gestion.Gestion_noticia;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Me_gusta_noticia;
import com.example.modelo.Noticia;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class Detalle_Articulo_Activity extends AppCompatActivity {

    private TextView tituto_textview;
    private ImageView imagen_noticiaImageView;
    private TextView contenido_TextView;
    private CheckBox megusta_CheckBox;
    private TextView numero_megusta_TextView;
    private TextView fecha_textView_itemNoticia;
    private int id_noticia;
    private boolean cambiando_estado;
    private int categoria;
    private TextView categoriaNoticiaTextView;
    public static EscuchadoMeGusta escuchadoMeGusta;
    public interface EscuchadoMeGusta
    {
        void meMeGusta();
    }
    public Detalle_Articulo_Activity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__articulo_);

        ShowToolbar("",true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade());
        }
        tituto_textview = findViewById(R.id.titulo_textView_itemNoticia);
        imagen_noticiaImageView = findViewById(R.id.imagen_imageView_itemNoticia);
        contenido_TextView = findViewById(R.id.contenido_textView_itemNoticia);
        megusta_CheckBox = findViewById(R.id.me_gusta_checkBox_itemNoticia);
        numero_megusta_TextView = findViewById(R.id.me_gusta_textView_itemNoticia);
        fecha_textView_itemNoticia = findViewById(R.id.fecha_textView_itemNoticia);
        categoriaNoticiaTextView = findViewById(R.id.categoriaTextView);
        id_noticia = getIntent().getExtras().getInt("id_noticia");
        tituto_textview.setText(getIntent().getStringExtra("id_noticia"));
        tituto_textview.setText(getIntent().getStringExtra("titulo"));
        contenido_TextView.setText(getIntent().getStringExtra("contenido"));
        fecha_textView_itemNoticia.setText(getIntent().getStringExtra("fecha") + " " + getIntent().getStringExtra("hora") );
        String url = getIntent().getStringExtra("imagen");
        if(url.length() > 1)
        {
            Picasso.with(getBaseContext()).load(getIntent().getStringExtra("imagen")).placeholder(R.drawable.perfil2)
                    .error(R.drawable.perfil2).into(imagen_noticiaImageView);
        }

        categoria = getIntent().getExtras().getInt("categoria");
        consultar_noticia();
        switch (categoria)
        {
            case 1:
                categoriaNoticiaTextView.setBackgroundResource(R.color.colorPrimaryDark);
                categoriaNoticiaTextView.setText("SEXUALIDAD");
                break;
            case 2:
                categoriaNoticiaTextView.setBackgroundResource(R.color.tipo_one);
                categoriaNoticiaTextView.setText("EMBARAZO");
                break;
            case 3:
                categoriaNoticiaTextView.setBackgroundResource(R.color.colorPrimaryDark);
                categoriaNoticiaTextView.setText("MALTRATO");
                break;
            case 4:
                categoriaNoticiaTextView.setBackgroundResource(R.color.Gris3);
                categoriaNoticiaTextView.setText("IDENTIDAD");
                break;
        }
        if(Gestion_usuario.getUsuario_online() != null)
        {
            megusta_CheckBox.setEnabled(true);
            tengo_me_gusta();
            megusta_CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(Gestion_usuario.getUsuario_online() != null && !cambiando_estado)
                    {
                        HashMap<String,String> params = new Gestion_me_gusta_noticia().dar_me_gusta(id_noticia, Gestion_usuario.getUsuario_online().id_usuario);
                        Response.Listener<String> stringListener = new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                consultar_noticia();
                                escuchadoMeGusta.meMeGusta();
                            }
                        };
                        Response.ErrorListener errorListener = new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                tengo_me_gusta();
                            }
                        };
                        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
                        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
                    }
                }
            });
        }
    }

    private void consultar_noticia()
    {
        HashMap<String,String> params = new Gestion_noticia().noticia_por_id(id_noticia);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                if(response != "")
                {
                    ArrayList<Noticia> noticias = new Gestion_noticia().generar_json(response);
                    if(!noticias.isEmpty())
                    {
                        numero_megusta_TextView.setText(noticias.get(0).numero_me_gusta + " me gusta");
                    }
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
    }

    private void tengo_me_gusta()
    {
        HashMap<String,String> params = new Gestion_me_gusta_noticia().me_gusta_por_noticia_y_usuario(Gestion_usuario.getUsuario_online().id_usuario, id_noticia);
        Log.d("Response", params.toString());

        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                ArrayList<Me_gusta_noticia> me_gusta_noticiaArraYList = new Gestion_me_gusta_noticia().generar_json(response);
                cambiando_estado = true;
                if(me_gusta_noticiaArraYList.isEmpty())
                {
                    megusta_CheckBox.setChecked(false);
                }
                else
                {
                    megusta_CheckBox.setChecked(true);
                }
                cambiando_estado = false;
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
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
