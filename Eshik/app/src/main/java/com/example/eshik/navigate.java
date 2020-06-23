package com.example.eshik;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class navigate extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
ImageView popupUserImg,popupPostImg,popupaddBtn;
EditText popupTitle,popupDesc;
ProgressBar popupProgress;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    int flag=0;
    Intent intent;
    Dialog popAddPost;
    static  int PReqCode=2;
    static int RCode=2;
    ImageView image;
    Uri pickedImgUri ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);


        mAuth=FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


       flag=0;
       iniPopup();
setupPopupImageClick();
        intent=new Intent(this, Register.class);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 popAddPost.show();


            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
      //  getSupportFragmentManager().beginTransaction().replace(R.id.frame_container1,new HomeFragment()).commit();
        updateNavHeader();
    }

    private void setupPopupImageClick() {

        popupPostImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //here when image we need to open gallery
                //bedfore we open the gallery we need to check the access
checkAndRequestForPermission();
            }
        });
    }

    private void iniPopup() {
popAddPost=new Dialog(this);
        popAddPost.setContentView(R.layout.popup_add_post);
  popAddPost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
  popAddPost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
  popAddPost.getWindow().getAttributes().gravity= Gravity.TOP;

  popupUserImg= popAddPost.findViewById(R.id.popup_user_image);
  popupPostImg= popAddPost.findViewById(R.id.popup_img);
  popupTitle= popAddPost.findViewById(R.id.popup_title);
  popupDesc =popAddPost.findViewById(R.id.pop_desc);
  popupaddBtn= popAddPost.findViewById(R.id.popup_add);
  popupProgress =popAddPost.findViewById(R.id.popup_progressbar);

  //add post click listener
popupaddBtn.setVisibility(View.VISIBLE);
popupProgress.setVisibility(View.INVISIBLE);
        Glide.with(navigate.this).load(currentUser.getPhotoUrl()).into(popupUserImg);
        //load current user profile pic
        popupaddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupProgress.setVisibility(View.VISIBLE);
                popupaddBtn.setVisibility(View.INVISIBLE);

                if(!popupTitle.getText().toString().isEmpty() && !popupDesc.getText().toString().isEmpty() && pickedImgUri != null)
                {
                    // everything is ok
                    //create post object
                    //we need to upload post ile
                    //access firebase storage
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("blog_files");
                    final StorageReference filePath =storageReference.child(pickedImgUri.getLastPathSegment());
                    filePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                             filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                 @Override
                                 public void onSuccess(Uri uri) {

                                     String fileDownloadLink = uri.toString();
                                     //create post object

                                     Post post =new Post(popupTitle.getText().toString(),popupDesc.getText().toString(),fileDownloadLink,currentUser.getUid(),currentUser.getPhotoUrl().toString());
                                 addPost(post);
                                 }
                             }).addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     //something went wrong
                                     Toast.makeText(navigate.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                     popupProgress.setVisibility(View.INVISIBLE);
                                     popupaddBtn.setVisibility(View.VISIBLE);



                                 }
                             });
                        }
                    });


                }
                else
                {
                    Toast.makeText(navigate.this, "Please verify all input fields and choose a file", Toast.LENGTH_SHORT).show();
                    popupaddBtn.setVisibility(View.VISIBLE);
                    popupProgress.setVisibility(View.INVISIBLE);
                }


            }
        });

    }

    private void addPost(Post post) {

        FirebaseDatabase  database = FirebaseDatabase.getInstance();
        DatabaseReference myref= database.getReference("Posts").push();
     String key  =myref.getKey();
     post.setPostKey(key);

     //add post data to firebase database
        myref.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(navigate.this, "Query fired Successfully", Toast.LENGTH_SHORT).show();
                popupProgress.setVisibility(View.INVISIBLE);
                popupaddBtn.setVisibility(View.VISIBLE);
                popAddPost.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportActionBar().setTitle("Home");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container1,new Home()).commit();
        } else if (id == R.id.nav_Subjects) {
            getSupportActionBar().setTitle("Semesters");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container1,new nav11()).commit();}
        else if (id == R.id.nav_download) {
            getSupportActionBar().setTitle("Download and Learn ");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container1,new GotoDownloads()).commit();
        } else if (id == R.id.nav_query) {
            getSupportActionBar().setTitle("Query and Posts");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container1,new HomeFragment()).commit();
        } else if (id == R.id.nav_logout) {

            FirebaseAuth.getInstance().signOut();
            Intent loginActivity = new Intent(getApplicationContext(),Auth.class);
            startActivity(loginActivity);
            finish();
        }
        else if (id == R.id.nav_rateus) {
            getSupportActionBar().setTitle("Rate us");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container1,new Ratings()).commit();
        }
        else if (id == R.id.nav_shareus) {
           getSupportActionBar().setTitle("share us");
            ApplicationInfo api = getApplicationContext().getApplicationInfo();
            String apkpath =api.sourceDir;
            Intent it =new Intent();
            it.setAction(Intent.ACTION_SEND);
            // it.putExtra(Intent.EXTRA_TEXT,"Paste your app");
            // it.setType("text/plain");
            // startActivity(Intent.createChooser(it,"choose app"));
            it.setType("application/vnd.android.package-archive");
            it.putExtra(Intent.EXTRA_PACKAGE_NAME, Uri.fromFile(new File(apkpath)));
            startActivity(Intent.createChooser(it,"ShareVia"));



        }

        else if (id == R.id.nav_changepass) {
           getSupportActionBar().setTitle("Change Password");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container1,new new_password()).commit();


        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader()
    {
        NavigationView navigationView=findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView nusername = headerView.findViewById(R.id.Nusername);
        ImageView Nimage= headerView.findViewById(R.id.Npic);
        TextView  nmail = headerView.findViewById(R.id.Nmail);
        nusername.setText(currentUser.getDisplayName());
        nmail.setText(currentUser.getEmail());
        // Glide to load user image
        // import the library

        Glide.with(this).load(currentUser.getPhotoUrl()).into(Nimage);
    }

    private void checkAndRequestForPermission()
    {
        if(ContextCompat.checkSelfPermission(navigate.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(navigate.this,Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                Toast.makeText(this, "Please give required permission", Toast.LENGTH_SHORT).show();
                Intent i= new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri u = Uri.fromParts("package",getPackageName(),null);
                i.setData(u);

            }
            else
            {
                ActivityCompat.requestPermissions(navigate.this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE},PReqCode);
            }
        }
        else
        {

            openGallery();
        }
    }

    private void openGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,RCode);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == RCode && data != null)
        {
            // user successfully picked an image
            // save refernce
            pickedImgUri=data.getData();
            popupPostImg.setImageURI(pickedImgUri);

        }
    }




}
