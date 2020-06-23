package com.example.eshik;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class nav11 extends Fragment {

Button b1;
    View view;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
  view=inflater.inflate(R.layout.nav1,container,false);

addListenerOnButton(view);

        return view;
    }
public void addListenerOnButton(View view)
{
    b1=(Button)view.findViewById(R.id.sem1);
   b1.setOnClickListener(new View.OnClickListener(){
       public void onClick(View v){
           getFragmentManager().beginTransaction().replace(R.id.frame_container1,new nav22()).commit();


       }
   });

}

}


