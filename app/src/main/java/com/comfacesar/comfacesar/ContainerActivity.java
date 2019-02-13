package com.comfacesar.comfacesar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.comfacesar.comfacesar.adapterViewpager.MyPagerAdapter;
import com.comfacesar.comfacesar.fragment.AlertTempranaFragment;
import com.comfacesar.comfacesar.fragment.UbicacionFragment;
import com.roughike.bottombar.BottomBar;

public class ContainerActivity extends AppCompatActivity {

    public ViewPager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container2);



        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("ServiAmigo");

        toolbar.setBackgroundResource(R.color.Gris3);
        setSupportActionBar(toolbar);


        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(myPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);


       /* bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {

                switch (tabId)
                {

                    case R.id.id_AlerTemprana:
                        AlertTempranaFragment alerta=new AlertTempranaFragment();

                        //getSupportFragmentManager().beginTransaction().replace(R.id.container,alerta)
                               // .setTransition(FragmentTransaction.TRANSIT_EXIT_MASK).addToBackStack(null).commit();


                        FragmentManager fragmentManager = getSupportFragmentManager();

                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        //CustomFragment newCustomFragment = CustomFragment.newInstance();
                        transaction.replace(R.id.container, alerta );
                        transaction.addToBackStack(null);
                        transaction.commit();


                        break;

                    case   R.id.id_Home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();

                        FragmentManager fragmentManager2 = getSupportFragmentManager();

                        FragmentTransaction transaction2 = fragmentManager2.beginTransaction();
                        transaction2.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        //CustomFragment newCustomFragment = CustomFragment.newInstance();
                        transaction2.replace(R.id.container, new HomeFragment() );
                        transaction2.addToBackStack(null);
                        transaction2.commit();

                        break;

                    case   R.id.id_Ubicacion:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.container,new UbicacionFragment())
                                //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();

                        FragmentManager fragmentManager3 = getSupportFragmentManager();

                        FragmentTransaction transaction3 = fragmentManager3.beginTransaction();
                        transaction3.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        //CustomFragment newCustomFragment = CustomFragment.newInstance();
                        transaction3.replace(R.id.container, new UbicacionFragment()) ;
                        transaction3.addToBackStack(null);
                        transaction3.commit();


                        break;

                }
            }
        });*/





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
        Intent intent;
        //Fragment mifragment=null;
        switch (item.getItemId())
        {
            case R.id.historialAlertasMenu:
                intent = new Intent(ContainerActivity.this, ContainertwoActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);
                break;

            case  R.id.historialAsesoriasMenu:
                intent = new Intent(ContainerActivity.this, ContainertwoActivity.class);
                intent.putExtra("id",2);
                startActivity(intent);
                break;
            case  R.id.iniciarSesionMenu:
                intent = new Intent(ContainerActivity.this, ContainertwoActivity.class);
                intent.putExtra("id",3);
                startActivity(intent);
                break;
            case  R.id.registrarmeMenu:
                intent = new Intent(ContainerActivity.this, ContainertwoActivity.class);
                intent.putExtra("id",4);
                startActivity(intent);
                break;
            case  R.id.acercaDeMenu:
                intent = new Intent(ContainerActivity.this, ContainertwoActivity.class);
                intent.putExtra("id",5);
                startActivity(intent);
                break;
        }
        return true;
    }
}




