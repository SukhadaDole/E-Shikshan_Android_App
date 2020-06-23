package com.example.eshik;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class Rateus extends AppCompatActivity {

    Button button;
    RatingBar ratingStars;

    float myRating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rateus);

        button = (Button)findViewById(R.id.ratesss);
        ratingStars = findViewById(R.id.ratingBar);

        ratingStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                int rating = (int) v;
                String message = null;

                myRating =(float) ratingBar.getRating();

                switch (rating){

                    case 1:
                        message = "Sorry to hear that! :(";
                        break;

                    case 2:
                        message = "You always accept suggestion!";
                        break;

                    case 3:
                        message = "Good enough!";
                        break;

                    case 4:
                        message = "Great! Thank you!";
                        break;

                    case 5:
                        message = "Awesome! you are the best!";
                        break;
                }

                Toast.makeText(Rateus.this,message, Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Rateus.this,String.valueOf(myRating),Toast.LENGTH_SHORT).show();
            }
        });
    }
  /*  public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are You Sure You Want To Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       Rateus.super.onBackPressed();

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


    }*/
}

