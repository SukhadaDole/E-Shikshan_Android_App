package com.example.eshik;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Notify extends AppCompatActivity {
    Button b1; String msg;
    EditText t1,t2;
    RequestQueue requestQueue;
    Editable messageToSend;
    Editable titleText;
    String URL="https://fcm.googleapis.com/fcm/send";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);


        t1=findViewById(R.id.NotifyBody);
        t2= findViewById(R.id.titleText);
        messageToSend= t1.getText();
        titleText= t2.getText();
        requestQueue = Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("Logged");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Notify", "Notify", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
//Notification will be send to only logged device
        FirebaseMessaging.getInstance().subscribeToTopic("Logged")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                    }
                });

        b1=findViewById(R.id.NotifyText);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t2.getText().equals("") || t1.getText().equals("")) {
                    Toast.makeText(Notify.this, "enter valid data", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendNotification();
                }
            }
        });


    }

    private void sendNotification() {

        // JSON object
        JSONObject jsonObject = new JSONObject();
        try {


            jsonObject.put("to", "/topics/" + "Logged");
            JSONObject notification = new JSONObject();
            notification.put("title", titleText);
            notification.put("body", messageToSend);
            jsonObject.put("notification", notification);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=AIzaSyB11EYg3Jk_h3-Ug3vrST0IhLytt-ao1hQ");
                    //  Toast.makeText(Notify.this, "header", Toast.LENGTH_SHORT).show();
                    return header;
                }

            };
            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Toast.makeText(this, "message send", Toast.LENGTH_SHORT).show();
    }
}

