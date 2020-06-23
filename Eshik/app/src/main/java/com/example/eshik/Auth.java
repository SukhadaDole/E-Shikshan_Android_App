package com.example.eshik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Auth extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText mail,password;
    Button b,b2;
    ProgressBar load;
    Intent intent;
    FirebaseUser firebaseUser;
    FirebaseAuth mauth;
    ImageView image;
    String flag ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        Intent intent1 = getIntent();
        flag = intent1.getStringExtra("name");
        intent=new Intent(this,navigate.class);
        mail=findViewById(R.id.Amail);
        password=findViewById(R.id.Apassword);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        b=findViewById(R.id.Auth);

        load=findViewById(R.id.load1);
        mAuth=FirebaseAuth.getInstance();
        image=findViewById(R.id.Aimage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent Aintent = new Intent(getApplicationContext(),Register.class);
                startActivity(Aintent);
                finish();

            }
        });

        load.setVisibility(View.INVISIBLE);
        b.setVisibility(View.VISIBLE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load.setVisibility(View.VISIBLE);
                b.setVisibility(View.INVISIBLE);

                final String mail1 = mail.getText().toString();
                final String password1 = password.getText().toString();

                if(mail1.isEmpty() || password1.isEmpty())
                {
                    Toast.makeText(Auth.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    b.setVisibility(View.VISIBLE);
                    load.setVisibility(View.INVISIBLE);
                }
                else
                {
                    signIn(mail1,password1);
                }
            }
        });


    }

    private void signIn(String mail1, String password1) {
        mAuth.signInWithEmailAndPassword(mail1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                { if(mAuth.getCurrentUser().isEmailVerified())
                {
                    load.setVisibility(View.INVISIBLE);
                    b.setVisibility(View.VISIBLE);
                    updateUI();}

                else
                {
                    Toast.makeText(Auth.this, "Please verify your email ", Toast.LENGTH_SHORT).show();
                    load.setVisibility(View.INVISIBLE);
                    b.setVisibility(View.VISIBLE);
                }
                }

                else
                {
                    load.setVisibility(View.INVISIBLE);
                    b.setVisibility(View.VISIBLE);
                    Toast.makeText(Auth.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUI() {
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user=mAuth.getCurrentUser();
        if(user!= null && mAuth.getCurrentUser().isEmailVerified())
        {
            // user already connected so we need to redirect
            updateUI();
        }

    }


}
