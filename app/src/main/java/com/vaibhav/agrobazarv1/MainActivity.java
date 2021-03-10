package com.vaibhav.agrobazarv1;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity  implements LogIn_Fragment.logInListner ,CreateAcc_Fragment.createAcc,Verify_fragment.verfyingNumber{
    NavigationView nav;
    ImageView cartB;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    TextView view;
    ImageView logo,loadingImg;
    ProgressBar pb;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        loadingImg=findViewById(R.id.loadingImg);
        pb=findViewById(R.id.logInloading);
        loadingProcess(0);
        logo=findViewById(R.id.logo);
        cartB = findViewById(R.id.cartButton);
        toolbar = findViewById(R.id.toolbar);
        nav = findViewById(R.id.navmenu);
        drawer = findViewById(R.id.drawer);
        View hView=nav.getHeaderView(0);
        view=(TextView)hView.findViewById(R.id.userText);
        view.setText("");
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.colseDrawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new LogIn_Fragment()).commit();
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment temp;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Home:
                        temp = new Home_fragment();
                        break;
                    case R.id.orders:
                        temp = new orders_fragment();
                        break;
                    case R.id.account:
                        temp = new Account_Fragment();
                        break;
                    case R.id.seller:
                        sellerCall();
                        break;
                    case R.id.Logout:
                        temp=new LogIn_Fragment();

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }

        });
        cartB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Cart_Fragment()).commit();
            }
        });
    }

    public void sellerCall(){
        Intent intent = new Intent(this, Seller.class);
        startActivity(intent);
    }

    public void isCreated(boolean b){
        if(b){
            toolbar.setVisibility(View.INVISIBLE);
            cartB.setVisibility(View.INVISIBLE);
            logo.setVisibility(View.INVISIBLE);
        }
        else {
            drawer.closeDrawer(GravityCompat.START);
            toolbar.setVisibility(View.VISIBLE);
            cartB.setVisibility(View.VISIBLE);
            logo.setVisibility(View.VISIBLE);
        }
    }
    public void loadingProcess(int b)
    {
        Log.d("usecase", String.valueOf(b));
        switch (b)
        {
            case 1:
                loadingImg.setVisibility(View.VISIBLE);
                loadingImg.setAlpha(0.9f);
                pb.setVisibility(View.VISIBLE);
                break;
            case 0:
                loadingImg.setVisibility(View.INVISIBLE);
                pb.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }
    public void setName(String s){
        view.setText("Hello "+s);
    }
    @Override
    public void clikedLogIn() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Home_fragment()).commit();
    }
}