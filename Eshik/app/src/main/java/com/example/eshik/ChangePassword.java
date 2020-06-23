package com.example.eshik;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePassword extends AppCompatActivity {
    Button b1;
    EditText e1,e2;
Intent intent;
CardView c1;
String user,pass;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference mroot=firebaseDatabase.getReference("Login");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);

        c1=findViewById(R.id.cardView2);
        b1 = findViewById(R.id.done);
        e1 = findViewById(R.id.changetext);
        e2= findViewById(R.id.conpass);
        intent=new Intent(this, com.example.eshik.NewPassword.class);

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                user=e1.getText().toString();
                pass=e2.getText().toString();




                mroot.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) { int f=0;String username=null,password=null;
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                             username=dataSnapshot1.child("username").getValue().toString();
                             password=dataSnapshot1.child("password").getValue().toString();
                            // Toast.makeText(UserActivity.this, username+":"+password, Toast.LENGTH_SHORT).show();
                            if(user.contains(username)&&pass.contains(password))
                            {

                                //startActivity(new Intent(ChangePassword.this,New_password.class));
                                f=1;
                                Toast.makeText(ChangePassword.this, "Valid", Toast.LENGTH_SHORT).show();
                                user=user.replace(" ","");

                               intent.putExtra("name",user);
                                startActivity(intent);
                            }
                        }
                     if(f==0)
                        {
                            Toast.makeText(ChangePassword.this, "Invalid", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                e1.setText("");
                e2.setText("");
            }
        });
    }
}
