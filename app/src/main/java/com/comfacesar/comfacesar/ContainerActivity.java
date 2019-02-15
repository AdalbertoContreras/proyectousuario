package com.comfacesar.comfacesar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import com.comfacesar.comfacesar.adapterViewpager.MyPagerAdapter;
import com.example.extra.Config;

public class ContainerActivity extends AppCompatActivity {

    public static  Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container2);
        bundle = savedInstanceState;
        new Config().iniciar_config(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("ServiAmigo");
        toolbar.setBackgroundResource(R.color.Gris3);
        setSupportActionBar(toolbar);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), getBaseContext());
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //añadir color  a menu
        for(int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            spanString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.Gris)), 0,     spanString.length(), 0); //fix the color to white
            item.setTitle(spanString);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent = new Intent(ContainerActivity.this, ContainertwoActivity.class);;
        boolean selecionado = false;
        //Fragment mifragment=null;
        switch (item.getItemId())
        {
            case R.id.historialAlertasMenu:
                intent.putExtra("id",1);
                selecionado = true;
                break;

            case  R.id.historialAsesoriasMenu:
                intent.putExtra("id",2);
                selecionado = true;
                break;
            case  R.id.iniciarSesionMenu:
                intent.putExtra("id",3);
                selecionado = true;
                break;
            case  R.id.registrarmeMenu:
                intent.putExtra("id",4);
                selecionado = true;
                break;
            case  R.id.acercaDeMenu:
                intent.putExtra("id",5);
                selecionado = true;
                break;
        }
        if(selecionado)
        {
            startActivity(intent);
        }
        return true;
    }
}




