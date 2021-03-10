package com.vaibhav.agrobazarv1;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Seller extends AppCompatActivity {
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbarS);
        setSupportActionBar(toolbar);
        nav=findViewById(R.id.sellerNavmenu);
        drawer=findViewById(R.id.sellerDrawer);
        toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.openDrawer,R.string.colseDrawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
}