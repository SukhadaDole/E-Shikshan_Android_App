package com.example.eshik;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Register extends AppCompatActivity {
    Button b;
    FirebaseAuth mAuth;
    ImageView image;
    Uri pickedImgUri ;
    static  int PReqCode=1;
    static int RCode=1;
    EditText name,phone,mail,Clss;
    ProgressBar load;
    EditText pass1,pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        pass1=findViewById(R.id.pass1);
        pass2=findViewById(R.id.pass2);
        image = findViewById(R.id.userpic);
        mail=findViewById(R.id.mail);
        phone=findViewById(R.id.contact);
        name=findViewById(R.id.name);
        load=findViewById(R.id.load);
        Clss=findViewById(R.id.std);
        load.setVisibility(View.INVISIBLE);

        mAuth= FirebaseAuth.getInstance();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT >=22)
                {
                    checkAndRequestForPermission();
                }
                else
                {
                    openGallery();
                }
            }
        });
        b=findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b.setVisibility(View.INVISIBLE);
                load.setVisibility(View.VISIBLE);

                String mail1 = mail.getText().toString();
                String contact1 = phone.getText().toString();
                String  name1 = name.getText().toString();
                String class1 = Clss.getText().toString();
                String password1=pass1.getText().toString();
                String password2=pass2.getText().toString();
                if(mail1.isEmpty() || contact1.isEmpty() || name1.isEmpty() || class1.isEmpty())
                {
                    Toast.makeText(Register.this, "Invalid Credentials ", Toast.LENGTH_SHORT).show();
                    b.setVisibility(View.VISIBLE);
                    load.setVisibility(View.INVISIBLE);}

                else
                {
                    createUser(name1,mail1,class1,contact1,password1);
                }
            }
        });
    }

    private void createUser(final String name1, final String mail1, final String class1, String contact1, String password1) {

        mAuth.createUserWithEmailAndPassword(mail1,password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Register.this, "Account Created", Toast.LENGTH_SHORT).show();
                    updateUserInfo(name1,pickedImgUri,mAuth.getCurrentUser());
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(Register.this, "Registered Successfully !! Please check email for verification ", Toast.LENGTH_LONG).show();
                                load.setVisibility(View.INVISIBLE);
                                b.setVisibility(View.VISIBLE);
                                updateUI();
                            }
                            else
                            {
                                Toast.makeText(Register.this, ""+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(Register.this, "Account creation Failed " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    mail.setText("");
                    phone.setText("");
                    Clss.setText("");
                    pass1.setText("");
                    pass2.setText("");
                    name.setText("");
                }
            }
        });

    }

    private void updateUserInfo(final String name1, Uri pickedImgUri, final FirebaseUser currentUser) {
        //first we need to upload user photo to firebase storage and get url

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("users_photo");
        final StorageReference imageFilepath = storageReference.child(pickedImgUri.getLastPathSegment());
        imageFilepath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //image uploaded ,now we can get our image url
                imageFilepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(name1).setPhotoUri(uri).build();

                        currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                {
                                    Toast.makeText(Register.this, "Register complete", Toast.LENGTH_SHORT).show();
                                    updateUI();
                                }
                            }
                        });

                    }
                });
            }
        });

    }

    private void updateUI() {

        Intent intent=new Intent(getApplicationContext(),Auth.class);
        startActivity(intent);
        finish();
    }


    private void openGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,RCode);
    }

    private void checkAndRequestForPermission()
    {
        if(ContextCompat.checkSelfPermission(Register.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(Register.this,Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                Toast.makeText(this, "Please give required permission", Toast.LENGTH_SHORT).show();
                Intent i= new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri u = Uri.fromParts("package",getPackageName(),null);
                i.setData(u);

            }
            else
            {
                ActivityCompat.requestPermissions(Register.this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE},PReqCode);
            }
        }
        else
        {
            openGallery();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == RCode && data != null)
        {
            // user successfully picked an image
            // save refernce
            pickedImgUri=data.getData();
            image.setImageURI(pickedImgUri);

        }
    }
}
