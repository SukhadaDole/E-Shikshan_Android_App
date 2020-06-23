package com.example.eshik;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Password extends AppCompatActivity {
    EditText e1,e2;
    TextView t1;
    Button b1;
    String str;
    ImageView imageView;
    FirebaseAuth mauth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        e1=findViewById(R.id.password1);
        e2=findViewById(R.id.password2);
        progressDialog =new ProgressDialog(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        imageView=findViewById(R.id.uImg);
        Glide.with(this).load(user.getPhotoUrl()).into(imageView);
        mauth =FirebaseAuth.getInstance();
        t1=findViewById(R.id.uname);
        t1.setText("Hii !  " +user.getDisplayName());
        b1 =findViewById(R.id.change);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change();

            }
        });

    }

    public void change()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null)
        {    if(e1.getText().toString().equals(e2.getText().toString())) {
            progressDialog.setMessage("Please Wait ....");
            progressDialog.show();
            Toast.makeText(this, "" + e2.getText().toString(), Toast.LENGTH_SHORT).show();
            user.updatePassword(e2.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(Password.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                        mauth.signOut();
                        finish();
                        Intent in = new Intent(Password.this, Auth.class);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Password.this, "Password could not change", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(this, "Password Doesnot match", Toast.LENGTH_SHORT).show();
        }
        }

    }


}
