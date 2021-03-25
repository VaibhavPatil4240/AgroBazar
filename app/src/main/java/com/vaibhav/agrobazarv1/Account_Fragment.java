package com.vaibhav.agrobazarv1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Account_Fragment() {
        // Required empty public constructor
    }
    boolean editFlag;
    TextView phno,username,gmail;
    EditText npass1,npass2;
    ImageView bkIMG;
    CardView cardView;
    Button EditGmail,changePassword,mngAdd,change,cancel;
    String cusID;
    accountInfo id;
    String values[];
    interface accountInfo{
        String getUser();
    }
    public static Account_Fragment newInstance(String param1, String param2) {
        Account_Fragment fragment = new Account_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_account_, container, false);
        setAccounValues();
        username=v.findViewById(R.id.userName);
        phno=v.findViewById(R.id.phnoText);
        gmail=v.findViewById(R.id.gmailText);
        EditGmail=v.findViewById(R.id.EditGmail);
        changePassword=v.findViewById(R.id.EditPassword);
        mngAdd=v.findViewById(R.id.manageAdd);
        npass1=v.findViewById(R.id.newPass);
        npass2=v.findViewById(R.id.newPass2);
        change=v.findViewById(R.id.changePass);
        cancel=v.findViewById(R.id.cancel);
        cardView=v.findViewById(R.id.cardView2);
        bkIMG=v.findViewById(R.id.blurredIMG);
        cardView.setVisibility(View.INVISIBLE);
        bkIMG.setVisibility(View.INVISIBLE);
        mngAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.container,new address_Fragment()).addToBackStack(null).commit();
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editFlag=true;
                passOrgmail(editFlag);
                cardView.setVisibility(View.VISIBLE);
                bkIMG.setVisibility(View.VISIBLE);
            }
        });
        EditGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editFlag=false;
                passOrgmail(editFlag);
                cardView.setVisibility(View.VISIBLE);
                bkIMG.setVisibility(View.VISIBLE);
            }
        });
        bkIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardView.setVisibility(View.INVISIBLE);
                bkIMG.setVisibility(View.INVISIBLE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardView.setVisibility(View.INVISIBLE);
                bkIMG.setVisibility(View.INVISIBLE);
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editFlag)
                {
                    String pass1= npass1.getText().toString().trim();
                    String pass2=npass2.getText().toString().trim();
                    if(!pass1.equals(pass2))
                    {
                        Toast.makeText(getContext(),"Confirm Password Correctly",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        changePassword(pass1);
                    }
                }
                else
                {
                    String newGmail=npass2.getText().toString().trim();
                    if(newGmail.isEmpty())
                    {
                        Toast.makeText(getContext(),"Please Enter gmail address",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        changeGmail(newGmail);
                    }
                }
            }
        });
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof accountInfo){
            id=(accountInfo) context;
        }else {
            throw new RuntimeException(context.toString()+"Must implement fragment listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        id=null;
    }

    public void setAccounValues()
    {
        cusID=id.getUser();
        String url="https://agrobazarvaibhavpatil.000webhostapp.com/Customer/setAccountInfo.php";
        String qry="?t1="+cusID;
        class setAccInfo extends AsyncTask<String,Void,String>
        {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                values=s.split("-");
                username.setText(values[0]+" "+values[1]);
                gmail.setText("Gmail : \n"+values[2]);
                phno.setText("Phone Number : "+values[3]);
            }
            @Override
            protected String doInBackground(String... strings){
                String furl=strings[0];
                try {
                    URL url=new URL(furl);
                    HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    return br.readLine();
                } catch (Exception e) {
                    return e.getMessage();
                }
            }
        }
        setAccInfo rs=new setAccInfo();
        rs.execute(url+qry);
    }
    public void passOrgmail(boolean b)
    {
        if(b)
        {
            npass1.setVisibility(View.VISIBLE);
            npass2.setHint("Confirm Password");
        }
        else {
            npass1.setVisibility(View.INVISIBLE);
            npass2.setHint("New Gmail");
        }
    }
    public void changePassword(String pas)
    {
        cardView.setVisibility(View.INVISIBLE);
        bkIMG.setVisibility(View.INVISIBLE);
        String url="https://agrobazarvaibhavpatil.000webhostapp.com/LogIn/changePass.php";
        String qry="?t1="+values[3].trim()+"&t2="+pas.trim();
        class createaccproc extends AsyncTask<String,Void,String>
        {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.trim().equals("1"))
                {
                    Toast.makeText(getContext(),"Password changed successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(),"Some error occured please retry", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(String... strings) {
                String furl=strings[0];
                try {
                    URL url=new URL(furl);
                    HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    return br.readLine().toString().trim();
                } catch (Exception e) {
                    return e.getMessage();
                }
            }
        }
        createaccproc acc=new createaccproc();
        acc.execute(url+qry);
    }
    public void changeGmail(String newGmail)
    {
        cardView.setVisibility(View.INVISIBLE);
        bkIMG.setVisibility(View.INVISIBLE);
        String url="https://agrobazarvaibhavpatil.000webhostapp.com/Customer/changeGmail.php";
        String qry="?t1="+cusID+"&t2="+newGmail;
        class createaccproc extends AsyncTask<String,Void,String>
        {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.trim().equals("1"))
                {
                    Toast.makeText(getContext(),"Gmail Address changed successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(),"Some error occured please retry", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(String... strings) {
                String furl=strings[0];
                try {
                    URL url=new URL(furl);
                    HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    return br.readLine().toString().trim();
                } catch (Exception e) {
                    return e.getMessage();
                }
            }
        }
        createaccproc acc=new createaccproc();
        acc.execute(url+qry);
    }
}