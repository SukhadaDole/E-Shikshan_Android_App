package com.example.eshik;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity0 extends Activity {
    Button about_course,about_certification,Sectors_in_python,login;
    TextView txt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity0);

        about_course=(Button) findViewById(R.id.button1);

        about_certification=(Button) findViewById(R.id.button2);
        Sectors_in_python=(Button) findViewById(R.id.button3);
        login=(Button) findViewById(R.id.button4);
        txt1 = findViewById(R.id.Textview1);

        about_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt1.setText("About course");

            }
        });
        about_certification.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       txt1.setText("About Certification");

                                                   }
                                               }
        );
        Sectors_in_python.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     txt1.setText("Sectors in Learning Python");

                                                 }
                                             }
        );
        login.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         openActivity1();
                                     }
                                 }
        );


    }
    public void openActivity1() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }  }