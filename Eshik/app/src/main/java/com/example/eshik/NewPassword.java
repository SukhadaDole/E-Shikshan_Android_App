package com.example.eshik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewPassword extends AppCompatActivity {

    TextView e1;
    EditText e2, e3;
    Button b1;
    String user;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mroot = firebaseDatabase.getReference("Login");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password2);

        Intent intent = getIntent();
        user = intent.getStringExtra("name");

        e1 = findViewById(R.id.textView4);
        e2 = findViewById(R.id.changetext);
        e3 = findViewById(R.id.conpass);
        b1 = findViewById(R.id.button7);
        e1.setText(user);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newpass = e2.getText().toString();
                String conpass = e3.getText().toString();
                if (newpass.equals(conpass)) {
                    mroot.child(user).removeValue();
                    Login l = new Login(user, newpass);
                    mroot.child(user).setValue(l);
                    Toast.makeText(NewPassword.this, "PassWord Changed Successfully !!!", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(NewPassword.this,UserActivity.class));
                }
                else {
                    e2.setText("");
                    e3.setText("");
                    Toast.makeText(NewPassword.this, "Password Doesnot Match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}