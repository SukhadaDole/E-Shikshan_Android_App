package com.example.eshik;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class nav extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer=findViewById(R.id.drawer_layout);

     ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new Home()).commit();
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
    } else{
        super.onBackPressed();}
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new Home()).commit();
        } else if (id == R.id.nav_slideshow) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new nav11()).commit();
        } else if (id == R.id.nav_slideshow) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new nav33()).commit();
        } else if (id == R.id.nav_tools) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new nav11()).commit();
        } else if (id == R.id.nav_share) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new nav22()).commit();
        } else if (id == R.id.nav_send) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new nav11()).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
