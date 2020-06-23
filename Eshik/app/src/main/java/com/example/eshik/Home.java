package com.example.eshik;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Home extends Fragment {


    Button b1, b2, b4, b3;
    View view;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home2, container, false);
        b1 = (Button) view.findViewById(R.id.ru);
        b2 = (Button) view.findViewById(R.id.syl);
        b3 = (Button) view.findViewById(R.id.upld);
        b4 = (Button) view.findViewById(R.id.ob);

        // addListenerOnButton(view);


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Syllabus.class);
                startActivity(intent);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), upload.class);
                startActivity(intent);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), viewFiles.class);
                startActivity(intent);
            }
        });

        /*b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(),nav33.class);
                startActivity(intent);
            }
        });*/




       b4.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               getFragmentManager().beginTransaction().replace(R.id.frame_container1,new nav33()).commit();
           }

           });
           /* public void onClick(View v){*/




        return view;
    }

}
