package com.comfacesar.comfacesar.ViewPagerIntro;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.comfacesar.comfacesar.R;
import com.comfacesar.comfacesar.adapterViewpager.adapterViewpager;
import com.comfacesar.comfacesar.fragment.Intro_five_Fragment;
import com.comfacesar.comfacesar.fragment.Intro_four_Fragment;
import com.comfacesar.comfacesar.fragment.Intro_one_Fragment;
import com.comfacesar.comfacesar.fragment.Intro_tree_Fragment;
import com.comfacesar.comfacesar.fragment.Intro_two_Fragment;

import java.util.ArrayList;
import java.util.List;

public class ContedorIntro extends AppCompatActivity {

    private LinearLayout Dots_Layout;
    private ImageView [] dots;
    private List<Fragment> listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_contedor_intro);

        if(Build.VERSION.SDK_INT >=19)
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else
        {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ViewPager viewPager = findViewById(R.id.viewPager);


        listFragment = new ArrayList<>();
        listFragment.add(new Intro_one_Fragment());
        listFragment.add(new Intro_two_Fragment());
        listFragment.add(new Intro_tree_Fragment());
        listFragment.add(new Intro_four_Fragment());
        listFragment.add(new Intro_five_Fragment());


        Dots_Layout = findViewById(R.id.id_dotsLayout);

        adapterViewpager viewPagerAdapter = new adapterViewpager(getSupportFragmentManager(),listFragment);
        viewPager.setAdapter(viewPagerAdapter);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                CreateDots(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }

    });

}
    private void CreateDots(int curren_position)
    {
        if (Dots_Layout != null)
            Dots_Layout.removeAllViews();

        dots = new ImageView[listFragment.size()];

        for (int i = 0; i <listFragment.size(); i++) {

            dots[i] = new ImageView(this);

            if (i == curren_position) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_puntos));

            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_puntos_defaul));
            }

            // esta linea crea los margenes para cada shape
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);

            Dots_Layout.addView(dots[i], params);
        }


    }
}
