package com.comfacesar.comfacesar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class PruebaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_vacio);

    }

        public void goCreateAccount ( View view)
        {
            Intent intent= new Intent(this, ContainerActivity.class );
            startActivity(intent);
        }


    }
