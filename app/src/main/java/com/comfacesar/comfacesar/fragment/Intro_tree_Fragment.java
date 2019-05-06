package com.comfacesar.comfacesar.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.comfacesar.comfacesar.R;
import com.comfacesar.comfacesar.SplashScreenActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class Intro_tree_Fragment extends Fragment {

    private View view;
    private Button button;

    public Intro_tree_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_intro_tree_, container, false);
        button= view.findViewById(R.id.id_boton_tree);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "presiono boton", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent (getActivity(), SplashScreenActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
