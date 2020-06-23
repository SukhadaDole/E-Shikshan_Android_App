package com.example.eshik;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class upload extends AppCompatActivity {
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    //private ImageView imageView;

    private EditText txtImageName;
    public EditText FolderName;
    private Uri imgUri;
    Button button2,button1;

    // public static final String FB_STORAGE_PATH= "image/";
    //public static final String FB_DATABASE_PATH= "image";
    public static final int REQUEST_CODE=1234;

    public static String mypath;
    public void accept()
    {
        FolderName = (EditText) findViewById(R.id.Foldername);
        mypath=FolderName.getText().toString();
        Toast.makeText(this, "In Folder :"+mypath, Toast.LENGTH_SHORT).show();
        return;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        txtImageName=(EditText)findViewById(R.id.txtImageName);
        button2=(Button)findViewById(R.id.button2);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFile();

            }
        });

    }

    private void selectFile() {

        Intent intent= new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select File"),1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data !=null && data.getData()!=null){
            uploadFile(data.getData());
        }
    }

    private void uploadFile(Uri data) {

        mStorageRef = FirebaseStorage.getInstance().getReference();
        accept();


        mDatabaseRef= FirebaseDatabase.getInstance().getReference("Uploads");

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference reference=mStorageRef.child("uploads/"+System.currentTimeMillis());
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                        while(!uri.isComplete());
                        Uri url=uri.getResult();
                        String foldername=FolderName.getText().toString();
                        ImageUpload imageUpload=new ImageUpload(txtImageName.getText().toString(),url.toString(),foldername.toString());

                        mDatabaseRef.child(mDatabaseRef.push().getKey()).setValue(imageUpload);
                        Toast.makeText(upload.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage("Uploaded : "+(int)progress+"%");
                    }
                });
    }

  public void btn_viewfiles(View view) {

        startActivity(new Intent(getApplicationContext(),viewFiles.class));

    }


}
