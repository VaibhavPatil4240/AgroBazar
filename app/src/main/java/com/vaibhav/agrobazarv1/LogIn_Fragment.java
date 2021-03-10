package com.vaibhav.agrobazarv1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogIn_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class LogIn_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button b;
    ViewGroup progressView;
    protected boolean isProgressShowing = false;
    private View v1;
    EditText t1;
    EditText t2;
    TextView invalidInput;
    TextView createacc,forgotPass;
    public LogIn_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogIn_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static final String apiurl="https://agrobazarvaibhavpatil.000webhostapp.com/LogIn/logIn.php";
    public static LogIn_Fragment newInstance(String param1, String param2) {
        LogIn_Fragment fragment = new LogIn_Fragment();
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
    private  logInListner loggedIn;
    public interface logInListner{
        void clikedLogIn();
        public void setName(String s);
        void loadingProcess(int b);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).isCreated(true);
        View v=inflater.inflate(R.layout.fragment_log_in, container, false);
        b=v.findViewById(R.id.logInbutton);
        createacc= v.findViewById(R.id.createAccText);
        invalidInput=v.findViewById(R.id.textView);
        forgotPass=v.findViewById(R.id.ForgotPass);
        invalidInput.setText("");
        t1=v.findViewById(R.id.ed1);
        t2=v.findViewById(R.id.ed2);
        t1.setText("");
        t2.setText("");
        createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.container, new CreateAcc_Fragment()).commit();
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loggedIn.loadingProcess(1);
                logIN(v);
            }
        });
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.container, new forgotPassword_fragment()).commit();
            }
        });
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof logInListner){
            loggedIn=(logInListner) context;
        }else {
            throw new RuntimeException(context.toString()+"Must implement fragment listener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity)getActivity()).isCreated(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loggedIn=null;
    }
    public void logIN(View v){
        t1=v.findViewById(R.id.ed1);
        t2=v.findViewById(R.id.ed2);
        invalidInput=v.findViewById(R.id.textView);
        String qry="?t1="+t1.getText().toString().trim()+"&t2="+t2.getText().toString().trim();
        class dbprocess extends AsyncTask<String,Void,String>
        {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loggedIn.loadingProcess(0);
                if(s.equals("not found")){
                    t1.setText("");
                    t2.setText("");
                    invalidInput.setText("Invalid username or password");
                }
                else {
                    loggedIn.setName(s);
                    loggedIn.clikedLogIn();
                }

            }

            @Override
            protected String doInBackground(String... strings) {
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
        dbprocess dp=new dbprocess();
        dp.execute(apiurl+qry);
    }

}