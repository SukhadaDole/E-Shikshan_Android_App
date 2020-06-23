package com.example.eshik;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Linkplay extends AppCompatActivity {
    Button bt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkplay);

        findViewById(R.id.nptel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked_btn( "https://nptel.ac.in");
            }
        });


        findViewById(R.id.sololearn_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked_btn( "https://sololearn.com");
            }
        });



        findViewById(R.id.tutorialspoint_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked_btn( "https://tutorialspoint.com");



            }
        });

        bt = (Button) findViewById(R.id.button);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Linkplay.this,SecondActivity.class);
                startActivity(i);
            }
        });

    }




    public void clicked_btn(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
  /* public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are You Sure You Want To Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       Linkplay.super.onBackPressed();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    } */
}
