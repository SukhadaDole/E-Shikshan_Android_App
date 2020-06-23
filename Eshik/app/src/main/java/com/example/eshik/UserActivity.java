package com.example.eshik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eshik.ChangePassword;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity  {

    Button b1,b2;
    EditText e1,e2;
    TextView e3;
    String user,pass;
  private  FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
  private DatabaseReference mroot=firebaseDatabase.getReference("Login");
/*  private DatabaseReference userref=mroot.child("username");
  private DatabaseReference passref=mroot.child("password");*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);



        b1 = findViewById(R.id.loginbtn);
        b2 = findViewById(R.id.loginbtn2);
        e1 = findViewById(R.id.changetext);
        e2= findViewById(R.id.conpass);
        e3= findViewById(R.id.changetxt);
        e3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserActivity.this, ChangePassword.class));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserActivity.this,Register.class));
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user=e1.getText().toString();
                pass=e2.getText().toString();
                user=user.replace(" ","");
                pass=pass.replace(" ","");

                mroot.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) { int f=0;
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            String username=dataSnapshot1.child("username").getValue().toString();
                            String password=dataSnapshot1.child("password").getValue().toString();
                           // Toast.makeText(UserActivity.this, username+":"+password, Toast.LENGTH_SHORT).show();
                              if(user.contains(username)&&pass.contains(password))
                              {               startActivity(new Intent(UserActivity.this,Notify.class));
                                 // Toast.makeText(UserActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                  f=1;
                              }


                        }

                        if(f==0)
                        {
                            Toast.makeText(UserActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                  /* Login l=new Login(user,pass);
                    mroot.child(username).setValue(l);*/
                });


                e1.setText("");
                e2.setText("");

            }
        });

    }

}
