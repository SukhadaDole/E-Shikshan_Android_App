package com.example.eshik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Syllabus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);


        final Context context=this;
        Button SE=(Button) findViewById(R.id.SE_syl);
        Button TE=(Button) findViewById(R.id.TE_syl);
        Button FE=(Button) findViewById(R.id.FE_syl);
        Button BE=(Button) findViewById(R.id.BE_syl);

        SE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://collegecirculars.unipune.ac.in/sites/documents/Syllabus%202016/SPPU_SE_Computer_Engg_2015_Course_Syllabus-4-7-16.pdf"));
                startActivity(intent);

            }
        });




        FE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://collegecirculars.unipune.ac.in/sites/documents/Syllabus%202019/First%20Year%20Engineering%202019%20Patt.Syllabus_05.072019.pdf"));
                startActivity(intent);

            }
        });

        TE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://collegecirculars.unipune.ac.in/sites/documents/Syllabus%202017/TE_Computer_Engg_Syllabus_2015_Course_10.072018.pdf"));
                startActivity(intent);

            }
        });
        BE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://engg.dypvp.edu.in/Downloads/BE-Syllabus/BE-Computer-2015.pdf"));

                startActivity(intent);

            }
        });



    }
}
