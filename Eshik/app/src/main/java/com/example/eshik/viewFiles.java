package com.example.eshik;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class viewFiles extends AppCompatActivity {

    ListView filelistView;
    DatabaseReference databaseReference;
    List<ImageUpload> uploadfiles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_files);

        filelistView=(ListView)findViewById(R.id.mylistview);
        uploadfiles=new ArrayList<>();


        viewAllFiles();

        filelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageUpload imageUpload=uploadfiles.get(position);

                Intent intent=new Intent();
                intent.setType(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(imageUpload.getUrl()));
                startActivity(intent);

            }
        });




    }

    private void viewAllFiles() {

        databaseReference= FirebaseDatabase.getInstance().getReference("Uploads");
        //Toast.makeText(getApplicationContext(), "IN viewfiles", Toast.LENGTH_SHORT).show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    ImageUpload imageUpload=postSnapshot.getValue(ImageUpload.class);
                    uploadfiles.add(imageUpload);
                }
                String[] uploads=new String[uploadfiles.size()];
                for(int i=0;i<uploads.length;i++){
                    uploads[i]=uploadfiles.get(i).getName();
                }

                ArrayAdapter<String> adapter =new ArrayAdapter<String>(getApplicationContext(),R.id.row,R.id.textView,uploads)
                {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        View view=super.getView(position,convertView,parent);
                        // TextView mytext=(TextView)view.findViewById(android.R.id.);
                        TextView mytext=view.findViewById(android.R.id.text1);
                        mytext.setTextColor(Color.BLACK);


                        return view;
                    }
                };

                filelistView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
