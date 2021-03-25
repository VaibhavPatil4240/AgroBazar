package com.vaibhav.agrobazarv1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity  implements LogIn_Fragment.logInListner ,CreateAcc_Fragment.createAcc,Verify_fragment.verfyingNumber,forgotPassword_fragment.forgotPass,singleProduct_Fragment.singleMainListner,Account_Fragment.accountInfo,address_Fragment.addressFrag{
    private static final String CHANNEL_ID ="101";
    NavigationView nav;
    ImageView cartB;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    TextView view,loadingText;
    ImageView logo,loadingImg;
    ProgressBar pb;
    SharedPreferences sp;
    String nameID,userid,username;;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        getToken();
        sp=getSharedPreferences("login", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        loadingImg=findViewById(R.id.loadingImg);
        loadingText=findViewById(R.id.loadingText);
        pb=findViewById(R.id.logInloading);
        loadingProcess(0);
        loadingProcess(0,"");
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
        nameID=sp.getString("Name",null);
        if(nameID==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new LogIn_Fragment()).commit();
        }
        else
        {
            String ni[]=nameID.split("-");
            userid=ni[0];
            username=ni[1];
            view.setText("Hello "+username);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Home_fragment()).commit();
        }
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
                        SharedPreferences.Editor editor=getSharedPreferences("login",Context.MODE_PRIVATE).edit();
                        editor.remove("Name");
                        editor.commit();

                }
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction().replace(R.id.container, temp);
                ft.addToBackStack(null);
                ft.commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }

        });
        cartB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Cart_Fragment()).addToBackStack(null).commit();
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
        switch (b)
        {
            case 1:
                loadingImg.setVisibility(View.VISIBLE);
                loadingImg.setAlpha(0.75f);
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
    public void loadingProcess(int b,String s)
    {
        switch (b)
        {
            case 1:
                loadingImg.setVisibility(View.VISIBLE);
                loadingImg.setAlpha(0.75f);
                pb.setVisibility(View.VISIBLE);
                loadingText.setText("         "+s);
                loadingText.setVisibility(View.VISIBLE);
                break;
            case 0:
                loadingImg.setVisibility(View.INVISIBLE);
                pb.setVisibility(View.INVISIBLE);
                loadingText.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }
    public void setName()
    {
       String arr[]= sp.getString("Name","Use-er").split("-");
       userid=arr[0];
       view.setText("Hello "+arr[1]);
    }
    public String getUser()
    {
        return userid;
    }
    private void getToken()
    {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                Log.d("Token",instanceIdResult.getToken());
            }
        });
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Firebase notification channel";
            String description = "This is the channel to recieve notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}