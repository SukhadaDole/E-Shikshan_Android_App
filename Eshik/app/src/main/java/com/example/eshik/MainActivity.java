package com.example.eshik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void nextActivity(View view)
    {

        Intent intent=new Intent(this, com.example.eshik.navigate.class);
        startActivity(intent);




    }

}
